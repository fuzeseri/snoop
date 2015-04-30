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

import org.junit.Test;

import com.glueball.snoop.entity.FileData;
import com.glueball.snoop.entity.FilePath;
import com.glueball.snoop.util.MD5;

/**
 * @author karesz
 */
public class MemoryMappedFileReadTest {

    private static final List<FileData> fDatas =
            new ArrayList<FileData>(1000000);

    private static final List<FilePath> fPaths =
            new ArrayList<FilePath>(1000000);

    private static final int BUFFER_LENGTH = 100;

    private static final int FILE_DATA_SIZE = new FileData().size();

    private static final int FILE_PATH_SIZE = new FilePath().size();

    private static final FileData datObj = new FileData();
    private static final FilePath pathObj = new FilePath();

    @Test
    public final void testReadFileData() throws IOException,
            NoSuchAlgorithmException,
            InterruptedException {

        final File f = new File("/tmp/filedata.map");
        long bufferSize = FILE_DATA_SIZE * BUFFER_LENGTH;

        try (final RandomAccessFile raf = new RandomAccessFile(f, "r");
                final FileChannel fc = raf.getChannel()) {

            long start = 0;

            MappedByteBuffer mem = fc.map(FileChannel.MapMode.READ_ONLY,
                    start,
                    bufferSize);

            long counter = 0;
            while (counter < fc.size()) {

                if (!mem.hasRemaining()) {

                    start += mem.position();
                    mem = fc.map(FileChannel.MapMode.READ_ONLY, start,
                            bufferSize);
                }

                byte[] dat = new byte[FILE_DATA_SIZE];
                mem.get(dat);

                fDatas.add(datObj.fromByteArray(dat));
                counter = counter + dat.length;
            }
        }
    }

    @Test
    public final void testReadFilePath() throws IOException,
            NoSuchAlgorithmException,
            InterruptedException {

        final File f = new File("/tmp/filepath.map");
        long bufferSize = FILE_PATH_SIZE * BUFFER_LENGTH;

        try (final RandomAccessFile raf = new RandomAccessFile(f, "r");
                final FileChannel fc = raf.getChannel()) {

            long start = 0;

            MappedByteBuffer mem = fc.map(FileChannel.MapMode.READ_ONLY,
                    start,
                    bufferSize);

            long counter = 0;
            while (counter < fc.size()) {

                if (!mem.hasRemaining()) {

                    start += mem.position();
                    mem = fc.map(FileChannel.MapMode.READ_ONLY, start,
                            bufferSize);
                }

                byte[] dat = new byte[FILE_PATH_SIZE];
                mem.get(dat);

                fPaths.add(pathObj.fromByteArray(dat));
                counter = counter + dat.length;
            }
        }
    }

    @Test
    public void writeofresult() {

        int i = 0;
        for (FileData data : fDatas) {

            System.out.println(data.getPosition());
            System.out.println(data.getDeleted());
            System.out.println(MD5.toHexString(data.getId().getBytes()));
            System.out.println(data.getLmtime());
            System.out.println(data.getLitime());
            System.out.println(data.getStatus());
            System.out.println(data.getLock());
            System.out.println(data.getLocktime());

            System.out.println("--------------------------------");

            if (i++ > 2) {
                break;
            }
        }

        int j = 0;
        for (FilePath path : fPaths) {

            System.out.println(path.getPosition());
            System.out.println(path.getDeleted());
            System.out.println(MD5.toHexString(path.getId().getBytes()));
            System.out.println(path.getOrder());
            System.out.println(new String(path.getPath()));

            System.out.println("--------------------------------");

            if (j++ > 2) {
                break;
            }
        }
    }
}
