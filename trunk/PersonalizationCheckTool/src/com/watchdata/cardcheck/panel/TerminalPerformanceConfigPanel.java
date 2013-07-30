package com.watchdata.cardcheck.panel;

import java.awt.BorderLayout;
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
import javax.swing.border.TitledBorder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.watchdata.cardcheck.dao.ITermPerformConfigDao;
import com.watchdata.cardcheck.dao.pojo.TermPerformConfig;
import com.watchdata.cardcheck.utils.PropertiesManager;

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
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class TerminalPerformanceConfigPanel extends JPanel {
	Color colorYellow = new Color(255, 255, 192);
	private PropertiesManager pm = new PropertiesManager();
	/**
	 * Create the panel
	 */
	public ApplicationContext ctx = new FileSystemXmlApplicationContext(
			"classpath:applicationContext.xml");
	private TermPerformConfig termPerConfig = new TermPerformConfig();
	private ITermPerformConfigDao iTermPerConfigDao;
	public static JCheckBox checkBox;
	private JCheckBox checkBox_1;
	private JCheckBox checkBox_2;
	private JCheckBox checkBox_3;
	private JCheckBox checkBox_4;
	private JCheckBox checkBox_5;
	private JCheckBox checkBox_6;
	private JCheckBox checkBox_7;
	private JCheckBox checkBox_8;
	private JCheckBox checkBox_9;
	private JCheckBox checkBox_10;
	private JCheckBox checkBox_11;

	public TerminalPerformanceConfigPanel() {
		super(new BorderLayout());
		setLayout(null);
		setBorder(JTBorderFactory.createTitleBorder(pm.getString("mv.terminalperformanceconfig.name")));

		final JLabel termPermLabel = new JLabel();
		termPermLabel.setBounds(0, 50, 97, 20);
		termPermLabel.setHorizontalAlignment(SwingConstants.CENTER);
		termPermLabel.setFont(new Font(pm.getString("mv.applicaiton.font"), Font.BOLD, 12));
		termPermLabel.setText(pm
				.getString("mv.terminalperformanceconfig.performance"));
		add(termPermLabel);

		final JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(79, 60, 730, 20);
		add(separator_1);

		/*
		 * final JSplitPane splitPane = new JSplitPane();
		 * splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		 * splitPane.setDividerSize(0); splitPane.setDividerLocation(500);
		 * add(splitPane);
		 * 
		 * final JPanel panel = new JPanel(); panel.setLayout(null);
		 * panel.setBorder(JTBorderFactory.createTitleBorder("终端性能"));
		 * panel.setBackground(colorYellow); splitPane.setLeftComponent(panel);
		 */
		

		final JLabel byte1Label = new JLabel();
		byte1Label.setHorizontalAlignment(SwingConstants.LEFT);
		byte1Label.setBounds(30, 95, 210, 30);
		byte1Label.setText(pm.getString("mv.terminalperformanceconfig.byte1"));
		add(byte1Label);
		
		final JPanel byte1Panel = new JPanel();
		byte1Panel.setLayout(null);
		byte1Panel.setBorder(new TitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		byte1Panel.setBounds(30, 135, 215, 215);
		add(byte1Panel);
		
		final JLabel label_2 = new JLabel();
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setText(pm.getString("mv.terminalperformanceconfig.bit8"));
		label_2.setBounds(0, 5, 80, 30);
		byte1Panel.add(label_2);

		checkBox = new JCheckBox();
		checkBox.setText(pm.getString("mv.terminalperformanceconfig.keybord"));
		/* checkBox.setBackground(colorYellow); */
		checkBox.setBounds(90, 5, 120, 30);
		byte1Panel.add(checkBox);
		
		final JLabel label_4 = new JLabel();
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setText(pm.getString("mv.terminalperformanceconfig.bit7"));
		label_4.setBounds(0, 50, 80, 30);
		byte1Panel.add(label_4);

		checkBox_2 = new JCheckBox();
		checkBox_2.setText(pm
				.getString("mv.terminalperformanceconfig.magnetic"));
		/* checkBox_2.setBackground(colorYellow); */
		checkBox_2.setBounds(90, 50, 120, 30);
		byte1Panel.add(checkBox_2);
		
		final JLabel label_6 = new JLabel();
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setText(pm.getString("mv.terminalperformanceconfig.bit6"));
		label_6.setBounds(0, 95, 80, 30);
		byte1Panel.add(label_6);

		checkBox_4 = new JCheckBox();
		checkBox_4.setText(pm
				.getString("mv.terminalperformanceconfig.contactic"));
		/* checkBox_4.setBackground(colorYellow); */
		checkBox_4.setBounds(90, 95, 120, 30);
		byte1Panel.add(checkBox_4);
		
		final JLabel label_8 = new JLabel();
		label_8.setHorizontalAlignment(SwingConstants.RIGHT);
		label_8.setText(pm.getString("mv.terminalperformanceconfig.bit51"));
		label_8.setBounds(0, 140, 80, 30);
		byte1Panel.add(label_8);

		final JLabel rufLabel = new JLabel();
		rufLabel.setText(pm.getString("mv.terminalperformanceconfig.ruf"));
		rufLabel.setBounds(90, 140, 120, 30);
		byte1Panel.add(rufLabel);

		final JLabel label_1 = new JLabel();
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setText(pm.getString("mv.terminalperformanceconfig.byte3"));
		label_1.setBounds(300, 95, 210, 30);
		add(label_1);

		final JPanel byte3Panel = new JPanel();
		byte3Panel.setBorder(new TitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		byte3Panel.setBounds(295, 135, 225, 215);
		byte3Panel.setLayout(null);
		add(byte3Panel);

		final JLabel label_3 = new JLabel();
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setText(pm.getString("mv.terminalperformanceconfig.bit8"));
		label_3.setBounds(10, 5, 80, 30);
		byte3Panel.add(label_3);

		checkBox_1 = new JCheckBox();
		checkBox_1.setText(pm.getString("mv.terminalperformanceconfig.sda"));
		/* checkBox_1.setBackground(colorYellow); */
		checkBox_1.setBounds(100, 5, 120, 30);
		byte3Panel.add(checkBox_1);

		

		final JLabel label_5 = new JLabel();
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setText(pm.getString("mv.terminalperformanceconfig.bit7"));
		label_5.setBounds(10, 50, 80, 30);
		byte3Panel.add(label_5);

		checkBox_3 = new JCheckBox();
		checkBox_3.setText(pm.getString("mv.terminalperformanceconfig.dda"));
		/* checkBox_3.setBackground(colorYellow); */
		checkBox_3.setBounds(100, 50, 120, 30);
		byte3Panel.add(checkBox_3);

		

		final JLabel label_7 = new JLabel();
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		label_7.setText(pm.getString("mv.terminalperformanceconfig.bit6"));
		label_7.setBounds(10, 95, 80, 30);
		byte3Panel.add(label_7);

		checkBox_5 = new JCheckBox();
		checkBox_5
				.setText(pm.getString("mv.terminalperformanceconfig.eatcard"));
		/* checkBox_5.setBackground(colorYellow); */
		checkBox_5.setBounds(100, 95, 120, 30);
		byte3Panel.add(checkBox_5);

		

		final JLabel label_10 = new JLabel();
		label_10.setHorizontalAlignment(SwingConstants.RIGHT);
		label_10.setText(pm.getString("mv.terminalperformanceconfig.bit4"));
		label_10.setBounds(10, 140, 80, 30);
		byte3Panel.add(label_10);

		checkBox_6 = new JCheckBox();
		checkBox_6.setText(pm.getString("mv.terminalperformanceconfig.cda"));
		/* checkBox_6.setBackground(colorYellow); */
		checkBox_6.setBounds(100, 140, 120, 30);
		byte3Panel.add(checkBox_6);

		final JLabel label_11 = new JLabel();
		label_11.setHorizontalAlignment(SwingConstants.RIGHT);
		label_11.setText(pm.getString("mv.terminalperformanceconfig.otherbits"));
		label_11.setBounds(5, 185, 85, 30);
		byte3Panel.add(label_11);

		final JLabel rufLabel_1 = new JLabel();
		rufLabel_1.setText(pm.getString("mv.terminalperformanceconfig.ruf"));
		rufLabel_1.setBounds(100, 185, 120, 30);
		byte3Panel.add(rufLabel_1);

		final JLabel label_13 = new JLabel();
		label_13.setHorizontalAlignment(SwingConstants.LEFT);
		label_13.setText(pm.getString("mv.terminalperformanceconfig.byte2"));
		label_13.setBounds(30, 365, 210, 30);
		add(label_13);
		
		final JPanel byte2Panel = new JPanel();
		byte2Panel.setBorder(new TitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		byte2Panel.setBounds(30, 405, 490, 130);
		byte2Panel.setLayout(null);
		add(byte2Panel);

		final JLabel label_14 = new JLabel();
		label_14.setHorizontalAlignment(SwingConstants.RIGHT);
		label_14.setText(pm.getString("mv.terminalperformanceconfig.bit8"));
		label_14.setBounds(0, 5, 80, 30);
		byte2Panel.add(label_14);

		checkBox_7 = new JCheckBox();
		checkBox_7.setText(pm.getString("mv.terminalperformanceconfig.icpin"));
		/* checkBox_7.setBackground(colorYellow); */
		checkBox_7.setBounds(90, 5, 144, 30);
		byte2Panel.add(checkBox_7);

		final JLabel label_15 = new JLabel();
		label_15.setHorizontalAlignment(SwingConstants.RIGHT);
		label_15.setText(pm.getString("mv.terminalperformanceconfig.bit7"));
		label_15.setBounds(0, 50, 80, 30);
		byte2Panel.add(label_15);

		checkBox_8 = new JCheckBox();
		checkBox_8.setText(pm.getString("mv.terminalperformanceconfig.pin"));
		/* checkBox_8.setBackground(colorYellow); */
		checkBox_8.setBounds(90, 50, 120, 30);
		byte2Panel.add(checkBox_8);

		final JLabel label_16 = new JLabel();
		label_16.setHorizontalAlignment(SwingConstants.RIGHT);
		label_16.setText(pm.getString("mv.terminalperformanceconfig.bit6"));
		label_16.setBounds(0, 95, 80, 30);
		byte2Panel.add(label_16);

		checkBox_9 = new JCheckBox();
		checkBox_9.setText(pm.getString("mv.terminalperformanceconfig.sign"));
		/* checkBox_9.setBackground(colorYellow); */
		checkBox_9.setBounds(90, 95, 120, 30);
		byte2Panel.add(checkBox_9);

		final JLabel label_17 = new JLabel();
		label_17.setHorizontalAlignment(SwingConstants.RIGHT);
		label_17.setText(pm.getString("mv.terminalperformanceconfig.bit4"));
		label_17.setBounds(270, 5, 80, 30);
		byte2Panel.add(label_17);

		checkBox_10 = new JCheckBox();
		checkBox_10.setText(pm.getString("mv.terminalperformanceconfig.cvm"));
		/* checkBox_10.setBackground(colorYellow); */
		checkBox_10.setBounds(360, 5, 120, 30);
		byte2Panel.add(checkBox_10);

		final JLabel label_18 = new JLabel();
		label_18.setHorizontalAlignment(SwingConstants.RIGHT);
		label_18.setText(pm.getString("mv.terminalperformanceconfig.bit1"));
		label_18.setBounds(270, 50, 80, 30);
		byte2Panel.add(label_18);

		checkBox_11 = new JCheckBox();
		checkBox_11
				.setText(pm.getString("mv.terminalperformanceconfig.verify"));
		/* checkBox_11.setBackground(colorYellow); */
		checkBox_11.setBounds(360, 50, 120, 30);
		byte2Panel.add(checkBox_11);

		final JLabel label_19 = new JLabel();
		label_19.setHorizontalAlignment(SwingConstants.RIGHT);
		label_19.setText(pm.getString("mv.terminalperformanceconfig.otherbits"));
		label_19.setBounds(265, 95, 85, 30);
		byte2Panel.add(label_19);
		
		final JLabel rufLabel_2 = new JLabel();
		rufLabel_2.setText(pm.getString("mv.terminalperformanceconfig.ruf"));
		rufLabel_2.setBounds(360, 95, 120, 30);
		byte2Panel.add(rufLabel_2);
		
		init();
		
		final JButton saveButton = new JButton();
		saveButton.setText(pm.getString("mv.terminalperformanceconfig.save"));
		saveButton.setBounds(496, 584, 84, 21);
		add(saveButton);
		// 保存按钮消息响应函数
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveTermPerformConfig();
			}
		});
		

		

		

		

		/*
		 * final JPanel panel_1 = new JPanel();
		 * splitPane.setRightComponent(panel_1);
		 */

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
		if (checkBox_7.isSelected()) {
			Byte2 += 1;
		}
		if (checkBox_8.isSelected()) {
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
		if (checkBox_1.isSelected()) {
			Byte3 += 1;
		}
		if (checkBox_3.isSelected()) {
			Byte3 += 2;
		}
		if (checkBox_5.isSelected()) {
			Byte3 += 4;
		}
		if (checkBox_6.isSelected()) {
			Byte3 += 16;
		}
		valueStr = Integer.toHexString(Byte3);
		termPerform += addStr(valueStr.toUpperCase(), "0", 2, 0);

		termPerConfig = iTermPerConfigDao.findTermPerform();
		if (null == termPerConfig) {
			JOptionPane.showMessageDialog(null, pm
					.getString("mv.terminalperformanceconfig.termid"), pm
					.getString("mv.terminalperformanceconfig.infoWindow"),
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		termPerConfig.setTermPerform(termPerform);
		if (iTermPerConfigDao.updateTermPerform(termPerConfig)) {
			JOptionPane.showMessageDialog(null, pm
					.getString("mv.terminalperformanceconfig.savesuccess"), pm
					.getString("mv.terminalperformanceconfig.infoWindow"),
					JOptionPane.INFORMATION_MESSAGE);
		}

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

		iTermPerConfigDao = (ITermPerformConfigDao) ctx
				.getBean("iTermPerConfigDao");
		// termPerConfig=null;
		TermPerformConfig tpconfig = new TermPerformConfig();
		tpconfig = null;
		tpconfig = iTermPerConfigDao.findTermPerform();

		if (tpconfig != null&&tpconfig.getTermPerform()!=null && tpconfig.getTermPerform().trim().length() > 5) {
			String B1 = tpconfig.getTermPerform().substring(0, 2);
			// 记住，截取到的字符串是16进制的，所以需要调用Integer.parseInt(B1, 16)才能正确还原为2进制
			B1 = Integer.toBinaryString(Integer.parseInt(B1, 16));
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

			String B2 = tpconfig.getTermPerform().substring(2, 4);
			B2 = Integer.toBinaryString(Integer.parseInt(B2, 16));
			if (B2.length() < 8) {
				B2 = fillZero(B2);
			}
			if ('1' == B2.charAt(7)) {
				checkBox_7.setSelected(true);
			}
			if ('1' == B2.charAt(6)) {
				checkBox_8.setSelected(true);
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

			String B3 = tpconfig.getTermPerform().substring(4);
			B3 = Integer.toBinaryString(Integer.parseInt(B3, 16));
			if (B3.length() < 8) {
				B3 = fillZero(B3);
			}

			if ('1' == B3.charAt(7)) {
				checkBox_1.setSelected(true);
			}
			if ('1' == B3.charAt(6)) {
				checkBox_3.setSelected(true);
			}
			if ('1' == B3.charAt(5)) {
				checkBox_5.setSelected(true);
			}
			if ('1' == B3.charAt(3)) {
				checkBox_6.setSelected(true);
			}

		}
		/*
		 * initModel(); initControls();
		 */
		/* initListeners(); */
	}

}
