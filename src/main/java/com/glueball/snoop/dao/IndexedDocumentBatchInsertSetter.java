package com.glueball.snoop.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.glueball.snoop.entity.IndexedDocument;

public class IndexedDocumentBatchInsertSetter implements BatchPreparedStatementSetter {
	
	final List<IndexedDocument> docs;
	
	public IndexedDocumentBatchInsertSetter(final List<IndexedDocument> docs) {
		this.docs = docs;
	}

	public void setValues(final PreparedStatement pstmt, int i) throws SQLException {
		pstmt.setString(1, docs.get(i).getId());
		pstmt.setString(2, docs.get(i).getMd5Sum());
		pstmt.setString(3, docs.get(i).getFileName());
		pstmt.setString(4, docs.get(i).getUri());
		pstmt.setString(5, docs.get(i).getPath());
		pstmt.setTimestamp(6, docs.get(i).getLastModifiedTime());
		pstmt.setTimestamp(7, docs.get(i).getLastIndexedTime());
		pstmt.setString(8, docs.get(i).getContentType());
		pstmt.setString(9, docs.get(i).getIndexState());
		pstmt.setInt(10, docs.get(i).getLock());
		pstmt.setTimestamp(11, docs.get(i).getLockTime());
	}

	public int getBatchSize() {
		return docs.size();
	}
}