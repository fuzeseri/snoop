package com.glueball.snoop.parser; 

public class AdobeFontMetricParser extends AbstractParser {

	public AdobeFontMetricParser () {
		super(new org.apache.tika.parser.font.AdobeFontMetricParser());
	}
}
