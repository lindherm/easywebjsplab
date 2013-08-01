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
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowHeights = new int[] {0,7,7,7,7,7,7};
		setLayout(gridBagLayout);
		setSize(235, 801);
		setName("配置");

		final JPanel panel = new JPanel();
		final GridBagLayout gridBagLayout_1 = new GridBagLayout();
		gridBagLayout_1.rowHeights = new int[] {0,7};
		panel.setLayout(gridBagLayout_1);
		panel.setBorder(new TitledBorder(null, "AID配置", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints.ipady = 16;
		gridBagConstraints.ipadx = 16;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 0;
		add(panel, gridBagConstraints);
		
		final JButton button = new JButton();
		button.addActionListener(new ActionListener() {
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
				RightPanel.staticDataCalibrationResultsPanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
				AIDConfigPanel.textFieldAid.requestFocus();
			}
		});
		button.setText("增加AID");
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.ipadx = 55;
		gridBagConstraints_6.gridx = 0;
		gridBagConstraints_6.gridy = 0;
		gridBagConstraints_6.insets = new Insets(0, 0, 0, 0);
		panel.add(button, gridBagConstraints_6);

		final JButton button_1 = new JButton();
		button_1.setText("编辑AID");
		button_1.addActionListener(new ActionListener() {
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
				RightPanel.staticDataCalibrationResultsPanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
				AIDConfigPanel.buttonDel.requestFocus();
			}
		});
		final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
		gridBagConstraints_7.ipadx = 55;
		gridBagConstraints_7.gridx = 0;
		gridBagConstraints_7.gridy = 1;
		gridBagConstraints_7.insets = new Insets(5, 0, 0, 0);
		panel.add(button_1, gridBagConstraints_7);

		final JPanel panel_1 = new JPanel();
		final GridBagLayout gridBagLayout_2 = new GridBagLayout();
		gridBagLayout_2.rowHeights = new int[] {0,7};
		panel_1.setLayout(gridBagLayout_2);
		panel_1.setBorder(new TitledBorder(null, "CA公钥配置", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_1.ipady = 16;
		gridBagConstraints_1.ipadx = 16;
		gridBagConstraints_1.gridy = 1;
		gridBagConstraints_1.gridx = 0;
		add(panel_1, gridBagConstraints_1);

		final JButton button_2 = new JButton();
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				Application.rightPanel.add(RightPanel.caPublicKeyConfigPanel,BorderLayout.CENTER);
				RightPanel.caPublicKeyConfigPanel.setVisible(true);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(false);	
				RightPanel.issuerKeyConfigPanel.setVisible(false);
				RightPanel.terminalLimitConfigPanel.setVisible(false);
				RightPanel.terminalTypeConfigPanel.setVisible(false);
				RightPanel.terminalPerformanceConfigPanel.setVisible(false);
				RightPanel.tradePanel.setVisible(false);
				RightPanel.staticDataCalibrationResultsPanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
				CAPublicKeyConfigPanel.RIDCombox.requestFocus();
			}
		});
		button_2.setText("CA密钥管理");
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.ipadx = 35;
		gridBagConstraints_8.gridx = 0;
		gridBagConstraints_8.gridy = 0;
		gridBagConstraints_8.insets = new Insets(0, 0, 0, 0);
		panel_1.add(button_2, gridBagConstraints_8);

		final JButton button_3 = new JButton();
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				Application.rightPanel.add(RightPanel.caPublicKeyConfigPanel,BorderLayout.CENTER);
				RightPanel.caPublicKeyConfigPanel.setVisible(true);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(false);	
				RightPanel.issuerKeyConfigPanel.setVisible(false);
				RightPanel.terminalLimitConfigPanel.setVisible(false);
				RightPanel.terminalTypeConfigPanel.setVisible(false);
				RightPanel.terminalPerformanceConfigPanel.setVisible(false);
				RightPanel.tradePanel.setVisible(false);
				RightPanel.staticDataCalibrationResultsPanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
			}
		});
		button_3.setText("CA管理");
		final GridBagConstraints gridBagConstraints_9 = new GridBagConstraints();
		gridBagConstraints_9.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_9.gridx = 0;
		gridBagConstraints_9.gridy = 1;
		gridBagConstraints_9.insets = new Insets(5, 0, 0, 0);
		panel_1.add(button_3, gridBagConstraints_9);

		final JPanel panel_2 = new JPanel();
		panel_2.setLayout(new GridBagLayout());
		panel_2.setBorder(new TitledBorder(null, "发卡行密钥配置", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_2.ipady = 16;
		gridBagConstraints_2.ipadx = 16;
		gridBagConstraints_2.gridy = 2;
		gridBagConstraints_2.gridx = 0;
		add(panel_2, gridBagConstraints_2);
		
		final JButton button_4 = new JButton();
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				Application.rightPanel.add(RightPanel.issuerKeyConfigPanel,BorderLayout.CENTER);
				RightPanel.issuerKeyConfigPanel.setVisible(true);
				RightPanel.caPublicKeyConfigPanel.setVisible(false);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(false);	
				RightPanel.terminalLimitConfigPanel.setVisible(false);
				RightPanel.terminalTypeConfigPanel.setVisible(false);
				RightPanel.terminalPerformanceConfigPanel.setVisible(false);
				RightPanel.tradePanel.setVisible(false);
				RightPanel.staticDataCalibrationResultsPanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
			}
		});
		button_4.setText("发卡行密钥");
		final GridBagConstraints gridBagConstraints_10 = new GridBagConstraints();
		gridBagConstraints_10.ipadx = 35;
		gridBagConstraints_10.gridx = 0;
		gridBagConstraints_10.gridy = 0;
		gridBagConstraints_10.insets = new Insets(0, 0, 0, 0);
		panel_2.add(button_4, gridBagConstraints_10);

		final JPanel panel_3 = new JPanel();
		final GridBagLayout gridBagLayout_3 = new GridBagLayout();
		gridBagLayout_3.rowHeights = new int[] {0,7,7};
		panel_3.setLayout(gridBagLayout_3);
		panel_3.setBorder(new TitledBorder(null, "终端限制配置", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_3.ipady = 16;
		gridBagConstraints_3.ipadx = 16;
		gridBagConstraints_3.gridy = 3;
		gridBagConstraints_3.gridx = 0;
		add(panel_3, gridBagConstraints_3);
		
		final JButton button_5 = new JButton();
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				Application.rightPanel.add(RightPanel.terminalLimitConfigPanel,BorderLayout.CENTER);
				RightPanel.terminalLimitConfigPanel.setVisible(true);
				RightPanel.issuerKeyConfigPanel.setVisible(false);
				RightPanel.caPublicKeyConfigPanel.setVisible(false);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(false);	
				RightPanel.terminalTypeConfigPanel.setVisible(false);
				RightPanel.terminalPerformanceConfigPanel.setVisible(false);
				RightPanel.tradePanel.setVisible(false);
				RightPanel.staticDataCalibrationResultsPanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
				TerminalLimitConfigPanel.tacRejectField.requestFocus();
			}
		});
		button_5.setText("终端行为代码");
		final GridBagConstraints gridBagConstraints_11 = new GridBagConstraints();
		gridBagConstraints_11.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_11.ipadx = 20;
		gridBagConstraints_11.gridx = 0;
		gridBagConstraints_11.gridy = 0;
		gridBagConstraints_11.insets = new Insets(0, 0, 0, 0);
		panel_3.add(button_5, gridBagConstraints_11);

		final JButton button_6 = new JButton();
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				Application.rightPanel.add(RightPanel.terminalLimitConfigPanel,BorderLayout.CENTER);
				RightPanel.terminalLimitConfigPanel.setVisible(true);
				RightPanel.issuerKeyConfigPanel.setVisible(false);
				RightPanel.caPublicKeyConfigPanel.setVisible(false);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(false);	
				RightPanel.terminalTypeConfigPanel.setVisible(false);
				RightPanel.terminalPerformanceConfigPanel.setVisible(false);
				RightPanel.tradePanel.setVisible(false);
				RightPanel.staticDataCalibrationResultsPanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
				TerminalLimitConfigPanel.lowLimitField.requestFocus();
			}
		});
		button_6.setText("终端限制");
		final GridBagConstraints gridBagConstraints_12 = new GridBagConstraints();
		gridBagConstraints_12.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_12.gridx = 0;
		gridBagConstraints_12.gridy = 1;
		gridBagConstraints_12.insets = new Insets(5, 0, 0, 0);
		panel_3.add(button_6, gridBagConstraints_12);

		final JButton button_7 = new JButton();
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				Application.rightPanel.add(RightPanel.terminalLimitConfigPanel,BorderLayout.CENTER);
				RightPanel.terminalLimitConfigPanel.setVisible(true);
				RightPanel.issuerKeyConfigPanel.setVisible(false);
				RightPanel.caPublicKeyConfigPanel.setVisible(false);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(false);	
				RightPanel.terminalTypeConfigPanel.setVisible(false);
				RightPanel.terminalPerformanceConfigPanel.setVisible(false);
				RightPanel.tradePanel.setVisible(false);
				RightPanel.staticDataCalibrationResultsPanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
				TerminalLimitConfigPanel.termIdField.requestFocus();
			}
		});
		button_7.setText("终端ID");
		final GridBagConstraints gridBagConstraints_13 = new GridBagConstraints();
		gridBagConstraints_13.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_13.gridx = 0;
		gridBagConstraints_13.gridy = 2;
		gridBagConstraints_13.insets = new Insets(5, 0, 0, 0);
		panel_3.add(button_7, gridBagConstraints_13);

		final JPanel panel_4 = new JPanel();
		panel_4.setLayout(new GridBagLayout());
		panel_4.setBorder(new TitledBorder(null, "终端性能配置", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_4.ipady = 16;
		gridBagConstraints_4.ipadx = 16;
		gridBagConstraints_4.gridy = 4;
		gridBagConstraints_4.gridx = 0;
		add(panel_4, gridBagConstraints_4);
		
		final JButton button_8 = new JButton();
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				Application.rightPanel.add(RightPanel.terminalPerformanceConfigPanel,BorderLayout.CENTER);
				RightPanel.terminalPerformanceConfigPanel.setVisible(true);
				RightPanel.terminalLimitConfigPanel.setVisible(false);
				RightPanel.issuerKeyConfigPanel.setVisible(false);
				RightPanel.caPublicKeyConfigPanel.setVisible(false);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(false);	
				RightPanel.terminalTypeConfigPanel.setVisible(false);
				RightPanel.tradePanel.setVisible(false);
				RightPanel.staticDataCalibrationResultsPanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
				TerminalPerformanceConfigPanel.checkBox.requestFocus();
			}
		});
		button_8.setText("终端性能");
		final GridBagConstraints gridBagConstraints_14 = new GridBagConstraints();
		gridBagConstraints_14.ipadx = 45;
		gridBagConstraints_14.gridx = 0;
		gridBagConstraints_14.gridy = 0;
		gridBagConstraints_14.insets = new Insets(0, 0, 0, 0);
		panel_4.add(button_8, gridBagConstraints_14);

		final JPanel panel_5 = new JPanel();
		panel_5.setLayout(new GridBagLayout());
		panel_5.setBorder(new TitledBorder(null, "终端类型配置", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_5.ipady = 16;
		gridBagConstraints_5.ipadx = 16;
		gridBagConstraints_5.gridy = 5;
		gridBagConstraints_5.gridx = 0;
		add(panel_5, gridBagConstraints_5);
		
		final JButton button_10 = new JButton();
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				Application.rightPanel.add(RightPanel.terminalTypeConfigPanel,BorderLayout.CENTER);
				RightPanel.terminalTypeConfigPanel.setVisible(true);
				RightPanel.terminalPerformanceConfigPanel.setVisible(false);
				RightPanel.terminalLimitConfigPanel.setVisible(false);
				RightPanel.issuerKeyConfigPanel.setVisible(false);
				RightPanel.caPublicKeyConfigPanel.setVisible(false);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(false);	
				RightPanel.tradePanel.setVisible(false);
				RightPanel.staticDataCalibrationResultsPanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
				TerminalTypeConfigPanel.counCodeComboBox.requestFocus();
			}
		});
		button_10.setText("终端类型管理");
		final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
		gridBagConstraints_15.ipadx = 20;
		gridBagConstraints_15.gridx = 0;
		gridBagConstraints_15.gridy = 0;
		gridBagConstraints_15.insets = new Insets(0, 0, 0, 0);
		panel_5.add(button_10, gridBagConstraints_15);

		final JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(null, "读卡器配置", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		panel_6.setLayout(new GridBagLayout());
		final GridBagConstraints gridBagConstraints_16 = new GridBagConstraints();
		gridBagConstraints_16.insets = new Insets(10, 0, 38, 0);
		gridBagConstraints_16.ipady = 16;
		gridBagConstraints_16.ipadx = 16;
		gridBagConstraints_16.gridy = 6;
		gridBagConstraints_16.gridx = 0;
		add(panel_6, gridBagConstraints_16);

		final JButton button_9 = new JButton();
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				Application.rightPanel.add(RightPanel.cardReaderPanel,BorderLayout.CENTER);
				RightPanel.cardReaderPanel.setVisible(true);
				RightPanel.terminalTypeConfigPanel.setVisible(false);
				RightPanel.terminalPerformanceConfigPanel.setVisible(false);
				RightPanel.terminalLimitConfigPanel.setVisible(false);
				RightPanel.issuerKeyConfigPanel.setVisible(false);
				RightPanel.caPublicKeyConfigPanel.setVisible(false);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(false);	
				RightPanel.tradePanel.setVisible(false);
				RightPanel.staticDataCalibrationResultsPanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				CardReaderPanel.comboBox.requestFocus();
			}
		});
		button_9.setText("读卡器配置");
		final GridBagConstraints gridBagConstraints_17 = new GridBagConstraints();
		gridBagConstraints_17.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints_17.ipadx = 30;
		gridBagConstraints_17.gridy = 0;
		gridBagConstraints_17.gridx = 0;
		panel_6.add(button_9, gridBagConstraints_17);

	}

}
