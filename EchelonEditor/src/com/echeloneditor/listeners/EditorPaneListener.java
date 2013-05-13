package com.echeloneditor.listeners;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.echeloneditor.actions.EmvFormatAction;
import com.echeloneditor.actions.XmlPreettifyAction;
import com.echeloneditor.main.CloseableTabComponent;
import com.echeloneditor.main.FontWidthRuler;
import com.echeloneditor.utils.SwingUtils;
import com.echeloneditor.vo.StatusObject;

public class EditorPaneListener implements MouseListener, DocumentListener, MouseMotionListener, KeyListener {
	public JTabbedPane tabbedPane;
	public StatusObject statusObject;
	public JPopupMenu jPopupMenu;
	public RSyntaxTextArea rSyntaxTextArea;

	public EditorPaneListener(final JTabbedPane tabbedPane, StatusObject statusObject) {
		this.tabbedPane = tabbedPane;
		this.statusObject = statusObject;

		jPopupMenu = new JPopupMenu();

		JMenuItem cutItem = new JMenuItem("剪切");
		cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		cutItem.setIcon(new ImageIcon(EditorPaneListener.class.getResource("/com/echeloneditor/resources/images/20130508105503688_easyicon_net_24.png")));
		cutItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				rSyntaxTextArea.cut();
			}
		});
		JMenuItem copyItem = new JMenuItem("复制");
		copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		copyItem.setIcon(new ImageIcon(EditorPaneListener.class.getResource("/com/echeloneditor/resources/images/20130508112701510_easyicon_net_24.png")));
		copyItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				rSyntaxTextArea.copy();
			}
		});
		JMenuItem pasteItem = new JMenuItem("粘贴");
		pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		pasteItem.setIcon(new ImageIcon(EditorPaneListener.class.getResource("/com/echeloneditor/resources/images/20130508105319839_easyicon_net_24.png")));
		pasteItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				rSyntaxTextArea.paste();
			}
		});
		JMenuItem formatItem = new JMenuItem("格式化");
		formatItem.setIcon(new ImageIcon(EditorPaneListener.class.getResource("/com/echeloneditor/resources/images/20130508011341649_easyicon_net_24.png")));
		formatItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
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

		JMenuItem menuItem = new JMenuItem("撤销");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				rSyntaxTextArea.undoLastAction();
			}
		});
		menuItem.setIcon(new ImageIcon(EditorPaneListener.class.getResource("/com/echeloneditor/resources/images/20130508104933496_easyicon_net_24.png")));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		jPopupMenu.add(menuItem);

		JMenuItem menuItem_1 = new JMenuItem("重做");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				rSyntaxTextArea.redoLastAction();
			}
		});
		menuItem_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
		menuItem_1.setIcon(new ImageIcon(EditorPaneListener.class.getResource("/com/echeloneditor/resources/images/20130508104655987_easyicon_net_24.png")));
		jPopupMenu.add(menuItem_1);

		JSeparator separator = new JSeparator();
		jPopupMenu.add(separator);
		jPopupMenu.add(cutItem);
		jPopupMenu.add(copyItem);
		jPopupMenu.add(pasteItem);
		JMenuItem selectAllItem = new JMenuItem("全选");
		selectAllItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		selectAllItem.setIcon(new ImageIcon(EditorPaneListener.class.getResource("/com/echeloneditor/resources/images/20130508105939375_easyicon_net_24.png")));
		selectAllItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				rSyntaxTextArea.selectAll();
			}
		});

		JMenuItem menuItem_2 = new JMenuItem("删除");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				if (rSyntaxTextArea == null) {
					return;
				}
				int selectStart = rSyntaxTextArea.getSelectionStart();
				if (selectStart < 0) {
					return;
				}
				if (rSyntaxTextArea.getSelectedText() == null) {
					return;
				}
				int len = rSyntaxTextArea.getSelectedText().length();
				try {
					rSyntaxTextArea.getDocument().remove(selectStart, len);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		menuItem_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		menuItem_2.setIcon(new ImageIcon(EditorPaneListener.class.getResource("/com/echeloneditor/resources/images/20130508105713413_easyicon_net_24.png")));
		jPopupMenu.add(menuItem_2);

		JSeparator separator_1 = new JSeparator();
		jPopupMenu.add(separator_1);

		jPopupMenu.add(selectAllItem);
		jPopupMenu.addSeparator();
		jPopupMenu.add(formatItem);

		JMenuItem mntmTlv = new JMenuItem("TLV");
		mntmTlv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RSyntaxTextArea rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				try {
					EmvFormatAction.format(rSyntaxTextArea);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmTlv.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_MASK));
		mntmTlv.setIcon(new ImageIcon(EditorPaneListener.class.getResource("/com/echeloneditor/resources/images/20130509034342785_easyicon_net_24.png")));
		jPopupMenu.add(mntmTlv);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			showCharNumOnStatusBar(e);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (SwingUtilities.isRightMouseButton(e)) {
			// PopupMenuUI popupMenuUI = new PopupMenuUI(tabbedPane, (RSyntaxTextArea) e.getComponent());
			jPopupMenu.show(SwingUtils.getRSyntaxTextArea(tabbedPane), e.getX(), e.getY());
		} else {
			showCharNumOnStatusBar(e);
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

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if (SwingUtilities.isLeftMouseButton(e)) {
			eventHander(e);
			showCharNumOnStatusBar(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if ((e.isShiftDown() && keyCode == KeyEvent.VK_LEFT) || (e.isShiftDown() && keyCode == KeyEvent.VK_RIGHT) || (e.isShiftDown() && keyCode == KeyEvent.VK_UP) || (e.isShiftDown() && keyCode == KeyEvent.VK_DOWN)) {
			showCharNumOnStatusBar(e);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private void updateStatus(DocumentEvent e) {
		statusObject.getSaveBtn().setEnabled(true);
		CloseableTabComponent closeableTabComponent = SwingUtils.getCloseableTabComponent(tabbedPane);
		closeableTabComponent.setModify(true);
	}

	private void showCharNumOnStatusBar(InputEvent e) {
		// 显示字符数
		RSyntaxTextArea edp = (RSyntaxTextArea) e.getComponent();
		String selText = edp.getSelectedText();
		if (selText == null || selText.isEmpty()) {
			return;
		}
		System.out.println(selText);
		selText = selText.replaceAll("\n", "").replaceAll("\r", "").replaceAll("\r\n", "");
		int num = selText.length();
		statusObject.getCharNum().setText("字符数：" + num);
	}

}
