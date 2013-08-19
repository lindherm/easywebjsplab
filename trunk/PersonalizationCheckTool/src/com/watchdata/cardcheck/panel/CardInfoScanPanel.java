package com.watchdata.cardcheck.panel;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

public class CardInfoScanPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public CardInfoScanPanel() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("helll");
		lblNewLabel.setBounds(181, 157, 54, 15);
		add(lblNewLabel);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(196, 228, 93, 23);
		add(btnNewButton);

	}

}
