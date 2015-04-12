package com.glueball.snoop.entity;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Abstract entity class to represent the snoop document.
 *
 * @author karesz
 */
@XmlTransient
public abstract class SnoopDocument implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Primary key to identify the document.
     */
    @XmlElement
    private byte[] id;

    /**
     * The name of the network share where file replaced.
     */
    @XmlElement
    private String shareName;

    /**
     * The name of the file.
     */
    @XmlElement
    private String fileName;

    /**
     * The path of the file as an uri.
     */
    @XmlElement
    private String uri;

    /**
     * The path of the file.
     */
    @XmlElement
    private String path;

    /**
     * The local path of the file.
     */
    @XmlElement
    private String localPath;

    /**
     * The timestamp when the file was modified last time.
     */
    @XmlElement
    private long lastModifiedTime;

    /**
     * The timestamp when the file was indexed last time.
     */
    @XmlElement
    private long lastIndexedTime;

    /**
     * The mime type of the file content.
     */
    @XmlElement
    private String contentType;

    /**
     * @return the id
     */
    public final byte[] getId() {

        return id;
    }

    /**
     * @param pId
     *            the id to set
     */
    public final void setId(final byte[] pId) {

        this.id = pId;
    }

    /**
     * @return the shareName
     */
    public final String getShareName() {

        return shareName;
    }

    /**
     * @param pShareName
     *            the shareName to set
     */
    public final void setShareName(final String pShareName) {

        this.shareName = pShareName;
    }

    /**
     * @return the fileName
     */
    public final String getFileName() {

        return fileName;
    }

    /**
     * @param pFileName
     *            the fileName to set
     */
    public final void setFileName(final String pFileName) {

        this.fileName = pFileName;
    }

    /**
     * @return the uri
     */
    public final String getUri() {

        return uri;
    }

    /**
     * @param pUri
     *            the uri to set
     */
    public final void setUri(final String pUri) {

        this.uri = pUri;
    }

    /**
     * @return the path
     */
    public final String getPath() {

        return path;
    }

    /**
     * @param pPath
     *            the path to set
     */
    public final void setPath(final String pPath) {

        this.path = pPath;
    }

    /**
     * @return the localPath
     */
    public final String getLocalPath() {

        return localPath;
    }

    /**
     * @param pLocalPath
     *            the localPath to set
     */
    public final void setLocalPath(final String pLocalPath) {

        this.localPath = pLocalPath;
    }

    /**
     * @return the lastModifiedTime
     */
    public final long getLastModifiedTime() {

        return lastModifiedTime;
    }

    /**
     * @param pLastModifiedTime
     *            the lastModifiedTime to set
     */
    public final void setLastModifiedTime(final long pLastModifiedTime) {

        this.lastModifiedTime = pLastModifiedTime;
    }

    /**
     * @return the lastIndexedTime
     */
    public final long getLastIndexedTime() {

        return lastIndexedTime;
    }

    /**
     * @param pLastIndexedTime
     *            the lastIndexedTime to set
     */
    public final void setLastIndexedTime(final long pLastIndexedTime) {

        this.lastIndexedTime = pLastIndexedTime;
    }

    /**
     * @return the contentType
     */
    public final String getContentType() {

        return contentType;
    }

    /**
     * @param pContentType
     *            the contentType to set
     */
    public final void setContentType(final String pContentType) {

        this.contentType = pContentType;
    }
}
