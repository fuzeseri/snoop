package com.glueball.snoop.entity;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.glueball.snoop.util.MD5;

/**
 * Entity class. Relational database representation of the document stored in
 * the index. Containing the related relational queries to store and read the
 * entities from the relational database and converter methods to another
 * SnoopDocument implementations.
 *
 * @author karesz
 */
public final class IndexedDocument extends SnoopDocument {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Index status of a the new document. Pre-index state.
     */
    public static final String INDEX_STATE_NEW = "NEW";

    /**
     * Index status of a modified document. Pre-index state.
     */
    public static final String INDEX_STATE_MODIFIED = "MODIFIED";

    /**
     * Index status of a deleted document. Pre-delete state.
     */
    public static final String INDEX_STATE_DELETED = "DELETED";

    /**
     * Index status of a successfully indexed document. Post-index state.
     */
    public static final String INDEX_STATE_INDEXED = "INDEXED";

    /**
     * Index status of of a document which manually set to reindex. Pre-index
     * state.
     */
    public static final String INDEX_STATE_REINDEX = "REINDEX";

    /**
     * Index status of a document if the indexing unsuccessful. Post-index
     * state.
     */
    public static final String INDEX_STATE_ERROR = "ERROR";

    /**
     * SQL query to insert an entity to the INDEXED_DOCUMENT table.
     */
    public static final String INSERT_DOCUMENT_QUERY =
            "INSERT INTO INDEXED_DOCUMENT "
                    + "(id,share_name,file_name,uri,path,local_path,"
                    + "last_modified_time,last_indexed_time,content_type,"
                    + "index_state,lock,lock_time)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ";

    /**
     * SQL query to select all entities from the INDEXED_DOCUMENT table.
     */
    public static final String SELECT_ALL_QUERY =
            "SELECT "
                    + " id,share_name,file_name,uri,path,local_path,"
                    + " last_modified_time,last_indexed_time,"
                    + " content_type,index_state,lock,lock_time "
                    + " FROM INDEXED_DOCUMENT ";

    /**
     * SQL query to select an entity by id from the INDEXED_DOCUMENT table.
     */
    public static final String SELECT_BY_ID_QUERY =
            " SELECT "
                    + " id,share_name,file_name,uri,path,local_path,"
                    + " last_modified_time,last_indexed_time, content_type,"
                    + " index_state,lock,lock_time "
                    + " FROM INDEXED_DOCUMENT WHERE id = ?";

    /**
     * SQL query to select entities by shareName from the INDEXED_DOCUMENT
     * table.
     */
    public static final String SELECT_BY_SHARE_NAME_QUERY =
            "SELECT "
                    + " id,share_name,file_name,uri,path,local_path,"
                    + " last_modified_time,last_indexed_time,content_type,"
                    + " index_state,lock,lock_time "
                    + " FROM INDEXED_DOCUMENT WHERE share_name = ?";

    /**
     * SQL query to get the actual number of entities in the INDEXED_DOCUMENT
     * table.
     */
    public static final String SELECT_ROW_NUM_QUERY =
            "SELECT COUNT(id) row_num FROM INDEXED_DOCUMENT";

    /**
     * SQL query to delete an entity by id from the INDEXED_DOCUMENT table.
     */
    public static final String DELETE_BY_ID_QUERY =
            "DELETE FROM INDEXED_DOCUMENT WHERE id = ?";

    /**
     * SQL query to delete all entities from the INDEXED_DOCUMENT table.
     */
    public static final String TRUNCATE_TABLE_QUERY =
            "DELETE FROM INDEXED_DOCUMENT";

    /**
     * SQL query to drop the INDEXED_DOCUMENT table in the database if it is
     * exists.
     */
    public static final String DROP_TABLE_QUERY =
            "DROP TABLE IF EXISTS INDEXED_DOCUMENT";

    /**
     * SQL query to select a limited set of documents to indexing. It is
     * important to use this query in transaction with the LOCK_DOCUMENTS_QUERY
     * query in a multi-threaded environment.
     */
    public static final String GET_INDEXABLE_DOCUMENTS_QUERY =
            "SELECT id FROM INDEXED_DOCUMENT "
                    + " WHERE index_state NOT IN ('"
                    + INDEX_STATE_INDEXED + "','" + INDEX_STATE_ERROR
                    + "') AND lock = 0 LIMIT ?";

    /**
     * SQL query to lock a set of document to indexing. It is important to use
     * this query in a transaction with the GET_INDEXABLE_DOCUMENTS_QUERY query
     * in a multi-threaded environment.
     */
    public static final String LOCK_DOCUMENTS_QUERY =
            "UPDATE INDEXED_DOCUMENT "
                    + " SET lock = ?, "
                    + " lock_time = ? "
                    + " WHERE id = ?";

    /**
     * SQL query to select documents locked with the given lock id.
     */
    public static final String SELECT_BY_LOCK_QUERY =
            "SELECT "
                    + " id,share_name,file_name,uri,path,local_path,"
                    + " last_modified_time,last_indexed_time,"
                    + " content_type,index_state,lock,lock_time "
                    + " FROM INDEXED_DOCUMENT WHERE lock = ?";

    /**
     * SQL query to update the status of the entities in the INDEXED_DOCUMENT
     * table.
     */
    public static final String UPDATE_STATE_QUERY =
            "UPDATE INDEXED_DOCUMENT "
                    + " SET "
                    + " last_modified_time = ?,"
                    + " last_indexed_time = ?,"
                    + "index_state = ? WHERE id = ?";

    /**
     * SQL query to update the status and unlock the entities and in the
     * INDEXED_DOCUMENT table.
     */
    public static final String UNLOCK_UPDATE_STATE_QUERY =
            "UPDATE INDEXED_DOCUMENT "
                    + " SET "
                    + " last_modified_time = ?,"
                    + " last_indexed_time = ?, "
                    + " index_state = ?, "
                    + " lock = 0,"
                    + " lock_time = null "
                    + "WHERE id = ?";

    /**
     * SQL query to create the INDEXED_DOCUMENT table in the database if it not
     * exists.
     */
    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS INDEXED_DOCUMENT ("
                    + "   id varchar(100),"
                    + "   share_name varchar(1024),"
                    + "   file_name varchar(1024),"
                    + "   uri varchar(1024),"
                    + "   path varchar(1024),"
                    + "   locaL_path varchar(1024),"
                    + "   last_modified_time BIGINT,"
                    + "   last_indexed_time BIGINT,"
                    + "   content_type varchar(100),"
                    + "   index_state varchar(10),"
                    + "   lock BIGINT,"
                    + "   lock_time BIGINT"
                    + ")";

    /**
     * SQL query to create indexes on the INDEXED_DOCUMENT table if they aren't
     * exists.
     */
    public static final List<String> CREATE_INDEX_QUERY = Collections
            .unmodifiableList(Arrays
                    .asList(new String[] {
                            "CREATE INDEX IF NOT EXISTS "
                                    + "ndxIDocId ON INDEXED_DOCUMENT (id)",

                            "CREATE INDEX IF NOT EXISTS "
                                    + "ndxIDocShareName ON "
                                    + "INDEXED_DOCUMENT (share_name)",

                            "CREATE INDEX IF NOT EXISTS "
                                    + "ndxIDocFileName ON "
                                    + "INDEXED_DOCUMENT (file_name)",

                            "CREATE INDEX IF NOT EXISTS "
                                    + "ndxIDocUri ON INDEXED_DOCUMENT (uri)",

                            "CREATE INDEX IF NOT EXISTS "
                                    + "ndxIDocPath ON INDEXED_DOCUMENT (path)",

                            "CREATE INDEX IF NOT EXISTS "
                                    + "ndxIDocLocalPath ON "
                                    + "INDEXED_DOCUMENT (local_path)",

                            "CREATE INDEX IF NOT EXISTS "
                                    + "ndxIDocLastModifiedTime ON "
                                    + "INDEXED_DOCUMENT (last_modified_time)",

                            "CREATE INDEX IF NOT EXISTS "
                                    + "ndxIDocContentType ON "
                                    + "INDEXED_DOCUMENT (content_type)",

                            "CREATE INDEX IF NOT EXISTS "
                                    + "ndxIDocIndexState ON "
                                    + "INDEXED_DOCUMENT (index_state)",

                            "CREATE INDEX IF NOT EXISTS "
                                    + "ndxIDocLock ON INDEXED_DOCUMENT (lock)",

                            "CREATE INDEX IF NOT EXISTS "
                                    + "ndxIDocLockTime "
                                    + "ON INDEXED_DOCUMENT (lock_time)" }));

    /**
     * Index status of the document.
     */
    private String indexState = "";

    /**
     * Lock.
     */
    private long lock = 0L;

    /**
     * The exact time when the docuemnt was locked.
     */
    private long lockTime = 0L;

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return " id : " + MD5.toHexString(getId()) + " ,fileName : "
                + getFileName()
                + " ,shareName : " + getShareName() + " ,uri : " + getUri()
                + " ,path : " + getPath() + " ,localPath : " + getLocalPath()
                + " ,lastModifiedTime : " + getLastModifiedTime()
                + " ,lastIndexedTime : " + getLastIndexedTime()
                + " ,contentType : " + getContentType() + " ,indexState : "
                + getIndexState() + " ,lock : " + getLock() + " ,lock_time : "
                + getLockTime();
    }

    /**
     * @return the indexState
     */
    public String getIndexState() {

        return indexState;
    }

    /**
     * @param pIndexState
     *            the pIndexState to set
     */
    public void setIndexState(final String pIndexState) {

        this.indexState = pIndexState;
    }

    /**
     * @return the lock
     */
    public long getLock() {

        return lock;
    }

    /**
     * @param pLock
     *            the lock to set
     */
    public void setLock(final long pLock) {

        this.lock = pLock;
    }

    /**
     * @return the lockTime
     */
    public long getLockTime() {

        return lockTime;
    }

    /**
     * @param pLockTime
     *            the lockTime to set
     */
    public void setLockTime(final long pLockTime) {

        this.lockTime = pLockTime;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((indexState == null) ? 0 : indexState.hashCode());
        result = prime * result + (int) (lock ^ (lock >>> 32));
        result = prime * result + (int) (lockTime ^ (lockTime >>> 32));
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof IndexedDocument)) {
            return false;
        }
        IndexedDocument other = (IndexedDocument) obj;
        if (indexState == null) {
            if (other.indexState != null) {
                return false;
            }
        } else if (!indexState.equals(other.indexState)) {
            return false;
        }
        if (lock != other.lock) {
            return false;
        }
        if (lockTime != other.lockTime) {
            return false;
        }
        return true;
    }

}
