/*
 * FolderPaneUI.java
 *
 * Created on June 9, 2007, 9:33 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.gerenhua.cardcheck.utils.menu;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.Timer;
import javax.swing.plaf.ComponentUI;

/**
 * FolderPane的UI类
 * 
 * @author William Chen
 * @mail rehte@hotmail.com
 */
public class FolderPaneUI extends ComponentUI {
	// 缺省常量
	private static final Color BACK_COLOR = new Color(116, 149, 226);
	private static final int LEFT_PADDING = 12;
	private static final int TOP_PADDING = 12;
	private static final int RIGHT_PADDING = 12;
	private static final int BOTTOM_PADDING = 12;
	private static final double INCDEC = 0.085;
	private static final double DECRATIO = 0.925;
	private static final int ANIMATION_INTERVAL = 15;
	// FolderPane的实例
	private FolderPane pane;

	// 安装ui
	public void installUI(JComponent c) {
		c.setOpaque(true);
		c.setBackground(BACK_COLOR);
		c.setBorder(BorderFactory.createEmptyBorder(TOP_PADDING, LEFT_PADDING, RIGHT_PADDING, BOTTOM_PADDING));
		pane = (FolderPane) c;
	}

	// 为该面板添加折叠处理事件
	void addTab(Folder tab) {
		tab.getCaption().addItemListener(new FolderTabItemListener(tab));
	}

	// 折叠事件处理器
	class FolderTabItemListener implements ItemListener {
		private Folder tab;
		private Timer timer;

		public FolderTabItemListener(Folder t) {
			tab = t;
		}

		public void itemStateChanged(ItemEvent e) {
			if (pane.isAnimated()) {
				// 需要动画效果，先关闭可能的timer
				if (timer != null) {
					if (timer.isRunning())
						timer.stop();
				}
				// 生成新的timer
				timer = new Timer(ANIMATION_INTERVAL, null);
				// 根据展开或者是关闭选择不同的动画处理动作
				ActionListener action;
				if (e.getStateChange() == e.DESELECTED)
					action = new FolderingAction(tab, timer);
				else
					action = new ExpandingAction(tab, timer);
				timer.addActionListener(action);
				// 启动timer
				timer.start();
			} else {
				// 无动画效果，直接展开或者关闭
				tab.getDrawer().setRatio(e.getStateChange() == e.DESELECTED ? 0 : 1.0);
				pane.doLayout();
			}
			// 确保界面更新
			pane.repaint();
		}
	}

	// 折叠事件基类
	abstract class FolderAction implements ActionListener {
		// 当前折叠的面板
		private Folder tab;
		// 相关的时钟
		private Timer timer;
		// 下面两个参数计算抽屉抽出或者关闭的实时速度
		private double ratio;
		private double exponent;

		public FolderAction(Folder t, Timer timer) {
			tab = t;
			this.timer = timer;
			ratio = INCDEC;
			exponent = DECRATIO;
			// 将抽屉置于动画效果状态
			tab.getDrawer().setAnimating(true);
		}

		// 计算速度变化，可以允许非匀速运动效果的抽屉
		protected double getDelta() {
			double r = ratio;
			ratio = ratio * exponent;
			return r;
		}

		public void actionPerformed(ActionEvent e) {
			// 计算新的值
			double r = delta(tab.getDrawer().getRatio(), getDelta());
			if (overflow(r)) {
				// 溢出则置为界限值
				r = bound();
				// 设置抽屉状态，停止动画效果，重新布局折叠面板，停止时钟，最好释放时钟
				tab.getDrawer().setAnimating(false);
				doLayout(r);
				timer.stop();
				timer = null;
			} else {
				// 普通动画的一帧，设置抽屉状态，重新布局折叠面板
				doLayout(r);
			}
		}

		private void doLayout(final double r) {
			tab.getDrawer().setRatio(r);
			// 相比以前添加了动画时，动态调整父容器布局，这对于父容器是JScrolPane
			// 的FolderPane来说很重要，它需要实时调整ScrollBar
			Container parent = pane.getParent();
			if (parent != null)
				parent.doLayout();
			// 调整自己的尺寸布局
			pane.doLayout();
		}

		// 计算新的位置和透明度
		protected abstract double delta(double r, double d);

		// 当前值是否溢出，比如超过1.0或者小于了0
		protected abstract boolean overflow(double r);

		// 当前运动的界限值
		protected abstract double bound();
	}

	// 展开用的动画处理事件
	class ExpandingAction extends FolderAction {
		public ExpandingAction(Folder t, Timer timer) {
			super(t, timer);
		}

		protected double delta(double r, double d) {
			return r + d;
		}// 递增

		protected boolean overflow(double r) {
			return r > 1;
		}// 上限为1

		protected double bound() {
			return 1;
		}// 上限值位1
	}

	// 折叠时用的动画处理事件
	class FolderingAction extends FolderAction {
		public FolderingAction(Folder t, Timer timer) {
			super(t, timer);
		}

		protected double delta(double r, double d) {
			return r - d;
		}// 递减

		protected boolean overflow(double r) {
			return r < 0;
		}// 下限为0

		protected double bound() {
			return 0;
		}// 下限值为0
	}
}
