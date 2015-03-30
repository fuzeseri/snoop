package com.glueball.snoop.entity;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DocumentPath extends AbstractSnoopDocument {

    public static final String INSERT_QUERY = "INSERT INTO DOCUMENT_PATH (id,share_name,file_name,uri,path,local_path,last_modified_time,content_type) VALUES (?,?,?,?,?,?,?,?)";

    public static final String SELECT_BY_ID_QUERY = "SELECT id,share_name,file_name,uri,path,local_path,last_modified_time,content_type FROM DOCUMENT_PATH WHERE id = ?";

    public static final String SELECT_BY_SUM_QUERY = "SELECT id,share_name,file_name,uri,path,local_path,last_modified_time,content_type FROM DOCUMENT_PATH WHERE md5_sum = ?";

    public static final String ROW_NUM_QUERY = "SELECT COUNT(id) row_num FROM DOCUMENT_PATH";

    public static final String SELECT_ALL_QUERY = "SELECT id,share_name,file_name,uri,path,local_path,last_modified_time,content_type FROM DOCUMENT_PATH";

    public static final String DELETE_BY_ID_QUERY = "DELETE FROM DOCUMENT_PATH WHERE id = ?";

    public static final String EMPTY_TABLE_QUERY = "DELETE FROM DOCUMENT_PATH";

    public static final String SELECT_NEW_DOCUMENTS_QUERY = " SELECT "
            + " docp.id id, " + " docp.share_name share_name, "
            + " docp.file_name file_name, " + " docp.uri uri, "
            + " docp.path path, " + " docp.local_path local_path,"
            + " docp.last_modified_time last_modified_time, "
            + " docp.content_type content_type " + " FROM "
            + " DOCUMENT_PATH docp "
            + " LEFT JOIN INDEXED_DOCUMENT idoc ON docp.id = idoc.id "
            + " WHERE " + " idoc.id IS NULL ";

    public static final String SELECT_MODIFIED_DOCUMENTS_QUERY = " SELECT "
            + " docp.id id, " + " docp.share_name share_name, "
            + " docp.file_name file_name, " + " docp.uri uri, "
            + " docp.path path, " + " docp.local_path local_path, "
            + " docp.last_modified_time last_modified_time, "
            + " docp.content_type content_type " + " FROM "
            + " DOCUMENT_PATH docp "
            + " INNER JOIN INDEXED_DOCUMENT idoc ON docp.id = idoc.id "
            + " WHERE " + " docp.last_modified_time > idoc.last_modified_time";

    public static final String UPDATE_DELETED_DOCUMENTS_QUERY = " UPDATE INDEXED_DOCUMENT SET index_state = ?, lock = 0, lock_time = null "
            + " WHERE id IN ( "
            + " SELECT "
            + " idoc.id id "
            + " FROM "
            + " INDEXED_DOCUMENT idoc"
            + " LEFT JOIN DOCUMENT_PATH docp ON idoc.id = docp.id "
            + " WHERE "
            + " idoc.share_name = ? AND docp.id IS NULL )";

    public static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS DOCUMENT_PATH ("
            + "id varchar(100),"
            + "share_name varchar(1024),"
            + "file_name varchar(1024),"
            + "uri varchar(1024),"
            + "path varchar(1024),"
            + "local_path varchar(1024),"
            + "last_modified_time timestamp,"
            + "content_type varchar(100)"
            + ")";

    public static final List<String> CREATE_INDEX_QUERY = Collections
            .unmodifiableList(Arrays
                    .asList(new String[] {
                            "CREATE INDEX IF NOT EXISTS ndxDocId ON DOCUMENT_PATH (id)",
                            "CREATE INDEX IF NOT EXISTS ndxDocShareName ON DOCUMENT_PATH (share_name)",
                            "CREATE INDEX IF NOT EXISTS ndxDocFileName ON DOCUMENT_PATH (file_name)",
                            "CREATE INDEX IF NOT EXISTS ndxDocUri ON DOCUMENT_PATH (uri)",
                            "CREATE INDEX IF NOT EXISTS ndxDocPath ON DOCUMENT_PATH (path)",
                            "CREATE INDEX IF NOT EXISTS ndxDocLocalPath ON DOCUMENT_PATH (local_path)",
                            "CREATE INDEX IF NOT EXISTS ndxDocLastModifiedTime ON DOCUMENT_PATH (last_modified_time)",
                            "CREATE INDEX IF NOT EXISTS ndxDocContentType ON DOCUMENT_PATH (content_type)" }));

    @Override
    public String toString() {

        return " id : " + getId() + " ,shareName : " + getShareName()
                + " ,fileName : " + getFileName() + " ,uri : " + getUri()
                + " ,path : " + getPath() + " ,localPath : " + getLocalPath()
                + " ,lastModifiedTime : " + getLastModifiedTime()
                + " ,contentType : " + getContentType();
    }

    public static final List<IndexedDocument> toIndexedDocumentList(
            final List<DocumentPath> docList, final String indexStateNew) {

        final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>(
                docList.size());

        for (final DocumentPath doc : docList) {

            final IndexedDocument idoc = new IndexedDocument();
            idoc.setContentType(doc.getContentType());
            idoc.setFileName(doc.getFileName());
            idoc.setId(doc.getId());
            idoc.setIndexState(indexStateNew);
            idoc.setLastIndexedTime(doc.getLastIndexedTime());
            idoc.setLastModifiedTime(doc.getLastModifiedTime());
            idoc.setLocalPath(doc.getLocalPath());
            idoc.setLock(0);
            idoc.setLockTime(null);
            idoc.setPath(doc.getPath());
            idoc.setShareName(doc.getShareName());
            idoc.setUri(doc.getUri());

            idocList.add(idoc);
        }
        return idocList;
    }
}
