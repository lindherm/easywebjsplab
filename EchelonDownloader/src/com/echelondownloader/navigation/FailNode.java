package com.echelondownloader.navigation;

import javax.swing.ImageIcon;

import com.echelondownloader.util.ImageUtil;

public class FailNode implements DownloadNode {

	public ImageIcon getImageIcon() {
		return ImageUtil.FAIL_NODE_IMAGE;
	}

	public String getText() {
		return "œ¬‘ÿ ß∞‹";
	}

}
