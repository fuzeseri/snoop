package com.glueball.snoop.module.main.ui;

import org.fusesource.restygwt.client.JsonEncoderDecoder;

import com.glueball.snoop.module.main.model.SearchResult;
import com.glueball.snoop.module.main.model.SearchResults;
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
	public @UiField TextBox searchBox;
	public @UiField Button  searchButton;

	private final VerticalPanel hits;
	
	private static SnoopHeadUiBinder uiBinder = GWT.create(SnoopHeadUiBinder.class);
	private Widget widget;

	interface SnoopHeadUiBinder extends UiBinder<Widget, SnoopHead> {
	}

	interface SearchCodec extends JsonEncoderDecoder<SearchResults> {
	}

	private final SearchCodec codec = GWT.create(SearchCodec.class);

	private final SnoopRequest<SearchResults> req = new SnoopRequest<SearchResults>(codec);

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

		req.getEntity(URLHelper.SEARCH, keyword, new SnoopRequestCallback<SearchResults>() {

			@Override
			public void onSuccess(final SearchResults results) {

				hits.clear();
				for (final SearchResult doc : results) {

					final Result result = new Result(doc);
					hits.add(result.asWidget());
				}
			}
		});
	}
}
