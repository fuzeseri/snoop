package com.glueball.snoop.entity;

import java.sql.Timestamp;

public class DocumentPath {

	private String id;
	private String md5Sum;
	private String fileName;
	private String uri;
	private String path;
	private Timestamp lastModifiedTime;
	private Timestamp lastIndexedTime;
	private String contentType;
	
	public String getCreateTable() {
		return   "CREATE TABLE IF NOT EXISTS DOCUMENT_PATH ("
			   + "id varchar(50),"
			   + "md5_sum varchar(255),"
			   + "file_name varchar(1024),"
			   + "uri varchar(1024),"
			   + "path varchar(1024),"
			   + "last_modified_time timestamp,"
			   + "last_indexed_time timestamp,"
			   + "content_type vachar(100)"
			   + ")";
	}

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

	public Timestamp getLastIndexedTime() {
		return lastIndexedTime;
	}

	public void setLastIndexedTime(Timestamp lastIndexedTime) {
		this.lastIndexedTime = lastIndexedTime;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
