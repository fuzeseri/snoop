package com.glueball.snoop.module.main.ui;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import com.glueball.snoop.module.ui.CrmContainer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainWindow extends CrmContainer {

    @UiField
    SimplePanel headPanel;
    @UiField
    VerticalPanel hits;

    private static MainWindowUiBinder uiBinder = GWT
            .create(MainWindowUiBinder.class);

    interface MainWindowUiBinder extends UiBinder<Widget, MainWindow> {
    }

    public MainWindow() {

        initWidget(uiBinder.createAndBindUi(this));
    }

    public SimplePanel getHeadPanel() {

        return headPanel;
    }

    public VerticalPanel getHits() {

        return hits;
    }

}
