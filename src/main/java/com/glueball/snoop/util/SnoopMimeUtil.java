/**
 * 
 */
package com.glueball.snoop.util;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MimeTypes;

/**
 * @author karesz
 */
public final class SnoopMimeUtil {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LogManager.getLogger(SnoopMimeUtil.class);

    /**
     * Helper method to detect contenct media type of file.
     *
     * @param path
     *            the path of file.
     * @return the mime type.
     */
    public static String detectMimeType(final String path) {

        final Metadata metadata = new Metadata();
        metadata.add(Metadata.RESOURCE_NAME_KEY, path);

        final TikaConfig config = TikaConfig.getDefaultConfig();
        final MimeTypes mimeTypes = config.getMimeRepository();
        // final MimeTypes mimeTypes = new MimeTypes();

        final TikaInputStream inputStream = TikaInputStream.get(
                path.getBytes(),
                metadata);

        try {

            return mimeTypes.detect(inputStream, metadata).toString();
        } catch (final IOException e) {

            LOG.info("Error detecting content type of file: " + path);
            LOG.debug(e.getMessage(), e);
            e.printStackTrace();

            return "";
        }
    }
}
