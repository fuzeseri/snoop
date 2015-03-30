package com.glueball.snoop.parser;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import org.apache.cxf.common.util.StringUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import com.glueball.snoop.entity.Meta;

public class SnoopParserImpl implements SnoopParser {

    protected Parser parser;

    public SnoopParserImpl(final Parser parser) {

        this.parser = parser;
    }

    protected SnoopParserImpl() {

    }

    @Override
    public Meta parseContent(final String _uri, final Writer output) throws IOException, SAXException, TikaException {

        try (final InputStream is = new FileInputStream(new File(_uri))) {

            return parse(is, output);
        }
    }

    @Override
    public Meta parseContent(final File path, final Writer output) throws IOException, SAXException, TikaException {

        try (final InputStream is = new FileInputStream(path)) {

            return parse(is, output);
        }
    }

    @Override
    public Meta parseContent(final InputStream is, final Writer output) throws IOException, SAXException, TikaException {

        return parse(is, output);
    }

    protected Meta parse(final InputStream input, final Writer output) throws IOException, SAXException, TikaException {

        try {

            final BodyContentHandler content = new BodyContentHandler(output);
            final Metadata metadata = new Metadata();
            final ParseContext context = new ParseContext();

            parser.parse(input, content, metadata, context);

            String title = "";
            String author = "";
            String description = "";

            for (final String name : metadata.names()) {
                if (StringUtils.isEmpty(title) && name.toLowerCase().contains("title")) {
                    title = metadata.get(name);
                }
                if (name.toLowerCase().equals("title")) {
                    title = metadata.get(name);
                }
                if (StringUtils.isEmpty(author) && name.toLowerCase().contains("author")) {
                    author = metadata.get(name);
                }
                if (name.toLowerCase().equals("author")) {
                    author = metadata.get(name);
                }
                if (StringUtils.isEmpty(description) && name.toLowerCase().contains("description")) {
                    description = metadata.get(name);
                }
                if (name.toLowerCase().equals("description")) {
                    description = metadata.get(name);
                }
            }
            return new Meta(author, title, description);

        } catch (final RuntimeException e) {

            throw new TikaException("Can't parse content. " + e.getLocalizedMessage());
        }
    }

    @Override
    public void setLuceneParser(final Parser _parser) {

        this.parser = _parser;
    }

}
