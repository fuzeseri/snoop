package com.glueball.snoop.bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import org.springframework.beans.factory.annotation.Autowired;

import com.glueball.snoop.dao.DocumentPathBean;
import com.glueball.snoop.dao.IndexedDocumentBean;
import com.glueball.snoop.entity.IndexedDocument;
import com.glueball.snoop.entity.SnoopDocument;

public class DataIndexer implements Runnable {

	private static final Logger LOG = LogManager.getLogger(DataIndexer.class);

	@Autowired
	IndexWriter indexWriter;

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
	
	public void run() {
		final List<IndexedDocument> indexedList = new ArrayList<IndexedDocument>();
		for (final SnoopDocument docPath : docPathBean.haveToIndex()) {
			final Document doc = new Document();
			doc.add(new StringField("id", 			docPath.getId(),       Field.Store.YES));
			doc.add(new StringField("fileName", 	docPath.getFileName(), Field.Store.YES));
			doc.add(new StringField("path", 		docPath.getPath(),     Field.Store.YES));
			doc.add(new StringField("uri", 			docPath.getUri(),      Field.Store.YES));
			doc.add(new StringField("contentType", 	docPath.getContentType(), Field.Store.YES));
			try {
				doc.add(new TextField("content", new FileReader(new File(docPath.getPath()))));
			} catch (final FileNotFoundException e1) {
				LOG.info("Can't open file: " + docPath.getPath(), e1);
			}
			try {
				LOG.info("Indexing file: " + docPath.toString());
				indexWriter.addDocument(doc);
				((IndexedDocument) docPath).setLastIndexedTime(new java.sql.Timestamp(new Date().getTime()));
				indexedList.add((IndexedDocument)docPath);
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
