package com.glueball.snoop.parser; 

import org.apache.tika.parser.pdf.PDFParserConfig;



public class PDFParser extends AbstractParser {

	private final PDFParserConfig pconfig = new PDFParserConfig();

	public PDFParser () {
		super(new org.apache.tika.parser.pdf.PDFParser());
		pconfig.setExtractInlineImages(false);
		//pconfig.setAverageCharTolerance((float) 23.33);
		pconfig.setEnableAutoSpace(true);
		pconfig.setExtractAcroFormContent(true);
		pconfig.setExtractAnnotationText(true);
		pconfig.setUseNonSequentialParser(true);
		((org.apache.tika.parser.pdf.PDFParser) parser).setPDFParserConfig(pconfig);
	}

	public void setExtractInlineImages(boolean _val) {
		pconfig.setExtractInlineImages(_val);
	}

	public void setAverageCharTolerance(float _val) {
		pconfig.setAverageCharTolerance(_val);
	}

	public void setEnableAutoSpace(boolean _val) {
		pconfig.setEnableAutoSpace(_val);
	}

	public void setExtractAcroFormContent(boolean _val) {
		pconfig.setExtractAcroFormContent(true);
	}

	public void setExtractAnnotationText(boolean _val) {
		pconfig.setExtractAnnotationText(_val);
	}

	public void setUseNonSequentialParser(boolean _val) {
		pconfig.setUseNonSequentialParser(_val);
	}
}
