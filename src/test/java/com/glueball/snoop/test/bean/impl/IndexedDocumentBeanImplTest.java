/**
 * 
 */
package com.glueball.snoop.test.bean.impl;

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
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.glueball.snoop.bean.IndexedDocumentBean;
import com.glueball.snoop.bean.impl.IndexedDocumentBeanImpl;
import com.glueball.snoop.dao.extractor.IndexedDocumentExtractor;
import com.glueball.snoop.dao.setter.IndexedDocumentBatchInsertSetter;
import com.glueball.snoop.entity.IndexedDocument;
import com.glueball.snoop.util.MD5;

/**
 * @author karesz
 */
public class IndexedDocumentBeanImplTest {

    /**
     * Spring application context.
     */
    private ApplicationContext ctx;

    /**
     * Database data source.
     */
    private DriverManagerDataSource ds;

    /**
     * Spring data jdbc template.
     */
    private JdbcTemplate template;

    /**
     * IndexedDocumentBean instance.
     */
    private IndexedDocumentBean bean;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        this.ctx = new ClassPathXmlApplicationContext(
                "classpath:spring/application-test.xml");

        ds = (DriverManagerDataSource) ctx.getBean("h2DataSource");

        template = new JdbcTemplate(ds);

        bean = ctx.getBean(IndexedDocumentBean.class);

    }

    /**
     * Test method for
     * {@link com.glueball.snoop.bean.impl.IndexedDocumentBeanImpl#init()}.
     * 
     * @throws SQLException
     *             on any SQL error.
     */
    @Test
    public final void testInit() throws SQLException {

        template.execute(IndexedDocument.DROP_TABLE_QUERY);

        ctx.getBean(IndexedDocumentBeanImpl.class).init();

        boolean indexedDocumentTableExists = false;

        try (final Connection conn = ds.getConnection()) {
            try (final ResultSet rset =
                    conn.getMetaData().getTables(null, null,
                            "INDEXED_DOCUMENT",
                            null)) {
                if (rset.next()) {
                    indexedDocumentTableExists = true;
                }
            }
        }
        assertTrue(indexedDocumentTableExists);
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.bean.impl.IndexedDocumentBeanImpl#unLockUpdateState(java.util.List)}
     * .
     * 
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    @Test
    public final void testUnLockUpdateState() throws NoSuchAlgorithmException {

        final String fileId1 = MD5.md5Digest("/testdir/filename_a.pdf");
        final String fileId2 = MD5.md5Digest("/testdir/filename_b.pdf");
        final String fileId3 = MD5.md5Digest("/testdir/filename_c.pdf");

        final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>();

        final IndexedDocument idoc1 = new IndexedDocument();
        idoc1.setContentType("application/pdf");
        idoc1.setFileName("filename_a.pdf");
        idoc1.setId(fileId1);
        idoc1.setLastModifiedTime(new java.sql.Timestamp(new Date().getTime()));
        idoc1.setLocalPath("/testdir/filename_a.pdf");
        idoc1.setPath("/testdir/filename_a.pdf");
        idoc1.setShareName("test-share");
        idoc1.setUri("file:///testdir/filename_a.pdf");
        idoc1.setLastIndexedTime(new java.sql.Timestamp(new Date().getTime()));
        idoc1.setLock(0);
        idoc1.setLockTime(null);
        idoc1.setIndexState(IndexedDocument.INDEX_STATE_NEW);
        idocList.add(idoc1);

        final IndexedDocument idoc2 = new IndexedDocument();
        idoc2.setContentType("application/pdf");
        idoc2.setFileName("filename_b.pdf");
        idoc2.setId(fileId2);
        idoc2.setLastModifiedTime(new java.sql.Timestamp(new Date().getTime()));
        idoc2.setLocalPath("/testdir/filename_b.pdf");
        idoc2.setPath("/testdir/filename_b.pdf");
        idoc2.setShareName("test-share");
        idoc2.setUri("file:///testdir/filename_b.pdf");
        idoc2.setLastIndexedTime(new java.sql.Timestamp(new Date().getTime()));
        idoc2.setLock(0);
        idoc2.setLockTime(null);
        idoc2.setIndexState(IndexedDocument.INDEX_STATE_NEW);
        idocList.add(idoc2);

        final IndexedDocument idoc3 = new IndexedDocument();
        idoc3.setContentType("application/pdf");
        idoc3.setFileName("filename_c.pdf");
        idoc3.setId(fileId3);
        idoc3.setLastModifiedTime(new java.sql.Timestamp(new Date().getTime()));
        idoc3.setLocalPath("/testdir/filename_c.pdf");
        idoc3.setPath("/testdir/filename_c.pdf");
        idoc3.setShareName("test-share");
        idoc3.setUri("file:///testdir/filename_c.pdf");
        idoc3.setLastIndexedTime(new java.sql.Timestamp(new Date().getTime()));
        idoc3.setLock(0);
        idoc3.setLockTime(null);
        idoc3.setIndexState(IndexedDocument.INDEX_STATE_NEW);
        idocList.add(idoc3);

        template.execute(IndexedDocument.TRUNCATE_TABLE_QUERY);
        template.batchUpdate(IndexedDocument.INSERT_DOCUMENT_QUERY,
                new IndexedDocumentBatchInsertSetter(idocList));

        assertEquals(IndexedDocument.INDEX_STATE_NEW,
                selectIndexedDocumentById(fileId1)
                        .getIndexState());
        assertEquals(IndexedDocument.INDEX_STATE_NEW,
                selectIndexedDocumentById(fileId2)
                        .getIndexState());
        assertEquals(IndexedDocument.INDEX_STATE_NEW,
                selectIndexedDocumentById(fileId3)
                        .getIndexState());

        final long lock = lockDocuments(idocList);

        assertEquals(lock, selectIndexedDocumentById(fileId1).getLock());
        assertEquals(lock, selectIndexedDocumentById(fileId2).getLock());
        assertEquals(lock, selectIndexedDocumentById(fileId3).getLock());

        assertTrue(selectIndexedDocumentById(fileId1).getLockTime() != null);
        assertTrue(selectIndexedDocumentById(fileId2).getLockTime() != null);
        assertTrue(selectIndexedDocumentById(fileId3).getLockTime() != null);

        idoc1.setIndexState(IndexedDocument.INDEX_STATE_INDEXED);
        idoc2.setIndexState(IndexedDocument.INDEX_STATE_INDEXED);
        idoc3.setIndexState(IndexedDocument.INDEX_STATE_INDEXED);

        bean.unLockUpdateState(idocList);

        assertEquals(IndexedDocument.INDEX_STATE_INDEXED,
                selectIndexedDocumentById(fileId1)
                        .getIndexState());
        assertEquals(IndexedDocument.INDEX_STATE_INDEXED,
                selectIndexedDocumentById(fileId2)
                        .getIndexState());
        assertEquals(IndexedDocument.INDEX_STATE_INDEXED,
                selectIndexedDocumentById(fileId3)
                        .getIndexState());

        assertEquals(0, selectIndexedDocumentById(fileId1).getLock());
        assertEquals(0, selectIndexedDocumentById(fileId2).getLock());
        assertEquals(0, selectIndexedDocumentById(fileId3).getLock());

        assertTrue(selectIndexedDocumentById(fileId1).getLockTime() == null);
        assertTrue(selectIndexedDocumentById(fileId2).getLockTime() == null);
        assertTrue(selectIndexedDocumentById(fileId3).getLockTime() == null);
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.bean.impl.IndexedDocumentBeanImpl#haveToIndex(int)}
     * .
     * 
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    @Test
    public final void testHaveToIndex() throws NoSuchAlgorithmException {

        final String fileId1 = MD5.md5Digest("/testdir/filename_a.pdf");
        final String fileId2 = MD5.md5Digest("/testdir/filename_b.pdf");
        final String fileId3 = MD5.md5Digest("/testdir/filename_c.pdf");

        final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>();

        final IndexedDocument idoc1 = new IndexedDocument();
        idoc1.setContentType("application/pdf");
        idoc1.setFileName("filename_a.pdf");
        idoc1.setId(fileId1);
        idoc1.setLastModifiedTime(new java.sql.Timestamp(new Date().getTime()));
        idoc1.setLocalPath("/testdir/filename_a.pdf");
        idoc1.setPath("/testdir/filename_a.pdf");
        idoc1.setShareName("test-share");
        idoc1.setUri("file:///testdir/filename_a.pdf");
        idoc1.setLastIndexedTime(new java.sql.Timestamp(new Date().getTime()));
        idoc1.setLock(0);
        idoc1.setLockTime(null);
        idoc1.setIndexState(IndexedDocument.INDEX_STATE_NEW);
        idocList.add(idoc1);

        final IndexedDocument idoc2 = new IndexedDocument();
        idoc2.setContentType("application/pdf");
        idoc2.setFileName("filename_b.pdf");
        idoc2.setId(fileId2);
        idoc2.setLastModifiedTime(new java.sql.Timestamp(new Date().getTime()));
        idoc2.setLocalPath("/testdir/filename_b.pdf");
        idoc2.setPath("/testdir/filename_b.pdf");
        idoc2.setShareName("test-share");
        idoc2.setUri("file:///testdir/filename_b.pdf");
        idoc2.setLastIndexedTime(new java.sql.Timestamp(new Date().getTime()));
        idoc2.setLock(0);
        idoc2.setLockTime(null);
        idoc2.setIndexState(IndexedDocument.INDEX_STATE_MODIFIED);
        idocList.add(idoc2);

        final IndexedDocument idoc3 = new IndexedDocument();
        idoc3.setContentType("application/pdf");
        idoc3.setFileName("filename_c.pdf");
        idoc3.setId(fileId3);
        idoc3.setLastModifiedTime(new java.sql.Timestamp(new Date().getTime()));
        idoc3.setLocalPath("/testdir/filename_c.pdf");
        idoc3.setPath("/testdir/filename_c.pdf");
        idoc3.setShareName("test-share");
        idoc3.setUri("file:///testdir/filename_c.pdf");
        idoc3.setLastIndexedTime(new java.sql.Timestamp(new Date().getTime()));
        idoc3.setLock(0);
        idoc3.setLockTime(null);
        idoc3.setIndexState(IndexedDocument.INDEX_STATE_DELETED);
        idocList.add(idoc3);

        template.execute(IndexedDocument.TRUNCATE_TABLE_QUERY);
        template.batchUpdate(IndexedDocument.INSERT_DOCUMENT_QUERY,
                new IndexedDocumentBatchInsertSetter(idocList));

        assertEquals(IndexedDocument.INDEX_STATE_NEW,
                selectIndexedDocumentById(fileId1)
                        .getIndexState());
        assertEquals(IndexedDocument.INDEX_STATE_MODIFIED,
                selectIndexedDocumentById(fileId2)
                        .getIndexState());
        assertEquals(IndexedDocument.INDEX_STATE_DELETED,
                selectIndexedDocumentById(fileId3)
                        .getIndexState());

        final List<IndexedDocument> indexList = bean.haveToIndex(3);

        assertEquals(3, indexList.size());
        assertTrue(indexList.get(0).getLock() != 0);
        assertTrue(indexList.get(1).getLock() != 0);
        assertTrue(indexList.get(2).getLock() != 0);

        assertTrue(indexList.get(0).getLockTime() != null);
        assertTrue(indexList.get(1).getLockTime() != null);
        assertTrue(indexList.get(2).getLockTime() != null);

        assertEquals(idoc1.getId(), indexList.get(0).getId());
        assertEquals(idoc2.getId(), indexList.get(1).getId());
        assertEquals(idoc3.getId(), indexList.get(2).getId());
    }

    /**
     * Helper method to select an IndexedDocument from the databse.
     *
     * @param id
     *            the document id.
     * @return the IndexedDocument.
     */
    private final IndexedDocument selectIndexedDocumentById(final String id) {

        final IndexedDocument retVal = new IndexedDocument();

        template.query(IndexedDocument.SELECT_BY_ID_QUERY,
                new PreparedStatementSetter() {

                    @Override
                    public void setValues(final PreparedStatement ps)
                            throws SQLException {

                        ps.setString(1, id);
                    }

                },
                new IndexedDocumentExtractor(retVal));
        return retVal;
    }

    /**
     * Helper method to lock documents.
     * 
     * @return lock id.
     */
    private final long lockDocuments(final List<IndexedDocument> docList) {

        final int lock = new Random().nextInt();

        template.batchUpdate(IndexedDocument.LOCK_DOCUMENTS_QUERY,
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(final PreparedStatement ps,
                            final int i)
                            throws SQLException {

                        ps.setLong(1, lock);
                        ps.setString(2, docList.get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {

                        return docList.size();
                    }
                });
        return lock;
    }

}
