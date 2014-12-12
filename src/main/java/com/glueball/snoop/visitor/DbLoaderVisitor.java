package com.glueball.snoop.visitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.util.MD5;

public class DbLoaderVisitor implements FileVisitor<Path> {
	
	private List<String> neededTypes = Arrays.asList(new String[]{".doc", ".xml", ".txt", ".xls", ".odf", ".rtf", ".java"});
	
	private final List<DocumentPath> docs;
	
	public DbLoaderVisitor(final List<DocumentPath> _docs) {
		this.docs = _docs;
	}
	
	public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
		if (attrs.isRegularFile() && !attrs.isDirectory()) {

			boolean needed = false;
			for (final String fileType : neededTypes) {
				needed = file.getFileName().toString().toLowerCase().endsWith(fileType);
				if (needed) break;
			}
			
			if (needed) {
				final DocumentPath doc = new DocumentPath();
				try {
					doc.setId(MD5.md5Digest(file.toUri().toString()));
				} catch (NoSuchAlgorithmException e) {
					throw new IOException(e);
				}
				doc.setMd5Sum("");
				doc.setFileName(file.getFileName().toString());
				doc.setContentType(Files.probeContentType(file));
				doc.setPath(file.toAbsolutePath().toString());
				doc.setUri(file.toUri().toString());
				doc.setLastIndexedTime(new java.sql.Timestamp(new Date().getTime()));
				doc.setLastModifiedTime(new java.sql.Timestamp(attrs.lastModifiedTime().toMillis()));
				docs.add(doc);
				System.out.println(file.toUri());
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
