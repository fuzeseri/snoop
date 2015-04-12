/**
 * 
 */
package com.glueball.snoop.test.dao.setter;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.glueball.snoop.dao.setter.IndexedDocumentBatchUpdateSetter;
import com.glueball.snoop.entity.IndexedDocument;
import com.glueball.snoop.util.MD5;

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
     * @throws NoSuchAlgorithmException
     */
    @Test
    public final void testSetValues() throws SQLException,
            NoSuchAlgorithmException {

        final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>();

        final IndexedDocument idoc1 = new IndexedDocument();
        idoc1.setContentType("application/pdf");
        idoc1.setFileName("filename_a.pdf");
        idoc1.setId(MD5.md5Digest("iid1"));
        idoc1.setLastModifiedTime(new Date().getTime());
        idoc1.setLocalPath("/testdir/filename_a.pdf");
        idoc1.setPath("/testdir/filename_a.pdf");
        idoc1.setShareName("test-share");
        idoc1.setUri("file:///testdir/filename_a.pdf");
        idoc1.setLastIndexedTime(new Date().getTime());
        idoc1.setLock(0);
        idoc1.setLockTime(new Date().getTime());
        idocList.add(idoc1);

        final IndexedDocumentBatchUpdateSetter setter =
                new IndexedDocumentBatchUpdateSetter(idocList);

        int i = 0;

        final PreparedStatement pstmt = Mockito.mock(PreparedStatement.class);

        setter.setValues(pstmt, i);

        int index = 1;
        Mockito.verify(pstmt).setLong(index++,
                idocList.get(i).getLastModifiedTime());
        Mockito.verify(pstmt).setLong(index++,
                idocList.get(i).getLastIndexedTime());
        Mockito.verify(pstmt).setString(index++,
                idocList.get(i).getIndexState());
        Mockito.verify(pstmt).setString(index++,
                MD5.toHexString(idocList.get(i).getId()));
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.setter.IndexedDocumentBatchUpdateSetter#getBatchSize()}
     * .
     * 
     * @throws NoSuchAlgorithmException
     */
    @Test
    public final void testGetBatchSize() throws NoSuchAlgorithmException {

        final List<IndexedDocument> idocList = new ArrayList<IndexedDocument>();

        final IndexedDocument idoc1 = new IndexedDocument();
        idoc1.setContentType("application/pdf");
        idoc1.setFileName("filename_a.pdf");
        idoc1.setId(MD5.md5Digest("iid1"));
        idoc1.setLastModifiedTime(new Date().getTime());
        idoc1.setLocalPath("/testdir/filename_a.pdf");
        idoc1.setPath("/testdir/filename_a.pdf");
        idoc1.setShareName("test-share");
        idoc1.setUri("file:///testdir/filename_a.pdf");
        idoc1.setLastIndexedTime(new Date().getTime());
        idoc1.setLock(0);
        idoc1.setLockTime(new Date().getTime());
        idocList.add(idoc1);

        final IndexedDocumentBatchUpdateSetter setter =
                new IndexedDocumentBatchUpdateSetter(idocList);

        assertEquals(1, setter.getBatchSize());
    }

}
