package com.glueball.snoop.dao.setter;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementSetter;

import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.util.MD5;

/**
 * PreparedStatementSetter implementation ot insert a single DocumentPath to the
 * database.
 *
 * @author karesz
 */
public final class DocumentPathInsertSetter
        implements PreparedStatementSetter {

    /**
     * The DocumentPath object to insert to the database.
     */
    private final DocumentPath doc;

    /**
     * Constructor.
     *
     * @param pDoc
     *            The DocumentPath object to insert to the database.
     */
    public DocumentPathInsertSetter(final DocumentPath pDoc) {

        this.doc = pDoc;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.jdbc.core.PreparedStatementSetter#setValues(java.
     * sql.PreparedStatement)
     */
    @Override
    public void setValues(final PreparedStatement pstmt) throws SQLException {

        int index = 1;
        pstmt.setString(index++, MD5.toHexString(doc.getId()));
        pstmt.setString(index++, doc.getShareName());
        pstmt.setString(index++, doc.getFileName());
        pstmt.setString(index++, doc.getUri());
        pstmt.setString(index++, doc.getPath());
        pstmt.setString(index++, doc.getLocalPath());
        pstmt.setLong(index++, doc.getLastModifiedTime());
        pstmt.setString(index++, doc.getContentType());
    }
}
