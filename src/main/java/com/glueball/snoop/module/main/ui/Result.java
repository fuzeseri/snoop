package com.glueball.snoop.module.main.ui;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import com.glueball.snoop.module.main.model.SearchResult;
import com.glueball.snoop.module.util.URLHelper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * View of the search results on web ui.
 *
 * @author karesz
 */
public final class Result extends Composite {

    /**
     * Title of the document.
     */
    @UiField
    Label title;

    /**
     * Author of the document.
     */
    @UiField
    Label author;

    /**
     * Description of the document.
     */
    @UiField
    Label description;

    /**
     * Link to the uri of the file.
     */
    @UiField
    Label link;

    /**
     * Mime icon image.
     */
    @UiField
    Image mimeIcon;

    /**
     * GWT uibinder instance.
     */
    private static ResultUiBinder uiBinder = GWT.create(ResultUiBinder.class);

    /**
     * GWT uibinder interface declaration.
     *
     * @author karesz
     */
    interface ResultUiBinder extends UiBinder<Widget, Result> {
    }

    /**
     * Widget object.
     */
    private final Widget widget;

    /**
     * The search result.
     */
    private final SearchResult result;

    /**
     * Constructor.
     *
     * @param res
     *            the SearchResult to set.
     */
    public Result(final SearchResult res) {

        this.widget = uiBinder.createAndBindUi(this);
        this.result = res;
        init();
    }

    /**
     * Initialization method.
     */
    private void init() {

        final String fileExt = this.result
                .getFileName()
                .substring(this.result.getFileName().lastIndexOf(".") + 1,
                        this.result.getFileName().length()).toLowerCase();

        mimeIcon.setAltText(fileExt);
        mimeIcon.setUrl(URLHelper.getImageUrl(fileExt + ".png"));

        title.setText(this.result.getTitle());
        author.setText(this.result.getAuthor());
        description.setText(this.result.getDescription());
        link.setText(this.result.getFileName());
    }

    /*
     * (non-Javadoc)
     * @see com.google.gwt.user.client.ui.Widget#asWidget()
     */
    @Override
    public Widget asWidget() {

        return this.widget;
    }

    /**
     * OnClick event handler od the link.
     *
     * @param event
     *            ClickEvent
     */
    @UiHandler("link")
    public void linkClickHandler(final ClickEvent event) {

        openFile(this.result.getUri());
    }

    public static native void openFile(final String uri) /*-{

		$wnd.snoopObj.openFile(uri);
    }-*/;
}
