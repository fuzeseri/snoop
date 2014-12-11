package com.glueball.snoop.module.common.ui;

import com.glueball.crm.module.client.model.PersonName;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ShowPersonNameWidget extends ShowWidget<PersonName> {

	@UiField Label title;
	@UiField Label firstName;
	@UiField Label lastName;

	private static ShowPersonNameWidgetUiBinder uiBinder = GWT.create(ShowPersonNameWidgetUiBinder.class);

	interface ShowPersonNameWidgetUiBinder extends UiBinder<Widget, ShowPersonNameWidget> {
	}

	public ShowPersonNameWidget(final PersonName personName) {
		this.widget = uiBinder.createAndBindUi(this);
		setEntity(personName);
	}

	@Override
	public PersonName getEntity() {
		return this.entity;
	}

	@Override
	public void setEntity(final PersonName entity) {
		this.entity = entity;
		title.setText(String.valueOf(this.entity.getTitle()));
		firstName.setText(String.valueOf(this.entity.getFirstName()));
		lastName.setText(String.valueOf(this.entity.getLastName()));		
	}	
}
