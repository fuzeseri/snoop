package com.glueball.snoop.util;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    public static final String md5Digest(final String original)
            throws NoSuchAlgorithmException {

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
