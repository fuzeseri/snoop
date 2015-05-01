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
import java.util.ArrayList;
import java.util.List;

/**
 * Reads Mappable objects from a memory mapped file.
 *
 * @author karesz
 */
public class MMapReader<T extends Mappable> {

    /**
     * The file where the objects are stored.
     */
    private final File file;

    /**
     * The maximum number of objects
     */
    private final int maxBufSize;

    /**
     * Mappable object to fill it with the data read.
     */
    private final T mapObj;

    public MMapReader(final File pFile, final int pMaxBufSize,
            final Class<T> pClazz) throws Exception {

        this.file = pFile;
        this.maxBufSize = pMaxBufSize;
        this.mapObj = pClazz.newInstance();
    }

    /**
     * Read Objects from the memory mapped file and return with a list of them.
     *
     * @return List of mappable object read form the file.
     * @throws IOException
     *             on any IO error.
     */
    @SuppressWarnings("unchecked")
    public List<T> read() throws IOException {

        List<T> list = new ArrayList<T>(0);
        int objectSize = 0;
        if (mapObj != null) {

            objectSize = mapObj.size();
        }

        try (final RandomAccessFile raf = new RandomAccessFile(file, "r");
                final FileChannel fc = raf.getChannel()) {

            if (fc.size() % objectSize > 0) {

                throw new IllegalArgumentException(
                        "File doesn't contain the given type of objects "
                                + mapObj.getClass().getName());
            }

            int listSize = (int) (fc.size() / objectSize);

            if (listSize == 0) {

                return list;
            }
            list = new ArrayList<T>(listSize);

            final BufferSizeCalculator bufSize = new BufferSizeCalculator(
                    (fc.size() / objectSize), maxBufSize);

            long start = 0;
            MappedByteBuffer mem = fc.map(FileChannel.MapMode.READ_ONLY,
                    start,
                    objectSize * bufSize.nextBufSize());

            final byte[] arr = new byte[objectSize];
            long position = 0;
            while (position < fc.size()) {

                if (!mem.hasRemaining()) {

                    start += mem.position();
                    mem = fc.map(FileChannel.MapMode.READ_ONLY, position,
                            objectSize * bufSize.nextBufSize());
                }

                mem.get(arr);
                list.add((T) mapObj.fromByteArray(arr));

                position = position + objectSize;
            }
        }

        return list;
    }

    /**
     * Read Objects from the memory mapped file and apply the given visitor on
     * them.
     *
     * @throws IOException
     *             on any IO error.
     */
    @SuppressWarnings("unchecked")
    public void read(final MappableVisitor<T> visitor)
            throws IOException {

        visitor.onStart();

        int objectSize = 0;
        if (mapObj != null) {

            objectSize = mapObj.size();
        }

        try (final RandomAccessFile raf = new RandomAccessFile(file, "r");
                final FileChannel fc = raf.getChannel()) {

            if (fc.size() % objectSize > 0) {

                throw new IllegalArgumentException(
                        "File doesn't contain the given type of objects "
                                + mapObj.getClass().getName());
            }

            final BufferSizeCalculator bufSize = new BufferSizeCalculator(
                    fc.size() / objectSize, maxBufSize);

            long start = 0;
            MappedByteBuffer mem = fc.map(FileChannel.MapMode.READ_ONLY,
                    start,
                    objectSize * bufSize.nextBufSize());

            final byte[] arr = new byte[objectSize];
            long position = 0;
            while (position < fc.size()) {

                if (!mem.hasRemaining()) {

                    start += mem.position();
                    mem = fc.map(FileChannel.MapMode.READ_ONLY, position,
                            objectSize * bufSize.nextBufSize());
                }

                mem.get(arr);
                visitor.onObject((T) mapObj.fromByteArray(arr));

                position = position + objectSize;
            }
        }

        visitor.onFinish();
    }

    /**
     * Read Objects from the memory mapped file and apply the given visitor on
     * them.
     *
     * @throws IOException
     *             on any IO error.
     */
    @SuppressWarnings("unchecked")
    public void read(final MappableVisitor<T> visitor, final int maxSize)
            throws IOException {

        visitor.onStart();

        int objectSize = 0;
        if (mapObj != null) {

            objectSize = mapObj.size();
        }

        try (final RandomAccessFile raf = new RandomAccessFile(file, "r");
                final FileChannel fc = raf.getChannel()) {

            if (fc.size() % objectSize > 0) {

                throw new IllegalArgumentException(
                        "File doesn't contain the given type of objects "
                                + mapObj.getClass().getName());
            }

            long objectsInFile = fc.size() / objectSize;

            final BufferSizeCalculator bufSize = new BufferSizeCalculator(
                    maxSize <= objectsInFile ? maxSize : objectsInFile,
                    maxBufSize);

            long start = 0;
            MappedByteBuffer mem = fc.map(FileChannel.MapMode.READ_ONLY,
                    start,
                    objectSize * bufSize.nextBufSize());

            final byte[] arr = new byte[objectSize];
            long position = 0;

            int objCounter = 0;
            while (position < fc.size() && objCounter < maxSize) {

                if (!mem.hasRemaining()) {

                    start += mem.position();
                    mem = fc.map(FileChannel.MapMode.READ_ONLY, position,
                            objectSize * bufSize.nextBufSize());
                }

                mem.get(arr);
                visitor.onObject((T) mapObj.fromByteArray(arr));

                position = position + objectSize;

                objCounter++;
            }
        }

        visitor.onFinish();
    }
}
