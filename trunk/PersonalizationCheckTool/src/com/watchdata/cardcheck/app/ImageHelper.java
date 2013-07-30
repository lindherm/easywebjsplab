package com.watchdata.cardcheck.app;

import java.awt.Toolkit;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

/**
 * @title ImageHelper.java
 * @description 读取图片文件
 * @author pei.li 2012-3-15
 * @version 1.0.0
 * @modify
 * @copyright watchdata
 */
public class ImageHelper {

	private static Logger log = Logger.getLogger(ImageHelper.class);

	private ImageHelper() {
	}

	public static ImageIcon loadImage(String url) {
		ImageIcon image = null;
		try {
			/* URL url = ImageHelper.class.getResource(url); */
			if (url != null) {
				java.awt.Image img = Toolkit.getDefaultToolkit().createImage(
						url);
				if (img != null)
					image = new ImageIcon(img);
			}
		} catch (Throwable ex) {
			log.info("ERROR: loading image failed");
		}
		return image;
	}
}
