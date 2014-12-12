package com.glueball.snoop.bean;

import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataLoader implements Runnable {

	private static final Logger LOG = LogManager.getLogger(DataLoader.class);

	private FileVisitor<Path> visitor;
	
	public void setVisitor(FileVisitor<Path> visitor) {
		this.visitor = visitor;
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
		try {
			Files.walkFileTree(source, visitor);
		} catch (final IOException e) {
			LOG.info(e.getMessage());
		}
	}

}
