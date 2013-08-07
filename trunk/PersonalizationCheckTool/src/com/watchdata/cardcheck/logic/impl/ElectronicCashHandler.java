package com.watchdata.cardcheck.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;

import org.apache.log4j.NDC;

import com.watchdata.cardcheck.log.Log;
import com.watchdata.cardcheck.logic.Constants;
import com.watchdata.cardcheck.logic.apdu.AbstractAPDU;
import com.watchdata.cardcheck.logic.apdu.CommonHelper;
import com.watchdata.cardcheck.logic.issuer.IIssuerDao;
import com.watchdata.cardcheck.logic.pki.DataAuthenticate;
import com.watchdata.cardcheck.utils.PropertiesManager;
import com.watchdata.cardcheck.utils.SpringUtil;
import com.watchdata.cardcheck.utils.TermSupportUtil;
import com.watchdata.cardcheck.utils.reportutil.APDUSendANDRes;
import com.watchdata.cardcheck.utils.reportutil.GenReportUtil;
import com.watchdata.commons.lang.WDAssert;
import com.watchdata.commons.lang.WDStringUtil;

public class ElectronicCashHandler extends BaseHandler {

	private static Log logger = new Log();
	private IIssuerDao issuerDao;

	public void setIssuerDao(IIssuerDao issuerDao) {
		this.issuerDao = issuerDao;
	}

	private APDUSendANDRes apduSendANDRes = null;
	private GenReportUtil genWordUtil = null;

	private PropertiesManager pm = new PropertiesManager();

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
	public boolean ECLoad(int tradeMount, String readerName, JLabel tradeLabel, TermSupportUtil termSupportUtil) {
		// issuerDao = (IIssuerDao) SpringUtil.getBean("issuerDao");
		// 初始化交易参数，如授权金额，pin等
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("9F02", WDStringUtil.paddingHeadZero(String.valueOf(tradeMount), 12));
		NDC.push("[E cash earmark]");
		logger.setLogDialogOn();
		logger.debug("E cash earmark start...",0);

		genWordUtil = new GenReportUtil();
		apduSendANDRes = new APDUSendANDRes();

		genWordUtil.open(pm.getString("mv.tradepanel.earmark"));
		genWordUtil.addFileTitle("圈存交易检测报告");
		genWordUtil.addTransactionName("电子现金圈存");

		try {
			// 为了保证卡片和读卡器的正确性，交易开始前务必先复位
			HashMap<String, String> res = apduHandler.reset(readerName);
			if (!"9000".equals(res.get("sw"))) {
				logger.error("Card Reset falied");
				tradeLabel.setText("      卡片复位失败！");
				genWordUtil.add("卡片复位失败");
				genWordUtil.close();
				return false;
			}
			// 复位报告内容
			apduSendANDRes.setSendAPDU("atr");
			apduSendANDRes.setDes("Card Reset");
			apduSendANDRes.setResponseAPDU(res.get("atr"));
			apduSendANDRes.setTagDes(new HashMap<String, String>());
			genWordUtil.add(apduSendANDRes);

			// 选择PSE应用
			HashMap<String, String> result = apduHandler.select(Constants.PSE);
			if (!Constants.SW_SUCCESS.equalsIgnoreCase(result.get("sw"))) {
				logger.error("ElectronicCashHandler ECLoad select PSE error,card return:" + result.get("sw"));
				genWordUtil.add("选择PSE出错");
				genWordUtil.close();
				return false;
			}

			// 选择pse报告内容
			apduSendANDRes.setSendAPDU(result.get("apdu"));
			apduSendANDRes.setDes("Select PSE");
			apduSendANDRes.setResponseAPDU(result.get("res"));
			apduSendANDRes.setTagDes(result);
			genWordUtil.add(apduSendANDRes);

			if (WDAssert.isNotEmpty(result.get("88"))) {
				// read dir, begin from 01
				List<HashMap<String, String>>  readDirList = apduHandler.readDir(result.get("88"));
				// select aid
				String aid = readDirList.get(0).get("4F");
				if (WDAssert.isEmpty(aid)) {
					logger.error("select aid is null");
					tradeLabel.setText("      获取aid为空！");
					genWordUtil.add("获取AID为空");
					genWordUtil.close();
					return false;
				}
				if (termSupportUtil.isSupportAID(aid)) {
					result = apduHandler.select(aid);
				} else {
					logger.error("Terminal can not support the app");
					tradeLabel.setText("      终端不支持此应用！");
					genWordUtil.add("终端不支持此应用");
					genWordUtil.close();
					return false;
				}
				if (!"9000".equals(result.get("sw"))) {
					logger.error("select app get response:" + result.get("sw"));
					tradeLabel.setText("      选择应用返回:" + result.get("sw"));
					genWordUtil.add("选择应用出错");
					genWordUtil.close();
					return false;
				}

				// 选择aid报告内容
				apduSendANDRes.setSendAPDU(result.get("apdu"));
				apduSendANDRes.setDes("Select AID");
				apduSendANDRes.setResponseAPDU(result.get("res"));
				apduSendANDRes.setTagDes(result);
				genWordUtil.add(apduSendANDRes);

				logger.info("=================================GET DATA=================================");
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
					tradeLabel.setText("交易金额大于单笔交易金额上限!");
					logger.error("ElectronicCashHandler ECLoad  single tradeMount is larger than the single top limit!");
					genWordUtil.add("交易金额大于单笔交易金额上限");
					genWordUtil.close();
					return false;
				}
				if (tradeMount + Integer.parseInt(balance) > Integer.parseInt(limit)) {
					tradeLabel.setText("现有余额与交易金额之和大于电子现金余额上限!");
					logger.error("balance plus trademount is larger than the top limit");
					genWordUtil.add("现有余额与交易金额之和大于电子现金余额上限");
					genWordUtil.close();
					return false;
				}
				// GET DATA 报告
				/*
				 * apduSendANDRes.setSendAPDU(reportNoreqAndRes); apduSendANDRes.setDes("GET DATA"); apduSendANDRes.setResponseAPDU(reportNoreqAndRes); apduSendANDRes.setTagDes(dataMap); genWordUtil.add(apduSendANDRes);
				 */
				genWordUtil.add("PDOL Data:" + pdol);
				genWordUtil.add("电子现金账户上限:" + limit);
				genWordUtil.add("单笔交易上限:" + singleLimit);
				genWordUtil.add("电子现金账户余额:" + balance);
				logger.info("=================================GPO=================================");
				String loadDolDataResult = "";
				try {
					loadDolDataResult = loadDolData(pdol, param);
				} catch (Exception e) {
					tradeLabel.setText("获取DDOL数据出错!");
					logger.error("ElectronicCashHandler ECLoad get ddol param exception!");
					genWordUtil.add("获取DDOL数据出错");
					genWordUtil.close();
					return false;
				}
				result = apduHandler.gpo("83" + CommonHelper.getLVData(loadDolDataResult, 1));
				String aip = result.get("82");

				// GPO报告
				apduSendANDRes.setSendAPDU(result.get("apdu"));
				apduSendANDRes.setDes("GPO");
				apduSendANDRes.setResponseAPDU(result.get("res"));
				apduSendANDRes.setTagDes(result);
				genWordUtil.add(apduSendANDRes);
				genWordUtil.add("LoadDolDataResult:" + loadDolDataResult);
				genWordUtil.add("AIP:" + aip);
				logger.info("=================================Read Record=================================");
				String staticDataList = "";
				ArrayList<String> fileList = CommonHelper.parseAppFileLocation(result.get("94"));
				HashMap<String, String> cardRecordData = new HashMap<String, String>();
				/*
				 * for(String fileId:fileList){ HashMap<String,String> fileData = apduHandler.readRecord(fileId.substring(0, 2), fileId.substring(2,4)); //静态数据列表 if(fileId.indexOf("T")>0){ staticDataList = fileData.get("70"); } CommonHelper.copyMapData(cardRecordData,fileData); }
				 */
				// Read Record 报告
				List<APDUSendANDRes> aList = new ArrayList<APDUSendANDRes>();
				cardRecordData = getCardRecordData(result.get("94"), aList);
				staticDataList = cardRecordData.get("staticDataList");

				// 读记录报告
				for (APDUSendANDRes apduSendANDRes2 : aList) {
					genWordUtil.add(apduSendANDRes2);
				}

				// 联机请求
				logger.info("=================================Internal Authenticate=================================");
				String termRandom = WDStringUtil.getRandomHexString(8);
				result = apduHandler.internalAuthenticate(termRandom);
				// 内部认证报告
				apduSendANDRes.setSendAPDU(result.get("apdu"));
				apduSendANDRes.setDes("Internal Authenticate");
				apduSendANDRes.setResponseAPDU(result.get("res"));
				apduSendANDRes.setTagDes(result);
				genWordUtil.add(apduSendANDRes);
				genWordUtil.add("Random Data:" + termRandom);
				genWordUtil.add("StaticDataList:" + staticDataList);
				logger.info("=================================DDA=================================");
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
					genWordUtil.close();
					return false;
				}
				// 动态数据认证报告
				/*
				 * apduSendANDRes.setSendAPDU(reportNoreqAndRes); apduSendANDRes.setDes("DDA"); apduSendANDRes.setResponseAPDU(reportNoreqAndRes); apduSendANDRes.setTagDes(new HashMap<String,String>()); genWordUtil.add(apduSendANDRes);
				 */
				/*
				 * genWordUtil.add("SignedDynmicData:" + signedDynmicData); genWordUtil.add("DdolDataList:" + ddolDataList); genWordUtil.add("IssuerPKCert:" + issuerPKCert); genWordUtil.add("IssuerPKReminder:" + issuerPKReminder); genWordUtil.add("IssuerPKExponent:" + issuerPKExponent); genWordUtil.add("IcPKCert:" + icPKCert); genWordUtil.add("IcPKExp:" + icPKExp); genWordUtil.add("IcPKReminder:" + icPKReminder); genWordUtil.add("CaPKIndex:" + caPKIndex); genWordUtil.add("StaticDataList:" + staticDataList); genWordUtil.add("Pan:" + pan); genWordUtil.add("Rid:" + rid);
				 */
				genWordUtil.add("DDA中使用的数据如下");
				for (String log : logList) {
					genWordUtil.add(log);
				}
				logger.info("=================================Generate AC1=================================");
				param.put("9F37", termRandom);
				Date dateTime = new Date();
				param.put("9A", getFormatDate(dateTime, Constants.FORMAT_SHORT_DATE));
				param.put("9F21", getFormatDate(dateTime, Constants.FORMAT_TIME));
				String cdol1Data = loadDolData(cardRecordData.get("8C"), param);// 9F0206 9F0306 9F1A02 9505 5F2A02 9A03 9C01 9F3704 9F2103 9F4E14
				result = apduHandler.generateAC(cdol1Data, AbstractAPDU.P1_ARQC);
				// Generate AC1 报告
				apduSendANDRes.setSendAPDU(result.get("apdu"));
				apduSendANDRes.setDes("Generate AC1");
				apduSendANDRes.setResponseAPDU(result.get("res"));
				apduSendANDRes.setTagDes(result);
				genWordUtil.add(apduSendANDRes);
				genWordUtil.add("CCDOL1 Data:" + cdol1Data);
				logger.info("=================================验证ARQC=================================");
				String arqc = result.get("9F26");
				String atc = result.get("9F36");
				String iad = result.get("9F10");
				String arpc = issuerDao.requestArpc(pan, panSerial, cdol1Data, aip, atc, iad, arqc);
				// ARQC报告
				/*
				 * apduSendANDRes.setSendAPDU(reportNoreqAndRes); apduSendANDRes.setDes("Check ARQC"); apduSendANDRes.setResponseAPDU(reportNoreqAndRes); apduSendANDRes.setTagDes(new HashMap<String,String>()); genWordUtil.add(apduSendANDRes);
				 */
				genWordUtil.add("验证ARQC中使用的数据");
				genWordUtil.add("ARQC:" + arqc);
				genWordUtil.add("ATC:" + atc);
				genWordUtil.add("IAD:" + iad);
				genWordUtil.add("ARPC:" + arpc);
				logger.info("=================================外部认证=================================");
				result = apduHandler.externalAuthenticate(arpc + authRespCode);
				if (!Constants.SW_SUCCESS.equalsIgnoreCase(result.get("sw"))) {
					logger.error("ElectronicCashHandler ECLoad external Authenticate failed,card return:" + result.get("sw"));
					genWordUtil.add("外部认证失败");
					genWordUtil.close();
					return false;
				}
				// 外部认证报告
				apduSendANDRes.setSendAPDU(result.get("apdu"));
				apduSendANDRes.setDes("External Authenticate");
				apduSendANDRes.setResponseAPDU(result.get("res"));
				apduSendANDRes.setTagDes(result);
				genWordUtil.add(apduSendANDRes);
				logger.info("=================================Generate AC2=================================");
				param.put("8A", authRespCode);
				String cdol2Data = loadDolData(cardRecordData.get("8D"), param);
				result = apduHandler.generateAC(cdol2Data, AbstractAPDU.P1_TC);
				// Generate AC2报告
				apduSendANDRes.setSendAPDU(result.get("apdu"));
				apduSendANDRes.setDes("Generate AC2");
				apduSendANDRes.setResponseAPDU(result.get("res"));
				apduSendANDRes.setTagDes(result);
				genWordUtil.add(apduSendANDRes);
				genWordUtil.add("CCDOL2 Data:" + cdol2Data);
				logger.info("=================================PUT DATA=================================");
				String[] script = issuerDao.generateLoadIssuerScript(pan, panSerial, atc, arqc, balance, tradeMount);
				String issuerScript = script[0];
				// 交易成功
				result = apduHandler.putData(issuerScript);
				// PUT DATA 报告
				apduSendANDRes.setSendAPDU(result.get("apdu"));
				apduSendANDRes.setDes("PUT DATA");
				apduSendANDRes.setResponseAPDU(result.get("res"));
				apduSendANDRes.setTagDes(result);
				genWordUtil.add(apduSendANDRes);
				genWordUtil.add("Issuer Script:" + issuerScript);

				result = apduHandler.getData("9F79");
				genWordUtil.add("电子现金账户余额：:" + result.get("9F79"));
				logger.info("=================================E cash earmark Finished=================================");
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
	public boolean ECPurcharse(int tradeMount, String readerName, JLabel tradeLabel, TermSupportUtil termSupportUtil) {
		// issuerDao = (IIssuerDao) SpringUtil.getBean("issuerDao");
		// 初始化交易参数，如授权金额，pin等
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("9F02", WDStringUtil.paddingHeadZero(String.valueOf(tradeMount), 12));
		NDC.push("[ElectronicCash ECPurcharse]");
		logger.setLogDialogOn();
		logger.debug("ElectronicCash ECPurcharse start...", 0);
		genWordUtil = new GenReportUtil();
		apduSendANDRes = new APDUSendANDRes();

		genWordUtil.open(pm.getString("mv.tradepanel.ecash"));
		genWordUtil.addFileTitle("消费交易检测报告");
		genWordUtil.addTransactionName("电子现金消费");

		try {
			// 为了保证卡片和读卡器的正确性，交易开始前务必先复位
			HashMap<String, String> res = apduHandler.reset(readerName);
			if (!"9000".equals(res.get("sw"))) {
				logger.error("Card Reset falied");
				tradeLabel.setText("      卡片复位失败!");
				genWordUtil.add("卡片复位失败");
				genWordUtil.close();
				return false;
			}

			// 复位报告内容
			apduSendANDRes.setSendAPDU("atr");
			apduSendANDRes.setDes("Card Reset");
			apduSendANDRes.setResponseAPDU(res.get("atr"));
			apduSendANDRes.setTagDes(new HashMap<String, String>());
			genWordUtil.add(apduSendANDRes);

			// 选择PSE应用
			HashMap<String, String> result = apduHandler.select(Constants.PSE);
			if (!Constants.SW_SUCCESS.equalsIgnoreCase(result.get("sw"))) {
				logger.error("ElectronicCashHandler ECPurcharse select PSE error,card return:" + result.get("sw"));
				tradeLabel.setText("选择PSE出错");
				genWordUtil.add("选择PSE出错");
				genWordUtil.close();
				return false;
			}

			// 选择pse报告内容
			apduSendANDRes.setSendAPDU(result.get("apdu"));
			apduSendANDRes.setDes("Select PSE");
			apduSendANDRes.setResponseAPDU(result.get("res"));
			apduSendANDRes.setTagDes(result);
			genWordUtil.add(apduSendANDRes);

			if (WDAssert.isNotEmpty(result.get("88"))) {
				// read dir, begin from 01
				List<HashMap<String, String>>  readDirList = apduHandler.readDir(result.get("88"));
				// select aid
				String aid = readDirList.get(0).get("4F");
				if (WDAssert.isEmpty(aid)) {
					logger.error("get AID null!");
					tradeLabel.setText("      获取aid为空！");
					genWordUtil.add("获取AID为空");
					genWordUtil.close();
					return false;
				}
				if (termSupportUtil.isSupportAID(aid)) {
					result = apduHandler.select(aid);
				} else {
					logger.error("Terminal can not support the App");
					tradeLabel.setText("      终端不支持此应用！");
					genWordUtil.add("终端不支持此应用");
					genWordUtil.close();
					return false;
				}
				if (!"9000".equals(result.get("sw"))) {
					logger.error("select app get response" + result.get("sw"));
					tradeLabel.setText("      选择应用返回:" + result.get("sw"));
					genWordUtil.add("选择应用出错");
					genWordUtil.close();
					return false;
				}

				// 选择aid报告内容
				apduSendANDRes.setSendAPDU(result.get("apdu"));
				apduSendANDRes.setDes("Select AID");
				apduSendANDRes.setResponseAPDU(result.get("res"));
				apduSendANDRes.setTagDes(result);
				genWordUtil.add(apduSendANDRes);

				logger.info("=================================GET DATA=================================");
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
					tradeLabel.setText("交易金额大于单笔交易金额上限");
					genWordUtil.add("交易金额大于单笔交易金额上限");
					genWordUtil.close();
					return false;
				}

				/*
				 * apduSendANDRes.setSendAPDU(reportNoreqAndRes); apduSendANDRes.setDes("GET DATA"); apduSendANDRes.setResponseAPDU(reportNoreqAndRes); apduSendANDRes.setTagDes(dataMap); genWordUtil.add(apduSendANDRes);
				 */
				genWordUtil.add("PDOL Data:" + pdol);
				genWordUtil.add("单笔交易上限:" + singleLimit);
				genWordUtil.add("电子现金账户余额:" + balance);
				logger.info("=================================GPO=================================");
				String loadDolDataResult = "";
				try {
					loadDolDataResult = loadDolData(pdol, param);
				} catch (Exception e) {
					tradeLabel.setText("获取DDOL数据出错!");
					logger.error("ElectronicCashHandler ECPurcharse get ddol param exception!");
					genWordUtil.add("获取DDOL数据出错");
					genWordUtil.close();
					return false;
				}
				result = apduHandler.gpo("83" + CommonHelper.getLVData(loadDolDataResult, 1));
				String aip = result.get("82");

				apduSendANDRes.setSendAPDU(result.get("apdu"));
				apduSendANDRes.setDes("GPO");
				apduSendANDRes.setResponseAPDU(result.get("res"));
				apduSendANDRes.setTagDes(result);
				genWordUtil.add(apduSendANDRes);
				genWordUtil.add("LoadDolDataResult:" + loadDolDataResult);
				genWordUtil.add("AIP:" + aip);
				logger.info("=================================Read Record=================================");
				String staticDataList = "";
				ArrayList<String> fileList = CommonHelper.parseAppFileLocation(result.get("94"));
				HashMap<String, String> cardRecordData = new HashMap<String, String>();
				/*
				 * for(String fileId:fileList){ HashMap<String,String> fileData = apduHandler.readRecord(fileId.substring(0, 2), fileId.substring(2,4)); //静态数据列表 if(fileId.indexOf("T")>0){ staticDataList = fileData.get("70"); } CommonHelper.copyMapData(cardRecordData,fileData); }
				 */
				List<APDUSendANDRes> aList = new ArrayList<APDUSendANDRes>();
				cardRecordData = getCardRecordData(result.get("94"), aList);
				staticDataList = cardRecordData.get("staticDataList");
				// 读记录报告
				for (APDUSendANDRes apduSendANDRes2 : aList) {
					genWordUtil.add(apduSendANDRes2);
				}
				logger.info("=================================Internal Authenticate=================================");
				String termRandom = WDStringUtil.getRandomHexString(8);
				result = apduHandler.internalAuthenticate(termRandom);

				apduSendANDRes.setSendAPDU(result.get("apdu"));
				apduSendANDRes.setDes("Internal Authenticate");
				apduSendANDRes.setResponseAPDU(result.get("res"));
				apduSendANDRes.setTagDes(result);
				genWordUtil.add(apduSendANDRes);
				genWordUtil.add("RandomData:" + termRandom);
				genWordUtil.add("StaticDataList:" + staticDataList);
				logger.info("=================================DDA=================================");
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
					tradeLabel.setText("动态数据认证失败!");
					genWordUtil.add("动态数据认证失败");
					genWordUtil.close();
					return false;
				}

				/*
				 * apduSendANDRes.setSendAPDU(reportNoreqAndRes); apduSendANDRes.setDes("DDA"); apduSendANDRes.setResponseAPDU(reportNoreqAndRes); apduSendANDRes.setTagDes(new HashMap<String,String>()); genWordUtil.add(apduSendANDRes);
				 */
				/*
				 * genWordUtil.add("SignedDynmicData:" + signedDynmicData); genWordUtil.add("DdolDataList:" + ddolDataList); genWordUtil.add("IssuerPKCert:" + issuerPKCert); genWordUtil.add("IssuerPKReminder:" + issuerPKReminder); genWordUtil.add("IssuerPKExponent:" + issuerPKExponent); genWordUtil.add("IcPKCert:" + icPKCert); genWordUtil.add("IcPKExp:" + icPKExp); genWordUtil.add("IcPKReminder:" + icPKReminder); genWordUtil.add("CaPKIndex:" + caPKIndex); genWordUtil.add("StaticDataList:" + staticDataList); genWordUtil.add("Pan:" + pan); genWordUtil.add("Rid:" + rid);
				 */
				genWordUtil.add("DDA中使用到的数据");
				for (String log : logList) {
					genWordUtil.add(log);
				}

				Date dateTime = new Date();
				param.put("9F37", termRandom);
				param.put("9A", getFormatDate(dateTime, Constants.FORMAT_SHORT_DATE));
				param.put("9F21", getFormatDate(dateTime, Constants.FORMAT_TIME));
				// 判断是否需要联机
				if (Integer.parseInt(param.get("9F02")) > Integer.parseInt(balance)) {
					logger.info("=================================联机Generate AC1=================================");
					String cdol1Data = loadDolData(cardRecordData.get("8C"), param);// 9F0206 9F0306 9F1A02 9505 5F2A02 9A03 9C01 9F3704 9F2103 9F4E14
					result = apduHandler.generateAC(cdol1Data, AbstractAPDU.P1_ARQC);

					apduSendANDRes.setSendAPDU(result.get("apdu"));
					apduSendANDRes.setDes("OnLine Generate AC1");
					apduSendANDRes.setResponseAPDU(result.get("res"));
					apduSendANDRes.setTagDes(result);
					genWordUtil.add(apduSendANDRes);
					genWordUtil.add("CCDOL1 Data:" + cdol1Data);
					logger.info("=================================联机验证ARQC=================================");
					String arqc = result.get("9F26");
					String atc = result.get("9F36");
					String iad = result.get("9F10");
					String arpc = issuerDao.requestArpc(pan, panSerial, cdol1Data, aip, atc, iad, arqc);

					/*
					 * apduSendANDRes.setSendAPDU(reportNoreqAndRes); apduSendANDRes.setDes("OnLine Check ARQC"); apduSendANDRes.setResponseAPDU(reportNoreqAndRes); apduSendANDRes.setTagDes(new HashMap<String,String>()); genWordUtil.add(apduSendANDRes);
					 */
					genWordUtil.add("验证ARQC中使用的数据");
					genWordUtil.add("ARQC:" + arqc);
					genWordUtil.add("ATC:" + atc);
					genWordUtil.add("IAD:" + iad);
					genWordUtil.add("ARPC:" + arpc);
					logger.info("=================================联机外部认证=================================");
					result = apduHandler.externalAuthenticate(arpc + authRespCode);
					if (!Constants.SW_SUCCESS.equalsIgnoreCase(result.get("sw"))) {
						logger.error("ElectronicCashHandler ECPurcharse external Authenticate failed,card return:" + result.get("sw"));
						tradeLabel.setText("联机外部认证失败!");
						genWordUtil.add("联机外部认证失败");
						genWordUtil.close();
						return false;
					}
					apduSendANDRes.setSendAPDU(result.get("apdu"));
					apduSendANDRes.setDes("OnLine External Authenticate");
					apduSendANDRes.setResponseAPDU(result.get("res"));
					apduSendANDRes.setTagDes(result);
					genWordUtil.add(apduSendANDRes);
					logger.info("=================================联机Generate AC2=================================");
					param.put("8A", authRespCode);
					String cdol2Data = loadDolData(cardRecordData.get("8D"), param);
					result = apduHandler.generateAC(cdol2Data, AbstractAPDU.P1_TC);

					apduSendANDRes.setSendAPDU(result.get("apdu"));
					apduSendANDRes.setDes("OnLine Generate AC2");
					apduSendANDRes.setResponseAPDU(result.get("res"));
					apduSendANDRes.setTagDes(result);
					genWordUtil.add(apduSendANDRes);
					genWordUtil.add("CCDOL2 Data:" + cdol2Data);
				} else {
					logger.info("=================================脱机Generate AC1=================================");
					String cdol1Data = loadDolData(cardRecordData.get("8C"), param);// 9F0206 9F0306 9F1A02 9505 5F2A02 9A03 9C01 9F3704 9F2103 9F4E14
					result = apduHandler.generateAC(cdol1Data, AbstractAPDU.P1_TC);

					apduSendANDRes.setSendAPDU(result.get("apdu"));
					apduSendANDRes.setDes("OffLine Generate AC1");
					apduSendANDRes.setResponseAPDU(result.get("res"));
					apduSendANDRes.setTagDes(result);
					genWordUtil.add(apduSendANDRes);
					genWordUtil.add("CCDOL1 Data:" + cdol1Data);
				}

				result = apduHandler.getData("9F79");
				genWordUtil.add("电子现金账户余额:" + result.get("9F79"));
				logger.info("=================================Finished=================================");
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
		}
	}

	public static void main(String[] args) {
		// Generic EMV Smartcard Reader 0
		// Watchdata W5181 Contact Reader 0
		// WatchData CRW-V Plus PC/SC Reader 0
		ElectronicCashHandler electronicCashHandler = (ElectronicCashHandler) SpringUtil.getBean("electronicCashHandler");
		// electronicCashHandler.ECLoad(100,"Watchdata W5181 Contact Reader  0",null);
		// electronicCashHandler.ECPurcharse(136,"Watchdata W5181 Contact Reader  0");
	}
}