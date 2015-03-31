package com.glueball.snoop.entity;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import org.apache.cxf.common.util.StringUtils;

/**
 * Representation of the document metadata.
 *
 * @author karesz
 */
public class Meta {

    /**
     * The author of the document.
     */
    private final String author;

    /**
     * The title of the document.
     */
    private final String title;

    /**
     * The description of the document.
     */
    private final String description;

    /**
     * Constructor.
     *
     * @param pAuthor
     *            The author of the document.
     * @param pTitle
     *            The title of the document.
     * @param pDescription
     *            The description of the document.
     */
    public Meta(final String pAuthor, final String pTitle,
            final String pDescription) {

        this.author = pAuthor;
        this.title = pTitle;
        this.description = pDescription;
    }

    /**
     * @return the author
     */
    public final String getAuthor() {

        return author;
    }

    /**
     * @return the title
     */
    public final String getTitle() {

        return title;
    }

    /**
     * @return the description
     */
    public final String getDescription() {

        return description;
    }

    /**
     * Checks if the document has parsed metadata.
     *
     * @return true if metadata has parsed from the file.
     */
    public final boolean hasContent() {

        return (title.length() + author.length() + description.length()) > 0;
    }

    /**
     * Checks if the document has title.
     *
     * @return true if title has parsed from the file.
     */
    public final boolean hasTitle() {

        return !StringUtils.isEmpty(title);
    }

    /**
     * Checks if the document has author.
     *
     * @return true if author has parsed from the file.
     */
    public final boolean hasAuthor() {

        return !StringUtils.isEmpty(author);
    }

    /**
     * Checks if the document has description.
     *
     * @return true if description has parsed from the file.
     */
    public final boolean hasDescription() {

        return !StringUtils.isEmpty(description);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() {

        return "author: " + author + " ,title: " + title + " ,description: "
                + description;
    }
}
