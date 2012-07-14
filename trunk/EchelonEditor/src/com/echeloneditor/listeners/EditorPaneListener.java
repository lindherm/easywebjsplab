package com.echeloneditor.listeners;

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.echeloneditor.main.FontWidthRuler;
import com.echeloneditor.utils.SwingUtils;
import com.echeloneditor.vo.StatusObject;

public class EditorPaneListener implements MouseListener {
	public StatusObject statusObject;

	public EditorPaneListener(StatusObject statusObject) {
		this.statusObject = statusObject;
	}

	public EditorPaneListener() {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		eventHander(e);

		RSyntaxTextArea editorPane = (RSyntaxTextArea) e.getComponent();
		FontMetrics fontMetrics = editorPane.getFontMetrics(editorPane.getFont());
		int unit = fontMetrics.charWidth('A');

		int num = e.getX() / unit;
		statusObject.getCarNum().setText("字符数：" + num + "/B");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		eventHander(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		eventHander(e);

		// 显示字符数
		Component component=e.getComponent();
		RSyntaxTextArea edp = (RSyntaxTextArea) e.getComponent();
		String selText = edp.getSelectedText();
		if (selText == null) {
			return;
		}
		selText = selText.replaceAll("\n", "");
		int num = selText.length();
		statusObject.getCarNum().setText("字符数：" + num);
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
		// 画尺标
		Component component2 = SwingUtils.getColumnHeader(e.getComponent());
		FontWidthRuler fontWidthRuler = (FontWidthRuler) component2;
		fontWidthRuler.addSpin(e.getX());
		fontWidthRuler.repaint();

	}
}
