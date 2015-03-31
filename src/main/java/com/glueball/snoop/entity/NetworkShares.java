package com.glueball.snoop.entity;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Object to store the list of network shares.
 *
 * @author karesz
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(
        name = "shares",
        namespace = "http://snoop.glueball.com/share")
public final class NetworkShares implements Serializable {

    /**
     * Serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * List of network shares.
     */
    @XmlElement(name = "share",
            namespace = "http://snoop.glueball.com/share")
    private List<NetworkShare> shares = new ArrayList<NetworkShare>();

    /**
     * @return the shares
     */
    public final List<NetworkShare> getShares() {

        return shares;
    }

    /**
     * @param pShares
     *            the shares to set
     */
    public final void setShares(List<NetworkShare> pShares) {

        this.shares = pShares;
    }
}
