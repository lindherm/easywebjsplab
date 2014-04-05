/*
 * Folder.java
 *
 * Created on June 8, 2007, 5:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.gerenhua.cardcheck.utils.menu;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JComponent;

/**
 * 该类是个辅助类，实现折叠面板的一个逻辑面板，包括面板caption和内容。
 * 
 * @author William Chen
 * @mail rehte@hotmail.com
 */
class Folder extends JComponent {
	private String folderName="";
	// 缺省caption的高度
	private static final int CAPTION_HEIGHT = 25;
	// 标题组件
	private CaptionButton caption;
	// 放应用程序的抽屉
	private Drawer drawer;

	/**
	 * Creates a new instance of Folder
	 */
	Folder(String label, JComponent comp) {
		this(label, true, comp);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return folderName;
	}

	/**
	 * @param label
	 *            面板标题文字
	 * @param expanded
	 *            面板是否展开
	 * @param comp
	 *            应用程序组件
	 */
	Folder(String label, boolean expanded, JComponent comp) {
		this.folderName=label;
		// 设置自己的layout
		setLayout(new FolderTabLayout());
		// 生成并添加标题组件
		caption = new CaptionButton(label, expanded);
		add(caption);
		// 生成并添加抽屉
		drawer = new Drawer(expanded ? 1 : 0, comp);
		add(drawer);
	}

	CaptionButton getCaption() {
		return caption;
	}

	Drawer getDrawer() {
		return drawer;
	}

	/**
	 * 负责布局面板组件
	 */
	class FolderTabLayout implements LayoutManager {
		public void addLayoutComponent(String name, Component comp) {
		}

		public void removeLayoutComponent(Component comp) {
		}

		public Dimension preferredLayoutSize(Container parent) {
			return parent.getPreferredSize();
		}

		public Dimension minimumLayoutSize(Container parent) {
			return parent.getMinimumSize();
		}

		public void layoutContainer(Container parent) {
			int w = parent.getWidth();
			int h = parent.getHeight();
			// 标题栏总是固定高度
			caption.setBounds(0, 0, w, CAPTION_HEIGHT);
			// 抽屉只显示抽出的比例
			drawer.setBounds(0, CAPTION_HEIGHT, w, h - CAPTION_HEIGHT);
		}
	}

	// 获得该面板目前所需的空间大小：drawer+caption_height
	Dimension getRequiredDimension() {
		int w = drawer.getContentWidth();
		// 高度是抽屉的高度加上标题的高度，抽屉的高度是目前抽出的长度
		int h = (int) (drawer.getContentHeight() * drawer.getRatio()) + CAPTION_HEIGHT;
		return new Dimension(w, h);
	}
}
