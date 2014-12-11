package com.glueball.snoop.module.common.ui;

import java.util.ArrayList;
import java.util.List;


public abstract class EditWidget<T> extends CrmWidget implements Controllable<T> {
	
	protected T entity;
	
	protected final List<ControllerWidget<EditableWidget<T>>> controllers =
			new ArrayList<ControllerWidget<EditableWidget<T>>>();

	public abstract T getEntity();
	public abstract void setEntity(final T entity);

	public List<ControllerWidget<EditableWidget<T>>> getControllers() {
		return controllers;
	}

	public void addController(final ControllerWidget<EditableWidget<T>> controllerWidget) {
		controllers.add(controllerWidget);
	}

}
