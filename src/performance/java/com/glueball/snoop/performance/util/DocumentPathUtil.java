/**
 * 
 */
package com.glueball.snoop.performance.util;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.util.MD5;

/**
 * Utility class to generate DocumentPath object for performance tests.
 * 
 * @author karesz
 */
public final class DocumentPathUtil {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LogManager
            .getLogger(DocumentPathUtil.class);

    /**
     * Length of the random strings generated.
     */
    private static final int RANDOM_STRING_LENGTH = 8;

    /**
     * Time stamp.
     */
    private static final Timestamp NOW = new Timestamp(new Date().getTime());

    /**
     * Generate a ( large ) list of DocumentPath objects.
     *
     * @param size
     *            the number of objects to generate.
     * @return list of the generated objects.
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    public static final List<DocumentPath> genDocuemntPaths(final int size)
            throws NoSuchAlgorithmException {

        final List<DocumentPath> list = new ArrayList<DocumentPath>(size);

        for (int i = 0; i < size; i++) {

            final String path1 = RandomStringUtil
                    .generateString(RANDOM_STRING_LENGTH);
            final String path2 = RandomStringUtil
                    .generateString(RANDOM_STRING_LENGTH);
            final String path3 = RandomStringUtil
                    .generateString(RANDOM_STRING_LENGTH);
            final String path4 = RandomStringUtil
                    .generateString(RANDOM_STRING_LENGTH);

            final String filename = RandomStringUtil
                    .generateString(RANDOM_STRING_LENGTH);

            final StringBuilder pathBuilder = new StringBuilder();

            final String path = pathBuilder.append("/").append(path1)
                    .append("/").append(path2).append("/").append(path3)
                    .append("/").append(path4).append("/").append(filename)
                    .toString();

            final String fileId = MD5.md5Digest(path);

            final DocumentPath doc = new DocumentPath();
            doc.setContentType("application/pdf");
            doc.setFileName(filename);
            doc.setId(fileId);
            doc.setLastModifiedTime(NOW);
            doc.setLocalPath(path);
            doc.setPath(path);
            doc.setShareName("performance-share");
            doc.setUri("file://" + path);

            list.add(doc);
        }

        return list;
    }
}
