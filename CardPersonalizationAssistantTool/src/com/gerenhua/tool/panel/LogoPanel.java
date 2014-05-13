package com.gerenhua.tool.panel;

import javax.swing.JPanel;

import com.gerenhua.tool.utils.menu.FolderPaneUI;

/**
 * @author pei.li 2012.4.21
 * @version 类描述 进入的初始界面
 */

public class LogoPanel extends JPanel {

	private static final long serialVersionUID = -2439013595850901209L;
	public static String proPath = System.getProperty("user.dir");

	/**
	 * Create the panel
	 * 
	 * @return
	 * @throws Exception
	 */
	public LogoPanel() throws Exception {
		// super(ImageIO.read(LogoPanel.class.getResource("/com/gerenhua/tool/resources/images/wacthdata.png")));
		setBackground(FolderPaneUI.BACK_COLOR);
	}

}
