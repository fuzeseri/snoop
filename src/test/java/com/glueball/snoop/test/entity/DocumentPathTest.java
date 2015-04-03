/**
 * 
 */
package com.glueball.snoop.test.entity;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.entity.IndexedDocument;
import com.glueball.snoop.util.MD5;

/**
 * @author karesz
 */
public class DocumentPathTest {

    /**
     * Test method for
     * {@link com.glueball.snoop.entity.DocumentPath#toIndexedDocumentList(java.util.List, java.lang.String)}
     * .
     * 
     * @throws NoSuchAlgorithmException
     *             if md5 algorithm is not supported.
     */
    @Test
    public final void testToIndexedDocumentList()
            throws NoSuchAlgorithmException {

        final Timestamp now = new java.sql.Timestamp(new Date().getTime());

        final String fileId = MD5.md5Digest("/testdir/filename_a.pdf");

        final DocumentPath doc = new DocumentPath();
        doc.setContentType("application/pdf");
        doc.setFileName("filename_a.pdf");
        doc.setId(fileId);
        doc.setLastModifiedTime(now);
        doc.setLocalPath("/testdir/filename_a.pdf");
        doc.setPath("/testdir/filename_a.pdf");
        doc.setShareName("test-share");
        doc.setUri("file:///testdir/filename_a.pdf");

        final IndexedDocument idoc = new IndexedDocument();
        idoc.setContentType("application/pdf");
        idoc.setFileName("filename_a.pdf");
        idoc.setId(fileId);
        idoc.setLastModifiedTime(now);
        idoc.setLocalPath("/testdir/filename_a.pdf");
        idoc.setPath("/testdir/filename_a.pdf");
        idoc.setShareName("test-share");
        idoc.setUri("file:///testdir/filename_a.pdf");
        idoc.setIndexState(IndexedDocument.INDEX_STATE_NEW);
        idoc.setLock(0);
        idoc.setLockTime(null);

        final List<DocumentPath> docList = Arrays
                .asList(new DocumentPath[] { doc });

        final List<IndexedDocument> idocList = DocumentPath
                .toIndexedDocumentList(docList,
                        IndexedDocument.INDEX_STATE_NEW);

        assertEquals(1, idocList.size());
        assertEquals(idoc, idocList.get(0));
    }
}
