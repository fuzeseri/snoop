package com.glueball.snoop.module.main.model;

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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Entity object representing a single search result.
 * 
 * @author karesz
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "result")
@JsonIgnoreProperties(ignoreUnknown = true)
public final class SearchResult implements Serializable {

    /**
     * Serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The title of the docuemnt.
     */
    private String title;

    /**
     * The author of the document.
     */
    private String author;

    /**
     * The description of the docuemnt.
     */
    private String description;

    /**
     * The name of the file.
     */
    private String fileName;

    /**
     * The path of the file.
     */
    private String uri;

    /**
     * The mime-type of the file content.
     */
    private String contentType;

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "SearchResult [title=" + title + ", author=" + author
                + ", description=" + description + ", fileName=" + fileName
                + ", uri=" + uri + ", contentType=" + contentType + "]";
    }

    /**
     * @return the title
     */
    public String getTitle() {

        return title;
    }

    /**
     * @param pTitle
     *            the title to set
     */
    public void setTitle(final String pTitle) {

        this.title = pTitle;
    }

    /**
     * @return the author
     */
    public String getAuthor() {

        return author;
    }

    /**
     * @param pAuthor
     *            the author to set
     */
    public void setAuthor(final String pAuthor) {

        this.author = pAuthor;
    }

    /**
     * @return the description
     */
    public String getDescription() {

        return description;
    }

    /**
     * @param pDescription
     *            the description to set
     */
    public void setDescription(final String pDescription) {

        this.description = pDescription;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {

        return fileName;
    }

    /**
     * @param pFileName
     *            the fileName to set
     */
    public void setFileName(final String pFileName) {

        this.fileName = pFileName;
    }

    /**
     * @return the uri
     */
    public String getUri() {

        return uri;
    }

    /**
     * @param pUri
     *            the uri to set
     */
    public void setUri(final String pUri) {

        this.uri = pUri;
    }

    /**
     * @return the contentType
     */
    public String getContentType() {

        return contentType;
    }

    /**
     * @param pContentType
     *            the contentType to set
     */
    public void setContentType(final String pContentType) {

        this.contentType = pContentType;
    }
}
