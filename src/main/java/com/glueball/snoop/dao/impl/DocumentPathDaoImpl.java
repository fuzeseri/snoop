package com.glueball.snoop.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.glueball.snoop.dao.DocumentPathDao;
import com.glueball.snoop.dao.extractor.DocumentPathExtractor;
import com.glueball.snoop.dao.extractor.ListDocumentPathExtractor;
import com.glueball.snoop.dao.setter.DocumentPathBatchInsertSetter;
import com.glueball.snoop.dao.setter.DocumentPathInsertSetter;
import com.glueball.snoop.dao.setter.IndexedDocumentBatchInsertSetter;
import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.entity.IndexedDocument;

public class DocumentPathDaoImpl implements DocumentPathDao {

	private static final Logger LOG = LogManager
			.getLogger(DocumentPathDaoImpl.class);

	@Autowired(required = true)
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(final JdbcTemplate _jdbcTemplate) {

		this.jdbcTemplate = _jdbcTemplate;
	}

	@Override
	public void insertOne(final DocumentPath doc) {

		LOG.debug("Inserting document: " + doc.toString() + " query: "
				+ DocumentPath.INSERT_QUERY);

		this.jdbcTemplate.update(DocumentPath.INSERT_QUERY,
				new DocumentPathInsertSetter(doc));

		LOG.debug("Succesfully inserted document: " + doc.toString());
	}

	@Override
	public void insertList(final List<DocumentPath> docs)
			throws DataAccessException {

		LOG.debug("Inserting " + docs.size() + " documents. query: "
				+ DocumentPath.INSERT_QUERY);

		this.jdbcTemplate.batchUpdate(DocumentPath.INSERT_QUERY,
				new DocumentPathBatchInsertSetter(docs));

		LOG.debug(docs.toString() + " documents succesfully inserted.");
	}

	@Override
	public DocumentPath findById(final String Id) {

		LOG.debug("Running query: " + DocumentPath.SELECT_BY_ID_QUERY
				+ " with parameter [id : " + Id + "]");

		final DocumentPath doc = new DocumentPath();

		this.jdbcTemplate.query(DocumentPath.SELECT_BY_ID_QUERY,
				new PreparedStatementSetter() {

					@Override
					public void setValues(final PreparedStatement ps)
							throws SQLException {

						ps.setString(1, Id);
					}
				}, new DocumentPathExtractor(doc));
		return doc;
	}

	@Override
	public DocumentPath findBySum(final String md5sum)
			throws DataAccessException {

		LOG.debug("Running query: " + DocumentPath.SELECT_BY_SUM_QUERY
				+ " with parameter [id : " + md5sum + "]");

		final DocumentPath doc = new DocumentPath();

		this.jdbcTemplate.query(DocumentPath.SELECT_BY_SUM_QUERY,
				new PreparedStatementSetter() {

					@Override
					public void setValues(final PreparedStatement ps)
							throws SQLException {

						ps.setString(1, md5sum);
					}
				}, new DocumentPathExtractor(doc));
		return doc;
	}

	@Override
	public void createTable() throws DataAccessException {

		LOG.debug("Running query: " + DocumentPath.CREATE_TABLE_QUERY);

		this.jdbcTemplate.execute(DocumentPath.CREATE_TABLE_QUERY);

		LOG.debug("Running query: " + DocumentPath.CREATE_INDEX_QUERY);

		for (final String q : DocumentPath.CREATE_INDEX_QUERY) {

			this.jdbcTemplate.execute(q);
		}
		LOG.debug("Table DOCUMENT_PATH has successfully created.");
	}

	@Override
	public long rowNum() {

		LOG.debug("Running query: " + DocumentPath.ROW_NUM_QUERY);

		return (Long) this.jdbcTemplate.query(DocumentPath.ROW_NUM_QUERY,
				new ResultSetExtractor<Long>() {

					public Long extractData(ResultSet rs) throws SQLException,
							DataAccessException {

						long rowNum = 0;
						if (rs.next()) {

							rowNum = rs.getLong(1);
						}
						return rowNum;
					}
				});
	}

	@Override
	public void truncateTable() {

		LOG.debug("Running query: " + DocumentPath.EMPTY_TABLE_QUERY);

		this.jdbcTemplate.execute(DocumentPath.EMPTY_TABLE_QUERY);

		LOG.debug("Table DOCUMENT_PATH has successfully truncated.");
	}

	@Override
	public List<DocumentPath> selectAll() {

		LOG.debug("Running query: " + DocumentPath.SELECT_ALL_QUERY);

		final List<DocumentPath> docList = new ArrayList<DocumentPath>();

		this.jdbcTemplate.query(DocumentPath.SELECT_ALL_QUERY,
				new ListDocumentPathExtractor(docList));

		LOG.debug(docList.size() + " document selected.");

		return docList;
	}

	@Override
	public void deleteById(final String id) {

		LOG.debug("Running query: " + DocumentPath.DELETE_BY_ID_QUERY);

		this.jdbcTemplate.update(DocumentPath.DELETE_BY_ID_QUERY,
				new PreparedStatementSetter() {

					@Override
					public void setValues(final PreparedStatement ps)
							throws SQLException {

						ps.setString(1, id);
					}
				});

		LOG.debug("Document id : " + id + " successfully deleted.");
	}

	@Override
	public void updateDeletedDocuments(final String shareName) {

		LOG.debug("Running query: "
				+ DocumentPath.UPDATE_DELETED_DOCUMENTS_QUERY);

		jdbcTemplate.update(DocumentPath.UPDATE_DELETED_DOCUMENTS_QUERY,
				new PreparedStatementSetter() {

					@Override
					public void setValues(final PreparedStatement ps)
							throws SQLException {

						ps.setString(1, IndexedDocument.INDEX_STATE_DELETED);
						ps.setString(2, shareName);
					}
				});
		LOG.debug("Deleted documents status successfully updated on share : "
				+ shareName);
	}

	@Override
	public void updateNewDocuments(String shareName) {

		final List<DocumentPath> docList = new ArrayList<DocumentPath>();

		LOG.debug("Running query: " + DocumentPath.SELECT_NEW_DOCUMENTS_QUERY);

		jdbcTemplate.query(DocumentPath.SELECT_NEW_DOCUMENTS_QUERY,
				new ListDocumentPathExtractor(docList));

		LOG.debug(docList.size() + " new documents successfully selected");

		final List<IndexedDocument> idocList = DocumentPath
				.toIndexedDocumentList(docList, IndexedDocument.INDEX_STATE_NEW);

		jdbcTemplate.batchUpdate(IndexedDocument.INSERT_DOCUMENT_QUERY,
				new IndexedDocumentBatchInsertSetter(idocList));

		LOG.debug("New documents status successfully updated on share : "
				+ shareName);
	}

	@Override
	public void updateModifiedDocuments(String shareName) {

		final List<DocumentPath> docList = new ArrayList<DocumentPath>();

		LOG.debug("Running query: "
				+ DocumentPath.SELECT_MODIFIED_DOCUMENTS_QUERY);

		jdbcTemplate.query(DocumentPath.SELECT_MODIFIED_DOCUMENTS_QUERY,
				new ListDocumentPathExtractor(docList));

		LOG.debug(docList.size() + " new documents successfully selected");

		final List<IndexedDocument> idocList = DocumentPath
				.toIndexedDocumentList(docList,
						IndexedDocument.INDEX_STATE_MODIFIED);

		jdbcTemplate.batchUpdate(IndexedDocument.INSERT_DOCUMENT_QUERY,
				new IndexedDocumentBatchInsertSetter(idocList));

		LOG.debug("Modified documents status successfully updated on share : "
				+ shareName);
	}
}
