package com.gerenhua.cardcheck.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileFilter;

import com.gerenhua.cardcheck.app.JImagePanel;
import com.gerenhua.cardcheck.logic.apdu.CommonAPDU;
import com.gerenhua.cardcheck.logic.impl.TradeThread;
import com.gerenhua.cardcheck.utils.Config;
import com.gerenhua.cardcheck.utils.FileUtil;
import com.gerenhua.cardcheck.utils.PropertiesManager;
import com.watchdata.commons.lang.WDAssert;

public class AtmPanel extends JImagePanel {

	private static final long serialVersionUID = 8540752419780942870L;
	public static JTextField moneyTextField;
	public static String proPath = System.getProperty("user.dir");
	public static JButton qPBOCButton;
	public static JButton lendButton;
	public static JButton earmarkButton;
	public static JButton ecashButton;
	public final JButton reportButton;
	private static JLabel tradeTypeLabel;
	private static JTextField tradeTypeField;
	private static JLabel enterMoneyLabel;
	private JLabel tradingLabel;
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
	 * @throws IOException 
	 * 
	 * @throws Exception
	 */
	public AtmPanel(final JTextPane textPane,final JTextPane textPane1) throws IOException{
		
		super(ImageIO.read(AtmPanel.class.getResource("/com/watchdata/cardcheck/resources/images/trade.png")));
		
		setLayout(null);

		tradeTypeLabel = new JLabel();
		tradeTypeLabel.setText(pm.getString("mv.tradepanel.tradeType"));
		tradeTypeLabel.setBounds(117, 57, 54, 20);
		add(tradeTypeLabel);

		tradeTypeField = new JTextField();
		tradeTypeField.setBounds(171, 57, 120, 20);
		add(tradeTypeField);

		enterMoneyLabel = new JLabel();
		enterMoneyLabel.setText(pm.getString("mv.tradepanel.enterMoney"));
		enterMoneyLabel.setBounds(117, 87, 54, 20);
		add(enterMoneyLabel);

		moneyTextField = new JTextField();
		moneyTextField.setBounds(171, 87, 120, 20);
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

		tradingLabel = new JLabel();
		tradingLabel.setBounds(112, 105, 200, 100);
		add(tradingLabel);
		tradingLabel.setVisible(false);

		qPBOCButton = new JButton();
		/* qPBOCButton.setText("qPBOC"); */
		qPBOCButton.setBounds(6, 45, 84, 25);
		qPBOCButton.setOpaque(true);
		qPBOCButton.setFocusPainted(false);
		qPBOCButton.setContentAreaFilled(false);// 设置不画按钮背景
		qPBOCButton.setBorderPainted(false);
		qPBOCButton.setIcon(new ImageIcon(AtmPanel.class.getResource("/com/watchdata/cardcheck/resources/images/qPBOC.png")));
		add(qPBOCButton);
		qPBOCButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tradeType = pm.getString("mv.tradepanel.qPBOC");
				setTradeType(tradeType);
				//更新交易状态
				Config.setValue("Terminal_Data", "currentTradeType", tradeType);
				//设置检测报告按钮不可用
				reportButton.setEnabled(false);
			}
		});

		lendButton = new JButton();
		lendButton.setOpaque(true);
		lendButton.setFocusPainted(false);
		lendButton.setContentAreaFilled(false);// 设置不画按钮背景
		lendButton.setBorderPainted(false);
		lendButton.setIcon(new ImageIcon(AtmPanel.class.getResource("/com/watchdata/cardcheck/resources/images/lend.png")));
		lendButton.setBounds(6, 75, 84, 25);
		add(lendButton);
		lendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tradeType = pm.getString("mv.tradepanel.lend");
				setTradeType(tradeType);
				//更新交易状态
				Config.setValue("Terminal_Data", "currentTradeType", tradeType);
				//设置检测报告按钮不可用
				reportButton.setEnabled(false);
			}
		});

		ecashButton = new JButton();
		ecashButton.setOpaque(true);
		ecashButton.setFocusPainted(false);
		ecashButton.setContentAreaFilled(false);// 设置不画按钮背景
		ecashButton.setBorderPainted(false);
		ecashButton.setIcon(new ImageIcon(AtmPanel.class.getResource("/com/watchdata/cardcheck/resources/images/ecash.png")));
		ecashButton.setBounds(6, 105, 84, 25);
		add(ecashButton);
		ecashButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tradeType = pm.getString("mv.tradepanel.ecash");
				setTradeType(tradeType);
				//更新交易状态
				Config.setValue("Terminal_Data", "currentTradeType", tradeType);
				//设置检测报告按钮不可用
				reportButton.setEnabled(false);
			}
		});

		earmarkButton = new JButton();
		earmarkButton.setOpaque(true);
		earmarkButton.setFocusPainted(false);
		earmarkButton.setContentAreaFilled(false);// 设置不画按钮背景
		earmarkButton.setBorderPainted(false);
		earmarkButton.setIcon(new ImageIcon(AtmPanel.class.getResource("/com/watchdata/cardcheck/resources/images/earmark.png")));
		earmarkButton.setBounds(6, 135, 84, 25);
		add(earmarkButton);
		earmarkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tradeType = pm.getString("mv.tradepanel.earmark");
				setTradeType(tradeType);
				//更新交易状态
				Config.setValue("Terminal_Data", "currentTradeType", tradeType);
				//设置检测报告按钮不可用
				reportButton.setEnabled(false);
			}
		});

		reportButton = new JButton();
		reportButton.setBounds(423, 87, 120, 25);
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
				
				String currentTradeType=Config.getValue("Terminal_Data", "currentTradeType");
				
				if (WDAssert.isEmpty(currentTradeType)) {
					JOptionPane.showMessageDialog(null, "交易状态丢失！", "提示框", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String filePath = System.getProperty("user.dir") + "/report/" + currentTradeType + ".doc";
				
				File file=new File(filePath);
				if (file.exists()) {
					Object[] options = { "打开", "保存" };
					int ret = JOptionPane.showOptionDialog(null, "交易检测报告", "提示框", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
					if (ret == JOptionPane.YES_OPTION) {
						// word   
						try {
								Runtime.getRuntime().exec("cmd /c start winword \"\" \""+filePath+"\"");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "打开文件失败，位置："+filePath+"请手动操作！");
							return ;
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
								if (f.getName().endsWith(".doc")||f.isDirectory()){
									return true;
								}else {
									return false;
								}
							}
						});
						fileChooser.setSelectedFile(new File(currentTradeType+".doc"));
						ret = fileChooser.showSaveDialog(null);
						if (ret == JFileChooser.APPROVE_OPTION) {
							FileUtil.copyFile(filePath,fileChooser.getSelectedFile().getAbsolutePath());
							JOptionPane.showMessageDialog(null, "保存成功！");
						} else {
							return;
						}
					}
				}else {
					JOptionPane.showMessageDialog(null, "检测报告不存在！", "提示框", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		final JButton button0 = new JButton();
		button0.setOpaque(true);
		button0.setFocusPainted(false);
		button0.setContentAreaFilled(false);// 设置不画按钮背景
		button0.setBorderPainted(false);
		button0.setIcon(new ImageIcon(AtmPanel.class.getResource("/com/watchdata/cardcheck/resources/images/0.png")));
		button0.setBounds(152, 373, 32, 32);
		add(button0);
		button0.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				money = new StringBuffer(moneyTextField.getText());
				if (money.length() > 0) {
					money.append("0");
					if (decimalDigitsLimit(money.toString())) {
						moneyTextField.setText(money.toString());
					}
				}
			}
		});

		final JButton button1 = new JButton();
		button1.setOpaque(true);
		button1.setFocusPainted(false);
		button1.setContentAreaFilled(false);// 设置不画按钮背景
		button1.setBorderPainted(false);
		button1.setIcon(new ImageIcon(AtmPanel.class.getResource("/com/watchdata/cardcheck/resources/images/1.png")));
		button1.setBounds(107, 253, 32, 32);
		add(button1);
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				money = new StringBuffer(moneyTextField.getText());
				money.append("1");
				if (decimalDigitsLimit(money.toString())) {
					moneyTextField.setText(money.toString());
				}
			}
		});

		final JButton button2 = new JButton();
		button2.setOpaque(true);
		button2.setFocusPainted(false);
		button2.setContentAreaFilled(false);// 设置不画按钮背景
		button2.setBorderPainted(false);
		button2.setIcon(new ImageIcon(AtmPanel.class.getResource("/com/watchdata/cardcheck/resources/images/2.png")));
		button2.setBounds(152, 253, 32, 32);
		add(button2);
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				money = new StringBuffer(moneyTextField.getText());
				money.append("2");
				if (decimalDigitsLimit(money.toString())) {
					moneyTextField.setText(money.toString());
				}
			}
		});

		final JButton button3 = new JButton();
		button3.setOpaque(true);
		button3.setFocusPainted(false);
		button3.setContentAreaFilled(false);// 设置不画按钮背景
		button3.setBorderPainted(false);
		button3.setIcon(new ImageIcon(AtmPanel.class.getResource("/com/watchdata/cardcheck/resources/images/3.png")));
		button3.setBounds(197, 253, 32, 32);
		add(button3);
		button3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				money = new StringBuffer(moneyTextField.getText());
				money.append("3");
				if (decimalDigitsLimit(money.toString())) {
					moneyTextField.setText(money.toString());
				}
			}
		});

		final JButton button4 = new JButton();
		button4.setOpaque(true);
		button4.setFocusPainted(false);
		button4.setContentAreaFilled(false);// 设置不画按钮背景
		button4.setBorderPainted(false);
		button4.setIcon(new ImageIcon(AtmPanel.class.getResource("/com/watchdata/cardcheck/resources/images/4.png")));
		button4.setBounds(107, 293, 32, 32);
		add(button4);
		button4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				money = new StringBuffer(moneyTextField.getText());
				money.append("4");
				if (decimalDigitsLimit(money.toString())) {
					moneyTextField.setText(money.toString());
				}
			}
		});

		final JButton button5 = new JButton();
		button5.setOpaque(true);
		button5.setFocusPainted(false);
		button5.setContentAreaFilled(false);// 设置不画按钮背景
		button5.setBorderPainted(false);
		button5.setIcon(new ImageIcon(AtmPanel.class.getResource("/com/watchdata/cardcheck/resources/images/5.png")));
		button5.setBounds(152, 293, 32, 32);
		add(button5);
		button5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				money = new StringBuffer(moneyTextField.getText());
				money.append("5");
				if (decimalDigitsLimit(money.toString())) {
					moneyTextField.setText(money.toString());
				}
			}
		});

		final JButton button6 = new JButton();
		button6.setOpaque(true);
		button6.setFocusPainted(false);
		button6.setContentAreaFilled(false);// 设置不画按钮背景
		button6.setBorderPainted(false);
		button6.setIcon(new ImageIcon(AtmPanel.class.getResource("/com/watchdata/cardcheck/resources/images/6.png")));
		button6.setBounds(197, 293, 32, 32);
		add(button6);
		button6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				money = new StringBuffer(moneyTextField.getText());
				money.append("6");
				if (decimalDigitsLimit(money.toString())) {
					moneyTextField.setText(money.toString());
				}
			}
		});

		final JButton button7 = new JButton();
		button7.setOpaque(true);
		button7.setFocusPainted(false);
		button7.setContentAreaFilled(false);// 设置不画按钮背景
		button7.setBorderPainted(false);
		button7.setIcon(new ImageIcon(AtmPanel.class.getResource("/com/watchdata/cardcheck/resources/images/7.png")));
		button7.setBounds(107, 333, 32, 32);
		add(button7);
		button7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				money = new StringBuffer(moneyTextField.getText());
				money.append("7");
				if (decimalDigitsLimit(money.toString())) {
					moneyTextField.setText(money.toString());
				}
			}
		});

		final JButton button8 = new JButton();
		button8.setOpaque(true);
		button8.setFocusPainted(false);
		button8.setContentAreaFilled(false);// 设置不画按钮背景
		button8.setBorderPainted(false);
		button8.setIcon(new ImageIcon(AtmPanel.class.getResource("/com/watchdata/cardcheck/resources/images/8.png")));
		button8.setBounds(152, 333, 32, 32);
		add(button8);
		button8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				money = new StringBuffer(moneyTextField.getText());
				money.append("8");
				if (decimalDigitsLimit(money.toString())) {
					moneyTextField.setText(money.toString());
				}
			}
		});

		final JButton button9 = new JButton();
		button9.setOpaque(true);
		button9.setFocusPainted(false);
		button9.setContentAreaFilled(false);// 设置不画按钮背景
		button9.setBorderPainted(false);
		button9.setIcon(new ImageIcon(AtmPanel.class.getResource("/com/watchdata/cardcheck/resources/images/9.png")));
		button9.setBounds(197, 333, 32, 32);
		add(button9);
		button9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				money = new StringBuffer(moneyTextField.getText());
				money.append("9");
				if (decimalDigitsLimit(money.toString())) {
					moneyTextField.setText(money.toString());
				}
			}
		});

		final JButton dotButton = new JButton();
		dotButton.setOpaque(true);
		dotButton.setFocusPainted(false);
		dotButton.setContentAreaFilled(false);// 设置不画按钮背景
		dotButton.setBorderPainted(false);
		dotButton.setIcon(new ImageIcon(AtmPanel.class.getResource("/com/watchdata/cardcheck/resources/images/dot.png")));
		dotButton.setBounds(107, 373, 32, 32);
		add(dotButton);
		dotButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				money = new StringBuffer(moneyTextField.getText());
				money.append(".");
				if (decimalDigitsLimit(money.toString())) {
					moneyTextField.setText(money.toString());
				}
			}
		});

		final JButton backButton = new JButton();
		backButton.setOpaque(true);
		backButton.setFocusPainted(false);
		backButton.setContentAreaFilled(false);// 设置不画按钮背景
		backButton.setBorderPainted(false);
		backButton.setIcon(new ImageIcon(AtmPanel.class.getResource("/com/watchdata/cardcheck/resources/images/00.png")));
		backButton.setBounds(197, 373, 32, 32);
		add(backButton);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				money = new StringBuffer(moneyTextField.getText());
				money.append("00");
				if (decimalDigitsLimit(money.toString())) {
					moneyTextField.setText(money.toString());
				}
			}
		});

		final JButton okButton = new JButton();
		okButton.setOpaque(true);
		okButton.setFocusPainted(false);
		okButton.setContentAreaFilled(false);// 设置不画按钮背景
		okButton.setBorderPainted(false);
		okButton.setIcon(new ImageIcon(AtmPanel.class.getResource("/com/watchdata/cardcheck/resources/images/OK.png")));
		okButton.setBounds(242, 293, 70, 31);
		add(okButton);

		final JButton cancelButton = new JButton();
		cancelButton.setOpaque(true);
		cancelButton.setFocusPainted(false);
		cancelButton.setContentAreaFilled(false);// 设置不画按钮背景
		cancelButton.setBorderPainted(false);
		cancelButton.setIcon(new ImageIcon(AtmPanel.class.getResource("/com/watchdata/cardcheck/resources/images/cancel.png")));
		cancelButton.setBounds(242, 253, 70, 31);
		add(cancelButton);

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TradeThread tradeThread=new TradeThread(money, tradeType, tradingLabel,reportButton, textPane);
				Thread thread=new Thread(tradeThread);
				thread.start();
				
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tradeTypeLabel.setVisible(false);
				tradeTypeField.setVisible(false);
				enterMoneyLabel.setVisible(false);
				moneyTextField.setVisible(false);
				tradingLabel.setVisible(false);
			}
		});

	}

	public static boolean decimalDigitsLimit(String moneyStr) {
		String eg = "^[0-9]{1,3}([.]{1}[0-9]{0,2})?$";
		Matcher m = Pattern.compile(eg, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(moneyStr);
		return m.find() ? true : false;

	}

	public static void setTradeType(String tradeType) {
		money.delete(0, money.length());
		moneyTextField.setText(money.toString());
		tradeTypeLabel.setVisible(true);
		tradeTypeField.setVisible(true);
		enterMoneyLabel.setVisible(true);
		moneyTextField.setVisible(true);
		tradeTypeField.setText(tradeType);
	}

	public static void setTradeTypeStr(String tradeType) {
		AtmPanel.tradeType = tradeType;
	}
}
