package com.echeloneditor.utils;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.text.JTextComponent;

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
		if (editorComponent instanceof JEditorPane) {
			JScrollPane scrollPane = getScrollPane((JEditorPane) editorComponent);
			JViewport jViewport = scrollPane.getColumnHeader();
			Component component2 = jViewport.getView();
			return component2;
		}
		return null;
	}

}
