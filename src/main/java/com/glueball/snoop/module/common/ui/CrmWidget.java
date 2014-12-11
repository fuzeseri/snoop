package com.glueball.snoop.module.common.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public abstract class CrmWidget extends Composite {
	protected Widget widget;
	
	public Widget asWidget() {
		return this.widget;
	}
}
