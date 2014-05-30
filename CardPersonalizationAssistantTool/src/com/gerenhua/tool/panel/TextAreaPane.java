package com.gerenhua.tool.panel;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;

/**
 * 
 * @description: 带鼠标右键功能的文本块
 * @author: juan.jiang 2012-2-22
 * @version: 1.0.0
 * @modify:
 * @Copyright: watchdata
 */
class TextPaneMenu extends JTextPane implements MouseListener {

	private static final long serialVersionUID = -2308615404205560110L;

	private JPopupMenu pop = null; // 弹出菜单

	private JMenuItem copy = null, clear = null;// 功能菜单 :复制，清空
	private JMenuItem paste = null, cut = null; // 功能菜单 :粘贴，剪切

	public TextPaneMenu() {
		super();
		init();
	}

	private void init() {
		this.addMouseListener(this);
		pop = new JPopupMenu();
		pop.add(copy = new JMenuItem("复制"));
		pop.add(clear = new JMenuItem("清空"));

		paste = new JMenuItem("粘贴");
		cut = new JMenuItem("剪切");

//		copy.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
//		clear.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_MASK));

		paste.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));
		cut.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));
		copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action(e);
			}
		});
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action(e);
			}
		});
		paste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action(e);
			}
		});
		cut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action(e);
			}
		});
		this.add(pop);
	}

	/**
	 * 菜单动作
	 * 
	 * @param e
	 */
	public void action(ActionEvent e) {
		String str = e.getActionCommand();
		if (str.equals(copy.getText())) { // 复制
			this.copy();
		} else if (str.equals(clear.getText())) { // 清空
			try {
				this.getDocument().remove(0, this.getDocument().getLength());
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		} else if (str.equals(paste.getText())) { // 粘贴
			this.paste();
		} else if (str.equals(cut.getText())) { // 剪切
			this.cut();
		}
	}

	public JPopupMenu getPop() {
		return pop;
	}

	public void setPop(JPopupMenu pop) {
		this.pop = pop;
	}

	/**
	 * 剪切板中是否有文本数据可供粘贴
	 * 
	 * @return true为有文本数据
	 */
	public boolean isClipboardString() {
		boolean b = false;
		Clipboard clipboard = this.getToolkit().getSystemClipboard();
		Transferable content = clipboard.getContents(this);
		try {
			if (content.getTransferData(DataFlavor.stringFlavor) instanceof String) {
				b = true;
			}
		} catch (Exception e) {
		}
		return b;
	}

	/**
	 * 文本组件中是否具备复制的条件
	 * 
	 * @return true为具备
	 */
	public boolean isCanCopy() {
		boolean b = false;
		int start = this.getSelectionStart();
		int end = this.getSelectionEnd();
		if (start != end)
			b = true;
		return b;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			copy.setEnabled(isCanCopy());
			paste.setEnabled(isClipboardString());
			cut.setEnabled(isCanCopy());
			pop.show(this, e.getX(), e.getY());
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

}
