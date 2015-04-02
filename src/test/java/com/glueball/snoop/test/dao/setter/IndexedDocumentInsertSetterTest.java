/**
 * 
 */
package com.glueball.snoop.test.dao.setter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;
import org.mockito.Mockito;

import com.glueball.snoop.dao.setter.IndexedDocumentInsertSetter;
import com.glueball.snoop.entity.IndexedDocument;

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
     */
    @Test
    public final void testSetValues() throws SQLException {

        final IndexedDocument idoc = new IndexedDocument();
        idoc.setContentType("application/pdf");
        idoc.setFileName("filename_a.pdf");
        idoc.setId("iid1");
        idoc.setLastModifiedTime(new java.sql.Timestamp(new Date().getTime()));
        idoc.setLocalPath("/testdir/filename_a.pdf");
        idoc.setPath("/testdir/filename_a.pdf");
        idoc.setShareName("test-share");
        idoc.setUri("file:///testdir/filename_a.pdf");
        idoc.setLastIndexedTime(new java.sql.Timestamp(new Date().getTime()));
        idoc.setLock(0);
        idoc.setLockTime(new Timestamp(new Date().getTime()));

        final IndexedDocumentInsertSetter setter =
                new IndexedDocumentInsertSetter(idoc);

        final PreparedStatement pstmt = Mockito.mock(PreparedStatement.class);

        setter.setValues(pstmt);

        int index = 1;
        Mockito.verify(pstmt).setString(index++, idoc.getId());
        Mockito.verify(pstmt).setString(index++, idoc.getShareName());
        Mockito.verify(pstmt).setString(index++, idoc.getFileName());
        Mockito.verify(pstmt).setString(index++, idoc.getUri());
        Mockito.verify(pstmt).setString(index++, idoc.getPath());
        Mockito.verify(pstmt).setString(index++, idoc.getLocalPath());
        Mockito.verify(pstmt).setTimestamp(index++, idoc.getLastModifiedTime());
        Mockito.verify(pstmt).setTimestamp(index++, idoc.getLastIndexedTime());
        Mockito.verify(pstmt).setString(index++, idoc.getContentType());
        Mockito.verify(pstmt).setString(index++, idoc.getIndexState());
        Mockito.verify(pstmt).setLong(index++, idoc.getLock());
        Mockito.verify(pstmt).setTimestamp(index++, idoc.getLockTime());
    }

}
