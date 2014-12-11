package com.glueball.snoop.module.common.ui;

import com.glueball.crm.module.client.model.EmailAddress;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditEmailAddressWidget extends EditWidget<EmailAddress> {

	@UiField TextBox  mailboxId;
	@UiField TextBox  domain;
	@UiField CheckBox active;
	@UiField CheckBox notify;

	private static EditEmailAddressWidgetUiBinder uiBinder = GWT.create(EditEmailAddressWidgetUiBinder.class);

	interface EditEmailAddressWidgetUiBinder extends UiBinder<Widget, EditEmailAddressWidget> {
	}

	public EditEmailAddressWidget(final EmailAddress entity) {
		this.widget = uiBinder.createAndBindUi(this);
		this.entity = entity;
	}

	@Override
	public Widget asWidget() {
		return this.widget;
	}

	@Override
	public EmailAddress getEntity() {
		this.entity.setMailboxId(mailboxId.getText());
		this.entity.setDomain(domain.getText());
		this.entity.setActive(active.getValue());
		this.entity.setNotify(notify.getValue());		
		return this.entity;
	}

	@Override
	public void setEntity(final EmailAddress entity) {
		this.entity = entity;
		mailboxId.setText(this.entity.getMailboxId()==null?"":this.entity.getMailboxId());
		domain.setText(this.entity.getDomain()==null?"":this.entity.getDomain());
		active.setValue(this.entity.isActive());
		notify.setValue(this.entity.isNotify());
	}
}
