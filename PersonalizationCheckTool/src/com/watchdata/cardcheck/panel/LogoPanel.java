package com.watchdata.cardcheck.panel;

import java.io.File;

import javax.imageio.ImageIO;

import com.watchdata.cardcheck.app.JImagePanel;

/** 
 * @author pei.li 2012.4.21
 * @version 
 * 类描述   进入的初始界面
 */

public class LogoPanel extends JImagePanel {
	
	private static final long serialVersionUID = -2439013595850901209L;
	public static String proPath = System.getProperty("user.dir");

	/**
	 * Create the panel
	 * @return 
	 * @throws Exception 
	 */
	public LogoPanel() throws Exception {
		super(ImageIO.read(new File(proPath + "/resources/images/wacthdata.png")));
	}

}
