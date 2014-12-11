package com.glueball.snoop.module.common.ui;

import com.glueball.crm.module.client.model.BankAccount;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ShowBankAccountWidget extends ShowWidget<BankAccount> {

	@UiField Label accountId;
	@UiField Label iban;
	@UiField Label countryCode;
	@UiField Label swiftCode;
	@UiField Label bankName;
	@UiField Label active;
	@UiField Label securityDeposit;
	@UiField Label outgoingTransfer;

	private static ShowBankAccountWidgetUiBinder uiBinder = GWT.create(ShowBankAccountWidgetUiBinder.class);

	interface ShowBankAccountWidgetUiBinder extends UiBinder<Widget, ShowBankAccountWidget> {
	}

	public ShowBankAccountWidget(final BankAccount bankAccount) {
		this.widget = uiBinder.createAndBindUi(this);
		setEntity(bankAccount);
	}

	@Override
	public void setEntity(final BankAccount _entity) {
		this.entity = _entity;
		accountId.setText(this.entity.getAccountId() == null ? "" : this.entity.getAccountId());
		iban.setText(this.entity.getIban() == null ? "" : this.entity.getIban());
		countryCode.setText(this.entity.getCountryCode() == null ? "" : this.entity.getCountryCode());
		swiftCode.setText(this.entity.getSwiftCode() == null ? "" : this.entity.getSwiftCode());
		bankName.setText(this.entity.getBankName() == null ? "" : this.entity.getBankName());
		active.setText(this.entity.isActive() ? "Active" : "Inactive");
		securityDeposit.setText(this.entity.isSecurityDeposit() ? "SecDep" : "");
		outgoingTransfer.setText(this.entity.isOutgoingTransfer() ? "outgoing": "");
	}	
}
