package com.glueball.snoop.parser; 

public class AudioParser extends AbstractParser {

	public AudioParser () {
		super(new org.apache.tika.parser.audio.AudioParser());
	}
}
