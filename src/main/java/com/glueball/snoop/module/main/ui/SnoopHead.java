package com.glueball.snoop.module.main.ui;

import java.util.List;

import org.fusesource.restygwt.client.JsonEncoderDecoder;

import com.glueball.snoop.entity.SearchResult;
import com.glueball.snoop.module.util.SnoopRequest;
import com.glueball.snoop.module.util.SnoopRequestCallback;
import com.glueball.snoop.module.util.URLHelper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SnoopHead extends Composite {

	@UiField Image   logo;
	@UiField TextBox searchBox;
	@UiField Button  searchButton;
	
	private final VerticalPanel hits;
	
	private static SnoopHeadUiBinder uiBinder = GWT.create(SnoopHeadUiBinder.class);
	private Widget widget;

	interface SnoopHeadUiBinder extends UiBinder<Widget, SnoopHead> {
	}

	interface SearchCodec extends JsonEncoderDecoder<List<SearchResult>> {
	}

	private final SearchCodec codec = GWT.create(SearchCodec.class);

	private final SnoopRequest<List<SearchResult>> req = new SnoopRequest<List<SearchResult>>(codec);

	public SnoopHead(final VerticalPanel _hits) {
		this.hits = _hits;
		this.widget = uiBinder.createAndBindUi(this);
		logo.setAltText("snoop");
		logo.setHeight("26px");
		logo.setUrl(URLHelper.getImageUrl("VectorToons-dog4.jpg"));
	}

	public TextBox getSearchBox() {
		return searchBox;
	}

	public Button getSearchButton() {
		return searchButton;
	}

	@Override
	public Widget asWidget() {
		return this.widget;
	}

	@UiHandler("searchButton")
	public void buttonHandler(final ClickEvent evt) {
		search(searchBox.getText());
	}

	@UiHandler("searchBox")
	public void boxHandler(final KeyDownEvent evt) {

		if (evt.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			search(searchBox.getText());
		}
	}

	private void search(final String keyword) {

		req.getEntity(URLHelper.SEARCH, keyword, new SnoopRequestCallback<List<SearchResult>>() {

			@Override
			public void onSuccess(final List<SearchResult> entity) {

				hits.clear();
				for (final SearchResult doc : entity) {
					final Result result = new Result(doc);
					hits.add(result.asWidget());
				}
			}
		});
	}
}
