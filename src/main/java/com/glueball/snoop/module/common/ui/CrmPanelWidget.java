package com.glueball.snoop.module.common.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class CrmPanelWidget extends Composite {

	private Widget widget;
	@UiField ScrollPanel     contentPanel;
	@UiField HorizontalPanel controllerPanel;

	private CrmWidget content;

	private static CrmPanelWidgetUiBinder uiBinder = GWT.create(CrmPanelWidgetUiBinder.class);

	interface CrmPanelWidgetUiBinder extends UiBinder<Widget, CrmPanelWidget> {
	}

	public CrmPanelWidget(final CrmWidget content) {
		this.widget = uiBinder.createAndBindUi(this);
		setContent(content);
		contentPanel.setWidth("200px");
		contentPanel.setHeight("200px");
	}

	public CrmPanelWidget(final CrmWidget content, final String width, final String height) {
		this.widget = uiBinder.createAndBindUi(this);		
		setContent(content);
		contentPanel.setWidth(width);
		contentPanel.setHeight(height);
	}

	@Override
	public Widget asWidget() {
		return this.widget;
	}

	public void setContent(final CrmWidget content) {
		this.content = content;
		this.contentPanel.add(this.content.asWidget());
	}
	
	public void addController(final Widget controller) {
		this.controllerPanel.add(controller);
	}
	
	public void setWidth(final String width) {
		contentPanel.setWidth(width);
	}

	public void setHeight(final String height) {
		contentPanel.setWidth(height);
	}
}

