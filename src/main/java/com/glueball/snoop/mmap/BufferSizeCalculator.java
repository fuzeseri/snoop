package com.glueball.snoop.mmap;

/**
 * @author karesz
 */
public class BufferSizeCalculator {

    /**
     * The maximum buffer size.
     */
    private final int maxBufSize;

    /**
     * Number of the chunks which size is max buffer size;
     */
    private final long maxChunks;

    /**
     * The size of the last chunk.
     */
    private final long lastChunk;

    /**
     * Field to store number of the requests.
     */
    private int getCount = 0;

    /**
     * Constructor.
     *
     * @param pListSize
     * @param pMaxBufSize
     */
    public BufferSizeCalculator(final long pListSize, final int pMaxBufSize) {

        this.maxBufSize = pMaxBufSize;

        if (this.maxBufSize <= 0) {

            throw new IllegalArgumentException(
                    "pMaxBufferSize must be bigger than zero");
        }

        if (pListSize < 0) {

            throw new IllegalArgumentException(
                    "pListSize can't be negative");
        }

        if (pListSize < pMaxBufSize) {

            this.maxChunks = 0;
            this.lastChunk = pListSize;
        } else {

            this.maxChunks = pListSize / pMaxBufSize;
            this.lastChunk = pListSize % pMaxBufSize;
        }
    }

    /**
     * @return size of the next byte buffer.
     */
    public long nextBufSize() {

        if (getCount > maxChunks) {

            return 0;
        }

        if (maxChunks - getCount > 0) {

            getCount++;
            return maxBufSize;
        } else {

            getCount++;
            return lastChunk;
        }
    }
}
