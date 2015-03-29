package com.glueball.snoop.module.main.ui;

import org.fusesource.restygwt.client.JsonEncoderDecoder;

import com.glueball.snoop.module.main.model.SearchResult;
import com.glueball.snoop.module.main.model.SearchResults;
import com.glueball.snoop.module.main.model.ServerMessage;
import com.glueball.snoop.module.util.SnoopRequest;
import com.glueball.snoop.module.util.SnoopRequestCallback;
import com.glueball.snoop.module.util.URLHelper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.TextDecoration;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SnoopHead extends Composite {

    public @UiField TextBox searchBox;
    public @UiField Button searchButton;

    @UiField
    Image logo;
    @UiField
    HorizontalPanel pages;
    @UiField
    Label totalHits;

    private final VerticalPanel hits;

    private static SnoopHeadUiBinder uiBinder = GWT
	    .create(SnoopHeadUiBinder.class);
    private Widget widget;

    interface SnoopHeadUiBinder extends UiBinder<Widget, SnoopHead> {
    }

    interface SearchCodec extends JsonEncoderDecoder<SearchResults> {
    }

    private final SearchCodec codec = GWT.create(SearchCodec.class);

    private final SnoopRequest<SearchResults> req = new SnoopRequest<SearchResults>(
	    codec);

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

	search(searchBox.getText(), 1);
    }

    @UiHandler("searchBox")
    public void boxHandler(final KeyDownEvent evt) {

	if (evt.getNativeKeyCode() == KeyCodes.KEY_ENTER) {

	    search(searchBox.getText(), 1);
	}
    }

    private void search(final String keyword, int page) {

	req.request(URLHelper.SEARCH + keyword + "?page=" + page,
		new SnoopRequestCallback<SearchResults>() {

		    @Override
		    public void onSuccess(final SearchResults results) {

			hits.clear();
			for (final SearchResult doc : results) {

			    final Result result = new Result(doc);
			    hits.add(result.asWidget());
			}

			pages.clear();
			for (final int page : results.getPages()) {

			    final Label pageNum = new Label();
			    pageNum.setText(String.valueOf(page));
			    pageNum.getElement().getStyle()
				    .setCursor(Cursor.POINTER);
			    pageNum.getElement().getStyle()
				    .setPadding(1, Unit.PX);

			    if (results.getCurrentPage() == page) {

				pageNum.getElement().getStyle()
					.setColor("blue");
				pageNum.getElement().getStyle()
					.setFontWeight(FontWeight.BOLD);
			    } else {

				pageNum.getElement()
					.getStyle()
					.setTextDecoration(
						TextDecoration.UNDERLINE);
			    }

			    pageNum.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

				    search(searchBox.getText(), page);
				}

			    });
			    pages.add(pageNum.asWidget());
			}

			totalHits.setText(String.valueOf(results.getTotalHits()));
		    }

		    @Override
		    public void onMessage(final ServerMessage message) {

			hits.clear();
			hits.add(new Message(message).asWidget());
			pages.clear();
			totalHits.setText("");
		    }

		});
    }
}
