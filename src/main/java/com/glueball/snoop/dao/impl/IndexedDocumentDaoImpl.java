package com.glueball.snoop.dao.impl;

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
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.glueball.snoop.dao.IndexedDocumentDao;
import com.glueball.snoop.dao.extractor.IndexedDocumentExtractor;
import com.glueball.snoop.dao.extractor.ListIdExtractor;
import com.glueball.snoop.dao.extractor.ListIndexedDocumentExtractor;
import com.glueball.snoop.dao.setter.DeleteDocumentBatchPstmtSetter;
import com.glueball.snoop.dao.setter.IndexedDocumentBatchInsertSetter;
import com.glueball.snoop.dao.setter.IndexedDocumentBatchUpdateSetter;
import com.glueball.snoop.dao.setter.IndexedDocumentInsertSetter;
import com.glueball.snoop.entity.IndexedDocument;

public final class IndexedDocumentDaoImpl implements IndexedDocumentDao {

	private static final Logger LOG = LogManager
			.getLogger(IndexedDocumentDaoImpl.class);

	@Autowired(required = true)
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(final JdbcTemplate _jdbcTemplate) {
		this.jdbcTemplate = _jdbcTemplate;
	}

	private static final Random random = new Random();

	@Override
	public void insertOne(final IndexedDocument doc) {

		LOG.debug("Inserting document: " + doc.toString() + " query: "
				+ IndexedDocument.INSERT_DOCUMENT_QUERY);

		this.jdbcTemplate.update(IndexedDocument.INSERT_DOCUMENT_QUERY,
				new IndexedDocumentInsertSetter(doc));

		LOG.debug("Succesfully inserted document: " + doc.toString());
	}

	@Override
	public void insertList(final List<IndexedDocument> docs) {

		LOG.debug("Inserting " + docs.size() + " documents. query: "
				+ IndexedDocument.INSERT_DOCUMENT_QUERY);

		this.jdbcTemplate.batchUpdate(IndexedDocument.INSERT_DOCUMENT_QUERY,
				new IndexedDocumentBatchInsertSetter(docs));

		LOG.debug(docs.toString() + " documents succesfully inserted.");
	}

	@Override
	public IndexedDocument findById(final String id) {

		final IndexedDocument doc = new IndexedDocument();

		LOG.debug("Running query: " + IndexedDocument.SELECT_BY_ID_QUERY
				+ " with parameter [ id : " + id + "]");

		this.jdbcTemplate.query(IndexedDocument.SELECT_BY_ID_QUERY,
				new PreparedStatementSetter() {

					@Override
					public void setValues(final PreparedStatement ps)
							throws SQLException {

						ps.setString(1, id);
					}

				}, new IndexedDocumentExtractor(doc));
		return doc;
	}

	@Override
	public List<IndexedDocument> findByShareName(final String share) {

		final List<IndexedDocument> docList = new ArrayList<IndexedDocument>();

		LOG.debug("Running query: "
				+ IndexedDocument.SELECT_BY_SHARE_NAME_QUERY
				+ " with parameter [ shareName : " + share + "]");

		this.jdbcTemplate.query(IndexedDocument.SELECT_BY_SHARE_NAME_QUERY,
				new PreparedStatementSetter() {

					@Override
					public void setValues(final PreparedStatement ps)
							throws SQLException {

						ps.setString(1, share);
					}
				}, new ListIndexedDocumentExtractor(docList));

		LOG.debug(docList.size() + " document selected.");

		return docList;
	}

	@Override
	public void createTable() {

		LOG.debug("Running query: " + IndexedDocument.CREATE_TABLE_QUERY);

		this.jdbcTemplate.execute(IndexedDocument.CREATE_TABLE_QUERY);

		LOG.debug("Running query: " + IndexedDocument.CREATE_INDEX_QUERY);

		for (final String q : IndexedDocument.CREATE_INDEX_QUERY) {
			this.jdbcTemplate.execute(q);
		}
	}

	@Override
	public long rowNum() {

		LOG.debug("Running query: " + IndexedDocument.SELECT_ROW_NUM_QUERY);

		return (Long) this.jdbcTemplate.query(
				IndexedDocument.SELECT_ROW_NUM_QUERY,
				new ResultSetExtractor<Long>() {

					public Long extractData(final ResultSet rs)
							throws SQLException, DataAccessException {

						long rowNum = 0;
						if (rs.next()) {
							rowNum = rs.getLong(1);
						}
						return rowNum;
					}
				});
	}

	@Override
	public void deleteById(final String id) {

		LOG.debug("Running query: " + IndexedDocument.DELETE_BY_ID_QUERY);

		this.jdbcTemplate.update(IndexedDocument.DELETE_BY_ID_QUERY,
				new PreparedStatementSetter() {

					@Override
					public void setValues(final PreparedStatement ps)
							throws SQLException {

						ps.setString(1, id);
					}
				});
		LOG.debug("Ducument id : " + id + " successfully deleted.");
	}

	@Override
	public void truncateTable() {

		LOG.debug("Running query: " + IndexedDocument.TRUNCATE_TABLE_QUERY);

		this.jdbcTemplate.execute(IndexedDocument.TRUNCATE_TABLE_QUERY);

		LOG.debug("Table INDEXED_DOCUENT has successfully truncated.");
	}

	@Override
	public List<IndexedDocument> selectAll() throws DataAccessException {

		final List<IndexedDocument> docList = new ArrayList<IndexedDocument>();

		LOG.debug("Running query: " + IndexedDocument.SELECT_ALL_QUERY);
		this.jdbcTemplate.query(IndexedDocument.SELECT_ALL_QUERY,
				new ListIndexedDocumentExtractor(docList));

		LOG.debug(docList.size() + " document selected.");

		return docList;
	}

	@Override
	public void deleteByIds(final List<String> ids) {

		LOG.debug("Running batch delete. Query: "
				+ IndexedDocument.DELETE_BY_ID_QUERY + " with " + ids.size()
				+ " ids.");

		this.jdbcTemplate.batchUpdate(IndexedDocument.DELETE_BY_ID_QUERY,
				new DeleteDocumentBatchPstmtSetter(ids));

		LOG.debug(ids.size()
				+ " records was deleted from INDEXED_DOCUMENT table");
	}

	@Override
	public long lockDocuments(final int docNum) {

		final int lock = random.nextInt();

		LOG.debug("Running query: "
				+ IndexedDocument.GET_INDEXABLE_DOCUMENTS_QUERY);

		final List<String> ids = new ArrayList<String>((int) docNum);
		jdbcTemplate.query(IndexedDocument.GET_INDEXABLE_DOCUMENTS_QUERY,
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {

						ps.setInt(1, docNum);
					}
				}, new ListIdExtractor(ids));

		LOG.debug("Running query: " + IndexedDocument.LOCK_DOCUMENTS_QUERY);

		jdbcTemplate.batchUpdate(IndexedDocument.LOCK_DOCUMENTS_QUERY,
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(final PreparedStatement ps, int i)
							throws SQLException {

						ps.setLong(1, lock);
						ps.setString(2, ids.get(i));
					}

					@Override
					public int getBatchSize() {

						return ids.size();
					}
				});
		LOG.debug(ids.size() + " documents successfully locked");

		return lock;
	}

	@Override
	public List<IndexedDocument> getByLock(final long lock) {

		final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>();

		LOG.debug("Running query: " + IndexedDocument.SELECT_BY_LOCK_QUERY);

		jdbcTemplate.query(IndexedDocument.SELECT_BY_LOCK_QUERY,
				new PreparedStatementSetter() {

					@Override
					public void setValues(final PreparedStatement ps)
							throws SQLException {
						ps.setLong(1, lock);
					}
				}, new ListIndexedDocumentExtractor(idocList));

		LOG.debug(idocList.size() + " documents selected to index.");

		return idocList;
	}

	@Override
	public void updateState(final List<IndexedDocument> idocList) {

		LOG.debug("Running batch update. Query: "
				+ IndexedDocument.UPDATE_STATE_QUERY + " with "
				+ idocList.size() + " ids.");

		this.jdbcTemplate.batchUpdate(IndexedDocument.UPDATE_STATE_QUERY,
				new IndexedDocumentBatchUpdateSetter(idocList));

		LOG.debug(idocList.size() + " documents successfully updated.");
	}

	@Override
	public void unLockUpdateState(final List<IndexedDocument> idocList) {

		LOG.debug("Running batch update. Query: "
				+ IndexedDocument.UNLOCK_UPDATE_STATE_QUERY + " with "
				+ idocList.size() + " ids.");

		this.jdbcTemplate.batchUpdate(
				IndexedDocument.UNLOCK_UPDATE_STATE_QUERY,
				new IndexedDocumentBatchUpdateSetter(idocList));

		LOG.debug(idocList.size() + " documents successfully updated.");
	}
}