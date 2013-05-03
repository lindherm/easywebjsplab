package com.echeloneditor.main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AssistantToolDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblResult;
	private JTextField textField_2;

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
		setBounds(100, 100, 600, 325);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Data");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 34, 59, 15);
		contentPanel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(75, 34, 500, 22);
		contentPanel.add(textField);
		textField.setColumns(10);
		{
			JLabel lblKey = new JLabel("Key");
			lblKey.setHorizontalAlignment(SwingConstants.CENTER);
			lblKey.setBounds(10, 90, 59, 15);
			contentPanel.add(lblKey);
		}
		{
			textField_1 = new JTextField();
			textField_1.setColumns(10);
			textField_1.setBounds(75, 90, 300, 22);
			contentPanel.add(textField_1);
		}
		
		lblResult = new JLabel("Result");
		lblResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblResult.setBounds(10, 150, 59, 15);
		contentPanel.add(lblResult);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(75, 150, 500, 22);
		contentPanel.add(textField_2);
		
		JButton btnBase = new JButton("Base64解码");
		btnBase.setBounds(59, 252, 125, 25);
		contentPanel.add(btnBase);
		
		JButton btnBase_1 = new JButton("Base64编码");
		btnBase_1.setBounds(59, 207, 125, 25);
		contentPanel.add(btnBase_1);
		
		JButton btnNewButton = new JButton("ECB DES");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(194, 207, 112, 25);
		contentPanel.add(btnNewButton);
		
		JButton btnEcbDecrypt = new JButton("ECB Decrypt");
		btnEcbDecrypt.setBounds(194, 252, 112, 25);
		contentPanel.add(btnEcbDecrypt);
		
		JButton btnCbcDes = new JButton("CBC DES");
		btnCbcDes.setBounds(316, 207, 112, 25);
		contentPanel.add(btnCbcDes);
		
		JButton btnCbcDecrypt = new JButton("CBC Decrypt");
		btnCbcDecrypt.setBounds(316, 252, 112, 25);
		contentPanel.add(btnCbcDecrypt);
		
		JButton btnNewButton_1 = new JButton("TDES MAC");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(438, 207, 95, 25);
		contentPanel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("CMAC");
		btnNewButton_2.setBounds(438, 252, 95, 25);
		contentPanel.add(btnNewButton_2);
	}
}
