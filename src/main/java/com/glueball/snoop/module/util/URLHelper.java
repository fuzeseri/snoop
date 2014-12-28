package com.glueball.snoop.module.util;

import com.google.gwt.user.client.Window;

public final class URLHelper {

	public static final String SEARCH = getURL("search");

	public static String getURL(String path) {
		return Window.Location.getProtocol()+"//"+Window.Location.getHost()+"/"+path;
	}

	public static String getImageUrl(String image) {
		return Window.Location.getProtocol()+"//"+Window.Location.getHost()+"/page/image/"+image;
	}

}
