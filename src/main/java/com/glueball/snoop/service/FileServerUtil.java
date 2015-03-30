package com.glueball.snoop.service;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

final class FileServerUtil {

    public static String getContent(String filename) throws IOException {

        final Resource res = new ClassPathResource(filename);

        try (final InputStream is = res.getInputStream()) {

            final StringBuilder sb = new StringBuilder();
            int c;

            while ((c = is.read()) != -1) {

                sb.append((char) c);
            }

            return sb.toString();
        }
    }
}
