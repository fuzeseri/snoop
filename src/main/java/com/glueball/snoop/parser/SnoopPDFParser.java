package com.glueball.snoop.parser;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.parser.pdf.PDFParserConfig;

public class SnoopPDFParser extends SnoopParserImpl {

    private PDFParserConfig pconfig = new PDFParserConfig();

    public SnoopPDFParser() {

        final PDFParser pdfParser = new PDFParser();
        pconfig.setExtractInlineImages(false);
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
