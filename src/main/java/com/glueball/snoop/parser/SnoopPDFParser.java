package com.glueball.snoop.parser;

import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.parser.pdf.PDFParserConfig;

public class SnoopPDFParser extends SnoopParserImpl {

    private PDFParserConfig pconfig = new PDFParserConfig();

    public SnoopPDFParser() {
	final PDFParser pdfParser = new PDFParser();
	pconfig.setExtractInlineImages(false);
	// pconfig.setAverageCharTolerance((float) 23.33);
	pconfig.setEnableAutoSpace(true);
	pconfig.setExtractAcroFormContent(true);
	pconfig.setExtractAnnotationText(true);
	pconfig.setUseNonSequentialParser(true);
	pdfParser.setPDFParserConfig(pconfig);
	setLuceneParser(pdfParser);
    }

    public SnoopPDFParser(final PDFParserConfig _pconfig) {
	this.pconfig = _pconfig;
	final PDFParser pdfParser = new PDFParser();
	pdfParser.setPDFParserConfig(pconfig);
	setLuceneParser(pdfParser);
    }

    public void setPDFParserConfig(final PDFParserConfig _config) {
	this.pconfig = _config;
    }
}
