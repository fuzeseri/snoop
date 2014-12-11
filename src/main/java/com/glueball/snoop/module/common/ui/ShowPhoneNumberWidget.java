package com.glueball.snoop.module.common.ui;

import com.glueball.crm.module.client.model.PhoneNumber;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ShowPhoneNumberWidget extends ShowWidget<PhoneNumber> {

	@UiField Label countryCode;
	@UiField Label areaCode;
	@UiField Label number;
	@UiField Label fromHour;
	@UiField Label fromMin;
	@UiField Label toHour;
	@UiField Label toMin;
	@UiField Label language;
	@UiField Label sms;

	private static ShowPhoneNumberWidgetUiBinder uiBinder = GWT.create(ShowPhoneNumberWidgetUiBinder.class);

	interface ShowPhoneNumberWidgetUiBinder extends UiBinder<Widget, ShowPhoneNumberWidget> {
	}

	public ShowPhoneNumberWidget(final PhoneNumber phoneNumber) {
		this.widget = uiBinder.createAndBindUi(this);
		this.entity = phoneNumber;
	}

	@Override
	public void setEntity(final PhoneNumber entity) {
		this.entity = entity;
		countryCode.setText(String.valueOf(this.entity.getCountryCode()));
		areaCode.setText(String.valueOf(this.entity.getAreaCode()));
		number.setText(String.valueOf(this.entity.getNumber()));
		fromHour.setText(String.valueOf(this.entity.getFromHour()));
		fromMin.setText(String.valueOf(this.entity.getFromMin()));
		toHour.setText(String.valueOf(this.entity.getToHour()));
		toMin.setText(String.valueOf(this.entity.getToMin()));
		language.setText(this.entity.getLanguage());
		sms.setText(this.entity.isSms()?"Sms":"");
	}
}
