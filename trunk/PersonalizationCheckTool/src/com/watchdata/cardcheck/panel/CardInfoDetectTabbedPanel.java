package com.watchdata.cardcheck.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

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
		setSize(84, 750);
		setName("卡片");
		setLayout(null);

		final JButton button = new JButton();
		button.setBounds(0, 66, 80, 21);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		add(button);
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
				RightPanel.cardInfoScanPanel.setVisible(false);
			}
		});
		button.setText("卡片工具");

		final JButton button_1 = new JButton();
		button_1.setBounds(0, 91, 80, 21);
		button_1.setFocusPainted(false);
		button_1.setBorderPainted(false);
		add(button_1);
		button_1.setText("信息扫描");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				Application.rightPanel.add(RightPanel.cardInfoScanPanel, BorderLayout.CENTER);
				RightPanel.cardInfoScanPanel.setVisible(true);
				RightPanel.cardInfoDetectPanel.setVisible(false);
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
				AIDConfigPanel.buttonDel.requestFocus();
			}
		});

	}

}
