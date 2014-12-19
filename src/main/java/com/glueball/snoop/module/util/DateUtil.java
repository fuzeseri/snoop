package com.glueball.snoop.module.util;

import java.text.ParseException;
import java.util.Date;

import com.glueball.snoop.entity.CrmDate;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.datepicker.client.CalendarUtil;

public class DateUtil {

	public static Date getDate(final int year, final int month, final int day) {
        return DateTimeFormat.getFormat("yyyy-MM-dd").parse(year+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day));
	}

	public static int currentYear() {
		return getYear(new Date());
	}

	public static int currentMonth() {
		return getMonth(new Date());
	}

	public static int currentDay() {
		return getDay(new Date());
	}

	public static int getYear(final Date date) {
		return Integer.parseInt(DateTimeFormat.getFormat("yyyy").format(date));
	}

	public static int getMonth(final Date date) {
		return Integer.parseInt(DateTimeFormat.getFormat("M").format(date));
	}

	public static int getDay(final Date date) {
		return Integer.parseInt(DateTimeFormat.getFormat("d").format(date));
	}

	public static int daysOfMonth(final int year, final int month) {
		final Date start = getDate(year, month, 1);
		final Date end   = CalendarUtil.copyDate(start);
		CalendarUtil.addMonthsToDate(end, 1);
		return CalendarUtil.getDaysBetween(start, end);
	}

	public static String crmDateToString(final CrmDate crmDate, final DateTimeFormat format)
			throws ParseException {
		final String dateStr = crmDate.getYear()+""+
			(crmDate.getMonth()<10?"0"+crmDate.getMonth():crmDate.getMonth())+""+
				(crmDate.getDay()<10?"0"+crmDate.getDay():crmDate.getDay());
		return format.parse(dateStr).toString();
	}

	public static String crmDateToString(final CrmDate crmDate) {
		return crmDate.getYear()+"-"+
				(crmDate.getMonth()<10?"0"+crmDate.getMonth():crmDate.getMonth())+"-"+
				(crmDate.getDay()<10?"0"+crmDate.getDay():crmDate.getDay());
	}

	public static Date getDate(final CrmDate date) {
		return DateUtil.getDate(date.getYear(), date.getMonth(), date.getDay());
	}

	public static CrmDate newCrmDate() {
		final CrmDate date = new CrmDate();
		date.setDay(currentDay());
		date.setMonth(currentMonth());
		date.setYear(currentYear());
		return date;
	}
 }
