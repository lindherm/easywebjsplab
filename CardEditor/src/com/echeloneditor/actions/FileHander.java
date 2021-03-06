package com.echeloneditor.actions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

import org.fife.rsta.ac.LanguageSupport;
import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.rsta.ac.java.JavaLanguageSupport;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.echeloneditor.listeners.EditorPaneListener;
import com.echeloneditor.main.CloseableTabComponent;
import com.echeloneditor.main.FontWidthRuler;
import com.echeloneditor.utils.Config;
import com.echeloneditor.utils.FontUtil;
import com.echeloneditor.utils.ImageHelper;
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

			String fileContentType = SwingUtils.getFileContentType(file.getName());

			RSyntaxTextArea textArea = SwingUtils.createTextArea();

			
			LanguageSupportFactory lsf = LanguageSupportFactory.get();
			LanguageSupport support = lsf.getSupportFor(SyntaxConstants.SYNTAX_STYLE_JAVA);
			JavaLanguageSupport jls = (JavaLanguageSupport)support;
			// TODO: This API will change!  It will be easier to do per-editor
			// changes to the build path.
			try {
				jls.getJarManager().addCurrentJreClassFileSource();
				//jsls.getJarManager().addClassFileSource(ji);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			jls.setShowDescWindow(true);
			jls.setParameterAssistanceEnabled(true);
			jls.setAutoActivationEnabled(true);
			
			lsf.register(textArea);

			textArea.setSyntaxEditingStyle(fileContentType);
			
			EditorPaneListener editlistener=new EditorPaneListener(tabbedPane, statusObject);
			textArea.addMouseListener(editlistener);
			textArea.addMouseMotionListener(editlistener);
			textArea.addKeyListener(editlistener);
			textArea.getDocument().addDocumentListener(editlistener);
			
			RTextScrollPane sp = new RTextScrollPane(textArea);
			sp.setFoldIndicatorEnabled(true);

			Gutter gutter = sp.getGutter();
			gutter.setBookmarkingEnabled(true);
			ImageIcon ii = ImageHelper.loadImage("bookmark.png");
			gutter.setBookmarkIcon(ii);

			InputStream in = getClass().getResourceAsStream("/com/echeloneditor/resources/templates/eclipse.xml");
			try {
				Theme theme = Theme.load(in);
				theme.apply(textArea);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			// 加入标尺
			ruler = new FontWidthRuler(FontWidthRuler.HORIZONTAL, 10, textArea);
			ruler.addSpin(3);
			ruler.NeedPaint = true;
			sp.setColumnHeaderView(ruler);

			int tabCount = tabbedPane.getTabCount();
			CloseableTabComponent closeableTabComponent = new CloseableTabComponent(tabbedPane, statusObject);
			closeableTabComponent.setFilePath(file.getPath());
			closeableTabComponent.setFileEncode(encode);
			closeableTabComponent.setFileSzie(fileSize);

			tabbedPane.add("New Panel", sp);
			tabbedPane.setTabComponentAt(tabCount, closeableTabComponent);

			tabbedPane.setSelectedComponent(sp);
			// 设置选项卡title为打开文件的文件名
			SwingUtils.setTabbedPaneTitle(tabbedPane, file.getName());
			textArea.setText(map.get("fileContent"));

			String res = Config.getValue("CURRENT_THEME","current_font");

			textArea.setFont(FontUtil.getFont(res));
			statusObject.getSaveBtn().setEnabled(false);

			textArea.setCaretPosition(0);
			textArea.requestFocusInWindow();
			closeableTabComponent.setModify(false);
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

	/*public void openHexFile() {
		HexEditor hexEditor = new HexEditor();
		hexEditor.addHexEditorListener(new SimpleHexEditorListener(tabbedPane, statusObject));
		hexEditor.setCellEditable(true);

		int tabCount = tabbedPane.getTabCount();
		CloseableTabComponent closeableTabComponent = SwingUtils.getCloseableTabComponent(tabbedPane);

		CloseableTabComponent closeableTabComponent1 = new CloseableTabComponent(tabbedPane, statusObject);
		closeableTabComponent1.setFileEncode(closeableTabComponent.getFileEncode());
		closeableTabComponent1.setFilePath(closeableTabComponent.getFilePath());
		closeableTabComponent1.setFileSzie(closeableTabComponent.getFileSzie());
		closeableTabComponent1.setModify(closeableTabComponent.isModify());

		try {
			hexEditor.open(closeableTabComponent.getFilePath());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tabbedPane.add("New Panel", hexEditor);
		tabbedPane.setTabComponentAt(tabCount, closeableTabComponent1);
		tabbedPane.setSelectedComponent(hexEditor);

		SwingUtils.setTabbedPaneTitle(tabbedPane, new File(closeableTabComponent1.getFilePath()).getName());

		statusObject.getSaveBtn().setEnabled(false);
	}*/

	public void saveFile(String filePath, String fileEncode) {
		// 打开文件
		FileAction fileAction = new FileAction();
		try {
			fileAction.save(filePath, SwingUtils.getContent(tabbedPane), fileEncode);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void newFile() {
		RSyntaxTextArea textArea = SwingUtils.createTextArea();
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);

		EditorPaneListener editlistener=new EditorPaneListener(tabbedPane, statusObject);
		textArea.addMouseListener(editlistener);
		textArea.addMouseMotionListener(editlistener);
		textArea.addKeyListener(editlistener);
		textArea.getDocument().addDocumentListener(editlistener);
		
		RTextScrollPane sp = new RTextScrollPane(textArea);
		sp.setFoldIndicatorEnabled(true);

		Gutter gutter = sp.getGutter();
		gutter.setBookmarkingEnabled(true);
		ImageIcon ii = ImageHelper.loadImage("bookmark.png");
		gutter.setBookmarkIcon(ii);

		InputStream in = getClass().getResourceAsStream("/com/echeloneditor/resources/templates/eclipse.xml");
		try {
			Theme theme = Theme.load(in);
			theme.apply(textArea);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		// 加入标尺
		ruler = new FontWidthRuler(FontWidthRuler.HORIZONTAL, 10, textArea);
		ruler.addSpin(3);
		ruler.NeedPaint = true;
		sp.setColumnHeaderView(ruler);

		int tabCount = tabbedPane.getTabCount();
		CloseableTabComponent closeableTabComponent = new CloseableTabComponent(tabbedPane, statusObject);
		closeableTabComponent.setFileEncode("UTF-8");
		closeableTabComponent.setFileSzie("0");
		closeableTabComponent.setModify(false);
		tabbedPane.add("New Panel", sp);
		tabbedPane.setTabComponentAt(tabCount, closeableTabComponent);

		tabbedPane.setSelectedComponent(sp);
		// 设置选项卡title为打开文件的文件名
		SwingUtils.setTabbedPaneTitle(tabbedPane, "New Panel");

		String res = Config.getValue("CURRENT_THEME","current_font");

		textArea.setFont(FontUtil.getFont(res));

		statusObject.getSaveBtn().setEnabled(false);

		textArea.setCaretPosition(0);
		textArea.requestFocusInWindow();
	}
}
