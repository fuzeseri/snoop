/**
 * 
 */
package com.glueball.snoop.file.visitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.cxf.common.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.glueball.snoop.entity.FileData;
import com.glueball.snoop.entity.FilePath;
import com.glueball.snoop.entity.IndexStatus;
import com.glueball.snoop.entity.NetworkShare;
import com.glueball.snoop.util.MD5;

/**
 * @author karesz
 */
public class SnoopFileVisitor implements FileVisitor<Path> {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LogManager
            .getLogger(SnoopFileVisitor.class);

    /**
     * The network share to scan.
     */
    private final NetworkShare share;

    /**
     * List to add the file data objects to it.
     */
    private final List<FileData> files;

    /**
     * List to add the file local paths to it.
     */
    private final Map<byte[], Set<FilePath>> localPaths;

    /**
     * List to add the file remote paths to it.
     */
    private final Map<byte[], Set<FilePath>> remotePaths;

    /**
     * Constructor.
     *
     * @param pShare
     *            The network share to scan.
     * @param pFiles
     *            List to add the file data objects to it.
     * @param pFilePaths
     *            List to add the file paths to it.
     */
    public SnoopFileVisitor(final NetworkShare pShare,
            final List<FileData> pFiles,
            final Map<byte[], Set<FilePath>> pLocalPaths,
            final Map<byte[], Set<FilePath>> pRemotePaths) {

        this.share = pShare;
        this.files = pFiles;
        this.localPaths = pLocalPaths;
        this.remotePaths = pRemotePaths;
    }

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

        if (attrs.isRegularFile() && !attrs.isDirectory()) {

            final FileData data = new FileData();

            try {
                final byte[] fileId = MD5.md5Digest(file.toUri().toString());
                data.setId(fileId);

                data.setDeleted((byte) 0);
                data.setLmtime(attrs.lastModifiedTime().toMillis());
                data.setStatus(IndexStatus.NEW.getStatus());

                final String localPath = file.toAbsolutePath().toString();

                final String remotePath = !StringUtils.isEmpty(share
                        .getLocalPath()) ? file.toAbsolutePath().toString()
                        .replace(share.getLocalPath(),
                                share.getRemotePath())
                        : file.toAbsolutePath().toString();

                files.add(data);
                localPaths.put(fileId, FilePath.getPaths(fileId, localPath));
                remotePaths.put(fileId, FilePath.getPaths(fileId, remotePath));

            } catch (final Exception e) {

                LOG.error("Error generating docuemnt id."
                        + "MD5 algorith is not supported.");
                throw new IOException(e);
            }

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

        return FileVisitResult.CONTINUE;
    }

}
