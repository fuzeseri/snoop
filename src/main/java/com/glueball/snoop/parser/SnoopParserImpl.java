/**
 * 
 */
package com.glueball.snoop.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ParsingReader;
import org.springframework.beans.factory.annotation.Required;
import org.xml.sax.SAXException;

import com.glueball.snoop.entity.Meta;
import com.glueball.snoop.metadata.extractor.MetaDataExtractor;

/**
 * @author karesz
 */
public class SnoopParserImpl implements SnoopParser {

    /**
     * Tika parser instance.
     */
    private Parser tikaParser;

    /**
     * @param pParser
     *            the tika parser to set
     */
    @Required
    public final void setTikaParser(final Parser pParser) {

        this.tikaParser = pParser;
    }

    /**
     * MetaDataExtractor service.
     */
    private MetaDataExtractor metaDataExtractor;

    /**
     * @param pMetaDataExtractor
     *            the metaDataExtractor to set
     */
    @Required
    public final void setMetaDataExtractor(
            final MetaDataExtractor pMetaDataExtractor) {

        this.metaDataExtractor = pMetaDataExtractor;
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.parser.SnoopParser#parseContent(java.lang.String,
     * java.io.Writer)
     */
    @Override
    public final Reader parseContent(final String pUri, final Meta meta)
            throws IOException, SAXException, TikaException {

        try (final InputStream is = new FileInputStream(new File(pUri))) {

            return parse(is, meta);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.parser.SnoopParser#parseContent(java.io.File,
     * java.io.Writer)
     */
    @Override
    public final Reader parseContent(final File path, final Meta meta)
            throws IOException, SAXException, TikaException {

        try (final InputStream is = new FileInputStream(path)) {

            return parse(is, meta);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.glueball.snoop.parser.SnoopParser#parseContent(java.io.InputStream,
     * java.io.Writer)
     */
    @Override
    public final Reader parseContent(final InputStream is, final Meta meta)
            throws IOException, SAXException, TikaException {

        return parse(is, meta);
    }

    /**
     * Parse content from a file.
     *
     * @param input
     *            InputStream with the file content.
     * @param output
     *            Writer object to write the file text content to it.
     * @return Meta object filled with the parsed meta data.
     * @throws IOException
     *             on unsuccessful file read.
     * @throws SAXException
     *             on unsuccessful sax operation.
     * @throws TikaException
     *             on unsuccessful content parse operation (e.g. unsopported
     *             font type)
     */
    private final Reader parse(final InputStream input, final Meta meta)
            throws IOException, SAXException, TikaException {

        try {

            final Metadata metadata = new Metadata();
            final ParseContext context = new ParseContext();
            this.metaDataExtractor.extractMetaData(metadata, meta);

            return new ParsingReader(tikaParser, input, metadata, context);

        } catch (final RuntimeException e) {

            throw new TikaException("Can't parse content. "
                    + e.getLocalizedMessage());
        }
    }
}
