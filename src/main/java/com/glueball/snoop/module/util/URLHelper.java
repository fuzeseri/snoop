package com.glueball.snoop.module.util;

import com.google.gwt.user.client.Window;

public final class URLHelper {

	public static final String GET_CLIENT    = getURL("client/getById/");
	public static final String SAVE_CLIENT   = getURL("client/save/");
	public static final String MODIFY_CLIENT = getURL("client/modify/");

	public static String getURL(String path) {
		return Window.Location.getProtocol()+"//"+Window.Location.getHost()+"/"+path;
	}

	public static String getImageUrl(String image) {
		return Window.Location.getProtocol()+"//"+Window.Location.getHost()+"/page/image/"+image;
	}

}
