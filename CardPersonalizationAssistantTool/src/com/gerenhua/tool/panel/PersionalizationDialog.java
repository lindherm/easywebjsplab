package com.gerenhua.tool.panel;

import java.awt.BorderLayout;

import javax.swing.JDialog;

public class PersionalizationDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ConfigPanel configPanel=new ConfigPanel();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PersionalizationDialog dialog = new PersionalizationDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public PersionalizationDialog() {
		setSize(1024, 768);
		getContentPane().setLayout(new BorderLayout());
		add(configPanel,BorderLayout.CENTER	);
	}

}
