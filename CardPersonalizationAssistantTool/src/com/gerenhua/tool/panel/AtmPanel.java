package com.gerenhua.tool.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileFilter;

import com.gerenhua.tool.logic.apdu.CommonAPDU;
import com.gerenhua.tool.logic.impl.TradeThread;
import com.gerenhua.tool.utils.Config;
import com.gerenhua.tool.utils.FileUtil;
import com.gerenhua.tool.utils.PropertiesManager;
import com.watchdata.commons.lang.WDAssert;

public class AtmPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JTextField moneyTextField;
	public static String proPath = System.getProperty("user.dir");
	public static JButton qPBOCButton;
	public static JButton lendButton;
	public static JButton earmarkButton;
	public static JButton ecashButton;
	public final JButton reportButton;
	private static JLabel enterMoneyLabel;
	private PropertiesManager pm = new PropertiesManager();
	private static StringBuffer money = new StringBuffer();
	public static String tradeType = "";
	public CommonAPDU apduHandler;

	// 终端性能列表，与配置界面上的配置型一致，从第一个字节开始
	public enum TerminalSupportType {
		// 依次为：接触式IC、磁条、手工键盘输入、证件验证、无需CVM、签名、联机PIN、
		// IC卡明文PIN校验、支持CDA、吞卡、支持DDA、支持SDA
		TOUCHIC, TRACK, KEYBOARD, CERTIFICATECHECK, NOCVM, SIGN, LINKPIN, ICPINCHECK, SUPPORTCDA, EATCARD, SUPPORTDDA, SUPPORTSDA;
	}

	/**
	 * Create the panel
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 */
	public AtmPanel(final JTextPane textPane, final JTextPane textPane1) throws IOException {
		// super(ImageIO.read(AtmPanel.class.getResource("/com/gerenhua/tool/resources/images/trade.png")));
		setLayout(null);

		enterMoneyLabel = new JLabel();
		enterMoneyLabel.setText("金额");
		enterMoneyLabel.setBounds(6, 154, 40, 23);
		add(enterMoneyLabel);

		moneyTextField = new JTextField();
		moneyTextField.setBounds(46, 154, 60, 23);
		moneyTextField.setText(money.toString());
		add(moneyTextField);
		moneyTextField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				char dot = '.';
				char zero = '0';
				money = new StringBuffer(moneyTextField.getText());
				money.append(c);
				if (!(Character.isDigit(c) || dot == c) || (!decimalDigitsLimit(money.toString())) || (zero == c && money.length() == 1)) {
					e.consume();
					money.deleteCharAt(money.length() - 1);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		qPBOCButton = new JButton();
		qPBOCButton.setText("QPBOC");
		qPBOCButton.setBounds(6, 121, 100, 23);
		qPBOCButton.setFocusPainted(false);
		qPBOCButton.setBorderPainted(false);
		add(qPBOCButton);
		qPBOCButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tradeType = pm.getString("mv.tradepanel.qPBOC");
				// 更新交易状态
				Config.setValue("Terminal_Data", "currentTradeType", tradeType);
				// 设置检测报告按钮不可用
				reportButton.setEnabled(false);

				TradeThread tradeThread = new TradeThread(money, tradeType, reportButton, textPane);
				Thread thread = new Thread(tradeThread);
				thread.start();
			}
		});

		lendButton = new JButton();
		lendButton.setText("借贷记");
		lendButton.setFocusPainted(false);
		lendButton.setBorderPainted(false);
		lendButton.setBounds(6, 22, 100, 23);
		add(lendButton);
		lendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tradeType = pm.getString("mv.tradepanel.lend");
				// 更新交易状态
				Config.setValue("Terminal_Data", "currentTradeType", tradeType);
				// 设置检测报告按钮不可用
				reportButton.setEnabled(false);

				TradeThread tradeThread = new TradeThread(money, tradeType, reportButton, textPane);
				Thread thread = new Thread(tradeThread);
				thread.start();
			}
		});

		ecashButton = new JButton();
		ecashButton.setText("电子现金");
		ecashButton.setFocusPainted(false);
		ecashButton.setBorderPainted(false);
		ecashButton.setBounds(6, 88, 100, 23);
		add(ecashButton);
		ecashButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tradeType = pm.getString("mv.tradepanel.ecash");
				// 更新交易状态
				Config.setValue("Terminal_Data", "currentTradeType", tradeType);
				// 设置检测报告按钮不可用
				reportButton.setEnabled(false);

				TradeThread tradeThread = new TradeThread(money, tradeType, reportButton, textPane);
				Thread thread = new Thread(tradeThread);
				thread.start();
			}
		});

		earmarkButton = new JButton();
		earmarkButton.setText("圈存");
		earmarkButton.setFocusPainted(false);
		earmarkButton.setBorderPainted(false);
		earmarkButton.setBounds(6, 55, 100, 23);
		add(earmarkButton);
		earmarkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tradeType = pm.getString("mv.tradepanel.earmark");
				// 更新交易状态
				Config.setValue("Terminal_Data", "currentTradeType", tradeType);
				// 设置检测报告按钮不可用
				reportButton.setEnabled(false);

				TradeThread tradeThread = new TradeThread(money, tradeType, reportButton, textPane);
				Thread thread = new Thread(tradeThread);
				thread.start();
			}
		});

		reportButton = new JButton();
		reportButton.setBounds(6, 456, 100, 23);
		reportButton.setAlignmentX(CENTER_ALIGNMENT);
		reportButton.setOpaque(false);
		reportButton.setFocusPainted(false);
		reportButton.setContentAreaFilled(false);// 设置不画按钮背景
		reportButton.setBorderPainted(false);
		reportButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		reportButton.setText("交易检测报告");
		reportButton.setEnabled(false);
		add(reportButton);

		reportButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String currentTradeType = Config.getValue("Terminal_Data", "currentTradeType");

				if (WDAssert.isEmpty(currentTradeType)) {
					JOptionPane.showMessageDialog(null, "交易状态丢失！", "提示框", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String filePath = System.getProperty("user.dir") + "/report/" + currentTradeType + ".doc";

				File file = new File(filePath);
				if (file.exists()) {
					Object[] options = { "打开", "保存" };
					int ret = JOptionPane.showOptionDialog(null, "交易检测报告", "提示框", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
					if (ret == JOptionPane.YES_OPTION) {
						// word
						try {
							Runtime.getRuntime().exec("cmd /c start winword \"\" \"" + filePath + "\"");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "打开文件失败，位置：" + filePath + "请手动操作！");
							return;
						}

					} else if (ret == JOptionPane.NO_OPTION) {
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setCurrentDirectory(new File("."));
						fileChooser.setFileFilter(new FileFilter() {

							@Override
							public String getDescription() {
								// TODO Auto-generated method stub
								return "mcrosoft office word 文档";
							}

							@Override
							public boolean accept(File f) {
								// TODO Auto-generated method stub
								if (f.getName().endsWith(".doc") || f.isDirectory()) {
									return true;
								} else {
									return false;
								}
							}
						});
						fileChooser.setSelectedFile(new File(currentTradeType + ".doc"));
						ret = fileChooser.showSaveDialog(null);
						if (ret == JFileChooser.APPROVE_OPTION) {
							FileUtil.copyFile(filePath, fileChooser.getSelectedFile().getAbsolutePath());
							JOptionPane.showMessageDialog(null, "保存成功！");
						} else {
							return;
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "检测报告不存在！", "提示框", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}

	public static boolean decimalDigitsLimit(String moneyStr) {
		String eg = "^[0-9]{1,3}([.]{1}[0-9]{0,2})?$";
		Matcher m = Pattern.compile(eg, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(moneyStr);
		return m.find() ? true : false;

	}

}
