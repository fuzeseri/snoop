package com.glueball.snoop.entity;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "shares", namespace = "http://snoop.glueball.com/share")
public class NetworkShares implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "share", namespace = "http://snoop.glueball.com/share")
    private List<NetworkShare> shares = new ArrayList<NetworkShare>();

    public List<NetworkShare> getShares() {

        return shares;
    }

    public void setShares(List<NetworkShare> shares) {

        this.shares = shares;
    }
}
