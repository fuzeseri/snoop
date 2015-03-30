package com.glueball.snoop.bean;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.glueball.snoop.entity.IndexedDocument;
import com.glueball.snoop.entity.Meta;
import com.glueball.snoop.parser.ParserMap;

/**
 * This periodically checks the (scheduled by the spring framework) state of the
 * documents stored in the relational database, parses the meta data and the
 * content of the document and loads it to the lucene index.
 * 
 * @author karesz
 *
 */
public final class DataIndexer {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LogManager.getLogger(DataIndexer.class);

    /**
     * Constant default value of the maximum allowed documents per round.
     */
    private static final int DEFAULT_MAXDOC_NUM = 100;

    /**
     * The Apache lucene index writer object.
     */
    @Autowired
    private IndexWriter indexWriter;

    /**
     * Setter method of the indexWriter.
     *
     * @param _indexWriter
     *            the IndexWriter instance.
     */
    public void setIndexWriter(final IndexWriter _indexWriter) {

        this.indexWriter = _indexWriter;
    }

    /**
     * Indexed document database service.
     */
    @Autowired
    private IndexedDocumentBean indexedDocumentBean;

    /**
     * Setter of the indexedDocumentBean service field.
     *
     * @param _indexedDocumentBean
     *            the IndexedDocumentBean instance.
     */
    public void setIndexedDocumentBean(final IndexedDocumentBean _indexedDocumentBean) {

        this.indexedDocumentBean = _indexedDocumentBean;
    }

    /**
     * This map contains all of the file parser objects as value. The key is the
     * mime type.
     */
    @Autowired
    private ParserMap parserMap;

    /**
     * Setter methos of the ParserMap field.
     *
     * @param _parserMap
     */
    public void setParserMap(final ParserMap _parserMap) {

        this.parserMap = _parserMap;
    }

    /**
     * Maximum number of document to index in one round.
     */
    private int maxDoc = DEFAULT_MAXDOC_NUM;

    /**
     * Setter method of the maxDoc field.
     * 
     * @param _maxDoc
     */
    public void setMaxDoc(int _maxDoc) {

        this.maxDoc = _maxDoc;
    }

    /**
     * Removes documents from the lucene index marked as DELETED.
     * 
     * @param toDelete
     */
    private void removeModifiedDeletedDocsFromIndex(final List<String> toDelete) {

        try {

            for (final String docId : toDelete) {

                try {

                    indexWriter.deleteDocuments(new Term("id", docId));
                } catch (final IOException e) {

                    LOG.error("ERROR while deleting document from index the");
                    LOG.debug(e.getMessage());
                }
            }
        } finally {

            try {

                indexWriter.commit();
            } catch (IOException e) {

                LOG.error("ERROR while trying to commit index changes");
                LOG.debug(e.getMessage());
            }
            LOG.debug("index contains deleted files: " + indexWriter.hasDeletions());
            LOG.debug("index contains documents: " + indexWriter.maxDoc());
        }
    }

    /**
     * Loads a list of IndexedDocument to the lucene index.
     * 
     * @param haveToIndexList
     */
    private void indexList(final List<IndexedDocument> haveToIndexList) {

        try {

            for (final IndexedDocument idoc : haveToIndexList) {

                if (!this.parserMap.hasParser(idoc.getContentType())) {

                    LOG.debug("Can't find parser for content. File name: " + idoc.getFileName() + " content-type: " + idoc.getContentType());
                    continue;
                }

                LOG.debug("Indexing file: " + idoc.toString());

                boolean indexed = indexFileContent(idoc);

                idoc.setLastIndexedTime(new java.sql.Timestamp(new Date().getTime()));
                idoc.setIndexState(indexed ? IndexedDocument.INDEX_STATE_INDEXED : IndexedDocument.INDEX_STATE_ERROR);
            }
        } finally {

            try {

                indexWriter.commit();
                this.indexedDocumentBean.unLockUpdateState(haveToIndexList);
            } catch (IOException e) {

                LOG.error("ERROR while trying to commit index changes");
                LOG.debug(e.getMessage());
            }
        }
    }

    /**
     * Scheduled task method. It loads the IndexedDocuments from the relational
     * database. Group them by the status and calls the delete or index methods
     * on the lists.
     */
    @Scheduled(fixedDelay = 10 * 60 * 1000)
    public void index() {

        final List<IndexedDocument> haveToIndexList = new ArrayList<IndexedDocument>(maxDoc);

        final List<String> toRemove = new ArrayList<String>();
        for (final IndexedDocument idoc : indexedDocumentBean.haveToIndex(maxDoc)) {

            if (IndexedDocument.INDEX_STATE_DELETED.equals(idoc.getIndexState()) || IndexedDocument.INDEX_STATE_MODIFIED.equals(idoc.getIndexState())
                    || IndexedDocument.INDEX_STATE_REINDEX.equals(idoc.getIndexState())) {

                toRemove.add(idoc.getId());
                if (!IndexedDocument.INDEX_STATE_DELETED.equals(idoc.getIndexState())) {

                    haveToIndexList.add(idoc);
                }
            }
        }
        removeModifiedDeletedDocsFromIndex(toRemove);
        indexList(haveToIndexList);
    }

    /**
     * Creates apache lucene document from the data parsed out from the file.
     * 
     * @param idoc
     *            the IdexedDocument data from the relational database.
     * @param meta
     *            the metadata parsed out from the file.
     * @param contentReader
     *            reader instance for the content of the file.
     * @return apache lucene document.
     */
    private Document getLuceneDocument(final IndexedDocument idoc, final Meta meta, final Reader contentReader) {

        final Document doc = new Document();
        doc.add(new StringField("id", idoc.getId(), Field.Store.YES));
        doc.add(new StringField("fileName", idoc.getFileName(), Field.Store.YES));
        doc.add(new TextField("file", idoc.getFileName(), Field.Store.YES));
        doc.add(new StringField("path", idoc.getPath(), Field.Store.YES));
        doc.add(new StringField("uri", idoc.getUri(), Field.Store.YES));
        doc.add(new StringField("contentType", idoc.getContentType(), Field.Store.YES));

        if (meta.hasAuthor()) {
            doc.add(new TextField("author", meta.getAuthor(), Field.Store.YES));
        }
        if (meta.hasTitle()) {
            doc.add(new TextField("title", meta.getTitle(), Field.Store.YES));
        }
        if (meta.hasDescription()) {
            doc.add(new TextField("description", meta.getDescription(), Field.Store.YES));
        }
        doc.add(new TextField("content", contentReader));

        return doc;
    }

    /**
     * Loads the lucene document to the luecen index.
     * 
     * @param idoc
     *            IndexedDocument from the relational database.
     * @return true if the lucene docuemnt has successfully loaded to the lucene
     *         index.
     */
    private boolean indexFileContent(final IndexedDocument idoc) {

        try (final Writer contentWriter = new StringWriter()) {

            final Meta meta = this.parserMap.getParser(idoc.getContentType()).parseContent(idoc.getLocalPath(), contentWriter);

            try (final Reader contentReader = new StringReader(contentWriter.toString())) {

                indexWriter.addDocument(getLuceneDocument(idoc, meta, contentReader));
            }

            LOG.debug("File added to index: " + idoc.toString());

            return true;

        } catch (final Throwable e) {

            LOG.error("Can't find parser for file: " + idoc.getPath());
            LOG.debug(e.getMessage());

            return false;
        }
    }
}
