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

	private FileVisitor<Path> visitor;

	public void setVisitor(final FileVisitor<Path> visitor) {
		this.visitor = visitor;
	}

	private final List<DocumentPath> docs = new ArrayList<DocumentPath>();

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
		this.visitor = new DbLoaderVisitor(docs, parserMap, mimeFileextMap);
		try {
			this.docPathBean.createTable();
			this.docPathBean.deleteData();
			Files.walkFileTree(source, visitor);
			this.docPathBean.insertList(docs);
		} catch (final IOException e) {
			LOG.info(e.getMessage());
		}
	}

}
