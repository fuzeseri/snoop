package com.glueball.snoop.module.util;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "keyvalue")
public final class KeyValue implements Serializable {

    private static final long serialVersionUID = -4728213431741843295L;
    private String key;
    private String value;

    public KeyValue() {

    }

    public KeyValue(final String key, final String value) {

        this.key = key;
        this.value = value;
    }

    public String getKey() {

        return key;
    }

    public void setKey(String key) {

        this.key = key;
    }

    public String getValue() {

        return value;
    }

    public void setValue(String value) {

        this.value = value;
    }

}
