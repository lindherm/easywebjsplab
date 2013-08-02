package com.watchdata.cardcheck.app;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class OutLogDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the dialog.
	 */
	public OutLogDialog() {
		setSize(800, 600);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		this.setLocationRelativeTo(null);
	}

}
