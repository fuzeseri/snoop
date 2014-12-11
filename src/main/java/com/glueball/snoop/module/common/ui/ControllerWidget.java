package com.glueball.snoop.module.common.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class ControllerWidget<T> extends Composite {
	
	@UiField  Image  image;
	private   Widget widget;
	protected ControllerCallback<T> callback;
	protected T object = null;

	private static ControllerWidgetUiBinder uiBinder = GWT
			.create(ControllerWidgetUiBinder.class);

	@SuppressWarnings("rawtypes")
	interface ControllerWidgetUiBinder extends
			UiBinder<Widget, ControllerWidget> {
	}

	public ControllerWidget(final String url,
							final String altText,
							final ControllerCallback<T> callback) {
		this.widget = uiBinder.createAndBindUi(this);
		this.image.setUrl(url);
		this.image.setAltText(altText);
		this.callback = callback;
	}

	public ControllerWidget(final String url,
							final String altText,
							final T object,
							final ControllerCallback<T> callback) {
		this.widget = uiBinder.createAndBindUi(this);
		this.image.setUrl(url);
		this.image.setAltText(altText);
		this.callback = callback;
		this.object = object;
	}
	
	public Widget asWidget() {
		return this.widget;
	}

	@UiHandler("image")
	public void imageHandler(final ClickEvent evt) {
		if (object==null) {
			callback.fire();
		} else {
			callback.fire(object);
		}
	}
}
