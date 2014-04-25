package com.echeloneditor.utils;

import javax.swing.ImageIcon;
/**
 * 图片操作帮助类
 * @author Administrator
 *
 */
public class ImageHelper {

	private ImageHelper() {
	}
	/**
	 * load a picture from class images folder
	 * @param name
	 * @return
	 */
	public static ImageIcon loadImage(String name) {
		ImageIcon imageIcon=null;
		String imagePath="/com/echeloneditor/resources/images/";
		imageIcon=new ImageIcon(ImageHelper.class.getResource(imagePath+name));
		return imageIcon;
	}
}
