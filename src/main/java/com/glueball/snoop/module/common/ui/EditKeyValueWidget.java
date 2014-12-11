package com.glueball.snoop.module.common.ui;

import com.glueball.snoop.module.util.KeyValue;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditKeyValueWidget extends EditWidget<KeyValue> {
	
	@UiField TextBox valueField;

	private static EditKeyValueWidgetUiBinder uiBinder = GWT.create(EditKeyValueWidgetUiBinder.class);

	interface EditKeyValueWidgetUiBinder extends UiBinder<Widget, EditKeyValueWidget> {
	}

	public EditKeyValueWidget(final KeyValue entity) {
		this.widget = uiBinder.createAndBindUi(this);
		setEntity(entity);
	}

	@Override
	public KeyValue getEntity() {
		this.entity.setValue(valueField.getText());
		return this.entity;
	}

	@Override
	public void setEntity(final KeyValue _entity) {
		this.entity = _entity;
		valueField.setText(this.entity.getValue()==null?"":this.entity.getValue());
	}
}
