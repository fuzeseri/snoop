package com.glueball.snoop.dao.setter;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

public class DeleteDocumentBatchPstmtSetter implements
        BatchPreparedStatementSetter {

    final List<String> docIds;

    public DeleteDocumentBatchPstmtSetter(final List<String> _docIds) {

        this.docIds = _docIds;
    }

    @Override
    public void setValues(final PreparedStatement pstmt, int i)
            throws SQLException {

        pstmt.setString(1, this.docIds.get(i));
    }

    @Override
    public int getBatchSize() {

        return this.docIds.size();
    }
}
