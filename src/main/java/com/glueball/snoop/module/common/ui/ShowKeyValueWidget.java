package com.glueball.snoop.module.common.ui;

import com.glueball.snoop.module.util.KeyValue;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ShowKeyValueWidget extends ShowWidget<KeyValue> {
	
	@UiField Label valueField;

	private static ShowKeyValueWidgetUiBinder uiBinder = GWT.create(ShowKeyValueWidgetUiBinder.class);

	interface ShowKeyValueWidgetUiBinder extends UiBinder<Widget, ShowKeyValueWidget> {
	}

	public ShowKeyValueWidget(final KeyValue keyValue) {
		this.widget = uiBinder.createAndBindUi(this);
		setEntity(keyValue);
	}

	@Override
	public void setEntity(final KeyValue _entity) {
		this.entity = _entity;
		valueField.setText(this.entity.getValue());
	}	
}
