package com.glueball.snoop.metadata.extractor;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import org.apache.cxf.common.util.StringUtils;
import org.apache.tika.metadata.Metadata;

import com.glueball.snoop.entity.Meta;

/**
 * A general implememtation of the MetaDataExtractor service.
 *
 * @author karesz
 */
public class GeneralMetaDataExtractor implements MetaDataExtractor {

    /*
     * (non-Javadoc)
     * @see
     * com.glueball.snoop.metadata.extractor.MetaDataExtractor#extractMetaData
     * (org.apache.tika.metadata.Metadata)
     */
    @Override
    public final Meta extractMetaData(final Metadata metadata) {

        String title = "";
        String author = "";
        String description = "";

        for (final String name : metadata.names()) {

            if (StringUtils.isEmpty(title)
                    && name.toLowerCase().contains("title")) {
                title = metadata.get(name);
            }
            if (name.toLowerCase().equals("title")) {
                title = metadata.get(name);
            }
            if (StringUtils.isEmpty(author)
                    && name.toLowerCase().contains("author")) {
                author = metadata.get(name);
            }
            if (name.toLowerCase().equals("author")) {
                author = metadata.get(name);
            }
            if (StringUtils.isEmpty(description)
                    && name.toLowerCase().contains("description")) {
                description = metadata.get(name);
            }
            if (name.toLowerCase().equals("description")) {
                description = metadata.get(name);
            }
        }
        return new Meta(author, title, description);
    }
}
