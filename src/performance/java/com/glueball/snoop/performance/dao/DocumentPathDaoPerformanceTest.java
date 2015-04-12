/**
 * 
 */
package com.glueball.snoop.performance.dao;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.glueball.snoop.dao.DocumentPathDao;
import com.glueball.snoop.dao.impl.DocumentPathDaoImpl;
import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.util.DocumentPathUtil;

/**
 * Test class to measure the performance of DocumentPathDao.
 *
 * @author karesz
 */
public class DocumentPathDaoPerformanceTest {

    /**
     * Data access object for DocumentPaths.
     */
    private static DocumentPathDao dao;

    /**
     * Spring application context.
     */
    private static ApplicationContext ctx;

    /**
     * 
     */
    private static List<DocumentPath> docs10000;

    /**
     * 
     */
    private static List<DocumentPath> docs100000;

    /**
     * 
     */
    private static List<DocumentPath> docs70000;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUp() throws Exception {

        ctx = new ClassPathXmlApplicationContext(
                "classpath:spring/application-test.xml");

        dao = ctx.getBean(DocumentPathDaoImpl.class);

        docs10000 = DocumentPathUtil
                .genDocumentPaths(10000);

        docs100000 = DocumentPathUtil
                .genDocumentPaths(100000);

        docs70000 = DocumentPathUtil
                .genDocumentPaths(70000);

        dao.createTable();
        dao.truncateTable();
    }

    /**
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void insert1X10000RowsTest() throws NoSuchAlgorithmException {

        dao.insertList(docs10000);
    }

    /**
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void insert1X100000RowsTest() throws NoSuchAlgorithmException {

        dao.insertList(docs100000);
    }

    /**
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void insert1X70000RowsTest() throws NoSuchAlgorithmException {

        dao.insertList(docs70000);
    }
}
