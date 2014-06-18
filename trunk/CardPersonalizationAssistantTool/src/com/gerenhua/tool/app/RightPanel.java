package com.gerenhua.tool.app;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.gerenhua.tool.panel.AIDConfigPanel;
import com.gerenhua.tool.panel.CAPublicKeyConfigPanel;
import com.gerenhua.tool.panel.CardInfoDetectPanel;
import com.gerenhua.tool.panel.CardReaderPanel;
import com.gerenhua.tool.panel.ConfigPanel;
import com.gerenhua.tool.panel.FacePanel;
import com.gerenhua.tool.panel.IssuerKeyConfigPanel;
import com.gerenhua.tool.panel.LogoPanel;
import com.gerenhua.tool.panel.TerminalLimitConfigPanel;
import com.gerenhua.tool.panel.TerminalPerformanceConfigPanel;
import com.gerenhua.tool.panel.TerminalTypeConfigPanel;
import com.gerenhua.tool.panel.TestDataConfigPanel;
import com.gerenhua.tool.panel.TradePanel;

/**
 * @title RightPanel.java
 * @description
 * @author pei.li 2012-3-29
 * @version 1.0.0
 * @modify
 * @copyright watchdata
 */
public class RightPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2191162071900598973L;
	public static JTabbedPane tabbedPane = null;
	public static AIDConfigPanel aidConfigPanel = null;
	public static CAPublicKeyConfigPanel caPublicKeyConfigPanel = null;
	public static IssuerKeyConfigPanel issuerKeyConfigPanel = null;
	public static TerminalLimitConfigPanel terminalLimitConfigPanel = null;
	public static TerminalPerformanceConfigPanel terminalPerformanceConfigPanel = null;
	public static TerminalTypeConfigPanel terminalTypeConfigPanel = null;
	public static TestDataConfigPanel testDataConfigPanel = null;
	public static CardInfoDetectPanel cardInfoDetectPanel = null;
	public static TradePanel tradePanel = null;
	public static LogoPanel logoPanel = null;
	public static FacePanel facePanel = null;
	public static CardReaderPanel cardReaderPanel = new CardReaderPanel();
	public static ConfigPanel configPanel=null;
	//public static CardInfoScanPanel cardInfoScanPanel=null;
	

	public RightPanel() throws Exception {
		super();
		init();
	}

	private void init() throws Exception {
		aidConfigPanel = new AIDConfigPanel();
		caPublicKeyConfigPanel = new CAPublicKeyConfigPanel();
		issuerKeyConfigPanel = new IssuerKeyConfigPanel();
		terminalLimitConfigPanel = new TerminalLimitConfigPanel();
		terminalPerformanceConfigPanel = new TerminalPerformanceConfigPanel();
		terminalTypeConfigPanel = new TerminalTypeConfigPanel();
		testDataConfigPanel = new TestDataConfigPanel();
		cardInfoDetectPanel=new CardInfoDetectPanel();
		tradePanel = new TradePanel();
		facePanel=new FacePanel();
		configPanel=new ConfigPanel();
		//cardInfoScanPanel=new CardInfoScanPanel();
		setLayout(new BorderLayout());
		logoPanel = new LogoPanel();
		add(logoPanel, BorderLayout.CENTER);
	}

}
