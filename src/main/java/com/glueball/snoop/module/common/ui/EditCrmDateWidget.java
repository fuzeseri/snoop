package com.glueball.snoop.module.common.ui;

import java.util.Date;

import com.glueball.snoop.entity.CrmDate;
import com.glueball.snoop.module.util.DateUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

public class EditCrmDateWidget extends EditWidget<CrmDate> {

	@UiField ListBox year;
	@UiField ListBox month;
	@UiField ListBox day;
	@UiField DatePicker datePicker;

	private static EditCrmDateWidgetUiBinder uiBinder = GWT.create(EditCrmDateWidgetUiBinder.class);

	interface EditCrmDateWidgetUiBinder extends UiBinder<Widget, EditCrmDateWidget> {
	}

	public EditCrmDateWidget(final CrmDate crmDate) {
		this.widget = uiBinder.createAndBindUi(this);
		setEntity(crmDate);
		initYearBox();
		initMonthBox();
		initDayBox();
		initDatePicker();
	}

	private void initDatePicker() {
		datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			public void onValueChange(final ValueChangeEvent<Date> event) {
				final Date date = event.getValue();
				year.setSelectedIndex(DateUtil.getYear(date)+100-DateUtil.currentYear());
				month.setSelectedIndex(DateUtil.getMonth(date));
				day.setSelectedIndex(DateUtil.getDay(date));
			}
	      });
		updateDatePicker(DateUtil.getDate(this.entity));
	}

	private void initYearBox() {
		int currentYear = DateUtil.currentYear();
		for (int i = currentYear-100; i <= currentYear; i++) {
			year.addItem(String.valueOf(i));
		}
		year.setSelectedIndex(this.entity.getYear()+100-currentYear);
	}

	private void initMonthBox() {
		for (int i = 1; i <= 12; i++) {
			month.addItem(String.valueOf(i<10?"0"+i:i));
		}
		month.setSelectedIndex(this.entity.getMonth()-1);
		month.addChangeHandler(new ChangeHandler() {
			public void onChange(final ChangeEvent event) {
				refreshDays(entity.getDay());
			}
		});
	}

	private void initDayBox() {
		refreshDays(this.entity.getDay());
	}

	private void refreshDays(final int pday) {
		int dayCount = DateUtil.daysOfMonth(
				Integer.valueOf(year.getValue(year.getSelectedIndex())),
				month.getSelectedIndex());
		day.clear();
		for (int i = 1; i <= dayCount; i++) {
			day.addItem(String.valueOf(i<10?"0"+i:i));
		}
		day.setSelectedIndex(day.getItemCount() < pday ? day.getItemCount() : pday-1);
	}

	private void updateDatePicker(final Date date) {
		this.datePicker.setValue(date, false);
	}

	@Override
	public CrmDate getEntity() {
		this.entity.setDay(day.getSelectedIndex());
		this.entity.setMonth(month.getSelectedIndex());
		this.entity.setYear(year.getSelectedIndex());
		return this.entity; 
	}

	@Override
	public void setEntity(final CrmDate entity) {
		this.entity = entity;
		year.setSelectedIndex(this.entity.getYear()+100-DateUtil.currentYear());
		month.setSelectedIndex(this.entity.getMonth());
		day.setSelectedIndex(this.entity.getDay());
	}
}
