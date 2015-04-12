/**
 * 
 */
package com.glueball.snoop.entity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import com.glueball.snoop.mmap.Mappable;
import com.glueball.snoop.util.ByteUtil;

/**
 * @author karesz
 */
public final class FilePath implements Mappable {

    private static final int ID_LENGHT = 16;
    private static final int PATH_LENGHT = 80;

    private long position = 0L;
    private byte deleted = (byte) 0;
    private byte[] id = new byte[ID_LENGHT];
    private int order = 0;
    private byte[] path = new byte[PATH_LENGHT];

    /**
     * @param data
     * @return
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

    /**
     * @return
     * @throws IOException
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

    @Override
    public final int size() {

        return Long.BYTES + ID_LENGHT + PATH_LENGHT + Byte.BYTES
                + Integer.BYTES;
    }

}
