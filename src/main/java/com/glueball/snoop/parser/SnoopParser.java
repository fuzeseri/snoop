package com.glueball.snoop.parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.Parser;
import org.springframework.beans.factory.annotation.Required;
import org.xml.sax.SAXException;

import com.glueball.snoop.entity.Content;

public interface SnoopParser {

	Content parseContent(final String _uri) throws IOException, SAXException, TikaException;
	Content parseContent(final File path)   throws IOException, SAXException, TikaException;
	Content parseContent(final InputStream is) throws IOException, SAXException, TikaException;
	@Required
	void setLuceneParser(final Parser parser);
}