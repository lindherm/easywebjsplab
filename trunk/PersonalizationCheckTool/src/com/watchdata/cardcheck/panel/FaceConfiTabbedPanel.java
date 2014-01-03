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

public class FaceConfiTabbedPanel extends JPanel {

	private static final long serialVersionUID = 547600566322611144L;

	/**
	 * Create the panel
	 */
	public FaceConfiTabbedPanel() {
		super();
		setSize(99, 750);
		setName("皮肤");
		setLayout(null);

		final JButton btnAid = new JButton();
		btnAid.setHorizontalAlignment(SwingConstants.LEFT);
		btnAid.setBounds(0, 5, 80, 21);
		btnAid.setFocusPainted(false);
		btnAid.setBorderPainted(false);
		add(btnAid);
		btnAid.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				Application.rightPanel.add(RightPanel.aidConfigPanel, BorderLayout.CENTER);
				RightPanel.aidConfigPanel.setVisible(false);
				RightPanel.logoPanel.setVisible(true);
				RightPanel.caPublicKeyConfigPanel.setVisible(false);
				RightPanel.issuerKeyConfigPanel.setVisible(false);
				RightPanel.terminalLimitConfigPanel.setVisible(false);
				RightPanel.terminalTypeConfigPanel.setVisible(false);
				RightPanel.terminalPerformanceConfigPanel.setVisible(false);
				RightPanel.tradePanel.setVisible(false);
				RightPanel.testDataConfigPanel.setVisible(false);
				RightPanel.cardReaderPanel.setVisible(false);
				RightPanel.cardInfoDetectPanel.setVisible(false);
				
				new FaceDialog(Application.frame);
			}
		});
		btnAid.setText("皮肤设置");

	}

}
