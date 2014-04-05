package com.gerenhua.cardcheck.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.gerenhua.cardcheck.utils.Config;
import com.gerenhua.cardcheck.utils.PropertiesManager;

/**
 * TerminalTypeConfigPanel.java
 * 
 * @description: 终端类型配置界面
 * 
 * @author: pei.li 2012-3-23
 * 
 * @version:1.0.0
 * 
 * @modify：
 * 
 * @Copyright：watchdata
 */
public class TerminalTypeConfigPanel extends JPanel {

	private static final long serialVersionUID = 4197596742651268659L;
	private JTextField counCodeValTextField;
	private JLabel counCodeLabel;
	private JLabel tradeCodeLabel;
	private JTextField tradeCodeValTextField;
	private JLabel terTypeLabel;
	private JTextField terTypeValTextField;
	private JLabel tradeTypeLabel;
	private JTextField tradeTypeValTextField;
	private JButton saveButton;
	private PropertiesManager pm = new PropertiesManager();

	/**
	 * Create the panel
	 */
	public TerminalTypeConfigPanel() {
		super();
		setLayout(null);
		setBorder(null);
		init();

		// 终端国家代码配置
		counCodeLabel = new JLabel();
		counCodeLabel.setBounds(10, 10, 130, 20);
		counCodeLabel.setText("国家代码(9F1A):");
		add(counCodeLabel);

		counCodeValTextField = new JTextField();
		counCodeValTextField.setBounds(150, 10, 180, 20);
		counCodeValTextField.setText(Config.getValue("Terminal_Data", "9F1A"));
		add(counCodeValTextField);

		// 交易代码配置
		tradeCodeLabel = new JLabel();
		tradeCodeLabel.setText("交易货币代码(5F2A):");
		tradeCodeLabel.setBounds(10, 40, 130, 20);
		add(tradeCodeLabel);

		tradeCodeValTextField = new JTextField();
		tradeCodeValTextField.setBounds(150, 40, 180, 20);
		add(tradeCodeValTextField);
		tradeCodeValTextField.setText(Config.getValue("Terminal_Data", "5F2A"));

		// 终端类型配置
		terTypeLabel = new JLabel();
		terTypeLabel.setText("终端类型(9F35):");
		terTypeLabel.setBounds(10, 70, 130, 20);
		add(terTypeLabel);

		terTypeValTextField = new JTextField();
		terTypeValTextField.setBounds(150, 70, 180, 20);
		add(terTypeValTextField);

		terTypeValTextField.setText(Config.getValue("Terminal_Data", "9F35"));

		// 交易类型配置
		tradeTypeLabel = new JLabel();
		tradeTypeLabel.setText("交易类型(9C):");
		tradeTypeLabel.setBounds(10, 100, 130, 20);
		add(tradeTypeLabel);

		tradeTypeValTextField = new JTextField();
		tradeTypeValTextField.setBounds(150, 100, 180, 20);
		add(tradeTypeValTextField);

		tradeTypeValTextField.setText(Config.getValue("Terminal_Data", "9C"));

		// 保存配置
		saveButton = new JButton();
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				Config.setValue("Terminal_Data", "9F1A", counCodeValTextField.getText().trim());
				Config.setValue("Terminal_Data", "5F2A", tradeCodeValTextField.getText().trim());
				Config.setValue("Terminal_Data", "9F35", terTypeValTextField.getText().trim());
				Config.setValue("Terminal_Data", "9C", tradeTypeValTextField.getText().trim());
				
				JOptionPane.showMessageDialog(null, "保存成功！");
			}
		});
		saveButton.setText(pm.getString("mv.termtype.save"));
		saveButton.setBounds(150, 130, 84, 21);
		saveButton.setFocusPainted(false);
		saveButton.setBorderPainted(false);
		add(saveButton);

	}

	private void init() {
		setName(pm.getString("mv.termtype.name"));
	}

}
