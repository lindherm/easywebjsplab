package com.echeloneditor.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.echeloneditor.actions.XmlPreettifyAction;
import com.echeloneditor.utils.ImageHelper;
import com.echeloneditor.utils.SwingUtils;

public class PopupMenuUI {
	public JTabbedPane tabbedPane;
	public JPopupMenu jPopupMenu;

	public PopupMenuUI(final JTabbedPane tabbedPane, final RSyntaxTextArea rSyntaxTextArea) {
		this.tabbedPane = tabbedPane;
		jPopupMenu = new JPopupMenu();

		JMenuItem selectAllItem = new JMenuItem("全选");
		selectAllItem.setIcon(ImageHelper.loadImage("select-all.png"));
		selectAllItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				rSyntaxTextArea.selectAll();
			}
		});

		JMenuItem cutItem = new JMenuItem("剪切");
		cutItem.setIcon(ImageHelper.loadImage("cut-to-clipboard.png"));
		cutItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				rSyntaxTextArea.cut();
			}
		});
		JMenuItem copyItem = new JMenuItem("复制");
		copyItem.setIcon(ImageHelper.loadImage("copy-to-clipboard.png"));
		copyItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				rSyntaxTextArea.copy();
			}
		});
		JMenuItem pasteItem = new JMenuItem("粘贴");
		pasteItem.setIcon(ImageHelper.loadImage("paste-from-clipboard.png"));
		pasteItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				rSyntaxTextArea.paste();
			}
		});
		JMenuItem formatItem = new JMenuItem("格式化");
		formatItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				CloseableTabComponent ctc = SwingUtils.getCloseableTabComponent(tabbedPane);
				int pos = ctc.getFilePath().lastIndexOf(".");
				String fileExt = ctc.getFilePath().substring(pos+1);
				if (fileExt.equals("xml")) {
					boolean Success = XmlPreettifyAction.format(rSyntaxTextArea);
					if (!Success) {
						JOptionPane.showMessageDialog(null, "格式化失败");
						return;
					}
				}

			}
		});

		jPopupMenu.add(selectAllItem);
		jPopupMenu.add(cutItem);
		jPopupMenu.add(copyItem);
		jPopupMenu.add(pasteItem);
		jPopupMenu.addSeparator();
		jPopupMenu.add(formatItem);
	}
}
