/**
 * 
 */
package com.glueball.snoop.test.mmap;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.entity.FileData;
import com.glueball.snoop.mmap.MMapReader;
import com.glueball.snoop.mmap.MMapWriter;
import com.glueball.snoop.mmap.Mappable;
import com.glueball.snoop.performance.util.DocumentPathUtil;

/**
 * @author karesz
 */
public class MMapReaderWriterTest {

    private static final int BUFFER_SIZE = 100;

    final static List<FileData> writeList = new ArrayList<FileData>(100000);

    @BeforeClass
    public static void setup() throws NoSuchAlgorithmException {

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

            writeList.add(fd);
        }
    }

    /**
     * @throws Exception
     */
    @Test
    public final void readWriteTest() throws Exception {

        final File f = new File("/tmp/mmaptestfile.map");
        f.delete();
        f.createNewFile();

        final MMapWriter writer = new MMapWriter(f, BUFFER_SIZE);
        writer.write(writeList);

        final MMapReader reader = new MMapReader(f, BUFFER_SIZE, FileData.class);
        final List<Mappable> readlist = reader.read();

        assertEquals(100000, readlist.size());
    }

}
