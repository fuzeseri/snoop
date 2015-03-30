package com.glueball.snoop.entity;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import org.apache.cxf.common.util.StringUtils;

public class Meta {

    private final String author;
    private final String title;
    private final String description;

    public Meta(final String _author, final String _title,
            final String _description) {

        this.author = _author;
        this.title = _title;
        this.description = _description;
    }

    public String getAuthor() {

        return author;
    }

    public String getTitle() {

        return title;
    }

    public String getDescription() {

        return description;
    }

    public boolean hasContent() {

        return (title.length() + author.length() + description.length()) > 0;
    }

    public boolean hasTitle() {

        return !StringUtils.isEmpty(title);
    }

    public boolean hasAuthor() {

        return !StringUtils.isEmpty(author);
    }

    public boolean hasDescription() {

        return !StringUtils.isEmpty(description);
    }

    @Override
    public String toString() {

        return "author: " + author + " ,title: " + title + " ,description: "
                + description;
    }
}
