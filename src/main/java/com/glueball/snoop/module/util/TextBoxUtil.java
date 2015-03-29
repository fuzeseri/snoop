package com.glueball.snoop.module.util;

import com.google.gwt.user.client.ui.ListBox;

public class TextBoxUtil {

	public static int getIndexByText(final ListBox box, final String value) {
		if (box==null)
			return 0;
		
		if ("".equals(value) || value == null)
			return 0;
		
		for (int index = 0; index <= box.getItemCount(); index++) { 
			if (value.equals(box.getItemText(index))) {
				return index;
			}
		}
		return 0;
	}

	public static int getIndexByInt(final ListBox box, final Integer value) {
		if (box == null)
			return 0;
		
		if (value == null)
			return 0;

		for (int index = 0; index <= box.getItemCount(); index++) {
			if (Integer.parseInt(box.getItemText(index)) == value.intValue()) {
				return index;
			}
		}
		return 0;
	}
}
