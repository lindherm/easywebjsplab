/*
 * CaptionButton.java
 *
 * Created on June 8, 2007, 5:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.gerenhua.cardcheck.utils.menu;

import java.awt.ItemSelectable;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JComponent;

/**
 * 该组件是折叠面板的标题栏组件。这是一个类似toggle-button的组件。
 * 
 * @author William Chen
 * @mail rehte@hotmail.com
 */
public class CaptionButton extends JComponent implements ItemSelectable {
	// 事件处理器集合
	private ArrayList<ItemListener> listeners = new ArrayList<ItemListener>();

	// 是否展开
	private boolean expanded;
	// 标题文字
	private String text;

	/** Creates a new instance of CaptionButton */
	public CaptionButton() {
		this(null, true);
	}

	/**
	 * @param text
	 *            标题
	 * @expanded 目前是否展开
	 */
	public CaptionButton(String text, boolean expanded) {
		this.text = text;
		this.expanded = expanded;
		setUI(new CaptionButtonUI());
	}

	// 添加选择事件处理器
	public void addItemListener(ItemListener l) {
		if (!listeners.contains(l))
			listeners.add(l);
	}

	// 删除选择事件处理器
	public void removeItemListener(ItemListener l) {
		if (listeners.contains(l))
			listeners.remove(l);
	}

	// 触发事件处理器
	protected void fireItemStateChanged(ItemEvent e) {
		for (ItemListener l : listeners)
			l.itemStateChanged(e);
	}

	//
	public Object[] getSelectedObjects() {
		if (!expanded)
			return null;
		return new Object[] { text };
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
		repaint();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		repaint();
	}
}
