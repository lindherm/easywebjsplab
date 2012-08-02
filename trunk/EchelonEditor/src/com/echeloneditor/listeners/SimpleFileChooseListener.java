package com.echeloneditor.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import com.echeloneditor.actions.FileHander;
import com.echeloneditor.main.CloseableTabComponent;
import com.echeloneditor.utils.SwingUtils;
import com.echeloneditor.vo.StatusObject;

public class SimpleFileChooseListener implements ActionListener {
	// 选项卡
	public JTabbedPane tabbedPane;
	public StatusObject statusObject;
	public FileHander fileHander;

	public SimpleFileChooseListener(JTabbedPane tabbedPane, StatusObject statusObject) {
		this.tabbedPane = tabbedPane;
		this.statusObject = statusObject;
	}

	public void actionPerformed(ActionEvent e) {
		fileHander = new FileHander(tabbedPane, statusObject);
		CloseableTabComponent closeableTabComponent = SwingUtils.getCloseableTabComponent(tabbedPane);
		String filePath = closeableTabComponent.getFilePath();
		String fileEncode = closeableTabComponent.getFileEncode();

		String command = e.getActionCommand();

		JFileChooser fileChooser = new JFileChooser();
		if (command.equals("open")) {
			int ret = fileChooser.showOpenDialog(null);

			if (ret == JFileChooser.APPROVE_OPTION) {
				// 获得选择的文件
				File file = fileChooser.getSelectedFile();
				if (file.isFile()) {
					fileHander.openFileWithFilePath(file.getPath());
				}
			}
		} else if (command.equals("save")) {
			int tabCount = tabbedPane.getTabCount();
			if (tabCount > 0) {

				if (filePath == null || filePath.equals("")) {
					int ret = fileChooser.showSaveDialog(null);

					if (ret == JFileChooser.APPROVE_OPTION) {
						// 获得选择的文件
						File file = fileChooser.getSelectedFile();
						if (file.exists()) {
							Object[] options = { "<html>是&nbsp;(<u>Y</u>)</html>", "<html>否&nbsp;(<u>N</u>)</html>" };
							ret = JOptionPane.showOptionDialog(null, "文件已经存在，是否覆盖？", "信息框", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
							if (ret != JOptionPane.YES_OPTION) {
								return;
							}
						}
						fileHander.saveFile(file.getPath(), fileEncode);
						SwingUtils.setTabbedPaneTitle(tabbedPane, file.getName());
						closeableTabComponent.setFilePath(file.getPath());
						closeableTabComponent.setFileEncode("utf-8");
						closeableTabComponent.setFileSzie(String.valueOf(file.length()));
						closeableTabComponent.setModify(false);
					}
				} else {
					File file = new File(filePath);
					if (file.canWrite()) {
						fileHander.saveFile(filePath, fileEncode);
						closeableTabComponent.setFileSzie(String.valueOf(new File(filePath).length()));
						closeableTabComponent.setModify(false);
					} else {
						JOptionPane.showMessageDialog(null, "文件为只读，保存失败！");
						return;
					}

				}
				statusObject.getFileSize().setText("文件大小：" + closeableTabComponent.getFileSzie());
				statusObject.getFileEncode().setText("文件编码：" + closeableTabComponent.getFileEncode());
			}

		} else if (command.equalsIgnoreCase("saveas")) {
			// CloseableTabComponent closeableTabComponent = SwingUtils.getCloseableTabComponent(tabbedPane);
			int ret = fileChooser.showSaveDialog(null);

			if (ret == JFileChooser.APPROVE_OPTION) {
				// 获得选择的文件
				File file = fileChooser.getSelectedFile();
				if (file.exists()) {
					Object[] options = { "<html>是&nbsp;(<u>Y</u>)</html>", "<html>否&nbsp;(<u>N</u>)</html>" };
					ret = JOptionPane.showOptionDialog(null, "文件已经存在，是否覆盖？", "信息框", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					if (ret != JOptionPane.YES_OPTION) {
						return;
					}
				}
				fileHander.saveFile(file.getPath(), fileEncode);
				// SwingUtils.setTabbedPaneTitle(tabbedPane, file.getName());
				// closeableTabComponent.setFilePath(file.getPath());
				// closeableTabComponent.setFileEncode("utf-8");
				// closeableTabComponent.setFileSzie(String.valueOf(file.length()));
				// closeableTabComponent.setModify(false);
			}
		}
		statusObject.getSaveBtn().setEnabled(false);
	}

}
