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

import com.glueball.snoop.entity.IndexedDocument;

public class IndexedDocumentBatchUpdateSetter implements BatchPreparedStatementSetter {

    final List<IndexedDocument> docs;

    public IndexedDocumentBatchUpdateSetter(final List<IndexedDocument> docs) {

        this.docs = docs;
    }

    @Override
    public void setValues(final PreparedStatement pstmt, int i) throws SQLException {

        pstmt.setTimestamp(1, docs.get(i).getLastIndexedTime());
        pstmt.setString(2, docs.get(i).getIndexState());
        pstmt.setString(3, docs.get(i).getId());
    }

    @Override
    public int getBatchSize() {

        return docs.size();
    }
}
