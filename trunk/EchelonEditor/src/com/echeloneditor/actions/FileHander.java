package com.echeloneditor.actions;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Map;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.echeloneditor.listeners.EditorPaneListener;
import com.echeloneditor.listeners.SimpleFileChooseListener;
import com.echeloneditor.main.CloseableTabComponent;
import com.echeloneditor.main.FontWidthRuler;
import com.echeloneditor.utils.SwingUtils;
import com.echeloneditor.vo.StatusObject;

public class FileHander {
	public JTabbedPane tabbedPane;
	public StatusObject statusObject;
	public FontWidthRuler ruler;

	public FileHander(JTabbedPane tabbedPane, StatusObject statusObject) {
		this.tabbedPane = tabbedPane;
		this.statusObject = statusObject;
	}

	public void openFileWithFilePath(String filePath) {
		// 打开文件
		FileAction fileAction = new FileAction();
		try {
			Map<String, String> map = fileAction.open(filePath);
			// 更新状态栏文件编码信息
			String encode = map.get("encode");
			String fileSize = map.get("fileSize");

			statusObject.getFileSize().setText("文件大小：" + fileSize);
			statusObject.getFileEncode().setText("文件编码：" + encode);

			File file = new File(filePath);

			String fileContentType = SimpleFileChooseListener.getFileContentType(file);

			//JEditorPane editorPane = new JEditorPane();
			//editorPane.addMouseListener(new EditorPaneListener(statusObject));
			//JPanel cp = new JPanel(new BorderLayout());

		      RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
		      textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
		      textArea.setCodeFoldingEnabled(true);
		      textArea.setAntiAliasingEnabled(true);
		      RTextScrollPane sp = new RTextScrollPane(textArea);
		      sp.setFoldIndicatorEnabled(true);
		      //cp.add(sp);

			//JScrollPane scrollPane = new JScrollPane();

			// 加入标尺
			ruler = new FontWidthRuler(FontWidthRuler.HORIZONTAL, 10, textArea);
			ruler.setPreferredWidth(20000);
			ruler.addSpin(3);
			ruler.NeedPaint = true;
			sp.setColumnHeaderView(ruler);
			// 当前行高亮
			//HighlighterAction.install(editorPane);

			//sp.setViewportView(sp);

			int tabCount = tabbedPane.getTabCount();
			CloseableTabComponent closeableTabComponent = new CloseableTabComponent(tabbedPane, statusObject);
			closeableTabComponent.setFileEncode(encode);
			closeableTabComponent.setFileSzie(fileSize);

			tabbedPane.add("New Panel", sp);
			tabbedPane.setTabComponentAt(tabCount, closeableTabComponent);

			tabbedPane.setSelectedComponent(sp);
			// 设置选项卡title为打开文件的文件名
			SimpleFileChooseListener.setTabbedPaneTitle(tabbedPane, file.getName());
			//editorPane.setContentType(fileContentType);
			//editorPane.setText(map.get("fileContent"));

			textArea.setText(map.get("fileContent"));

			statusObject.getFontItem().setEnabled(true);

			//editorPane.requestFocusInWindow();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (UnsupportedCharsetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void saveFile(String filePath) {
		// 打开文件
		FileAction fileAction = new FileAction();
		JEditorPane editorPane = SwingUtils.getEditorPane(tabbedPane);
		try {
			fileAction.save(filePath, editorPane.getText(), statusObject.getFileEncode().getText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void newFile() {
		JEditorPane editorPane = new JEditorPane();
		editorPane.addMouseListener(new EditorPaneListener(statusObject));
		JScrollPane scrollPane = new JScrollPane();
		// 加入标尺
		ruler = new FontWidthRuler(FontWidthRuler.HORIZONTAL, 10, editorPane);
		ruler.setPreferredWidth(20000);
		ruler.addSpin(3);
		ruler.NeedPaint = true;
		scrollPane.setColumnHeaderView(ruler);

		HighlighterAction.install(editorPane);

		scrollPane.setViewportView(editorPane);
		// 设置编辑组件属性
		editorPane.setContentType("text/plain");
		int tabCount = tabbedPane.getTabCount();
		tabbedPane.add("New Panel", scrollPane);
		tabbedPane.setTitleAt(tabCount, "New Panel");
		tabbedPane.setTabComponentAt(tabCount, new CloseableTabComponent(tabbedPane, statusObject));
		tabbedPane.setSelectedComponent(scrollPane);

		statusObject.getFontItem().setEnabled(true);

		editorPane.requestFocusInWindow();
	}
}
