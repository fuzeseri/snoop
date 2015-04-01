package com.glueball.snoop.dao.impl;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.common.util.StringUtils;
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
import com.glueball.snoop.dao.setter.IndexedDocumentBatchUpdateSetter;
import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.entity.IndexedDocument;

/**
 * Implementation of the DocumentPathDao service.
 *
 * @author karesz
 */
public class DocumentPathDaoImpl implements
        DocumentPathDao {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LogManager
            .getLogger(DocumentPathDaoImpl.class);

    /**
     * Spring data jdbc template.
     */
    @Autowired(required = true)
    private JdbcTemplate jdbcTemplate;

    /**
     * Setter method of the jdbcTemplate field.
     *
     * @param pJdbcTemplate
     *            The Spring data jdbc template object.
     */
    public final void setJdbcTemplate(final JdbcTemplate pJdbcTemplate) {

        this.jdbcTemplate = pJdbcTemplate;
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.dao.SnoopDao#insertOne(java.lang.Object)
     */
    @Override
    public final void insertOne(final DocumentPath doc) {

        LOG.debug("Inserting document: " + doc.toString() + " query: "
                + DocumentPath.INSERT_QUERY);

        this.jdbcTemplate.update(DocumentPath.INSERT_QUERY,
                new DocumentPathInsertSetter(doc));

        LOG.debug("Succesfully inserted document: " + doc.toString());
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.dao.SnoopDao#insertList(java.util.List)
     */
    @Override
    public final void insertList(final List<DocumentPath> docs)
            throws DataAccessException {

        LOG.debug("Inserting " + docs.size() + " documents. query: "
                + DocumentPath.INSERT_QUERY);

        this.jdbcTemplate.batchUpdate(DocumentPath.INSERT_QUERY,
                new DocumentPathBatchInsertSetter(docs));

        LOG.debug(docs.toString() + " documents succesfully inserted.");
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.dao.SnoopDao#findById(java.lang.String)
     */
    @Override
    public final DocumentPath findById(final String id) {

        LOG.debug("Running query: " + DocumentPath.SELECT_BY_ID_QUERY
                + " with parameter [id : " + id + "]");

        final DocumentPath doc = new DocumentPath();

        this.jdbcTemplate.query(DocumentPath.SELECT_BY_ID_QUERY,
                new PreparedStatementSetter() {

                    @Override
                    public void setValues(final PreparedStatement ps)
                            throws SQLException {

                        ps.setString(1, id);
                    }
                }, new DocumentPathExtractor(doc));

        return StringUtils.isEmpty(doc.getId()) ? null : doc;
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.dao.SnoopDao#createTable()
     */
    @Override
    public final void createTable() throws DataAccessException {

        LOG.debug("Running query: " + DocumentPath.CREATE_TABLE_QUERY);

        this.jdbcTemplate.execute(DocumentPath.CREATE_TABLE_QUERY);

        LOG.debug("Running query: " + DocumentPath.CREATE_INDEX_QUERY);

        for (final String q : DocumentPath.CREATE_INDEX_QUERY) {

            this.jdbcTemplate.execute(q);
        }
        LOG.debug("Table DOCUMENT_PATH has successfully created.");
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.dao.SnoopDao#rowNum()
     */
    @Override
    public final long rowNum() {

        LOG.debug("Running query: " + DocumentPath.ROW_NUM_QUERY);

        return this.jdbcTemplate.query(DocumentPath.ROW_NUM_QUERY,
                new ResultSetExtractor<Long>() {

                    @Override
                    public Long extractData(final ResultSet rs)
                            throws SQLException,
                            DataAccessException {

                        long rowNum = 0;
                        if (rs.next()) {

                            rowNum = rs.getLong(1);
                        }
                        return rowNum;
                    }
                });
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.dao.SnoopDao#truncateTable()
     */
    @Override
    public final void truncateTable() {

        LOG.debug("Running query: " + DocumentPath.EMPTY_TABLE_QUERY);

        this.jdbcTemplate.execute(DocumentPath.EMPTY_TABLE_QUERY);

        LOG.debug("Table DOCUMENT_PATH has successfully truncated.");
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.dao.SnoopDao#selectAll()
     */
    @Override
    public final List<DocumentPath> selectAll() {

        LOG.debug("Running query: " + DocumentPath.SELECT_ALL_QUERY);

        final List<DocumentPath> docList = new ArrayList<DocumentPath>();

        this.jdbcTemplate.query(DocumentPath.SELECT_ALL_QUERY,
                new ListDocumentPathExtractor(docList));

        LOG.debug(docList.size() + " document selected.");

        return docList;
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.dao.SnoopDao#deleteById(java.lang.String)
     */
    @Override
    public final void deleteById(final String id) {

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

    /*
     * (non-Javadoc)
     * @see
     * com.glueball.snoop.dao.DocumentPathDao#updateDeletedDocuments(java.lang
     * .String)
     */
    @Override
    public final void updateDeletedDocuments(final String shareName) {

        LOG.debug("Running query: "
                + DocumentPath.UPDATE_DELETED_DOCUMENTS_QUERY);

        this.jdbcTemplate.update(
                DocumentPath.UPDATE_DELETED_DOCUMENTS_QUERY,
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

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.dao.DocumentPathDao#updateNewDocuments(
     * java.lang.String)
     */
    @Override
    public final void updateNewDocuments(final String shareName) {

        final List<DocumentPath> docList = new ArrayList<DocumentPath>();

        LOG.debug("Running query: " + DocumentPath.SELECT_NEW_DOCUMENTS_QUERY);

        this.jdbcTemplate.query(DocumentPath.SELECT_NEW_DOCUMENTS_QUERY,
                new ListDocumentPathExtractor(docList));

        LOG.debug(docList.size() + " new documents successfully selected");

        final List<IndexedDocument> idocList = DocumentPath
                .toIndexedDocumentList(docList,
                        IndexedDocument.INDEX_STATE_NEW);

        this.jdbcTemplate.batchUpdate(
                IndexedDocument.INSERT_DOCUMENT_QUERY,
                new IndexedDocumentBatchInsertSetter(idocList));

        LOG.debug("New documents status successfully updated on share : "
                + shareName);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.glueball.snoop.dao.DocumentPathDao#updateModifiedDocuments(java.lang
     * .String)
     */
    @Override
    public final void updateModifiedDocuments(final String shareName) {

        final List<DocumentPath> docList = new ArrayList<DocumentPath>();

        LOG.debug("Running query: "
                + DocumentPath.SELECT_MODIFIED_DOCUMENTS_QUERY);

        this.jdbcTemplate.query(
                DocumentPath.SELECT_MODIFIED_DOCUMENTS_QUERY,
                new ListDocumentPathExtractor(docList));

        LOG.debug(docList.size() + " new documents successfully selected");

        final List<IndexedDocument> idocList = DocumentPath
                .toIndexedDocumentList(docList,
                        IndexedDocument.INDEX_STATE_MODIFIED);

        this.jdbcTemplate.batchUpdate(
                IndexedDocument.UPDATE_STATE_QUERY,
                new IndexedDocumentBatchUpdateSetter(idocList));

        LOG.debug("Modified documents status successfully updated on share : "
                + shareName);
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.dao.SnoopDao#dropTable()
     */
    @Override
    public void dropTable() {

        LOG.debug("Running query: "
                + DocumentPath.DROP_TABLE_QUERY);

        this.jdbcTemplate.execute(DocumentPath.DROP_TABLE_QUERY);

        LOG.debug("Table: DOCUMENT_PATH successfully has droppped.");

    }
}
