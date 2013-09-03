package com.watchdata.cardcheck.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import org.apache.log4j.NDC;

import com.watchdata.cardcheck.log.Log;
import com.watchdata.cardcheck.logic.Constants;
import com.watchdata.cardcheck.logic.apdu.AbstractAPDU;
import com.watchdata.cardcheck.logic.apdu.CommonHelper;
import com.watchdata.cardcheck.logic.issuer.IIssuerDao;
import com.watchdata.cardcheck.logic.issuer.local.IssuerDaoImpl;
import com.watchdata.cardcheck.logic.pki.DataAuthenticate;
import com.watchdata.cardcheck.utils.PropertiesManager;
import com.watchdata.cardcheck.utils.TermSupportUtil;
import com.watchdata.cardcheck.utils.reportutil.APDUSendANDRes;
import com.watchdata.cardcheck.utils.reportutil.GenReportUtil;
import com.watchdata.commons.lang.WDAssert;
import com.watchdata.commons.lang.WDStringUtil;

/**
 * 
 * @description: PBOC交易逻辑处理
 * @author: juan.jiang 2012-3-21
 * @version: 1.0.0
 * @modify:
 * @Copyright: watchdata
 */
public class PBOCHandler extends BaseHandler {
	private static Log logger = new Log();
	private IIssuerDao issuerDao = new IssuerDaoImpl();
	private GenReportUtil genWordUtil = null;

	private PropertiesManager pm = new PropertiesManager();

	public PBOCHandler(JTextPane textPane) {
		logger.setLogArea(textPane);
	}

	/**
	 * @author juan.jiang
	 * @param tradeMount
	 *            交易金额
	 * @param readerName
	 *            读卡器名称
	 * @param tradeLabel
	 *            交易界面显示详细控件
	 * @param termSupportUtil
	 *            判断终端性能
	 * @return
	 */
	public boolean doTrade(int tradeMount, String readerName, JLabel tradeLabel, TermSupportUtil termSupportUtil) {
		// 初始化交易参数，如授权金额，pin等
		HashMap<String, String> param = new HashMap<String, String>();
		String termRandom = WDStringUtil.getRandomHexString(8);
		param.put("9F02", WDStringUtil.paddingHeadZero(String.valueOf(tradeMount), 12));
		// param.put("9C","40");
		param.put("9F7A", "00");
		param.put("9F37", termRandom);
		Date dateTime = new Date();
		param.put("9A", getFormatDate(dateTime, Constants.FORMAT_SHORT_DATE));
		param.put("9F21", getFormatDate(dateTime, Constants.FORMAT_TIME));
		param.put("9F66", "46800000");// 非接触能力
		NDC.push("[PBOC]");
		logger.debug("PBOC trade start...", 0);
		genWordUtil = new GenReportUtil();

		genWordUtil.open(pm.getString("mv.tradepanel.lend"));
		genWordUtil.addFileTitle("PBOC交易检测报告");
		genWordUtil.addTransactionName("PBOC");

		try {
			// 为了保证卡片和读卡器的正确性，交易开始前务必先复位
			logger.debug("=============================reset===================================");
			HashMap<String, String> res = apduHandler.reset(readerName);
			if (!"9000".equals(res.get("sw"))) {
				logger.error("card reset falied");
				tradeLabel.setText("      卡片复位失败！");
				genWordUtil.add("卡片复位失败");
				// genWordUtil.close();
				return false;
			}
			logger.debug("atr:" + res.get("atr"));
			// 复位报告内容
			genWordUtil.add("atr", "Card Reset", res.get("atr"), new HashMap<String, String>());

			logger.debug("============================select PSE=================================");
			HashMap<String, String> result = apduHandler.select(Constants.PSE);
			if (!Constants.SW_SUCCESS.equalsIgnoreCase(result.get("sw"))) {
				logger.error("select PSE error,card return:" + result.get("sw"));
				tradeLabel.setText("      选择PSE出错！");
				genWordUtil.add("选择PSE出错");
				// genWordUtil.close();
				return false;
			}

			// 选择ppse报告内容
			genWordUtil.add(result.get("apdu"), "Select PSE", result.get("res"), result);

			if (WDAssert.isNotEmpty(result.get("88"))) {
				// read dir, begin from 01
				logger.debug("==============================read dir================================");
				List<HashMap<String, String>> readDirList = apduHandler.readDir(result.get("88"));

				// select aid
				String aid = readDirList.get(0).get("4F");
				logger.debug("===============================select aid==============================");
				if (WDAssert.isEmpty(aid)) {
					logger.error("select aid is null");
					tradeLabel.setText("      获取aid为空！");
					genWordUtil.add("获取AID为空");
					// genWordUtil.close();
					return false;
				}
				if (termSupportUtil.isSupportAID(aid)) {
					result = apduHandler.select(aid);
				} else {
					logger.error("Terminal can not support the app");
					tradeLabel.setText("      终端不支持此应用！");
					genWordUtil.add("终端不支持此应用");
					// genWordUtil.close();
					return false;
				}
				if (!"9000".equals(result.get("sw"))) {
					logger.error("select app get response:" + result.get("sw"));
					tradeLabel.setText("      选择应用返回:" + result.get("sw"));
					genWordUtil.add("选择应用出错");
					// genWordUtil.close();
					return false;
				}
				String pdol = result.get("9F38");

				// 选择aid报告内容
				genWordUtil.add(result.get("apdu"), "Select AID", result.get("res"), result);

				// get data
				logger.debug("================================get data=============================");
				HashMap<String, String> dataMap = new HashMap<String, String>();
				result = apduHandler.getData("9F52");
				dataMap.put("9F52", result.get("9F52"));
				result = apduHandler.getData("9F54");
				dataMap.put("9F54", result.get("9F54"));
				result = apduHandler.getData("9F5C");
				dataMap.put("9F5C", result.get("9F5C"));
				result = apduHandler.getData("9F56");
				dataMap.put("9F56", result.get("9F56"));
				result = apduHandler.getData("9F57");
				dataMap.put("9F57", result.get("9F57"));
				result = apduHandler.getData("9F58");
				dataMap.put("9F58", result.get("9F58"));
				result = apduHandler.getData("9F59");
				dataMap.put("9F59", result.get("9F59"));

				genWordUtil.add("PDOL Data:" + pdol);
				// gpo
				logger.debug("==================================gpo==================================");
				String loadDolDataResult = "";
				try {
					loadDolDataResult = loadDolData(pdol, param);
				} catch (Exception e) {
					tradeLabel.setText("获取DDOL数据出错!");
					logger.error("PBOC get ddol param exception!");
					genWordUtil.add("获取DDOL数据出错");
					// genWordUtil.close();
					return false;
				}
				result = apduHandler.gpo("83" + CommonHelper.getLVData(loadDolDataResult, 1));
				String aip = result.get("82");

				genWordUtil.add(result.get("apdu"), "GPO", result.get("res"), result);

				genWordUtil.add("LoadDolDataResult:" + loadDolDataResult);
				genWordUtil.add("AIP:" + aip);

				// read record
				logger.debug("=================================read record===========================");
				List<APDUSendANDRes> aList = new ArrayList<APDUSendANDRes>();
				HashMap<String, String> cardRecordData = getCardRecordData(result.get("94"), aList);
				String staticDataList = cardRecordData.get("staticDataList");

				// 读记录报告
				for (APDUSendANDRes apduSendANDRes2 : aList) {
					genWordUtil.add(apduSendANDRes2);
				}
				// Verify PIN
				if (WDAssert.isNotEmpty(cardRecordData.get("8E"))) {
					if (CommonHelper.parse8E(cardRecordData.get("8E"))) {
						logger.debug("=================================Verify PIN===========================");
						String pin = JOptionPane.showInputDialog("请输入PIN：");
						if (WDAssert.isNotEmpty(pin)) {
							result = apduHandler.verifyPin(pin);
							if (!Constants.SW_SUCCESS.equalsIgnoreCase(result.get("sw"))) {
								logger.error("verify pin failed,card return:" + result.get("sw"));
								genWordUtil.add(result.get("apdu"), "Verify PIN", result.get("res"), result);
							} else {
								logger.debug("verify pin pass!");
								genWordUtil.add(result.get("apdu"), "Verify PIN", result.get("res"), result);
							}
						}else {
							logger.error("verify pin failed,card return:" + result.get("sw"));
						}
					}
				}

				// Internal Authenticate
				logger.debug("=======================internal Authenticate==============================");
				result = apduHandler.internalAuthenticate(termRandom);
				String signedDynmicData = result.get("80");

				genWordUtil.add(result.get("apdu"), "Internal Authenticate", result.get("res"), result);

				genWordUtil.add("Random Data:" + termRandom);
				genWordUtil.add("StaticDataList:" + staticDataList);
				// DDA,SDA
				logger.debug("===========================DDA validate===============================");
				String issuerPKCert = cardRecordData.get("90");
				String issuerPKReminder = cardRecordData.get("92");
				String issuerPKExp = cardRecordData.get("9F32");
				// String signedStaticData = cardRecordData.get("93");
				String icPKCert = cardRecordData.get("9F46");
				String icPKExp = cardRecordData.get("9F47");
				String icPKReminder = cardRecordData.get("9F48");
				String caPKIndex = cardRecordData.get("8F");
				staticDataList += aip;
				String pan = cardRecordData.get("5A");
				pan = pan.replaceAll("F", "");
				String panSerial = cardRecordData.get("5F34");
				String rid = aid.substring(0, 10);

				DataAuthenticate dataAuthenticate = new DataAuthenticate(rid, caPKIndex, issuerPKCert, issuerPKReminder, issuerPKExp, pan, staticDataList);
				List<String> logList = new ArrayList<String>();
				if (!dataAuthenticate.dynamicDataAuthenticate(icPKCert, icPKReminder, icPKExp, signedDynmicData, termRandom, logList)) {
					logger.error("DDA failed!");
					tradeLabel.setText("动态数据认证失败!");
					genWordUtil.add("动态数据认证失败");
					// genWordUtil.close();
					return false;
				}
				logger.debug("DDA validate successed!");

				genWordUtil.add("DDA中使用的数据");
				for (String log : logList) {
					genWordUtil.add(log);
				}
				// Generate arqc
				logger.debug("==========================Generate AC1================================");
				String cdol1Data = loadDolData(cardRecordData.get("8C"), param);// 9F0206 9F0306 9F1A02 9505 5F2A02 9A03 9C01 9F3704 9F2103 9F4E14
				result = apduHandler.generateAC(cdol1Data, AbstractAPDU.P1_ARQC);

				genWordUtil.add(result.get("apdu"), "Generate AC1", result.get("res"), result);

				genWordUtil.add("CDOL1 Data:" + cdol1Data);

				String arqc = result.get("9F26");
				String atc = result.get("9F36");
				String iad = result.get("9F10");

				logger.debug("=================================ARQC=================================");
				String arpc = issuerDao.requestArpc(pan, panSerial, cdol1Data, aip, atc, iad, arqc);
				logger.debug("online validate successed!");

				genWordUtil.add("验证ARQC中使用的数据");
				genWordUtil.add("ARQC:" + arqc);
				genWordUtil.add("ATC:" + atc);
				genWordUtil.add("IAD:" + iad);
				genWordUtil.add("ARPC:" + arpc);
				// 请求发卡行认证AC密文
				logger.debug("=======================external Authenticate============================");
				result = apduHandler.externalAuthenticate(arpc + authRespCode);
				if (!Constants.SW_SUCCESS.equalsIgnoreCase(result.get("sw"))) {
					logger.error("external Authenticate failed,card return:" + result.get("sw"));
					tradeLabel.setText("外部认证失败!");
					genWordUtil.add("外部认证失败");
					// genWordUtil.close();
					return false;
				}

				genWordUtil.add(result.get("apdu"), "External Authenticate", result.get("res"), result);

				// Generate tc
				logger.debug("===========================Generate AC2===========================");
				param.put("8A", authRespCode);
				String cdol2Data = loadDolData(cardRecordData.get("8D"), param);
				result = apduHandler.generateAC(cdol2Data, AbstractAPDU.P1_TC);

				genWordUtil.add(result.get("apdu"), "Generate AC2", result.get("res"), result);
				genWordUtil.add("CDOL2 Data:" + cdol2Data);
				logger.debug("========================PBOC trade finished!=======================");
				genWordUtil.add("PBOC交易完成!");
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			genWordUtil.add(e.getMessage());
			return false;
		} finally {
			genWordUtil.close();
			NDC.pop();
			NDC.remove();
			apduHandler.close();
		}

	}
}
