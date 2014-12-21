package com.glueball.snoop.parser;

import java.util.HashMap;
import java.util.Map;

public final class ParserMap {

	private final Map<String, SnoopParser> parserMap = new HashMap<String, SnoopParser>();

	@SuppressWarnings("unused")
	private ParserMap() {
	}

	public ParserMap(final Map<String, SnoopParser> _parserMap) {
		this.parserMap.putAll(_parserMap);
	}

	public boolean hasParser(final String contentType) {
		return this.parserMap.containsKey(contentType);
	}

	public SnoopParser getParser(final String contentType) throws UnavialableParserException {
		return this.parserMap.get(contentType);
	}
}
