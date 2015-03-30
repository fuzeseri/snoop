package com.glueball.snoop.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    public static final String md5Digest(final String original) throws NoSuchAlgorithmException {

        final MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(original.getBytes(Charset.defaultCharset()));

        final byte[] mdbytes = md.digest();

        final StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {

            String hex = Integer.toHexString(0xff & mdbytes[i]);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
