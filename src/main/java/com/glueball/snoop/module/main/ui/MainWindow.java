package com.glueball.snoop.module.main.ui;

import com.glueball.snoop.module.ui.CrmContainer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class MainWindow extends CrmContainer {
	
	@UiField SimplePanel headPanel;
	@UiField Frame		 body;
	
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

	public Frame getBody() {
		return body;
	}

}
