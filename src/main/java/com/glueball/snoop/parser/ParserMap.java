package com.glueball.snoop.parser;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Maps mime-type of the file content to the parser object of it.
 *
 * @author karesz
 */
public final class ParserMap {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LogManager
            .getLogger(ParserMap.class);

    /**
     * Map to store the mime-types and parser objects.
     */
    private final Map<String, SnoopParser> parserMap;

    /**
     * Constructor.
     *
     * @param pParserMap
     *            the parserMap object to set.
     */
    public ParserMap(final Map<String, SnoopParser> pParserMap) {

        this.parserMap = Collections.unmodifiableMap(pParserMap);
    }

    /**
     * Check if the snoop software has a parser for the given mime-type.
     *
     * @param contentType
     *            the mime-tyep to check.
     * @return true if the snoop software has a parser for the given mime-type.
     */
    public boolean hasParser(final String contentType) {

        boolean hasParser = this.parserMap.containsKey(contentType);

        if (!hasParser) {
            LOG.debug("No parser available for mime-type: " + contentType);
        }

        return hasParser;
    }

    /**
     * Getter method of the SnoopParser.
     *
     * @param contentType
     *            the mime-type.
     * @return the parser object.
     * @throws UnavialableParserException
     *             if the given mime-type is not supported.
     */
    public SnoopParser getParser(final String contentType)
            throws UnavialableParserException {

        if (hasParser(contentType)) {
            return this.parserMap.get(contentType);
        }
        throw new UnavialableParserException();
    }

    /**
     * Method to get the list of supported mime-types.
     * 
     * @return the list of supported mime-types.
     */
    public Set<String> getMimeTypes() {

        return this.parserMap.keySet();
    }
}
