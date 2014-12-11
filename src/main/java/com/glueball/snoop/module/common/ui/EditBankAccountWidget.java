package com.glueball.snoop.module.common.ui;

import com.glueball.crm.module.client.model.BankAccount;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditBankAccountWidget extends EditWidget<BankAccount> {

	@UiField TextBox accountId;
	@UiField TextBox iban;
	@UiField TextBox countryCode;
	@UiField TextBox swiftCode;
	@UiField TextBox bankName;
	@UiField CheckBox active;
	@UiField CheckBox securityDeposit;
	@UiField CheckBox outgoingTransfer;

	private static EditBankAccountWidgetUiBinder uiBinder = GWT.create(EditBankAccountWidgetUiBinder.class);

	interface EditBankAccountWidgetUiBinder extends	UiBinder<Widget, EditBankAccountWidget> {
	}
		
	public EditBankAccountWidget(final BankAccount entity) {
		this.widget = uiBinder.createAndBindUi(this);
		setEntity(entity);
	}

	@Override
	public BankAccount getEntity() {
		this.entity.setAccountId(accountId.getText());
		this.entity.setIban(iban.getText());
		this.entity.setCountryCode(countryCode.getText());
		this.entity.setSwiftCode(swiftCode.getText());
		this.entity.setBankName(bankName.getText());
		this.entity.setActive(active.getValue());
		this.entity.setSecurityDeposit(securityDeposit.getValue());
		this.entity.setOutgoingTransfer(outgoingTransfer.getValue());
		return this.entity;

	}

	@Override
	public void setEntity(final BankAccount entity) {
		this.entity = entity;
		accountId.setText(this.entity.getAccountId()==null?"":this.entity.getAccountId());
		iban.setText(this.entity.getIban()==null?"":this.entity.getIban());
		countryCode.setText(this.entity.getCountryCode()==null?"":this.entity.getCountryCode());
		swiftCode.setText(this.entity.getSwiftCode()==null?"":this.entity.getSwiftCode());
		bankName.setText(this.entity.getBankName()==null?"":this.entity.getBankName());
		active.setValue(this.entity.isActive());
		securityDeposit.setValue(this.entity.isSecurityDeposit());
		outgoingTransfer.setValue(this.entity.isOutgoingTransfer());
	}	
}
