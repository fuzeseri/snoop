/**
 * 
 */
package com.glueball.snoop.task.document;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.glueball.snoop.bean.DataIndexer;
import com.glueball.snoop.entity.FileData;
import com.glueball.snoop.entity.FileId;
import com.glueball.snoop.entity.FilePath;
import com.glueball.snoop.entity.IndexStatus;
import com.glueball.snoop.entity.NetworkShare;
import com.glueball.snoop.mmap.MMapReader;
import com.glueball.snoop.mmap.MMapWriter;
import com.glueball.snoop.mmap.MappableVisitor;
import com.glueball.snoop.quartz.job.SnoopTask;
import com.glueball.snoop.util.MD5;

/**
 * @author karesz
 */
public class IndexTask implements SnoopTask {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LogManager
            .getLogger(IndexTask.class);

    /**
     * Random number generator.
     */
    private final Random RAND = new Random();

    /**
     * File read/write buffer size.
     */
    private final int BUFFER_SIZE = 500;

    /**
     * The name of the task.
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
     * Maximum files per round.
     */
    private final int maxFiles;

    /**
     * Data indexer bean.
     */
    private DataIndexer dataIndexer;

    /**
     * @param dataIndexer
     *            the dataIndexer to set
     */
    public final void setDataIndexer(final DataIndexer dataIndexer) {

        this.dataIndexer = dataIndexer;
    }

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
    public IndexTask(final NetworkShare pShare, final long pInterval,
            final String pName, final int pMaxFiles) {

        this.name = pName;
        this.interval = pInterval;
        this.share = pShare;
        this.maxFiles = pMaxFiles;
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.quartz.job.SnoopTask#run()
     */
    @Override
    public void run() {

        LOG.info("Indexing netwok share: " + share.getName());
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

            final List<FileData> locked = lockFileData(dataFile);
            final Map<FileId, String> fileNames = getMappedData(
                    fileNameFile, locked);
            final Map<FileId, String> localPaths = getMappedData(
                    localPathFile, locked);
            final Map<FileId, String> remotePaths = getMappedData(
                    remotePathFile, locked);

            dataIndexer.index(locked, fileNames, localPaths, remotePaths);

            updateDocuments(dataFile, locked);

        } catch (final Exception e) {

            LOG.info("Error indexing network share: " + share.getName());
            e.printStackTrace();
            LOG.debug(e.getMessage(), e);
        }

        LOG.info("Netwok share: " + share.getName()
                + " successfully indexed.");

    }

    private final List<FileData> lockFileData(final File dataFile)
            throws Exception {

        final long lock = RAND.nextLong();
        final long now = new Date().getTime();

        final List<FileData> datas = new ArrayList<FileData>(maxFiles);
        final MMapReader<FileData> reader =
                new MMapReader<FileData>(dataFile, BUFFER_SIZE, FileData.class);

        reader.read(new MappableVisitor<FileData>() {

            @Override
            public void onObject(final FileData data) {

                if (data.getLock() == 0
                        &&
                        (data.getStatus() == IndexStatus.NEW.getStatus()
                                || data.getStatus() == IndexStatus.MODIFIED
                                        .getStatus()
                                || data.getStatus() == IndexStatus.REINDEX
                                        .getStatus()
                                || data.getStatus() == IndexStatus.DELETED
                                .getStatus())
                ) {
                    data.setLock(lock);
                    data.setLocktime(now);
                    datas.add(data);
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

        }, maxFiles);

        final MMapWriter<FileData> writer = new MMapWriter<FileData>(
                dataFile, BUFFER_SIZE);

        writer.write(datas);

        return datas;
    }

    private Map<FileId, String> getMappedData(final File file,
            final List<FileData> datas) throws Exception {

        final List<FileId> ids = new ArrayList<FileId>(datas.size());
        for (final FileData data : datas) {

            ids.add(data.getId());
        }

        final Map<FileId, StringBuilder> map =
                new HashMap<FileId, StringBuilder>();
        final MMapReader<FilePath> reader =
                new MMapReader<FilePath>(file, BUFFER_SIZE, FilePath.class);

        reader.read(new MappableVisitor<FilePath>() {

            @Override
            public void onObject(final FilePath filePath) {

                final FileId fileId = filePath.getId();
                if (ids.contains(fileId)) {

                    if (!map.containsKey(fileId)) {

                        map.put(fileId, new StringBuilder());
                    }
                    try {
                        map.get(fileId).append(
                                new String(filePath.getPath(), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {

                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }
        });

        final Map<FileId, String> retMap = new HashMap<FileId, String>();

        for (final FileId id : map.keySet()) {

            retMap.put(id, map.get(id).toString().trim());
        }
        return retMap;
    }

    private void updateDocuments(final File f, final List<FileData> data)
            throws IOException {

        final long now = new Date().getTime();
        final MMapWriter<FileData> writer = new MMapWriter<FileData>(
                f, BUFFER_SIZE);

        for (final FileData dat : data) {

            dat.setLock(0L);
            dat.setLocktime(0L);
            dat.setLitime(now);
        }
        writer.update(data);
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
    public void setName(final String pName) {

        this.name = pName;
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
}
