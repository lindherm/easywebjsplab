package com.echeloneditor.vo;

import javax.swing.JLabel;
import javax.swing.JMenuItem;

public class StatusObject {
	JLabel carNum;
	JLabel fileEncode;
	JLabel fileSize;
	
	JMenuItem fontItem;

	public JMenuItem getFontItem() {
		return fontItem;
	}

	public void setFontItem(JMenuItem fontItem) {
		this.fontItem = fontItem;
	}

	public JLabel getCarNum() {
		return carNum;
	}

	public void setCarNum(JLabel carNum) {
		this.carNum = carNum;
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
