package com.echelondownloader.navigation;

import javax.swing.ImageIcon;

import com.echelondownloader.util.ImageUtil;

public class FinishNode implements DownloadNode {

	public ImageIcon getImageIcon() {
		return ImageUtil.FINISH_NODE_IMAGE;
	}

	public String getText() {
		return "обтьмЙЁи";
	}

}
