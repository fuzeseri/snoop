/**
 * 
 */
package com.gleuball.snoop.test.dao.setter;

import static org.junit.Assert.assertEquals;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.glueball.snoop.dao.setter.DeleteDocumentBatchPstmtSetter;

/**
 * @author karesz
 */
public class DeleteDocumentBatchPstmtSetterTest {

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.setter.DeleteDocumentBatchPstmtSetter#setValues(java.sql.PreparedStatement, int)}
     * .
     *
     * @throws SQLException
     *             on any SQL error.
     */
    @Test
    public final void testSetValues() throws SQLException {

        final List<String> idList = Arrays.asList(new String[] { "id1" });

        final DeleteDocumentBatchPstmtSetter setter =
                new DeleteDocumentBatchPstmtSetter(idList);

        int i = 0;

        final PreparedStatement pstmt = Mockito.mock(PreparedStatement.class);

        setter.setValues(pstmt, i);

        Mockito.verify(pstmt).setString(1, idList.get(0));
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.setter.DeleteDocumentBatchPstmtSetter#getBatchSize()}
     * .
     */
    @Test
    public final void testGetBatchSize() {

        final List<String> idList = Arrays.asList(new String[] { "id1" });
        final DeleteDocumentBatchPstmtSetter setter =
                new DeleteDocumentBatchPstmtSetter(idList);

        assertEquals(1, setter.getBatchSize());
    }
}
