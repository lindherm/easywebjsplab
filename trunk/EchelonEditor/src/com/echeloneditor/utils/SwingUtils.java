package com.echeloneditor.utils;

import java.awt.Component;
import java.awt.Container;
import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.text.JTextComponent;

import org.fife.ui.hex.ByteBuffer;
import org.fife.ui.hex.swing.HexEditor;
import org.fife.ui.hex.swing.HexTable;
import org.fife.ui.hex.swing.HexTableModel;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.echeloneditor.main.CloseableTabComponent;

public class SwingUtils {

	public static CloseableTabComponent getCloseableTabComponent(JTabbedPane tabbedPane) {
		Component component = tabbedPane.getTabComponentAt(tabbedPane.getSelectedIndex());
		CloseableTabComponent closeableTabComponent = (CloseableTabComponent) component;
		return closeableTabComponent;
	}

	/**
	 * 获取选项卡选中的RSyntaxTextArea
	 * 
	 * @param tabbedPane
	 * @return
	 */
	public static RSyntaxTextArea getRSyntaxTextArea(JTabbedPane tabbedPane) {
		Component com = tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
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
		return null;
	}

	/**
	 * 获取选项卡选中的RSyntaxTextArea
	 * 
	 * @param tabbedPane
	 * @return
	 */
	public static HexEditor getHexEditor(JTabbedPane tabbedPane) {
		Component com = tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
		if (com instanceof HexEditor) {
			return (HexEditor) com;
		}
		return null;
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
		// textArea.requestFocusInWindow();
		textArea.setMarkOccurrences(true);
		textArea.setCodeFoldingEnabled(true);
		textArea.setClearWhitespaceLinesEnabled(true);
		textArea.setAntiAliasingEnabled(true);
		return textArea;
	}

	/**
	 * 设置焦点选项卡的标题
	 */
	public static void setTabbedPaneTitle(JTabbedPane tabbedPane, String fileName) {
		tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), fileName);
		Component componentl = tabbedPane.getTabComponentAt(tabbedPane.getSelectedIndex());
		((CloseableTabComponent) componentl).titleLabel.setText(fileName + "  ");
	}

	/**
	 * 获取编辑区内容
	 * 
	 * @param tabbedPane
	 * @return
	 */
	public static String getContent(JTabbedPane tabbedPane) {
		String text ="";
		RSyntaxTextArea rSyntaxTextArea=SwingUtils.getRSyntaxTextArea(tabbedPane);
		if (rSyntaxTextArea!=null) {
			text=rSyntaxTextArea.getText();
		}else {
			HexEditor hexEditor=SwingUtils.getHexEditor(tabbedPane);
			
			CloseableTabComponent closeableTabComponent=SwingUtils.getCloseableTabComponent(tabbedPane);
			
			Component component=hexEditor.getViewport();
			if (component instanceof JViewport) {
				Component component2=((JViewport)component).getView();
				HexTableModel hexTableModel=(HexTableModel)((HexTable)component2).getModel();
				ByteBuffer byteBuffer=hexTableModel.getDoc();
				try {
					text=new String(byteBuffer.getBuffer(),closeableTabComponent.getFileEncode());
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return text;
	}

	/**
	 * 设置编辑区内容
	 * 
	 * @param tabbedPane
	 * @param text
	 */
	public static void setContent(JTabbedPane tabbedPane, String text) {
		SwingUtils.getRSyntaxTextArea(tabbedPane).setText(text);
	}

	/**
	 * 根据文件扩展名获取文件的内容类型
	 * 
	 * @param file
	 */
	public static String getFileContentType(File file) {
		String result = "";
		String fileName = file.getName();
		int pos = fileName.lastIndexOf(".");

		String fileExt = fileName.substring(pos + 1, fileName.length());
		result = "text/" + fileExt;
		System.out.println(result);
		return result;
	}
}
