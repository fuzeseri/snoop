/**
 * 
 */
package com.glueball.snoop.test.dao.extractor;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.glueball.snoop.dao.extractor.ListIndexedDocumentExtractor;
import com.glueball.snoop.entity.IndexedDocument;
import com.glueball.snoop.util.MD5;

/**
 * @author karesz
 */
public class ListIndexedDocumentExtractorTest {

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.extractor.ListIndexedDocumentExtractor#extractData(java.sql.ResultSet)}
     * .
     * 
     * @throws SQLException
     *             on any SQL error.
     * @throws NoSuchAlgorithmException
     */
    @Test
    public final void testExtractData() throws SQLException,
            NoSuchAlgorithmException {

        final long now = new Date().getTime();

        final IndexedDocument refIdoc = new IndexedDocument();
        refIdoc.setContentType("application/pdf");
        refIdoc.setFileName("filename_a.pdf");
        refIdoc.setId(MD5.md5Digest("id"));
        refIdoc.setLastModifiedTime(now);
        refIdoc.setLocalPath("/testdir/filename_a.pdf");
        refIdoc.setPath("/testdir/filename_a.pdf");
        refIdoc.setShareName("test-share");
        refIdoc.setUri("file:///testdir/filename_a.pdf");
        refIdoc.setLastIndexedTime(now);
        refIdoc.setLock(0);
        refIdoc.setLockTime(0L);
        refIdoc.setIndexState(IndexedDocument.INDEX_STATE_NEW);

        final ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.next()).thenReturn(true, false);

        Mockito.when(rs.getString("id")).thenReturn("id");
        Mockito.when(rs.getString("share_name")).thenReturn("test-share");
        Mockito.when(rs.getString("file_name")).thenReturn("filename_a.pdf");
        Mockito.when(rs.getString("uri")).thenReturn(
                "file:///testdir/filename_a.pdf");
        Mockito.when(rs.getString("path"))
                .thenReturn("/testdir/filename_a.pdf");
        Mockito.when(rs.getString("local_path")).thenReturn(
                "/testdir/filename_a.pdf");
        Mockito.when(rs.getLong("last_modified_time")).thenReturn(now);
        Mockito.when(rs.getLong("last_indexed_time")).thenReturn(now);
        Mockito.when(rs.getString("content_type"))
                .thenReturn("application/pdf");
        Mockito.when(rs.getString("index_state")).thenReturn(
                IndexedDocument.INDEX_STATE_NEW);
        Mockito.when(rs.getInt("lock")).thenReturn(0);
        Mockito.when(rs.getTimestamp("lock_time")).thenReturn(null);

        final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>();

        final ListIndexedDocumentExtractor extractor =
                new ListIndexedDocumentExtractor(idocList);

        extractor.extractData(rs);

        assertEquals(1, idocList.size());
        assertEquals(refIdoc, idocList.get(0));
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.extractor.ListIndexedDocumentExtractor#extractData(java.sql.ResultSet)}
     * .
     * 
     * @throws SQLException
     *             on any SQL error.
     */
    @Test
    public final void testExtractDataNoResult() throws SQLException {

        final Timestamp now = new Timestamp(new Date().getTime());

        final ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.next()).thenReturn(false);

        Mockito.when(rs.getString("id")).thenReturn("id");
        Mockito.when(rs.getString("share_name")).thenReturn("test-share");
        Mockito.when(rs.getString("file_name")).thenReturn("filename_a.pdf");
        Mockito.when(rs.getString("uri")).thenReturn(
                "file:///testdir/filename_a.pdf");
        Mockito.when(rs.getString("path"))
                .thenReturn("/testdir/filename_a.pdf");
        Mockito.when(rs.getString("local_path")).thenReturn(
                "/testdir/filename_a.pdf");
        Mockito.when(rs.getTimestamp("last_modified_time")).thenReturn(now);
        Mockito.when(rs.getTimestamp("last_indexed_time")).thenReturn(now);
        Mockito.when(rs.getString("content_type"))
                .thenReturn("application/pdf");
        Mockito.when(rs.getString("index_state")).thenReturn(
                IndexedDocument.INDEX_STATE_NEW);
        Mockito.when(rs.getInt("lock")).thenReturn(0);
        Mockito.when(rs.getTimestamp("lock_time")).thenReturn(null);

        final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>();

        final ListIndexedDocumentExtractor extractor =
                new ListIndexedDocumentExtractor(idocList);

        extractor.extractData(rs);

        assertEquals(0, idocList.size());
    }
}
