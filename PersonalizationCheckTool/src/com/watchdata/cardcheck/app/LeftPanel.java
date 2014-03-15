package com.watchdata.cardcheck.app;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.watchdata.cardcheck.panel.CardInfoDetectTabbedPanel;
import com.watchdata.cardcheck.panel.CheckTabbedPanel;
import com.watchdata.cardcheck.panel.ConfigTabbedPanel;
import com.watchdata.cardcheck.panel.TradeTabbedPanel;
import com.watchdata.cardcheck.utils.PropertiesManager;
import com.watchdata.cardcheck.utils.menu.FolderPane;
import com.watchdata.cardcheck.utils.menu.ListPane;

/**
 * @title LeftPanel.java
 * @description
 * @author pei.li 2012-3-16
 * @version 1.0.0
 * @modify
 * @copyright watchdata
 */
public class LeftPanel extends JPanel {

	private static final long serialVersionUID = 3433561501023042680L;
	//private JTabbedPane tabbedPane;
	private ConfigTabbedPanel configTabbedPanel = new ConfigTabbedPanel();
	private TradeTabbedPanel tradeTabbedPanel = new TradeTabbedPanel();
	private CheckTabbedPanel checkTabbedPanel = new CheckTabbedPanel();
	private CardInfoDetectTabbedPanel cardDetectTabbedPanel = new CardInfoDetectTabbedPanel();
	// private FaceConfiTabbedPanel faceConfiTabbedPanel = new FaceConfiTabbedPanel();
	private PropertiesManager pm = new PropertiesManager();

	public LeftPanel() {
		super(new BorderLayout());
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		//final JPanel panel = new JPanel();
		//panel.setLayout(new BorderLayout());
		//panel.setBorder(new TitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		//add(panel);

		FolderPane fp = getFolderPane();
		// 添加到JScrollPane
		JScrollPane jsp = new JScrollPane(fp);
		// 为了好看，Viewport背景设置成FolderPane的背景。这儿可以自己控制
		jsp.getViewport().setBackground(fp.getBackground());
		add(jsp, BorderLayout.CENTER);

	}

	private FolderPane getFolderPane() {
		FolderPane fp = new FolderPane();
		fp.setAnimated(true);
		fp.addFolder("设置", getConfigFileFolderPane());
		fp.addFolder("交易", getTradeFileFolderPane());
		fp.addFolder("检测", getCheckFileFolderPane());
		fp.addFolder("卡片", getCardFileFolderPane());
		return fp;
	}
/*
	private ListPane getDetailsPane() {
		ListPane p = new ListPane();
		p.addItem("<html><b>java_source</b><br>文件夹</html>", null);
		p.addItem("<html>修改日期: 2001年11月8日,<br>22:39</html>", null);
		p.setSize(140, 74);
		return p;
	}*/

	private ListPane getConfigFileFolderPane() {
		ListPane p = new ListPane();
		p.addItem("AID设置", "/com/watchdata/cardcheck/resources/images/menu/drive.png");
		p.addItem("CA管理", "/com/watchdata/cardcheck/resources/images/menu/mydoc.png");
		p.addItem("应用密钥", "/com/watchdata/cardcheck/resources/images/menu/shareddoc.png");
		p.addItem("终端限制", "/com/watchdata/cardcheck/resources/images/menu/mycom.png");
		p.addItem("终端性能", "/com/watchdata/cardcheck/resources/images/menu/neighbor.png");
		p.addItem("终端类型", "/com/watchdata/cardcheck/resources/images/menu/neighbor.png");
		p.addItem("皮肤设置", "/com/watchdata/cardcheck/resources/images/menu/neighbor.png");
		p.addItem("读卡器", "/com/watchdata/cardcheck/resources/images/menu/neighbor.png");
		p.setSize(140,240);
		return p;
	}
	private ListPane getTradeFileFolderPane() {
		ListPane p = new ListPane();
		p.addItem("交易", "/com/watchdata/cardcheck/resources/images/menu/drive.png");
		p.setSize(140,30);
		return p;
	}
	private ListPane getCheckFileFolderPane() {
		ListPane p = new ListPane();
		p.addItem("检测", "/com/watchdata/cardcheck/resources/images/menu/drive.png");
		p.setSize(140,30);
		return p;
	}
	private ListPane getCardFileFolderPane() {
		ListPane p = new ListPane();
		p.addItem("卡片", "/com/watchdata/cardcheck/resources/images/menu/drive.png");
		p.setSize(140,30);
		return p;
	}
}