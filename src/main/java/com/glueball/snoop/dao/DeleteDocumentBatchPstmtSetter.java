package com.glueball.snoop.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

public class DeleteDocumentBatchPstmtSetter implements BatchPreparedStatementSetter {
	
	final List<String> docIds;
	
	public DeleteDocumentBatchPstmtSetter(final List<String> _docIds) {
		this.docIds = _docIds;
	}

	public void setValues(final PreparedStatement pstmt, int i) throws SQLException {
		pstmt.setString(1, this.docIds.get(i));
	}

	public int getBatchSize() {
		return this.docIds.size();
	}
}
