package com.echelondownloader.state;

import javax.swing.ImageIcon;


import com.echelondownloader.object.Resource;
import com.echelondownloader.util.ImageUtil;

public class Downloading extends AbstractState {

	@Override
	public ImageIcon getIcon() {
		return ImageUtil.DOWNLOADING_IMAGE;
	}
	public String getState() {
		return "downloading";
	}
}
