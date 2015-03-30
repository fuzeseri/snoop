package com.glueball.snoop.visitor;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.cxf.common.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.entity.NetworkShare;
import com.glueball.snoop.parser.MimeFileextMap;
import com.glueball.snoop.parser.ParserMap;
import com.glueball.snoop.util.MD5;

public class DbLoaderVisitor implements FileVisitor<Path> {

    private static final Logger LOG = LogManager
            .getLogger(DbLoaderVisitor.class);

    private final ParserMap parserMap;

    private final MimeFileextMap mimeFileextMap;

    private final List<DocumentPath> docs;

    private final NetworkShare share;

    public DbLoaderVisitor(final List<DocumentPath> _docs,
            final ParserMap _parserMap, final MimeFileextMap _mimeFileextMap,
            final NetworkShare _share) {

        this.docs = _docs;
        this.parserMap = _parserMap;
        this.mimeFileextMap = _mimeFileextMap;
        this.share = _share;
    }

    @Override
    public FileVisitResult preVisitDirectory(final Path dir,
            final BasicFileAttributes attrs) throws IOException {

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(final Path file,
            final BasicFileAttributes attrs) throws IOException {

        if (attrs.isRegularFile() && !attrs.isDirectory()) {

            final String contentType = Files.probeContentType(file);

            if (parserMap.hasParser(contentType)
                    && mimeFileextMap.checkFile(contentType, file.getFileName()
                            .toString())) {

                final DocumentPath doc = new DocumentPath();

                try {

                    doc.setId(MD5.md5Digest(file.toUri().toString()));
                } catch (final NoSuchAlgorithmException e) {

                    throw new IOException(e);
                }

                doc.setShareName(share.getName());
                doc.setFileName(file.getFileName().toString());
                doc.setContentType(contentType);
                doc.setLocalPath(file.toAbsolutePath().toString());

                final String remotePath = !StringUtils.isEmpty(share
                        .getLocalPath()) ? file.toAbsolutePath().toString()
                        .replace(share.getLocalPath(), share.getRemotePath())
                        : file.toAbsolutePath().toString();

                doc.setPath(remotePath);
                doc.setUri(Paths.get(remotePath).toUri().toString());
                doc.setLastModifiedTime(new java.sql.Timestamp(attrs
                        .lastModifiedTime().toMillis()));
                docs.add(doc);

                LOG.info("File Path loaded: " + doc.getPath());
            }
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(final Path file,
            final IOException exc) throws IOException {

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(final Path dir,
            final IOException exc) throws IOException {

        return FileVisitResult.CONTINUE;
    }

}
