/**
 * 
 */
package com.glueball.snoop.entity;

import java.util.Arrays;

/**
 * @author karesz
 */
public final class FileId {

    private final byte[] bytes;

    public FileId(final byte[] pId) {

        this.bytes = pId;
    }

    public byte[] getBytes() {

        return bytes;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(bytes);
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
        FileId other = (FileId) obj;
        if (!Arrays.equals(bytes, other.bytes))
            return false;
        return true;
    }
}
