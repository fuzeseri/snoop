package com.glueball.snoop.module.common.ui;

import org.fusesource.restygwt.client.JsonEncoderDecoder;

import com.glueball.snoop.entity.CrmDate;
import com.glueball.snoop.module.util.CrmRequest;
import com.glueball.snoop.module.util.CrmRequestCallback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class CrmDateWidget extends EditableWidget<CrmDate> {
	
	private static CrmDateWidgetUiBinder uiBinder = GWT.create(CrmDateWidgetUiBinder.class);

	interface CrmDateWidgetUiBinder extends UiBinder<Widget, CrmDateWidget> {
	}

	interface CrmDateCodec extends JsonEncoderDecoder<CrmDate> {
	}

	private final CrmDateCodec codec = GWT.create(CrmDateCodec.class);

	private final CrmRequest<CrmDate> req = new CrmRequest<CrmDate>(codec);
	
	protected final String saveUrl;

	public CrmDateWidget(
				final CrmDate crmDate,
				final SimplePanel showPanel,
				final HorizontalPanel controllerPanel,
				final StartView startView, 
				final String saveUrl) {
		super(showPanel,
			  controllerPanel,
			  new ShowCrmDateWidget(crmDate),
			  new EditCrmDateWidget(crmDate)
			);
		setEntity(crmDate);
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
		req.save(this.saveUrl, this.editWidget.getEntity(), new CrmRequestCallback<CrmDate>() {
			public void onSuccess(final CrmDate _entity) {
				setEntity(_entity);
			}
		});
		this.add = false;
		changeView(this.showWidget);
	}
}
