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

import com.glueball.snoop.dao.setter.IndexedDocumentInsertSetter;
import com.glueball.snoop.entity.IndexedDocument;
import com.glueball.snoop.util.MD5;

/**
 * @author karesz
 */
public class IndexedDocumentInsertSetterTest {

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.setter.IndexedDocumentInsertSetter#setValues(java.sql.PreparedStatement)}
     * .
     * 
     * @throws SQLException
     *             on any SQL error.
     * @throws NoSuchAlgorithmException
     */
    @Test
    public final void testSetValues() throws SQLException,
            NoSuchAlgorithmException {

        final IndexedDocument idoc = new IndexedDocument();
        idoc.setContentType("application/pdf");
        idoc.setFileName("filename_a.pdf");
        idoc.setId(MD5.md5Digest("iid1"));
        idoc.setLastModifiedTime(new Date().getTime());
        idoc.setLocalPath("/testdir/filename_a.pdf");
        idoc.setPath("/testdir/filename_a.pdf");
        idoc.setShareName("test-share");
        idoc.setUri("file:///testdir/filename_a.pdf");
        idoc.setLastIndexedTime(new Date().getTime());
        idoc.setLock(0);
        idoc.setLockTime(new Date().getTime());

        final IndexedDocumentInsertSetter setter =
                new IndexedDocumentInsertSetter(idoc);

        final PreparedStatement pstmt = Mockito.mock(PreparedStatement.class);

        setter.setValues(pstmt);

        int index = 1;
        Mockito.verify(pstmt).setString(index++, MD5.toHexString(idoc.getId()));
        Mockito.verify(pstmt).setString(index++, idoc.getShareName());
        Mockito.verify(pstmt).setString(index++, idoc.getFileName());
        Mockito.verify(pstmt).setString(index++, idoc.getUri());
        Mockito.verify(pstmt).setString(index++, idoc.getPath());
        Mockito.verify(pstmt).setString(index++, idoc.getLocalPath());
        Mockito.verify(pstmt).setLong(index++, idoc.getLastModifiedTime());
        Mockito.verify(pstmt).setLong(index++, idoc.getLastIndexedTime());
        Mockito.verify(pstmt).setString(index++, idoc.getContentType());
        Mockito.verify(pstmt).setString(index++, idoc.getIndexState());
        Mockito.verify(pstmt).setLong(index++, idoc.getLock());
        Mockito.verify(pstmt).setLong(index++, idoc.getLockTime());
    }

}
