package com.echelondownloader.navigation;

import javax.swing.ImageIcon;

import com.echelondownloader.util.ImageUtil;

public class TaskNode implements DownloadNode {

	public ImageIcon getImageIcon() {
		return ImageUtil.TASK_NODE_IMAGE;
	}

	public String getText() {
		return "хннЯ";
	}

}
