package com.glueball.snoop.entity;

public class Content {
	
	private final String author;
	private final String title;
	private final String body;
	
	public Content(final String _author,final String _title, final String _body) {
		this.author = _author;
		this.title = _title;
		this.body = _body;
	}

	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}
}
