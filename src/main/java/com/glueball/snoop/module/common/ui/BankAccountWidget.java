package com.glueball.snoop.module.common.ui;

import org.fusesource.restygwt.client.JsonEncoderDecoder;

import com.glueball.crm.module.client.model.BankAccount;
import com.glueball.snoop.module.util.CrmRequest;
import com.glueball.snoop.module.util.CrmRequestCallback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class BankAccountWidget extends EditableWidget<BankAccount>{
	
	private static BankAccountWidgetUiBinder uiBinder = GWT.create(BankAccountWidgetUiBinder.class);

	interface BankAccountWidgetUiBinder extends UiBinder<Widget, BankAccountWidget> {
	}
	
	interface BankAccountCodec extends JsonEncoderDecoder<BankAccount> {
	}

	private final BankAccountCodec codec = GWT.create(BankAccountCodec.class);

	private final CrmRequest<BankAccount> req = new CrmRequest<BankAccount>(codec);
	
	protected final String saveUrl;

	public BankAccountWidget(
			final BankAccount entity,
			final SimplePanel showPanel,
			final HorizontalPanel controllerPanel,
			final StartView startView,
			final String saveUrl
			) {
		super(showPanel,
			  controllerPanel,
			  new ShowBankAccountWidget(entity),
		      new EditBankAccountWidget(entity)
			);
		setEntity(entity);
		this.widget = uiBinder.createAndBindUi(this);
		this.saveUrl = saveUrl;
		addDefaultEditController();
		addDefaultSaveController();
		if (!this.add) {
			addDefaultCancelController();
		}
		start(startView);
	}

	public void save() {
		req.save(this.saveUrl, this.editWidget.getEntity(), new CrmRequestCallback<BankAccount>() {
				public void onSuccess(final BankAccount _entity) {
					setEntity(_entity);
				}
			});
		this.add = false;
		changeView(this.showWidget);
	}
}
