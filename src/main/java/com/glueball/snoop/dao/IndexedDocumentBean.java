package com.glueball.snoop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.glueball.snoop.entity.IndexedDocument;

public class IndexedDocumentBean implements SnoopDao<IndexedDocument>, IndexedDocumentDao {

	private static final Logger LOG = LogManager.getLogger(IndexedDocumentBean.class);

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

	final Random random = new Random();

	int maxDoc = 100;

	public void setMaxDoc(int maxDocsPerRound) {
		this.maxDoc = maxDocsPerRound;
	}

	public void insertOne(final IndexedDocument doc) throws DataAccessException {

		final String query = "INSERT INTO "
				+ " INDEXED_DOCUMENT "
				+ " (id,share_name,file_name,uri,path,local_path,last_modified_time, "
				+ " last_indexed_time,content_type,index_state,lock,lock_time) "
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ";

		LOG.debug("Inserting document: " + doc.toString() + " query: " + query);

		this.jdbcTemplate.execute(
				new PreparedStatementCreator() {

					public PreparedStatement createPreparedStatement(final Connection conn) 
							throws SQLException {

						final PreparedStatement pstmt = conn.prepareStatement(query);
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

						pstmt.executeUpdate();

						return pstmt;
					}
				}, new PreparedStatementCallback() {

					public Object doInPreparedStatement(final PreparedStatement pstmt) 
							throws SQLException, DataAccessException {
						return null;
					}
				});

		LOG.debug("Succesfully inserted document: " + doc.toString());
	}

	public void insertList(final List<IndexedDocument> docs) throws DataAccessException {

		final String query = "INSERT INTO INDEXED_DOCUMENT "
				+ " (id,share_name,file_name,uri,path,local_path,last_modified_time,last_indexed_time,"
				+ " content_type,index_state,lock,lock_time) "
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

		LOG.debug("Inserting " + docs.size() + " documents. query: " + query);
		this.jdbcTemplate.batchUpdate(query, new IndexedDocumentBatchInsertSetter(docs));
		LOG.debug(docs.toString() + " documents succesfully inserted.");
	}

	public IndexedDocument findById(final String Id) throws DataAccessException {

		final String query = "SELECT "
				+ " id,share_name,file_name,uri,path,local_path,last_modified_time,last_indexed_time,"
				+ " content_type,index_state,lock,lock_time,lock,lock_time "
				+ " FROM INDEXED_DOCUMENT WHERE id = ?";

		final IndexedDocument doc = new IndexedDocument();

		LOG.debug("Running query: " + query + " with parameter [id : "+ Id +"]");
		this.jdbcTemplate.query(
				new PreparedStatementCreator() {

					public PreparedStatement createPreparedStatement(final Connection conn) 
							throws SQLException {

						final PreparedStatement pstmt = conn.prepareStatement(query);
						pstmt.setString(1, Id);

						return pstmt;
					}
				}, new IndexedDocumentExtractor(doc));

		return doc;
	}

	public IndexedDocument findBySum(final String md5sum) throws DataAccessException {

		final String query = "SELECT "
				+ " id,share_name,file_name,uri,path,local_path,last_modified_time,last_indexed_time,"
				+ " content_type,index_state,lock,lock_time "
				+ " FROM INDEXED_DOCUMENT WHERE share_name = ?";

		final IndexedDocument doc = new IndexedDocument();

		LOG.debug("Running query: " + query + " with parameter [id : "+ md5sum +"]");
		this.jdbcTemplate.query(
				new PreparedStatementCreator() {

					public PreparedStatement createPreparedStatement(final Connection conn) 
							throws SQLException {

						final PreparedStatement pstmt = conn.prepareStatement(query);
						pstmt.setString(1, md5sum);

						return pstmt;
					}
				}, new IndexedDocumentExtractor(doc));

		return doc;
	}

	public void createTable() throws DataAccessException {

		LOG.debug("Running query: " + IndexedDocument.getCreateTable());
		this.jdbcTemplate.execute(IndexedDocument.getCreateTable());
		LOG.debug("Running query: " + IndexedDocument.getCreateIndex());
		for (final String q : IndexedDocument.getCreateIndex())
			this.jdbcTemplate.execute(q);
	}

	public long rowNum() {

		LOG.debug("Running query: SELECT COUNT(*) row_num FROM INDEXED_DOCUMENT");
		return (Long) this.jdbcTemplate.query("SELECT COUNT(*) row_num FROM INDEXED_DOCUMENT", 
				new ResultSetExtractor(){

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				long rowNum = 0;
				if (rs.next()) {
					rowNum = rs.getLong(1);
				}
				return rowNum;
			}});
	}

	public void deleteData(final String id) throws DataAccessException {

		final String query = "DELETE FROM INDEXED_DOCUMENT WHERE id = '" + id + "'";
		LOG.debug("Running query: " + query);
		this.jdbcTemplate.execute(query);
	}

	public void deleteALL() throws DataAccessException {

		final String query = "DELETE FROM INDEXED_DOCUMENT";
		LOG.debug("Running query: " + query);
		this.jdbcTemplate.execute(query);
	}

	@Override
	public List<IndexedDocument> selectAll() throws DataAccessException {

		final String query = "SELECT "
				+ " id,share_name,file_name,uri,path,local_path,last_modified_time,last_indexed_time,"
				+ " content_type,index_state,lock,lock_time "
				+ " FROM INDEXED_DOCUMENT ";

		final List<IndexedDocument> docList = new ArrayList<IndexedDocument>();

		LOG.debug("Running query: " + query);
		this.jdbcTemplate.query(query, new ListIndexedDocumentExtractor(docList));
		LOG.debug(docList.size() + " document selected.");

		return docList;
	}

	@Override
	public void deleteByIds(final List<String> ids) throws DataAccessException {

		final String query = "DELETE FROM INDEXED_DOCUMENT WHERE id = ?";
		LOG.debug("Running batch delete. Query: " + query + " with " + ids.size() + " ids.");
		this.jdbcTemplate.batchUpdate(query,new DeleteDocumentBatchPstmtSetter(ids));
		LOG.debug(ids.size() + " records was deleted from INDEXED_DOCUMENT table");
	}

	@Override
	public List<IndexedDocument> haveToIndex() throws DataAccessException {

		final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>();
		transactionTemplate.setIsolationLevel(TransactionTemplate.ISOLATION_SERIALIZABLE);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			
			final int lock = random.nextInt();

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {

				final String getQuery = "SELECT id FROM INDEXED_DOCUMENT WHERE index_state IN "
						+ "("
						+ "'"+ IndexedDocument.INDEX_STATE_NEW +"',"
						+ "'"+ IndexedDocument.INDEX_STATE_MODIFIED +"',"
						+ "'"+ IndexedDocument.INDEX_STATE_DELETED +"', "
						+ "'"+ IndexedDocument.INDEX_STATE_REINDEX +"'"
					    + ") "
						+ " AND lock = 0 "
						+ " LIMIT " + maxDoc;

				final String updateQuery = "UPDATE INDEXED_DOCUMENT SET lock = "+ lock +", "
						+ "lock_time = now() WHERE id IN (" + getQuery + ")";

				LOG.debug("Running query: " + updateQuery);
				jdbcTemplate.execute(updateQuery);

				final String query = "SELECT "
						+ " id,share_name,file_name,uri,path,local_path,last_modified_time,last_indexed_time,"
						+ " content_type,index_state,lock,lock_time "
						+ " FROM INDEXED_DOCUMENT WHERE lock = " + lock;

				LOG.debug("Running query: " + query);
				jdbcTemplate.query(query, new ListIndexedDocumentExtractor(idocList));
				LOG.debug(idocList.size() + " documents selected to index.");
			}
		});

		return idocList;
	}

	@Override
	public void updateState(final List<IndexedDocument> idocList)
			throws DataAccessException {

		final String query = "UPDATE INDEXED_DOCUMENT SET "
				+ " last_indexed_time = ?, index_state = ? "
				+ " WHERE id = ?";

		LOG.debug("Running batch update. Query: " + query + " with " + idocList.size() + " ids.");
		this.jdbcTemplate.batchUpdate(query, new IndexedDocumentBatchUpdateSetter(idocList));
		LOG.debug(idocList.size() + " documents successfully updated.");
	}

	@Override
	public void unLockUpdateState(List<IndexedDocument> idocList)
			throws DataAccessException {

		final String query = "UPDATE INDEXED_DOCUMENT SET "
				+ " last_indexed_time = ?, index_state = ?, lock = 0, lock_time = null "
				+ " WHERE id = ?";

		LOG.debug("Running batch update. Query: " + query + " with " + idocList.size() + " ids.");
		this.jdbcTemplate.batchUpdate(query, new IndexedDocumentBatchUpdateSetter(idocList));
		LOG.debug(idocList.size() + " documents successfully updated.");
	}
}
