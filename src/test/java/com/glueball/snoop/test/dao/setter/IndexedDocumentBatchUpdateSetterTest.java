/**
 * 
 */
package com.glueball.snoop.test.dao.setter;

import static org.junit.Assert.assertEquals;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.glueball.snoop.dao.setter.IndexedDocumentBatchUpdateSetter;
import com.glueball.snoop.entity.IndexedDocument;

/**
 * @author karesz
 */
public class IndexedDocumentBatchUpdateSetterTest {

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.setter.IndexedDocumentBatchUpdateSetter#setValues(java.sql.PreparedStatement, int)}
     * .
     * 
     * @throws SQLException
     *             on any SQL error.
     */
    @Test
    public final void testSetValues() throws SQLException {

        final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>();

        final IndexedDocument idoc1 = new IndexedDocument();
        idoc1.setContentType("application/pdf");
        idoc1.setFileName("filename_a.pdf");
        idoc1.setId("iid1");
        idoc1.setLastModifiedTime(new java.sql.Timestamp(new Date().getTime()));
        idoc1.setLocalPath("/testdir/filename_a.pdf");
        idoc1.setPath("/testdir/filename_a.pdf");
        idoc1.setShareName("test-share");
        idoc1.setUri("file:///testdir/filename_a.pdf");
        idoc1.setLastIndexedTime(new java.sql.Timestamp(new Date().getTime()));
        idoc1.setLock(0);
        idoc1.setLockTime(new Timestamp(new Date().getTime()));
        idocList.add(idoc1);

        final IndexedDocumentBatchUpdateSetter setter =
                new IndexedDocumentBatchUpdateSetter(idocList);

        int i = 0;

        final PreparedStatement pstmt = Mockito.mock(PreparedStatement.class);

        setter.setValues(pstmt, i);

        int index = 1;
        Mockito.verify(pstmt).setTimestamp(index++,
                idocList.get(i).getLastIndexedTime());
        Mockito.verify(pstmt).setString(index++,
                idocList.get(i).getIndexState());
        Mockito.verify(pstmt).setString(index++, idocList.get(i).getId());
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.setter.IndexedDocumentBatchUpdateSetter#getBatchSize()}
     * .
     */
    @Test
    public final void testGetBatchSize() {

        final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>();

        final IndexedDocument idoc1 = new IndexedDocument();
        idoc1.setContentType("application/pdf");
        idoc1.setFileName("filename_a.pdf");
        idoc1.setId("iid1");
        idoc1.setLastModifiedTime(new java.sql.Timestamp(new Date().getTime()));
        idoc1.setLocalPath("/testdir/filename_a.pdf");
        idoc1.setPath("/testdir/filename_a.pdf");
        idoc1.setShareName("test-share");
        idoc1.setUri("file:///testdir/filename_a.pdf");
        idoc1.setLastIndexedTime(new java.sql.Timestamp(new Date().getTime()));
        idoc1.setLock(0);
        idoc1.setLockTime(new Timestamp(new Date().getTime()));
        idocList.add(idoc1);

        final IndexedDocumentBatchUpdateSetter setter =
                new IndexedDocumentBatchUpdateSetter(idocList);

        assertEquals(1, setter.getBatchSize());
    }

}
