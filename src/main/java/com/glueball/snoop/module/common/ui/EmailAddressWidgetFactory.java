package com.glueball.snoop.module.common.ui;

import com.glueball.crm.module.client.model.EmailAddress;
import com.glueball.snoop.module.common.ui.EditableWidget.StartView;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class EmailAddressWidgetFactory implements EditableWidgetFactory<EmailAddress> {
	
	private final String saveUrl;

	public EmailAddressWidgetFactory(String saveUrl) {
		this.saveUrl = saveUrl;
	}

	public EditableWidget<EmailAddress> build(
			final EmailAddress entity,
			final SimplePanel showPanel,
			final HorizontalPanel controllerPanel,
			final StartView startView) {
		return new EmailAddressWidget(entity, showPanel, controllerPanel, startView, saveUrl);
	}

	public EditableWidget<EmailAddress> build(
			final SimplePanel showPanel,
			final HorizontalPanel controllerPanel,
			final StartView startView) {
		return new EmailAddressWidget(new EmailAddress(), showPanel, controllerPanel, startView, saveUrl);	
	}
}
