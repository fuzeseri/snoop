package com.glueball.snoop.entity;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import com.glueball.snoop.mmap.Mappable;
import com.glueball.snoop.util.ByteUtil;

/**
 * Mappable object to store the path of a file.
 * 
 * @author karesz
 */
public final class FilePath implements Mappable {

    /**
     * The length of the id.
     */
    private static final int ID_LENGHT = 16;

    /**
     * The maximum length of path in bytes.
     */
    private static final int PATH_LENGHT = 80;

    /**
     * The start position of the object in the memory mapped file.
     */
    private long position = 0L;

    /**
     * Flag to mark this object as deleted.
     */
    private byte deleted = (byte) 0;

    /**
     * The file id.
     */
    private byte[] id = new byte[ID_LENGHT];

    /**
     * The order of this slice of the path.
     */
    private int order = 0;

    /**
     * Byte array to store the path.
     */
    private byte[] path = new byte[PATH_LENGHT];

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.mmap.Mappable#fromByteArray(byte[])
     */
    @Override
    public FilePath fromByteArray(final byte[] data) {

        final FilePath fp = new FilePath();

        int i = 0;
        fp.setPosition(ByteUtil
                .bytesToLong(Arrays.copyOfRange(data, i, i + Long.BYTES)));
        i = i + Long.BYTES;

        fp.setDeleted(data[i++]);

        fp.setId(Arrays.copyOfRange(data, i, i + ID_LENGHT));
        i = i + ID_LENGHT;

        fp.setOrder(ByteUtil
                .bytesToInt(Arrays.copyOfRange(data, i, i + Integer.BYTES)));
        i = i + Integer.BYTES;

        fp.setPath(Arrays.copyOfRange(data, i, i + PATH_LENGHT));
        i = i + PATH_LENGHT;

        return fp;
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.mmap.Mappable#toByteArray()
     */
    @Override
    public final byte[] toByteArray() throws IOException {

        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            bos.write(ByteUtil.longToBytes(position));
            bos.write(deleted);
            bos.write(id);
            bos.write(ByteUtil.intToBytes(order));
            bos.write(path);

            return bos.toByteArray();
        }
    }

    /**
     * @return the position
     */
    @Override
    public final long getPosition() {

        return position;
    }

    /**
     * @param position
     *            the position to set
     */
    @Override
    public final void setPosition(long position) {

        this.position = position;
    }

    /**
     * @return the deleted
     */
    public final byte getDeleted() {

        return deleted;
    }

    /**
     * @param deleted
     *            the deleted to set
     */
    public final void setDeleted(byte deleted) {

        this.deleted = deleted;
    }

    /**
     * @return the id
     */
    public final byte[] getId() {

        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public final void setId(byte[] id) {

        this.id = id;
    }

    /**
     * @return the order
     */
    public final int getOrder() {

        return order;
    }

    /**
     * @param order
     *            the order to set
     */
    public final void setOrder(int order) {

        this.order = order;
    }

    /**
     * @return the path
     */
    public final byte[] getPath() {

        return path;
    }

    /**
     * @param path
     *            the path to set
     */
    public final void setPath(byte[] path) {

        this.path = path;
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.mmap.Mappable#size()
     */
    @Override
    public final int size() {

        return Long.BYTES + ID_LENGHT + PATH_LENGHT + Byte.BYTES
                + Integer.BYTES;
    }

}
