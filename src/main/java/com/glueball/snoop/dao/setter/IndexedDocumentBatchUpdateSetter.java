package com.glueball.snoop.dao.setter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.glueball.snoop.entity.IndexedDocument;

public class IndexedDocumentBatchUpdateSetter implements
	BatchPreparedStatementSetter {

    final List<IndexedDocument> docs;

    public IndexedDocumentBatchUpdateSetter(final List<IndexedDocument> docs) {

	this.docs = docs;
    }

    public void setValues(final PreparedStatement pstmt, int i)
	    throws SQLException {

	pstmt.setTimestamp(1, docs.get(i).getLastIndexedTime());
	pstmt.setString(2, docs.get(i).getIndexState());
	pstmt.setString(3, docs.get(i).getId());
    }

    public int getBatchSize() {

	return docs.size();
    }
}
