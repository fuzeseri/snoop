package com.glueball.snoop.module.common.ui;

import com.glueball.crm.module.client.model.PhoneNumber;
import com.glueball.snoop.module.common.ui.EditableWidget.StartView;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class PhoneNumberWidgetFactory implements EditableWidgetFactory<PhoneNumber> {

	private final String saveUrl;

	public PhoneNumberWidgetFactory(String saveUrl) {
		this.saveUrl = saveUrl;
	}

	public EditableWidget<PhoneNumber> build(
			final PhoneNumber entity,
			final SimplePanel showPanel,
			final HorizontalPanel controllerPanel,
			final StartView startView) {
		return new PhoneNumberWidget(entity, showPanel, controllerPanel, startView, saveUrl);
	}

	public EditableWidget<PhoneNumber> build(
			final SimplePanel showPanel,
			final HorizontalPanel controllerPanel,
			final StartView startView) {
		return new PhoneNumberWidget(new PhoneNumber(), showPanel, controllerPanel, startView, saveUrl);
	}

}
