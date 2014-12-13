package com.glueball.snoop.bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.glueball.snoop.dao.DocumentPathBean;
import com.glueball.snoop.entity.DocumentPath;

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

	public void run() {
		try {
			indexWriter.deleteAll();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		for (final DocumentPath docPath : docPathBean.selectAll()) {
			final Document doc = new Document();
			doc.add(new StringField("id", 			docPath.getId(),       Field.Store.YES));
			doc.add(new StringField("fileName", 	docPath.getFileName(), Field.Store.YES));
			doc.add(new StringField("path", 		docPath.getPath(),     Field.Store.YES));
			doc.add(new StringField("uri", 			docPath.getUri(),      Field.Store.YES));
			doc.add(new StringField("contentType", 	docPath.getContentType(), Field.Store.YES));
			try {
				doc.add(new TextField("content", new FileReader(new File(docPath.getPath()))));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			try {
				System.out.println("Indexing file: " + docPath.toString());
				indexWriter.addDocument(doc);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			indexWriter.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
