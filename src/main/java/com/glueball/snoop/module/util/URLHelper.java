package com.glueball.snoop.module.util;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import com.google.gwt.user.client.Window;

/**
 * Utility class to help provide the server services url.
 *
 * @author karesz
 */
public final class URLHelper {

    /**
     * Hide constructor.
     */
    private URLHelper() {

    }

    /**
     * rest Url part of the search service.
     */
    public static final String SEARCH = getURL("search/");

    /**
     * Provides a service request url.
     *
     * @param path
     *            the path of the service.
     * @return the url.
     */
    public static String getURL(final String path) {

        return Window.Location.getProtocol() + "//" + Window.Location.getHost()
                + "/" + path;
    }

    /**
     * Provides an image service request url.
     *
     * @param image
     *            the name of teh image file.
     * @return the url.
     */
    public static String getImageUrl(final String image) {

        return Window.Location.getProtocol() + "//" + Window.Location.getHost()
                + "/page/image/" + image;
    }

}
