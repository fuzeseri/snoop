package com.glueball.snoop.module.main.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CrmHead extends Composite {

	@UiField MenuBar headMenu;
	@UiField TextBox searchBox;
	@UiField Button  searchButton;
	@UiField Anchor  advancedSearch;
	@UiField Label	 userName;
	
	private static CrmHeadUiBinder uiBinder = GWT.create(CrmHeadUiBinder.class);
	private Widget widget;

	interface CrmHeadUiBinder extends UiBinder<Widget, CrmHead> {
	}

	public CrmHead() {
		this.widget = uiBinder.createAndBindUi(this);
	}

	public MenuBar getHeadMenu() {
		return headMenu;
	}

	public TextBox getSearchBox() {
		return searchBox;
	}

	public Button getSearchButton() {
		return searchButton;
	}

	public Anchor getAdvancedSearch() {
		return advancedSearch;
	}

	public Label getUserName() {
		return userName;
	}
	
	@Override
	public Widget asWidget() {
		return this.widget;
	}
	
}
