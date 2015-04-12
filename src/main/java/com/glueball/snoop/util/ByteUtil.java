/**
 * 
 */
package com.glueball.snoop.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * @author karesz
 */
public final class ByteUtil {

    public static byte[] longToBytes(long x) {

        final ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {

        final ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();// need flip
        return buffer.getLong();
    }

    public static byte[] intToBytes(int x) {

        final ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(0, x);
        return buffer.array();
    }

    public static int bytesToInt(byte[] bytes) {

        final ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();// need flip
        return buffer.getInt();
    }

    public static short bytesToShort(byte[] bytes) {

        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getShort();
    }

    public static byte[] shortToBytes(short value) {

        return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(
                value).array();
    }

    public static byte[][] stringToByteArrays(final String str,
            final int sliceSize) {

        final byte[] strToByte = str.getBytes();
        int sliceCount = (strToByte.length / sliceSize)
                + (strToByte.length % sliceSize == 0 ? 0 : 1);

        final byte[][] slices = new byte[sliceCount][];

        for (int i = 0; i < sliceCount; i++) {

            slices[i] = Arrays.copyOfRange(strToByte, i * 80, i * 80 + 80);
        }
        return slices;
    }
}
