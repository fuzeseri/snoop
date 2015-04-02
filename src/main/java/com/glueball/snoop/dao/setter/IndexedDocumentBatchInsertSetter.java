package com.glueball.snoop.dao.setter;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.glueball.snoop.entity.IndexedDocument;

/**
 * BatchPreparedStatementSetter implementation to insert a set of
 * IndexedDocuments to the database.
 *
 * @author karesz
 */
public final class IndexedDocumentBatchInsertSetter implements
        BatchPreparedStatementSetter {

    /**
     * The list of IndexedDocuments to insert to the database.
     */
    private final List<IndexedDocument> docs;

    /**
     * Constructor.
     *
     * @param pDocs
     *            The list of IndexedDocuments to insert to the database.
     */
    public IndexedDocumentBatchInsertSetter(
            final List<IndexedDocument> pDocs) {

        this.docs = pDocs;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.jdbc.core.BatchPreparedStatementSetter#setValues(
     * java.sql.PreparedStatement, int)
     */
    @Override
    public void setValues(final PreparedStatement pstmt, final int i)
            throws SQLException {

        int index = 1;
        pstmt.setString(index++, docs.get(i).getId());
        pstmt.setString(index++, docs.get(i).getShareName());
        pstmt.setString(index++, docs.get(i).getFileName());
        pstmt.setString(index++, docs.get(i).getUri());
        pstmt.setString(index++, docs.get(i).getPath());
        pstmt.setString(index++, docs.get(i).getLocalPath());
        pstmt.setTimestamp(index++, docs.get(i).getLastModifiedTime());
        pstmt.setTimestamp(index++, docs.get(i).getLastIndexedTime());
        pstmt.setString(index++, docs.get(i).getContentType());
        pstmt.setString(index++, docs.get(i).getIndexState());
        pstmt.setLong(index++, docs.get(i).getLock());

        if (docs.get(i).getLockTime() != null) {
            pstmt.setTimestamp(index++, docs.get(i).getLockTime());
        }
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#
     * getBatchSize()
     */
    @Override
    public int getBatchSize() {

        return docs.size();
    }
}
