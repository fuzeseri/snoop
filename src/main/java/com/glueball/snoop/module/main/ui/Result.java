package com.glueball.snoop.module.main.ui;


import com.glueball.snoop.module.main.model.SearchResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Result extends Composite {
	
	@UiField Label  title;
	@UiField Label  author;
	@UiField Label  description;
	@UiField Anchor link;

	private static ResultUiBinder uiBinder = GWT.create(ResultUiBinder.class);

	interface ResultUiBinder extends UiBinder<Widget, Result> {
	}

	private final Widget widget;

	public Result(final SearchResult res) {
		this.widget = uiBinder.createAndBindUi(this);
		title.setText(res.getTitle());
		author.setText(res.getAuthor());
		description.setText(res.getDescription());
		link = new Anchor(res.getFileName(), res.getUri());
	}

	@Override
	public Widget asWidget() {
		return this.widget;
	}
}
