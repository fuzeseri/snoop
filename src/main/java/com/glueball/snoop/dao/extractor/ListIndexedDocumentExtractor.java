package com.glueball.snoop.dao.extractor;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.glueball.snoop.entity.IndexedDocument;
import com.glueball.snoop.util.MD5;

/**
 * ResultseExtractor implementation to extract data to List of IndexedDocument
 * objects from database query result.
 *
 * @author karesz
 */
public final class ListIndexedDocumentExtractor implements
        ResultSetExtractor<List<IndexedDocument>> {

    /**
     * The List of IndexedDocument objects to extract the data to it.
     */
    private final List<IndexedDocument> docList;

    /**
     * Constructor.
     *
     * @param pDocList
     *            The List of IndexedDocument objects to extract the data to it.
     */
    public ListIndexedDocumentExtractor(final List<IndexedDocument> pDocList) {

        this.docList = pDocList;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql
     * .ResultSet)
     */
    @Override
    public List<IndexedDocument> extractData(final ResultSet rs)
            throws SQLException, DataAccessException {

        while (rs.next()) {

            final IndexedDocument doc = new IndexedDocument();
            doc.setId(MD5.hexStringToByteArray(rs.getString("id")));
            doc.setShareName(rs.getString("share_name"));
            doc.setFileName(rs.getString("file_name"));
            doc.setUri(rs.getString("uri"));
            doc.setPath(rs.getString("path"));
            doc.setLocalPath(rs.getString("local_path"));
            doc.setLastModifiedTime(rs.getLong("last_modified_time"));
            doc.setLastIndexedTime(rs.getLong("last_indexed_time"));
            doc.setContentType(rs.getString("content_type"));
            doc.setIndexState(rs.getString("index_state"));
            doc.setLock(rs.getInt("lock"));
            doc.setLockTime(rs.getLong("lock_time"));
            docList.add(doc);
        }
        return docList;
    }
}
