package com.glueball.snoop.dao.extractor;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.glueball.snoop.entity.DocumentPath;

/**
 * ResultseExtractor implementation to extract data to a DocumentPath object
 * from database query result.
 *
 * @author karesz
 */
public final class DocumentPathExtractor
        implements ResultSetExtractor<DocumentPath> {

    /**
     * The DocumentPath object to extract data into it.
     */
    private DocumentPath doc;

    /**
     * Constructor.
     *
     * @param pDoc
     *            The DocumentPath object to extract data into it.
     */
    public DocumentPathExtractor(final DocumentPath pDoc) {

        this.doc = pDoc;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql
     * .ResultSet)
     */
    @Override
    public DocumentPath extractData(final ResultSet rs) throws SQLException {

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
