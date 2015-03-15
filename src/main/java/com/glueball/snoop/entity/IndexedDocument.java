package com.glueball.snoop.entity;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


public class IndexedDocument extends AbstractSnoopDocument {

	public static String INDEX_STATE_NEW      = "NEW";
	public static String INDEX_STATE_MODIFIED = "MODIFIED";
	public static String INDEX_STATE_DELETED  = "DELETED";
	public static String INDEX_STATE_INDEXED  = "INDEXED";
	public static String INDEX_STATE_REINDEX  = "REINDEX";
	public static String INDEX_STATE_ERROR    = "ERROR";

	private String    indexState = "";
	private Integer   lock = 0;
	private Timestamp lockTime = null;

	public static String getCreateTable() {

		return   "CREATE TABLE IF NOT EXISTS INDEXED_DOCUMENT ("
			   + "id varchar(100),"
			   + "share_name varchar(1024),"
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

	public static List<String> getCreateIndex() {

		return Arrays.asList(new String[] {
			"CREATE INDEX IF NOT EXISTS ndxIDocId ON INDEXED_DOCUMENT (id)",
			"CREATE INDEX IF NOT EXISTS ndxIDocShareName ON INDEXED_DOCUMENT (share_name)",
			"CREATE INDEX IF NOT EXISTS ndxIDocFileName ON INDEXED_DOCUMENT (file_name)",
			"CREATE INDEX IF NOT EXISTS ndxIDocUri ON INDEXED_DOCUMENT (uri)",
			"CREATE INDEX IF NOT EXISTS ndxIDocPath ON INDEXED_DOCUMENT (path)",
			"CREATE INDEX IF NOT EXISTS ndxIDocLocalPath ON INDEXED_DOCUMENT (local_path)",
			"CREATE INDEX IF NOT EXISTS ndxIDocLastModifiedTime ON INDEXED_DOCUMENT (last_modified_time)",
			"CREATE INDEX IF NOT EXISTS ndxIDocContentType ON INDEXED_DOCUMENT (content_type)",
			"CREATE INDEX IF NOT EXISTS ndxIDocIndexState ON INDEXED_DOCUMENT (index_state)",
			"CREATE INDEX IF NOT EXISTS ndxIDocLock ON INDEXED_DOCUMENT (lock)",
			"CREATE INDEX IF NOT EXISTS ndxIDocLockTime ON INDEXED_DOCUMENT (lock_time)"
			}
		);
	}

	public String toString() {

		return " id : " + getId() +  
				" ,fileName : " + getFileName() +
				" ,shareName : " + getShareName() +
				" ,uri : " + getUri() + 
				" ,path : " + getPath() +
				" ,localPath : " + getLocalPath() +
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