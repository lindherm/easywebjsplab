package com.echeloneditor.actions;

import javax.swing.text.JTextComponent;

import org.fife.ui.rtextarea.SearchEngine;

public class FindAndReplaceAction {
	public FindAndReplaceAction() {

	}

	/**
	 * find a string and select in the container
	 * 
	 * @param jTextComponent
	 * @param targetStr
	 * @return
	 */
	public static boolean find(JTextComponent jTextComponent, String targetStr, boolean farword, boolean machCase, boolean wholeWord) {
		boolean isFind = false;

		if (farword) {
			int start = jTextComponent.getSelectionEnd();
			if (jTextComponent.getSelectionEnd() == jTextComponent.getSelectionStart()) {
				start++;
			}
			if (start >= jTextComponent.getDocument().getLength()) {
				start = jTextComponent.getDocument().getLength();
			}
			String searchIn = jTextComponent.getText().substring(start);
			int pos = SearchEngine.getNextMatchPos(targetStr, searchIn, farword, machCase, wholeWord);

			if (pos != -1) {
				jTextComponent.select(start + pos, start + pos + targetStr.length());
				isFind = true;
			}
		} else {
			int start = jTextComponent.getSelectionStart();
			if (jTextComponent.getSelectionEnd() == jTextComponent.getSelectionStart()) {
				start--;
			}
			if (start <= 0) {
				start = 0;
			}
			String searchIn = jTextComponent.getText().substring(0, start);

			int pos = SearchEngine.getNextMatchPos(targetStr, searchIn, farword, machCase, wholeWord);

			if (pos != -1) {
				jTextComponent.select(pos, pos + targetStr.length());
				isFind = true;
			}
		}

		return isFind;
	}
}
