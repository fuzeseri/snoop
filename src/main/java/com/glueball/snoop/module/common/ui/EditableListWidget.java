package com.glueball.snoop.module.common.ui;

import java.util.ArrayList;
import java.util.List;

import com.glueball.snoop.module.common.ui.EditableWidget.StartView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class EditableListWidget<T extends EditableWidget<E>,E> extends ListWidget<E> {

	@UiField Grid editableContainer = new Grid(1, 2);;

	private final List<T> editableList = new ArrayList<T>();

	private final EditableWidgetFactory<E> factory;

	private static EditableListWidgetUiBinder uiBinder = GWT.create(EditableListWidgetUiBinder.class);

	@SuppressWarnings("rawtypes")
	interface EditableListWidgetUiBinder extends UiBinder<Widget, EditableListWidget> {
	}

	public EditableListWidget(
			final List<E> entityList,
			final EditableWidgetFactory<E> factory) {
		this.widget = uiBinder.createAndBindUi(this);
		this.entityList = (List<E>) entityList;
		this.factory = factory;
		//this.editableList.addAll(getEditables());
		refresh();
	}

	@SuppressWarnings("unchecked")
	public void addNew() {

		final SimplePanel showPanel = new SimplePanel();
		final HorizontalPanel controllerPanel = new HorizontalPanel();

		editableContainer.insertRow(0);
		editableContainer.resizeColumns(2);
		editableContainer.setWidget(0, 0, showPanel);
		editableContainer.setWidget(0, 1, controllerPanel);

		final T newT = (T) factory.build(showPanel, controllerPanel, StartView.ADD);
		newT.addCancelController(
				new ControllerCallback<EditableWidget<E>>() {
					public void fire() {
					}
					public void fire(EditableWidget<E> object) {
						remove(object);
					}
				});

		final List<T> tempList = new ArrayList<T>();
		tempList.add(newT);
		for (final T editable : this.editableList) {
			tempList.add(editable);
		}
		this.editableList.clear();
		this.editableList.addAll(tempList);
		//refresh();
	}

	public void remove(final EditableWidget<E> editable) {
		this.editableList.remove(editable);
		refresh();
	}

//	public void refresh() {
//		this.editableContainer.clear();
//		for (final T editable : this.editableList) {
//			this.editableContainer.add(editable.asWidget());
//		}
//	}

	public void save(final T w) {
		this.editableList.add(w);
	}

	private void refresh() {
		final List<T> editables = new ArrayList<T>();		
		for (final E entity : this.entityList) {

			final SimplePanel showPanel = new SimplePanel();
			final HorizontalPanel controllerPanel = new HorizontalPanel();

			editableContainer.insertRow(0);
			editableContainer.resizeColumns(2);
			editableContainer.setWidget(0, 0, showPanel);
			editableContainer.setWidget(0, 1, controllerPanel);

			@SuppressWarnings("unchecked")
			final T newT = (T) factory.build(entity, showPanel, controllerPanel, StartView.SHOW);
			editables.add(newT);
		}
		this.editableList.clear();
		this.editableList.addAll(editables);
	}

	public List<E> getEntities() {
		this.entityList.clear();
		for (final T w : this.editableList) {
			this.entityList.add(w.getEntity());
		}
		return this.entityList;
	}

	public ControllerCallback<T> getAddCallback() {
		return new ControllerCallback<T>() {
			public void fire() {
				addNew();
			}
			public void fire(final T object) {
			}
		};
	}

	public ControllerCallback<T> getRefreshCallback() {
		return new ControllerCallback<T>() {
			public void fire() {
				refresh();
			}
			public void fire(final T object) {
			}
		};
	}
}
