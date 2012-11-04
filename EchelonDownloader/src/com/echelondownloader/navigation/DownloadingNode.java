package com.echelondownloader.navigation;

import javax.swing.ImageIcon;

import com.echelondownloader.util.ImageUtil;

public class DownloadingNode implements DownloadNode {

	public ImageIcon getImageIcon() {
		return ImageUtil.DOWNLOADING_NODE_IMAGE;
	}

	public String getText() {
		return "ÕıÔÚÏÂÔØ";
	}

}
