package com.glueball.snoop.module.common.ui;

import com.glueball.crm.module.client.model.EmailAddress;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ShowEmailAddressWidget extends ShowWidget<EmailAddress> {

	@UiField Label mailboxId;
	@UiField Label domain;
	@UiField Label active;
	@UiField Label notify;

	private static ShowEmailAddressWidgetUiBinder uiBinder = GWT.create(ShowEmailAddressWidgetUiBinder.class);

	interface ShowEmailAddressWidgetUiBinder extends UiBinder<Widget, ShowEmailAddressWidget> {
	}

	private EmailAddress entity;

	public ShowEmailAddressWidget(final EmailAddress entity) {
		this.widget = uiBinder.createAndBindUi(this);
		setEntity(entity);
	}

	@Override
	public EmailAddress getEntity() {
		return this.entity;
	}

	@Override
	public void setEntity(final EmailAddress entity) {
		this.entity = entity;
		mailboxId.setText(this.entity.getMailboxId());
		domain.setText(this.entity.getDomain());
		active.setText(this.entity.isActive()?"A":"I");
		notify.setText(this.entity.isNotify()?"N":"");		
	}
}
