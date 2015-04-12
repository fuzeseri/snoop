package com.glueball.snoop.entity;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity class. Representation of the document found on the file system.
 * Containing the related relational queries to store and read the entities from
 * the relational database and converter methods to another SnoopDocument
 * implementations.
 *
 * @author karesz
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "path")
public final class DocumentPath extends SnoopDocument {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * SQL query insert an entity to the DOCUMENT_PATH table.
     */
    public static final String INSERT_QUERY =
            "INSERT INTO DOCUMENT_PATH "
                    + " (id,share_name,file_name,uri,path,local_path,"
                    + " last_modified_time,content_type)"
                    + " VALUES (?,?,?,?,?,?,?,?)";

    /**
     * SQL query to select an entity by id from the DOCUMENT_PATH table.
     */
    public static final String SELECT_BY_ID_QUERY =
            "SELECT id,share_name,file_name,uri,path,local_path,"
                    + " last_modified_time,content_type FROM DOCUMENT_PATH"
                    + " WHERE id = ?";

    /**
     * SQL query to select an entity by md5sum from the DOCUMENT_PATH table.
     */
    public static final String SELECT_BY_SUM_QUERY =
            "SELECT id,share_name,file_name,uri,path,local_path,"
                    + " last_modified_time,content_type FROM DOCUMENT_PATH"
                    + " WHERE md5_sum = ?";

    /**
     * SQL query to select the actual row number of the DOCUMENT_PATH table.
     */
    public static final String ROW_NUM_QUERY =
            "SELECT COUNT(id) row_num FROM DOCUMENT_PATH";

    /**
     * SQL query to select all entities from the DOCUMENT_PATH table.
     */
    public static final String SELECT_ALL_QUERY =
            "SELECT id,share_name,file_name,uri,path,local_path,"
                    + "last_modified_time,content_type FROM DOCUMENT_PATH";

    /**
     * SQL query to delete an entity by id from the DOCUMENT_PATH table.
     */
    public static final String DELETE_BY_ID_QUERY =
            "DELETE FROM DOCUMENT_PATH WHERE id = ?";

    /**
     * SQL query to delete all entities from the DOCUMENT_PATH table.
     */
    public static final String EMPTY_TABLE_QUERY =
            "DELETE FROM DOCUMENT_PATH";

    /**
     * SQL query to drop the DOCUMENT_PATH table in the database if it is
     * exists.
     */
    public static final String DROP_TABLE_QUERY =
            "DROP TABLE IF EXISTS DOCUMENT_PATH";

    /**
     * SQL query to select new documents from the database. It important to
     * refresh the DOCUMENT_PATH tables data from the file system before run
     * this query.
     */
    public static final String SELECT_NEW_DOCUMENTS_QUERY =
            " SELECT "
                    + "   docp.id id, "
                    + "   docp.share_name share_name, "
                    + "   docp.file_name file_name, "
                    + "   docp.uri uri, "
                    + "   docp.path path, "
                    + "   docp.local_path local_path,"
                    + "   docp.last_modified_time last_modified_time, "
                    + "   docp.content_type content_type "
                    + " FROM "
                    + "   DOCUMENT_PATH docp "
                    + "   LEFT JOIN INDEXED_DOCUMENT idoc ON docp.id = idoc.id"
                    + " WHERE "
                    + "   idoc.id IS NULL ";

    /**
     * SQL query to select modified documents from the database. It important
     * refresh the DOCUMENT_PATH tables data from the file system before run
     * this query.
     */
    public static final String SELECT_MODIFIED_DOCUMENTS_QUERY =
            " SELECT "
                    + "   docp.id id, "
                    + "   docp.share_name share_name, "
                    + "   docp.file_name file_name, "
                    + "   docp.uri uri, "
                    + "   docp.path path, "
                    + "   docp.local_path local_path, "
                    + "   docp.last_modified_time last_modified_time, "
                    + "   docp.content_type content_type "
                    + " FROM "
                    + "  DOCUMENT_PATH docp "
                    + "  INNER JOIN INDEXED_DOCUMENT idoc ON docp.id = idoc.id"
                    + " WHERE "
                    + "   docp.last_modified_time > idoc.last_modified_time";

    /**
     * SQL query to update the index status of the documents which was deleted
     * on the file system. It is important to refresh the DOCUMENT_PATH tables
     * data from the file system before run this query.
     */
    public static final String UPDATE_DELETED_DOCUMENTS_QUERY =
            " UPDATE INDEXED_DOCUMENT SET "
                    + "    index_state = ?, lock = 0, lock_time = null "
                    + " WHERE id IN ( "
                    + "  SELECT "
                    + "       idoc.id id "
                    + "  FROM "
                    + "     INDEXED_DOCUMENT idoc"
                    + "     LEFT JOIN DOCUMENT_PATH docp ON idoc.id = docp.id "
                    + "  WHERE "
                    + "    idoc.share_name = ? AND docp.id IS NULL )";

    /**
     * SQL query to create the DOCUMENT_PATH table in the database if it not
     * exists.
     */
    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS DOCUMENT_PATH ("
                    + "id varchar(100),"
                    + "share_name varchar(1024),"
                    + "file_name varchar(1024),"
                    + "uri varchar(1024),"
                    + "path varchar(1024),"
                    + "local_path varchar(1024),"
                    + "last_modified_time timestamp,"
                    + "content_type varchar(100)"
                    + ")";

    /**
     * SQL query to create indexes on the DOCUMENT_PATH table in the database
     * they are not exists.
     */
    public static final List<String> CREATE_INDEX_QUERY = Collections
            .unmodifiableList(Arrays
                    .asList(new String[] {
                            "CREATE INDEX IF NOT EXISTS "
                                    + "ndxDocId "
                                    + "ON DOCUMENT_PATH (id)",

                            "CREATE INDEX IF NOT EXISTS "
                                    + "ndxDocShareName "
                                    + "ON DOCUMENT_PATH (share_name)",

                            "CREATE INDEX IF NOT EXISTS "
                                    + "ndxDocFileName "
                                    + "ON DOCUMENT_PATH (file_name)",

                            "CREATE INDEX IF NOT EXISTS "
                                    + "ndxDocUri "
                                    + "ON DOCUMENT_PATH (uri)",

                            "CREATE INDEX IF NOT EXISTS "
                                    + "ndxDocPath "
                                    + "ON DOCUMENT_PATH (path)",

                            "CREATE INDEX IF NOT EXISTS "
                                    + "ndxDocLocalPath "
                                    + "ON DOCUMENT_PATH (local_path)",

                            "CREATE INDEX IF NOT EXISTS "
                                    + "ndxDocLastModifiedTime "
                                    + "ON DOCUMENT_PATH (last_modified_time)",

                            "CREATE INDEX IF NOT EXISTS "
                                    + "ndxDocContentType "
                                    + "ON DOCUMENT_PATH (content_type)" }));

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return " id : " + getId() + " ,shareName : " + getShareName()
                + " ,fileName : " + getFileName() + " ,uri : " + getUri()
                + " ,path : " + getPath() + " ,localPath : " + getLocalPath()
                + " ,lastModifiedTime : " + getLastModifiedTime()
                + " ,contentType : " + getContentType();
    }

    /**
     * Method to convert a set of DocumentPath entities to the same set of
     * IndexedDocument entities.
     *
     * @param docList
     *            the list of DocumentPaths to convert.
     * @param indexStateNew
     *            the index status of the returned IndexedDocuments.
     * @return list of IndexedDocuments.
     */
    public static List<IndexedDocument> toIndexedDocumentList(
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
            idoc.setLockTime(0L);
            idoc.setPath(doc.getPath());
            idoc.setShareName(doc.getShareName());
            idoc.setUri(doc.getUri());

            idocList.add(idoc);
        }
        return idocList;
    }
}
