package com.glueball.snoop.parser;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
/**
 * Exception thrown where teh mime-type of the document is not supported.
 *
 * @author karesz
 */
public class UnsupportedMimeTypeException extends Exception {

    /**
     * Serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     *
     * @param e
     *            Message.
     */
    public UnsupportedMimeTypeException(final String e) {

        super(e);
    }
}
