package com.glueball.snoop.module.common.ui;

import com.glueball.snoop.module.util.URLHelper;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class EditableWidget<T> extends CrmWidget {

	protected final ShowWidget<T>   showWidget;
	protected final EditWidget<T>   editWidget;
	protected final SimplePanel     showPanel;
	protected final HorizontalPanel controlPanel;

	protected Widget  widget;
	protected T       entity;
	protected boolean add = false;

	protected EditableWidget(
			final SimplePanel showPanel,
			final HorizontalPanel controlPanel,
			final ShowWidget<T>   showWidget,
			final EditWidget<T>   editWidget
			) {
		this.showPanel    = showPanel;
		this.controlPanel = controlPanel;
		this.showWidget   = showWidget;
		this.editWidget   = editWidget;
	}

	@Override
	public Widget asWidget() {
		return this.widget;
	}

	protected void changeView(final Controllable<T> controllable) {
		this.showPanel.clear();
		this.showPanel.add(controllable.asWidget());
		this.controlPanel.clear();
		for (final ControllerWidget<EditableWidget<T>> controller : controllable.getControllers()) {
			this.controlPanel.add(controller.asWidget());
		}
	}

	public enum StartView {
		SHOW,EDIT,ADD
	}

	public void setEntity(T t) {
		this.entity = t;
		this.showWidget.setEntity(this.entity);
		this.editWidget.setEntity(this.entity);
	}

	public T getEntity() {
		return this.entity;
	}

	public void start(final StartView startView) {
		switch (startView) {
			case SHOW:
				changeView(this.showWidget);
				break;
			case EDIT:
				changeView(this.editWidget);
				break;
			case ADD:
				this.add = true;
				changeView(this.editWidget);
				break;
			default:
				changeView(this.showWidget);
		}
	}

	protected void addDefaultEditController() {
		this.showWidget.addController(
				new ControllerWidget<EditableWidget<T>>(URLHelper.getImageUrl("Settings.png"), "Edit", new ControllerCallback<EditableWidget<T>>() {
					public void fire() {
						edit();
					}
					public void fire(EditableWidget<T> object) {
					}
				})
			);
	}

	protected void addDefaultSaveController() {
		this.editWidget.addController(
				new ControllerWidget<EditableWidget<T>>(URLHelper.getImageUrl("Apply.png"), "Save", new ControllerCallback<EditableWidget<T>>() {
					public void fire() {
						save();
					}
					public void fire(final EditableWidget<T> object) {
					}					
				})
			);
	}

	protected void addDefaultCancelController() {
		this.editWidget.addController(
			new ControllerWidget<EditableWidget<T>>(URLHelper.getImageUrl("Remove.png"), "Cancel", new ControllerCallback<EditableWidget<T>>() {
				public void fire() {
					cancel();
				}
				public void fire(final EditableWidget<T> object) {
				}					
			})
		);	
	}

	public void addCancelController(final ControllerCallback<EditableWidget<T>> callback) {
		this.editWidget.addController(
				new ControllerWidget<EditableWidget<T>>(URLHelper.getImageUrl("Remove.png"), "Cancel", this, callback)
			);
	}

	public abstract void save();
	public void cancel() {
		setEntity(this.entity);
		changeView(this.showWidget);
	}

	public void edit() {
		changeView(this.editWidget);
	}

}
