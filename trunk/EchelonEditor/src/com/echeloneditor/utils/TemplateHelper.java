package com.echeloneditor.utils;

import java.io.InputStream;

public class TemplateHelper {
	public static InputStream load(String templateName) {
		String tPath = "/com/echeloneditor/resources/template/";
		tPath = tPath + templateName;
		return TemplateHelper.class.getResourceAsStream(tPath);
	}
}
