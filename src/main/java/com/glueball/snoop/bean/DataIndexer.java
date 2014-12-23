package com.glueball.snoop.bean;

import java.io.IOException;
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
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import com.glueball.snoop.dao.DocumentPathBean;
import com.glueball.snoop.dao.IndexedDocumentBean;
import com.glueball.snoop.entity.Content;
import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.entity.IndexedDocument;
import com.glueball.snoop.entity.SnoopDocument;
import com.glueball.snoop.parser.ParserMap;
import com.glueball.snoop.parser.UnavialableParserException;

public class DataIndexer implements Runnable {

	private static final Logger LOG = LogManager.getLogger(DataIndexer.class);

	@Autowired
	private IndexWriter indexWriter;

	public void setIndexWriter(IndexWriter indexWriter) {
		this.indexWriter = indexWriter;
	}

	@Autowired
	private DocumentPathBean docPathBean;

	public void setDocPathBean(final DocumentPathBean docPathBean) {
		this.docPathBean = docPathBean;
	}

	@Autowired
	private IndexedDocumentBean indexedDocumentBean;

	public void setIndexedDocumentBean(final IndexedDocumentBean indexedDocumentBean) {
		this.indexedDocumentBean = indexedDocumentBean;
	}

	@Autowired
	private ParserMap parserMap;

	public void setParserMap(final ParserMap _parserMap) {
		this.parserMap = _parserMap;
	}

	private void removeModifiedDeletedDocsFromIndex() {

		final List<String> dbDelete = new ArrayList<String>();
		try {

			final List<String> toDelete = new ArrayList<String>();
			toDelete.addAll(indexedDocumentBean.getDeletedDocIds());
			toDelete.addAll(indexedDocumentBean.getModifiedDocIds());

			for (final String docId : toDelete) {

				try {

					indexWriter.deleteDocuments(new Term("id", docId));
					dbDelete.add(docId);
				} catch (final IOException e) {

					LOG.error("ERROR while deleting document from index the");
					LOG.debug(e.getMessage());
				}
			} 
		} finally {

			try {

				indexWriter.commit();
				indexedDocumentBean.deleteByIds(dbDelete);				
			} catch (IOException e) {

				LOG.error("ERROR while trying to commit index changes");
				LOG.debug(e.getMessage());
			}
			LOG.debug("index contains deleted files: " + indexWriter.hasDeletions());
			LOG.debug("index contains documents: " + indexWriter.maxDoc());
		}
	}

	private void doIndex() {

		final List<DocumentPath> haveToIndexList = docPathBean.haveToIndex();
		final List<IndexedDocument> docPathList = new ArrayList<IndexedDocument>(haveToIndexList.size());

		try {

			long haveToIndexSize = docPathBean.haveToIndex().size();
			long counter = 0;

			for (final SnoopDocument docPath : haveToIndexList) {

				boolean indexed = false;

				if (!this.parserMap.hasParser(docPath.getContentType())) {

					LOG.debug("Can't find parser for content. File name: " + docPath.getFileName() + " content-type: " + docPath.getContentType());
					haveToIndexSize--;
				} else {

					LOG.debug("Indexing file: " + docPath.toString());
					try {
						final Content content = this.parserMap.getParser(docPath.getContentType()).parseContent(docPath.getPath());
						if (content.hasContent()) {

							final Document doc = new Document();
							doc.add(new StringField("id", 			docPath.getId(),       Field.Store.YES));
							doc.add(new StringField("fileName", 	docPath.getFileName(), Field.Store.YES));
							doc.add(new StringField("path", 		docPath.getPath(),     Field.Store.YES));
							doc.add(new StringField("uri", 			docPath.getUri(),      Field.Store.YES));
							doc.add(new StringField("contentType", 	docPath.getContentType(), Field.Store.YES));

							if (content.hasAuthor()) {
								doc.add(new StringField("author", content.getAuthor(), Field.Store.YES));
							}
							if (content.hasTitle()) {
								doc.add(new StringField("title", content.getTitle(), Field.Store.YES));							
							}
							if (content.hasDescription()) {
								doc.add(new StringField("description", content.getDescription(), Field.Store.YES));
							}
							if (content.hasBody()) {
								doc.add(new TextField("content", content.getBody(), Field.Store.NO));
							}
							indexWriter.addDocument(doc);
							indexed = true;

							LOG.debug("File added to index: " + docPath.toString() +"  "+ ++counter + " of " + haveToIndexSize);
						} else {
							haveToIndexSize--;
						}
					} catch (final UnavialableParserException e) {

						LOG.error("Can't find parser for file: " + docPath.getPath());
						LOG.debug(e.getMessage());
						haveToIndexSize--;
					} catch (final IOException e) {

						LOG.error("Error parsing file: " + docPath.getPath());
						LOG.debug(e.getMessage());
						haveToIndexSize--;
					} catch (final SAXException e) {

						LOG.error("Error parsing file: " + docPath.getPath());
						LOG.debug(e.getMessage());
						haveToIndexSize--;
					} catch (final TikaException e) {

						LOG.error("Error parsing file: " + docPath.getPath());
						LOG.debug(e.getMessage());
						haveToIndexSize--;
					}
				}

				((IndexedDocument) docPath).setLastIndexedTime(new java.sql.Timestamp(new Date().getTime()));
				((IndexedDocument) docPath).setIndexState(indexed ? "INDEXED" : "ERROR");
				docPathList.add((IndexedDocument) docPath);
			}
		} finally {

			try {

				indexWriter.commit();
				this.indexedDocumentBean.insertList(docPathList);
			} catch (IOException e) {

				LOG.error("ERROR while trying to commit index changes");
				LOG.debug(e.getMessage());
			}
		}
	}

	public void run() {

		indexedDocumentBean.createTable();
		removeModifiedDeletedDocsFromIndex();
		doIndex();
	}
}
