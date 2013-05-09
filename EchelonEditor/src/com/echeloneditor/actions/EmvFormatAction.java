package com.echeloneditor.actions;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

public class EmvFormatAction {
	/**
	 * tlv format
	 * @param rSyntaxTextArea
	 * @return
	 */
	public static boolean format(RSyntaxTextArea rSyntaxTextArea) {
		String hexLen=rSyntaxTextArea.getSelectedText();
		int intlen=Integer.valueOf(hexLen, 10);
		
		String 
		return true;
	}
}
