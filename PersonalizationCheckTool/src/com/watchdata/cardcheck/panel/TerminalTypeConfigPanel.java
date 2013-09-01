package com.watchdata.cardcheck.panel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.watchdata.cardcheck.utils.Config;
import com.watchdata.cardcheck.utils.PropertiesManager;

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
		final JLabel label_3 = new JLabel();
		label_3.setBounds(0, 50, 97, 20);
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font(pm.getString("mv.applicaiton.font"), Font.BOLD, 12));
		label_3.setText(pm.getString("mv.termtype.typeconfig"));
		add(label_3);

		final JSeparator separator = new JSeparator();
		separator.setBounds(79, 60, 730, 20);
		add(separator);

		// 终端国家代码配置
		counCodeLabel = new JLabel();
		counCodeLabel.setBounds(29, 125, 130, 20);
		counCodeLabel.setText("国家代码(9F1A):");
		add(counCodeLabel);

		counCodeValTextField = new JTextField();
		counCodeValTextField.setBounds(169, 125, 180, 20);
		counCodeValTextField.setText(Config.getValue("Terminal_Data", "9F1A"));
		add(counCodeValTextField);

		// 交易代码配置
		tradeCodeLabel = new JLabel();
		tradeCodeLabel.setText("交易货币代码(5F2A):");
		tradeCodeLabel.setBounds(29, 165, 130, 20);
		add(tradeCodeLabel);

		tradeCodeValTextField = new JTextField();
		tradeCodeValTextField.setBounds(169, 165, 180, 20);
		add(tradeCodeValTextField);
		tradeCodeValTextField.setText(Config.getValue("Terminal_Data", "5F2A"));

		// 终端类型配置
		terTypeLabel = new JLabel();
		terTypeLabel.setText("终端类型(9F35):");
		terTypeLabel.setBounds(29, 205, 130, 20);
		add(terTypeLabel);

		terTypeValTextField = new JTextField();
		terTypeValTextField.setBounds(169, 205, 180, 20);
		add(terTypeValTextField);

		terTypeValTextField.setText(Config.getValue("Terminal_Data", "9F35"));

		// 交易类型配置
		tradeTypeLabel = new JLabel();
		tradeTypeLabel.setText("交易类型(9C):");
		tradeTypeLabel.setBounds(29, 250, 130, 20);
		add(tradeTypeLabel);

		tradeTypeValTextField = new JTextField();
		tradeTypeValTextField.setBounds(169, 250, 180, 20);
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
		saveButton.setBounds(169, 296, 84, 21);
		saveButton.setFocusPainted(false);
		saveButton.setBorderPainted(false);
		add(saveButton);

	}

	private void init() {
		setName(pm.getString("mv.termtype.name"));
	}

}
