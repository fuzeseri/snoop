package com.glueball.snoop.bean;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.springframework.beans.factory.annotation.Autowired;

import com.glueball.snoop.entity.FileData;
import com.glueball.snoop.entity.FileId;
import com.glueball.snoop.entity.IndexStatus;
import com.glueball.snoop.entity.Meta;
import com.glueball.snoop.parser.ParserMap;
import com.glueball.snoop.util.MD5;
import com.glueball.snoop.util.SnoopMimeUtil;

/**
 * This periodically checks the ( scheduled by the spring framework ) status of
 * the documents stored in the relational database, parses the metadata and the
 * content of the document and loads it to the lucene index.
 *
 * @author karesz
 */
public final class DataIndexer {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LogManager.getLogger(DataIndexer.class);

    /**
     * The Apache lucene index writer object.
     */
    @Autowired
    private IndexWriter indexWriter;

    /**
     * Setter method of the indexWriter.
     *
     * @param pIndexWriter
     *            the IndexWriter instance.
     */
    public void setIndexWriter(final IndexWriter pIndexWriter) {

        this.indexWriter = pIndexWriter;
    }

    /**
     * This map contains all of the file parser objects as value. Key is the
     * mime type.
     */
    @Autowired
    private ParserMap parserMap;

    /**
     * Setter methos of the ParserMap field.
     *
     * @param pParserMap
     *            the ParserMap object.
     */
    public void setParserMap(final ParserMap pParserMap) {

        this.parserMap = pParserMap;
    }

    /**
     * Removes documents from the lucene index marked as DELETED.
     *
     * @param toDelete
     *            list of document Ids to delete from the lucene index.
     */
    private void removeDocsFromIndex(final List<FileData> toDelete) {

        try {
            for (final FileData data : toDelete) {
                try {

                    indexWriter.deleteDocuments(new Term("id",
                            MD5.toHexString(data.getId().getBytes())));

                    if (IndexStatus.DELETED.getStatus() == data.getStatus()) {

                        data.setDeleted((byte) 1);
                    }
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
            LOG.info("index contains deleted files: "
                    + indexWriter.hasDeletions());

            LOG.info("index contains documents: " + indexWriter.maxDoc());
        }
    }

    /**
     * Index a list of documents.
     *
     * @param toIndex
     *            List of file data.
     * @param fileNames
     *            file names map.
     * @param localPaths
     *            local paths map.
     * @param remotePaths
     *            remote paths map.
     */
    private void indexList(final List<FileData> toIndex,
            final Map<FileId, String> fileNames,
            final Map<FileId, String> localPaths,
            final Map<FileId, String> remotePaths) {

        try {

            for (final FileData data : toIndex) {

                final String localPath = localPaths.get(data.getId());
                final String remotePath = remotePaths.get(data.getId());
                final String fileName = fileNames.get(data.getId());

                final String contentType = getContentType(localPath);

                if (!this.parserMap.hasParser(contentType)) {

                    LOG.info("Can't find parser for content. File name: "
                            + localPath + " content-type: "
                            + contentType);

                    data.setStatus(IndexStatus.ERROR.getStatus());

                    continue;
                }

                LOG.info("Indexing file: " + localPath);

                boolean indexed = indexFileContent(data, fileName, localPath,
                        remotePath, contentType);

                data.setLitime(new Date().getTime());
                data.setStatus(indexed ? IndexStatus.INDEXED.getStatus()
                        : IndexStatus.ERROR.getStatus());
            }
        } catch (final Throwable e) {

            e.printStackTrace();
        } finally {

            try {

                indexWriter.commit();
            } catch (IOException e) {

                LOG.error("ERROR while trying to commit index changes");
                LOG.debug(e.getMessage());
            }
        }
    }

    /**
     * Index, re-index or delete documents.
     *
     * @param datas
     *            List of file data.
     * @param fileNames
     *            file names map.
     * @param localPaths
     *            local paths map.
     * @param remotePaths
     *            remote paths map.
     */
    public void index(final List<FileData> datas,
            final Map<FileId, String> fileNames,
            final Map<FileId, String> localPaths,
            final Map<FileId, String> remotePaths) {

        final List<FileData> toIndexList =
                new ArrayList<FileData>(datas.size());

        final List<FileData> toRemoveList = new ArrayList<FileData>();

        for (final FileData data : datas) {

            if (IndexStatus.INDEXED.getStatus() != data.getStatus()
                    && IndexStatus.DELETED.getStatus() != data.getStatus()) {

                toIndexList.add(data);
            }

            if (IndexStatus.DELETED.getStatus() == data.getStatus()
                    || IndexStatus.MODIFIED.getStatus() == data.getStatus()
                    || IndexStatus.REINDEX.getStatus() == data.getStatus()) {

                toRemoveList.add(data);
            }
        }

        removeDocsFromIndex(toRemoveList);
        indexList(toIndexList, fileNames, localPaths, remotePaths);
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
    private Document getLuceneDocument(
            final FileData data,
            final String fileName,
            final String localPath,
            final String remotePath,
            final String contentType,
            final Meta meta,
            final Reader contentReader) {

        final Document doc = new Document();
        doc.add(new StringField("id", MD5.toHexString(data.getId().getBytes()),
                Field.Store.YES));
        doc.add(new StringField(
                "fileName", fileName, Field.Store.YES));
        doc.add(new TextField("file", fileName, Field.Store.YES));
        doc.add(new StringField("path", remotePath, Field.Store.YES));
        doc.add(new StringField("uri",
                // Paths.get(remotePath).toUri().toString(),
                remotePath,
                Field.Store.YES));
        doc.add(new StringField("contentType", contentType,
                Field.Store.YES));

        if (meta.hasAuthor()) {
            doc.add(new TextField(
                    "author", meta.getAuthor(), Field.Store.YES));
        }
        if (meta.hasTitle()) {
            doc.add(new TextField("title", meta.getTitle(), Field.Store.YES));
        }
        if (meta.hasDescription()) {
            doc.add(new TextField("description", meta.getDescription(),
                    Field.Store.YES));
        }
        doc.add(new TextField("content", contentReader));

        return doc;
    }

    /**
     * Loads the lucene document to the lucene index.
     *
     * @param idoc
     *            IndexedDocument from the relational database.
     * @return true if the document has successfully loaded to the index.
     */
    private boolean indexFileContent(
            final FileData data,
            final String fileName,
            final String localPath,
            final String remotePath,
            final String contentType) {

        LOG.info("Indexing file: " + fileName);

        try (final InputStream fis = new FileInputStream(localPath)) {

            final Meta meta = new Meta();
            final Reader reader = this.parserMap.getParser(contentType)
                    .parseContent(fis, meta);

            indexWriter.addDocument(getLuceneDocument(data, fileName,
                    localPath, remotePath, contentType, meta,
                    reader));

            LOG.info("File added to index: " + fileName);

            return true;

        } catch (final Throwable e) {

            LOG.error("Error parsing content of file: " + localPath, e);

            return false;
        }
    }

    /**
     * Get content type of the file.
     *
     * @param localPath
     *            the local paths of the file.
     * @return the mime type.
     */
    private String getContentType(final String localPath) {

        return SnoopMimeUtil.detectMimeType(localPath);
    }
}
