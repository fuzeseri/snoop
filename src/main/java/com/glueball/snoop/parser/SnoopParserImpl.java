package com.glueball.snoop.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import com.glueball.snoop.entity.Content;

public class SnoopParserImpl implements SnoopParser {

	protected final BodyContentHandler handler = new BodyContentHandler(Integer.MAX_VALUE);
	protected final Metadata metadata = new Metadata();
	protected final ParseContext context = new ParseContext();	
	protected Parser parser;

	protected SnoopParserImpl(final Parser parser) {
		this.parser = parser;
	}

	protected SnoopParserImpl() {
	}

	public Content parseContent(final String _uri) throws IOException, SAXException, TikaException {
		final File path = new File(_uri);
		final InputStream is = new FileInputStream(path);
		return parse(is);
	}

	public Content parseContent(final File path) throws IOException, SAXException, TikaException {
		final InputStream is = new FileInputStream(path);
		return parse(is);
	}

	public Content parseContent(final InputStream is) throws IOException, SAXException, TikaException {
		return parse(is);
	}

	protected Content parse(final InputStream input) throws IOException, SAXException, TikaException {

		try {
			parser.parse(input, handler, metadata, context);
	
			String title  = "";
			String author = "";
	
			for (final String name : metadata.names()) {
				if (name.equalsIgnoreCase("title")) {
					title = metadata.get(name);
				}
				if (name.equalsIgnoreCase("author")) {
					author = title = metadata.get(name);
				}
			}
	
			return new Content(author, title, handler.toString());
		} catch (final RuntimeException e) {
			throw new TikaException("Can't parse content. " + e.getLocalizedMessage());
		}
	}

	public void setLuceneParser(final Parser _parser) {
		this.parser = _parser;
	}

}
