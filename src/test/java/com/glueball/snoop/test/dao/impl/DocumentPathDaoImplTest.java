package com.glueball.snoop.test.dao.impl;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.glueball.snoop.dao.DocumentPathDao;
import com.glueball.snoop.dao.extractor.IndexedDocumentExtractor;
import com.glueball.snoop.dao.impl.DocumentPathDaoImpl;
import com.glueball.snoop.dao.setter.IndexedDocumentBatchInsertSetter;
import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.entity.IndexedDocument;
import com.glueball.snoop.util.MD5;

/**
 * @author karesz
 */
public class DocumentPathDaoImplTest {

    /**
     * Data access object for DocumentPaths.
     */
    private DocumentPathDao dao;

    /**
     * Spring application context.
     */
    private ApplicationContext ctx;

    /**
     * Database data source.
     */
    private DriverManagerDataSource ds;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        this.ctx = new ClassPathXmlApplicationContext(
                "classpath:spring/application-test.xml");

        this.dao = ctx.getBean(DocumentPathDaoImpl.class);

        ds = (DriverManagerDataSource) ctx.getBean("h2DataSource");

        dao.createTable();
        dao.truncateTable();
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.DocumentPathDaoImpl#insertOne(com.glueball.snoop.entity.DocumentPath)}
     *
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    @Test
    public final void testInsertOne() throws NoSuchAlgorithmException {

        final byte[] fileId1 = MD5.md5Digest("/testdir/filename_a.pdf");
        final byte[] fileId2 = MD5.md5Digest("/testdir/filename_b.pdf");

        final DocumentPath doc1 = new DocumentPath();
        doc1.setContentType("application/pdf");
        doc1.setFileName("filename_a.pdf");
        doc1.setId(fileId1);
        doc1.setLastModifiedTime(new Date().getTime());
        doc1.setLocalPath("/testdir/filename_a.pdf");
        doc1.setPath("/testdir/filename_a.pdf");
        doc1.setShareName("test-share");
        doc1.setUri("file:///testdir/filename_a.pdf");

        dao.truncateTable();
        dao.insertOne(doc1);

        assertEquals(1, dao.rowNum());
        assertEquals(doc1, dao.findById(MD5.toHexString(fileId1)));

        final DocumentPath doc2 = new DocumentPath();
        doc2.setContentType("application/pdf");
        doc2.setFileName("filename_b.pdf");
        doc2.setId(fileId2);
        doc2.setLastModifiedTime(new Date().getTime());
        doc2.setLocalPath("/testdir/filename_b.pdf");
        doc2.setPath("/testdir/filename_b.pdf");
        doc2.setShareName("test-share");
        doc2.setUri("file:///testdir/filename_b.pdf");

        dao.insertOne(doc2);

        assertEquals(2, dao.rowNum());
        assertEquals(doc2, dao.findById(MD5.toHexString(fileId2)));
        assertEquals(doc1, dao.findById(MD5.toHexString(fileId1)));
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.DocumentPathDaoImpl#insertList(java.util.List)}
     * .
     * 
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    @Test
    public final void testInsertList() throws NoSuchAlgorithmException {

        final List<DocumentPath> list = new ArrayList<DocumentPath>();

        final byte[] fileId1 = MD5.md5Digest("/testdir/filename_a.pdf");
        final byte[] fileId2 = MD5.md5Digest("/testdir/filename_b.pdf");
        final byte[] fileId3 = MD5.md5Digest("/testdir/filename_c.pdf");

        final DocumentPath doc1 = new DocumentPath();
        doc1.setContentType("application/pdf");
        doc1.setFileName("filename_a.pdf");
        doc1.setId(fileId1);
        doc1.setLastModifiedTime(new Date().getTime());
        doc1.setLocalPath("/testdir/filename_a.pdf");
        doc1.setPath("/testdir/filename_a.pdf");
        doc1.setShareName("test-share");
        doc1.setUri("file:///testdir/filename_a.pdf");

        list.add(doc1);

        final DocumentPath doc2 = new DocumentPath();
        doc2.setContentType("application/pdf");
        doc2.setFileName("filename_b.pdf");
        doc2.setId(fileId2);
        doc2.setLastModifiedTime(new Date().getTime());
        doc2.setLocalPath("/testdir/filename_b.pdf");
        doc2.setPath("/testdir/filename_b.pdf");
        doc2.setShareName("test-share");
        doc2.setUri("file:///testdir/filename_b.pdf");

        list.add(doc2);

        final DocumentPath doc3 = new DocumentPath();
        doc3.setContentType("application/pdf");
        doc3.setFileName("filename_c.pdf");
        doc3.setId(fileId3);
        doc3.setLastModifiedTime(new Date().getTime());
        doc3.setLocalPath("/testdir/filename_c.pdf");
        doc3.setPath("/testdir/filename_c.pdf");
        doc3.setShareName("test-share");
        doc3.setUri("file:///testdir/filename_c.pdf");

        list.add(doc3);

        dao.truncateTable();
        dao.insertList(list);

        assertEquals(3, dao.rowNum());
        assertEquals(doc1, dao.findById(MD5.toHexString(fileId1)));
        assertEquals(doc2, dao.findById(MD5.toHexString(fileId2)));
        assertEquals(doc3, dao.findById(MD5.toHexString(fileId3)));
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.DocumentPathDaoImpl#findById(java.lang.String)}
     * .
     * 
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    @Test
    public final void testFindById() throws NoSuchAlgorithmException {

        final DocumentPath doc = new DocumentPath();

        final byte[] fileId = MD5.md5Digest("/testdir/filename.pdf");

        doc.setContentType("application/pdf");
        doc.setFileName("filename.pdf");
        doc.setId(fileId);
        doc.setLastModifiedTime(new Date().getTime());
        doc.setLocalPath("/testdir/filename.pdf");
        doc.setPath("/testdir/filename.pdf");
        doc.setShareName("test-share");
        doc.setUri("file:///testdir/filename.pdf");

        dao.truncateTable();
        dao.insertOne(doc);

        assertEquals(doc, dao.findById(MD5.toHexString(fileId)));
        assertEquals(1, dao.rowNum());
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.DocumentPathDaoImpl#createTable()}.
     * 
     * @throws SQLException
     *             on any SQL failure.
     */
    @Test
    public final void testCreateTable() throws SQLException {

        boolean tableExists = false;

        dao.dropTable();
        dao.createTable();

        try (final Connection conn = ds.getConnection()) {
            try (final ResultSet rset =
                    conn.getMetaData().getTables(null, null, "DOCUMENT_PATH",
                            null)) {
                if (rset.next()) {
                    tableExists = true;
                }
            }
        }
        assertTrue(tableExists);
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.DocumentPathDaoImpl#dropTable()}.
     *
     * @throws SQLException
     *             on any SQL failure.
     */
    @Test
    public final void testDropTable() throws SQLException {

        boolean tableExists = false;

        dao.dropTable();

        try (final Connection conn = ds.getConnection()) {
            try (final ResultSet rset =
                    conn.getMetaData().getTables(null, null, "DOCUMENT_PATH",
                            null)) {
                if (rset.next()) {
                    tableExists = true;
                }
            }
        }
        assertTrue(!tableExists);
        dao.createTable();
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.DocumentPathDaoImpl#rowNum()}.
     * 
     * @throws NoSuchAlgorithmException
     */
    @Test
    public final void testRowNum() throws NoSuchAlgorithmException {

        dao.truncateTable();

        final List<DocumentPath> docList = new ArrayList<DocumentPath>();
        for (int i = 0; i < 100; i++) {
            final DocumentPath doc = new DocumentPath();
            doc.setId(MD5.md5Digest("" + i));
            docList.add(doc);
        }

        dao.insertList(docList);
        assertEquals(100, dao.rowNum());
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.DocumentPathDaoImpl#truncateTable()}.
     * 
     * @throws NoSuchAlgorithmException
     */
    @Test
    public final void testTruncateTable() throws NoSuchAlgorithmException {

        dao.truncateTable();

        final List<DocumentPath> docList = new ArrayList<DocumentPath>();
        for (int i = 0; i < 100; i++) {
            final DocumentPath doc = new DocumentPath();
            doc.setId(MD5.md5Digest("" + i));
            docList.add(doc);
        }

        dao.insertList(docList);
        assertEquals(100, dao.rowNum());

        dao.truncateTable();
        assertEquals(0, dao.rowNum());
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.DocumentPathDaoImpl#selectAll()}.
     * 
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    @Test
    public final void testSelectAll() throws NoSuchAlgorithmException {

        final List<DocumentPath> list = new ArrayList<DocumentPath>();

        final byte[] fileId1 = MD5.md5Digest("/testdir/filename_a.pdf");
        final byte[] fileId2 = MD5.md5Digest("/testdir/filename_b.pdf");
        final byte[] fileId3 = MD5.md5Digest("/testdir/filename_c.pdf");

        final DocumentPath doc1 = new DocumentPath();
        doc1.setContentType("application/pdf");
        doc1.setFileName("filename_a.pdf");
        doc1.setId(fileId1);
        doc1.setLastModifiedTime(new Date().getTime());
        doc1.setLocalPath("/testdir/filename_a.pdf");
        doc1.setPath("/testdir/filename_a.pdf");
        doc1.setShareName("test-share");
        doc1.setUri("file:///testdir/filename_a.pdf");

        list.add(doc1);

        final DocumentPath doc2 = new DocumentPath();
        doc2.setContentType("application/pdf");
        doc2.setFileName("filename_b.pdf");
        doc2.setId(fileId2);
        doc2.setLastModifiedTime(new Date().getTime());
        doc2.setLocalPath("/testdir/filename_b.pdf");
        doc2.setPath("/testdir/filename_b.pdf");
        doc2.setShareName("test-share");
        doc2.setUri("file:///testdir/filename_b.pdf");

        list.add(doc2);

        final DocumentPath doc3 = new DocumentPath();
        doc3.setContentType("application/pdf");
        doc3.setFileName("filename_c.pdf");
        doc3.setId(fileId3);
        doc3.setLastModifiedTime(new Date().getTime());
        doc3.setLocalPath("/testdir/filename_c.pdf");
        doc3.setPath("/testdir/filename_c.pdf");
        doc3.setShareName("test-share");
        doc3.setUri("file:///testdir/filename_c.pdf");

        list.add(doc3);

        dao.truncateTable();
        dao.insertList(list);

        final List<DocumentPath> results = dao.selectAll();

        assertEquals(3, results.size());
        assertEquals(doc1, results.get(0));
        assertEquals(doc2, results.get(1));
        assertEquals(doc3, results.get(2));
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.DocumentPathDaoImpl#deleteById(java.lang.String)}
     * .
     * 
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    @Test
    public final void testDeleteById() throws NoSuchAlgorithmException {

        final DocumentPath doc = new DocumentPath();

        final byte[] fileId = MD5.md5Digest("/testdir/filename.pdf");

        doc.setContentType("application/pdf");
        doc.setFileName("filename.pdf");
        doc.setId(fileId);
        doc.setLastModifiedTime(new Date().getTime());
        doc.setLocalPath("/testdir/filename.pdf");
        doc.setPath("/testdir/filename.pdf");
        doc.setShareName("test-share");
        doc.setUri("file:///testdir/filename.pdf");

        dao.truncateTable();
        dao.insertOne(doc);

        assertEquals(doc, dao.findById(MD5.toHexString(fileId)));
        assertEquals(1, dao.rowNum());

        dao.deleteById(MD5.toHexString(fileId));

        assertEquals(null, dao.findById(MD5.toHexString(fileId)));
        assertEquals(0, dao.rowNum());
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.DocumentPathDaoImpl# updateDeletedDocuments(java.lang.String)}
     * .
     * 
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    @Test
    public final void testUpdateDeletedDocuments()
            throws NoSuchAlgorithmException {

        final List<DocumentPath> list = new ArrayList<DocumentPath>();

        final byte[] fileId1 = MD5.md5Digest("/testdir/filename_a.pdf");
        final byte[] fileId2 = MD5.md5Digest("/testdir/filename_b.pdf");

        final DocumentPath doc1 = new DocumentPath();
        doc1.setContentType("application/pdf");
        doc1.setFileName("filename_a.pdf");
        doc1.setId(fileId1);
        doc1.setLastModifiedTime(new Date().getTime());
        doc1.setLocalPath("/testdir/filename_a.pdf");
        doc1.setPath("/testdir/filename_a.pdf");
        doc1.setShareName("test-share");
        doc1.setUri("file:///testdir/filename_a.pdf");

        list.add(doc1);

        final DocumentPath doc2 = new DocumentPath();
        doc2.setContentType("application/pdf");
        doc2.setFileName("filename_b.pdf");
        doc2.setId(fileId2);
        doc2.setLastModifiedTime(new Date().getTime());
        doc2.setLocalPath("/testdir/filename_b.pdf");
        doc2.setPath("/testdir/filename_b.pdf");
        doc2.setShareName("test-share");
        doc2.setUri("file:///testdir/filename_b.pdf");

        list.add(doc2);

        final List<IndexedDocument> idocList =
                DocumentPath.toIndexedDocumentList(list,
                        IndexedDocument.INDEX_STATE_INDEXED);

        list.remove(1);

        dao.truncateTable();
        dao.insertList(list);

        final JdbcTemplate template = new JdbcTemplate(ds);

        template.execute(IndexedDocument.TRUNCATE_TABLE_QUERY);

        template.batchUpdate(IndexedDocument.INSERT_DOCUMENT_QUERY,
                new IndexedDocumentBatchInsertSetter(idocList));

        dao.updateDeletedDocuments("test-share");

        final IndexedDocument idoc1 = new IndexedDocument();

        template.query(IndexedDocument.SELECT_BY_ID_QUERY,
                new PreparedStatementSetter() {

                    @Override
                    public void setValues(final PreparedStatement ps)
                            throws SQLException {

                        ps.setString(1, MD5.toHexString(fileId1));
                    }

                }, new IndexedDocumentExtractor(idoc1));

        assertEquals(IndexedDocument.INDEX_STATE_INDEXED,
                idoc1.getIndexState());

        final IndexedDocument idoc2 = new IndexedDocument();

        template.query(IndexedDocument.SELECT_BY_ID_QUERY,
                new PreparedStatementSetter() {

                    @Override
                    public void setValues(final PreparedStatement ps)
                            throws SQLException {

                        ps.setString(1, MD5.toHexString(fileId2));
                    }

                }, new IndexedDocumentExtractor(idoc2));

        assertEquals(IndexedDocument.INDEX_STATE_DELETED,
                idoc2.getIndexState());
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.DocumentPathDaoImpl #updateNewDocuments(java.lang.String)}
     *
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    @Test
    public final void testUpdateNewDocuments() throws NoSuchAlgorithmException {

        final List<DocumentPath> list = new ArrayList<DocumentPath>();

        final byte[] fileId1 = MD5.md5Digest("/testdir/filename_a.pdf");
        final byte[] fileId2 = MD5.md5Digest("/testdir/filename_b.pdf");

        final DocumentPath doc1 = new DocumentPath();
        doc1.setContentType("application/pdf");
        doc1.setFileName("filename_a.pdf");
        doc1.setId(fileId1);
        doc1.setLastModifiedTime(new Date().getTime() - 100000);
        doc1.setLocalPath("/testdir/filename_a.pdf");
        doc1.setPath("/testdir/filename_a.pdf");
        doc1.setShareName("test-share");
        doc1.setUri("file:///testdir/filename_a.pdf");

        list.add(doc1);

        final DocumentPath doc2 = new DocumentPath();
        doc2.setContentType("application/pdf");
        doc2.setFileName("filename_b.pdf");
        doc2.setId(fileId2);
        doc2.setLastModifiedTime(new Date().getTime());
        doc2.setLocalPath("/testdir/filename_b.pdf");
        doc2.setPath("/testdir/filename_b.pdf");
        doc2.setShareName("test-share");
        doc2.setUri("file:///testdir/filename_b.pdf");

        list.add(doc2);

        dao.truncateTable();
        dao.insertList(list);

        final JdbcTemplate template = new JdbcTemplate(ds);

        template.execute(IndexedDocument.TRUNCATE_TABLE_QUERY);

        dao.updateNewDocuments("test-share");

        final IndexedDocument idoc1 = new IndexedDocument();

        template.query(IndexedDocument.SELECT_BY_ID_QUERY,
                new PreparedStatementSetter() {

                    @Override
                    public void setValues(final PreparedStatement ps)
                            throws SQLException {

                        ps.setString(1, MD5.toHexString(fileId1));
                    }

                }, new IndexedDocumentExtractor(idoc1));

        assertEquals(IndexedDocument.INDEX_STATE_NEW, idoc1
                .getIndexState());

        final IndexedDocument idoc2 = new IndexedDocument();

        template.query(IndexedDocument.SELECT_BY_ID_QUERY,
                new PreparedStatementSetter() {

                    @Override
                    public void setValues(final PreparedStatement ps)
                            throws SQLException {

                        ps.setString(1, MD5.toHexString(fileId2));
                    }

                }, new IndexedDocumentExtractor(idoc2));

        assertEquals(IndexedDocument.INDEX_STATE_NEW, idoc2
                .getIndexState());
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.DocumentPathDaoImpl# updateModifiedDocuments(java.lang.String)}
     * 
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    @Test
    public final void testUpdateModifiedDocuments()
            throws NoSuchAlgorithmException {

        final long now = new Date().getTime();
        final long later = new Date().getTime() + 10000;

        final List<DocumentPath> list = new ArrayList<DocumentPath>();

        final byte[] fileId1 = MD5.md5Digest("/testdir/filename_a.pdf");
        final byte[] fileId2 = MD5.md5Digest("/testdir/filename_b.pdf");

        final DocumentPath doc1 = new DocumentPath();
        doc1.setContentType("application/pdf");
        doc1.setFileName("filename_a.pdf");
        doc1.setId(fileId1);
        doc1.setLastModifiedTime(now);
        doc1.setLocalPath("/testdir/filename_a.pdf");
        doc1.setPath("/testdir/filename_a.pdf");
        doc1.setShareName("test-share");
        doc1.setUri("file:///testdir/filename_a.pdf");

        list.add(doc1);

        final DocumentPath doc2 = new DocumentPath();
        doc2.setContentType("application/pdf");
        doc2.setFileName("filename_b.pdf");
        doc2.setId(fileId2);
        doc2.setLastModifiedTime(now);
        doc2.setLocalPath("/testdir/filename_b.pdf");
        doc2.setPath("/testdir/filename_b.pdf");
        doc2.setShareName("test-share");
        doc2.setUri("file:///testdir/filename_b.pdf");

        list.add(doc2);

        final List<IndexedDocument> idocList =
                DocumentPath.toIndexedDocumentList(list,
                        IndexedDocument.INDEX_STATE_INDEXED);

        list.get(0).setLastModifiedTime(later);

        assertTrue(list.get(0).getLastModifiedTime() > (idocList.get(0)
                .getLastModifiedTime()));

        dao.truncateTable();
        dao.insertList(list);

        final JdbcTemplate template = new JdbcTemplate(ds);

        template.execute(IndexedDocument.TRUNCATE_TABLE_QUERY);

        template.batchUpdate(IndexedDocument.INSERT_DOCUMENT_QUERY,
                new IndexedDocumentBatchInsertSetter(idocList));

        dao.updateModifiedDocuments("test-share");

        final IndexedDocument idoc1 = new IndexedDocument();

        template.query(IndexedDocument.SELECT_BY_ID_QUERY,
                new PreparedStatementSetter() {

                    @Override
                    public void setValues(final PreparedStatement ps)
                            throws SQLException {

                        ps.setString(1, MD5.toHexString(fileId1));
                    }

                }, new IndexedDocumentExtractor(idoc1));

        assertEquals(IndexedDocument.INDEX_STATE_MODIFIED, idoc1
                .getIndexState());

        assertEquals(later, idoc1.getLastModifiedTime());

        final IndexedDocument idoc2 = new IndexedDocument();

        template.query(IndexedDocument.SELECT_BY_ID_QUERY,
                new PreparedStatementSetter() {

                    @Override
                    public void setValues(final PreparedStatement ps)
                            throws SQLException {

                        ps.setString(1, MD5.toHexString(fileId2));
                    }

                }, new IndexedDocumentExtractor(idoc2));

        assertEquals(IndexedDocument.INDEX_STATE_INDEXED, idoc2
                .getIndexState());

        assertEquals(now, idoc2.getLastModifiedTime());
    }
}
