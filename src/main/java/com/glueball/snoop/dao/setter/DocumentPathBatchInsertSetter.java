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

import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.util.MD5;

/**
 * BatchPreparedStatementSetter implementation used in batch insert statements
 * to insert a set of DocumentPaths to the relational database.
 *
 * @author karesz
 */
public final class DocumentPathBatchInsertSetter implements
        BatchPreparedStatementSetter {

    /**
     * List of DocumentPaths to insert to the database.
     */
    private final List<DocumentPath> docs;

    /**
     * Constructor.
     *
     * @param pDocs
     *            The list of DocumentPaths to insert to the database.
     */
    public DocumentPathBatchInsertSetter(final List<DocumentPath> pDocs) {

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
        pstmt.setString(index++, MD5.toHexString(docs.get(i).getId()));
        pstmt.setString(index++, docs.get(i).getShareName());
        pstmt.setString(index++, docs.get(i).getFileName());
        pstmt.setString(index++, docs.get(i).getUri());
        pstmt.setString(index++, docs.get(i).getPath());
        pstmt.setString(index++, docs.get(i).getLocalPath());
        pstmt.setLong(index++, docs.get(i).getLastModifiedTime());
        pstmt.setString(index++, docs.get(i).getContentType());
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
