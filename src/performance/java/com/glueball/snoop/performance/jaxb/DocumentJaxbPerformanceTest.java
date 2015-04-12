/**
 * 
 */
package com.glueball.snoop.performance.jaxb;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.BeforeClass;
import org.junit.Test;

import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.entity.DocumentPaths;
import com.glueball.snoop.performance.util.DocumentPathUtil;

/**
 * @author karesz
 */
public class DocumentJaxbPerformanceTest {

    /**
     * Hash map to simulate the set of documents found on the file system.
     */
    private static Map<String, Timestamp> left = new HashMap<String, Timestamp>();

    /**
     * Hash map simulate the set a documents stored in the index.
     */
    private static Map<String, Timestamp> rigth = new HashMap<String, Timestamp>();

    /**
     * List to store the generated documents.
     */
    private static List<DocumentPath> docPathLeft;

    // /**
    // * List to store the generated documents.
    // */
    // private static List<SnoopDocument> snoopDocs;

    /**
     * Initialize the lists with random data.
     *
     * @throws NoSuchAlgorithmException
     *             if md5 is not supported.
     */
    @BeforeClass
    public static void setup() throws NoSuchAlgorithmException {

        docPathLeft = DocumentPathUtil.genDocumentPaths(500000);
        // snoopDocs = new ArrayList<SnoopDocument>(500000);
        // // docPathLeft = DocumentPathUtil.genDocumentPaths(500000);
        // for (final DocumentPath doc :
        // DocumentPathUtil.genDocumentPaths(500000)) {
        // snoopDocs.add(doc);
        // }
    }

    @Test
    public final void testWrite() {

        try {

            final File file = new File("performance-share.xml");
            final JAXBContext jaxbContext = JAXBContext
                    .newInstance(DocumentPaths.class);
            final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            final DocumentPaths docPaths = new DocumentPaths();
            docPaths.setPaths(docPathLeft);

            jaxbMarshaller.marshal(docPaths, file);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    @Test
    public final void testRead() throws JAXBException {

        final JAXBContext jaxbContext = JAXBContext
                .newInstance(DocumentPaths.class);
        final Unmarshaller jaxbUnmarshaller = jaxbContext
                .createUnmarshaller();
        final DocumentPaths paths = (DocumentPaths) jaxbUnmarshaller
                .unmarshal(new File("performance-share.xml"));
    }
}
