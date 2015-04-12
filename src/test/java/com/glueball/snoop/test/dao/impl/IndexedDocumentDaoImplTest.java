/**
 * 
 */
package com.glueball.snoop.test.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bouncycastle.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.glueball.snoop.dao.IndexedDocumentDao;
import com.glueball.snoop.dao.impl.IndexedDocumentDaoImpl;
import com.glueball.snoop.entity.IndexedDocument;
import com.glueball.snoop.util.MD5;

/**
 * JUnit testcases for the IndexedDocumentDaoImpl class.
 *
 * @author karesz
 */
public class IndexedDocumentDaoImplTest {

    /**
     * Data access object for DocumentPaths.
     */
    private IndexedDocumentDao dao;

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

        this.dao = ctx.getBean(IndexedDocumentDaoImpl.class);

        ds = (DriverManagerDataSource) ctx.getBean("h2DataSource");

        dao.createTable();
        dao.truncateTable();
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.IndexedDocumentDaoImpl#insertOne(com.glueball.snoop.entity.IndexedDocument)}
     * .
     *
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    @Test
    public final void testInsertOne() throws NoSuchAlgorithmException {

        final byte[] fileId1 = MD5.md5Digest("/testdir/filename_a.pdf");
        final byte[] fileId2 = MD5.md5Digest("/testdir/filename_b.pdf");

        final IndexedDocument idoc1 = new IndexedDocument();
        idoc1.setContentType("application/pdf");
        idoc1.setFileName("filename_a.pdf");
        idoc1.setId(fileId1);
        idoc1.setLastModifiedTime(new Date().getTime());
        idoc1.setLocalPath("/testdir/filename_a.pdf");
        idoc1.setPath("/testdir/filename_a.pdf");
        idoc1.setShareName("test-share");
        idoc1.setUri("file:///testdir/filename_a.pdf");
        idoc1.setLastIndexedTime(new Date().getTime());
        idoc1.setLock(0);
        idoc1.setLockTime(0L);

        dao.truncateTable();
        dao.insertOne(idoc1);

        assertEquals(1, dao.rowNum());
        assertEquals(idoc1, dao.findById(MD5.toHexString(fileId1)));

        final IndexedDocument idoc2 = new IndexedDocument();
        idoc2.setContentType("application/pdf");
        idoc2.setFileName("filename_b.pdf");
        idoc2.setId(fileId2);
        idoc2.setLastModifiedTime(new Date().getTime());
        idoc2.setLocalPath("/testdir/filename_b.pdf");
        idoc2.setPath("/testdir/filename_b.pdf");
        idoc2.setShareName("test-share");
        idoc2.setUri("file:///testdir/filename_b.pdf");
        idoc2.setLastIndexedTime(new Date().getTime());
        idoc2.setLock(0);
        idoc2.setLockTime(0L);

        dao.insertOne(idoc2);

        assertEquals(2, dao.rowNum());
        assertEquals(idoc2, dao.findById(MD5.toHexString(fileId2)));
        assertEquals(idoc1, dao.findById(MD5.toHexString(fileId1)));

    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.IndexedDocumentDaoImpl#insertList(java.util.List)}
     * .
     *
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    @Test
    public final void testInsertList() throws NoSuchAlgorithmException {

        final byte[] fileId1 = MD5.md5Digest("/testdir/filename_a.pdf");
        final byte[] fileId2 = MD5.md5Digest("/testdir/filename_b.pdf");
        final byte[] fileId3 = MD5.md5Digest("/testdir/filename_c.pdf");

        final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>();

        final IndexedDocument idoc1 = new IndexedDocument();
        idoc1.setContentType("application/pdf");
        idoc1.setFileName("filename_a.pdf");
        idoc1.setId(fileId1);
        idoc1.setLastModifiedTime(new Date().getTime());
        idoc1.setLocalPath("/testdir/filename_a.pdf");
        idoc1.setPath("/testdir/filename_a.pdf");
        idoc1.setShareName("test-share");
        idoc1.setUri("file:///testdir/filename_a.pdf");
        idoc1.setLastIndexedTime(new Date().getTime());
        idoc1.setLock(0);
        idoc1.setLockTime(0L);
        idocList.add(idoc1);

        final IndexedDocument idoc2 = new IndexedDocument();
        idoc2.setContentType("application/pdf");
        idoc2.setFileName("filename_b.pdf");
        idoc2.setId(fileId2);
        idoc2.setLastModifiedTime(new Date().getTime());
        idoc2.setLocalPath("/testdir/filename_b.pdf");
        idoc2.setPath("/testdir/filename_b.pdf");
        idoc2.setShareName("test-share");
        idoc2.setUri("file:///testdir/filename_b.pdf");
        idoc2.setLastIndexedTime(new Date().getTime());
        idoc2.setLock(0);
        idoc2.setLockTime(0L);
        idocList.add(idoc2);

        final IndexedDocument idoc3 = new IndexedDocument();
        idoc3.setContentType("application/pdf");
        idoc3.setFileName("filename_c.pdf");
        idoc3.setId(fileId3);
        idoc3.setLastModifiedTime(new Date().getTime());
        idoc3.setLocalPath("/testdir/filename_c.pdf");
        idoc3.setPath("/testdir/filename_c.pdf");
        idoc3.setShareName("test-share");
        idoc3.setUri("file:///testdir/filename_c.pdf");
        idoc3.setLastIndexedTime(new Date().getTime());
        idoc3.setLock(0);
        idoc3.setLockTime(0L);
        idocList.add(idoc3);

        dao.truncateTable();
        dao.insertList(idocList);

        assertEquals(3, dao.rowNum());
        assertEquals(idoc1, dao.findById(MD5.toHexString(fileId1)));
        assertEquals(idoc2, dao.findById(MD5.toHexString(fileId2)));
        assertEquals(idoc3, dao.findById(MD5.toHexString(fileId3)));
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.IndexedDocumentDaoImpl#findById(java.lang.String)}
     * .
     *
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    @Test
    public final void testFindById() throws NoSuchAlgorithmException {

        final byte[] fileId = MD5.md5Digest("/testdir/filename_a.pdf");

        final IndexedDocument idoc = new IndexedDocument();
        idoc.setContentType("application/pdf");
        idoc.setFileName("filename_a.pdf");
        idoc.setId(fileId);
        idoc.setLastModifiedTime(new Date().getTime());
        idoc.setLocalPath("/testdir/filename_a.pdf");
        idoc.setPath("/testdir/filename_a.pdf");
        idoc.setShareName("test-share");
        idoc.setUri("file:///testdir/filename_a.pdf");
        idoc.setLastIndexedTime(new Date().getTime());
        idoc.setLock(0);
        idoc.setLockTime(0L);

        dao.truncateTable();
        dao.insertOne(idoc);

        assertEquals(idoc, dao.findById(MD5.toHexString(fileId)));
        assertEquals(1, dao.rowNum());
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.IndexedDocumentDaoImpl#createTable()}.
     *
     * @throws SQLException
     *             on any SQL error.
     */
    @Test
    public final void testCreateTable() throws SQLException {

        boolean tableExists = false;

        dao.dropTable();
        dao.createTable();

        try (final Connection conn = ds.getConnection()) {
            try (final ResultSet rset =
                    conn.getMetaData().getTables(null, null,
                            "INDEXED_DOCUMENT",
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
     * {@link com.glueball.snoop.dao.impl.IndexedDocumentDaoImpl#dropTable()}.
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
                    conn.getMetaData().getTables(null, null,
                            "INDEXED_DOCUMENT",
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
     * {@link com.glueball.snoop.dao.impl.IndexedDocumentDaoImpl#rowNum()}.
     * 
     * @throws NoSuchAlgorithmException
     */
    @Test
    public final void testRowNum() throws NoSuchAlgorithmException {

        final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>();
        for (int i = 0; i < 100; i++) {
            final IndexedDocument idoc = new IndexedDocument();
            idoc.setId(MD5.md5Digest("" + i));
            idocList.add(idoc);
        }

        dao.truncateTable();
        dao.insertList(idocList);
        assertEquals(100, dao.rowNum());
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.IndexedDocumentDaoImpl#deleteById(java.lang.String)}
     * .
     * 
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    @Test
    public final void testDeleteById() throws NoSuchAlgorithmException {

        final byte[] fileId = MD5.md5Digest("/testdir/filename_a.pdf");

        final IndexedDocument idoc = new IndexedDocument();
        idoc.setContentType("application/pdf");
        idoc.setFileName("filename_a.pdf");
        idoc.setId(fileId);
        idoc.setLastModifiedTime(new Date().getTime());
        idoc.setLocalPath("/testdir/filename_a.pdf");
        idoc.setPath("/testdir/filename_a.pdf");
        idoc.setShareName("test-share");
        idoc.setUri("file:///testdir/filename_a.pdf");
        idoc.setLastIndexedTime(new Date().getTime());
        idoc.setLock(0);
        idoc.setLockTime(0L);

        dao.truncateTable();
        dao.insertOne(idoc);

        assertEquals(idoc, dao.findById(MD5.toHexString(fileId)));
        assertEquals(1, dao.rowNum());

        dao.deleteById(MD5.toHexString(fileId));

        assertEquals(null, dao.findById(MD5.toHexString(fileId)));
        assertEquals(0, dao.rowNum());
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.IndexedDocumentDaoImpl#truncateTable()}
     * .
     * 
     * @throws NoSuchAlgorithmException
     */
    @Test
    public final void testTruncateTable() throws NoSuchAlgorithmException {

        dao.truncateTable();

        final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>();
        for (int i = 0; i < 100; i++) {
            final IndexedDocument idoc = new IndexedDocument();
            idoc.setId(MD5.md5Digest("" + i));
            idocList.add(idoc);
        }

        dao.insertList(idocList);
        assertEquals(100, dao.rowNum());

        dao.truncateTable();
        assertEquals(0, dao.rowNum());
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.IndexedDocumentDaoImpl#selectAll()}.
     *
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    @Test
    public final void testSelectAll() throws NoSuchAlgorithmException {

        final byte[] fileId1 = MD5.md5Digest("/testdir/filename_a.pdf");
        final byte[] fileId2 = MD5.md5Digest("/testdir/filename_b.pdf");
        final byte[] fileId3 = MD5.md5Digest("/testdir/filename_c.pdf");

        final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>();

        final IndexedDocument idoc1 = new IndexedDocument();
        idoc1.setContentType("application/pdf");
        idoc1.setFileName("filename_a.pdf");
        idoc1.setId(fileId1);
        idoc1.setLastModifiedTime(new Date().getTime());
        idoc1.setLocalPath("/testdir/filename_a.pdf");
        idoc1.setPath("/testdir/filename_a.pdf");
        idoc1.setShareName("test-share");
        idoc1.setUri("file:///testdir/filename_a.pdf");
        idoc1.setLastIndexedTime(new Date().getTime());
        idoc1.setLock(0);
        idoc1.setLockTime(0L);
        idocList.add(idoc1);

        final IndexedDocument idoc2 = new IndexedDocument();
        idoc2.setContentType("application/pdf");
        idoc2.setFileName("filename_b.pdf");
        idoc2.setId(fileId2);
        idoc2.setLastModifiedTime(new Date().getTime());
        idoc2.setLocalPath("/testdir/filename_b.pdf");
        idoc2.setPath("/testdir/filename_b.pdf");
        idoc2.setShareName("test-share");
        idoc2.setUri("file:///testdir/filename_b.pdf");
        idoc2.setLastIndexedTime(new Date().getTime());
        idoc2.setLock(0);
        idoc2.setLockTime(0L);
        idocList.add(idoc2);

        final IndexedDocument idoc3 = new IndexedDocument();
        idoc3.setContentType("application/pdf");
        idoc3.setFileName("filename_c.pdf");
        idoc3.setId(fileId3);
        idoc3.setLastModifiedTime(new Date().getTime());
        idoc3.setLocalPath("/testdir/filename_c.pdf");
        idoc3.setPath("/testdir/filename_c.pdf");
        idoc3.setShareName("test-share");
        idoc3.setUri("file:///testdir/filename_c.pdf");
        idoc3.setLastIndexedTime(new Date().getTime());
        idoc3.setLock(0);
        idoc3.setLockTime(0L);
        idocList.add(idoc3);

        dao.truncateTable();
        dao.insertList(idocList);

        final List<IndexedDocument> results = dao.selectAll();

        assertEquals(3, results.size());
        assertEquals(idoc1, results.get(0));
        assertEquals(idoc2, results.get(1));
        assertEquals(idoc3, results.get(2));
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.IndexedDocumentDaoImpl#deleteByIds(java.util.List)}
     * .
     * 
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    @Test
    public final void testDeleteByIds() throws NoSuchAlgorithmException {

        final byte[] fileId1 = MD5.md5Digest("/testdir/filename_a.pdf");
        final byte[] fileId2 = MD5.md5Digest("/testdir/filename_b.pdf");
        final byte[] fileId3 = MD5.md5Digest("/testdir/filename_c.pdf");

        final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>();

        final IndexedDocument idoc1 = new IndexedDocument();
        idoc1.setContentType("application/pdf");
        idoc1.setFileName("filename_a.pdf");
        idoc1.setId(fileId1);
        idoc1.setLastModifiedTime(new Date().getTime());
        idoc1.setLocalPath("/testdir/filename_a.pdf");
        idoc1.setPath("/testdir/filename_a.pdf");
        idoc1.setShareName("test-share");
        idoc1.setUri("file:///testdir/filename_a.pdf");
        idoc1.setLastIndexedTime(new Date().getTime());
        idoc1.setLock(0);
        idoc1.setLockTime(0L);
        idocList.add(idoc1);

        final IndexedDocument idoc2 = new IndexedDocument();
        idoc2.setContentType("application/pdf");
        idoc2.setFileName("filename_b.pdf");
        idoc2.setId(fileId2);
        idoc2.setLastModifiedTime(new Date().getTime());
        idoc2.setLocalPath("/testdir/filename_b.pdf");
        idoc2.setPath("/testdir/filename_b.pdf");
        idoc2.setShareName("test-share");
        idoc2.setUri("file:///testdir/filename_b.pdf");
        idoc2.setLastIndexedTime(new Date().getTime());
        idoc2.setLock(0);
        idoc2.setLockTime(0L);
        idocList.add(idoc2);

        final IndexedDocument idoc3 = new IndexedDocument();
        idoc3.setContentType("application/pdf");
        idoc3.setFileName("filename_c.pdf");
        idoc3.setId(fileId3);
        idoc3.setLastModifiedTime(new Date().getTime());
        idoc3.setLocalPath("/testdir/filename_c.pdf");
        idoc3.setPath("/testdir/filename_c.pdf");
        idoc3.setShareName("test-share");
        idoc3.setUri("file:///testdir/filename_c.pdf");
        idoc3.setLastIndexedTime(new Date().getTime());
        idoc3.setLock(0);
        idoc3.setLockTime(0L);
        idocList.add(idoc3);

        dao.truncateTable();
        dao.insertList(idocList);

        assertEquals(3, dao.rowNum());

        final List<String> deleteIdList = new ArrayList<String>();
        deleteIdList.add(MD5.toHexString(fileId1));
        deleteIdList.add(MD5.toHexString(fileId2));

        dao.deleteByIds(deleteIdList);

        assertEquals(1, dao.rowNum());
        assertEquals(null, dao.findById(MD5.toHexString(fileId1)));
        assertEquals(null, dao.findById(MD5.toHexString(fileId2)));
        assertEquals(idoc3, dao.findById(MD5.toHexString(fileId3)));
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.IndexedDocumentDaoImpl#lockDocuments(int)}
     * .
     * 
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    @Test
    public final void testLockDocuments() throws NoSuchAlgorithmException {

        final byte[] fileId1 = MD5.md5Digest("/testdir/filename_a.pdf");
        final byte[] fileId2 = MD5.md5Digest("/testdir/filename_b.pdf");
        final byte[] fileId3 = MD5.md5Digest("/testdir/filename_c.pdf");

        final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>();

        final IndexedDocument idoc1 = new IndexedDocument();
        idoc1.setContentType("application/pdf");
        idoc1.setFileName("filename_a.pdf");
        idoc1.setId(fileId1);
        idoc1.setLastModifiedTime(new Date().getTime());
        idoc1.setLocalPath("/testdir/filename_a.pdf");
        idoc1.setPath("/testdir/filename_a.pdf");
        idoc1.setShareName("test-share");
        idoc1.setUri("file:///testdir/filename_a.pdf");
        idoc1.setLastIndexedTime(new Date().getTime());
        idoc1.setLock(0);
        idoc1.setLockTime(0L);
        idocList.add(idoc1);

        final IndexedDocument idoc2 = new IndexedDocument();
        idoc2.setContentType("application/pdf");
        idoc2.setFileName("filename_b.pdf");
        idoc2.setId(fileId2);
        idoc2.setLastModifiedTime(new Date().getTime());
        idoc2.setLocalPath("/testdir/filename_b.pdf");
        idoc2.setPath("/testdir/filename_b.pdf");
        idoc2.setShareName("test-share");
        idoc2.setUri("file:///testdir/filename_b.pdf");
        idoc2.setLastIndexedTime(new Date().getTime());
        idoc2.setLock(0);
        idoc2.setLockTime(0L);
        idocList.add(idoc2);

        final IndexedDocument idoc3 = new IndexedDocument();
        idoc3.setContentType("application/pdf");
        idoc3.setFileName("filename_c.pdf");
        idoc3.setId(fileId3);
        idoc3.setLastModifiedTime(new Date().getTime());
        idoc3.setLocalPath("/testdir/filename_c.pdf");
        idoc3.setPath("/testdir/filename_c.pdf");
        idoc3.setShareName("test-share");
        idoc3.setUri("file:///testdir/filename_c.pdf");
        idoc3.setLastIndexedTime(new Date().getTime());
        idoc3.setLock(0);
        idoc3.setLockTime(0L);
        idocList.add(idoc3);

        dao.truncateTable();
        dao.insertList(idocList);

        assertEquals(3, dao.rowNum());

        final long lock = dao.lockDocuments(3);

        assertEquals(lock, dao.findById(MD5.toHexString(fileId1)).getLock());
        assertEquals(lock, dao.findById(MD5.toHexString(fileId2)).getLock());
        assertEquals(lock, dao.findById(MD5.toHexString(fileId3)).getLock());

        assertTrue(dao.findById(
                MD5.toHexString(fileId1)).getLockTime() != 0L);
        assertTrue(dao.findById(
                MD5.toHexString(fileId2)).getLockTime() != 0L);
        assertTrue(dao.findById(
                MD5.toHexString(fileId3)).getLockTime() != 0L);
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.IndexedDocumentDaoImpl#getByLock(long)}
     * .
     * 
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    @Test
    public final void testGetByLock() throws NoSuchAlgorithmException {

        final byte[] fileId1 = MD5.md5Digest("/testdir/filename_a.pdf");
        final byte[] fileId2 = MD5.md5Digest("/testdir/filename_b.pdf");
        final byte[] fileId3 = MD5.md5Digest("/testdir/filename_c.pdf");

        final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>();

        final IndexedDocument idoc1 = new IndexedDocument();
        idoc1.setContentType("application/pdf");
        idoc1.setFileName("filename_a.pdf");
        idoc1.setId(fileId1);
        idoc1.setLastModifiedTime(new Date().getTime());
        idoc1.setLocalPath("/testdir/filename_a.pdf");
        idoc1.setPath("/testdir/filename_a.pdf");
        idoc1.setShareName("test-share");
        idoc1.setUri("file:///testdir/filename_a.pdf");
        idoc1.setLastIndexedTime(new Date().getTime());
        idoc1.setLock(0);
        idoc1.setLockTime(0L);
        idocList.add(idoc1);

        final IndexedDocument idoc2 = new IndexedDocument();
        idoc2.setContentType("application/pdf");
        idoc2.setFileName("filename_b.pdf");
        idoc2.setId(fileId2);
        idoc2.setLastModifiedTime(new Date().getTime());
        idoc2.setLocalPath("/testdir/filename_b.pdf");
        idoc2.setPath("/testdir/filename_b.pdf");
        idoc2.setShareName("test-share");
        idoc2.setUri("file:///testdir/filename_b.pdf");
        idoc2.setLastIndexedTime(new Date().getTime());
        idoc2.setLock(0);
        idoc2.setLockTime(0L);
        idocList.add(idoc2);

        final IndexedDocument idoc3 = new IndexedDocument();
        idoc3.setContentType("application/pdf");
        idoc3.setFileName("filename_c.pdf");
        idoc3.setId(fileId3);
        idoc3.setLastModifiedTime(new Date().getTime());
        idoc3.setLocalPath("/testdir/filename_c.pdf");
        idoc3.setPath("/testdir/filename_c.pdf");
        idoc3.setShareName("test-share");
        idoc3.setUri("file:///testdir/filename_c.pdf");
        idoc3.setLastIndexedTime(new Date().getTime());
        idoc3.setLock(0);
        idoc3.setLockTime(0L);
        idocList.add(idoc3);

        dao.truncateTable();
        dao.insertList(idocList);

        assertEquals(3, dao.rowNum());

        final long lock = dao.lockDocuments(3);
        final List<IndexedDocument> lockedList = dao.getByLock(lock);

        assertEquals(3, lockedList.size());

        assertTrue(Arrays.areEqual(idoc1.getId(), lockedList.get(0).getId()));
        assertTrue(Arrays.areEqual(idoc2.getId(), lockedList.get(1).getId()));
        assertTrue(Arrays.areEqual(idoc3.getId(), lockedList.get(2).getId()));

        assertEquals(lock, lockedList.get(0).getLock());
        assertEquals(lock, lockedList.get(1).getLock());
        assertEquals(lock, lockedList.get(2).getLock());

        assertTrue(lockedList.get(0).getLockTime() != 0L);
        assertTrue(lockedList.get(1).getLockTime() != 0L);
        assertTrue(lockedList.get(2).getLockTime() != 0L);
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.IndexedDocumentDaoImpl#updateState(java.util.List)}
     * .
     * 
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    @Test
    public final void testUpdateState() throws NoSuchAlgorithmException {

        final byte[] fileId1 = MD5.md5Digest("/testdir/filename_a.pdf");
        final byte[] fileId2 = MD5.md5Digest("/testdir/filename_b.pdf");
        final byte[] fileId3 = MD5.md5Digest("/testdir/filename_c.pdf");

        final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>();

        final IndexedDocument idoc1 = new IndexedDocument();
        idoc1.setContentType("application/pdf");
        idoc1.setFileName("filename_a.pdf");
        idoc1.setId(fileId1);
        idoc1.setLastModifiedTime(new Date().getTime());
        idoc1.setLocalPath("/testdir/filename_a.pdf");
        idoc1.setPath("/testdir/filename_a.pdf");
        idoc1.setShareName("test-share");
        idoc1.setUri("file:///testdir/filename_a.pdf");
        idoc1.setLastIndexedTime(new Date().getTime());
        idoc1.setLock(0);
        idoc1.setLockTime(0L);
        idoc1.setIndexState(IndexedDocument.INDEX_STATE_NEW);
        idocList.add(idoc1);

        final IndexedDocument idoc2 = new IndexedDocument();
        idoc2.setContentType("application/pdf");
        idoc2.setFileName("filename_b.pdf");
        idoc2.setId(fileId2);
        idoc2.setLastModifiedTime(new Date().getTime());
        idoc2.setLocalPath("/testdir/filename_b.pdf");
        idoc2.setPath("/testdir/filename_b.pdf");
        idoc2.setShareName("test-share");
        idoc2.setUri("file:///testdir/filename_b.pdf");
        idoc2.setLastIndexedTime(new Date().getTime());
        idoc2.setLock(0);
        idoc2.setLockTime(0L);
        idoc2.setIndexState(IndexedDocument.INDEX_STATE_NEW);
        idocList.add(idoc2);

        final IndexedDocument idoc3 = new IndexedDocument();
        idoc3.setContentType("application/pdf");
        idoc3.setFileName("filename_c.pdf");
        idoc3.setId(fileId3);
        idoc3.setLastModifiedTime(new Date().getTime());
        idoc3.setLocalPath("/testdir/filename_c.pdf");
        idoc3.setPath("/testdir/filename_c.pdf");
        idoc3.setShareName("test-share");
        idoc3.setUri("file:///testdir/filename_c.pdf");
        idoc3.setLastIndexedTime(new Date().getTime());
        idoc3.setLock(0);
        idoc3.setLockTime(0L);
        idoc3.setIndexState(IndexedDocument.INDEX_STATE_NEW);
        idocList.add(idoc3);

        dao.truncateTable();
        dao.insertList(idocList);

        assertEquals(3, dao.rowNum());

        assertEquals(IndexedDocument.INDEX_STATE_NEW, dao.findById(
                MD5.toHexString(fileId1))
                .getIndexState());
        assertEquals(IndexedDocument.INDEX_STATE_NEW, dao.findById(
                MD5.toHexString(fileId2))
                .getIndexState());
        assertEquals(IndexedDocument.INDEX_STATE_NEW, dao.findById(
                MD5.toHexString(fileId3))
                .getIndexState());

        idoc1.setIndexState(IndexedDocument.INDEX_STATE_INDEXED);
        idoc2.setIndexState(IndexedDocument.INDEX_STATE_INDEXED);
        idoc3.setIndexState(IndexedDocument.INDEX_STATE_INDEXED);
        dao.updateState(idocList);

        assertEquals(IndexedDocument.INDEX_STATE_INDEXED, dao.findById(
                MD5.toHexString(fileId1))
                .getIndexState());
        assertEquals(IndexedDocument.INDEX_STATE_INDEXED, dao.findById(
                MD5.toHexString(fileId2))
                .getIndexState());
        assertEquals(IndexedDocument.INDEX_STATE_INDEXED, dao.findById(
                MD5.toHexString(fileId3))
                .getIndexState());
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.impl.IndexedDocumentDaoImpl#unLockUpdateState(java.util.List)}
     * .
     * 
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    @Test
    public final void testUnLockUpdateState() throws NoSuchAlgorithmException {

        final byte[] fileId1 = MD5.md5Digest("/testdir/filename_a.pdf");
        final byte[] fileId2 = MD5.md5Digest("/testdir/filename_b.pdf");
        final byte[] fileId3 = MD5.md5Digest("/testdir/filename_c.pdf");

        final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>();

        final IndexedDocument idoc1 = new IndexedDocument();
        idoc1.setContentType("application/pdf");
        idoc1.setFileName("filename_a.pdf");
        idoc1.setId(fileId1);
        idoc1.setLastModifiedTime(new Date().getTime());
        idoc1.setLocalPath("/testdir/filename_a.pdf");
        idoc1.setPath("/testdir/filename_a.pdf");
        idoc1.setShareName("test-share");
        idoc1.setUri("file:///testdir/filename_a.pdf");
        idoc1.setLastIndexedTime(new Date().getTime());
        idoc1.setLock(0);
        idoc1.setLockTime(0L);
        idoc1.setIndexState(IndexedDocument.INDEX_STATE_NEW);
        idocList.add(idoc1);

        final IndexedDocument idoc2 = new IndexedDocument();
        idoc2.setContentType("application/pdf");
        idoc2.setFileName("filename_b.pdf");
        idoc2.setId(fileId2);
        idoc2.setLastModifiedTime(new Date().getTime());
        idoc2.setLocalPath("/testdir/filename_b.pdf");
        idoc2.setPath("/testdir/filename_b.pdf");
        idoc2.setShareName("test-share");
        idoc2.setUri("file:///testdir/filename_b.pdf");
        idoc2.setLastIndexedTime(new Date().getTime());
        idoc2.setLock(0);
        idoc2.setLockTime(0L);
        idoc2.setIndexState(IndexedDocument.INDEX_STATE_NEW);
        idocList.add(idoc2);

        final IndexedDocument idoc3 = new IndexedDocument();
        idoc3.setContentType("application/pdf");
        idoc3.setFileName("filename_c.pdf");
        idoc3.setId(fileId3);
        idoc3.setLastModifiedTime(new Date().getTime());
        idoc3.setLocalPath("/testdir/filename_c.pdf");
        idoc3.setPath("/testdir/filename_c.pdf");
        idoc3.setShareName("test-share");
        idoc3.setUri("file:///testdir/filename_c.pdf");
        idoc3.setLastIndexedTime(new Date().getTime());
        idoc3.setLock(0);
        idoc3.setLockTime(0L);
        idoc3.setIndexState(IndexedDocument.INDEX_STATE_NEW);
        idocList.add(idoc3);

        dao.truncateTable();
        dao.insertList(idocList);

        assertEquals(3, dao.rowNum());

        assertEquals(IndexedDocument.INDEX_STATE_NEW, dao.findById(
                MD5.toHexString(fileId1))
                .getIndexState());
        assertEquals(IndexedDocument.INDEX_STATE_NEW, dao.findById(
                MD5.toHexString(fileId2))
                .getIndexState());
        assertEquals(IndexedDocument.INDEX_STATE_NEW, dao.findById(
                MD5.toHexString(fileId3))
                .getIndexState());

        final long lock = dao.lockDocuments(3);
        List<IndexedDocument> lockedList = dao.getByLock(lock);

        assertEquals(3, lockedList.size());

        assertTrue(Arrays.areEqual(idoc1.getId(), lockedList.get(0).getId()));
        assertTrue(Arrays.areEqual(idoc2.getId(), lockedList.get(1).getId()));
        assertTrue(Arrays.areEqual(idoc3.getId(), lockedList.get(2).getId()));

        assertEquals(lock, lockedList.get(0).getLock());
        assertEquals(lock, lockedList.get(1).getLock());
        assertEquals(lock, lockedList.get(2).getLock());

        assertTrue(lockedList.get(0).getLockTime() != 0L);
        assertTrue(lockedList.get(1).getLockTime() != 0L);
        assertTrue(lockedList.get(2).getLockTime() != 0L);

        idoc1.setIndexState(IndexedDocument.INDEX_STATE_INDEXED);
        idoc2.setIndexState(IndexedDocument.INDEX_STATE_INDEXED);
        idoc3.setIndexState(IndexedDocument.INDEX_STATE_INDEXED);

        dao.unLockUpdateState(idocList);

        assertEquals(IndexedDocument.INDEX_STATE_INDEXED, dao.findById(
                MD5.toHexString(fileId1))
                .getIndexState());
        assertEquals(IndexedDocument.INDEX_STATE_INDEXED, dao.findById(
                MD5.toHexString(fileId2))
                .getIndexState());
        assertEquals(IndexedDocument.INDEX_STATE_INDEXED, dao.findById(
                MD5.toHexString(fileId3))
                .getIndexState());

        lockedList = dao.selectAll();
        assertEquals(3, lockedList.size());

        assertEquals(0, lockedList.get(0).getLock());
        assertEquals(0, lockedList.get(1).getLock());
        assertEquals(0, lockedList.get(2).getLock());

        assertTrue(lockedList.get(0).getLockTime() == 0L);
        assertTrue(lockedList.get(1).getLockTime() == 0L);
        assertTrue(lockedList.get(2).getLockTime() == 0L);
    }
}
