package com.watchdata.cardcheck.app;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.watchdata.cardcheck.panel.AtmPanel;
import com.watchdata.cardcheck.panel.CardInfoDetectTabbedPanel;
import com.watchdata.cardcheck.panel.CheckTabbedPanel;
import com.watchdata.cardcheck.panel.ConfigTabbedPanel;
import com.watchdata.cardcheck.panel.TradeTabbedPanel;
import com.watchdata.cardcheck.utils.Config;
import com.watchdata.cardcheck.utils.PropertiesManager;
import java.awt.FlowLayout;

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
	private JTabbedPane tabbedPane;
	private ConfigTabbedPanel configTabbedPanel = new ConfigTabbedPanel();
	private TradeTabbedPanel tradeTabbedPanel = new TradeTabbedPanel();
	private CheckTabbedPanel checkTabbedPanel = new CheckTabbedPanel();
	private CardInfoDetectTabbedPanel cardDetectTabbedPanel = new CardInfoDetectTabbedPanel();
	private PropertiesManager pm = new PropertiesManager();

	public LeftPanel() {
		super(new BorderLayout());
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(new TitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		add(panel);

		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.add(configTabbedPanel.getName(), configTabbedPanel);
		panel.add(tabbedPane);
		FlowLayout flowLayout = (FlowLayout) tradeTabbedPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		tabbedPane.add(tradeTabbedPanel.getName(), tradeTabbedPanel);
		tabbedPane.add(checkTabbedPanel.getName(), checkTabbedPanel);
		tabbedPane.add(cardDetectTabbedPanel.getName(), cardDetectTabbedPanel);
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if ("配置".equals(tabbedPane.getSelectedComponent().getName())) {
					Application.rightPanel.add(RightPanel.logoPanel, BorderLayout.CENTER);
					RightPanel.logoPanel.setVisible(true);
					RightPanel.aidConfigPanel.setVisible(false);
					RightPanel.caPublicKeyConfigPanel.setVisible(false);
					RightPanel.issuerKeyConfigPanel.setVisible(false);
					RightPanel.terminalLimitConfigPanel.setVisible(false);
					RightPanel.terminalTypeConfigPanel.setVisible(false);
					RightPanel.terminalPerformanceConfigPanel.setVisible(false);
					RightPanel.tradePanel.setVisible(false);
					RightPanel.testDataConfigPanel.setVisible(false);
					RightPanel.cardInfoDetectPanel.setVisible(false);
					RightPanel.cardInfoScanPanel.setVisible(false);
				} else if ("交易".equals(tabbedPane.getSelectedComponent().getName())) {
					Application.rightPanel.add(RightPanel.tradePanel, BorderLayout.CENTER);
					RightPanel.tradePanel.setVisible(true);
					RightPanel.aidConfigPanel.setVisible(false);
					RightPanel.logoPanel.setVisible(false);
					RightPanel.caPublicKeyConfigPanel.setVisible(false);
					RightPanel.issuerKeyConfigPanel.setVisible(false);
					RightPanel.terminalLimitConfigPanel.setVisible(false);
					RightPanel.terminalTypeConfigPanel.setVisible(false);
					RightPanel.terminalPerformanceConfigPanel.setVisible(false);
					RightPanel.testDataConfigPanel.setVisible(false);
					RightPanel.cardReaderPanel.setVisible(false);
					RightPanel.cardInfoDetectPanel.setVisible(false);
					RightPanel.cardInfoScanPanel.setVisible(false);
					AtmPanel.tradeType = pm.getString("mv.tradepanel.lend");
					AtmPanel.setTradeType(AtmPanel.tradeType);
					//更新交易状态
					Config.setValue("Terminal_Data", "currentTradeType", pm.getString("mv.tradepanel.lend"));
				} else if ("检测".equals(tabbedPane.getSelectedComponent().getName())) {
					Application.rightPanel.add(RightPanel.testDataConfigPanel, BorderLayout.CENTER);
					RightPanel.testDataConfigPanel.setVisible(true);
					RightPanel.terminalTypeConfigPanel.setVisible(false);
					RightPanel.terminalPerformanceConfigPanel.setVisible(false);
					RightPanel.terminalLimitConfigPanel.setVisible(false);
					RightPanel.issuerKeyConfigPanel.setVisible(false);
					RightPanel.caPublicKeyConfigPanel.setVisible(false);
					RightPanel.aidConfigPanel.setVisible(false);
					RightPanel.logoPanel.setVisible(false);
					RightPanel.tradePanel.setVisible(false);
					RightPanel.cardReaderPanel.setVisible(false);
					RightPanel.cardInfoDetectPanel.setVisible(false);
					RightPanel.cardInfoScanPanel.setVisible(false);
				} else if ("卡片".equals(tabbedPane.getSelectedComponent().getName())) {
					Application.rightPanel.add(RightPanel.cardInfoDetectPanel, BorderLayout.CENTER);
					RightPanel.cardInfoDetectPanel.setVisible(true);
					RightPanel.testDataConfigPanel.setVisible(false);
					RightPanel.terminalTypeConfigPanel.setVisible(false);
					RightPanel.terminalPerformanceConfigPanel.setVisible(false);
					RightPanel.terminalLimitConfigPanel.setVisible(false);
					RightPanel.issuerKeyConfigPanel.setVisible(false);
					RightPanel.caPublicKeyConfigPanel.setVisible(false);
					RightPanel.aidConfigPanel.setVisible(false);
					RightPanel.logoPanel.setVisible(false);
					RightPanel.tradePanel.setVisible(false);
					RightPanel.cardReaderPanel.setVisible(false);
					RightPanel.cardInfoScanPanel.setVisible(false);
				}
			}
		});

	}

}