package com.glueball.snoop.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

final class FileServerUtil {
	
	public static String getContent(String filename) throws IOException {
		
		final Resource res = new ClassPathResource(filename);
    	
    	final StringBuilder sb = new StringBuilder();
    	final InputStream is = res.getInputStream();

    	int c;
    	while ( (c = is.read()) != -1 ) {
    		sb.append((char)c);
    	}
    	
    	return sb.toString();
    	
	}
	
}
