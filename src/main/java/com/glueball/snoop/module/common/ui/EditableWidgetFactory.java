package com.glueball.snoop.module.common.ui;

import com.glueball.snoop.module.common.ui.EditableWidget.StartView;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public interface EditableWidgetFactory<E> {
	public EditableWidget<E> build(
				final E entity,
				final SimplePanel showPanel,
				final HorizontalPanel controllerPanel,
				final StartView startView
	);

	public EditableWidget<E> build(
			final SimplePanel showPanel,
			final HorizontalPanel controllerPanel,
			final StartView startView
	);
}
