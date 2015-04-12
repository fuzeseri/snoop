/**
 * 
 */
package com.glueball.snoop.performance.dao;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.performance.util.DocumentPathUtil;

/**
 * Test class to measure performance of has based diff algorithms.
 * 
 * @author karesz
 */
public class HashMapDocumentPathDiffPerformanceTest {

    /**
     * Hash map to simulate the set of documents found on the file system.
     */
    private static Map<byte[], Long> left =
            new HashMap<byte[], Long>();

    /**
     * Hash map simulate the set a documents stored in the index.
     */
    private static Map<byte[], Long> rigth =
            new HashMap<byte[], Long>();

    /**
     * List to store the generated documents.
     */
    private static List<DocumentPath> docPathLeft;

    /**
     * List to store the generated documents.
     */
    private static List<DocumentPath> docPathRight;

    /**
     * Initialize the lists with random data.
     *
     * @throws NoSuchAlgorithmException
     *             if md5 is not supported.
     */
    @BeforeClass
    public static void setup() throws NoSuchAlgorithmException {

        docPathLeft = DocumentPathUtil.genDocumentPaths(500000);

        docPathRight = DocumentPathUtil.genDocumentPaths(500000);
    }

    /**
     * Initialize the lists with random data.
     */
    @BeforeClass
    public static final void load() {

        for (final DocumentPath docPath : docPathLeft) {

            left.put(docPath.getId(), docPath.getLastModifiedTime());
        }

        rigth.putAll(left);
        for (final DocumentPath docPath : docPathRight) {

            rigth.put(docPath.getId(), docPath.getLastModifiedTime());
        }
    }

    /**
     * Do the test.
     */
    @Test
    public final void test() {

        final List<byte[]> toAdd = new ArrayList<byte[]>(left.size());
        final List<byte[]> toUpdate = new ArrayList<byte[]>(left.size());
        final List<byte[]> toDelete = new ArrayList<byte[]>(rigth.size());

        for (final byte[] key : left.keySet()) {

            if (rigth.containsKey(key)) {

                if (left.get(key) > rigth.get(key)) {

                    toUpdate.add(key);
                }
            } else {

                toAdd.add(key);
            }
        }

        for (final byte[] key : rigth.keySet()) {

            if (!left.containsKey(key)) {

                toDelete.add(key);
            }
        }
    }
}
