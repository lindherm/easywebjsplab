package com.echeloneditor.utils;

import java.awt.Font;

public class FontUtil {
	/**
	 * getfont
	 * 
	 * @param config
	 * @return
	 */
	public static Font getFont(String config) {
		Font font = null;
		String[] resArray = config.split("\\|");
		if (resArray == null) {
			return null;
		}

		String fontName = resArray[0];
		int fontModel = Integer.parseInt(resArray[1]);
		int fontSize = Integer.parseInt(resArray[2]);

		font = new Font(fontName, fontModel, fontSize);
		return font;
	}
}
