package com.glueball.snoop.test.mmap;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
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
import com.glueball.snoop.mmap.MappableVisitor;
import com.glueball.snoop.util.DocumentPathUtil;

/**
 * Test class to test the memory mapped file write and read operations.
 *
 * @author karesz
 */
public class MMapReaderWriterTest {

    /**
     * Buffer size.
     */
    private static final int BUFFER_SIZE = 100;

    /**
     * List of generated objects to write into and read from the memory mapped
     * file.
     */
    final static List<FileData> writeList = new ArrayList<FileData>(500000);

    /**
     * Setup method to generate test data.
     *
     * @throws NoSuchAlgorithmException
     */
    @BeforeClass
    public static void setup() throws NoSuchAlgorithmException {

        for (final DocumentPath dp : DocumentPathUtil
                .genDocumentPaths(500000)) {

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
     * Simple write and read test.
     *
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

        assertEquals(500000, readlist.size());
    }

    /**
     * Test read method with visitor.
     *
     * @throws Exception
     */
    @Test
    public final void readTest() throws Exception {

        final File f = new File("/tmp/mmaptestfile.map");
        f.delete();
        f.createNewFile();

        final MMapWriter writer = new MMapWriter(f, BUFFER_SIZE);
        writer.write(writeList);

        final MMapReader reader =
                new MMapReader(f, BUFFER_SIZE, FileData.class);

        final List<FileData> list = new ArrayList<FileData>();

        reader.read(new MappableVisitor() {

            @Override
            public void onObject(final Mappable object) {

                list.add((FileData) object);
            }

            @Override
            public void onStart() {

                // TODO Auto-generated method stub

            }

            @Override
            public void onFinish() {

                // TODO Auto-generated method stub

            }

        });

        assertEquals(writeList, list);
    }
}
