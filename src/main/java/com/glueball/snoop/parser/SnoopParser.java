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
import org.xml.sax.SAXException;

import com.glueball.snoop.entity.Meta;

/**
 * Service to parse mate data and text content from a file.
 *
 * @author karesz
 */
public interface SnoopParser {

    /**
     * Parse content from a file.
     *
     * @param uri
     *            the uri path of the file.
     * @param out
     *            Writer object to write the file text content to it.
     * @return Meta object filled with the parsed meta data.
     * @throws IOException
     *             on unsuccessful file read.
     * @throws SAXException
     *             on unsuccessful sax operation.
     * @throws TikaException
     *             on unsuccessful content parse operation (e.g. unsopported
     *             font type)
     */
    Meta parseContent(final String uri, final Writer out) throws IOException,
            SAXException, TikaException;

    /**
     * Parse content from a file.
     *
     * @param path
     *            the path of the file.
     * @param out
     *            Writer object to write the file text content to it.
     * @return Meta object filled with the parsed meta data.
     * @throws IOException
     *             on unsuccessful file read.
     * @throws SAXException
     *             on unsuccessful sax operation.
     * @throws TikaException
     *             on unsuccessful content parse operation (e.g. unsopported
     *             font type)
     */
    Meta parseContent(final File path, final Writer out) throws IOException,
            SAXException,
            TikaException;

    /**
     * Parse content from a file.
     *
     * @param is
     *            InputStream with the parsable content.
     * @param out
     *            Writer object to write the file text content to it.
     * @return Meta object filled with the parsed meta data.
     * @throws IOException
     *             on unsuccessful file read.
     * @throws SAXException
     *             on unsuccessful sax operation.
     * @throws TikaException
     *             on unsuccessful content parse operation (e.g. unsopported
     *             font type)
     */
    Meta parseContent(final InputStream is, final Writer out)
            throws IOException,
            SAXException, TikaException;
}
