package com.glueball.snoop.entity;

import java.sql.Timestamp;

public abstract class AbstractSnoopDocument implements SnoopDocument {

	protected String id;
	protected String md5Sum;
	protected String fileName;
	protected String uri;
	protected String path;
	protected Timestamp lastModifiedTime;
	protected Timestamp lastIndexedTime;
	protected String contentType;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMd5Sum() {
		return md5Sum;
	}

	public void setMd5Sum(String md5Sum) {
		this.md5Sum = md5Sum;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Timestamp getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Timestamp lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Timestamp getLastIndexedTime() {
		return lastIndexedTime;
	}

	public void setLastIndexedTime(Timestamp lastIndexedTime) {
		this.lastIndexedTime = lastIndexedTime;
	}

}
