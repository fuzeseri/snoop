package com.glueball.snoop.entity;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.sql.Timestamp;

public abstract class AbstractSnoopDocument implements SnoopDocument {

    protected String id;
    protected String shareName;
    protected String fileName;
    protected String uri;
    protected String path;
    protected String localPath;
    protected Timestamp lastModifiedTime;
    protected Timestamp lastIndexedTime;
    protected String contentType;

    @Override
    public String getId() {

        return id;
    }

    @Override
    public void setId(String id) {

        this.id = id;
    }

    @Override
    public String getFileName() {

        return fileName;
    }

    @Override
    public void setFileName(String fileName) {

        this.fileName = fileName;
    }

    @Override
    public String getUri() {

        return uri;
    }

    @Override
    public void setUri(String uri) {

        this.uri = uri;
    }

    @Override
    public String getPath() {

        return path;
    }

    @Override
    public void setPath(String path) {

        this.path = path;
    }

    @Override
    public String getLocalPath() {

        return localPath;
    }

    @Override
    public void setLocalPath(String localPath) {

        this.localPath = localPath;
    }

    @Override
    public Timestamp getLastModifiedTime() {

        return lastModifiedTime;
    }

    @Override
    public void setLastModifiedTime(Timestamp lastModifiedTime) {

        this.lastModifiedTime = lastModifiedTime;
    }

    @Override
    public String getContentType() {

        return contentType;
    }

    @Override
    public void setContentType(String contentType) {

        this.contentType = contentType;
    }

    @Override
    public Timestamp getLastIndexedTime() {

        return lastIndexedTime;
    }

    @Override
    public void setLastIndexedTime(Timestamp lastIndexedTime) {

        this.lastIndexedTime = lastIndexedTime;
    }

    @Override
    public String getShareName() {

        return shareName;
    }

    @Override
    public void setShareName(String shareName) {

        this.shareName = shareName;
    }
}
