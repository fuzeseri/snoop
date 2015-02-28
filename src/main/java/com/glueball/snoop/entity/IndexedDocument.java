package com.glueball.snoop.entity;

import java.sql.Timestamp;


public class IndexedDocument extends AbstractSnoopDocument {

	public static String INDEX_STATE_NEW      = "NEW";
	public static String INDEX_STATE_MODIFIED = "MODIFIED";
	public static String INDEX_STATE_DELETED  = "DELETED";
	public static String INDEX_STATE_INDEXED  = "INDEXED";
	public static String INDEX_STATE_REINDEX  = "REINDEX";

	private String    indexState = "";
	private Integer   lock = 0;
	private Timestamp lockTime = null;

	public static String getCreateTable() {

		return   "CREATE TABLE IF NOT EXISTS INDEXED_DOCUMENT ("
			   + "id varchar(50),"
			   + "md5_sum varchar(255),"
			   + "file_name varchar(1024),"
			   + "uri varchar(1024),"
			   + "path varchar(1024),"
			   + "locaL_path varchar(1024),"
			   + "last_modified_time timestamp,"
			   + "last_indexed_time timestamp,"
			   + "content_type varchar(100),"
			   + "index_state varchar(10),"
			   + "lock integer,"
			   + "lock_time timestamp"
			   + ")";
	}

	public String toString() {

		return " id : " + getId() + 
				" ,md5Sum : " + getMd5Sum() + 
				" ,fileName : " + getFileName() + 
				" ,uri : " + getUri() + 
				" ,path : " + getPath() +
				" ,local_path : " + getLocalPath() +
				" ,lastModifiedTime : " + getLastModifiedTime() +
				" ,lastIndexedTime : " + getLastIndexedTime() +
				" ,contentType : " + getContentType() +
				" ,indexState : " + getIndexState() + 
				" ,lock : " + getLock() +
				" ,lock_time : " + getLockTime(); 

	}

	public String getIndexState() {
		return indexState;
	}

	public void setIndexState(final String _indexState) {
		this.indexState = _indexState;
	}

	public Integer getLock() {
		return lock;
	}

	public void setLock(final Integer _lock) {
		this.lock = _lock;
	}

	public Timestamp getLockTime() {
		return lockTime;
	}

	public void setLockTime(final Timestamp _lockTime) {
		this.lockTime = _lockTime;
	}
}