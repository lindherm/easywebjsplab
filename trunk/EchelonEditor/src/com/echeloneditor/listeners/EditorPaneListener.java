package com.echeloneditor.listeners;

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.echeloneditor.actions.XmlPreettifyAction;
import com.echeloneditor.main.CloseableTabComponent;
import com.echeloneditor.main.FontWidthRuler;
import com.echeloneditor.utils.ImageHelper;
import com.echeloneditor.utils.SwingUtils;
import com.echeloneditor.vo.StatusObject;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;

public class EditorPaneListener implements MouseListener, DocumentListener {
	public JTabbedPane tabbedPane;
	public StatusObject statusObject;
	public JPopupMenu jPopupMenu;
	public RSyntaxTextArea rSyntaxTextArea;

	public EditorPaneListener(final JTabbedPane tabbedPane, StatusObject statusObject) {
		try {
			UIManager.setLookAndFeel(new McWinLookAndFeel());
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		this.tabbedPane = tabbedPane;
		this.statusObject = statusObject;

		jPopupMenu = new JPopupMenu();
		JMenuItem selectAllItem = new JMenuItem("全选");
		selectAllItem.setIcon(ImageHelper.loadImage("select-all.png"));
		selectAllItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				rSyntaxTextArea.selectAll();
			}
		});

		JMenuItem cutItem = new JMenuItem("剪切");
		cutItem.setIcon(ImageHelper.loadImage("cut-to-clipboard.png"));
		cutItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				rSyntaxTextArea.cut();
			}
		});
		JMenuItem copyItem = new JMenuItem("复制");
		copyItem.setIcon(ImageHelper.loadImage("copy-to-clipboard.png"));
		copyItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				rSyntaxTextArea.copy();
			}
		});
		JMenuItem pasteItem = new JMenuItem("粘贴");
		pasteItem.setIcon(ImageHelper.loadImage("paste-from-clipboard.png"));
		pasteItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				rSyntaxTextArea.paste();
			}
		});
		JMenuItem formatItem = new JMenuItem("格式化");
		formatItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);

				CloseableTabComponent ctc = SwingUtils.getCloseableTabComponent(tabbedPane);
				int pos = ctc.getFilePath().lastIndexOf(".");
				String fileExt = ctc.getFilePath().substring(pos + 1);
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

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		eventHander(e);

		RSyntaxTextArea editorPane = (RSyntaxTextArea) e.getComponent();
		FontMetrics fontMetrics = editorPane.getFontMetrics(editorPane.getFont());
		int unit = fontMetrics.charWidth('A');

		int num = e.getX() / unit;
		statusObject.getCharNum().setText("字符数：" + num + "/B");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		eventHander(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (SwingUtilities.isRightMouseButton(e)) {
			// PopupMenuUI popupMenuUI = new PopupMenuUI(tabbedPane, (RSyntaxTextArea) e.getComponent());
			jPopupMenu.show(SwingUtils.getRSyntaxTextArea(tabbedPane), e.getX(), e.getY());
		}

		// 显示字符数
		RSyntaxTextArea edp = (RSyntaxTextArea) e.getComponent();
		String selText = edp.getSelectedText();
		if (selText == null) {
			return;
		}
		selText = selText.replaceAll("\n", "");
		selText = selText.replaceAll("\r", "");
		int num = selText.length();
		statusObject.getCharNum().setText("字符数：" + num);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void eventHander(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			// 画尺标
			Component component2 = SwingUtils.getColumnHeader(e.getComponent());
			FontWidthRuler fontWidthRuler = (FontWidthRuler) component2;
			fontWidthRuler.addSpin(e.getX());
			fontWidthRuler.repaint();
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		updateStatus(e);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		updateStatus(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		updateStatus(e);
	}

	private void updateStatus(DocumentEvent e) {
		statusObject.getSaveBtn().setEnabled(true);
		CloseableTabComponent closeableTabComponent = SwingUtils.getCloseableTabComponent(tabbedPane);
		closeableTabComponent.setModify(true);
	}
}
