/**
 * 
 */
package com.glueball.snoop.test.dao.extractor;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessException;

import com.glueball.snoop.dao.extractor.ListIdExtractor;

/**
 * @author karesz
 */
public class ListIdExtractorTest {

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.extractor.ListIdExtractor#extractData(java.sql.ResultSet)}
     * .
     *
     * @throws SQLException
     *             on any SQL error.
     * @throws DataAccessException
     */
    @Test
    public final void testExtractData() throws DataAccessException,
            SQLException {

        final List<String> list = new ArrayList<String>();
        final ListIdExtractor extractor = new ListIdExtractor(list);

        final ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.next()).thenReturn(true, true, true, false);

        Mockito.when(rs.getString("id")).thenReturn("id1", "id2", "id3");

        extractor.extractData(rs);

        assertEquals(3, list.size());
        assertEquals("id1", list.get(0));
        assertEquals("id2", list.get(1));
        assertEquals("id3", list.get(2));
    }

    /**
     * Test method for
     * {@link com.glueball.snoop.dao.extractor.ListIdExtractor#extractData(java.sql.ResultSet)}
     * .
     *
     * @throws SQLException
     *             on any SQL error.
     * @throws DataAccessException
     */
    @Test
    public final void testExtractDataNoResult() throws DataAccessException,
            SQLException {

        final List<String> list = new ArrayList<String>();
        final ListIdExtractor extractor = new ListIdExtractor(list);

        final ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.next()).thenReturn(false);

        Mockito.when(rs.getString("id")).thenReturn("id1");

        extractor.extractData(rs);

        assertEquals(0, list.size());
    }
}
