package com.glueball.snoop.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.glueball.snoop.entity.IndexedDocument;

public class ListIndexedDocumentExtractor implements
	ResultSetExtractor<List<IndexedDocument>> {

    final List<IndexedDocument> docList;

    public ListIndexedDocumentExtractor(final List<IndexedDocument> _docList) {
	this.docList = _docList;
    }

    public List<IndexedDocument> extractData(final ResultSet rs)
	    throws SQLException, DataAccessException {

	while (rs.next()) {

	    final IndexedDocument doc = new IndexedDocument();
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
	    doc.setLockTime(rs.getTimestamp("lock_time"));
	    docList.add(doc);
	}
	return docList;
    }

}
