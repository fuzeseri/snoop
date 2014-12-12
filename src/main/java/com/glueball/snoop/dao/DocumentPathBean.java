package com.glueball.snoop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.glueball.snoop.entity.DocumentPath;

public class DocumentPathBean implements DocumentPathDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(final JdbcTemplate _jdbcTemplate) {
		this.jdbcTemplate = _jdbcTemplate;
	}

	public void insertOne(final DocumentPath doc) throws DataAccessException {

		final String query = "INSERT INTO DOCUMENT_PATH (id,md5_sum,file_name,uri,path,last_modified_time,last_indexed_time,content_type) VALUES (?,?,?,?,?,?,?,?)";

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
						pstmt.setTimestamp(7, doc.getLastIndexedTime());
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

	public void insertList(final List<DocumentPath> docs) throws DataAccessException {

		final String query = "INSERT INTO DOCUMENT_PATH (id,md5_sum,file_name,uri,path,last_modified_time,last_indexed_time,content_type) VALUES (?,?,?,?,?,?,?,?)";

		this.jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {

			public void setValues(final PreparedStatement pstmt, int i) throws SQLException {
				pstmt.setString(1, docs.get(i).getId());
				pstmt.setString(2, docs.get(i).getMd5Sum());
				pstmt.setString(3, docs.get(i).getFileName());
				pstmt.setString(4, docs.get(i).getUri());
				pstmt.setString(5, docs.get(i).getPath());
				pstmt.setTimestamp(6, docs.get(i).getLastModifiedTime());
				pstmt.setTimestamp(7, docs.get(i).getLastIndexedTime());
				pstmt.setString(8, docs.get(i).getContentType());

				System.out.println(i + " doc insterted to db");
			}

			public int getBatchSize() {
				return docs.size();
			}
			
		});

	}

	public DocumentPath findById(final String Id) throws DataAccessException {

		final String query = "SELECT id,md5_sum,file_name,uri,path,last_modified_time,last_indexed_time,content_type FROM DOCUMENT_PATH WHERE id = ?";
		final DocumentPath doc = new DocumentPath();

		this.jdbcTemplate.query(
				new PreparedStatementCreator() {

					public PreparedStatement createPreparedStatement(final Connection conn) throws SQLException {

						final PreparedStatement pstmt = conn.prepareStatement(query);
						pstmt.setString(1, Id);

						return pstmt;
					}
				}, new DocumentPathExtractor(doc));

		return doc;
	}

	public DocumentPath findBySum(final String md5sum) throws DataAccessException {
		final String query = "SELECT id,md5_sum,file_name,uri,path,last_modified_time,last_indexed_time,content_type FROM DOCUMENT_PATH WHERE md5_sum = ?";
		final DocumentPath doc = new DocumentPath();

		this.jdbcTemplate.query(
				new PreparedStatementCreator() {

					public PreparedStatement createPreparedStatement(final Connection conn) throws SQLException {

						final PreparedStatement pstmt = conn.prepareStatement(query);
						pstmt.setString(1, md5sum);

						return pstmt;
					}
				}, new DocumentPathExtractor(doc));

		return doc;
	}

	public Set<DocumentPath> fullTextSearch(final String search) throws DataAccessException {

		return null;
	}

	public void createTable() {
		this.jdbcTemplate.execute(DocumentPath.getCreateTable());
	}
	
	public long rowNum() {

		return (Long) this.jdbcTemplate.query("SELECT COUNT(*) row_num FROM DOCUMENT_PATH", new ResultSetExtractor(){

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				long rowNum = 0;
				if (rs.next()) {
					rowNum = rs.getLong(1);
				}
				return rowNum;
			}});
	}
	/*
	 *  private String id;
		private String md5Sum;
		private String fileName;
		private String uri;
		private String path;
		private Timestamp lastModifiedTime;
		private Timestamp lastIndexedTime;
		private String contentType;
	 */

}
