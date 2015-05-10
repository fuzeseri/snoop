/**
 * 
 */
package com.glueball.snoop.metadata.extractor;

import org.apache.tika.metadata.Metadata;

import com.glueball.snoop.entity.Meta;

/**
 * Service to extract meta data from the parsed content.
 *
 * @author karesz
 */
public interface MetaDataExtractor {

    /**
     * Extracts meta data from the parsed content.
     *
     * @param metadata
     *            the tika metadat object.
     * @return snoop meta data object
     */
    Meta extractMetaData(final Metadata metadata);

    void extractMetaData(final Metadata metadata, final Meta meta);
}
