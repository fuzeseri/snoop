package com.glueball.snoop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.entity.IndexedDocument;


public class DocumentPathBean implements SnoopDao<DocumentPath>, DocumentPathDao {

	private static final Logger LOG = Logger.getLogger(DocumentPathBean.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(final JdbcTemplate _jdbcTemplate) {
		this.jdbcTemplate = _jdbcTemplate;
	}

	@Autowired
	private TransactionTemplate transactionTemplate;

	public void setTransactionTemplate(final TransactionTemplate _transactionTemplate) {
		this.transactionTemplate = _transactionTemplate;
	}

	@Autowired
	private IndexedDocumentBean indexedDocumentBean;

	public void setIndexedDocumentBean(final IndexedDocumentBean indexedDocumentBean) {
		this.indexedDocumentBean = indexedDocumentBean;
	}

	public void insertOne(final DocumentPath doc) throws DataAccessException {

		final String query = "INSERT INTO DOCUMENT_PATH (id,md5_sum,file_name,uri,path,last_modified_time,content_type) VALUES (?,?,?,?,?,?,?)";

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
						pstmt.setString(7, doc.getContentType());
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

		final String query = "INSERT INTO DOCUMENT_PATH (id,md5_sum,file_name,uri,path,last_modified_time,content_type) VALUES (?,?,?,?,?,?,?)";

		this.jdbcTemplate.batchUpdate(query, new DocumentPathBatchInsertSetter(docs));
	}

	public DocumentPath findById(final String Id) throws DataAccessException {

		final String query = "SELECT id,md5_sum,file_name,uri,path,last_modified_time,content_type FROM DOCUMENT_PATH WHERE id = ?";
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
		final String query = "SELECT id,md5_sum,file_name,uri,path,last_modified_time,content_type FROM DOCUMENT_PATH WHERE md5_sum = ?";
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

	public void createTable() throws DataAccessException {
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

	public void deleteData() throws DataAccessException {
		this.jdbcTemplate.execute("DELETE FROM DOCUMENT_PATH");
	}

	public List<DocumentPath> selectAll() {

		final String query = "SELECT id,md5_sum,file_name,uri,path,last_modified_time,content_type FROM DOCUMENT_PATH";
		final List<DocumentPath> docList = new ArrayList<DocumentPath>();

		this.jdbcTemplate.query(query, new ListDocumentPathExtractor(docList));

		return docList;
	}

//	public List<DocumentPath> haveToIndex() throws DataAccessException {
//
//		final String query =
//				" SELECT "
//					+ " docp.id id, "
//					+ " docp.md5_sum md5_sum, "
//					+ " docp.file_name file_name, "
//					+ " docp.uri uri, "
//					+ " docp.path path, "
//					+ " docp.last_modified_time last_modified_time, "
//					+ " docp.content_type content_type "
//				+ " FROM "
//					+ " DOCUMENT_PATH docp "
//					+ " LEFT JOIN INDEXED_DOCUMENT idoc ON docp.id = idoc.id "
//				+ " WHERE "
//				+ " idoc.id IS NULL ";
//
//		final List<DocumentPath> docList = new ArrayList<DocumentPath>();
//		this.jdbcTemplate.query(query, new ListDocumentPathExtractor(docList));
//
//		LOG.info("HAveTOinDeX: " + docList.size());
//
//		return docList;
//	}

	@Override
	public void deleteData(String id) throws DataAccessException {
		this.jdbcTemplate.execute("DELETE FROM DOCUMENT_PATH WHERE id = '" + id + "'");
	}

	@Override
	public void deleteALL() throws DataAccessException {
		this.jdbcTemplate.execute("DELETE FROM DOCUMENT_PATH");
	}

	@Override
	public synchronized void updateDocumentPath(final List<DocumentPath> docs) throws DataAccessException {

		transactionTemplate.setIsolationLevel(TransactionTemplate.ISOLATION_SERIALIZABLE);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(final TransactionStatus status) {

				jdbcTemplate.execute(DocumentPath.getCreateTable());
				jdbcTemplate.execute("DELETE FROM DOCUMENT_PATH");
				final String query = "INSERT INTO DOCUMENT_PATH (id,md5_sum,file_name,uri,path,last_modified_time,content_type) VALUES (?,?,?,?,?,?,?)";
				jdbcTemplate.batchUpdate(query, new DocumentPathBatchInsertSetter(docs));
			}
		});
	}

	@Override
	public synchronized void updateNewDocuments() throws DataAccessException {

		transactionTemplate.setIsolationLevel(TransactionTemplate.ISOLATION_SERIALIZABLE);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(final TransactionStatus status) {

				jdbcTemplate.execute(IndexedDocument.getCreateTable());

				final String query =
						" SELECT "
							+ " docp.id id, "
							+ " docp.md5_sum md5_sum, "
							+ " docp.file_name file_name, "
							+ " docp.uri uri, "
							+ " docp.path path, "
							+ " docp.last_modified_time last_modified_time, "
							+ " docp.content_type content_type "
						+ " FROM "
							+ " DOCUMENT_PATH docp "
							+ " LEFT JOIN INDEXED_DOCUMENT idoc ON docp.id = idoc.id "
						+ " WHERE "
						+ " idoc.id IS NULL ";

				final List<DocumentPath> docList = new ArrayList<DocumentPath>();
				jdbcTemplate.query(query, new ListDocumentPathExtractor(docList));

				final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>(docList.size());
				for (final DocumentPath doc : docList) {
					final IndexedDocument idoc = (IndexedDocument) doc;
					idoc.setIndexState(IndexedDocument.INDEX_STATE_NEW);
					idocList.add(idoc);
				}
				indexedDocumentBean.insertList(idocList);
			}
		});
	}

	@Override
	public synchronized void updateModifiedDocumetns() throws DataAccessException {

		transactionTemplate.setIsolationLevel(TransactionTemplate.ISOLATION_SERIALIZABLE);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(final TransactionStatus status) {

				jdbcTemplate.execute(IndexedDocument.getCreateTable());

				final String query =
						" SELECT "
							+ " docp.id id, "
							+ " docp.md5_sum md5_sum, "
							+ " docp.file_name file_name, "
							+ " docp.uri uri, "
							+ " docp.path path, "
							+ " docp.last_modified_time last_modified_time, "
							+ " docp.content_type content_type "
						+ " FROM "
							+ " DOCUMENT_PATH docp "
							+ " INNER JOIN INDEXED_DOCUMENT idoc ON docp.id = idoc.id "
						+ " WHERE "
						+ " docp.last_modified_time > idoc.last_modified_time";

				final List<DocumentPath> docList = new ArrayList<DocumentPath>();
				jdbcTemplate.query(query, new ListDocumentPathExtractor(docList));

				final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>(docList.size());
				for (final DocumentPath doc : docList) {
					final IndexedDocument idoc = (IndexedDocument) doc;
					idoc.setIndexState(IndexedDocument.INDEX_STATE_MODIFIED);
					idoc.setLock(null);
					idoc.setLockTime(null);
					idocList.add(idoc);
				}
				indexedDocumentBean.insertList(idocList);
			}
		});
	}

	@Override
	public synchronized void updateDeletedDocuments() throws DataAccessException {

		transactionTemplate.setIsolationLevel(TransactionTemplate.ISOLATION_SERIALIZABLE);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(final TransactionStatus status) {

				jdbcTemplate.execute(IndexedDocument.getCreateTable());

				final String query =
						" UPDATE INDEXED_DOCUMENT SET index_state = '"+ IndexedDocument.INDEX_STATE_DELETED +"', lock = null, lock_time = null "
						+ " WHERE id IN ( "
						+ " SELECT "
							+ " idoc.id id "
						+ " FROM "
							+ " INDEXED_DOCUMENT idoc"
							+ " LEFT JOIN DOCUMENT_PATH docp ON idoc.id = docp.id "
						+ " WHERE "
						+ " docp.id IS NULL )";

				jdbcTemplate.execute(query);
			}
		});
	}
}
