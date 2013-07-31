package com.watchdata.cardcheck.logic.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.watchdata.cardcheck.logic.apdu.CommonAPDU;
import com.watchdata.cardcheck.logic.apdu.CommonHelper;
import com.watchdata.cardcheck.logic.issuer.IIssuerDao;
import com.watchdata.cardcheck.utils.reportutil.APDUSendANDRes;
import com.watchdata.commons.lang.WDAssert;

/**
 * 
 * @description: 交易逻辑基类
 * @author: juan.jiang Apr 10, 2012
 * @version: 1.0.0
 * @modify:
 * @Copyright: watchdata
 */
public class BaseHandler {
	public static HashMap<String, String> tradeDefaultPara;
	public static String authRespCode = "3030";
	public CommonAPDU apduHandler;
	protected IIssuerDao issuerDao;

	public BaseHandler() {
		if (tradeDefaultPara == null) {
			tradeDefaultPara = new HashMap<String, String>();
			tradeDefaultPara.put("9F7A", "01"); // 电子现金终端指示器
			tradeDefaultPara.put("5F2A", "0156"); // 交易货币代码
			tradeDefaultPara.put("9F1A", "0156"); // 终端国家代码
			tradeDefaultPara.put("9F66", "B6000000");// 非接触能力
			tradeDefaultPara.put("9F03", "000000000000");// 其他金额
			tradeDefaultPara.put("9F4E", "ABEDF8D910F0CBD8EBEDF8D910F0CBD8E5A81BA0");// 商户名称
			tradeDefaultPara.put("95", "0000000000");// 终端验证结果（TVR）
			tradeDefaultPara.put("9C", "40");
		}
	}

	/**
	 * 装配gpo数据
	 * 
	 * @param pdol
	 * @param param
	 * @return
	 * @throws Exception
	 */
	protected String loadDolData(String dol, HashMap<String, String> param) throws Exception {
		StringBuffer result = new StringBuffer();
		ArrayList<String> tagList = CommonHelper.parseTLData(dol);
		for (String tag : tagList) {
			if (WDAssert.isNotEmpty(param.get(tag))) {
				result.append(param.get(tag));
			} else if (WDAssert.isNotEmpty(tradeDefaultPara.get(tag))) {
				result.append(tradeDefaultPara.get(tag));
			} else {
				throw new Exception("lack dol param of tag[" + tag + "]");
			}
		}
		return result.toString();
	}

	/**
	 * 获取卡片应用文件记录数据
	 * 
	 * @param afl
	 * @return
	 * @throws Exception
	 */
	protected HashMap<String, String> getCardRecordData(String afl, List<APDUSendANDRes> list) throws Exception {
		//填充目的集合用于复制
		List<APDUSendANDRes> aList = new ArrayList<APDUSendANDRes>();
		for (int i = 0; i < CommonHelper.getFileCount(afl); i++) {
			list.add(null);
		}
		// read record
		String staticDataList = "";
		ArrayList<String> fileList = CommonHelper.parseAppFileLocation(afl);
		HashMap<String, String> cardRecordData = new HashMap<String, String>();
		for (String fileId : fileList) {
			HashMap<String, String> fileData = apduHandler.readRecord(fileId.substring(0, 2), fileId.substring(2, 4));
			
			// Read Record报告内容
			APDUSendANDRes apduSendANDRes = new APDUSendANDRes();
			apduSendANDRes.setSendAPDU(fileData.get("apdu"));
			apduSendANDRes.setDes("Read Record_DGI"+CommonHelper.getDgiHead(fileId.substring(0, 2))+fileId.substring(2, 4));
			apduSendANDRes.setResponseAPDU(fileData.get("res"));
			apduSendANDRes.setTagDes(fileData);
			aList.add(apduSendANDRes);

			// 静态数据列表
			if (fileId.indexOf("T") > 0) {
				staticDataList = fileData.get("70");
			}
			CommonHelper.copyMapData(cardRecordData, fileData);
		}
		// 复制集合到输出内存
		Collections.copy(list, aList);

		cardRecordData.put("staticDataList", staticDataList);
		return cardRecordData;
	}

	public void setApduHandler(CommonAPDU apduHandler) {
		this.apduHandler = apduHandler;
	}

	protected String getFormatDate(Date date, String format) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
		return simpledateformat.format(date);
	}

	public void setIssuerDao(IIssuerDao issuerDao) {
		this.issuerDao = issuerDao;
	}

}
