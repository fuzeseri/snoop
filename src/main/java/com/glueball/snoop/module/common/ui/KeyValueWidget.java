package com.glueball.snoop.module.common.ui;

import org.fusesource.restygwt.client.JsonEncoderDecoder;

import com.glueball.snoop.module.util.CrmRequest;
import com.glueball.snoop.module.util.CrmRequestCallback;
import com.glueball.snoop.module.util.KeyValue;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class KeyValueWidget extends EditableWidget<KeyValue> {

	private static KeyValueWidgetUiBinder uiBinder = GWT.create(KeyValueWidgetUiBinder.class);

	interface KeyValueWidgetUiBinder extends UiBinder<Widget, KeyValueWidget> {
	}

	interface KeyValueCodec extends JsonEncoderDecoder<KeyValue> {
	}

	private final KeyValueCodec codec = GWT.create(KeyValueCodec.class);

	private final CrmRequest<KeyValue> req = new CrmRequest<KeyValue>(codec);
	
	protected String saveUrl;

	public KeyValueWidget(
			final KeyValue keyValue,
			final SimplePanel showPanel,
			final HorizontalPanel controllerPanel,
			final StartView startView,
			final String saveUrl) {
		super(showPanel,
			  controllerPanel,
			  new ShowKeyValueWidget(keyValue),
			  new EditKeyValueWidget(keyValue)
			 );
		setEntity(keyValue);
		this.saveUrl = saveUrl;
		this.widget = uiBinder.createAndBindUi(this);
		addDefaultEditController();
		addDefaultSaveController();
		addDefaultCancelController();
		start(startView);
	}

	@Override
	public void save() {
		req.save(this.saveUrl, this.editWidget.getEntity(), new CrmRequestCallback<KeyValue>() {
			public void onSuccess(final KeyValue _entity) {
				setEntity(_entity);
			}
		});
		this.add = false;
		changeView(this.showWidget);
	}
}
