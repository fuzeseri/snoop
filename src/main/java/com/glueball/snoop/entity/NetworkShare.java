package com.glueball.snoop.entity;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity class representing a network share.
 *
 * @author karesz
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(
        name = "share",
        namespace = "http://snoop.glueball.com/share")
public final class NetworkShare implements Serializable {

    /**
     * Serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The name of the share.
     */
    @XmlElement(namespace = "http://snoop.glueball.com/share")
    private String name;

    /**
     * The local path of the file system directory.
     */
    @XmlElement(namespace = "http://snoop.glueball.com/share")
    private String localPath;

    /**
     * The remote path of the file system directory.
     */
    @XmlElement(namespace = "http://snoop.glueball.com/share")
    private String remotePath;

    /**
     * Active.
     */
    @XmlElement(namespace = "http://snoop.glueball.com/share")
    private boolean active = true;

    /**
     * Scan delay.
     */
    @XmlElement(namespace = "http://snoop.glueball.com/share")
    private String scanDelay;

    /**
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * @param pName
     *            the name to set
     */
    public void setName(final String pName) {

        this.name = pName;
    }

    /**
     * @return the localPath
     */
    public String getLocalPath() {

        return localPath;
    }

    /**
     * @param pLocalPath
     *            the localPath to set
     */
    public void setLocalPath(final String pLocalPath) {

        this.localPath = pLocalPath;
    }

    /**
     * @return the remotePath
     */
    public String getRemotePath() {

        return remotePath;
    }

    /**
     * @param pRemotePath
     *            the remotePath to set
     */
    public void setRemotePath(final String pRemotePath) {

        this.remotePath = pRemotePath;
    }

    /**
     * @return the active
     */
    public boolean isActive() {

        return active;
    }

    /**
     * @param pActive
     *            the active to set
     */
    public void setActive(final boolean pActive) {

        this.active = pActive;
    }

    /**
     * @return the scanDelay
     */
    public final String getScanDelay() {

        return scanDelay;
    }

    /**
     * @param scanDelay
     *            the scanDelay to set
     */
    public final void setScanDelay(String scanDelay) {

        this.scanDelay = scanDelay;
    }

}
