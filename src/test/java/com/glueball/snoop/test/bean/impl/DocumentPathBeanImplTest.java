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
import java.sql.Timestamp;
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

import com.glueball.snoop.bean.DocumentPathBean;
import com.glueball.snoop.bean.impl.DocumentPathBeanImpl;
import com.glueball.snoop.dao.extractor.IndexedDocumentExtractor;
import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.entity.IndexedDocument;
import com.glueball.snoop.util.MD5;

/**
 * @author karesz
 */
public class DocumentPathBeanImplTest {

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
     * DocumentPathBean instance.
     */
    private DocumentPathBean bean;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        this.ctx = new ClassPathXmlApplicationContext(
                "classpath:spring/application-test.xml");

        ds = (DriverManagerDataSource) ctx.getBean("h2DataSource");

        template = new JdbcTemplate(ds);

        bean = ctx.getBean(DocumentPathBean.class);

    }

    /**
     * Test method for
     * {@link com.glueball.snoop.bean.impl.DocumentPathBeanImpl#init()}.
     * 
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     * @throws SQLException
     *             any SQL error.
     */
    @Test
    public final void testInit() throws NoSuchAlgorithmException, SQLException {

        template.execute(DocumentPath.DROP_TABLE_QUERY);

        ctx.getBean(DocumentPathBeanImpl.class).init();

        boolean documentPathTableExists = false;

        try (final Connection conn = ds.getConnection()) {
            try (final ResultSet rset =
                    conn.getMetaData().getTables(null, null, "DOCUMENT_PATH",
                            null)) {
                if (rset.next()) {
                    documentPathTableExists = true;
                }
            }
        }
        assertTrue(documentPathTableExists);
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.bean.impl.DocumentPathBeanImpl#updateDocuments(java.lang.String, java.util.List)}
     * 
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    @Test
    public final void testUpdateDocuments() throws NoSuchAlgorithmException {

        final Timestamp now = new java.sql.Timestamp(new Date().getTime());
        final Timestamp later = new java.sql.Timestamp(
                new Date().getTime() + 10000);

        template.execute(IndexedDocument.TRUNCATE_TABLE_QUERY);
        template.execute(DocumentPath.EMPTY_TABLE_QUERY);

        final List<DocumentPath> list = new ArrayList<DocumentPath>();

        final String fileId1 = MD5.md5Digest("/testdir/filename_a.pdf");
        final String fileId2 = MD5.md5Digest("/testdir/filename_b.pdf");
        final String fileId3 = MD5.md5Digest("/testdir/filename_c.pdf");

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

        final DocumentPath doc3 = new DocumentPath();
        doc3.setContentType("application/pdf");
        doc3.setFileName("filename_c.pdf");
        doc3.setId(fileId3);
        doc3.setLastModifiedTime(now);
        doc3.setLocalPath("/testdir/filename_c.pdf");
        doc3.setPath("/testdir/filename_c.pdf");
        doc3.setShareName("test-share");
        doc3.setUri("file:///testdir/filename_c.pdf");

        list.add(doc3);

        bean.updateDocuments("test-share", list);

        assertEquals(IndexedDocument.INDEX_STATE_NEW,
                selectIndexedDocumentById(fileId1)
                        .getIndexState());
        assertEquals(IndexedDocument.INDEX_STATE_NEW,
                selectIndexedDocumentById(fileId2)
                        .getIndexState());
        assertEquals(IndexedDocument.INDEX_STATE_NEW,
                selectIndexedDocumentById(fileId3)
                        .getIndexState());

        final DocumentPath doc2mod = new DocumentPath();
        doc2mod.setContentType("application/pdf");
        doc2mod.setFileName("filename_b.pdf");
        doc2mod.setId(fileId2);
        doc2mod.setLastModifiedTime(later);
        doc2mod.setLocalPath("/testdir/filename_b.pdf");
        doc2mod.setPath("/testdir/filename_b.pdf");
        doc2mod.setShareName("test-share");
        doc2mod.setUri("file:///testdir/filename_b.pdf");

        list.remove(doc2);
        list.add(doc2mod);

        final DocumentPath doc3mod = new DocumentPath();
        doc3mod.setContentType("application/pdf");
        doc3mod.setFileName("filename_c.pdf");
        doc3mod.setId(fileId3);
        doc3mod.setLastModifiedTime(later);
        doc3mod.setLocalPath("/testdir/filename_c.pdf");
        doc3mod.setPath("/testdir/filename_c.pdf");
        doc3mod.setShareName("test-share");
        doc3mod.setUri("file:///testdir/filename_c.pdf");

        list.remove(doc3);
        list.add(doc3mod);

        bean.updateDocuments("test-share", list);

        assertEquals(IndexedDocument.INDEX_STATE_NEW,
                selectIndexedDocumentById(fileId1)
                        .getIndexState());
        assertEquals(IndexedDocument.INDEX_STATE_MODIFIED,
                selectIndexedDocumentById(fileId2)
                        .getIndexState());
        assertEquals(IndexedDocument.INDEX_STATE_MODIFIED,
                selectIndexedDocumentById(fileId3)
                        .getIndexState());

        assertEquals(later,
                selectIndexedDocumentById(fileId2)
                        .getLastModifiedTime());
        assertEquals(later,
                selectIndexedDocumentById(fileId3)
                        .getLastModifiedTime());

        list.remove(doc1);

        bean.updateDocuments("test-share", list);

        assertEquals(IndexedDocument.INDEX_STATE_DELETED,
                selectIndexedDocumentById(fileId1)
                        .getIndexState());
        assertEquals(IndexedDocument.INDEX_STATE_MODIFIED,
                selectIndexedDocumentById(fileId2)
                        .getIndexState());
        assertEquals(IndexedDocument.INDEX_STATE_MODIFIED,
                selectIndexedDocumentById(fileId3)
                        .getIndexState());

    }

    /**
     * Helper method to get IndexedDocument from the database.
     *
     * @param id
     *            the document id.
     * @return the IndexedDocument selected.
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
}
