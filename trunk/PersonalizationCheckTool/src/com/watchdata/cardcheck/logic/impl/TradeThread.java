package com.watchdata.cardcheck.logic.impl;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import com.watchdata.cardcheck.configdao.AIDInfo;
import com.watchdata.cardcheck.configdao.TermInfo;
import com.watchdata.cardcheck.panel.AtmPanel.TerminalSupportType;
import com.watchdata.cardcheck.utils.Config;
import com.watchdata.cardcheck.utils.PropertiesManager;
import com.watchdata.cardcheck.utils.TermSupportUtil;
import com.watchdata.commons.lang.WDStringUtil;

public class TradeThread implements Runnable {
	public StringBuffer money;
	public JTextPane textPane;
	public String tradeType;
	public JLabel tradingLabel;
	public JButton reportButton;

	private boolean success = false;
	private PropertiesManager pm = new PropertiesManager();

	public TermInfo termInfo = new TermInfo();

	public TradeThread(StringBuffer money, String tradeType, JLabel tradingLabel, JButton reportButton, JTextPane textPane) {
		this.money = money;
		this.tradeType = tradeType;
		this.reportButton = reportButton;
		this.textPane = textPane;
		this.tradingLabel = tradingLabel;
	}

	@Override
	public void run() {
		if ("".equals(tradeType)) {
			JOptionPane.showMessageDialog(null, pm.getString("mv.tradepanel.selectTradeType"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
		} else {
			if (money.length() > 0) {
				// 获取终端性能参数
				String termPerform = "";
				boolean touchSupport = false;
				try {
					termPerform = termInfo.getTermInfo("Terminal_Data").getTerminal_perform();
					termPerform = Integer.toBinaryString(Integer.parseInt(termPerform, 16));
					termPerform = WDStringUtil.paddingHeadZero(termPerform, 24);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "获取终端性能参数出错!", pm.getString("mv.testdata.InfoWindow"), JOptionPane.ERROR_MESSAGE);
					return;
				}
				// 获取终端支持的AID列表
				List<AIDInfo> aidlist = new AIDInfo().getAidInfos("SupAID");
				// 判断终端性能
				TermSupportUtil termSupportUtil = new TermSupportUtil(termPerform, aidlist);
				// 判断是否支持接触式IC
				if (termSupportUtil.isSupportTheFunction(TerminalSupportType.TOUCHIC)) {
					touchSupport = true;
				} else {
					touchSupport = false;
				}
				// 读卡器驱动名称
				String readerName = Config.getValue("Terminal_Data", "reader");
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
				if (termSupportUtil.isSupportTheFunction(TerminalSupportType.SUPPORTDDA)) {
					if ("qPBOC".equals(tradeType)) {
						// 执行交易
						QPBOCHandler qpbocHandler = new QPBOCHandler(textPane);
						success = qpbocHandler.trade(readerName, tradeMount, termSupportUtil, tradingLabel);
					} else {
						if (!touchSupport) {
							JOptionPane.showMessageDialog(null, "终端不支持接触式IC,交易无法进行!", pm.getString("mv.testdata.InfoWindow"), JOptionPane.ERROR_MESSAGE);
							return;
						}
						if ("借贷记".equals(tradeType)) {
							PBOCHandler pBOCHandler = new PBOCHandler(textPane);
							success = pBOCHandler.doTrade(tradeMount, readerName, tradingLabel, termSupportUtil);
						} else if ("电子现金".equals(tradeType)) {
							ElectronicCashHandler electronicCashHandler = new ElectronicCashHandler(textPane);
							success = electronicCashHandler.ECPurcharse(tradeMount, readerName, tradingLabel, termSupportUtil);
						} else if ("圈存".equals(tradeType)) {
							ElectronicCashHandler electronicCashHandler = new ElectronicCashHandler(textPane);
							success = electronicCashHandler.ECLoad(tradeMount, readerName, tradingLabel, termSupportUtil);
						}
					}
					success=true;
				} else {
					tradingLabel.setText("终端不支持DDA!");
					success = false;
				}
				reportButton.setEnabled(true);
			}
		}

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

}
