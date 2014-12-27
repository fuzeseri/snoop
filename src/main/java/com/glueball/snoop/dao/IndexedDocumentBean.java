package com.glueball.snoop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

import com.glueball.snoop.entity.IndexedDocument;

public class IndexedDocumentBean implements SnoopDao<IndexedDocument>, IndexedDocumentDao {

	private static final Logger LOG = Logger.getLogger(IndexedDocumentBean.class);

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

	int maxDocsPerRound = 100;

	public int getMaxDocsPerRound() {
		return maxDocsPerRound;
	}

	public void setMaxDocsPerRound(int maxDocsPerRound) {
		this.maxDocsPerRound = maxDocsPerRound;
	}

	public void insertOne(final IndexedDocument doc) throws DataAccessException {

		final String query = "INSERT INTO "
				+ " INDEXED_DOCUMENT "
				+ " (id,md5_sum,file_name,uri,path,last_modified_time, "
				+ " last_indexed_time,content_type,index_state,lock,lock_time) "
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?) ";

		this.jdbcTemplate.execute(
				new PreparedStatementCreator() {

					public PreparedStatement createPreparedStatement(final Connection conn) 
							throws SQLException {

						final PreparedStatement pstmt = conn.prepareStatement(query);
						pstmt.setString(1, doc.getId());
						pstmt.setString(2, doc.getMd5Sum());
						pstmt.setString(3, doc.getFileName());
						pstmt.setString(4, doc.getUri());
						pstmt.setString(5, doc.getPath());
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
	}

	public void insertList(final List<IndexedDocument> docs) throws DataAccessException {

		final String query = "INSERT INTO INDEXED_DOCUMENT "
				+ " (id,md5_sum,file_name,uri,path,last_modified_time,last_indexed_time,"
				+ " content_type,index_state,lock,lock_time) "
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?)";

		this.jdbcTemplate.batchUpdate(query, new IndexedDocumentBatchInsertSetter(docs));
	}

	public IndexedDocument findById(final String Id) throws DataAccessException {

		final String query = "SELECT "
				+ " id,md5_sum,file_name,uri,path,last_modified_time,last_indexed_time,"
				+ " content_type,index_state,lock,lock_time,lock,lock_time "
				+ " FROM INDEXED_DOCUMENT WHERE id = ?";

		final IndexedDocument doc = new IndexedDocument();

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
				+ " id,md5_sum,file_name,uri,path,last_modified_time,last_indexed_time,"
				+ " content_type,index_state,lock,lock_time "
				+ " FROM INDEXED_DOCUMENT WHERE md5_sum = ?";

		final IndexedDocument doc = new IndexedDocument();

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
		this.jdbcTemplate.execute(IndexedDocument.getCreateTable());
	}

	public long rowNum() {

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
		this.jdbcTemplate.execute("DELETE FROM INDEXED_DOCUMENT WHERE id = '" + id + "'");
	}

	public void deleteALL() throws DataAccessException {
		this.jdbcTemplate.execute("DELETE FROM INDEXED_DOCUMENT");
	}

	@Override
	public List<IndexedDocument> selectAll() throws DataAccessException {

		final String query = "SELECT "
				+ " id,md5_sum,file_name,uri,path,last_modified_time,last_indexed_time,"
				+ " content_type,index_state,lock,lock_time "
				+ " FROM INDEXED_DOCUMENT ";

		final List<IndexedDocument> docList = new ArrayList<IndexedDocument>();

		this.jdbcTemplate.query(query, new ListIndexedDocumentExtractor(docList));

		return docList;
	}

//	@Override
//	public List<String> getDeletedDocIds() throws DataAccessException {
//
//		final String query =
//				" SELECT "
//					+ " idoc.id id "
//				+ " FROM "
//					+ " INDEXED_DOCUMENT idoc"
//					+ " LEFT JOIN DOCUMENT_PATH docp ON idoc.id = docp.id "
//				+ " WHERE "
//				+ " docp.id IS NULL ";
//
//		final List<String> idList = new ArrayList<String>();
//		this.jdbcTemplate.query(query, new ListIdExtractor(idList));
//
//		LOG.info("DelETed size: " + idList.size());
//
//		return idList;
//	}
//
//	@Override
//	public List<String> getModifiedDocIds() throws DataAccessException {
//
//		final String query =
//				" SELECT "
//					+ " idoc.id id "
//				+ " FROM "
//					+ " INDEXED_DOCUMENT idoc"
//					+ " INNER JOIN DOCUMENT_PATH docp ON idoc.id = docp.id "
//				+ " WHERE "
//				+ " docp.last_modified_time > idoc.last_modified_time";
//
//		final List<String> idList = new ArrayList<String>();
//		this.jdbcTemplate.query(query, new ListIdExtractor(idList));
//
//		LOG.info("MoDiFIed size: " + idList.size());
//
//		return idList;		
//	}

	@Override
	public void deleteByIds(final List<String> ids) throws DataAccessException {

		final String query = "DELETE FROM INDEXED_DOCUMENT WHERE id = ?";
		this.jdbcTemplate.batchUpdate(query,new DeleteDocumentBatchPstmtSetter(ids));

		LOG.info(ids.size() + " records was deleted from INDEXED_DOCUMENT table");
	}

	@Override
	public List<IndexedDocument> haveToIndex() throws DataAccessException {

		final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>();
		transactionTemplate.setIsolationLevel(TransactionTemplate.ISOLATION_SERIALIZABLE);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			
			final long lock = random.nextLong();

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				final String getQuery = "SELECT id FROM INDEXED_DOCUMENT WHERE index_state IN "
						+ "("
						+ "'"+ IndexedDocument.INDEX_STATE_NEW +"',"
						+ "'"+ IndexedDocument.INDEX_STATE_MODIFIED +"',"
						+ "'"+ IndexedDocument.INDEX_STATE_DELETED +"', "
						+ "'"+ IndexedDocument.INDEX_STATE_REINDEX +"'"
					    + ") "
						+ " AND lock IS NULL "
						+ " LIMIT " + maxDocsPerRound;

				final String updateQuery = "UPDATE INDEXED_DOCUMENT SET lock = "+ lock +", "
						+ "lock_time = now() WHERE id IN (" + getQuery + ")";

				jdbcTemplate.execute(updateQuery);

				final String query = "SELECT "
						+ " id,md5_sum,file_name,uri,path,last_modified_time,last_indexed_time,"
						+ " content_type,index_state,lock,lock_time "
						+ " FROM INDEXED_DOCUMENT WHERE lock = " + lock;

				jdbcTemplate.query(query, new ListIndexedDocumentExtractor(idocList));
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

		this.jdbcTemplate.batchUpdate(query, new IndexedDocumentBatchUpdateSetter(idocList));
	}
}
