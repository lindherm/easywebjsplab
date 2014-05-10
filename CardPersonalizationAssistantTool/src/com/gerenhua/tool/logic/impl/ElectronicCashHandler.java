package com.gerenhua.tool.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import org.apache.log4j.NDC;

import com.gerenhua.tool.log.Log;
import com.gerenhua.tool.logic.Constants;
import com.gerenhua.tool.logic.apdu.AbstractAPDU;
import com.gerenhua.tool.logic.apdu.CommonHelper;
import com.gerenhua.tool.logic.issuer.IIssuerDao;
import com.gerenhua.tool.logic.issuer.local.IssuerDaoImpl;
import com.gerenhua.tool.logic.pki.DataAuthenticate;
import com.gerenhua.tool.utils.PropertiesManager;
import com.gerenhua.tool.utils.TermSupportUtil;
import com.gerenhua.tool.utils.reportutil.APDUSendANDRes;
import com.gerenhua.tool.utils.reportutil.GenReportUtil;
import com.watchdata.commons.lang.WDAssert;
import com.watchdata.commons.lang.WDStringUtil;

public class ElectronicCashHandler extends BaseHandler {
	private static Log logger = new Log();
	private IIssuerDao issuerDao=new IssuerDaoImpl();
	private GenReportUtil genWordUtil = null;
	private PropertiesManager pm = new PropertiesManager();
	
	public ElectronicCashHandler(JTextPane textPane){
		logger.setLogArea(textPane);
	}
	/**
	 * @author peng.wang
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
	public boolean ECLoad(int tradeMount, String readerName,TermSupportUtil termSupportUtil) {
		// 初始化交易参数，如授权金额，pin等
		HashMap<String, String> param = new HashMap<String, String>();
		String termRandom = WDStringUtil.getRandomHexString(8);
		param.put("9F02", WDStringUtil.paddingHeadZero(String.valueOf(tradeMount), 12));
		param.put("9F37", termRandom);
		Date dateTime = new Date();
		param.put("9A", getFormatDate(dateTime, Constants.FORMAT_SHORT_DATE));
		param.put("9F21", getFormatDate(dateTime, Constants.FORMAT_TIME));
		param.put("9F66", "46800000");// 非接触能力
		NDC.push("[E cash load]");
		logger.debug("E cash load start...",0);

		genWordUtil = new GenReportUtil();

		genWordUtil.open(pm.getString("mv.tradepanel.earmark"));
		genWordUtil.addFileTitle("圈存交易检测报告");
		genWordUtil.addTransactionName("电子现金圈存");

		try {
			// 为了保证卡片和读卡器的正确性，交易开始前务必先复位
			logger.debug("=============================reset===================================");
			HashMap<String, String> res = apduHandler.reset();
			if (!"9000".equals(res.get("sw"))) {
				logger.error("Card Reset falied");
				genWordUtil.add("卡片复位失败");
				//genWordUtil.close();
				return false;
			}
			logger.debug("atr:"+res.get("atr"));
			// 复位报告内容
			genWordUtil.add("atr", "Card Reset", res.get("atr"), new HashMap<String, String>());

			logger.debug("============================select PSE=================================");
			// 选择PSE应用
			HashMap<String, String> result = apduHandler.select(Constants.PSE);
			if (!Constants.SW_SUCCESS.equalsIgnoreCase(result.get("sw"))) {
				logger.error("ElectronicCashHandler ECLoad select PSE error,card return:" + result.get("sw"));
				genWordUtil.add("选择PSE出错");
				//genWordUtil.close();
				return false;
			}
			// 选择pse报告内容
			genWordUtil.add(result.get("apdu"), "Select PSE", result.get("res"), result);

			if (WDAssert.isNotEmpty(result.get("88"))) {
				logger.debug("==============================read dir================================");
				// read dir, begin from 01
				List<HashMap<String, String>>  readDirList = apduHandler.readDir(result.get("88"));
				// select aid
				String aid = readDirList.get(0).get("4F");
				logger.debug("===============================select aid==============================");
				if (WDAssert.isEmpty(aid)) {
					logger.error("select aid is null");
					genWordUtil.add("获取AID为空");
					//genWordUtil.close();
					return false;
				}
				if (termSupportUtil.isSupportAID(aid)) {
					result = apduHandler.select(aid);
				} else {
					logger.error("Terminal can not support the app");
					genWordUtil.add("终端不支持此应用");
					//genWordUtil.close();
					return false;
				}
				if (!"9000".equals(result.get("sw"))) {
					logger.error("select app get response:" + result.get("sw"));
					genWordUtil.add("选择应用出错");
					//genWordUtil.close();
					return false;
				}
				// 选择aid报告内容
				genWordUtil.add(result.get("apdu"), "Select AID", result.get("res"), result);

				// get data
				logger.debug("================================get data=============================");
				HashMap<String, String> dataMap = new HashMap<String, String>();
				String pdol = result.get("9F38");
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
				result = apduHandler.getData("9F77");
				String limit = result.get("9F77");
				result = apduHandler.getData("9F78");
				String singleLimit = result.get("9F78");
				result = apduHandler.getData("9F79");
				String balance = result.get("9F79");
				if (tradeMount > Integer.parseInt(singleLimit)) {
					logger.error("ElectronicCashHandler ECLoad  single tradeMount is larger than the single top limit!");
					genWordUtil.add("交易金额大于单笔交易金额上限");
					//genWordUtil.close();
					return false;
				}
				if (tradeMount + Integer.parseInt(balance) > Integer.parseInt(limit)) {
					logger.error("balance plus trademount is larger than the top limit");
					genWordUtil.add("现有余额与交易金额之和大于电子现金余额上限");
					//genWordUtil.close();
					return false;
				}
				genWordUtil.add("PDOL Data:" + pdol);
				genWordUtil.add("电子现金账户上限:" + limit);
				genWordUtil.add("单笔交易上限:" + singleLimit);
				genWordUtil.add("电子现金账户余额:" + balance);
				
				
				logger.debug("==================================gpo==================================");
				String loadDolDataResult = "";
				try {
					loadDolDataResult = loadDolData(pdol, param);
				} catch (Exception e) {
					logger.error("ElectronicCashHandler ECLoad get ddol param exception!");
					genWordUtil.add("获取DDOL数据出错");
					//genWordUtil.close();
					return false;
				}
				result = apduHandler.gpo("83" + CommonHelper.getLVData(loadDolDataResult, 1));
				String aip = result.get("82");

				// GPO报告
				genWordUtil.add(result.get("apdu"), "GPO", result.get("res"), result);
				
				genWordUtil.add("LoadDolDataResult:" + loadDolDataResult);
				genWordUtil.add("AIP:" + aip);
				
				
				// read record
				logger.debug("=================================read record===========================");
				String staticDataList = "";
				HashMap<String, String> cardRecordData = new HashMap<String, String>();
				
				// Read Record 报告
				List<APDUSendANDRes> aList = new ArrayList<APDUSendANDRes>();
				cardRecordData = getCardRecordData(result.get("94"), aList);
				staticDataList = cardRecordData.get("staticDataList");

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
				
				// 联机请求
				logger.debug("=================================Internal Authenticate=================================");
				result = apduHandler.internalAuthenticate(termRandom);
				// 内部认证报告
				genWordUtil.add(result.get("apdu"), "Internal Authenticate", result.get("res"), result);
				
				genWordUtil.add("Random Data:" + termRandom);
				genWordUtil.add("StaticDataList:" + staticDataList);
				
				
				logger.debug("=================================DDA validate=================================");
				String signedDynmicData = result.get("80");
				String ddolDataList = termRandom;
				String issuerPKCert = cardRecordData.get("90");
				String issuerPKReminder = cardRecordData.get("92");
				String issuerPKExponent = cardRecordData.get("9F32");
				String icPKCert = cardRecordData.get("9F46");
				String icPKExp = cardRecordData.get("9F47");
				String icPKReminder = cardRecordData.get("9F48");
				String caPKIndex = cardRecordData.get("8F");
				staticDataList += aip;
				String pan = cardRecordData.get("5A");
				if (pan.lastIndexOf("F") > -1) {
					pan = pan.substring(0, pan.length() - 1);
				}
				String panSerial = cardRecordData.get("5F34");
				String rid = aid.substring(0, 10);
				DataAuthenticate dataAuthenticate = new DataAuthenticate(rid, caPKIndex, issuerPKCert, issuerPKReminder, issuerPKExponent, pan, staticDataList);
				List<String> logList = new ArrayList<String>();
				if (!dataAuthenticate.dynamicDataAuthenticate(icPKCert, icPKReminder, icPKExp, signedDynmicData, ddolDataList, logList)) {
					logger.error("ElectronicCashHandler ECLoad DDA failed");
					genWordUtil.add("动态数据认证失败");
					//genWordUtil.close();
					return false;
				}
				genWordUtil.add("DDA中使用的数据如下");
				for (String log : logList) {
					genWordUtil.add(log);
				}
				logger.debug("DDA validate successed!");
				
				
				logger.debug("=================================Generate AC1=================================");
				String cdol1Data = loadDolData(cardRecordData.get("8C"), param);// 9F0206 9F0306 9F1A02 9505 5F2A02 9A03 9C01 9F3704 9F2103 9F4E14
				result = apduHandler.generateAC(cdol1Data, AbstractAPDU.P1_ARQC);
				// Generate AC1 报告
				genWordUtil.add(result.get("apdu"), "Generate AC1", result.get("res"), result);
				genWordUtil.add("CCDOL1 Data:" + cdol1Data);
				logger.debug("=================================ARQC=================================");
				String arqc = result.get("9F26");
				String atc = result.get("9F36");
				String iad = result.get("9F10");
				String arpc = issuerDao.requestArpc(pan, panSerial, cdol1Data, aip, atc, iad, arqc);
				logger.debug("online validate successed!");
				
				genWordUtil.add("验证ARQC中使用的数据");
				genWordUtil.add("ARQC:" + arqc);
				genWordUtil.add("ATC:" + atc);
				genWordUtil.add("IAD:" + iad);
				genWordUtil.add("ARPC:" + arpc);
				
				logger.debug("=================================external Authenticate=================================");
				result = apduHandler.externalAuthenticate(arpc + authRespCode);
				if (!Constants.SW_SUCCESS.equalsIgnoreCase(result.get("sw"))) {
					logger.error("ElectronicCashHandler ECLoad external Authenticate failed,card return:" + result.get("sw"));
					genWordUtil.add("外部认证失败");
					//genWordUtil.close();
					return false;
				}
				// 外部认证报告
				genWordUtil.add(result.get("apdu"), "External Authenticate", result.get("res"), result);
				
				logger.debug("=================================Generate AC2=================================");
				param.put("8A", authRespCode);
				String cdol2Data = loadDolData(cardRecordData.get("8D"), param);
				result = apduHandler.generateAC(cdol2Data, AbstractAPDU.P1_TC);
				// Generate AC2报告
				genWordUtil.add(result.get("apdu"), "Generate AC2", result.get("res"), result);
				genWordUtil.add("CCDOL2 Data:" + cdol2Data);
				logger.debug("=================================PUT DATA=================================");
				String[] script = issuerDao.generateLoadIssuerScript(pan, panSerial, atc, arqc, balance, tradeMount);
				String issuerScript = script[0];
				// 交易成功
				result = apduHandler.putData(issuerScript);
				// PUT DATA 报告
				genWordUtil.add(result.get("apdu"), "PUT DATA", result.get("res"), result);

				result = apduHandler.getData("9F79");
				genWordUtil.add("电子现金账户余额：:" + result.get("9F79"));
				logger.debug("=================================E cash load finished=================================");
				genWordUtil.add("电子现金圈存交易完成!");
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			genWordUtil.add(e.getMessage());
			return false;
		} finally {
			// 关闭文档
			genWordUtil.close();
			NDC.pop();
			NDC.remove();
			//apduHandler.close();
		}
	}

	/**
	 * @author peng.wang
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
	public boolean ECPurcharse(int tradeMount, String readerName,TermSupportUtil termSupportUtil) {
		// issuerDao = (IIssuerDao) SpringUtil.getBean("issuerDao");
		// 初始化交易参数，如授权金额，pin等
		HashMap<String, String> param = new HashMap<String, String>();
		String termRandom = WDStringUtil.getRandomHexString(8);
		param.put("9F02", WDStringUtil.paddingHeadZero(String.valueOf(tradeMount), 12));
		Date dateTime = new Date();
		param.put("9F37", termRandom);
		param.put("9A", getFormatDate(dateTime, Constants.FORMAT_SHORT_DATE));
		param.put("9F21", getFormatDate(dateTime, Constants.FORMAT_TIME));
		param.put("9F66", "46800000");// 非接触能力
		NDC.push("[e cash purcharse]");
		logger.debug("e cash purcharse start...", 0);
		
		genWordUtil = new GenReportUtil();
		
		genWordUtil.open(pm.getString("mv.tradepanel.ecash"));
		genWordUtil.addFileTitle("消费交易检测报告");
		genWordUtil.addTransactionName("电子现金消费");

		try {
			logger.debug("=============================reset===================================");
			// 为了保证卡片和读卡器的正确性，交易开始前务必先复位
			HashMap<String, String> res = apduHandler.reset();
			if (!"9000".equals(res.get("sw"))) {
				logger.error("Card Reset falied");
				genWordUtil.add("卡片复位失败");
				genWordUtil.close();
				return false;
			}
			logger.debug("atr:"+res.get("atr"));
			// 复位报告内容
			genWordUtil.add("atr", "Card Reset", res.get("atr"), new HashMap<String, String>());

			logger.debug("============================select PSE=================================");
			// 选择PSE应用
			HashMap<String, String> result = apduHandler.select(Constants.PSE);
			if (!Constants.SW_SUCCESS.equalsIgnoreCase(result.get("sw"))) {
				logger.error("ElectronicCashHandler ECPurcharse select PSE error,card return:" + result.get("sw"));
				genWordUtil.add("选择PSE出错");
				genWordUtil.close();
				return false;
			}

			// 选择pse报告内容
			genWordUtil.add(result.get("apdu"), "Select PSE", result.get("res"), result);

			if (WDAssert.isNotEmpty(result.get("88"))) {
				logger.debug("==============================read dir================================");
				// read dir, begin from 01
				List<HashMap<String, String>>  readDirList = apduHandler.readDir(result.get("88"));
				// select aid
				String aid = readDirList.get(0).get("4F");
				logger.debug("===============================select aid==============================");
				if (WDAssert.isEmpty(aid)) {
					logger.error("get AID null!");
					genWordUtil.add("获取AID为空");
					genWordUtil.close();
					return false;
				}
				if (termSupportUtil.isSupportAID(aid)) {
					result = apduHandler.select(aid);
				} else {
					logger.error("Terminal can not support the App");
					genWordUtil.add("终端不支持此应用");
					genWordUtil.close();
					return false;
				}
				if (!"9000".equals(result.get("sw"))) {
					logger.error("select app get response" + result.get("sw"));
					genWordUtil.add("选择应用出错");
					genWordUtil.close();
					return false;
				}

				// 选择aid报告内容
				genWordUtil.add(result.get("apdu"), "Select AID", result.get("res"), result);

				// get data
				logger.debug("================================get data=============================");
				HashMap<String, String> dataMap = new HashMap<String, String>();
				String pdol = result.get("9F38");
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
				result = apduHandler.getData("9F78");
				String singleLimit = result.get("9F78");
				result = apduHandler.getData("9F79");
				String balance = result.get("9F79");

				if (tradeMount > Integer.parseInt(singleLimit)) {
					logger.error("ElectronicCashHandler ECPurcharse single tradeMount is larger than the single top limit!");
					genWordUtil.add("交易金额大于单笔交易金额上限");
					genWordUtil.close();
					return false;
				}
				genWordUtil.add("PDOL Data:" + pdol);
				genWordUtil.add("单笔交易上限:" + singleLimit);
				genWordUtil.add("电子现金账户余额:" + balance);
				logger.debug("==================================gpo==================================");
				String loadDolDataResult = "";
				try {
					loadDolDataResult = loadDolData(pdol, param);
				} catch (Exception e) {
					logger.error("ElectronicCashHandler ECPurcharse get ddol param exception!");
					genWordUtil.add("获取DDOL数据出错");
					genWordUtil.close();
					return false;
				}
				result = apduHandler.gpo("83" + CommonHelper.getLVData(loadDolDataResult, 1));
				String aip = result.get("82");

				genWordUtil.add(result.get("apdu"), "GPO", result.get("res"), result);
				
				genWordUtil.add("LoadDolDataResult:" + loadDolDataResult);
				genWordUtil.add("AIP:" + aip);
				
				// read record
				logger.debug("=================================read record===========================");
				String staticDataList = "";
				HashMap<String, String> cardRecordData = new HashMap<String, String>();

				List<APDUSendANDRes> aList = new ArrayList<APDUSendANDRes>();
				cardRecordData = getCardRecordData(result.get("94"), aList);
				staticDataList = cardRecordData.get("staticDataList");
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
				
				logger.debug("=======================internal Authenticate==============================");
				result = apduHandler.internalAuthenticate(termRandom);

				genWordUtil.add(result.get("apdu"), "Internal Authenticate", result.get("res"), result);

				genWordUtil.add("RandomData:" + termRandom);
				genWordUtil.add("StaticDataList:" + staticDataList);
				// DDA,SDA
				logger.debug("===========================DDA validate===============================");
				String signedDynmicData = result.get("80");
				String ddolDataList = termRandom;
				String issuerPKCert = cardRecordData.get("90");
				String issuerPKReminder = cardRecordData.get("92");
				String issuerPKExponent = cardRecordData.get("9F32");
				String icPKCert = cardRecordData.get("9F46");
				String icPKExp = cardRecordData.get("9F47");
				String icPKReminder = cardRecordData.get("9F48");
				String caPKIndex = cardRecordData.get("8F");
				staticDataList += aip;
				String pan = cardRecordData.get("5A");
				if (pan.lastIndexOf("F") > -1) {
					pan = pan.substring(0, pan.length() - 1);
				}
				String panSerial = cardRecordData.get("5F34");
				String rid = aid.substring(0, 10);

				DataAuthenticate dataAuthenticate = new DataAuthenticate(rid, caPKIndex, issuerPKCert, issuerPKReminder, issuerPKExponent, pan, staticDataList);
				List<String> logList = new ArrayList<String>();
				if (!dataAuthenticate.dynamicDataAuthenticate(icPKCert, icPKReminder, icPKExp, signedDynmicData, ddolDataList, logList)) {
					logger.error("ElectronicCashHandler ECPurcharse DDA failed");
					genWordUtil.add("动态数据认证失败");
					genWordUtil.close();
					return false;
				}
				logger.debug("DDA validate successed!");

				genWordUtil.add("DDA中使用到的数据");
				for (String log : logList) {
					genWordUtil.add(log);
				}
				// 判断是否需要联机
				if (Integer.parseInt(param.get("9F02")) > Integer.parseInt(balance)) {
					
					logger.debug("=================================Generate AC1=================================");
					String cdol1Data = loadDolData(cardRecordData.get("8C"), param);// 9F0206 9F0306 9F1A02 9505 5F2A02 9A03 9C01 9F3704 9F2103 9F4E14
					result = apduHandler.generateAC(cdol1Data, AbstractAPDU.P1_ARQC);

					genWordUtil.add(result.get("apdu"), "Generate AC1", result.get("res"), result);
					
					genWordUtil.add("CCDOL1 Data:" + cdol1Data);
					
					logger.debug("=================================ARQC=================================");
					String arqc = result.get("9F26");
					String atc = result.get("9F36");
					String iad = result.get("9F10");
					String arpc = issuerDao.requestArpc(pan, panSerial, cdol1Data, aip, atc, iad, arqc);
					
					logger.debug("online validate successed!");
					genWordUtil.add("验证ARQC中使用的数据");
					genWordUtil.add("ARQC:" + arqc);
					genWordUtil.add("ATC:" + atc);
					genWordUtil.add("IAD:" + iad);
					genWordUtil.add("ARPC:" + arpc);
					
					logger.debug("=================================联机外部认证=================================");
					result = apduHandler.externalAuthenticate(arpc + authRespCode);
					if (!Constants.SW_SUCCESS.equalsIgnoreCase(result.get("sw"))) {
						logger.error("ElectronicCashHandler ECPurcharse external Authenticate failed,card return:" + result.get("sw"));
						genWordUtil.add("联机外部认证失败");
						genWordUtil.close();
						return false;
					}
					genWordUtil.add(result.get("apdu"), "联机外部认证", result.get("res"), result);
					
					logger.debug("=================================联机Generate AC2=================================");
					param.put("8A", authRespCode);
					String cdol2Data = loadDolData(cardRecordData.get("8D"), param);
					result = apduHandler.generateAC(cdol2Data, AbstractAPDU.P1_TC);

					genWordUtil.add(result.get("apdu"), "联机Generate AC2", result.get("res"), result);
					genWordUtil.add("CCDOL2 Data:" + cdol2Data);
				} else {
					logger.debug("=================================脱机Generate AC1=================================");
					String cdol1Data = loadDolData(cardRecordData.get("8C"), param);// 9F0206 9F0306 9F1A02 9505 5F2A02 9A03 9C01 9F3704 9F2103 9F4E14
					result = apduHandler.generateAC(cdol1Data, AbstractAPDU.P1_TC);

					genWordUtil.add(result.get("apdu"), "脱机Generate AC1", result.get("res"), result);
					genWordUtil.add("CCDOL1 Data:" + cdol1Data);
				}

				result = apduHandler.getData("9F79");
				genWordUtil.add("电子现金账户余额:" + result.get("9F79"));
				logger.debug("=================================e cash purcharse finished=================================");
				genWordUtil.add("电子现金消费交易完成!");
				
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			genWordUtil.add(e.getMessage());
			return false;
		} finally {
			// 关闭文档
			genWordUtil.close();
			NDC.pop();
			NDC.remove();
			//apduHandler.close();
		}
	}
}
