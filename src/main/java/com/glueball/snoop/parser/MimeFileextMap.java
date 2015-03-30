package com.glueball.snoop.parser;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class MimeFileextMap {

    private static final Logger LOG = LogManager
            .getLogger(MimeFileextMap.class);

    private final Map<String, String[]> mimeExtMap = new HashMap<String, String[]>();

    public MimeFileextMap(final Map<String, String[]> _mimeExtMap) {

        this.mimeExtMap.putAll(_mimeExtMap);
    }

    public boolean hasMime(final String key) {

        return this.mimeExtMap.containsKey(key);
    }

    public String[] getExtensions(final String key)
            throws UnsupportedMimeTypeException {

        if (this.mimeExtMap.containsKey(key)) {
            return this.mimeExtMap.get(key);
        }
        throw new UnsupportedMimeTypeException(key);
    }

    public boolean checkFile(final String mimeType, final String fileName) {

        boolean check = false;
        if (hasMime(mimeType)) {
            for (final String ext : this.mimeExtMap.get(mimeType)) {
                if (fileName.toLowerCase().endsWith(ext.toLowerCase())) {
                    check = true;
                    break;
                }
            }
        }

        if (!check) {
            LOG.debug("File has no parsable mime-type: " + fileName
                    + " mime-type: " + mimeType);
        }

        return check;
    }
}
