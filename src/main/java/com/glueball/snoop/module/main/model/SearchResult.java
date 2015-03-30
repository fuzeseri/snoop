package com.glueball.snoop.module.main.model;

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
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "result")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResult implements Serializable {

    private static final long serialVersionUID = 1L;
    private String title;
    private String author;
    private String description;
    private String fileName;
    private String uri;
    private String contentType;

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getAuthor() {

        return author;
    }

    public void setAuthor(String author) {

        this.author = author;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String desription) {

        this.description = desription;
    }

    public String getFileName() {

        return fileName;
    }

    public void setFileName(String fileName) {

        this.fileName = fileName;
    }

    public String getUri() {

        return uri;
    }

    public void setUri(String uri) {

        this.uri = uri;
    }

    public String getContentType() {

        return contentType;
    }

    public void setContentType(String contentType) {

        this.contentType = contentType;
    }

    @Override
    public String toString() {

        return "SearchResult [title=" + title + ", author=" + author
                + ", description=" + description + ", fileName=" + fileName
                + ", uri=" + uri + ", contentType=" + contentType + "]";
    }
}
