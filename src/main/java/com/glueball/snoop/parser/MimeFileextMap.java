package com.glueball.snoop.parser;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Object to map mime-types and the matching file extensions.
 *
 * @author karesz
 */
public final class MimeFileextMap {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LogManager
            .getLogger(MimeFileextMap.class);

    /**
     * Map to store the mime-types and the matching file extensions.
     */
    private final Map<String, String[]> mimeExtMap =
            new HashMap<String, String[]>();

    /**
     * Constructor.
     *
     * @param pMmimeExtMap
     *            the map containing the mime-types and the matching file
     *            extensions. This parameter is designed to create in spring
     *            application xml.
     */
    public MimeFileextMap(final Map<String, String[]> pMmimeExtMap) {

        this.mimeExtMap.putAll(pMmimeExtMap);
    }

    /**
     * Check if the given mime-type is supported.
     *
     * @param mimeType
     *            the mime-tyep to check.
     * @return true if mime-type is supported.
     */
    public boolean hasMime(final String mimeType) {

        return this.mimeExtMap.containsKey(mimeType);
    }

    /**
     * @param mimeType
     *            the mime-type.
     * @return Array of file extensions related to the mime-type.
     * @throws UnsupportedMimeTypeException
     *             if the mime-type is not supported.
     */
    public String[] getExtensions(final String mimeType)
            throws UnsupportedMimeTypeException {

        if (hasMime(mimeType)) {

            return this.mimeExtMap.get(mimeType);
        }
        throw new UnsupportedMimeTypeException(mimeType);
    }

    /**
     * Check the file if the extension of it is valid for the mime-type of its
     * content.
     *
     * @param mimeType
     *            The mime-type of the file content.
     * @param fileName
     *            the name of the file.
     * @return true if the extension of it is valid for the mime-type of its
     *         content.
     */
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
