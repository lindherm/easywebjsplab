package com.watchdata.cardcheck.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.watchdata.cardcheck.app.Application;
import com.watchdata.cardcheck.app.RightPanel;

/**
 * 
 * @author pei.li
 * 
 */

public class ConfigTabbedPanel extends JPanel {

	private static final long serialVersionUID = 547600566322611144L;

	/**
	 * Create the panel
	 */
	public ConfigTabbedPanel() {
		super();
		setSize(99, 750);
		setName("配置");
		setLayout(null);

		final JButton btnAid = new JButton();
		btnAid.setHorizontalAlignment(SwingConstants.LEFT);
		btnAid.setBounds(5, 5, 80, 21);
		btnAid.setFocusPainted(false);
		btnAid.setBorderPainted(false);
		add(btnAid);
		btnAid.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				Application.rightPanel.add(RightPanel.aidConfigPanel, BorderLayout.CENTER);
				RightPanel.aidConfigPanel.setVisible(true);
				RightPanel.logoPanel.setVisible(false);
				RightPanel.caPublicKeyConfigPanel.setVisible(false);
				RightPanel.issuerKeyConfigPanel.setVisible(false);
				RightPanel.terminalLimitConfigPanel.setVisible(false);
				RightPanel.terminalTypeConfigPanel.setVisible(false);
				RightPanel.terminalPerformanceConfigPanel.setVisible(false);
				RightPanel.tradePanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
				RightPanel.cardInfoDetectPanel.setVisible(false);
				RightPanel.cardInfoScanPanel.setVisible(false);
				AIDConfigPanel.textFieldAid.requestFocus();
			}
		});
		btnAid.setText("AID配置");

		final JButton button_3 = new JButton();
		button_3.setHorizontalAlignment(SwingConstants.LEFT);
		button_3.setBounds(5, 30, 80, 21);
		button_3.setFocusPainted(false);
		button_3.setBorderPainted(false);
		add(button_3);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				Application.rightPanel.add(RightPanel.caPublicKeyConfigPanel, BorderLayout.CENTER);
				RightPanel.caPublicKeyConfigPanel.setVisible(true);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(false);
				RightPanel.issuerKeyConfigPanel.setVisible(false);
				RightPanel.terminalLimitConfigPanel.setVisible(false);
				RightPanel.terminalTypeConfigPanel.setVisible(false);
				RightPanel.terminalPerformanceConfigPanel.setVisible(false);
				RightPanel.tradePanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardInfoDetectPanel.setVisible(false);
				RightPanel.cardInfoScanPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
			}
		});
		button_3.setText("CA管理");

		final JButton button_4 = new JButton();
		button_4.setHorizontalAlignment(SwingConstants.LEFT);
		button_4.setBounds(5, 55, 80, 21);
		button_4.setFocusPainted(false);
		button_4.setBorderPainted(false);
		add(button_4);
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				Application.rightPanel.add(RightPanel.issuerKeyConfigPanel, BorderLayout.CENTER);
				RightPanel.issuerKeyConfigPanel.setVisible(true);
				RightPanel.caPublicKeyConfigPanel.setVisible(false);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(false);
				RightPanel.terminalLimitConfigPanel.setVisible(false);
				RightPanel.terminalTypeConfigPanel.setVisible(false);
				RightPanel.terminalPerformanceConfigPanel.setVisible(false);
				RightPanel.tradePanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardInfoDetectPanel.setVisible(false);
				RightPanel.cardInfoScanPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
			}
		});
		button_4.setText("应用密钥");

		final JButton button_6 = new JButton();
		button_6.setHorizontalAlignment(SwingConstants.LEFT);
		button_6.setBounds(5, 80, 80, 21);
		button_6.setFocusPainted(false);
		button_6.setBorderPainted(false);
		add(button_6);
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				Application.rightPanel.add(RightPanel.terminalLimitConfigPanel, BorderLayout.CENTER);
				RightPanel.terminalLimitConfigPanel.setVisible(true);
				RightPanel.issuerKeyConfigPanel.setVisible(false);
				RightPanel.caPublicKeyConfigPanel.setVisible(false);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(false);
				RightPanel.terminalTypeConfigPanel.setVisible(false);
				RightPanel.terminalPerformanceConfigPanel.setVisible(false);
				RightPanel.tradePanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
				RightPanel.cardInfoDetectPanel.setVisible(false);
				RightPanel.cardInfoScanPanel.setVisible(false);
				TerminalLimitConfigPanel.lowLimitField.requestFocus();
			}
		});
		button_6.setText("终端限制");

		final JButton button_8 = new JButton();
		button_8.setHorizontalAlignment(SwingConstants.LEFT);
		button_8.setBounds(5, 105, 80, 21);
		button_8.setFocusPainted(false);
		button_8.setBorderPainted(false);
		add(button_8);
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				Application.rightPanel.add(RightPanel.terminalPerformanceConfigPanel, BorderLayout.CENTER);
				RightPanel.terminalPerformanceConfigPanel.setVisible(true);
				RightPanel.terminalLimitConfigPanel.setVisible(false);
				RightPanel.issuerKeyConfigPanel.setVisible(false);
				RightPanel.caPublicKeyConfigPanel.setVisible(false);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(false);
				RightPanel.terminalTypeConfigPanel.setVisible(false);
				RightPanel.tradePanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
				RightPanel.cardInfoDetectPanel.setVisible(false);
				RightPanel.cardInfoScanPanel.setVisible(false);
				TerminalPerformanceConfigPanel.checkBox.requestFocus();
			}
		});
		button_8.setText("终端性能");

		final JButton button_10 = new JButton();
		button_10.setHorizontalAlignment(SwingConstants.LEFT);
		button_10.setBounds(5, 130, 80, 21);
		button_10.setFocusPainted(false);
		button_10.setBorderPainted(false);
		add(button_10);
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				Application.rightPanel.add(RightPanel.terminalTypeConfigPanel, BorderLayout.CENTER);
				RightPanel.terminalTypeConfigPanel.setVisible(true);
				RightPanel.terminalPerformanceConfigPanel.setVisible(false);
				RightPanel.terminalLimitConfigPanel.setVisible(false);
				RightPanel.issuerKeyConfigPanel.setVisible(false);
				RightPanel.caPublicKeyConfigPanel.setVisible(false);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(false);
				RightPanel.tradePanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardInfoDetectPanel.setVisible(false);
				RightPanel.cardInfoScanPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
			}
		});
		button_10.setText("终端类型");

		final JButton button_9 = new JButton();
		button_9.setHorizontalAlignment(SwingConstants.LEFT);
		button_9.setBounds(5, 155, 80, 21);
		button_9.setFocusPainted(false);
		button_9.setBorderPainted(false);
		add(button_9);
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				Application.rightPanel.add(RightPanel.cardReaderPanel, BorderLayout.CENTER);
				RightPanel.cardReaderPanel.setVisible(true);
				RightPanel.terminalTypeConfigPanel.setVisible(false);
				RightPanel.terminalPerformanceConfigPanel.setVisible(false);
				RightPanel.terminalLimitConfigPanel.setVisible(false);
				RightPanel.issuerKeyConfigPanel.setVisible(false);
				RightPanel.caPublicKeyConfigPanel.setVisible(false);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(false);
				RightPanel.tradePanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardInfoDetectPanel.setVisible(false);
				RightPanel.cardInfoScanPanel.setVisible(false);
				CardReaderPanel.comboBox.requestFocus();
			}
		});
		button_9.setText("读卡器");

	}

}
