package com.glueball.snoop.module.common.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;

public abstract class ListWidget<E> extends CrmWidget {

	protected List<E> entityList = new ArrayList<E>();

	@Override
	public Widget asWidget() {
		return this.widget;
	}

	public List<E> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<E> entityList) {
		this.entityList = entityList;
	}
}
