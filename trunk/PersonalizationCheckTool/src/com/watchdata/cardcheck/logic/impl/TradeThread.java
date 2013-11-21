package com.watchdata.cardcheck.logic.impl;

import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import com.watchdata.cardcheck.configdao.AIDInfo;
import com.watchdata.cardcheck.configdao.TermInfo;
import com.watchdata.cardcheck.log.Log;
import com.watchdata.cardcheck.logic.Constants;
import com.watchdata.cardcheck.logic.apdu.CommonAPDU;
import com.watchdata.cardcheck.logic.apdu.CommonHelper;
import com.watchdata.cardcheck.panel.AtmPanel.TerminalSupportType;
import com.watchdata.cardcheck.utils.Config;
import com.watchdata.cardcheck.utils.PropertiesManager;
import com.watchdata.cardcheck.utils.TermSupportUtil;
import com.watchdata.commons.lang.WDAssert;
import com.watchdata.commons.lang.WDStringUtil;

public class TradeThread implements Runnable {
	private static Log logger = new Log();
	public StringBuffer money;
	public JTextPane textPane;
	public JTextPane readlogTextPane;
	public String tradeType;
	public JLabel tradingLabel;
	public JButton reportButton;

	private boolean success = false;
	private PropertiesManager pm = new PropertiesManager();

	public TermInfo termInfo = new TermInfo();

	public TradeThread(StringBuffer money, String tradeType, JLabel tradingLabel, JButton reportButton, JTextPane textPane,JTextPane readlogTextPane) {
		this.money = money;
		this.tradeType = tradeType;
		this.reportButton = reportButton;
		this.textPane = textPane;
		this.tradingLabel = tradingLabel;
		this.readlogTextPane=readlogTextPane;
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
				if ("qPBOC".equals(tradeType)) {
					// tradingSet(tradeType, money);
					if (termSupportUtil.isSupportTheFunction(TerminalSupportType.SUPPORTDDA)) {
						// 执行交易
						QPBOCHandler qpbocHandler = new QPBOCHandler(textPane);
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
					// tradingSet(tradeType, money);
					if (termSupportUtil.isSupportTheFunction(TerminalSupportType.SUPPORTDDA)) {
						// 执行交易
						PBOCHandler pBOCHandler = new PBOCHandler(textPane);
						success = pBOCHandler.doTrade(tradeMount, readerName, tradingLabel, termSupportUtil);
					} else {
						tradingLabel.setText("终端不支持DDA!");
						success = false;
					}
				} else if ("电子现金".equals(tradeType)) {
					ElectronicCashHandler electronicCashHandler = new ElectronicCashHandler(textPane);
					if (!touchSupport) {
						JOptionPane.showMessageDialog(null, "终端不支持接触式IC,交易无法进行!", pm.getString("mv.testdata.InfoWindow"), JOptionPane.ERROR_MESSAGE);
						return;
					}
					// tradingSet(tradeType, money);
					if (termSupportUtil.isSupportTheFunction(TerminalSupportType.SUPPORTDDA)) {
						// 执行交易
						success = electronicCashHandler.ECPurcharse(tradeMount, readerName, tradingLabel, termSupportUtil);
					} else {
						tradingLabel.setText("终端不支持DDA!");
						success = false;
					}
				} else if ("圈存".equals(tradeType)) {
					ElectronicCashHandler electronicCashHandler = new ElectronicCashHandler(textPane);
					if (!touchSupport) {
						JOptionPane.showMessageDialog(null, "终端不支持接触式IC,交易无法进行!", pm.getString("mv.testdata.InfoWindow"), JOptionPane.ERROR_MESSAGE);
						return;
					}
					// tradingSet(tradeType, money);
					if (termSupportUtil.isSupportTheFunction(TerminalSupportType.SUPPORTDDA)) {
						// 执行交易
						success = electronicCashHandler.ECLoad(tradeMount, readerName, tradingLabel, termSupportUtil);
					} else {
						tradingLabel.setText("终端不支持DDA!");
						success = false;
					}
				}
				showReadTradeLog();
				reportButton.setEnabled(true);
			}
		}

	}
	
	public void showReadTradeLog(){
		// TODO Auto-generated method stub
		logger.setLogArea(readlogTextPane);
		CommonAPDU apduHandler=new CommonAPDU();
		// 为了保证卡片和读卡器的正确性，交易开始前务必先复位
		logger.debug("=============================reset===================================",0);
		HashMap<String, String> res = apduHandler.reset(Config.getValue("Terminal_Data", "reader"));
		if (!"9000".equals(res.get("sw"))) {
			logger.error("card reset falied");
		}
		logger.debug("atr:" + res.get("atr"));

		logger.debug("============================select PSE=================================");
		HashMap<String, String> result = apduHandler.select(Constants.PSE);
		if (!Constants.SW_SUCCESS.equalsIgnoreCase(result.get("sw"))) {
			logger.error("select PSE error,card return:" + result.get("sw"));
		}

		if (WDAssert.isNotEmpty(result.get("88"))) {
			// read dir, begin from 01
			logger.debug("==============================read dir================================");
			List<HashMap<String, String>> readDirList = apduHandler.readDir(result.get("88"));

			// select aid
			String aid = readDirList.get(0).get("4F");
			logger.debug("===============================select aid==============================");
			if (WDAssert.isEmpty(aid)) {
				logger.error("select aid is null");
			}
			result = apduHandler.select(aid);
			
			if (!Constants.SW_SUCCESS.equalsIgnoreCase(result.get("sw"))) {
				logger.error("select aid error,card return:" + result.get("sw"));
			}
			String tag9f4d=result.get("9F4D");
			if (WDAssert.isEmpty(tag9f4d)) {
				logger.error("logentry is not exists.:" + result.get("sw"));
			}
			result=apduHandler.getData("9F4F");
			String strLog=result.get("res");
			strLog=strLog.substring(6,strLog.length()-4);
			List<String> tlList=CommonHelper.parseTLDataCommon(strLog);
			
			String sfi=tag9f4d.substring(0,2);
			String logCount=tag9f4d.substring(2);
			for (int i = 1; i <=Integer.parseInt(logCount, 16); i++) {
				logger.debug("===============================readlog=============================="+sfi+WDStringUtil.paddingHeadZero(Integer.toHexString(i),2));
				HashMap<String, String> readList= apduHandler.readDirCommon(sfi,WDStringUtil.paddingHeadZero(Integer.toHexString(i), 2));
				if (!readList.get("sw").equalsIgnoreCase("9000")) {
					break;
				}
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
