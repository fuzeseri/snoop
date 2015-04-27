package com.glueball.snoop.task.document;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.cxf.common.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.glueball.snoop.entity.FileData;
import com.glueball.snoop.entity.FilePath;
import com.glueball.snoop.entity.IndexStatus;
import com.glueball.snoop.entity.NetworkShare;
import com.glueball.snoop.file.visitor.SnoopFileVisitor;
import com.glueball.snoop.mmap.MMapReader;
import com.glueball.snoop.mmap.MMapWriter;
import com.glueball.snoop.quartz.job.SnoopTask;
import com.glueball.snoop.util.MD5;

/**
 * Scheduled task to scan a network share and maintain the file data maps of it.
 *
 * @author karesz
 */
public final class ScanTask implements SnoopTask {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LogManager
            .getLogger(ScanTask.class);

    /**
     * File read/write buffer size.
     */
    private final int BUFFER_SIZE = 2000;

    /**
     * Field to store the name of the task.
     */
    private String name;

    /**
     * Run interval in millis.
     */
    private long interval;

    /**
     * The network share to scan.
     */
    private NetworkShare share;

    /**
     * Root path of the file data map files.
     */
    private String fileDataDirectory;

    /**
     * Constructor.
     *
     * @param pShare
     *            The network share to scan.
     * @param pInterval
     *            Run interval in millis.
     * @param pName
     *            Field to store the name of the task.
     */
    public ScanTask(final NetworkShare pShare, final long pInterval,
            final String pName) {

        this.name = pName;
        this.interval = pInterval;
        this.share = pShare;
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.quartz.job.SnoopTask#run()
     */
    @Override
    public void run() {

        LOG.info("Loading netwok share: " + share.getName());
        LOG.info("Remote path: " + share.getRemotePath());
        LOG.info("Local  path: " + share.getLocalPath());

        try {

            final String mapFileName = MD5.md5DigestHexStr(share
                    .getRemotePath());

            final File mapFile = new File(fileDataDirectory
                    + File.pathSeparator + mapFileName + ".map");

            final MMapReader<FileData> reader =
                    new MMapReader<FileData>(
                            mapFile, BUFFER_SIZE, FileData.class);

            final List<FileData> indexStatus = reader.read();
            final List<FileData> files = new ArrayList<FileData>();

            final Map<byte[], Set<FilePath>> localPaths =
                    new HashMap<byte[], Set<FilePath>>();

            final Map<byte[], Set<FilePath>> remotePaths =
                    new HashMap<byte[], Set<FilePath>>();

            final String path = !StringUtils.isEmpty(
                    share.getLocalPath()) ? share.getLocalPath() : share
                    .getRemotePath();

            Files.walkFileTree(Paths.get(path),
                    new SnoopFileVisitor(share,
                            files,
                            localPaths,
                            remotePaths));

            final List<FileData> addList = new ArrayList<FileData>();
            final List<FileData> updateList = new ArrayList<FileData>();
            final List<FileData> deleteList = new ArrayList<FileData>();
            processDocumentStatus(files, indexStatus, addList, updateList,
                    deleteList);

            final MMapWriter<FileData> writer = new MMapWriter<FileData>(
                    mapFile, BUFFER_SIZE);

            writer.write(addList);
            writer.update(updateList);
            writer.update(deleteList);

        } catch (final Exception e) {

            LOG.info("Error scanning network share: " + share.getName());
            LOG.debug(e.getMessage(), e);
        }

        LOG.info("Loading netwok share: " + share.getName()
                + " successfully udpated.");
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.quartz.job.SnoopTask#getName()
     */
    @Override
    public String getName() {

        return name;
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.quartz.job.SnoopTask#setName(java.lang.String)
     */
    @Override
    public void setName(final String name) {

        this.name = name;
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.quartz.job.SnoopTask#getIntervalSeconds()
     */
    @Override
    public int getIntervalSeconds() {

        return (int) (interval / 1000);
    }

    /**
     * @return the interval
     */
    public final long getInterval() {

        return interval;
    }

    /**
     * @param interval
     *            the interval to set
     */
    public final void setInterval(final long interval) {

        this.interval = interval;
    }

    /**
     * @return the share
     */
    public final NetworkShare getShare() {

        return share;
    }

    /**
     * @param share
     *            the share to set
     */
    public final void setShare(final NetworkShare share) {

        this.share = share;
    }

    private void processDocumentStatus(final List<FileData> pLeft,
            final List<FileData> pRight, final List<FileData> pToAdd,
            final List<FileData> pToUpdate, final List<FileData> pToDelete) {

        final Map<byte[], Long> left = toIdLastmTimeMap(pLeft);
        final Map<byte[], Long> right = toIdLastmTimeMap(pRight);

        final Map<byte[], FileData> leftMap = toIdFileDataMap(pLeft);
        final Map<byte[], FileData> rightMap = toIdFileDataMap(pRight);

        for (final byte[] key : left.keySet()) {

            if (right.containsKey(key)) {

                if (left.get(key) > right.get(key)) {

                    final FileData data = leftMap.get(key);
                    data.setStatus(IndexStatus.MODIFIED.getStatus());

                    pToUpdate.add(data);
                }
            } else {

                pToAdd.add(leftMap.get(key));
            }
        }

        for (final byte[] key : right.keySet()) {

            if (!left.containsKey(key)) {

                final FileData data = rightMap.get(key);
                data.setStatus(IndexStatus.DELETED.getStatus());

                pToDelete.add(data);
            }
        }
    }

    private Map<byte[], Long> toIdLastmTimeMap(final List<FileData> files) {

        final Map<byte[], Long> map = new HashMap<byte[], Long>();

        for (final FileData file : files) {

            map.put(file.getId(), file.getLmtime());
        }
        return map;
    }

    private final Map<byte[], FileData> toIdFileDataMap(
            final List<FileData> files) {

        final Map<byte[], FileData> map = new HashMap<byte[], FileData>();

        for (final FileData file : files) {

            map.put(file.getId(), file);
        }
        return map;
    }
}
