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
public final class FileData implements Mappable {

    private static final int ID_LENGHT = 16;

    private long position = 0L;
    private byte deleted = (byte) 0;
    private byte[] id = new byte[ID_LENGHT];
    private long lmtime = 0L;
    private long litime = 0L;
    private short status = (short) 0;
    private long lock = 0L;
    private long locktime = 0L;

    @Override
    public final FileData fromByteArray(final byte[] data) {

        final FileData fData = new FileData();

        int i = 0;
        fData.setPosition(ByteUtil
                .bytesToLong(Arrays.copyOfRange(data, i, i + Long.BYTES)));
        i = i + Long.BYTES;

        fData.setDeleted(data[i++]);

        fData.setId(Arrays.copyOfRange(data, i, i + ID_LENGHT));
        i = i + ID_LENGHT;

        fData.setLmtime(ByteUtil.bytesToLong(Arrays.copyOfRange(data, i, i
                + Long.BYTES)));
        i = i + Long.BYTES;

        fData.setLitime(ByteUtil.bytesToLong(Arrays.copyOfRange(data, i, i
                + Long.BYTES)));
        i = i + Long.BYTES;

        fData.setStatus(ByteUtil.bytesToShort(Arrays.copyOfRange(data, i, i
                + Short.BYTES)));
        i = i + Short.BYTES;

        fData.setLock(ByteUtil.bytesToLong(Arrays.copyOfRange(data, i, i
                + Long.BYTES)));
        i = i + Long.BYTES;

        fData.setLocktime(ByteUtil.bytesToLong(Arrays.copyOfRange(data, i, i
                + Long.BYTES)));

        return fData;
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
            bos.write(ByteUtil.longToBytes(lmtime));
            bos.write(ByteUtil.longToBytes(litime));
            bos.write(ByteUtil.shortToBytes(status));
            bos.write(ByteUtil.longToBytes(lock));
            bos.write(ByteUtil.longToBytes(locktime));

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
     * @return the lmtime
     */
    public final long getLmtime() {

        return lmtime;
    }

    /**
     * @param lmtime
     *            the lmtime to set
     */
    public final void setLmtime(long lmtime) {

        this.lmtime = lmtime;
    }

    /**
     * @return the litime
     */
    public final long getLitime() {

        return litime;
    }

    /**
     * @param litime
     *            the litime to set
     */
    public final void setLitime(long litime) {

        this.litime = litime;
    }

    /**
     * @return the status
     */
    public final short getStatus() {

        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public final void setStatus(short status) {

        this.status = status;
    }

    /**
     * @return the lock
     */
    public final long getLock() {

        return lock;
    }

    /**
     * @param lock
     *            the lock to set
     */
    public final void setLock(long lock) {

        this.lock = lock;
    }

    /**
     * @return the locktime
     */
    public final long getLocktime() {

        return locktime;
    }

    /**
     * @param locktime
     *            the locktime to set
     */
    public final void setLocktime(long locktime) {

        this.locktime = locktime;
    }

    @Override
    public final int size() {

        return 5 * Long.BYTES + ID_LENGHT + Byte.BYTES + Short.BYTES;
    }
}
