package com.glueball.snoop.parser;

import java.util.HashMap;
import java.util.Map;

public final class MimeFileextMap {

	private final Map<String, String[]> mimeExtMap = new HashMap<String, String[]>();
	
	public MimeFileextMap(final Map<String, String[]> _mimeExtMap) {
		this.mimeExtMap.putAll(_mimeExtMap);
	}

	public boolean hasMime(final String key) {
		return this.mimeExtMap.containsKey(key);
	}

	public String[] getExtensions(final String key) throws UnsupportedMimeTypeException {
		if (this.mimeExtMap.containsKey(key)) {
			return this.mimeExtMap.get(key);
		}
		throw new UnsupportedMimeTypeException(key);
	}

	public boolean checkFile(final String mimeType, final String fileName) {
		boolean check = false;
		if (hasMime(mimeType)) {
			for (final String ext : this.mimeExtMap.get(mimeType)) {
				if (fileName.toLowerCase().endsWith(ext.toLowerCase())) {
					check = true;
					break;
				}
			}
		}
		return check;
	}
}
