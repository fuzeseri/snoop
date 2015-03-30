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

import com.glueball.snoop.entity.DocumentPath;

public class DocumentPathBatchInsertSetter implements
        BatchPreparedStatementSetter {

    final List<DocumentPath> docs;

    public DocumentPathBatchInsertSetter(final List<DocumentPath> docs) {

        this.docs = docs;
    }

    @Override
    public void setValues(PreparedStatement pstmt, int i) throws SQLException {

        pstmt.setString(1, docs.get(i).getId());
        pstmt.setString(2, docs.get(i).getShareName());
        pstmt.setString(3, docs.get(i).getFileName());
        pstmt.setString(4, docs.get(i).getUri());
        pstmt.setString(5, docs.get(i).getPath());
        pstmt.setString(6, docs.get(i).getLocalPath());
        pstmt.setTimestamp(7, docs.get(i).getLastModifiedTime());
        pstmt.setString(8, docs.get(i).getContentType());
    }

    @Override
    public int getBatchSize() {

        return docs.size();
    }
}
