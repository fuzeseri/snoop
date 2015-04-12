/**
 * 
 */
package com.glueball.snoop.mmap;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author karesz
 */
public class MMapReader {

    private final File file;
    private final int maxBufSize;
    private final Mappable mapObj;

    public MMapReader(final File pFile, final int pMaxBufSize,
            final Class<? extends Mappable> pClazz) throws Exception {

        this.file = pFile;
        this.maxBufSize = pMaxBufSize;
        this.mapObj = pClazz.newInstance();
    }

    public List<Mappable> read() throws IOException {

        List<Mappable> list = new ArrayList<Mappable>(0);
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
            list = new ArrayList<Mappable>(listSize);

            final BufferSizeCalculator bufSize = new BufferSizeCalculator(
                    listSize, maxBufSize);

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
                list.add(mapObj.fromByteArray(arr));

                position = position + objectSize;
            }
        }

        return list;
    }
}
