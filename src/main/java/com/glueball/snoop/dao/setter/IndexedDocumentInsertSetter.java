package com.glueball.snoop.dao.setter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementSetter;

import com.glueball.snoop.entity.IndexedDocument;

public class IndexedDocumentInsertSetter implements PreparedStatementSetter {

    final IndexedDocument doc;

    public IndexedDocumentInsertSetter(final IndexedDocument _doc) {

	this.doc = _doc;
    }

    @Override
    public void setValues(PreparedStatement pstmt) throws SQLException {

	pstmt.setString(1, doc.getId());
	pstmt.setString(2, doc.getShareName());
	pstmt.setString(3, doc.getFileName());
	pstmt.setString(4, doc.getUri());
	pstmt.setString(5, doc.getPath());
	pstmt.setString(6, doc.getLocalPath());
	pstmt.setTimestamp(6, doc.getLastModifiedTime());
	pstmt.setTimestamp(7, doc.getLastIndexedTime());
	pstmt.setString(8, doc.getContentType());
	pstmt.setString(9, doc.getIndexState());
	pstmt.setInt(10, doc.getLock());
	pstmt.setTimestamp(11, doc.getLockTime());
    }
}