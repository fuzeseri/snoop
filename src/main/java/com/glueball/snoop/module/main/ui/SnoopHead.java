package com.glueball.snoop.module.main.ui;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
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

/**
 * The header box on the web ui.
 *
 * @author karesz
 */
public final class SnoopHead extends Composite {

    /**
     * Textbox to write in the search string.
     */
    @UiField
    public TextBox searchBox;

    /**
     * Search button.
     */
    @UiField
    public Button searchButton;

    /**
     * Snoop logo.
     */
    @UiField
    Image logo;

    /**
     * HPanel to store the page selector links.
     */
    @UiField
    HorizontalPanel pages;

    /**
     * Label to show the total hits value.
     */
    @UiField
    Label totalHits;

    /**
     * Vertical panel to store the hits.
     */
    private final VerticalPanel hits;

    /**
     * GWT uibinder instance.
     */
    private static SnoopHeadUiBinder UIBINDER = GWT
            .create(SnoopHeadUiBinder.class);

    /**
     * Widget object.
     */
    private Widget widget;

    /**
     * GWT uibinder interface declaration.
     * 
     * @author karesz
     */
    interface SnoopHeadUiBinder extends UiBinder<Widget, SnoopHead> {
    }

    /**
     * GWT searchcodec interface declaration.
     * 
     * @author karesz
     */
    interface SearchCodec extends JsonEncoderDecoder<SearchResults> {
    }

    /**
     * Search codec instance.
     */
    private final SearchCodec codec = GWT.create(SearchCodec.class);

    /**
     * Http request object.
     */
    private final SnoopRequest<SearchResults> req =
            new SnoopRequest<SearchResults>(codec);

    /**
     * Constructor.
     *
     * @param pHits
     *            the vertical panel to store the hits.
     */
    public SnoopHead(final VerticalPanel pHits) {

        this.hits = pHits;
        this.widget = UIBINDER.createAndBindUi(this);
        logo.setAltText("snoop");
        logo.setHeight("26px");
        logo.setUrl(URLHelper.getImageUrl("VectorToons-dog4.jpg"));
    }

    /**
     * @return the searchBox
     */
    public TextBox getSearchBox() {

        return searchBox;
    }

    /**
     * @return the searchButton
     */
    public Button getSearchButton() {

        return searchButton;
    }

    /*
     * (non-Javadoc)
     * @see com.google.gwt.user.client.ui.Widget#asWidget()
     */
    @Override
    public Widget asWidget() {

        return this.widget;
    }

    @UiHandler("searchButton")
    public void buttonHandler(final ClickEvent evt) {

        search(searchBox.getText(), 1);
    }

    /**
     * KeyDown event handler method of the searchBox.
     *
     * @param evt
     *            KeyDownEvent to set.
     */
    @UiHandler("searchBox")
    public void boxHandler(final KeyDownEvent evt) {

        if (evt.getNativeKeyCode() == KeyCodes.KEY_ENTER) {

            search(searchBox.getText(), 1);
        }
    }

    /**
     * Sends search query to the server and shows the response.
     *
     * @param keyword
     *            the search String to send.
     * @param page
     *            the current page number.
     */
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

                        refreshPages(results);
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

    /**
     * Refreshes the page selector widget.
     *
     * @param results
     *            the result object.
     */
    private void refreshPages(final SearchResults results) {

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

        totalHits.setText(String
                .valueOf(results.getTotalHits()));
    }
}
