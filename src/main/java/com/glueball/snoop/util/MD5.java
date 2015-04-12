package com.glueball.snoop.util;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class to create md5 digest.
 *
 * @author karesz
 */
public final class MD5 {

    /**
     * Hide constructor.
     */
    private MD5() {

    }

    /**
     * Hexadecimal value 0xFF.
     */
    private static final int HEXFF = 0xff;

    /**
     * Created an md5 digest.
     *
     * @param original
     *            the original string.
     * @return the md5 digest created.
     * @throws NoSuchAlgorithmException
     *             if the md5 algorithm is not supported.
     */
    public static final String md5DigestHexStr(final String original)
            throws NoSuchAlgorithmException {

        final MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(original.getBytes(Charset.defaultCharset()));

        return toHexString(md.digest());

    }

    /**
     * Created an md5 digest.
     *
     * @param original
     *            the original string.
     * @return the md5 digest created.
     * @throws NoSuchAlgorithmException
     *             if the md5 algorithm is not supported.
     */
    public static final byte[] md5Digest(final String original)
            throws NoSuchAlgorithmException {

        final MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(original.getBytes(Charset.defaultCharset()));

        return md.digest();
    }

    public static final String toHexString(final byte[] mdbytes) {

        final StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {

            final String hex = Integer.toHexString(HEXFF & mdbytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static final byte[] hexStringToByteArray(final String str) {

        int len = str.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4)
                    + Character.digit(str.charAt(i + 1), 16));
        }
        return data;
    }

}
