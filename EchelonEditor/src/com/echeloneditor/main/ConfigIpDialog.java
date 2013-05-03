package com.echeloneditor.main;

import java.awt.EventQueue;

import javax.swing.JDialog;

public class ConfigIpDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigIpDialog dialog = new ConfigIpDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public ConfigIpDialog() {
		setBounds(100, 100, 450, 169);
		getContentPane().setLayout(null);

	}

}
