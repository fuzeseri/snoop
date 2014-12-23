package com.glueball.snoop.entity;


public class IndexedDocument extends AbstractSnoopDocument {

	private String indexState = "";
	
	public static String getCreateTable() {
		return   "CREATE TABLE IF NOT EXISTS INDEXED_DOCUMENT ("
			   + "id varchar(50),"
			   + "md5_sum varchar(255),"
			   + "file_name varchar(1024),"
			   + "uri varchar(1024),"
			   + "path varchar(1024),"
			   + "last_modified_time timestamp,"
			   + "last_indexed_time timestamp,"
			   + "content_type varchar(100),"
			   + "index_state varchar(10)"
			   + ")";
	}

	public String toString() {
		return " id : " + getId() + 
				" ,md5Sum : " + getMd5Sum() + 
				" ,fileName : " + getFileName() + 
				" ,uri : " + getUri() + 
				" ,path : " + getPath() + 
				" ,lastModifiedTime : " + getLastModifiedTime() +
				" ,lastIndexedTime : " + getLastIndexedTime() +
				" ,contentType : " + getContentType() +
				" ,indexState : " + getIndexState();
	}

	public String getIndexState() {
		return indexState;
	}

	public void setIndexState(String indexState) {
		this.indexState = indexState;
	}

}
