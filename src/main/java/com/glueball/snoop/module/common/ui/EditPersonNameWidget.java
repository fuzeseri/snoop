package com.glueball.snoop.module.common.ui;

import com.glueball.crm.module.client.model.PersonName;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditPersonNameWidget extends EditWidget<PersonName> {

	@UiField TextBox firstName;
	@UiField TextBox lastName;
	@UiField TextBox title;	

	private static EditPersonNameWidgetUiBinder uiBinder = GWT.create(EditPersonNameWidgetUiBinder.class);

	interface EditPersonNameWidgetUiBinder extends UiBinder<Widget, EditPersonNameWidget> {
	}

	public EditPersonNameWidget(final PersonName personName) {
		this.widget = uiBinder.createAndBindUi(this);
		setEntity(personName);
	}

	@Override
	public PersonName getEntity() {
		this.entity.setFirstName(firstName.getText());
		this.entity.setLastName(lastName.getText());
		this.entity.setTitle(title.getText());
		return this.entity;
	}

	@Override
	public void setEntity(final PersonName entity) {
		this.entity = entity;
		firstName.setText(this.entity.getFirstName()==null?"":this.entity.getFirstName());
		lastName.setText(this.entity.getLastName()==null?"":this.entity.getLastName());
		title.setText(this.entity.getTitle()==null?"":this.entity.getTitle());		
	}
}
