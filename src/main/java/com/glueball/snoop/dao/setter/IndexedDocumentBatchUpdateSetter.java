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
 * BatchPreparedStatementSetter implementation to update index status of a set
 * of IndexedDocuments in the database.
 *
 * @author karesz
 */
public final class IndexedDocumentBatchUpdateSetter implements
        BatchPreparedStatementSetter {

    /**
     * The list of IndexedDocuments to update in the database.
     */
    private final List<IndexedDocument> docs;

    /**
     * Constructor.
     *
     * @param pDocs
     *            The list of IndexedDocuments to update in the database.
     */
    public IndexedDocumentBatchUpdateSetter(
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
        pstmt.setTimestamp(index++, docs.get(i).getLastModifiedTime());
        pstmt.setTimestamp(index++, docs.get(i).getLastIndexedTime());
        pstmt.setString(index++, docs.get(i).getIndexState());
        pstmt.setString(index++, docs.get(i).getId());
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
