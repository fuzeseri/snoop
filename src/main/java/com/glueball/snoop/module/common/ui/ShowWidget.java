package com.glueball.snoop.module.common.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;

public abstract class ShowWidget<T> implements Controllable<T> {

	protected final List<ControllerWidget<EditableWidget<T>>> controllers =
			new ArrayList<ControllerWidget<EditableWidget<T>>>();

	protected Widget widget;

	protected T entity;

	public T getEntity() {
		return this.entity;
	}

	public abstract void setEntity(final T entity);

	public List<ControllerWidget<EditableWidget<T>>> getControllers() {
		return controllers;
	}

	public void addController(final ControllerWidget<EditableWidget<T>> controllerWidget) {
		controllers.add(controllerWidget);
	}

	public Widget asWidget() {
		return this.widget;
	}
}
