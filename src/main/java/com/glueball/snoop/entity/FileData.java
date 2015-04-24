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
 * Mappable object to store file data.
 * 
 * @author karesz
 */
public final class FileData implements Mappable {

    /**
     * The length of the ids.
     */
    private static final int ID_LENGHT = 16;

    /**
     * The starting position of this object in the memory mapped file.
     */
    private long position = 0L;

    /**
     * Flag to mark the object as deleted.
     */
    private byte deleted = (byte) 0;

    /**
     * The id of the represented file.
     */
    private byte[] id = new byte[ID_LENGHT];

    /**
     * The last modification time of the file.
     */
    private long lmtime = 0L;

    /**
     * The time when the file was indexed last time.
     */
    private long litime = 0L;

    /**
     * The index status of the file.
     */
    private short status = (short) 0;

    /**
     * Lock id.
     */
    private long lock = 0L;

    /**
     * The time when the file was locked.
     */
    private long locktime = 0L;

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.mmap.Mappable#fromByteArray(byte[])
     */
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
            bos.write(ByteUtil.longToBytes(lmtime));
            bos.write(ByteUtil.longToBytes(litime));
            bos.write(ByteUtil.shortToBytes(status));
            bos.write(ByteUtil.longToBytes(lock));
            bos.write(ByteUtil.longToBytes(locktime));

            return bos.toByteArray();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.mmap.Mappable#getPosition()
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

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.mmap.Mappable#size()
     */
    @Override
    public final int size() {

        return 5 * Long.BYTES + ID_LENGHT + Byte.BYTES + Short.BYTES;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + deleted;
        result = prime * result + Arrays.hashCode(id);
        result = prime * result + (int) (litime ^ (litime >>> 32));
        result = prime * result + (int) (lmtime ^ (lmtime >>> 32));
        result = prime * result + (int) (lock ^ (lock >>> 32));
        result = prime * result + (int) (locktime ^ (locktime >>> 32));
        result = prime * result + (int) (position ^ (position >>> 32));
        result = prime * result + status;
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FileData other = (FileData) obj;
        if (deleted != other.deleted)
            return false;
        if (!Arrays.equals(id, other.id))
            return false;
        if (litime != other.litime)
            return false;
        if (lmtime != other.lmtime)
            return false;
        if (lock != other.lock)
            return false;
        if (locktime != other.locktime)
            return false;
        if (position != other.position)
            return false;
        if (status != other.status)
            return false;
        return true;
    }

}
