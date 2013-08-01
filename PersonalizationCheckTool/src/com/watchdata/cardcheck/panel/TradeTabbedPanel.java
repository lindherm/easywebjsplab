package com.watchdata.cardcheck.panel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.watchdata.cardcheck.app.Application;
import com.watchdata.cardcheck.app.RightPanel;
import com.watchdata.cardcheck.utils.PropertiesManager;

public class TradeTabbedPanel extends JPanel {

	private static final long serialVersionUID = -3638522566025326277L;
	private PropertiesManager pm = new PropertiesManager();

	/**
	 * Create the panel
	 */
	public TradeTabbedPanel() {
		super();
		setName("交易");
		setLayout(new GridBagLayout());

		final JPanel panel = new JPanel();
		//panel.setBorder(new TitledBorder(null, "交易", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowHeights = new int[] {0,7,7,7};
		panel.setLayout(gridBagLayout);
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 475, 0);
		gridBagConstraints.ipady = 16;
		gridBagConstraints.ipadx = 16;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 0;
		add(panel, gridBagConstraints);

		final JButton qpbocButton = new JButton();
		qpbocButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				Application.rightPanel.add(RightPanel.tradePanel, BorderLayout.CENTER);
				RightPanel.tradePanel.setVisible(true);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(false);	
				RightPanel.caPublicKeyConfigPanel.setVisible(false);
				RightPanel.issuerKeyConfigPanel.setVisible(false);
				RightPanel.terminalLimitConfigPanel.setVisible(false);
				RightPanel.terminalTypeConfigPanel.setVisible(false);
				RightPanel.terminalPerformanceConfigPanel.setVisible(false);
				RightPanel.staticDataCalibrationResultsPanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
				TradePanel.setTradeTypeStr(pm.getString("mv.tradepanel.qPBOC"));
				TradePanel.setTradeType(pm.getString("mv.tradepanel.qPBOC"));
			}
		});
		qpbocButton.setActionCommand("交易");
		qpbocButton.setText("qPBOC");
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints_1.ipadx = 60;
		gridBagConstraints_1.gridy = 0;
		gridBagConstraints_1.gridx = 0;
		panel.add(qpbocButton, gridBagConstraints_1);

		final JButton button = new JButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				Application.rightPanel.add(RightPanel.tradePanel, BorderLayout.CENTER);
				RightPanel.tradePanel.setVisible(true);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(false);	
				RightPanel.caPublicKeyConfigPanel.setVisible(false);
				RightPanel.issuerKeyConfigPanel.setVisible(false);
				RightPanel.terminalLimitConfigPanel.setVisible(false);
				RightPanel.terminalTypeConfigPanel.setVisible(false);
				RightPanel.terminalPerformanceConfigPanel.setVisible(false);
				RightPanel.staticDataCalibrationResultsPanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
				TradePanel.setTradeTypeStr(pm.getString("mv.tradepanel.lend"));
				TradePanel.setTradeType(pm.getString("mv.tradepanel.lend")); 
			}
		});
		button.setText("借贷记");
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(5, 0, 0, 0);
		gridBagConstraints_2.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_2.gridy = 1;
		gridBagConstraints_2.gridx = 0;
		panel.add(button, gridBagConstraints_2);

		final JButton button_1 = new JButton();
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				Application.rightPanel.add(RightPanel.tradePanel, BorderLayout.CENTER);
				RightPanel.tradePanel.setVisible(true);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(false);	
				RightPanel.caPublicKeyConfigPanel.setVisible(false);
				RightPanel.issuerKeyConfigPanel.setVisible(false);
				RightPanel.terminalLimitConfigPanel.setVisible(false);
				RightPanel.terminalTypeConfigPanel.setVisible(false);
				RightPanel.terminalPerformanceConfigPanel.setVisible(false);
				RightPanel.staticDataCalibrationResultsPanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
				TradePanel.setTradeTypeStr(pm.getString("mv.tradepanel.ecash"));
				TradePanel.setTradeType(pm.getString("mv.tradepanel.ecash"));
			}
		});
		button_1.setText("电子现金");
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.insets = new Insets(5, 0, 0, 0);
		gridBagConstraints_3.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_3.gridy = 2;
		gridBagConstraints_3.gridx = 0;
		panel.add(button_1, gridBagConstraints_3);

		final JButton button_2 = new JButton();
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				Application.rightPanel.add(RightPanel.tradePanel, BorderLayout.CENTER);
				RightPanel.tradePanel.setVisible(true);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(false);	
				RightPanel.caPublicKeyConfigPanel.setVisible(false);
				RightPanel.issuerKeyConfigPanel.setVisible(false);
				RightPanel.terminalLimitConfigPanel.setVisible(false);
				RightPanel.terminalTypeConfigPanel.setVisible(false);
				RightPanel.terminalPerformanceConfigPanel.setVisible(false);
				RightPanel.staticDataCalibrationResultsPanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
				TradePanel.setTradeTypeStr(pm.getString("mv.tradepanel.earmark"));
				TradePanel.setTradeType(pm.getString("mv.tradepanel.earmark"));
			}
		});
		button_2.setText("圈存");
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.insets = new Insets(5, 0, 0, 0);
		gridBagConstraints_4.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_4.gridy = 3;
		gridBagConstraints_4.gridx = 0;
		panel.add(button_2, gridBagConstraints_4);
		//
	}

}
