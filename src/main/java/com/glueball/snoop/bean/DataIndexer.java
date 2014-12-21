package com.glueball.snoop.bean;

import java.io.IOException;
import java.io.InputStream;
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
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import com.glueball.snoop.dao.DocumentPathBean;
import com.glueball.snoop.dao.IndexedDocumentBean;
import com.glueball.snoop.entity.Content;
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

	public void run() {

		//indexedDocumentBean.deleteALL();

		final List<IndexedDocument> indexedList = new ArrayList<IndexedDocument>();

		long haveToIndexSize = docPathBean.haveToIndex().size();
		long counter = 0;

		for (final SnoopDocument docPath : docPathBean.haveToIndex()) {

			final Document doc = new Document();

			doc.add(new StringField("id", 			docPath.getId(),       Field.Store.YES));
			doc.add(new StringField("fileName", 	docPath.getFileName(), Field.Store.YES));
			doc.add(new StringField("path", 		docPath.getPath(),     Field.Store.YES));
			doc.add(new StringField("uri", 			docPath.getUri(),      Field.Store.YES));
			doc.add(new StringField("contentType", 	docPath.getContentType(), Field.Store.YES));

			if (!this.parserMap.hasParser(docPath.getContentType())) {
				LOG.info("Can't find parser for content. File name: " + docPath.getFileName() + " content-type: " + docPath.getContentType());
			} else {
				LOG.info("Indexing file: " + docPath.toString());
				Content content;
				try {
					content = this.parserMap.getParser(docPath.getContentType()).parseContent(docPath.getPath());
					doc.add(new StringField("author", content.getAuthor(), Field.Store.YES));
					doc.add(new StringField("title", content.getTitle(), Field.Store.YES));
					doc.add(new TextField("content", content.getBody(), Field.Store.NO));
				} catch (final UnavialableParserException e) {
					LOG.error("Can't find parser for file: " + docPath.getPath(), e);
				} catch (final IOException e) {
					LOG.error("Error parsing file: " + docPath.getPath(), e);
				} catch (final SAXException e) {
					LOG.error("Error parsing file: " + docPath.getPath(), e);
				} catch (final TikaException e) {
					LOG.error("Error parsing file: " + docPath.getPath(), e);
				}
			}

			try {
				indexWriter.addDocument(doc);
				((IndexedDocument) docPath).setLastIndexedTime(new java.sql.Timestamp(new Date().getTime()));
				indexedList.add((IndexedDocument)docPath);
				LOG.info(++counter + " of " + haveToIndexSize + " files currently indexed");
			} catch (final IOException e) {
				LOG.error("Error when add document to index: ", e);
			}
		}

		try {

			indexWriter.commit();
			this.indexedDocumentBean.createTable();
			this.indexedDocumentBean.insertList(indexedList);
		} catch (IOException e) {

			LOG.error("Error commit index changes: ", e);
		}
	}

}
