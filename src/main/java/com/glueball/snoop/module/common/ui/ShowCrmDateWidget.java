package com.glueball.snoop.module.common.ui;

import com.glueball.crm.module.client.model.CrmDate;
import com.glueball.snoop.module.util.DateUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ShowCrmDateWidget extends ShowWidget<CrmDate> {
	
	@UiField Label date;

	private static ShowCrmDateWidgetUiBinder uiBinder = GWT.create(ShowCrmDateWidgetUiBinder.class);

	interface ShowCrmDateWidgetUiBinder extends UiBinder<Widget, ShowCrmDateWidget> {
	}
	
	public ShowCrmDateWidget(final CrmDate crmDate) {
		this.widget = uiBinder.createAndBindUi(this);
		setEntity(crmDate);
	}

	@Override
	public CrmDate getEntity() {
		return this.entity;	
	}

	@Override
	public void setEntity(final CrmDate entity) {
		this.entity = entity;
		date.setText(DateUtil.crmDateToString(this.entity));
	}

	
}
