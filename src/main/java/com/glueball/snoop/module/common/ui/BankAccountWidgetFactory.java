package com.glueball.snoop.module.common.ui;

import com.glueball.crm.module.client.model.BankAccount;
import com.glueball.snoop.module.common.ui.EditableWidget.StartView;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class BankAccountWidgetFactory implements EditableWidgetFactory<BankAccount> {

	private final String saveUrl;

	public BankAccountWidgetFactory(final String saveUrl) {
		this.saveUrl = saveUrl;
	}

	public EditableWidget<BankAccount> build(
			final BankAccount entity,
			final SimplePanel showPanel,
			final HorizontalPanel controllerPanel,
			final StartView startView
			) {
		return new BankAccountWidget(entity, showPanel, controllerPanel, startView, saveUrl);		
	}

	public EditableWidget<BankAccount> build(
			final SimplePanel showPanel,
			final HorizontalPanel controllerPanel,
			final StartView startView) {
		return new BankAccountWidget(new BankAccount(), showPanel, controllerPanel, startView, saveUrl);
	}
}
