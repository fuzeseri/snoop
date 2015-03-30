package com.glueball.snoop.entity;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "share", namespace = "http://snoop.glueball.com/share")
public class NetworkShare implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(namespace = "http://snoop.glueball.com/share")
    private String name;

    @XmlElement(namespace = "http://snoop.glueball.com/share")
    private String localPath;

    @XmlElement(namespace = "http://snoop.glueball.com/share")
    private String remotePath;

    @XmlElement(namespace = "http://snoop.glueball.com/share")
    private boolean aktiv = true;

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getLocalPath() {

        return localPath;
    }

    public void setLocalPath(String localPath) {

        this.localPath = localPath;
    }

    public String getRemotePath() {

        return remotePath;
    }

    public void setRemotePath(String remotePath) {

        this.remotePath = remotePath;
    }

    public boolean isAktiv() {

        return aktiv;
    }

    public void setAktiv(boolean aktiv) {

        this.aktiv = aktiv;
    }
}
