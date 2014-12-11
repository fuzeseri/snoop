package com.glueball.snoop.module.common.ui;

import com.glueball.crm.module.client.model.PhoneNumber;
import com.glueball.snoop.module.util.TextBoxUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditPhoneNumberWidget extends EditWidget<PhoneNumber> { 
	
	@UiField TextBox countryCode;
	@UiField TextBox areaCode;
	@UiField TextBox number;
	@UiField ListBox fromHour;
	@UiField ListBox fromMin;
	@UiField ListBox toHour;
	@UiField ListBox toMin;
	@UiField ListBox language;
	@UiField CheckBox active;
	@UiField CheckBox sms;
	
	interface EditPhoneNumberWidgetUiBinder extends UiBinder<Widget, EditPhoneNumberWidget> {
	}

	private static EditPhoneNumberWidgetUiBinder uiBinder = GWT.create(EditPhoneNumberWidgetUiBinder.class);

	public EditPhoneNumberWidget(final PhoneNumber phoneNumber) {
		this.widget = uiBinder.createAndBindUi(this);
		setEntity(phoneNumber);
	}

	@Override
	public PhoneNumber getEntity() {
		this.entity.setActive(active.getValue());
		this.entity.setCountryCode(Integer.valueOf(countryCode.getText()));
		this.entity.setAreaCode(Integer.valueOf(areaCode.getText()));
		this.entity.setNumber(Integer.valueOf(number.getText()));
		this.entity.setFromHour(Integer.valueOf(fromHour.getValue(fromHour.getSelectedIndex())));
		this.entity.setFromMin(Integer.valueOf(fromMin.getValue(fromMin.getSelectedIndex())));
		this.entity.setToHour(Integer.valueOf(toHour.getValue(toHour.getSelectedIndex())));
		this.entity.setToMin(Integer.valueOf(toMin.getValue(toMin.getSelectedIndex())));
		this.entity.setLanguage(language.getValue(language.getSelectedIndex()));
		this.entity.setSms(sms.getValue());
		return this.entity;
	}

	@Override
	public void setEntity(final PhoneNumber entity) {
		this.entity = entity;
		countryCode.setText(this.entity.getCountryCode() == null ? "" :
			String.valueOf(this.entity.getCountryCode()));
		areaCode.setText(this.entity.getAreaCode() == null ? "" :
				String.valueOf(this.entity.getAreaCode()));
		number.setText(this.entity.getNumber() == null ? "" :
				String.valueOf(this.entity.getNumber()));
		fromHour.setSelectedIndex(TextBoxUtil.getIndexByInt(
				fromHour, this.entity.getFromHour()));
		fromMin.setSelectedIndex(TextBoxUtil.getIndexByInt(
				fromMin, this.entity.getFromMin()));
		toHour.setSelectedIndex(TextBoxUtil.getIndexByInt(
				toHour, this.entity.getToHour()));
		toMin.setSelectedIndex(TextBoxUtil.getIndexByInt(
				toMin, this.entity.getToMin()));
		language.setSelectedIndex(TextBoxUtil.getIndexByText(
				language, this.entity.getLanguage()));
		active.setValue(this.entity.isActive());
		sms.setValue(this.entity.isSms());
	}
}
