package com.echeloneditor.vo;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

public class StatusObject {
	JLabel charNum;
	JLabel fileEncode;
	JLabel fileSize;
	
	JMenuItem fontItem;
	
	JButton saveBtn;

	public JButton getSaveBtn() {
		return saveBtn;
	}

	public void setSaveBtn(JButton saveBtn) {
		this.saveBtn = saveBtn;
	}

	public JLabel getCharNum() {
		return charNum;
	}

	public void setCharNum(JLabel charNum) {
		this.charNum = charNum;
	}

	public JMenuItem getFontItem() {
		return fontItem;
	}

	public void setFontItem(JMenuItem fontItem) {
		this.fontItem = fontItem;
	}

	public JLabel getFileEncode() {
		return fileEncode;
	}

	public void setFileEncode(JLabel fileEncode) {
		this.fileEncode = fileEncode;
	}

	public JLabel getFileSize() {
		return fileSize;
	}

	public void setFileSize(JLabel fileSize) {
		this.fileSize = fileSize;
	}
}
