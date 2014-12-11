package com.glueball.snoop.module.common.ui;

import org.fusesource.restygwt.client.JsonEncoderDecoder;

import com.glueball.crm.module.client.model.PersonName;
import com.glueball.snoop.module.util.CrmRequest;
import com.glueball.snoop.module.util.CrmRequestCallback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class PersonNameWidget extends EditableWidget<PersonName> {

	private static PersonNameWidgetUiBinder uiBinder = GWT.create(PersonNameWidgetUiBinder.class);

	interface PersonNameWidgetUiBinder extends UiBinder<Widget, PersonNameWidget> {
	}

	interface PersonNameCodec extends JsonEncoderDecoder<PersonName> {
	}

	private final PersonNameCodec codec = GWT.create(PersonNameCodec.class);

	private final CrmRequest<PersonName> req = new CrmRequest<PersonName>(codec);
	
	protected final String saveUrl;

	public PersonNameWidget(
				final PersonName personName,
				final SimplePanel showPanel,
				final HorizontalPanel controllerPanel,
				final StartView startView,
				final String saveUrl) {
		super(showPanel,
			  controllerPanel,
			  new ShowPersonNameWidget(personName),
			  new EditPersonNameWidget(personName)
		     );
		setEntity(personName);
		this.widget = uiBinder.createAndBindUi(this);
		this.saveUrl = saveUrl;
		addDefaultEditController();
		addDefaultSaveController();
		if (!this.add) {
			addDefaultCancelController();
		}
		start(startView);
	}

	@Override
	public void save() {
		req.save(this.saveUrl, this.editWidget.getEntity(), new CrmRequestCallback<PersonName>() {
			public void onSuccess(final PersonName _entity) {
				setEntity(_entity);
			}
		});
		this.add = false;
		changeView(this.showWidget);
	}
}
