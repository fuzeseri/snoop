/**
 * 
 */
package com.glueball.snoop.performance.dao;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.glueball.snoop.dao.DocumentPathDao;
import com.glueball.snoop.dao.impl.DocumentPathDaoImpl;
import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.performance.util.DocumentPathUtil;

/**
 * Test class to measure the performance of DocumentPathDao.
 *
 * @author karesz
 */
public class DocumentPathDaoPerformanceTest {

    /**
     * Data access object for DocumentPaths.
     */
    private DocumentPathDao dao;

    /**
     * Spring application context.
     */
    private ApplicationContext ctx;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        this.ctx = new ClassPathXmlApplicationContext(
                "classpath:spring/application-test.xml");

        this.dao = ctx.getBean(DocumentPathDaoImpl.class);

        dao.createTable();
        dao.truncateTable();
    }

    @Test
    public void insert100X600RowsTest() throws NoSuchAlgorithmException {

        final List<DocumentPath> docs = DocumentPathUtil
                .genDocuemntPaths(600);

        dao.truncateTable();
        for (int i = 0; i < 100; i++) {
            dao.insertList(docs);
        }
    }

    @Test
    public void insert100X500RowsTest() throws NoSuchAlgorithmException {

        final List<DocumentPath> docs = DocumentPathUtil
                .genDocuemntPaths(500);

        dao.truncateTable();
        for (int i = 0; i < 100; i++) {
            dao.insertList(docs);
        }
    }

    @Test
    public void insert100X400RowsTest() throws NoSuchAlgorithmException {

        final List<DocumentPath> docs = DocumentPathUtil
                .genDocuemntPaths(400);

        dao.truncateTable();
        for (int i = 0; i < 100; i++) {
            dao.insertList(docs);
        }
    }

    @Test
    public void insert100X300RowsTest() throws NoSuchAlgorithmException {

        final List<DocumentPath> docs = DocumentPathUtil
                .genDocuemntPaths(300);

        dao.truncateTable();
        for (int i = 0; i < 100; i++) {
            dao.insertList(docs);
        }
    }

    @Test
    public void insert100X200RowsTest() throws NoSuchAlgorithmException {

        final List<DocumentPath> docs = DocumentPathUtil
                .genDocuemntPaths(200);

        dao.truncateTable();
        for (int i = 0; i < 100; i++) {
            dao.insertList(docs);
        }
    }

    @Test
    public void insert100X100RowsTest() throws NoSuchAlgorithmException {

        dao.truncateTable();
        final List<DocumentPath> docs = DocumentPathUtil
                .genDocuemntPaths(100);

        for (int i = 0; i < 100; i++) {
            dao.insertList(docs);
        }
    }
}
