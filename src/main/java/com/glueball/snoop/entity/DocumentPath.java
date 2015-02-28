package com.glueball.snoop.entity;



public class DocumentPath extends IndexedDocument {

	public static String getCreateTable() {
		return   "CREATE TABLE IF NOT EXISTS DOCUMENT_PATH ("
			   + "id varchar(50),"
			   + "share_name varchar(1024),"
			   + "file_name varchar(1024),"
			   + "uri varchar(1024),"
			   + "path varchar(1024),"
			   + "local_path varchar(1024),"
			   + "last_modified_time timestamp,"
			   + "content_type varchar(100)"
			   + ")";
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
