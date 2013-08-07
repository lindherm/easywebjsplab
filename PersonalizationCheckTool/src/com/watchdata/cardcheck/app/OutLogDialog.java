package com.watchdata.cardcheck.app;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class OutLogDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the dialog.
	 */
	public OutLogDialog() {
		setSize(1000, 600);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JTextPane jTextPane = new JTextPane(){
			private static final long serialVersionUID = 1L;

			@Override
	        public boolean getScrollableTracksViewportWidth() {
	            return false;
	        }
	    };
		JScrollPane sp = new JScrollPane(jTextPane);
		jTextPane.setFont(new Font("宋体", Font.PLAIN,14));
		panel.add(sp);
		this.setLocationRelativeTo(null);
	}

	

}
