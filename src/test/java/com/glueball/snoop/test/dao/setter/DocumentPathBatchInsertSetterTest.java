/**
 * 
 */
package com.glueball.snoop.test.dao.setter;

import static org.junit.Assert.assertEquals;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.glueball.snoop.dao.setter.DocumentPathBatchInsertSetter;
import com.glueball.snoop.entity.DocumentPath;

/**
 * @author karesz
 */
public class DocumentPathBatchInsertSetterTest {

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.setter.DocumentPathBatchInsertSetter#setValues(java.sql.PreparedStatement, int)}
     * .
     * 
     * @throws SQLException
     */
    @Test
    public final void testSetValues() throws SQLException {

        final List<DocumentPath> list = new ArrayList<DocumentPath>();

        final DocumentPath doc = new DocumentPath();
        doc.setContentType("application/pdf");
        doc.setFileName("filename_a.pdf");
        doc.setId("id1");
        doc.setLastModifiedTime(new java.sql.Timestamp(new Date().getTime()));
        doc.setLocalPath("/testdir/filename_a.pdf");
        doc.setPath("/testdir/filename_a.pdf");
        doc.setShareName("test-share");
        doc.setUri("file:///testdir/filename_a.pdf");

        list.add(doc);

        final DocumentPathBatchInsertSetter setter =
                new DocumentPathBatchInsertSetter(list);

        int i = 0;

        final PreparedStatement pstmt = Mockito.mock(PreparedStatement.class);

        setter.setValues(pstmt, i);

        Mockito.verify(pstmt).setString(1, list.get(0).getId());
        Mockito.verify(pstmt).setString(2, list.get(0).getShareName());
        Mockito.verify(pstmt).setString(3, list.get(0).getFileName());
        Mockito.verify(pstmt).setString(4, list.get(0).getUri());
        Mockito.verify(pstmt).setString(5, list.get(0).getPath());
        Mockito.verify(pstmt).setString(6, list.get(0).getLocalPath());
        Mockito.verify(pstmt)
                .setTimestamp(7, list.get(0).getLastModifiedTime());
        Mockito.verify(pstmt).setString(8, list.get(0).getContentType());
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.setter.DocumentPathBatchInsertSetter#getBatchSize()}
     * .
     */
    @Test
    public final void testGetBatchSize() {

        final List<DocumentPath> list = new ArrayList<DocumentPath>();

        final DocumentPath doc = new DocumentPath();
        doc.setContentType("application/pdf");
        doc.setFileName("filename_a.pdf");
        doc.setId("id1");
        doc.setLastModifiedTime(new java.sql.Timestamp(new Date().getTime()));
        doc.setLocalPath("/testdir/filename_a.pdf");
        doc.setPath("/testdir/filename_a.pdf");
        doc.setShareName("test-share");
        doc.setUri("file:///testdir/filename_a.pdf");

        list.add(doc);

        final DocumentPathBatchInsertSetter setter =
                new DocumentPathBatchInsertSetter(list);

        assertEquals(1, setter.getBatchSize());
    }

}
