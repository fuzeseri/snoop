package com.glueball.snoop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.glueball.snoop.entity.IndexedDocument;

public class IndexedDocumentBean implements IndexedDocumentDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(final JdbcTemplate _jdbcTemplate) {
		this.jdbcTemplate = _jdbcTemplate;
	}

	public void insertOne(final IndexedDocument doc) throws DataAccessException {

		final String query = "INSERT INTO INDEXED_DOCUMENT (id,md5_sum,file_name,uri,path,last_modified_time,last_indexed_time,content_type) VALUES (?,?,?,?,?,?,?,?)";

		this.jdbcTemplate.execute(
				new PreparedStatementCreator() {

					public PreparedStatement createPreparedStatement(final Connection conn) throws SQLException {

						final PreparedStatement pstmt = conn.prepareStatement(query);
						pstmt.setString(1, doc.getId());
						pstmt.setString(2, doc.getMd5Sum());
						pstmt.setString(3, doc.getFileName());
						pstmt.setString(4, doc.getUri());
						pstmt.setString(5, doc.getPath());
						pstmt.setTimestamp(6, doc.getLastModifiedTime());
						pstmt.setString(8, doc.getContentType());
						pstmt.executeUpdate();

						return pstmt;
					}
				}, new PreparedStatementCallback() {

					public Object doInPreparedStatement(final PreparedStatement pstmt) throws SQLException, DataAccessException {
						return null;
					}
				});
	}

	public void insertList(final List<IndexedDocument> docs) throws DataAccessException {

		final String query = "INSERT INTO INDEXED_DOCUMENT (id,md5_sum,file_name,uri,path,last_modified_time,last_indexed_time,content_type) VALUES (?,?,?,?,?,?,?,?)";

		this.jdbcTemplate.batchUpdate(query, new IndexedDocumentBatchPstmtSetter(docs));
	}

	public IndexedDocument findById(final String Id) throws DataAccessException {

		final String query = "SELECT id,md5_sum,file_name,uri,path,last_modified_time,last_indexed_time,content_type FROM INDEXED_DOCUMENT WHERE id = ?";
		final IndexedDocument doc = new IndexedDocument();

		this.jdbcTemplate.query(
				new PreparedStatementCreator() {

					public PreparedStatement createPreparedStatement(final Connection conn) throws SQLException {

						final PreparedStatement pstmt = conn.prepareStatement(query);
						pstmt.setString(1, Id);

						return pstmt;
					}
				}, new IndexedDocumentExtractor(doc));

		return doc;
	}

	public IndexedDocument findBySum(final String md5sum) throws DataAccessException {
		final String query = "SELECT id,md5_sum,file_name,uri,path,last_modified_time,last_indexed_time,content_type FROM INDEXED_DOCUMENT WHERE md5_sum = ?";
		final IndexedDocument doc = new IndexedDocument();

		this.jdbcTemplate.query(
				new PreparedStatementCreator() {

					public PreparedStatement createPreparedStatement(final Connection conn) throws SQLException {

						final PreparedStatement pstmt = conn.prepareStatement(query);
						pstmt.setString(1, md5sum);

						return pstmt;
					}
				}, new IndexedDocumentExtractor(doc));

		return doc;
	}

	public Set<IndexedDocument> fullTextSearch(final String search) throws DataAccessException {

		return null;
	}

	public void createTable() throws DataAccessException {
		this.jdbcTemplate.execute(IndexedDocument.getCreateTable());
	}


	public long rowNum() {

		return (Long) this.jdbcTemplate.query("SELECT COUNT(*) row_num FROM INDEXED_DOCUMENT", new ResultSetExtractor(){

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				long rowNum = 0;
				if (rs.next()) {
					rowNum = rs.getLong(1);
				}
				return rowNum;
			}});
	}

	public void deleteData(final String id) throws DataAccessException {
		this.jdbcTemplate.execute("DELETE FROM INDEXED_DOCUMENT WHERE id = '" + id + "'");
	}
}
