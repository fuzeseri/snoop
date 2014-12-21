package com.glueball.snoop.visitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.log4j.Logger;

import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.parser.ParserMap;
import com.glueball.snoop.util.MD5;


public class DbLoaderVisitor implements FileVisitor<Path> {

	private static final Logger LOG = Logger.getLogger(DbLoaderVisitor.class);

	private final ParserMap parserMap;

	private final List<DocumentPath> docs;

	public DbLoaderVisitor(final List<DocumentPath> _docs, final ParserMap _parserMap) {
		this.docs = _docs;
		this.parserMap = _parserMap;
	}

	public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {

		if (attrs.isRegularFile() && !attrs.isDirectory()) {
			
			final String contentType =  Files.probeContentType(file);

			if (parserMap.hasParser(contentType)) {

				final DocumentPath doc = new DocumentPath();

				try {
					doc.setId(MD5.md5Digest(file.toUri().toString()));
				} catch (NoSuchAlgorithmException e) {
					throw new IOException(e);
				}
				doc.setMd5Sum("");
				doc.setFileName(file.getFileName().toString());
				doc.setContentType(contentType);
				doc.setPath(file.toAbsolutePath().toString());
				doc.setUri(file.toUri().toString());
				doc.setLastModifiedTime(new java.sql.Timestamp(attrs.lastModifiedTime().toMillis()));
				docs.add(doc);

				LOG.info("File Path loaded: " + doc.getPath());
			}
		}
		return FileVisitResult.CONTINUE;
	}

	public FileVisitResult visitFileFailed(final Path file, final IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}

}
