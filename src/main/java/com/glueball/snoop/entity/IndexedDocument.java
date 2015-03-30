package com.glueball.snoop.entity;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IndexedDocument extends AbstractSnoopDocument {

    public static final String INDEX_STATE_NEW = "NEW";
    public static final String INDEX_STATE_MODIFIED = "MODIFIED";
    public static final String INDEX_STATE_DELETED = "DELETED";
    public static final String INDEX_STATE_INDEXED = "INDEXED";
    public static final String INDEX_STATE_REINDEX = "REINDEX";
    public static final String INDEX_STATE_ERROR = "ERROR";

    public static final String INSERT_DOCUMENT_QUERY = "INSERT INTO INDEXED_DOCUMENT "
            + "(id,share_name,file_name,uri,path,local_path,"
            + "last_modified_time,last_indexed_time,content_type,"
            + "index_state,lock,lock_time)"
            + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ";

    public static final String SELECT_ALL_QUERY = "SELECT "
            + " id,share_name,file_name,uri,path,local_path,last_modified_time,last_indexed_time,"
            + " content_type,index_state,lock,lock_time "
            + " FROM INDEXED_DOCUMENT ";

    public static final String SELECT_BY_ID_QUERY = " SELECT "
            + " id,share_name,file_name,uri,path,local_path,last_modified_time,"
            + " last_indexed_time, content_type,index_state,lock,lock_time,"
            + " lock,lock_time FROM INDEXED_DOCUMENT WHERE id = ?";

    public static final String SELECT_BY_SHARE_NAME_QUERY = "SELECT "
            + " id,share_name,file_name,uri,path,local_path,last_modified_time,"
            + " last_indexed_time,content_type,index_state,lock,lock_time "
            + " FROM INDEXED_DOCUMENT WHERE share_name = ?";

    public static final String SELECT_ROW_NUM_QUERY = "SELECT COUNT(id) row_num FROM INDEXED_DOCUMENT";

    public static final String DELETE_BY_ID_QUERY = "DELETE FROM DOCUMENT_PATH WHERE id = ?";

    public static final String TRUNCATE_TABLE_QUERY = "DELETE FROM INDEXED_DOCUMENT";

    public static final String GET_INDEXABLE_DOCUMENTS_QUERY = "SELECT id FROM INDEXED_DOCUMENT "
            + " WHERE index_state NOT IN ('"
            + INDEX_STATE_INDEXED
            + "','"
            + INDEX_STATE_ERROR + "') AND lock = 0 LIMIT ?";

    public static final String LOCK_DOCUMENTS_QUERY = "UPDATE INDEXED_DOCUMENT "
            + " SET lock = ?, lock_time = now() WHERE id = ?";

    public static final String SELECT_BY_LOCK_QUERY = "SELECT "
            + " id,share_name,file_name,uri,path,local_path,last_modified_time,last_indexed_time,"
            + " content_type,index_state,lock,lock_time "
            + " FROM INDEXED_DOCUMENT WHERE lock = ?";

    public static final String UPDATE_STATE_QUERY = "UPDATE INDEXED_DOCUMENT "
            + " SET last_indexed_time = ?, index_state = ? WHERE id = ?";

    public static final String UNLOCK_UPDATE_STATE_QUERY = "UPDATE INDEXED_DOCUMENT "
            + " SET last_indexed_time = ?, index_state = ?, lock = 0, lock_time = null WHERE id = ?";

    public static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS INDEXED_DOCUMENT ("
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
            + "lock integer," + "lock_time timestamp" + ")";

    public static final List<String> CREATE_INDEX_QUERY = Collections
            .unmodifiableList(Arrays
                    .asList(new String[] {
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
                            "CREATE INDEX IF NOT EXISTS ndxIDocLockTime ON INDEXED_DOCUMENT (lock_time)" }));

    private String indexState = "";
    private Integer lock = 0;
    private Timestamp lockTime = null;

    @Override
    public String toString() {

        return " id : " + getId() + " ,fileName : " + getFileName()
                + " ,shareName : " + getShareName() + " ,uri : " + getUri()
                + " ,path : " + getPath() + " ,localPath : " + getLocalPath()
                + " ,lastModifiedTime : " + getLastModifiedTime()
                + " ,lastIndexedTime : " + getLastIndexedTime()
                + " ,contentType : " + getContentType() + " ,indexState : "
                + getIndexState() + " ,lock : " + getLock() + " ,lock_time : "
                + getLockTime();

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
