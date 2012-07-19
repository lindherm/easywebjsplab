package com.echeloneditor.listeners;

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.echeloneditor.main.CloseableTabComponent;
import com.echeloneditor.main.FontWidthRuler;
import com.echeloneditor.main.PopupMenuUI;
import com.echeloneditor.utils.SwingUtils;
import com.echeloneditor.vo.StatusObject;

public class EditorPaneListener implements MouseListener, DocumentListener {
	public JTabbedPane tabbedPane;
	public StatusObject statusObject;

	public EditorPaneListener(JTabbedPane tabbedPane, StatusObject statusObject) {
		this.tabbedPane = tabbedPane;
		this.statusObject = statusObject;
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
			PopupMenuUI popupMenuUI=new PopupMenuUI((RSyntaxTextArea)e.getComponent());
			popupMenuUI.jPopupMenu.show(SwingUtils.getRSyntaxTextArea(tabbedPane),e.getX(), e.getY());
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
		CloseableTabComponent closeableTabComponent=SwingUtils.getCloseableTabComponent(tabbedPane);
		closeableTabComponent.setModify(true);
	}
}
