package com.glueball.snoop.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import com.glueball.snoop.dao.IndexedDocumentBean;
import com.glueball.snoop.entity.Content;
import com.glueball.snoop.entity.IndexedDocument;
import com.glueball.snoop.parser.ParserMap;
import com.glueball.snoop.parser.UnavialableParserException;

public class DataIndexer implements Runnable {

	private static final Logger LOG = Logger.getLogger(DataIndexer.class);

	@Autowired
	private IndexWriter indexWriter;

	public void setIndexWriter(IndexWriter indexWriter) {
		this.indexWriter = indexWriter;
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

	private void doIndex(final List<IndexedDocument> haveToIndexList) {

		try {

			long haveToIndexSize = haveToIndexList.size();
			long counter = 0;

			for (final IndexedDocument idoc : haveToIndexList) {

				boolean indexed = false;

				if (!this.parserMap.hasParser(idoc.getContentType())) {

					LOG.debug("Can't find parser for content. File name: " + idoc.getFileName() + " content-type: " + idoc.getContentType());
					haveToIndexSize--;
				} else {

					LOG.debug("Indexing file: " + idoc.toString());
					try {
						final Content content = this.parserMap.getParser(idoc.getContentType()).parseContent(idoc.getPath());
						if (content.hasContent()) {

							final Document doc = new Document();
							doc.add(new StringField("id", 			idoc.getId(),       Field.Store.YES));
							doc.add(new StringField("fileName", 	idoc.getFileName(), Field.Store.YES));
							doc.add(new StringField("path", 		idoc.getPath(),     Field.Store.YES));
							doc.add(new StringField("uri", 			idoc.getUri(),      Field.Store.YES));
							doc.add(new StringField("contentType", 	idoc.getContentType(), Field.Store.YES));

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

							LOG.debug("File added to index: " + idoc.toString() +"  "+ ++counter + " of " + haveToIndexSize);
						} else {
							haveToIndexSize--;
						}
					} catch (final UnavialableParserException e) {

						LOG.error("Can't find parser for file: " + idoc.getPath());
						LOG.debug(e.getMessage());
						haveToIndexSize--;
					} catch (final IOException e) {

						LOG.error("Error parsing file: " + idoc.getPath());
						LOG.debug(e.getMessage());
						haveToIndexSize--;
					} catch (final SAXException e) {

						LOG.error("Error parsing file: " + idoc.getPath());
						LOG.debug(e.getMessage());
						haveToIndexSize--;
					} catch (final TikaException e) {

						LOG.error("Error parsing file: " + idoc.getPath());
						LOG.debug(e.getMessage());
						haveToIndexSize--;
						e.printStackTrace();
					}
				}

				idoc.setLastIndexedTime(new java.sql.Timestamp(new Date().getTime()));
				idoc.setIndexState(indexed ? "INDEXED" : "ERROR");
			}
		} finally {

			try {

				indexWriter.commit();
				this.indexedDocumentBean.updateState(haveToIndexList);
			} catch (IOException e) {

				LOG.error("ERROR while trying to commit index changes");
				LOG.debug(e.getMessage());
			}
		}
	}

	public void run() {

		indexedDocumentBean.createTable();
		final List<IndexedDocument> haveToIndexList = indexedDocumentBean.haveToIndex();

		final List<String> toRemove = new ArrayList<String>();
		for (final IndexedDocument idoc : haveToIndexList) {
			if (   IndexedDocument.INDEX_STATE_DELETED.equals(idoc.getIndexState())
				|| IndexedDocument.INDEX_STATE_MODIFIED.equals(idoc.getIndexState())
				|| IndexedDocument.INDEX_STATE_REINDEX.equals(idoc.getIndexState())
					) {
				toRemove.add(idoc.getId());
			}
		}
		removeModifiedDeletedDocsFromIndex(toRemove);
		doIndex(haveToIndexList);
	}
}
