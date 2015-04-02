/**
 * 
 */
package com.glueball.snoop.test.dao.setter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import org.junit.Test;
import org.mockito.Mockito;

import com.glueball.snoop.dao.setter.DocumentPathInsertSetter;
import com.glueball.snoop.entity.DocumentPath;

/**
 * @author karesz
 */
public class DocumentPathInsertSetterTest {

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.setter.DocumentPathInsertSetter#setValues(java.sql.PreparedStatement)}
     * .
     * 
     * @throws SQLException
     *             on any SQL error.
     */
    @Test
    public final void testSetValues() throws SQLException {

        final DocumentPath doc = new DocumentPath();
        doc.setContentType("application/pdf");
        doc.setFileName("filename_a.pdf");
        doc.setId("id1");
        doc.setLastModifiedTime(new java.sql.Timestamp(new Date().getTime()));
        doc.setLocalPath("/testdir/filename_a.pdf");
        doc.setPath("/testdir/filename_a.pdf");
        doc.setShareName("test-share");
        doc.setUri("file:///testdir/filename_a.pdf");

        final DocumentPathInsertSetter setter =
                new DocumentPathInsertSetter(doc);

        final PreparedStatement pstmt = Mockito.mock(PreparedStatement.class);

        setter.setValues(pstmt);

        Mockito.verify(pstmt).setString(1, doc.getId());
        Mockito.verify(pstmt).setString(2, doc.getShareName());
        Mockito.verify(pstmt).setString(3, doc.getFileName());
        Mockito.verify(pstmt).setString(4, doc.getUri());
        Mockito.verify(pstmt).setString(5, doc.getPath());
        Mockito.verify(pstmt).setString(6, doc.getLocalPath());
        Mockito.verify(pstmt)
                .setTimestamp(7, doc.getLastModifiedTime());
        Mockito.verify(pstmt).setString(8, doc.getContentType());
    }

}
