package com.glueball.snoop.module.common.ui;

import org.fusesource.restygwt.client.JsonEncoderDecoder;

import com.glueball.crm.module.client.model.PhoneNumber;
import com.glueball.snoop.module.util.CrmRequest;
import com.glueball.snoop.module.util.CrmRequestCallback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class PhoneNumberWidget extends EditableWidget<PhoneNumber> {

	private static PhoneNumberWidgetUiBinder uiBinder = GWT.create(PhoneNumberWidgetUiBinder.class);

	interface PhoneNumberWidgetUiBinder extends UiBinder<Widget, PhoneNumberWidget> {
	}
	
	interface PhoneNumberCodec extends JsonEncoderDecoder<PhoneNumber> {
	}

	private final PhoneNumberCodec codec = GWT.create(PhoneNumberCodec.class);

	private final CrmRequest<PhoneNumber> req = new CrmRequest<PhoneNumber>(codec);
	
	protected final String saveUrl;

	public PhoneNumberWidget(
				final PhoneNumber phoneNumber,
				final SimplePanel showPanel,
				final HorizontalPanel controllerPanel,
				final StartView startView,
				final String saveUrl) {
		super(showPanel,
			  controllerPanel,
			  new ShowPhoneNumberWidget(phoneNumber),
			  new EditPhoneNumberWidget(phoneNumber)
			);
		setEntity(phoneNumber);
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
		req.save(this.saveUrl, this.editWidget.getEntity(), new CrmRequestCallback<PhoneNumber>() {
			public void onSuccess(final PhoneNumber _entity) {
				setEntity(_entity);
			}
		});
		this.add = false;
		changeView(this.showWidget);
	}
}
