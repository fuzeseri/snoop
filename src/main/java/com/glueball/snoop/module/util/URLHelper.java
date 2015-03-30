package com.glueball.snoop.module.util;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import com.google.gwt.user.client.Window;

public final class URLHelper {

    public static final String SEARCH = getURL("search/");

    public static String getURL(String path) {

        return Window.Location.getProtocol() + "//" + Window.Location.getHost() + "/" + path;
    }

    public static String getImageUrl(String image) {

        return Window.Location.getProtocol() + "//" + Window.Location.getHost() + "/page/image/" + image;
    }

}
