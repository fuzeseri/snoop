package com.glueball.snoop.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class ParseSingleFileContent {
	
	protected static final BodyContentHandler content = new BodyContentHandler(Integer.MAX_VALUE);
	protected static final Metadata metadata = new Metadata();
	protected static final ParseContext context = new ParseContext();	
	
	public static void main(String args[]) throws IOException, SAXException, TikaException {
		
//		SnoopParser parser = new SnoopParserImpl(new org.apache.tika.parser.image.ImageParser());
//		System.out.println(
//				parser.parseContent("/usr/share/cheese/icons/hicolor/32x32/actions/cheese-take-burst.png")
//				);
		
		PDFParser parser = new org.apache.tika.parser.pdf.PDFParser();
		
		File file = new File("/home/karesz/dokumentumok/php/Php_24_ora_alatt/22-Hibakereses.pdf");
		
		parser.parse(new FileInputStream(file), content, metadata, context);
		
		System.out.println(new String(content.toString().getBytes(), "utf-8"));
		
		for (String name : metadata.names()) {
			System.out.println(name + "  : " + metadata.get(name).toString());
		}
	}

}
