package com.watchdata.cardcheck.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.watchdata.cardcheck.app.JImagePanel;
import com.watchdata.cardcheck.dao.IAppInfoDao;
import com.watchdata.cardcheck.dao.ITermPerformConfigDao;
import com.watchdata.cardcheck.dao.pojo.AppInfo;
import com.watchdata.cardcheck.logic.impl.ElectronicCashHandler;
import com.watchdata.cardcheck.logic.impl.PBOCHandler;
import com.watchdata.cardcheck.logic.impl.QPBOCHandler;
import com.watchdata.cardcheck.utils.Config;
import com.watchdata.cardcheck.utils.Configuration;
import com.watchdata.cardcheck.utils.FaceListener;
import com.watchdata.cardcheck.utils.FaceThread;
import com.watchdata.cardcheck.utils.FileUtil;
import com.watchdata.cardcheck.utils.PropertiesManager;
import com.watchdata.cardcheck.utils.SpringUtil;
import com.watchdata.cardcheck.utils.TermSupportUtil;
import com.watchdata.commons.lang.WDAssert;
import com.watchdata.commons.lang.WDStringUtil;

public class TradePanel extends JImagePanel {

	private static final long serialVersionUID = 8540752419780942870L;
	public static JTextField moneyTextField;
	public static String proPath = System.getProperty("user.dir");
	public ImageIcon qPBOCIcon = new ImageIcon(proPath + "/resources/images/qPBOC.png");
	public ImageIcon lendIcon = new ImageIcon(proPath + "/resources/images/lend.png");
	public ImageIcon earmarkIcon = new ImageIcon(proPath + "/resources/images/earmark.png");
	public ImageIcon ecashIcon = new ImageIcon(proPath + "/resources/images/ecash.png");
	public ImageIcon okIcon = new ImageIcon(proPath + "/resources/images/OK.png");
	public ImageIcon cancelIcon = new ImageIcon(proPath + "/resources/images/cancel.png");
	public ImageIcon zeroIcon = new ImageIcon(proPath + "/resources/images/0.png");
	public ImageIcon oneIcon = new ImageIcon(proPath + "/resources/images/1.png");
	public ImageIcon twoIcon = new ImageIcon(proPath + "/resources/images/2.png");
	public ImageIcon threeIcon = new ImageIcon(proPath + "/resources/images/3.png");
	public ImageIcon fourIcon = new ImageIcon(proPath + "/resources/images/4.png");
	public ImageIcon fiveIcon = new ImageIcon(proPath + "/resources/images/5.png");
	public ImageIcon sixIcon = new ImageIcon(proPath + "/resources/images/6.png");
	public ImageIcon sevenIcon = new ImageIcon(proPath + "/resources/images/7.png");
	public ImageIcon eightIcon = new ImageIcon(proPath + "/resources/images/8.png");
	public ImageIcon nineIcon = new ImageIcon(proPath + "/resources/images/9.png");
	public ImageIcon dotIcon = new ImageIcon(proPath + "/resources/images/dot.png");
	public ImageIcon backIcon = new ImageIcon(proPath + "/resources/images/00.png");
	public static JButton qPBOCButton;
	public static JButton lendButton;
	public static JButton earmarkButton;
	public static JButton ecashButton;
	public final JButton reportButton;
	private static JLabel welcomLabel;
	private static JLabel tradeTypeLabel;
	private static JTextField tradeTypeField;
	private static JLabel enterMoneyLabel;
	private JLabel tradingLabel;
	private PropertiesManager pm = new PropertiesManager();
	private static StringBuffer money = new StringBuffer();
	private static String tradeType = "";
	private String trading = "";
	private String tradeResult = "";
	private boolean success = false;

	private ElectronicCashHandler electronicCashHandler;
	private QPBOCHandler qpbocHandler;
	private PBOCHandler pBOCHandler;

	public void setElectronicCashHandler(ElectronicCashHandler electronicCashHandler) {
		this.electronicCashHandler = electronicCashHandler;
	}

	private ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

	private ITermPerformConfigDao iTermPerConfigDao;

	// 终端性能列表，与配置界面上的配置型一致，从第一个字节开始
	public enum TerminalSupportType {
		// 依次为：接触式IC、磁条、手工键盘输入、证件验证、无需CVM、签名、联机PIN、
		// IC卡明文PIN校验、支持CDA、吞卡、支持DDA、支持SDA
		TOUCHIC, TRACK, KEYBOARD, CERTIFICATECHECK, NOCVM, SIGN, LINKPIN, ICPINCHECK, SUPPORTCDA, EATCARD, SUPPORTDDA, SUPPORTSDA;
	}

	// 判断终端性能是否支持的辅助类
	private TermSupportUtil termSupportUtil;

	/**
	 * Create the panel
	 * 
	 * @throws Exception
	 */
	public TradePanel() throws Exception {
		super(proPath + "/resources/images/trade.png");

		setLayout(null);
		//setBorder(JTBorderFactory.createTitleBorder(pm.getString("mv.left.trade")));

		welcomLabel = new JLabel();
		welcomLabel.setText(pm.getString("mv.tradepanel.welcome"));
		welcomLabel.setBounds(285, 215, 200, 20);
		add(welcomLabel);

		tradeTypeLabel = new JLabel();
		tradeTypeLabel.setText(pm.getString("mv.tradepanel.tradeType"));
		tradeTypeLabel.setBounds(280, 215, 64, 20);
		add(tradeTypeLabel);
		tradeTypeLabel.setVisible(false);

		tradeTypeField = new JTextField();
		tradeTypeField.setBounds(345, 215, 120, 20);
		add(tradeTypeField);
		tradeTypeField.setEditable(false);
		tradeTypeField.setVisible(false);

		enterMoneyLabel = new JLabel();
		enterMoneyLabel.setText(pm.getString("mv.tradepanel.enterMoney"));
		enterMoneyLabel.setBounds(280, 245, 64, 20);
		add(enterMoneyLabel);
		enterMoneyLabel.setVisible(false);

		moneyTextField = new JTextField();
		moneyTextField.setBounds(345, 245, 120, 20);
		moneyTextField.setText(money.toString());
		add(moneyTextField);
		moneyTextField.setVisible(false);
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
		tradingLabel.setBounds(280, 175, 200, 100);
		add(tradingLabel);
		tradingLabel.setVisible(false);

		qPBOCButton = new JButton();
		/* qPBOCButton.setText("qPBOC"); */
		qPBOCButton.setBounds(162, 225, 84, 25);
		qPBOCButton.setOpaque(true);
		qPBOCButton.setFocusPainted(false);
		qPBOCButton.setContentAreaFilled(false);// 设置不画按钮背景
		qPBOCButton.setBorderPainted(false);
		qPBOCButton.setIcon(qPBOCIcon);
		add(qPBOCButton);
		qPBOCButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tradeType = pm.getString("mv.tradepanel.qPBOC");
				setTradeType(tradeType);
				//更新交易状态
				Configuration configuration=new Configuration();
				configuration.setValue("currentTradeType", tradeType);
				//设置检测报告按钮不可用
				reportButton.setEnabled(false);
			}
		});

		lendButton = new JButton();
		lendButton.setOpaque(true);
		lendButton.setFocusPainted(false);
		lendButton.setContentAreaFilled(false);// 设置不画按钮背景
		lendButton.setBorderPainted(false);
		lendButton.setIcon(lendIcon);
		lendButton.setBounds(162, 255, 84, 25);
		add(lendButton);
		lendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tradeType = pm.getString("mv.tradepanel.lend");
				setTradeType(tradeType);
				//更新交易状态
				Configuration configuration=new Configuration();
				configuration.setValue("currentTradeType", tradeType);
				//设置检测报告按钮不可用
				reportButton.setEnabled(false);
			}
		});

		ecashButton = new JButton();
		ecashButton.setOpaque(true);
		ecashButton.setFocusPainted(false);
		ecashButton.setContentAreaFilled(false);// 设置不画按钮背景
		ecashButton.setBorderPainted(false);
		ecashButton.setIcon(ecashIcon);
		ecashButton.setBounds(162, 285, 84, 25);
		add(ecashButton);
		ecashButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tradeType = pm.getString("mv.tradepanel.ecash");
				setTradeType(tradeType);
				//更新交易状态
				Configuration configuration=new Configuration();
				configuration.setValue("currentTradeType", tradeType);
				//设置检测报告按钮不可用
				reportButton.setEnabled(false);
			}
		});

		earmarkButton = new JButton();
		earmarkButton.setOpaque(true);
		earmarkButton.setFocusPainted(false);
		earmarkButton.setContentAreaFilled(false);// 设置不画按钮背景
		earmarkButton.setBorderPainted(false);
		earmarkButton.setIcon(earmarkIcon);
		earmarkButton.setBounds(162, 315, 84, 25);
		add(earmarkButton);
		earmarkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tradeType = pm.getString("mv.tradepanel.earmark");
				setTradeType(tradeType);
				//更新交易状态
				Configuration configuration=new Configuration();
				configuration.setValue("currentTradeType", tradeType);
				//设置检测报告按钮不可用
				reportButton.setEnabled(false);
			}
		});

		reportButton = new JButton();
		reportButton.setBounds(590, 215, 120, 25);
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
				Configuration configuration=new Configuration();
				String currentTradeType=configuration.getValue("currentTradeType");
				
				if (WDAssert.isEmpty(currentTradeType)) {
					JOptionPane.showMessageDialog(null, "交易状态丢失！", "提示框", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String filePath = System.getProperty("user.dir") + "/report/transaction/" + currentTradeType + ".doc";
				
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
		button0.setIcon(zeroIcon);
		button0.setBounds(325, 496, 32, 32);
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
		button1.setIcon(oneIcon);
		button1.setBounds(287, 382, 32, 32);
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
		button2.setIcon(twoIcon);
		button2.setBounds(325, 382, 32, 32);
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
		button3.setIcon(threeIcon);
		button3.setBounds(363, 382, 32, 32);
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
		button4.setIcon(fourIcon);
		button4.setBounds(287, 420, 32, 32);
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
		button5.setIcon(fiveIcon);
		button5.setBounds(325, 420, 32, 32);
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
		button6.setIcon(sixIcon);
		button6.setBounds(363, 420, 32, 32);
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
		button7.setIcon(sevenIcon);
		button7.setBounds(287, 458, 32, 32);
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
		button8.setIcon(eightIcon);
		button8.setBounds(325, 458, 32, 32);
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
		button9.setIcon(nineIcon);
		button9.setBounds(363, 458, 32, 32);
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
		dotButton.setIcon(dotIcon);
		dotButton.setBounds(287, 496, 32, 32);
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
		backButton.setIcon(backIcon);
		backButton.setBounds(364, 496, 32, 32);
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
		okButton.setIcon(okIcon);
		okButton.setBounds(402, 420, 70, 31);
		add(okButton);

		final JButton cancelButton = new JButton();
		cancelButton.setOpaque(true);
		cancelButton.setFocusPainted(false);
		cancelButton.setContentAreaFilled(false);// 设置不画按钮背景
		cancelButton.setBorderPainted(false);
		cancelButton.setIcon(cancelIcon);
		cancelButton.setBounds(402, 382, 70, 31);
		add(cancelButton);

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FaceThread faceThread = new FaceThread();
				money = new StringBuffer(moneyTextField.getText());
				if ("".equals(tradeType)) {
					JOptionPane.showMessageDialog(null, pm.getString("mv.tradepanel.selectTradeType"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
				} else {
					if (money.length() > 0) {
						// 获取终端性能参数
						String termPerform = "";
						boolean touchSupport = false;
						try {
							iTermPerConfigDao = (ITermPerformConfigDao) ctx.getBean("iTermPerConfigDao");
							termPerform = iTermPerConfigDao.findTermPerform().getTermPerform();
							termPerform = Integer.toBinaryString(Integer.parseInt(termPerform, 16));
							termPerform = WDStringUtil.paddingHeadZero(termPerform, 24);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "获取终端性能参数出错!", pm.getString("mv.testdata.InfoWindow"), JOptionPane.ERROR_MESSAGE);
							return;
						}
						// 获取终端支持的AID列表
						IAppInfoDao appInfoDao = (IAppInfoDao) SpringUtil.getBean("appInfoDao");
						List<AppInfo> aidlist = appInfoDao.findAppInfo();
						// 判断终端性能
						termSupportUtil = new TermSupportUtil(termPerform, aidlist);
						// 判断是否支持接触式IC
						if (termSupportUtil.isSupportTheFunction(TerminalSupportType.TOUCHIC)) {
							touchSupport = true;
						} else {
							touchSupport = false;
						}
						electronicCashHandler = (ElectronicCashHandler) SpringUtil.getBean("electronicCashHandler");
						// 读卡器驱动名称
						String readerName =Config.getValue("Terminal_Data", "reader");
						// 交易金额
						int tradeMount = 0;
						try {
							// 将交易金额转换为int型
							tradeMount = getTradeAmount(money.toString());
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, e1.getMessage(), pm.getString("mv.testdata.InfoWindow"), JOptionPane.ERROR_MESSAGE);
							return;
						}
						if ("qPBOC".equals(tradeType)) {
							tradingSet(tradeType, money);
							cancelButton.setEnabled(false);
							qPBOCButton.setEnabled(false);
							lendButton.setEnabled(false);
							earmarkButton.setEnabled(false);
							ecashButton.setEnabled(false);
							if (termSupportUtil.isSupportTheFunction(TerminalSupportType.SUPPORTDDA)) {
								// 执行交易
								qpbocHandler = (QPBOCHandler) SpringUtil.getBean("qPbocHandler");
								success = qpbocHandler.trade(readerName, tradeMount, termSupportUtil, tradingLabel);
							} else {
								tradingLabel.setText("终端不支持DDA!");
								success = false;
							}
						} else if ("借贷记".equals(tradeType)) {
							if (!touchSupport) {
								JOptionPane.showMessageDialog(null, "终端不支持接触式IC,交易无法进行!", pm.getString("mv.testdata.InfoWindow"), JOptionPane.ERROR_MESSAGE);
								return;
							}
							tradingSet(tradeType, money);
							cancelButton.setEnabled(false);
							qPBOCButton.setEnabled(false);
							lendButton.setEnabled(false);
							earmarkButton.setEnabled(false);
							ecashButton.setEnabled(false);
							if (termSupportUtil.isSupportTheFunction(TerminalSupportType.SUPPORTDDA)) {
								// 执行交易
								pBOCHandler = (PBOCHandler) SpringUtil.getBean("pbocHandler");
								success = pBOCHandler.doTrade(tradeMount, readerName, tradingLabel, termSupportUtil);
							} else {
								tradingLabel.setText("终端不支持DDA!");
								success = false;
							}
						} else if ("电子现金".equals(tradeType)) {
							if (!touchSupport) {
								JOptionPane.showMessageDialog(null, "终端不支持接触式IC,交易无法进行!", pm.getString("mv.testdata.InfoWindow"), JOptionPane.ERROR_MESSAGE);
								return;
							}
							tradingSet(tradeType, money);
							cancelButton.setEnabled(false);
							qPBOCButton.setEnabled(false);
							lendButton.setEnabled(false);
							earmarkButton.setEnabled(false);
							ecashButton.setEnabled(false);
							if (termSupportUtil.isSupportTheFunction(TerminalSupportType.SUPPORTDDA)) {
								// 执行交易
								success = electronicCashHandler.ECPurcharse(tradeMount, readerName, tradingLabel, termSupportUtil);
							} else {
								tradingLabel.setText("终端不支持DDA!");
								success = false;
							}
						} else if ("圈存".equals(tradeType)) {
							if (!touchSupport) {
								JOptionPane.showMessageDialog(null, "终端不支持接触式IC,交易无法进行!", pm.getString("mv.testdata.InfoWindow"), JOptionPane.ERROR_MESSAGE);
								return;
							}
							tradingSet(tradeType, money);
							cancelButton.setEnabled(false);
							qPBOCButton.setEnabled(false);
							lendButton.setEnabled(false);
							earmarkButton.setEnabled(false);
							ecashButton.setEnabled(false);
							if (termSupportUtil.isSupportTheFunction(TerminalSupportType.SUPPORTDDA)) {
								// 执行交易
								success = electronicCashHandler.ECLoad(tradeMount, readerName, tradingLabel, termSupportUtil);
							} else {
								tradingLabel.setText("终端不支持DDA!");
								success = false;
							}
						}
						faceThread.addListener(new FaceListener() {
							@Override
							public void UIOperate() {
								try {
									Thread.sleep(2000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (success) {
									trading = pm.getString("mv.tradepanel.tradeSuccess");
									tradingLabel.setText(trading);
									tradingLabel.repaint();
									tradeType = "";
									money.delete(0, money.length());
								} else {
									tradeResult = pm.getString("mv.tradepanel.tradeFail");
									tradingLabel.setText(tradeResult);
									tradingLabel.repaint();
									tradeType = "";
									money.delete(0, money.length());
								}
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								tradingLabel.setVisible(false);
								welcomLabel.setVisible(true);
								cancelButton.setEnabled(true);
								qPBOCButton.setEnabled(true);
								lendButton.setEnabled(true);
								earmarkButton.setEnabled(true);
								ecashButton.setEnabled(true);
								reportButton.setEnabled(true);
							}
						});
						faceThread.start();
					} else {
						JOptionPane.showMessageDialog(null, pm.getString("mv.tradepanel.PlsEnterMoney"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				welcomLabel.setVisible(true);
				tradeTypeLabel.setVisible(false);
				tradeTypeField.setVisible(false);
				enterMoneyLabel.setVisible(false);
				moneyTextField.setVisible(false);
				tradingLabel.setVisible(false);
			}
		});

	}

	public void tradingSet(String tradeType, StringBuffer money) {
		welcomLabel.setVisible(false);
		tradeTypeLabel.setVisible(false);
		tradeTypeField.setVisible(false);
		enterMoneyLabel.setVisible(false);
		moneyTextField.setVisible(false);
		tradingLabel.setVisible(true);
		trading = "<html>您的交易类型为:" + tradeType + "，<br> " + "交易金额为:" + money.toString() + "，<br>交易正在进行请稍候...</html>";
		tradingLabel.setText(trading);
	}

	public static boolean decimalDigitsLimit(String moneyStr) {
		String eg = "^[0-9]{1,3}([.]{1}[0-9]{0,2})?$";
		Matcher m = Pattern.compile(eg, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(moneyStr);
		return m.find() ? true : false;

	}

	/**
	 * 将界面上文本框中输入数据转换为int型
	 * 
	 * @param tradeAmount
	 * @return
	 * @throws Exception
	 */
	public int getTradeAmount(String tradeAmount) throws Exception {
		if (tradeAmount.indexOf(".") > -1) {
			String amountFront = tradeAmount.substring(0, tradeAmount.indexOf("."));
			String amountTail = tradeAmount.substring(tradeAmount.indexOf(".") + 1);
			if (amountTail.length() == 1) {
				amountTail = amountTail + "0";
			}
			try {
				return Integer.parseInt(amountFront + amountTail);
			} catch (NumberFormatException e) {
				throw new Exception("输入的交易金额超出了最大限额");
			}
		} else {
			try {
				return Integer.parseInt(tradeAmount + "00");
			} catch (NumberFormatException e) {
				throw new Exception("输入的交易金额超出了最大限额");
			}
		}
	}

	/*
	 * public static void main(String[]args){ System.out.println(decimalDigitsLimit("2333.")); }
	 */

	public static void setTradeType(String tradeType) {
		money.delete(0, money.length());
		moneyTextField.setText(money.toString());
		welcomLabel.setVisible(false);
		tradeTypeLabel.setVisible(true);
		tradeTypeField.setVisible(true);
		enterMoneyLabel.setVisible(true);
		moneyTextField.setVisible(true);
		tradeTypeField.setText(tradeType);
	}

	public static void setTradeTypeStr(String tradeType) {
		TradePanel.tradeType = tradeType;
	}
}
