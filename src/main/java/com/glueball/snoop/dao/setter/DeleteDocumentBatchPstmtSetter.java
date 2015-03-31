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

/**
 * BatchPreparedStatementSetter implementation to set document ids in batch
 * delete statements.
 *
 * @author karesz
 */
public final class DeleteDocumentBatchPstmtSetter implements
        BatchPreparedStatementSetter {

    /**
     * List of document ids to delete.
     */
    final List<String> docIds;

    /**
     * Constructor.
     *
     * @param pDocIds
     *            The list of document ids to delete.
     */
    public DeleteDocumentBatchPstmtSetter(final List<String> pDocIds) {

        this.docIds = pDocIds;
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

        pstmt.setString(1, this.docIds.get(i));
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.jdbc.core.BatchPreparedStatementSetter#getBatchSize()
     */
    @Override
    public int getBatchSize() {

        return this.docIds.size();
    }
}
