package com.glueball.snoop.module.main.ui;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import com.glueball.snoop.module.main.model.SearchResult;
import com.glueball.snoop.module.util.URLHelper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Result extends Composite {

    @UiField
    Label title;
    @UiField
    Label author;
    @UiField
    Label description;
    @UiField
    Anchor link;
    @UiField
    Image mimeIcon;

    private static ResultUiBinder uiBinder = GWT.create(ResultUiBinder.class);

    interface ResultUiBinder extends UiBinder<Widget, Result> {
    }

    private final Widget widget;

    private final SearchResult result;

    public Result(final SearchResult res) {

        this.widget = uiBinder.createAndBindUi(this);
        this.result = res;
        init();
    }

    private void init() {

        final String fileExt = this.result.getFileName().substring(this.result.getFileName().lastIndexOf(".") + 1, this.result.getFileName().length())
                .toLowerCase();

        mimeIcon.setAltText(fileExt);
        mimeIcon.setUrl(URLHelper.getImageUrl(fileExt + ".png"));

        title.setText(this.result.getTitle());
        author.setText(this.result.getAuthor());
        description.setText(this.result.getDescription());
        link.setHref(this.result.getUri());
        link.setText(this.result.getFileName());
    }

    @Override
    public Widget asWidget() {

        return this.widget;
    }

    @UiHandler("link")
    public void linkClickHandler(final ClickEvent event) {

        Window.Location.replace(this.result.getUri());
    }
}
