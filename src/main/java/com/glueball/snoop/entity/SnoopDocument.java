package com.glueball.snoop.entity;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.sql.Timestamp;

/**
 * Abstract entity class to represent the snoop document.
 *
 * @author karesz
 */
public abstract class SnoopDocument {

    /**
     * Primary key to identify the document.
     */
    private String id;

    /**
     * The name of the network share where file replaced.
     */
    private String shareName;

    /**
     * The name of the file.
     */
    private String fileName;

    /**
     * The path of the file as an uri.
     */
    private String uri;

    /**
     * The path of the file.
     */
    private String path;

    /**
     * The local path of the file.
     */
    private String localPath;

    /**
     * The timestamp when the file was modified last time.
     */
    private Timestamp lastModifiedTime;

    /**
     * The timestamp when the file was indexed last time.
     */
    private Timestamp lastIndexedTime;

    /**
     * The mime type of the file content.
     */
    private String contentType;

    /**
     * @return the id
     */
    public final String getId() {

        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public final void setId(final String id) {

        this.id = id;
    }

    /**
     * @return the shareName
     */
    public final String getShareName() {

        return shareName;
    }

    /**
     * @param shareName
     *            the shareName to set
     */
    public final void setShareName(final String shareName) {

        this.shareName = shareName;
    }

    /**
     * @return the fileName
     */
    public final String getFileName() {

        return fileName;
    }

    /**
     * @param fileName
     *            the fileName to set
     */
    public final void setFileName(final String fileName) {

        this.fileName = fileName;
    }

    /**
     * @return the uri
     */
    public final String getUri() {

        return uri;
    }

    /**
     * @param uri
     *            the uri to set
     */
    public final void setUri(final String uri) {

        this.uri = uri;
    }

    /**
     * @return the path
     */
    public final String getPath() {

        return path;
    }

    /**
     * @param path
     *            the path to set
     */
    public final void setPath(final String path) {

        this.path = path;
    }

    /**
     * @return the localPath
     */
    public final String getLocalPath() {

        return localPath;
    }

    /**
     * @param localPath
     *            the localPath to set
     */
    public final void setLocalPath(final String localPath) {

        this.localPath = localPath;
    }

    /**
     * @return the lastModifiedTime
     */
    public final Timestamp getLastModifiedTime() {

        return lastModifiedTime;
    }

    /**
     * @param lastModifiedTime
     *            the lastModifiedTime to set
     */
    public final void setLastModifiedTime(final Timestamp lastModifiedTime) {

        this.lastModifiedTime = lastModifiedTime;
    }

    /**
     * @return the lastIndexedTime
     */
    public final Timestamp getLastIndexedTime() {

        return lastIndexedTime;
    }

    /**
     * @param lastIndexedTime
     *            the lastIndexedTime to set
     */
    public final void setLastIndexedTime(final Timestamp lastIndexedTime) {

        this.lastIndexedTime = lastIndexedTime;
    }

    /**
     * @return the contentType
     */
    public final String getContentType() {

        return contentType;
    }

    /**
     * @param contentType
     *            the contentType to set
     */
    public final void setContentType(final String contentType) {

        this.contentType = contentType;
    }
}
