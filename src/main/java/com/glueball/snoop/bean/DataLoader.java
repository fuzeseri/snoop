package com.glueball.snoop.bean;

import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.glueball.snoop.dao.DocumentPathBean;
import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.parser.MimeFileextMap;
import com.glueball.snoop.parser.ParserMap;
import com.glueball.snoop.visitor.DbLoaderVisitor;

public class DataLoader implements Runnable {

	private static final Logger LOG = LogManager.getLogger(DataLoader.class);

	@Autowired
	private DocumentPathBean docPathBean;

	public void setDocPathBean(final DocumentPathBean docPathBean) {
		this.docPathBean = docPathBean;
	}

	@Autowired
	private ParserMap parserMap;

	public void setParserMap(final ParserMap parserMap) {
		this.parserMap = parserMap;
	}

	@Autowired
	private MimeFileextMap mimeFileextMap;

	public void setPMimeFileextMap(final MimeFileextMap _mimeFileextMap) {
		this.mimeFileextMap = _mimeFileextMap;
	}

	private Path source;

	public void setSource(final Path source) {
		this.source = source;
	}

	public DataLoader() {
	}

	public DataLoader(final Path source) {
		this.source = source;
	}

	public DataLoader(final String source) {
		this.source = Paths.get(source);
	}

	public void run() {

		final List<DocumentPath> docs = new ArrayList<DocumentPath>();
		final FileVisitor<Path> visitor = new DbLoaderVisitor(docs, parserMap, mimeFileextMap);
		try {

			Files.walkFileTree(source, visitor);
			this.docPathBean.updateDocumentPath(docs);
			this.docPathBean.updateNewDocuments();
			this.docPathBean.updateModifiedDocumetns();
			this.docPathBean.updateDeletedDocuments();
		} catch (final IOException e) {

			LOG.error("IO ERROR when discovering files");
			LOG.debug(e.getMessage());
		}
	}
}
