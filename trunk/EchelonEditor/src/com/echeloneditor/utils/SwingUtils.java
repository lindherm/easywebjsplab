package com.echeloneditor.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

import org.fife.ui.hex.ByteBuffer;
import org.fife.ui.hex.swing.HexEditor;
import org.fife.ui.hex.swing.HexTable;
import org.fife.ui.hex.swing.HexTableModel;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.echeloneditor.main.CloseableTabComponent;

public class SwingUtils {
	/**
	 * get the select closeableTabComponent use the indicate by the tabbedpane
	 * 
	 * @param tabbedPane
	 * @return
	 */
	public static CloseableTabComponent getCloseableTabComponent(JTabbedPane tabbedPane) {
		Component component = tabbedPane.getTabComponentAt(tabbedPane.getSelectedIndex());
		return (CloseableTabComponent) component;
	}

	/**
	 * get columnHeader with the textcomponent
	 * 
	 * @param editorComponent
	 * @return
	 */
	public static Component getColumnHeader(Component editorComponent) {
		Component target = null;
		if (editorComponent instanceof RSyntaxTextArea) {
			JScrollPane scrollPane = getScrollPane((RSyntaxTextArea) editorComponent);
			JViewport jViewport = scrollPane.getColumnHeader();
			target = jViewport.getView();
		}
		return target;
	}

	/**
	 * 获取选项卡选中的RSyntaxTextArea
	 * 
	 * @param tabbedPane
	 * @return
	 */
	public static RSyntaxTextArea getRSyntaxTextArea(JTabbedPane tabbedPane) {
		RSyntaxTextArea rSyntaxTextArea = null;
		Component com = tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
		if (com instanceof JScrollPane) {
			Component[] component = ((JScrollPane) com).getComponents();
			if (component[0] instanceof RSyntaxTextArea) {
				rSyntaxTextArea = (RSyntaxTextArea) component[0];
			} else if (component[0] instanceof JViewport) {
				Component[] component2 = ((JViewport) component[0]).getComponents();
				if (component2[0] instanceof RSyntaxTextArea) {
					rSyntaxTextArea = (RSyntaxTextArea) component2[0];
				}
			}
		}
		return rSyntaxTextArea;
	}

	/**
	 * 获取选项卡选中的RSyntaxTextArea
	 * 
	 * @param tabbedPane
	 * @return
	 */
	public static HexEditor getHexEditor(JTabbedPane tabbedPane) {
		HexEditor hexEditor = null;
		Component com = tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
		return (com instanceof HexEditor) ? (HexEditor) com : hexEditor;
	}

	/**
	 * Get the JscrollPane that contains this EditorPane, or null if no JScrollPane is the parent of this editor
	 * 
	 * @param editorPane
	 * @return
	 */
	public static JScrollPane getScrollPane(JTextComponent editorPane) {
		JScrollPane jScrollPane = null;
		Container p = editorPane.getParent();
		if (p != null) {
			jScrollPane = (p instanceof JScrollPane) ? (JScrollPane) p : (JScrollPane) p.getParent();
		}
		return jScrollPane;
	}

	/**
	 * new a RSyntaxTextArea editor
	 * 
	 * @return
	 */
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
	 * 获取select编辑区内容
	 * 
	 * @param tabbedPane
	 * @return
	 */
	public static String getContent(JTabbedPane tabbedPane) {
		String text = "";
		RSyntaxTextArea rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
		if (rSyntaxTextArea != null) {
			text = rSyntaxTextArea.getText();
		} else {
			CloseableTabComponent closeableTabComponent = SwingUtils.getCloseableTabComponent(tabbedPane);
			HexTable hexTable = getHexTable(tabbedPane);

			HexTableModel hexTableModel = (HexTableModel) hexTable.getModel();
			ByteBuffer byteBuffer = hexTableModel.getDoc();
			try {
				text = new String(byteBuffer.getBuffer(), closeableTabComponent.getFileEncode());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return text;
	}

	/**
	 * get hextable
	 * 
	 * @param tabbedPane
	 * @return
	 */
	public static HexTable getHexTable(JTabbedPane tabbedPane) {
		HexTable hexTable = null;
		HexEditor hexEditor = SwingUtils.getHexEditor(tabbedPane);
		Component component = hexEditor.getViewport();

		return (component instanceof JViewport) ? (HexTable) (((JViewport) component).getView()) : hexTable;
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
		return result;
	}

	public static void updateUI() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Window windows[] = Window.getWindows();
				for (int i = 0; i < windows.length; i++) {
					if (windows[i].isDisplayable()) {
						SwingUtilities.updateComponentTreeUI(windows[i]);
					}
				}
			}
		});
	}

	public static void restart(String appName) throws IOException {
		// 用一条指定的命令去构造一个进程生成器
		ProcessBuilder pb = new ProcessBuilder("java", "-jar", appName+".jar");
		// 让这个进程的工作区空间改为F:\dist
		// 这样的话,它就会去F:\dist目录下找Test.jar这个文件
		//pb.directory(new File("F:\\dist"));
		// 得到进程生成器的环境 变量,这个变量我们可以改,
		// 改了以后也会反应到新起的进程里面去
		//Map<String, String> map = pb.environment();
		Process p = pb.start();
		// 然后就可以对p做自己想做的事情了
		// 自己这个时候就可以退出了
		System.exit(0);
	}

}
