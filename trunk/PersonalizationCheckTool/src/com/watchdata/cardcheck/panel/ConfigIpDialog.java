package com.watchdata.cardcheck.panel;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.watchdata.cardcheck.utils.Config;
import com.watchdata.kms.kmsi.IKms;
import com.watchdata.kms.kmsi.IKmsException;

public class ConfigIpDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private final JButton btnNewButton;
	private final JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigIpDialog dialog = new ConfigIpDialog(null);
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
	public ConfigIpDialog(JDialog dialog) {
		super(dialog, true);
		setTitle("HSM");
		setBounds(100, 100, 450, 139);
		getContentPane().setLayout(null);

		final JIpAddressField ipAddressField = new JIpAddressField();
		ipAddressField.setBounds(84, 10, 160, 22);
		getContentPane().add(ipAddressField);

		textField = new JTextField();
		textField.setBounds(290, 10, 66, 22);
		getContentPane().add(textField);
		textField.setColumns(10);

		btnNewButton = new JButton("连接");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IKms iKms = IKms.getInstance();
				int ret = -1;
				try {
					ret = iKms.connect(ipAddressField.getText(), Integer.parseInt(textField.getText()), Integer.parseInt(Config.getValue("HSM", "timeout")), "pct");
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (IKmsException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					e1.printStackTrace();
				}
				if (ret == 0) {
					Config.setValue("HSM", "ip", ipAddressField.getText());
					Config.setValue("HSM", "port", textField.getText());
					btnNewButton.setEnabled(false);
					btnNewButton_1.setEnabled(true);
				}
			}
		});
		btnNewButton.setBounds(92, 57, 95, 25);
		getContentPane().add(btnNewButton);

		JLabel lblNewLabel = new JLabel("IP");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(28, 10, 54, 22);
		getContentPane().add(lblNewLabel);

		ipAddressField.setText(Config.getValue("HSM", "ip"));
		textField.setText(Config.getValue("HSM", "port"));

		JLabel lblNewLabel_1 = new JLabel("PORT");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(226, 10, 54, 22);
		getContentPane().add(lblNewLabel_1);

		btnNewButton_1 = new JButton("断开");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IKms iKms = IKms.getInstance();
				try {
					iKms.disconn(ipAddressField.getText() + "_" + textField.getText());
				} catch (IKmsException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					e1.printStackTrace();
				}
				btnNewButton.setEnabled(true);
				btnNewButton_1.setEnabled(false);
			}
		});
		btnNewButton_1.setBounds(202, 57, 95, 25);
		getContentPane().add(btnNewButton_1);

	}
}
