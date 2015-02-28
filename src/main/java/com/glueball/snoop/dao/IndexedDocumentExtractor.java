package com.glueball.snoop.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.glueball.snoop.entity.IndexedDocument;

public class IndexedDocumentExtractor implements ResultSetExtractor {

	final IndexedDocument doc;

	public IndexedDocumentExtractor(final IndexedDocument _doc) {
		this.doc = _doc;
	}

	public Object extractData(final ResultSet rs) throws SQLException, DataAccessException {

		if (rs.next()) {
			doc.setId(rs.getString("id"));
			doc.setShareName(rs.getString("share_name"));
			doc.setFileName(rs.getString("file_name"));
			doc.setUri(rs.getString("uri"));
			doc.setPath(rs.getString("path"));
			doc.setLocalPath(rs.getString("local_path"));
			doc.setLastModifiedTime(rs.getTimestamp("last_modified_time"));
			doc.setLastIndexedTime(rs.getTimestamp("last_indexed_time"));
			doc.setContentType(rs.getString("content_type"));
			doc.setIndexState(rs.getString("index_state"));
			doc.setLock(rs.getInt("lock"));
			doc.setLockTime(rs.getTimestamp("lockTime"));
		}

		return rs;
	}

}
