package com.echeloneditor.utils;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.text.JTextComponent;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

public class SwingUtils {
	/**
	 * 获取选项卡选中的jeditorpane
	 * 
	 * @param tabbedPane
	 * @return
	 */
	public static JEditorPane getEditorPane(JTabbedPane tabbedPane) {
		Component com = tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
		if (com instanceof JScrollPane) {
			Component[] component = ((JScrollPane) com).getComponents();
			if (component[0] instanceof JEditorPane) {
				return (JEditorPane) component[0];
			} else if (component[0] instanceof JViewport) {
				Component[] component2 = ((JViewport) component[0]).getComponents();
				if (component2[0] instanceof JEditorPane) {
					return (JEditorPane) component2[0];
				}
			}
		}
		return new JEditorPane();
	}

	/**
	 * 获取选项卡选中的RSyntaxTextArea
	 * 
	 * @param tabbedPane
	 * @return
	 */
	public static RSyntaxTextArea getSyntaxArea(JTabbedPane tabbedPane) {
		Component com = tabbedPane.getSelectedComponent();
		if (com instanceof JScrollPane) {
			Component[] component = ((JScrollPane) com).getComponents();
			if (component[0] instanceof RSyntaxTextArea) {
				return (RSyntaxTextArea) component[0];
			} else if (component[0] instanceof JViewport) {
				Component[] component2 = ((JViewport) component[0]).getComponents();
				if (component2[0] instanceof RSyntaxTextArea) {
					return (RSyntaxTextArea) component2[0];
				}
			}
		}
		return new RSyntaxTextArea();
	}

	/**
	 * Get the JscrollPane that contains this EditorPane, or null if no JScrollPane is the parent of this editor
	 * 
	 * @param editorPane
	 * @return
	 */
	public static JScrollPane getScrollPane(JTextComponent editorPane) {
		Container p = editorPane.getParent();
		while (p != null) {
			if (p instanceof JScrollPane) {
				return (JScrollPane) p;
			}
			p = p.getParent();
		}
		return null;
	}

	public static Component getColumnHeader(Component editorComponent) {
		if (editorComponent instanceof RSyntaxTextArea) {
			JScrollPane scrollPane = getScrollPane((RSyntaxTextArea) editorComponent);
			JViewport jViewport = scrollPane.getColumnHeader();
			Component component2 = jViewport.getView();
			return component2;
		}
		return null;
	}

	public static RSyntaxTextArea createTextArea() {
		RSyntaxTextArea textArea = new RSyntaxTextArea(25, 70);
		textArea.setCaretPosition(0);
		//textArea.requestFocusInWindow();
		textArea.setMarkOccurrences(true);
		textArea.setCodeFoldingEnabled(true);
		textArea.setClearWhitespaceLinesEnabled(true);
		textArea.setAntiAliasingEnabled(true);
		return textArea;
	}
}
