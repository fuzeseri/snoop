package com.glueball.snoop.module.common.ui;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;

public interface Controllable<T> {
	public List<ControllerWidget<EditableWidget<T>>> getControllers();
	public void addController(final ControllerWidget<EditableWidget<T>> controller);
	public Widget asWidget();
}
