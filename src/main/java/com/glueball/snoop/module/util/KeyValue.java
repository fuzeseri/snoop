package com.glueball.snoop.module.util;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity class to send and receive key/value pairs.
 *
 * @author karesz
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "keyvalue")
public final class KeyValue implements Serializable {

    /**
     * Serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Key.
     */
    private String key;

    /**
     * Value.
     */
    private String value;

    /**
     * Contructor.
     *
     * @param key
     *            the Key.
     * @param value
     *            the Value.
     */
    public KeyValue(final String key, final String value) {

        this.key = key;
        this.value = value;
    }

    /**
     * @return key
     */
    public String getKey() {

        return key;
    }

    /**
     * @param pKey
     *            the key to set
     */
    public void setKey(final String pKey) {

        this.key = pKey;
    }

    /**
     * @return value
     */
    public String getValue() {

        return value;
    }

    /**
     * @param pValue
     *            the value to set
     */
    public void setValue(final String pValue) {

        this.value = pValue;
    }

}
