/*
 * FolderPane.java
 *
 * Created on June 8, 2007, 5:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.gerenhua.tool.utils.menu;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.Scrollable;

/**
 * Windows文件浏览器左侧折叠面板组件类 实现了Scrollable，使其能放在JScrollPane中。
 * 
 * @author William Chen
 * @mail rehte@hotmail.com
 */
public class FolderPane extends JComponent implements Scrollable {
	// 所有的面板
	private ArrayList<Folder> tabs = new ArrayList<Folder>();
	// 是否启动动画效果
	private boolean animated;

	/** Creates a new instance of FolderPane */
	public FolderPane() {
		// set UI
		setUI(new FolderPaneUI());
		setLayout(new FolderPaneLayout());
	}

	public void addFolder(String title, JComponent comp) {
		addFolder(title, true, comp);
	}

	/**
	 * @param title
	 *            该面板的标题
	 * @param expanded
	 *            该面板初始状态是否展开
	 * @param comp
	 *            该面板的应用程序组件
	 */
	public void addFolder(String title, boolean expanded, JComponent comp) {
		Folder tab = new Folder(title, expanded, comp);
		tabs.add(tab);
		this.add(tab);
		// 为该面板添加抽屉事件
		((FolderPaneUI) ui).addTab(tab);
	}

	public boolean isAnimated() {
		return animated;
	}

	public void setAnimated(boolean animated) {
		this.animated = animated;
	}

	ArrayList<Folder> getTabs() {
		return tabs;
	}

	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}

	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		return 10;
	}

	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		return 100;
	}

	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	// 该布局类的布局管理器
	class FolderPaneLayout implements LayoutManager {
		private static final int INTER_TAB_PADDING = 15;

		public FolderPaneLayout() {
		}

		public void addLayoutComponent(String name, Component comp) {
		}

		public void removeLayoutComponent(Component comp) {
		}

		public Dimension preferredLayoutSize(Container parent) {
			// 补充实现preferredSize以便在JScrollPane调整时使用
			Insets insets = parent.getInsets();
			int w = 0;
			int h = 0;
			for (Folder tab : tabs) {
				Dimension dim = tab.getRequiredDimension();
				if (dim.width > w)
					w = dim.width;
				h += dim.height + INTER_TAB_PADDING;
			}
			w += insets.left + insets.right;
			h += insets.top + insets.bottom;
			return new Dimension(w, h);
		}

		public Dimension minimumLayoutSize(Container parent) {
			return new Dimension(0, 0);
		}

		public void layoutContainer(Container parent) {
			Insets insets = parent.getInsets();
			int x = insets.left;
			int y = insets.top;
			for (Folder tab : tabs) {
				Dimension dim = tab.getRequiredDimension();
				tab.setBounds(x, y, dim.width, dim.height);
				tab.doLayout();
				y += dim.height + INTER_TAB_PADDING;
			}
		}
	}
}
