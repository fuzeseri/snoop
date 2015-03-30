package com.glueball.snoop.parser;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.Parser;
import org.springframework.beans.factory.annotation.Required;
import org.xml.sax.SAXException;

import com.glueball.snoop.entity.Meta;

public interface SnoopParser {

    Meta parseContent(String _uri, Writer out) throws IOException,
            SAXException, TikaException;

    Meta parseContent(File path, Writer out) throws IOException, SAXException,
            TikaException;

    Meta parseContent(InputStream is, Writer out) throws IOException,
            SAXException, TikaException;

    @Required
    void setLuceneParser(final Parser parser);
}
