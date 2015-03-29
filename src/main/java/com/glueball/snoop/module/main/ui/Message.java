package com.glueball.snoop.module.main.ui;

import com.glueball.snoop.module.main.model.ServerMessage;
import com.glueball.snoop.module.util.URLHelper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Message extends Composite {

    @UiField
    Image icon;
    @UiField
    Label message;

    private static MessageUiBinder uiBinder = GWT.create(MessageUiBinder.class);

    interface MessageUiBinder extends UiBinder<Widget, Message> {
    }

    private final Widget widget;

    private final ServerMessage serverMessage;

    public Message(final ServerMessage msg) {

	this.widget = uiBinder.createAndBindUi(this);
	this.serverMessage = msg;
	init();
    }

    private void init() {

	icon.setAltText(ServerMessage.getTypeText(serverMessage
		.getMessageType()));
	icon.setUrl(URLHelper.getImageUrl(ServerMessage.getIcon(serverMessage
		.getMessageType())));
	message.setText(serverMessage.getMessage());
    }

    @Override
    public Widget asWidget() {
	return this.widget;
    }
}
