/**
 * 
 */
package com.glueball.snoop.test.dao.setter;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import org.junit.Test;
import org.mockito.Mockito;

import com.glueball.snoop.dao.setter.DocumentPathInsertSetter;
import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.util.MD5;

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
     * @throws NoSuchAlgorithmException
     */
    @Test
    public final void testSetValues() throws SQLException,
            NoSuchAlgorithmException {

        final DocumentPath doc = new DocumentPath();
        doc.setContentType("application/pdf");
        doc.setFileName("filename_a.pdf");
        doc.setId(MD5.md5Digest("id1"));
        doc.setLastModifiedTime(new Date().getTime());
        doc.setLocalPath("/testdir/filename_a.pdf");
        doc.setPath("/testdir/filename_a.pdf");
        doc.setShareName("test-share");
        doc.setUri("file:///testdir/filename_a.pdf");

        final DocumentPathInsertSetter setter =
                new DocumentPathInsertSetter(doc);

        final PreparedStatement pstmt = Mockito.mock(PreparedStatement.class);

        setter.setValues(pstmt);

        Mockito.verify(pstmt).setString(1, MD5.toHexString(doc.getId()));
        Mockito.verify(pstmt).setString(2, doc.getShareName());
        Mockito.verify(pstmt).setString(3, doc.getFileName());
        Mockito.verify(pstmt).setString(4, doc.getUri());
        Mockito.verify(pstmt).setString(5, doc.getPath());
        Mockito.verify(pstmt).setString(6, doc.getLocalPath());
        Mockito.verify(pstmt)
                .setLong(7, doc.getLastModifiedTime());
        Mockito.verify(pstmt).setString(8, doc.getContentType());
    }

}
