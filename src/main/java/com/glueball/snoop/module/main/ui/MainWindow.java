package com.glueball.snoop.module.main.ui;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Main window of the web search ui.
 *
 * @author karesz
 */
public final class MainWindow extends Composite {

    /**
     * Simple panel to store head.
     */
    @UiField
    SimplePanel headPanel;

    /**
     * VerticalPanel to store hits label and value.
     */
    @UiField
    VerticalPanel hits;

    /**
     * GWT UIBinder object.
     */
    private static MainWindowUiBinder UIBINDER = GWT
            .create(MainWindowUiBinder.class);

    /**
     * GWT uibinder interface declaration.
     * 
     * @author karesz
     */
    interface MainWindowUiBinder extends UiBinder<Widget, MainWindow> {
    }

    /**
     * Default constructor.
     */
    public MainWindow() {

        initWidget(UIBINDER.createAndBindUi(this));
    }

    /**
     * @return headPanel
     */
    public SimplePanel getHeadPanel() {

        return headPanel;
    }

    /**
     * @return hits panel.
     */
    public VerticalPanel getHits() {

        return hits;
    }

}
