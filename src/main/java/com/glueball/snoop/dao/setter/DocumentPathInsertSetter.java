package com.glueball.snoop.dao.setter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementSetter;

import com.glueball.snoop.entity.DocumentPath;

public class DocumentPathInsertSetter implements PreparedStatementSetter {

	final DocumentPath doc;

	public DocumentPathInsertSetter(final DocumentPath _doc) {
		this.doc = _doc;
	}

	@Override
	public void setValues(final PreparedStatement pstmt) throws SQLException {
		pstmt.setString(1, doc.getId());
		pstmt.setString(2, doc.getShareName());
		pstmt.setString(3, doc.getFileName());
		pstmt.setString(4, doc.getUri());
		pstmt.setString(5, doc.getPath());
		pstmt.setString(6, doc.getLocalPath());
		pstmt.setTimestamp(7, doc.getLastModifiedTime());
		pstmt.setString(8, doc.getContentType());
	}
}