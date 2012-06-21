package com.echeloneditor.listeners;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;

import org.apache.log4j.Logger;

import com.echeloneditor.actions.FileHander;
import com.echeloneditor.main.CloseableTabComponent;
import com.echeloneditor.utils.SwingUtils;
import com.echeloneditor.vo.StatusObject;

public class SimpleFileChooseListener implements ActionListener {
	// 日志
	private final static Logger log = Logger.getLogger(SimpleFileChooseListener.class);
	// 选项卡
	public JTabbedPane tabbedPane;
	public StatusObject statusObject;
	public FileHander fileHander;

	public SimpleFileChooseListener(JTabbedPane tabbedPane, StatusObject statusObject) {
		this.tabbedPane = tabbedPane;
		this.statusObject = statusObject;
		fileHander = new FileHander(this.tabbedPane, this.statusObject);
	}

	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		int ret = fileChooser.showOpenDialog(null);

		if (ret == JFileChooser.APPROVE_OPTION) {
			// 获得选择的文件
			File file = fileChooser.getSelectedFile();
			if (file.isFile()) {
				fileHander.openFileWithFilePath(file.getPath());
			}
		}
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
		return SwingUtils.getEditorPane(tabbedPane).getText();
	}

	/**
	 * 设置编辑区内容
	 * 
	 * @param tabbedPane
	 * @param text
	 */
	public static void setContent(JTabbedPane tabbedPane, String text) {
		SwingUtils.getEditorPane(tabbedPane).setText(text);
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

		String fileExt = fileName.substring(pos, fileName.length());
		// 根据文件扩展名判断文件类型
		if (fileExt.equalsIgnoreCase(".txt")) {
			result = "text/plain";
		} else if (fileExt.equalsIgnoreCase(".java")) {
			result = "text/java";
		} else if (fileExt.equalsIgnoreCase(".xml")) {
			result = "text/xml";
		} else if (fileExt.equalsIgnoreCase(".properties")) {
			result = "text/properties";
		} else if (fileExt.equalsIgnoreCase(".sql")) {
			result = "text/sql";
		} else if (fileExt.equalsIgnoreCase(".js")) {
			result = "text/javascript";
		} else if (fileExt.equalsIgnoreCase(".c")) {
			result = "text/c";
		} else if (fileExt.equalsIgnoreCase(".cpp")) {
			result = "text/cpp";
		} else {
			result = "text/plain";
		}
		return result;
	}
}
