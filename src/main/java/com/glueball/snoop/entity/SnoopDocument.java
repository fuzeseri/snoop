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
     * @param pId
     *            the id to set
     */
    public final void setId(final String pId) {

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
    public final Timestamp getLastModifiedTime() {

        return lastModifiedTime;
    }

    /**
     * @param pLastModifiedTime
     *            the lastModifiedTime to set
     */
    public final void setLastModifiedTime(final Timestamp pLastModifiedTime) {

        this.lastModifiedTime = pLastModifiedTime;
    }

    /**
     * @return the lastIndexedTime
     */
    public final Timestamp getLastIndexedTime() {

        return lastIndexedTime;
    }

    /**
     * @param pLastIndexedTime
     *            the lastIndexedTime to set
     */
    public final void setLastIndexedTime(final Timestamp pLastIndexedTime) {

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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((contentType == null) ? 0 : contentType.hashCode());
        result = prime * result
                + ((fileName == null) ? 0 : fileName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result
                + ((lastIndexedTime == null) ? 0 : lastIndexedTime.hashCode());
        result = prime
                * result
                + ((lastModifiedTime == null) ? 0 :
                        lastModifiedTime.hashCode());
        result = prime * result
                + ((localPath == null) ? 0 : localPath.hashCode());
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result
                + ((shareName == null) ? 0 : shareName.hashCode());
        result = prime * result + ((uri == null) ? 0 : uri.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final SnoopDocument other = (SnoopDocument) obj;

        if (id == null && other.id != null) {

            return false;
        } else if (!id.equals(other.id)) {

            return false;
        }

        if (fileName == null && other.fileName != null) {

            return false;
        } else if (!fileName.equals(other.fileName)) {

            return false;
        }

        if (localPath == null && other.localPath != null) {

            return false;
        } else if (!localPath.equals(other.localPath)) {

            return false;
        }

        if (path == null && other.path != null) {

            return false;
        } else if (!path.equals(other.path)) {

            return false;
        }

        if (contentType == null && other.contentType != null) {

            return false;
        } else if (!contentType.equals(other.contentType)) {

            return false;
        }

        if (lastIndexedTime == null && other.lastIndexedTime != null) {

            return false;
        } else if (!lastIndexedTime.equals(other.lastIndexedTime)) {

            return false;
        }

        if (lastModifiedTime == null && other.lastModifiedTime != null) {

            return false;
        } else if (!lastModifiedTime.equals(other.lastModifiedTime)) {

            return false;
        }

        if (shareName == null && other.shareName != null) {

            return false;
        } else if (!shareName.equals(other.shareName)) {

            return false;
        }

        if (uri == null && other.uri != null) {

            return false;
        } else if (!uri.equals(other.uri)) {

            return false;
        }

        return true;
    }
}
