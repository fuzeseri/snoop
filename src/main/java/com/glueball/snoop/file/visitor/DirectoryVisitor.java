/**
 * 
 */
package com.glueball.snoop.file.visitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.glueball.snoop.util.MD5;

/**
 * @author karesz
 */
public class DirectoryVisitor implements FileVisitor<Path> {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LogManager
            .getLogger(DirectoryVisitor.class);

    private int dirCounter = 0;

    private int fileCounter = 0;

    /*
     * (non-Javadoc)
     * @see java.nio.file.FileVisitor#preVisitDirectory(java.lang.Object,
     * java.nio.file.attribute.BasicFileAttributes)
     */
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
            throws IOException {

        if (attrs.isDirectory()
                && dir.getFileName().toString().startsWith(".")) {
            LOG.debug(dir.getFileName().toString());
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
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
            throws IOException {

        final String dirn = file.toUri().toString();

        fileCounter++;

        try {
            String str = " -  " + MD5.md5Digest(dirn)
                    + " - " + dirn + " -- "
                    + fileCounter;

            final String contentType = Files.probeContentType(file);

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return FileVisitResult.CONTINUE;
    }

    /*
     * (non-Javadoc)
     * @see java.nio.file.FileVisitor#visitFileFailed(java.lang.Object,
     * java.io.IOException)
     */
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc)
            throws IOException {

        return FileVisitResult.CONTINUE;
    }

    /*
     * (non-Javadoc)
     * @see java.nio.file.FileVisitor#postVisitDirectory(java.lang.Object,
     * java.io.IOException)
     */
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc)
            throws IOException {

        final String dirn = dir.toUri().toString();

        try {
            String str = ++dirCounter + " -  " + MD5.md5Digest(dirn)
                    + " - " + dirn + " -- "
                    + fileCounter;

            LOG.debug(str);

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        fileCounter = 0;

        return FileVisitResult.CONTINUE;
    }
}
