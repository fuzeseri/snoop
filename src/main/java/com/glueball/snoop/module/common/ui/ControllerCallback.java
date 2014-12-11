package com.glueball.snoop.module.common.ui;

public interface ControllerCallback<T> {
	public void fire();
	public void fire(T object);
}
