package com.glueball.snoop.module.common.ui;

import org.fusesource.restygwt.client.JsonEncoderDecoder;

import com.glueball.crm.module.client.model.EmailAddress;
import com.glueball.snoop.module.util.CrmRequest;
import com.glueball.snoop.module.util.CrmRequestCallback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class EmailAddressWidget extends EditableWidget<EmailAddress> {

	private static EmailAddressWidgetUiBinder uiBinder = GWT.create(EmailAddressWidgetUiBinder.class);

	interface EmailAddressWidgetUiBinder extends UiBinder<Widget, EmailAddressWidget> {
	}

	interface EmailAddressCodec extends JsonEncoderDecoder<EmailAddress> {
	}

	private final EmailAddressCodec codec = GWT.create(EmailAddressCodec.class);

	private final CrmRequest<EmailAddress> req = new CrmRequest<EmailAddress>(codec);
	
	protected final String saveUrl;

	public EmailAddressWidget(
			final EmailAddress entity,
			final SimplePanel showPanel,
			final HorizontalPanel controlPanel,
			final StartView startView,
			final String saveUrl
			) {
		super(showPanel,
			  controlPanel,
			  new ShowEmailAddressWidget(entity),
			  new EditEmailAddressWidget(entity)
			 );
		this.widget = uiBinder.createAndBindUi(this);
		setEntity(entity);
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
		req.save(this.saveUrl, this.editWidget.getEntity(), new CrmRequestCallback<EmailAddress>() {
			public void onSuccess(final EmailAddress _entity) {
				setEntity(_entity);
			}
		});
		this.add = false;
		changeView(this.showWidget);
	}
}
