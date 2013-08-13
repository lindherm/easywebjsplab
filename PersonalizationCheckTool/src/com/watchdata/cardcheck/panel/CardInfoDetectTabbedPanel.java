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

public class CardInfoDetectTabbedPanel extends JPanel {

	private static final long serialVersionUID = 547600566322611144L;

	/**
	 * Create the panel
	 */
	public CardInfoDetectTabbedPanel() {
		super();
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 7, 7, 7, 7, 7 };
		setLayout(gridBagLayout);
		setSize(235, 1089);
		setName("卡片");

		final JPanel panel = new JPanel();
		final GridBagLayout gridBagLayout_1 = new GridBagLayout();
		gridBagLayout_1.rowHeights = new int[] { 0, 7 };
		panel.setLayout(gridBagLayout_1);
		panel.setBorder(new TitledBorder(null, "卡片", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 500, 0);
		gridBagConstraints.ipady = 16;
		gridBagConstraints.ipadx = 16;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 0;
		add(panel, gridBagConstraints);

		final JButton button = new JButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				Application.rightPanel.add(RightPanel.cardInfoDetectPanel, BorderLayout.CENTER);
				RightPanel.cardInfoDetectPanel.setVisible(true);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(false);
				RightPanel.caPublicKeyConfigPanel.setVisible(false);
				RightPanel.issuerKeyConfigPanel.setVisible(false);
				RightPanel.terminalLimitConfigPanel.setVisible(false);
				RightPanel.terminalTypeConfigPanel.setVisible(false);
				RightPanel.terminalPerformanceConfigPanel.setVisible(false);
				RightPanel.tradePanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
			}
		});
		button.setText("卡片工具");
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.ipadx = 55;
		gridBagConstraints_6.gridx = 0;
		gridBagConstraints_6.gridy = 0;
		gridBagConstraints_6.insets = new Insets(0, 0, 0, 0);
		panel.add(button, gridBagConstraints_6);

		final JButton button_1 = new JButton();
		button_1.setText("信息扫描");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				/*Application.rightPanel.add(RightPanel.aidConfigPanel, BorderLayout.CENTER);
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
				AIDConfigPanel.buttonDel.requestFocus();*/
			}
		});
		final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
		gridBagConstraints_7.ipadx = 55;
		gridBagConstraints_7.gridx = 0;
		gridBagConstraints_7.gridy = 1;
		gridBagConstraints_7.insets = new Insets(5, 0, 0, 0);
		panel.add(button_1, gridBagConstraints_7);

	}

}
