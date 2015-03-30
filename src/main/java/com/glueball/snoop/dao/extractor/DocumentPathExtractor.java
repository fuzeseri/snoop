package com.glueball.snoop.dao.extractor;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.glueball.snoop.entity.DocumentPath;

public class DocumentPathExtractor implements ResultSetExtractor<DocumentPath> {

    final DocumentPath doc;

    public DocumentPathExtractor(final DocumentPath _doc) {

        this.doc = _doc;
    }

    @Override
    public DocumentPath extractData(final ResultSet rs) throws SQLException,
            DataAccessException {

        if (rs.next()) {
            doc.setId(rs.getString("id"));
            doc.setShareName(rs.getString("share_name"));
            doc.setFileName(rs.getString("file_name"));
            doc.setUri(rs.getString("uri"));
            doc.setPath(rs.getString("path"));
            doc.setLocalPath(rs.getString("local_path"));
            doc.setLastModifiedTime(rs.getTimestamp("last_modified_time"));
            doc.setContentType(rs.getString("content_type"));
        }

        return doc;
    }
}
