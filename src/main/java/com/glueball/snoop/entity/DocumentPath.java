package com.glueball.snoop.entity;

import java.util.Arrays;
import java.util.List;



public class DocumentPath extends IndexedDocument {

	public static String getCreateTable() {
		return   "CREATE TABLE IF NOT EXISTS DOCUMENT_PATH ("
			   + "id varchar(100),"
			   + "share_name varchar(1024),"
			   + "file_name varchar(1024),"
			   + "uri varchar(1024),"
			   + "path varchar(1024),"
			   + "local_path varchar(1024),"
			   + "last_modified_time timestamp,"
			   + "content_type varchar(100)"
			   + ")";
	}

	public static List<String> getCreateIndex() {

		return Arrays.asList(new String[] {
			"CREATE INDEX IF NOT EXISTS ndxDocId ON DOCUMENT_PATH (id)",
			"CREATE INDEX IF NOT EXISTS ndxDocShareName ON DOCUMENT_PATH (share_name)",
			"CREATE INDEX IF NOT EXISTS ndxDocFileName ON DOCUMENT_PATH (file_name)",
			"CREATE INDEX IF NOT EXISTS ndxDocUri ON DOCUMENT_PATH (uri)",
			"CREATE INDEX IF NOT EXISTS ndxDocPath ON DOCUMENT_PATH (path)",
			"CREATE INDEX IF NOT EXISTS ndxDocLocalPath ON DOCUMENT_PATH (local_path)",
			"CREATE INDEX IF NOT EXISTS ndxDocLastModifiedTime ON DOCUMENT_PATH (last_modified_time)",
			"CREATE INDEX IF NOT EXISTS ndxDocContentType ON DOCUMENT_PATH (content_type)"
			}
		);
	}

	@Override
	public String toString() {
		return " id : " + getId() + 
				" ,shareName : " + getShareName() + 
				" ,fileName : " + getFileName() + 
				" ,uri : " + getUri() + 
				" ,path : " + getPath() +
				" ,localPath : " + getLocalPath() +
				" ,lastModifiedTime : " + getLastModifiedTime() + 
				" ,contentType : " + getContentType() ;
	}
}
