package com.echeloneditor.listeners;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.echeloneditor.main.CloseableTabComponent;
import com.echeloneditor.utils.SwingUtils;
import com.echeloneditor.vo.StatusObject;

public class TabbedPaneChangeListener implements MouseListener {

	private static final Logger log = Logger.getLogger(TabbedPaneChangeListener.class);

	public JPopupMenu jPopupMenu;

	private JTabbedPane tabbedPane;
	public StatusObject statusObject;

	public TabbedPaneChangeListener(final JTabbedPane tabbedPane, final StatusObject statusObject) {
		this.tabbedPane = tabbedPane;
		this.statusObject = statusObject;

		jPopupMenu = new JPopupMenu();
		JMenuItem closeAll = new JMenuItem("关闭所有");
		closeAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.removeAll();
				statusObject.getFontItem().setEnabled(false);
			}
		});
		JMenuItem closeOther = new JMenuItem("关闭其他");
		closeOther.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectIndex = tabbedPane.getSelectedIndex();
				int count = tabbedPane.getTabCount();
				int left = selectIndex;
				int right = count - (selectIndex + 1);
				for (int i = 0; i < left; i++) {
					tabbedPane.remove(0);
					tabbedPane.repaint();
				}

				for (int j = 0; j < right; j++) {
					tabbedPane.remove(tabbedPane.getTabCount() - 1);
					tabbedPane.repaint();
				}
			}
		});
		jPopupMenu.add(closeOther);
		jPopupMenu.addSeparator();
		jPopupMenu.add(closeAll);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int tabCount = tabbedPane.getTabCount();
		if (tabCount > 0) {
			CloseableTabComponent closeableTabComponent = SwingUtils.getCloseableTabComponent(tabbedPane);
			String encode = closeableTabComponent.getFileEncode();
			String fileSize = closeableTabComponent.getFileSzie();
			boolean modify = closeableTabComponent.isModify();
			if (encode != null) {
				// log.debug("testencode:" + encode);
				statusObject.getFileSize().setText("文件大小：" + fileSize);
				statusObject.getFileEncode().setText("文件编码：" + encode);
				statusObject.getSaveBtn().setEnabled(modify);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.isPopupTrigger()) {
			Component component = SwingUtilities.getRoot(e.getComponent());
			int frameX = component.getX();
			int eX = e.getXOnScreen();

			jPopupMenu.show(tabbedPane, eX - frameX, e.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
