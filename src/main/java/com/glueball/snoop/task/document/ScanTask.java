package com.glueball.snoop.task.document;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.common.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.glueball.snoop.entity.FileData;
import com.glueball.snoop.entity.FileId;
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
    private final int BUFFER_SIZE = 500;

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
    private String fileDataDirectory = "install";

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

            String mapFileName = share.getName();
            try {
                mapFileName = MD5.md5DigestHexStr(share
                        .getRemotePath());

            } catch (final NoSuchAlgorithmException e) {

                LOG.warn("Error creating Scan Task: MD5 digest "
                        + "algorithm is not supported.");
                LOG.debug(e.getMessage(), e);
            }

            final File dataFile = new File(fileDataDirectory
                    + File.separator + mapFileName + "_d.map");
            if (!dataFile.exists()) {

                dataFile.createNewFile();
            }

            final MMapReader<FileData> reader =
                    new MMapReader<FileData>(
                            dataFile, BUFFER_SIZE, FileData.class);

            final List<FileData> indexStatus = reader.read();
            final List<FileData> files = new ArrayList<FileData>();

            final Map<FileId, FilePath[]> fileNames =
                    new HashMap<FileId, FilePath[]>();

            final Map<FileId, FilePath[]> localPaths =
                    new HashMap<FileId, FilePath[]>();

            final Map<FileId, FilePath[]> remotePaths =
                    new HashMap<FileId, FilePath[]>();

            final String path = !StringUtils.isEmpty(
                    share.getLocalPath()) ? share.getLocalPath() : share
                    .getRemotePath();

            Files.walkFileTree(Paths.get(path),
                    new SnoopFileVisitor(share,
                            files,
                            fileNames,
                            localPaths,
                            remotePaths));

            final List<FileData> addList = new ArrayList<FileData>();
            final List<FileData> updateList = new ArrayList<FileData>();
            final List<FileData> deleteList = new ArrayList<FileData>();
            processDocumentStatus(files, indexStatus, addList, updateList,
                    deleteList);

            updateFileData(dataFile, addList, updateList, deleteList);

            final File fileNameFile = new File(fileDataDirectory
                    + File.separator + mapFileName + "_fn.map");
            if (!fileNameFile.exists()) {

                fileNameFile.createNewFile();
            }

            final File localPathFile = new File(fileDataDirectory
                    + File.separator + mapFileName + "_lp.map");
            if (!localPathFile.exists()) {

                localPathFile.createNewFile();
            }

            final File remotePathFile = new File(fileDataDirectory
                    + File.separator + mapFileName + "_rp.map");
            if (!localPathFile.exists()) {

                localPathFile.createNewFile();
            }

            updateFilePath(fileNameFile, addList, fileNames);
            updateFilePath(localPathFile, addList, localPaths);
            updateFilePath(remotePathFile, addList, remotePaths);

        } catch (final Exception e) {

            LOG.info("Error scanning network share: " + share.getName());
            e.printStackTrace();
            LOG.debug(e.getMessage(), e);
        }

        LOG.info("Netwok share: " + share.getName()
                + " successfully udpated.");
    }

    /**
     * Helper method to write out the file data to the map file.
     *
     * @param addList
     *            list of new file data to write.
     * @param updateList
     *            list of modifies files data to write.
     * @param deleteList
     *            list of file data of deleted files.
     * @throws IOException
     *             on any IO error.
     */
    private void updateFileData(final File f, final List<FileData> addList,
            final List<FileData> updateList,
            final List<FileData> deleteList) throws IOException {

        final MMapWriter<FileData> writer = new MMapWriter<FileData>(
                f, BUFFER_SIZE);

        System.out.println(addList.size());
        writer.write(addList);
        System.out.println(updateList.size());
        writer.update(updateList);
        System.out.println(deleteList.size());
        writer.update(deleteList);

        LOG.info("File data has successfully update on share: "
                + share.getName());
    }

    private void updateFilePath(final File f, final List<FileData> datas,
            final Map<FileId, FilePath[]> paths) throws IOException {

        final List<FilePath> toWrite = new ArrayList<FilePath>();
        for (final FileData data : datas) {

            for (final FilePath path : paths.get(data.getId())) {

                toWrite.add(path);
            }
        }

        final MMapWriter<FilePath> writer = new MMapWriter<FilePath>(
                f, BUFFER_SIZE);

        writer.write(toWrite);
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

    /**
     * Helper method to compare to lists of FileData and produce the object to
     * add, modify or delete from the database.
     *
     * @param pLeft
     *            the objects on the left side.
     * @param pRight
     *            the objects on the right side.
     * @param pToAdd
     *            objects are available on the left side but not on the right
     *            side.
     * @param pToUpdate
     *            objects are available on both sides but the last modified time
     *            is newer on the left side.
     * @param pToDelete
     *            object are available on the right side but not on the left
     *            side.
     */
    private void processDocumentStatus(final List<FileData> pLeft,
            final List<FileData> pRight, final List<FileData> pToAdd,
            final List<FileData> pToUpdate, final List<FileData> pToDelete) {

        final Map<FileId, Long> left = toIdLastmTimeMap(pLeft);
        final Map<FileId, Long> right = toIdLastmTimeMap(pRight);

        final Map<FileId, FileData> leftMap = toIdFileDataMap(pLeft);
        final Map<FileId, FileData> rightMap = toIdFileDataMap(pRight);

        for (final FileId key : left.keySet()) {

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

        for (final FileId key : right.keySet()) {

            if (!left.containsKey(key)) {

                final FileData data = rightMap.get(key);
                data.setStatus(IndexStatus.DELETED.getStatus());

                pToDelete.add(data);
            }
        }
    }

    /**
     * Helper method to create a fileId, last update time map from the FileData
     * list.
     *
     * @param files
     *            the input list.
     * @return the map.
     */
    private Map<FileId, Long> toIdLastmTimeMap(final List<FileData> files) {

        final Map<FileId, Long> map = new HashMap<FileId, Long>();

        for (final FileData file : files) {

            map.put(file.getId(), file.getLmtime());
        }
        return map;
    }

    /**
     * Helper method to create a fileId, FileData map from a list of FileData.
     *
     * @param files
     *            the list of FileData.
     * @return the map.
     */
    private final Map<FileId, FileData> toIdFileDataMap(
            final List<FileData> files) {

        final Map<FileId, FileData> map = new HashMap<FileId, FileData>();

        for (final FileData file : files) {

            map.put(file.getId(), file);
        }
        return map;
    }
}
