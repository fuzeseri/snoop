package com.glueball.snoop.module.main.ui;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import com.glueball.snoop.module.main.model.ServerMessage;
import com.glueball.snoop.module.util.URLHelper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * View of a server message on the GWT web ui.
 * 
 * @author karesz
 */
public final class Message extends Composite {

    /**
     * Icon image.
     */
    @UiField
    Image icon;

    /**
     * Text message.
     */
    @UiField
    Label message;

    /**
     * GWT uibinder instance.
     */
    private static MessageUiBinder UIBINDER = GWT.create(MessageUiBinder.class);

    /**
     * GWT uibinder interface declaration.
     * 
     * @author karesz
     */
    interface MessageUiBinder extends UiBinder<Widget, Message> {
    }

    /**
     * Widget object.
     */
    private final Widget widget;

    /**
     * The server message field.
     */
    private final ServerMessage serverMessage;

    /**
     * Constructor.
     *
     * @param msg
     *            The server message to set.
     */
    public Message(final ServerMessage msg) {

        this.widget = UIBINDER.createAndBindUi(this);
        this.serverMessage = msg;
        init();
    }

    /**
     * Initialization of the object.
     */
    private void init() {

        icon.setAltText(ServerMessage.getTypeText(serverMessage
                .getMessageType()));
        icon.setUrl(URLHelper.getImageUrl(ServerMessage.getIcon(serverMessage
                .getMessageType())));
        message.setText(serverMessage.getMessage());
    }

    /*
     * (non-Javadoc)
     * @see com.google.gwt.user.client.ui.Widget#asWidget()
     */
    @Override
    public Widget asWidget() {

        return this.widget;
    }
}
