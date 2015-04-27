package com.glueball.snoop.file.visitor;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.apache.cxf.common.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.entity.NetworkShare;
import com.glueball.snoop.util.MD5;

/**
 * File Visitor implementation. Check if the visited file has a parsable content
 * and puts it to the documentpaths list if it has.
 * 
 * @author karesz
 */
public class DbLoaderVisitor implements FileVisitor<Path> {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LogManager
            .getLogger(DbLoaderVisitor.class);

    /**
     * The DocumentPath list to put the supported files into it.
     */
    private final Map<String, DocumentPath> docs;

    /**
     * The network share object.
     */
    private final NetworkShare share;

    /**
     * Constructor.
     *
     * @param pDocs
     *            the DocumentPath list to set.
     * @param pParserMap
     *            the ParserMap to set.
     * @param pShare
     *            the NetworkShare to set.
     */
    public DbLoaderVisitor(final Map<String, DocumentPath> pDocs,
            final NetworkShare pShare) {

        this.docs = pDocs;
        this.share = pShare;
    }

    /*
     * (non-Javadoc)
     * @see java.nio.file.FileVisitor#preVisitDirectory(java.lang.Object,
     * java.nio.file.attribute.BasicFileAttributes)
     */
    @Override
    public FileVisitResult preVisitDirectory(final Path dir,
            final BasicFileAttributes attrs) throws IOException {

        if (attrs.isDirectory()
                && dir.getFileName().toString().startsWith(".")) {

            return FileVisitResult.SKIP_SUBTREE;
        }
        return FileVisitResult.CONTINUE;
    }

    /*
     * (non-Javadoc)
     * @see java.nio.file.FileVisitor#visitFile(java.lang.Object,
     * java.nio.file.attribute.BasicFileAttributes)
     */
    @Override
    public FileVisitResult visitFile(final Path file,
            final BasicFileAttributes attrs) throws IOException {

        if (attrs.isRegularFile() && !attrs.isDirectory()) {

            final DocumentPath doc = new DocumentPath();

            try {

                doc.setId(MD5.md5Digest(file.toUri().toString()));
            } catch (final NoSuchAlgorithmException e) {

                LOG.error("Error generating docuemnt id."
                        + "MD5 algorith is not supported.");
                throw new IOException(e);
            }

            doc.setShareName(share.getName());
            doc.setFileName(file.getFileName().toString());

            doc.setLocalPath(file.toAbsolutePath().toString());

            final String remotePath = !StringUtils.isEmpty(share
                    .getLocalPath()) ? file.toAbsolutePath().toString()
                    .replace(share.getLocalPath(),
                            share.getRemotePath())
                    : file.toAbsolutePath().toString();

            doc.setPath(remotePath);
            doc.setUri(Paths.get(remotePath).toUri().toString());
            doc.setLastModifiedTime(attrs.lastModifiedTime().toMillis());

            docs.put(MD5.toHexString(doc.getId()), doc);
        }
        return FileVisitResult.CONTINUE;
    }

    /*
     * (non-Javadoc)
     * @see java.nio.file.FileVisitor#visitFileFailed(java.lang.Object,
     * java.io.IOException)
     */
    @Override
    public FileVisitResult visitFileFailed(final Path file,
            final IOException exc) throws IOException {

        return FileVisitResult.CONTINUE;
    }

    /*
     * (non-Javadoc)
     * @see java.nio.file.FileVisitor#postVisitDirectory(java.lang.Object,
     * java.io.IOException)
     */
    @Override
    public FileVisitResult postVisitDirectory(final Path dir,
            final IOException exc) throws IOException {

        return FileVisitResult.CONTINUE;
    }
}
