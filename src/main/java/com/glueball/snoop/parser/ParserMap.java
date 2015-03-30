package com.glueball.snoop.parser;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ParserMap {

    private static final Logger LOG = LogManager.getLogger(ParserMap.class);

    private final Map<String, SnoopParser> parserMap = new HashMap<String, SnoopParser>();

    @SuppressWarnings("unused")
    private ParserMap() {

    }

    public ParserMap(final Map<String, SnoopParser> _parserMap) {

        this.parserMap.putAll(_parserMap);
    }

    public boolean hasParser(final String contentType) {

        boolean hasParser = this.parserMap.containsKey(contentType);

        if (!hasParser) {
            LOG.debug("No parser available for mime-type: " + contentType);
        }

        return hasParser;
    }

    public SnoopParser getParser(final String contentType)
            throws UnavialableParserException {

        return this.parserMap.get(contentType);
    }
}
