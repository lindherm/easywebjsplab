package com.echeloneditor;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

public class PublicTransportationQueryTool {

	private JFrame frmPublictransportationquerytool;
	private JTextField textField;
	public JWebBrowser webBrowser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		NativeInterface.open();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PublicTransportationQueryTool window = new PublicTransportationQueryTool();
					window.frmPublictransportationquerytool.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// Here goes the rest of the program initialization
		NativeInterface.runEventPump();
	}

	/**
	 * Create the application.
	 */
	public PublicTransportationQueryTool() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPublictransportationquerytool = new JFrame();
		frmPublictransportationquerytool.setTitle("PublicTransportationQueryTool");
		frmPublictransportationquerytool.setBounds(0, 0, 1024, 768);
		frmPublictransportationquerytool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPublictransportationquerytool.setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		frmPublictransportationquerytool.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel = new JLabel("\u5361\u53F7");
		panel.add(lblNewLabel);

		textField = new JTextField();
		textField.setText("10007510637088309");
		panel.add(textField);
		textField.setColumns(30);

		webBrowser = new JWebBrowser();
		webBrowser.setButtonBarVisible(false);
		webBrowser.setMenuBarVisible(false);
		webBrowser.setLocationBarVisible(false);

		frmPublictransportationquerytool.getContentPane().add(webBrowser, BorderLayout.CENTER);
		JButton btnNewButton = new JButton("\u67E5\u8BE2");
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				webBrowser.navigate("http://www.bjsuperpass.com/pagecontrol.do?object=ecard&action=query&card_id=" + textField.getText());
			}
		});
		panel.add(btnNewButton);
	}

}
