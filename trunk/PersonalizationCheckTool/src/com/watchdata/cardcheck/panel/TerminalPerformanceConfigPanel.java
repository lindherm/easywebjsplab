package com.watchdata.cardcheck.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.watchdata.cardcheck.utils.Config;
import com.watchdata.cardcheck.utils.PropertiesManager;
import com.watchdata.commons.lang.WDAssert;

/**
 * 
 * @description:终端性能配置界面
 * 
 * @author: Desheng.Xu March 29, 2012
 * 
 * @version: 1.0.0
 * 
 * @modify:
 * 
 * @Copyright: watchdata
 */
public class TerminalPerformanceConfigPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Color colorYellow = new Color(255, 255, 192);
	private PropertiesManager pm = new PropertiesManager();
	/**
	 * Create the panel
	 */
	public static JCheckBox checkBox;
	private JCheckBox chckbxsda;
	private JCheckBox checkBox_2;
	private JCheckBox chckbxdda;
	private JCheckBox checkBox_4;
	private JCheckBox checkBox_5;
	private JCheckBox chckbxcda;
	private JCheckBox chckbxIcpin;
	private JCheckBox chckbxpin;
	private JCheckBox checkBox_9;
	private JCheckBox checkBox_10;
	private JCheckBox checkBox_11;

	public TerminalPerformanceConfigPanel() {
		setLayout(null);
		setLayout(null);
		//setBorder(JTBorderFactory.createTitleBorder(pm.getString("mv.terminalperformanceconfig.name")));

		final JLabel termPermLabel = new JLabel();
		termPermLabel.setBounds(0, 50, 97, 20);
		termPermLabel.setHorizontalAlignment(SwingConstants.CENTER);
		termPermLabel.setFont(new Font(pm.getString("mv.applicaiton.font"), Font.BOLD, 12));
		termPermLabel.setText(pm.getString("mv.terminalperformanceconfig.performance"));
		add(termPermLabel);

		final JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(79, 60, 730, 20);
		add(separator_1);

		final JPanel byte1Panel = new JPanel();
		byte1Panel.setLayout(null);
		byte1Panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "BYTE1-\u5361\u7247\u6570\u636E\u8F93\u5165\u6027\u80FD", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		byte1Panel.setBounds(30, 80, 300, 171);
		add(byte1Panel);

		final JLabel label_2 = new JLabel();
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setText(pm.getString("mv.terminalperformanceconfig.bit8"));
		label_2.setBounds(0, 21, 80, 30);
		byte1Panel.add(label_2);

		checkBox = new JCheckBox();
		checkBox.setText(pm.getString("mv.terminalperformanceconfig.keybord"));
		checkBox.setBounds(90, 21, 120, 30);
		byte1Panel.add(checkBox);

		final JLabel label_4 = new JLabel();
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setText(pm.getString("mv.terminalperformanceconfig.bit7"));
		label_4.setBounds(0, 57, 80, 30);
		byte1Panel.add(label_4);

		checkBox_2 = new JCheckBox();
		checkBox_2.setText(pm.getString("mv.terminalperformanceconfig.magnetic"));
		checkBox_2.setBounds(90, 57, 120, 30);
		byte1Panel.add(checkBox_2);

		final JLabel label_6 = new JLabel();
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setText(pm.getString("mv.terminalperformanceconfig.bit6"));
		label_6.setBounds(0, 93, 80, 30);
		byte1Panel.add(label_6);

		checkBox_4 = new JCheckBox();
		checkBox_4.setText(pm.getString("mv.terminalperformanceconfig.contactic"));
		checkBox_4.setBounds(90, 93, 120, 30);
		byte1Panel.add(checkBox_4);

		final JLabel lblOtherBits = new JLabel();
		lblOtherBits.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOtherBits.setText("Other：");
		lblOtherBits.setBounds(0, 133, 80, 30);
		byte1Panel.add(lblOtherBits);

		final JLabel rufLabel = new JLabel();
		rufLabel.setText(pm.getString("mv.terminalperformanceconfig.ruf"));
		rufLabel.setBounds(90, 133, 120, 30);
		byte1Panel.add(rufLabel);

		final JPanel byte3Panel = new JPanel();
		byte3Panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "BYTE3-\u5B89\u5168\u6027\u80FD", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		byte3Panel.setBounds(30, 412, 380, 195);
		byte3Panel.setLayout(null);
		add(byte3Panel);

		final JLabel label_3 = new JLabel();
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setText(pm.getString("mv.terminalperformanceconfig.bit8"));
		label_3.setBounds(0, 18, 80, 30);
		byte3Panel.add(label_3);

		chckbxsda = new JCheckBox();
		chckbxsda.setText("静态数据认证（SDA）");
		chckbxsda.setBounds(90, 18, 182, 30);
		byte3Panel.add(chckbxsda);

		final JLabel label_5 = new JLabel();
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setText(pm.getString("mv.terminalperformanceconfig.bit7"));
		label_5.setBounds(0, 50, 80, 30);
		byte3Panel.add(label_5);

		chckbxdda = new JCheckBox();
		chckbxdda.setText("动态数据认证（DDA）");
		chckbxdda.setBounds(90, 50, 182, 30);
		byte3Panel.add(chckbxdda);

		final JLabel label_7 = new JLabel();
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		label_7.setText(pm.getString("mv.terminalperformanceconfig.bit6"));
		label_7.setBounds(0, 86, 80, 30);
		byte3Panel.add(label_7);

		checkBox_5 = new JCheckBox();
		checkBox_5.setText(pm.getString("mv.terminalperformanceconfig.eatcard"));
		checkBox_5.setBounds(90, 86, 120, 30);
		byte3Panel.add(checkBox_5);

		final JLabel label_10 = new JLabel();
		label_10.setHorizontalAlignment(SwingConstants.RIGHT);
		label_10.setText(pm.getString("mv.terminalperformanceconfig.bit4"));
		label_10.setBounds(0, 122, 80, 30);
		byte3Panel.add(label_10);

		chckbxcda = new JCheckBox();
		chckbxcda.setText("复合动态数据认证/应用密文生成（CDA）");
		chckbxcda.setBounds(90, 122, 284, 30);
		byte3Panel.add(chckbxcda);

		final JLabel lblOther = new JLabel();
		lblOther.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOther.setText("Other：");
		lblOther.setBounds(-5, 162, 85, 30);
		byte3Panel.add(lblOther);

		final JLabel rufLabel_1 = new JLabel();
		rufLabel_1.setText(pm.getString("mv.terminalperformanceconfig.ruf"));
		rufLabel_1.setBounds(90, 162, 120, 30);
		byte3Panel.add(rufLabel_1);

		final JPanel byte2Panel = new JPanel();
		byte2Panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "BYTE2-CVM\u6027\u80FD\uFF1A", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		byte2Panel.setBounds(28, 261, 493, 141);
		byte2Panel.setLayout(null);
		add(byte2Panel);

		final JLabel label_14 = new JLabel();
		label_14.setHorizontalAlignment(SwingConstants.RIGHT);
		label_14.setText(pm.getString("mv.terminalperformanceconfig.bit8"));
		label_14.setBounds(2, 29, 80, 30);
		byte2Panel.add(label_14);

		chckbxIcpin = new JCheckBox();
		chckbxIcpin.setText("IC 卡明文PIN 验证");
		chckbxIcpin.setBounds(92, 29, 159, 30);
		byte2Panel.add(chckbxIcpin);

		final JLabel label_15 = new JLabel();
		label_15.setHorizontalAlignment(SwingConstants.RIGHT);
		label_15.setText(pm.getString("mv.terminalperformanceconfig.bit7"));
		label_15.setBounds(2, 65, 80, 30);
		byte2Panel.add(label_15);

		chckbxpin = new JCheckBox();
		chckbxpin.setText("加密PIN 联机验证");
		chckbxpin.setBounds(92, 65, 144, 30);
		byte2Panel.add(chckbxpin);

		final JLabel label_16 = new JLabel();
		label_16.setHorizontalAlignment(SwingConstants.RIGHT);
		label_16.setText(pm.getString("mv.terminalperformanceconfig.bit6"));
		label_16.setBounds(2, 101, 80, 30);
		byte2Panel.add(label_16);

		checkBox_9 = new JCheckBox();
		checkBox_9.setText("签名（纸）");
		checkBox_9.setBounds(92, 101, 120, 30);
		byte2Panel.add(checkBox_9);

		final JLabel label_17 = new JLabel();
		label_17.setHorizontalAlignment(SwingConstants.RIGHT);
		label_17.setText(pm.getString("mv.terminalperformanceconfig.bit4"));
		label_17.setBounds(257, 29, 66, 30);
		byte2Panel.add(label_17);

		checkBox_10 = new JCheckBox();
		checkBox_10.setText(pm.getString("mv.terminalperformanceconfig.cvm"));
		checkBox_10.setBounds(333, 29, 107, 30);
		byte2Panel.add(checkBox_10);

		final JLabel label_18 = new JLabel();
		label_18.setHorizontalAlignment(SwingConstants.RIGHT);
		label_18.setText(pm.getString("mv.terminalperformanceconfig.bit1"));
		label_18.setBounds(243, 65, 80, 30);
		byte2Panel.add(label_18);

		checkBox_11 = new JCheckBox();
		checkBox_11.setText("持卡人证件验证");
		checkBox_11.setBounds(333, 65, 144, 30);
		byte2Panel.add(checkBox_11);

		final JLabel lblOther_1 = new JLabel();
		lblOther_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOther_1.setText("Other：");
		lblOther_1.setBounds(238, 101, 85, 30);
		byte2Panel.add(lblOther_1);

		final JLabel rufLabel_2 = new JLabel();
		rufLabel_2.setText(pm.getString("mv.terminalperformanceconfig.ruf"));
		rufLabel_2.setBounds(333, 101, 120, 30);
		byte2Panel.add(rufLabel_2);

		init();

		final JButton saveButton = new JButton();
		saveButton.setText(pm.getString("mv.terminalperformanceconfig.save"));
		saveButton.setBounds(123, 623, 84, 21);
		add(saveButton);
		// 保存按钮消息响应函数
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveTermPerformConfig();
			}
		});

	}

	// 保存终端性能配置参数，采用整数相加的方式来实现二进制数之间的与运算
	private void saveTermPerformConfig() {
		int Byte1 = 0;
		int Byte2 = 0;
		int Byte3 = 0;
		String termPerform;
		String valueStr;

		if (checkBox.isSelected()) {
			Byte1 += 1;
		}
		if (checkBox_2.isSelected()) {
			Byte1 += 2;
		}
		if (checkBox_4.isSelected()) {
			Byte1 += 4;
		}
		valueStr = Integer.toHexString(Byte1);
		termPerform = addStr(valueStr.toUpperCase(), "0", 2, 0);
		if (chckbxIcpin.isSelected()) {
			Byte2 += 1;
		}
		if (chckbxpin.isSelected()) {
			Byte2 += 2;
		}
		if (checkBox_9.isSelected()) {
			Byte2 += 4;
		}
		if (checkBox_10.isSelected()) {
			Byte2 += 16;
		}
		// 0+128相当于执行00000000|10000000操作，其他语句以此类推
		if (checkBox_11.isSelected()) {
			Byte2 += 128;
		}
		valueStr = Integer.toHexString(Byte2);
		termPerform += addStr(valueStr.toUpperCase(), "0", 2, 0);
		if (chckbxsda.isSelected()) {
			Byte3 += 1;
		}
		if (chckbxdda.isSelected()) {
			Byte3 += 2;
		}
		if (checkBox_5.isSelected()) {
			Byte3 += 4;
		}
		if (chckbxcda.isSelected()) {
			Byte3 += 16;
		}
		valueStr = Integer.toHexString(Byte3);
		termPerform += addStr(valueStr.toUpperCase(), "0", 2, 0);

		
		Config.setValue("Terminal_Data", "terminal_perform", termPerform);
		JOptionPane.showMessageDialog(null, "保存成功！");
	}

	private String addStr(String str, String addStr, int len, int position) {
		int strLen = str.length();
		if (strLen >= len) {
			return str;
		} else {
			if (0 == position) {
				for (int i = 0; i < len - strLen; i++) {
					str = addStr + str;
				}
			} else {
				for (int i = 0; i < len - strLen; i++) {
					str = str + addStr;
				}
			}
		}
		return str;
	}

	// 此函数用于将长度不足8位的二进制字符串通过左补0的方式补足8位
	private String fillZero(String s) {
		String str;
		str = "00000000" + s;
		return str.substring(s.length());

	}

	// 初始化界面，将从数据库中取得的16进制字符串按照2字节的长度截为3段，
	// 然后将该16进制数还原为8字节的二进制字符串
	private void init() {
		setName(pm.getString("mv.terminalperformanceconfig.name"));

		String tpconfig=Config.getValue("Terminal_Data", "terminal_perform");

		if (WDAssert.isNotEmpty(tpconfig)) {
			String B1 = tpconfig.substring(0, 2);
			// 记住，截取到的字符串是16进制的，所以需要调用Integer.parseInt(B1, 16)才能正确还原为2进制
			B1 = Integer.toBinaryString(Integer.parseInt(B1, 16));
			//System.out.println(B1);
			if (B1.length() < 8) {
				B1 = fillZero(B1);
			}
			if ('1' == B1.charAt(7)) {
				checkBox.setSelected(true);
			}
			if ('1' == B1.charAt(6)) {
				checkBox_2.setSelected(true);
			}
			if ('1' == B1.charAt(5)) {
				checkBox_4.setSelected(true);
			}

			String B2 = tpconfig.substring(2, 4);
			B2 = Integer.toBinaryString(Integer.parseInt(B2, 16));
			//System.out.println(B2);
			if (B2.length() < 8) {
				B2 = fillZero(B2);
			}
			if ('1' == B2.charAt(7)) {
				chckbxIcpin.setSelected(true);
			}
			if ('1' == B2.charAt(6)) {
				chckbxpin.setSelected(true);
			}
			if ('1' == B2.charAt(5)) {
				checkBox_9.setSelected(true);
			}
			if ('1' == B2.charAt(3)) {
				checkBox_10.setSelected(true);
			}
			if ('1' == B2.charAt(0)) {
				checkBox_11.setSelected(true);
			}

			String B3 = tpconfig.substring(4);
			B3 = Integer.toBinaryString(Integer.parseInt(B3, 16));
			//System.out.println(B3);
			if (B3.length() < 8) {
				B3 = fillZero(B3);
			}

			if ('1' == B3.charAt(7)) {
				chckbxsda.setSelected(true);
			}
			if ('1' == B3.charAt(6)) {
				chckbxdda.setSelected(true);
			}
			if ('1' == B3.charAt(5)) {
				checkBox_5.setSelected(true);
			}
			if ('1' == B3.charAt(3)) {
				chckbxcda.setSelected(true);
			}

		}
	}

}
