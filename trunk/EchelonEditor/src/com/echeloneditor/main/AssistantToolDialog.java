package com.echeloneditor.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.echeloneditor.utils.Config;
import com.watchdata.commons.crypto.WD3DesCryptoUtil;
import com.watchdata.commons.jce.JceBase.Padding;
import com.watchdata.commons.lang.WDBase64;
import com.watchdata.commons.lang.WDStringUtil;
import com.watchdata.kms.kmsi.IKms;

public class AssistantToolDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField dataField;
	private JTextField keyField;
	private JLabel lblResult;
	private JTextField restultField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AssistantToolDialog dialog = new AssistantToolDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AssistantToolDialog() {
		setTitle("Assistant Tool");
		setBounds(100, 100, 600, 365);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Data");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 34, 59, 15);
		contentPanel.add(lblNewLabel);

		final JButton configip = new JButton("设置");
		configip.setVisible(false);
		configip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfigIpDialog dialog = new ConfigIpDialog((JDialog) SwingUtilities.getRoot((Component) e.getSource()));
				dialog.setVisible(true);
			}
		});
		configip.setBounds(418, 64, 101, 25);
		contentPanel.add(configip);

		final JCheckBox chckbxHsm = new JCheckBox("Hsm");
		chckbxHsm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxHsm.isSelected()) {
					keyField.setText(Config.getValue("HSM", "KEYINDEX"));
					configip.setVisible(true);
				} else {
					configip.setVisible(false);
				}
			}
		});
		chckbxHsm.setBounds(333, 65, 79, 23);
		contentPanel.add(chckbxHsm);

		dataField = new JTextField();
		dataField.setText("0000000000000000");
		dataField.setBounds(75, 34, 500, 22);
		contentPanel.add(dataField);
		dataField.setColumns(10);
		{
			JLabel lblKey = new JLabel("Key");
			lblKey.setHorizontalAlignment(SwingConstants.CENTER);
			lblKey.setBounds(10, 69, 59, 15);
			contentPanel.add(lblKey);
		}
		{
			keyField = new JTextField();
			keyField.setText("57415443484441544154696D65434F53");
			keyField.setColumns(10);
			keyField.setBounds(75, 69, 249, 22);
			contentPanel.add(keyField);
		}

		lblResult = new JLabel("Result");
		lblResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblResult.setBounds(10, 101, 59, 15);
		contentPanel.add(lblResult);

		restultField = new JTextField();
		restultField.setColumns(10);
		restultField.setBounds(75, 101, 500, 22);
		contentPanel.add(restultField);

		JButton btnBase = new JButton("Base64解码");
		btnBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restultField.setText(new String(WDBase64.decode(dataField.getText().getBytes())));
			}
		});
		btnBase.setBounds(322, 193, 125, 25);
		contentPanel.add(btnBase);

		JButton btnBase_1 = new JButton("Base64编码");
		btnBase_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restultField.setText(new String(WDBase64.encode(dataField.getText().getBytes())));
			}
		});
		btnBase_1.setBounds(322, 148, 125, 25);
		contentPanel.add(btnBase_1);

		JButton btnNewButton = new JButton("ECB DES");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (chckbxHsm.isSelected()) {
						IKms iKms = IKms.getInstance();

						restultField.setText(iKms.encrypt(keyField.getText(), IKms.DES_ECB, dataField.getText(), Config.getValue("HSM", "IP") + "_" + Config.getValue("HSM", "PORT")));
						Config.setValue("HSM", "KEYINDEX", keyField.getText());

					} else {
						restultField.setText(WD3DesCryptoUtil.ecb_encrypt(keyField.getText(), dataField.getText(), Padding.NoPadding));
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(78, 148, 112, 25);
		contentPanel.add(btnNewButton);

		JButton btnEcbDecrypt = new JButton("ECB Decrypt");
		btnEcbDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (chckbxHsm.isSelected()) {
						IKms iKms = IKms.getInstance();

						restultField.setText(iKms.decrypt(keyField.getText(), IKms.DES_ECB, dataField.getText(), Config.getValue("HSM", "IP") + "_" + Config.getValue("HSM", "PORT")));
						Config.setValue("HSM", "KEYINDEX", keyField.getText());

					} else {
						restultField.setText(WD3DesCryptoUtil.ecb_decrypt(keyField.getText(), dataField.getText(), Padding.NoPadding));
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		btnEcbDecrypt.setBounds(78, 193, 112, 25);
		contentPanel.add(btnEcbDecrypt);

		JButton btnCbcDes = new JButton("CBC DES");
		btnCbcDes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (chckbxHsm.isSelected()) {
						IKms iKms = IKms.getInstance();

						restultField.setText(iKms.encrypt(keyField.getText(), IKms.DES_CBC, dataField.getText(), "0000000000000000", Config.getValue("HSM", "IP") + "_" + Config.getValue("HSM", "PORT")));
						Config.setValue("HSM", "KEYINDEX", keyField.getText());

					} else {
						restultField.setText(WD3DesCryptoUtil.cbc_encrypt(keyField.getText(), dataField.getText(), Padding.NoPadding, "0000000000000000"));
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		btnCbcDes.setBounds(200, 148, 112, 25);
		contentPanel.add(btnCbcDes);

		JButton btnCbcDecrypt = new JButton("CBC Decrypt");
		btnCbcDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (chckbxHsm.isSelected()) {
						IKms iKms = IKms.getInstance();

						restultField.setText(iKms.decrypt(keyField.getText(), IKms.DES_CBC, dataField.getText(), "0000000000000000", Config.getValue("HSM", "IP") + "_" + Config.getValue("HSM", "PORT")));
						Config.setValue("HSM", "KEYINDEX", keyField.getText());

					} else {
						restultField.setText(WD3DesCryptoUtil.cbc_decrypt(keyField.getText(), dataField.getText(), Padding.NoPadding, "0000000000000000"));
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		btnCbcDecrypt.setBounds(200, 193, 112, 25);
		contentPanel.add(btnCbcDecrypt);

		JButton btnNewButton_1 = new JButton("TDES MAC");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(457, 148, 95, 25);
		contentPanel.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("CMAC");
		btnNewButton_2.setBounds(457, 193, 95, 25);
		contentPanel.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("ASC->String");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restultField.setText(WDStringUtil.hex2asc(dataField.getText()));
			}
		});
		btnNewButton_3.setBounds(79, 285, 125, 25);
		contentPanel.add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("String->ASC");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restultField.setText(WDStringUtil.asc2hex(dataField.getText()));
			}
		});
		btnNewButton_4.setBounds(79, 243, 125, 25);
		contentPanel.add(btnNewButton_4);

	}
}
