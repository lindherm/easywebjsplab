package com.gerenhua.cardcheck.app;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.gerenhua.cardcheck.panel.AIDConfigPanel;
import com.gerenhua.cardcheck.panel.CAPublicKeyConfigPanel;
import com.gerenhua.cardcheck.panel.CardInfoDetectPanel;
import com.gerenhua.cardcheck.panel.CardReaderPanel;
import com.gerenhua.cardcheck.panel.FaceConfiTabbedPanel;
import com.gerenhua.cardcheck.panel.IssuerKeyConfigPanel;
import com.gerenhua.cardcheck.panel.LogoPanel;
import com.gerenhua.cardcheck.panel.TerminalLimitConfigPanel;
import com.gerenhua.cardcheck.panel.TerminalPerformanceConfigPanel;
import com.gerenhua.cardcheck.panel.TerminalTypeConfigPanel;
import com.gerenhua.cardcheck.panel.TestDataConfigPanel;
import com.gerenhua.cardcheck.panel.TradePanel;

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
	public static FaceConfiTabbedPanel faceConfiTabbedPanel = null;
	public static TradePanel tradePanel = null;
	public static LogoPanel logoPanel = null;
	public static CardReaderPanel cardReaderPanel = new CardReaderPanel();
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
		faceConfiTabbedPanel=new FaceConfiTabbedPanel();
		tradePanel = new TradePanel();
		//cardInfoScanPanel=new CardInfoScanPanel();
		setLayout(new BorderLayout());
		logoPanel = new LogoPanel();
		add(logoPanel, BorderLayout.CENTER);
	}

}
