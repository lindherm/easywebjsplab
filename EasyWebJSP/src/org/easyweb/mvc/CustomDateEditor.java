package org.easyweb.mvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.propertyeditors.PropertiesEditor;

public class CustomDateEditor extends PropertiesEditor {
	SimpleDateFormat shortDateFormat;

	SimpleDateFormat fullDateFormat;

	@Override
	public String getAsText() {
		return fullDateFormat.format(getValue());
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			setValue(shortDateFormat.parse(text));
		} catch (ParseException e) {
			try {
				setValue(fullDateFormat.parse(text));
			} catch (ParseException e1) {
				throw new IllegalArgumentException(e1);
			}
		}
	}

	public void setFullDateFormat(String fullDateFormat) {
		this.fullDateFormat = new SimpleDateFormat(fullDateFormat);
	}

	public void setShortDateFormat(String shortDateFormat) {
		this.shortDateFormat = new SimpleDateFormat(shortDateFormat);
	}

}
