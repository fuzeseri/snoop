/**
 * 
 */
package com.glueball.snoop.test.dao.extractor;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;
import org.mockito.Mockito;

import com.glueball.snoop.dao.extractor.DocumentPathExtractor;
import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.util.MD5;

/**
 * @author karesz
 */
public class DocumentPathExtractorTest {

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.extractor.DocumentPathExtractor#extractData(java.sql.ResultSet)}
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

        final DocumentPath refDoc = new DocumentPath();
        refDoc.setContentType("application/pdf");
        refDoc.setFileName("filename_a.pdf");
        refDoc.setId(MD5.md5Digest("id"));
        refDoc.setLastModifiedTime(now);
        refDoc.setLocalPath("/testdir/filename_a.pdf");
        refDoc.setPath("/testdir/filename_a.pdf");
        refDoc.setShareName("test-share");
        refDoc.setUri("file:///testdir/filename_a.pdf");

        final DocumentPath doc = new DocumentPath();
        final DocumentPathExtractor extractor = new DocumentPathExtractor(doc);
        final ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.next()).thenReturn(true);

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
        Mockito.when(rs.getString("content_type"))
                .thenReturn("application/pdf");

        extractor.extractData(rs);

        assertEquals(refDoc, doc);
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.extractor.DocumentPathExtractor#extractData(java.sql.ResultSet)}
     * .
     * 
     * @throws SQLException
     *             on any SQL error.
     */
    @Test
    public final void testExtractDataNoResult() throws SQLException {

        final Timestamp now = new Timestamp(new Date().getTime());

        final DocumentPath doc = new DocumentPath();
        final DocumentPathExtractor extractor = new DocumentPathExtractor(doc);
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
        Mockito.when(rs.getString("content_type"))
                .thenReturn("application/pdf");

        extractor.extractData(rs);

        assertEquals(new DocumentPath(), doc);
    }

}
