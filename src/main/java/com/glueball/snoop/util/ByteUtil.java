package com.glueball.snoop.util;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Utility class to convert java types to byte arrays and back.
 *
 * @author karesz
 */
public final class ByteUtil {

    /**
     * Converts a long value to a byte array.
     *
     * @param pLong
     *            the long value to convert.
     * @return the byte array representation of the given value.
     */
    public static byte[] longToBytes(final long pLong) {

        final ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(0, pLong);

        return buffer.array();
    }

    /**
     * Converts a byte array to long.
     *
     * @param bytes
     *            the byte array to convert.
     * @return the long representation of the given value.
     */
    public static long bytesToLong(final byte[] bytes) {

        final ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();

        return buffer.getLong();
    }

    /**
     * Converts an int value to a byte array.
     *
     * @param pInt
     *            the int value to convert.
     * @return the byte array representation of the given value.
     */
    public static byte[] intToBytes(final int pInt) {

        final ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(0, pInt);

        return buffer.array();
    }

    /**
     * Converts a byte array to int.
     *
     * @param bytes
     *            the byte array to convert.
     * @return the int representation of the given value.
     */
    public static int bytesToInt(final byte[] bytes) {

        final ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();

        return buffer.getInt();
    }

    /**
     * Converts a byte array to a short value.
     *
     * @param bytes
     *            the byte array to short.
     * @return the short representation of the given value.
     */
    public static short bytesToShort(final byte[] bytes) {

        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getShort();
    }

    /**
     * Convert a short value to a byte array.
     *
     * @param value
     *            the short value to convert.
     * @return the byte array representation of the given value.
     */
    public static byte[] shortToBytes(final short value) {

        return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(
                value).array();
    }

    /**
     * Converts a String to byte array and cut it to fixed length slices.
     *
     * @param str
     *            the String to convert.
     * @param sliceSize
     *            the maximal size of a slice.
     * @return the array of the slices.
     */
    public static byte[][] stringToByteArrays(final String str,
            final int sliceSize) {

        final byte[] strToByte = str.getBytes(Charset.forName("UTF-8"));
        int sliceCount = (strToByte.length / sliceSize)
                + (strToByte.length % sliceSize == 0 ? 0 : 1);

        final byte[][] slices = new byte[sliceCount][];

        for (int i = 0; i < sliceCount; i++) {

            slices[i] = Arrays.copyOfRange(strToByte, i * sliceSize, i
                    * sliceSize + sliceSize);
        }
        return slices;
    }
}
