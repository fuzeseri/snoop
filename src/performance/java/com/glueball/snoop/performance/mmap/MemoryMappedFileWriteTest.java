/**
 * 
 */
package com.glueball.snoop.performance.mmap;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.entity.FileData;
import com.glueball.snoop.entity.FilePath;
import com.glueball.snoop.util.ByteUtil;
import com.glueball.snoop.util.DocumentPathUtil;

/**
 * @author karesz
 */
public class MemoryMappedFileWriteTest {

    final static List<FileData> fDatas = new ArrayList<FileData>(100000);

    final static List<FilePath> fPaths = new ArrayList<FilePath>(100000);

    private static final int BUFFER_LENGTH = 100;

    private static final int FILE_DATA_SIZE = new FileData().size();

    private static final int FILE_PATH_SIZE = new FilePath().size();

    @BeforeClass
    public final static void setup() throws NoSuchAlgorithmException,
            IOException {

        for (final DocumentPath dp : DocumentPathUtil
                .genDocumentPaths(100000)) {

            final FileData fd = new FileData();
            fd.setDeleted((byte) 1);
            fd.setId(dp.getId());
            fd.setLitime(Long.MAX_VALUE);
            fd.setLmtime(dp.getLastModifiedTime());
            fd.setStatus((short) 5);
            fd.setLock(0L);
            fd.setLocktime(0L);

            fDatas.add(fd);

            int i = 0;
            for (final byte[] slice : ByteUtil.stringToByteArrays(dp.getPath(),
                    80)) {

                final FilePath fp = new FilePath();
                fp.setDeleted((byte) 1);
                fp.setId(dp.getId());
                fp.setOrder(i++);
                fp.setPath(slice);

                fPaths.add(fp);
            }
        }
    }

    @Test
    public final void testWriteFileData() throws IOException,
            NoSuchAlgorithmException,
            InterruptedException {

        final File f = new File("/tmp/filedata.map");
        long bufferSize = FILE_DATA_SIZE * BUFFER_LENGTH;

        try (final RandomAccessFile raf = new RandomAccessFile(f, "rw");
                final FileChannel fc = raf.getChannel()) {

            long start = fc.size();

            MappedByteBuffer mem = fc.map(FileChannel.MapMode.READ_WRITE,
                    start,
                    bufferSize);

            long counter = start;
            for (final FileData fData : fDatas) {

                if (!mem.hasRemaining()) {

                    start += mem.position();
                    mem = fc.map(FileChannel.MapMode.READ_WRITE, start,
                            bufferSize);
                }

                fData.setPosition(counter);
                counter += fData.size();

                mem.put(fData.toByteArray());
            }
        }
    }

    @Test
    public final void testWriteFilePath() throws IOException,
            NoSuchAlgorithmException,
            InterruptedException {

        final File f = new File("/tmp/filepath.map");
        long bufferSize = FILE_PATH_SIZE * BUFFER_LENGTH;

        try (final RandomAccessFile raf = new RandomAccessFile(f, "rw");
                final FileChannel fc = raf.getChannel()) {

            long start = fc.size();

            MappedByteBuffer mem = fc.map(FileChannel.MapMode.READ_WRITE,
                    start,
                    bufferSize);

            long counter = start;
            for (final FilePath fPath : fPaths) {

                if (!mem.hasRemaining()) {

                    start += mem.position();
                    mem = fc.map(FileChannel.MapMode.READ_WRITE, start,
                            bufferSize);
                }

                fPath.setPosition(counter);
                counter += fPath.size();

                mem.put(fPath.toByteArray());
            }
        }
    }

}
