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
 * @author pei.li
 * @version 创建时间：2012-4-22 上午11:49:05 
 * 类说明  检测选项卡界面
 */

public class CheckTabbedPanel extends JPanel {

	private static final long serialVersionUID = -4394813101039119708L;

	/**
	 * Create the panel
	 */
	public CheckTabbedPanel() {
		super();
		final GridBagLayout gridBagLayout_1 = new GridBagLayout();
		gridBagLayout_1.rowHeights = new int[] {0,7};
		setLayout(gridBagLayout_1);
		setName("检测");

		final JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "检测数据配置", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowHeights = new int[] {0,7};
		panel.setLayout(gridBagLayout);
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.ipady = 16;
		gridBagConstraints.ipadx = 16;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 0;
		add(panel, gridBagConstraints);

		final JButton button = new JButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				Application.rightPanel.add(RightPanel.testDataConfigPanel,BorderLayout.CENTER);
				RightPanel.testDataConfigPanel.setVisible(true);
				RightPanel.terminalTypeConfigPanel.setVisible(false);
				RightPanel.terminalPerformanceConfigPanel.setVisible(false);
				RightPanel.terminalLimitConfigPanel.setVisible(false);
				RightPanel.issuerKeyConfigPanel.setVisible(false);
				RightPanel.caPublicKeyConfigPanel.setVisible(false);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(false);	
				RightPanel.tradePanel.setVisible(false);
				RightPanel.staticDataCalibrationResultsPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
				TestDataConfigPanel.appTypeComboBox.requestFocus();
			}
		});
		button.setText("增加数据");
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.ipadx = 50;
		gridBagConstraints_1.gridy = 0;
		gridBagConstraints_1.gridx = 0;
		panel.add(button, gridBagConstraints_1);

		final JButton button_1 = new JButton();
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				Application.rightPanel.add(RightPanel.testDataConfigPanel,BorderLayout.CENTER);
				RightPanel.testDataConfigPanel.setVisible(true);
				RightPanel.terminalTypeConfigPanel.setVisible(false);
				RightPanel.terminalPerformanceConfigPanel.setVisible(false);
				RightPanel.terminalLimitConfigPanel.setVisible(false);
				RightPanel.issuerKeyConfigPanel.setVisible(false);
				RightPanel.caPublicKeyConfigPanel.setVisible(false);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(false);	
				RightPanel.tradePanel.setVisible(false);
				RightPanel.staticDataCalibrationResultsPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
				TestDataConfigPanel.delButton.requestFocus();
			}
		});
		button_1.setText("编辑数据");
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(5, 0, 0, 0);
		gridBagConstraints_2.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_2.gridy = 1;
		gridBagConstraints_2.gridx = 0;
		panel.add(button_1, gridBagConstraints_2);

		final JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "检测", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		panel_1.setLayout(new GridBagLayout());
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.insets = new Insets(10, 0, 450, 0);
		gridBagConstraints_3.ipady = 16;
		gridBagConstraints_3.ipadx = 16;
		gridBagConstraints_3.gridy = 1;
		gridBagConstraints_3.gridx = 0;
		add(panel_1, gridBagConstraints_3);

		final JButton button_2 = new JButton();
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				Application.rightPanel.add(RightPanel.staticDataCalibrationResultsPanel,BorderLayout.CENTER);
				RightPanel.staticDataCalibrationResultsPanel.setVisible(true);
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
				StaticDataCalibrationResultsPanel.checkButton.requestFocus();
			}
		});
		button_2.setText("检测");
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_4.ipadx = 75;
		gridBagConstraints_4.gridy = 0;
		gridBagConstraints_4.gridx = 0;
		panel_1.add(button_2, gridBagConstraints_4);
		//
	}

}
