package com.glueball.snoop.entity;

import java.sql.Timestamp;

public interface SnoopDocument {

	public String getId();

	public void setId(String id);

	public String getMd5Sum();

	public void setMd5Sum(String md5Sum);

	public String getFileName();

	public void setFileName(String fileName);

	public String getUri();

	public void setUri(String uri);

	public String getPath();

	public void setPath(String path);

	public Timestamp getLastModifiedTime();

	public void setLastModifiedTime(Timestamp lastModifiedTime);

	public String getContentType();

	public void setContentType(String contentType);

	public Timestamp getLastIndexedTime();

	public void setLastIndexedTime(Timestamp lastIndexedTime);

}
