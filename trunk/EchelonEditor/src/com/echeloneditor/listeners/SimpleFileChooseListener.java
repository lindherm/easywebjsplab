package com.echeloneditor.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;

import com.echeloneditor.actions.FileHander;
import com.echeloneditor.vo.StatusObject;

public class SimpleFileChooseListener implements ActionListener {
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
		if (e.getActionCommand().endsWith("open")) {
			int ret = fileChooser.showOpenDialog(null);

			if (ret == JFileChooser.APPROVE_OPTION) {
				// 获得选择的文件
				File file = fileChooser.getSelectedFile();
				if (file.isFile()) {
					fileHander.openFileWithFilePath(file.getPath());
				}
			}
		} else if (e.getActionCommand().endsWith("save")) {
			int ret = fileChooser.showSaveDialog(null);

			if (ret == JFileChooser.APPROVE_OPTION) {
				// 获得选择的文件
				File file = fileChooser.getSelectedFile();
				fileHander.saveFile(file.getPath());
			}
		}

	}

}
