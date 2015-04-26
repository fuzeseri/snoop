package com.glueball.snoop.mmap;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * @author karesz
 */
public class MMapWriter {

    /**
     * File to write out the data.
     */
    private final File file;

    /**
     * The maximum buffer size to use at mapping.
     */
    private final int maxBufSize;

    /**
     * Constructor.
     *
     * @param pFile
     *            File to write out the data.
     * @param pMaxBufSize
     *            The maximum buffer size to use at mapping.
     */
    public MMapWriter(final File pFile, final int pMaxBufSize) {

        this.file = pFile;
        this.maxBufSize = pMaxBufSize;
    }

    /**
     * Writes a list of Mappable objects to the memory mapped file. It always
     * writes the data to end of the existing file.
     *
     * @param list
     *            The list Mappable objects to write.
     * @throws IOException
     *             on any IO errors.
     */
    public void write(final List<? extends Mappable> list) throws IOException {

        if (list.size() == 0) {

            return;
        }

        final BufferSizeCalculator bufSize = new BufferSizeCalculator(list
                .size(), maxBufSize);

        int objectSize = 0;
        if (list.get(0) != null) {

            objectSize = list.get(0).size();
        }

        try (final RandomAccessFile raf = new RandomAccessFile(file, "rw");
                final FileChannel fc = raf.getChannel()) {

            long start = fc.size();

            MappedByteBuffer mem = fc.map(FileChannel.MapMode.READ_WRITE,
                    start,
                    objectSize * bufSize.nextBufSize());

            long position = start;
            for (final Mappable mappable : list) {

                if (!mem.hasRemaining()) {

                    start += mem.position();
                    mem = fc.map(FileChannel.MapMode.READ_WRITE, start,
                            objectSize * bufSize.nextBufSize());
                }

                mappable.setPosition(position);
                mem.put(mappable.toByteArray());

                position = position + objectSize;
            }
        }
    }

    /**
     * Rewrites a list of Mappable objects in the memory mapped file.
     *
     * @param list
     *            The list Mappable objects to write.
     * @throws IOException
     *             on any IO errors.
     */
    public void update(final List<Mappable> list) throws IOException {

        if (list.size() > 0) {

            return;
        }

        int objectSize = 0;
        if (list.get(0) != null) {

            objectSize = list.get(0).size();
        }

        try (final RandomAccessFile raf = new RandomAccessFile(file, "rw");
                final FileChannel fc = raf.getChannel()) {

            for (final Mappable mappable : list) {

                final MappedByteBuffer mem =
                        fc.map(FileChannel.MapMode.READ_WRITE,
                                mappable.getPosition(),
                                objectSize);

                mem.put(mappable.toByteArray());
            }
        }
    }
}
