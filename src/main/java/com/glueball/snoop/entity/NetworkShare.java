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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(
        name = "share",
        namespace = "http://snoop.glueball.com/share")
public class NetworkShare implements Serializable {

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
     * @return the name
     */
    public final String getName() {

        return name;
    }

    /**
     * @param pName
     *            the name to set
     */
    public final void setName(final String pName) {

        this.name = pName;
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
     * @return the remotePath
     */
    public final String getRemotePath() {

        return remotePath;
    }

    /**
     * @param pRemotePath
     *            the remotePath to set
     */
    public final void setRemotePath(final String pRemotePath) {

        this.remotePath = pRemotePath;
    }

    /**
     * @return the active
     */
    public final boolean isActive() {

        return active;
    }

    /**
     * @param active
     *            the pActive to set
     */
    public final void setActive(final boolean pActive) {

        this.active = pActive;
    }
}
