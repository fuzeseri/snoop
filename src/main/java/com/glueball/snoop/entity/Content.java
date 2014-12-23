package com.glueball.snoop.entity;

import org.apache.cxf.common.util.StringUtils;

public class Content {

	private final String author;
	private final String title;
	private final String description;
	private final String body;

	public Content(final String _author,final String _title, final String _description, final String _body) {
		this.author = _author;
		this.title = _title;
		this.body = _body;
		this.description = _description;
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

	public String getDescription() {
		return description;
	}

	public boolean hasContent() {
		return (title.length() + author.length() + description.length() + body.length()) > 0;
	}

	public boolean hasTitle() {
		return !StringUtils.isEmpty(title);
	}

	public boolean hasAuthor() {
		return !StringUtils.isEmpty(author);
	}

	public boolean hasDescription() {
		return !StringUtils.isEmpty(description);
	}

	public boolean hasBody() {
		return !StringUtils.isEmpty(body);
	}

	@Override
	public String toString() {
		return "author: " + author + " ,title: " + title + " ,description: " + description + " ,body: " + body;
	}
}
