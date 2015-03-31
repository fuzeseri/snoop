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
public class IndexedDocumentBatchInsertSetter implements
        BatchPreparedStatementSetter {

    /**
     * The list of IndexedDocuments to insert to the database.
     */
    final List<IndexedDocument> docs;

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
    public void setValues(final PreparedStatement pstmt, int i)
            throws SQLException {

        pstmt.setString(1, docs.get(i).getId());
        pstmt.setString(2, docs.get(i).getShareName());
        pstmt.setString(3, docs.get(i).getFileName());
        pstmt.setString(4, docs.get(i).getUri());
        pstmt.setString(5, docs.get(i).getPath());
        pstmt.setString(6, docs.get(i).getLocalPath());
        pstmt.setTimestamp(7, docs.get(i).getLastModifiedTime());
        pstmt.setTimestamp(8, docs.get(i).getLastIndexedTime());
        pstmt.setString(9, docs.get(i).getContentType());
        pstmt.setString(10, docs.get(i).getIndexState());
        pstmt.setInt(11, docs.get(i).getLock());
        if (docs.get(i).getLockTime() != null)
            pstmt.setTimestamp(12, docs.get(i).getLockTime());
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.jdbc.core.BatchPreparedStatementSetter#getBatchSize()
     */
    @Override
    public int getBatchSize() {

        return docs.size();
    }
}
