package com.gerenhua.tool.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JTextPane;

import org.apache.log4j.NDC;

import com.gerenhua.tool.log.Log;
import com.gerenhua.tool.logic.Constants;
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

/**
 * QPBOC交易流程
 * 
 * @author liya.xiao
 * 
 */
public class QPBOCHandler extends BaseHandler {
	private static Log logger = new Log();
	private IIssuerDao issuerDao=new IssuerDaoImpl();
	private PropertiesManager pm = new PropertiesManager();
	
	public QPBOCHandler(JTextPane textPane){
		logger.setLogArea(textPane);
	}

	public boolean trade(String readerName, int tradeMount,TermSupportUtil termSupportUtil){
		//生成交易检测报告
		GenReportUtil genWordUtil=new GenReportUtil();
		//打开报告文档
		genWordUtil.open(pm.getString("mv.tradepanel.qPBOC"));
		genWordUtil.addFileTitle("交易检测报告");
		genWordUtil.addTransactionName("QPBOC");
		
		// 参数
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("9F02", WDStringUtil.paddingHeadZero(String.valueOf(tradeMount), 12));
		//param.put("9C", "40");//商品
		Date dateTime = new Date();
		param.put("9A",getFormatDate(dateTime,Constants.FORMAT_SHORT_DATE));
		param.put("9F21",getFormatDate(dateTime,Constants.FORMAT_TIME));
		String termRandom = WDStringUtil.getRandomHexString(8);
		param.put("9F37", termRandom);//终端随机数
		param.put("9F66", "2A000000");// 非接触能力
		NDC.push("[QPBOC]");
		logger.debug("QPBOC start...",0);
		try{
			// 复位
			logger.debug("===============================reset=====================================");
			HashMap<String,String> res=apduHandler.reset();
			if (!"9000".equals(res.get("sw"))) {
				genWordUtil.add("卡片复位失败！");
				genWordUtil.close();
				return false;
			}
			//复位报告内容
			genWordUtil.add("atr", "card ATR", res.get("atr"), new HashMap<String, String>());
			
			// 选择PPSE
			logger.debug("============================select PPSE=====================================");
			HashMap<String, String> result = apduHandler.select(Constants.PPSE);
			if (!"9000".equals(result.get("sw"))) {
				return false;
			}
			
			//选择ppse报告内容
			genWordUtil.add(result.get("apdu"), "select PPSE", result.get("res"), result);
			
			// 选择AID
			logger.debug("============================select AID=====================================");
			String aid=result.get("4F");
			if (WDAssert.isEmpty(aid)) {
				return false;
			}
			if (termSupportUtil.isSupportAID(aid)) {
				result = apduHandler.select(aid);
			}else {
				genWordUtil.add("终端不支持此应用！");
				genWordUtil.close();
				return false;
			}
			if (!"9000".equals(result.get("sw"))) {
				genWordUtil.add("选择应用返回:"+result.get("sw"));
				genWordUtil.close();
				return false;
			}
			
			//选择aid报告内容
			genWordUtil.add(result.get("apdu"), "select AID", result.get("res"), result);
			
			logger.debug("============================Balance=====================================");
			apduHandler.getData("9F79");
			
			// GPO如果pdol不存在 发8300
			logger.debug("===================================GPO=====================================");
			String pdolData = loadDolData(result.get("9F38"), param);
			result = apduHandler.gpo("83" + CommonHelper.getLVData(pdolData, 1));
			if (!"9000".equals(result.get("sw"))) {
				genWordUtil.add("GPO返回:"+result.get("sw"));
				genWordUtil.close();
				return false;
			}
			
			//GPO报告内容
			genWordUtil.add(result.get("apdu"), "GPO", result.get("res"), result);
			
			String aip = result.get("82");
			if (WDAssert.isEmpty(aip)) {
				return false;
			}
			
			//判断是否联机或脱机
			String cvrString = result.get("9F10").substring(8,10);
			if("80".equalsIgnoreCase(cvrString)){//AAC
				logger.debug("==============================card reject trade,failed!=============================");
				//报告
				genWordUtil.add("card reject trade,failed!");
				return false;
			}else if("90".equalsIgnoreCase(cvrString)){//TC
				genWordUtil.add("Card Accepted Offline Line Transaction.");
				// read record
				logger.debug("====================================read record=====================================");
				List<APDUSendANDRes> aList=new ArrayList<APDUSendANDRes>();
				HashMap<String,String> cardRecordData = getCardRecordData(result.get("94"),aList);
				String staticDataList = cardRecordData.get("staticDataList");
				
				//读记录报告
				for (APDUSendANDRes apduSendANDRes2 : aList) {
					genWordUtil.add(apduSendANDRes2);
				}
				
				//DDA,SDA	
				logger.debug("=====================================DDA validate=====================================");
				String issuerPKCert = cardRecordData.get("90");//发卡行公钥(IPK)证书
				String issuerPKReminder = cardRecordData.get("92");//发卡行公钥余数
				String issuerPKExp = cardRecordData.get("9F32");//发卡行公钥指数
				//String signedStaticData = cardRecordData.get("93");
				String icPKCert = cardRecordData.get("9F46");//IC卡公钥证书
				String icPKExp = cardRecordData.get("9F47");//IC卡公钥指数
				String icPKReminder = cardRecordData.get("9F48");//IC卡公钥余数
				String caPKIndex = cardRecordData.get("8F");//CA公钥索引(PKI)
				staticDataList += aip;
				String pan = cardRecordData.get("5A");
				pan = pan.replaceAll("F", "");//应用主帐户(PAN)			
				String signedDynmicData = cardRecordData.get("9F4B");//签名的动态数据
				String rid = aid.substring(0, 10);
				
				DataAuthenticate dataAuthenticate= new DataAuthenticate(rid, caPKIndex, issuerPKCert,
						issuerPKReminder, issuerPKExp, pan, staticDataList);
				List<String> logList=new ArrayList<String>();
				if(!dataAuthenticate.dynamicDataAuthenticate(icPKCert, icPKReminder, icPKExp, signedDynmicData, termRandom,logList)){
					logger.error("DDA failed!" );
					genWordUtil.add("DDA失败！");
					genWordUtil.close();
					return false;
				}
				//打印dda报告
				for (String string : logList) {
					genWordUtil.add(string);
				}
				genWordUtil.add("check DDA PASS.");
			}else if("A0".equalsIgnoreCase(cvrString)){//ARQC
				//报告
				genWordUtil.add("online validate!");
				logger.debug("============================online validata=====================================");
				//请求发卡行认证AC密文
				String arqc = result.get("9F26");
				String atc = result.get("9F36");
				String iad = result.get("9F10");
				String pan = result.get("57").substring(0,result.get("57").indexOf("D"));
				String panSerial = result.get("5F34");
				
				String arpc = issuerDao.requestArpc(pan, panSerial, pdolData.substring(8), aip, atc, iad, arqc);
				logger.debug("ARPC[" + arpc + "]");
				genWordUtil.add("ARPC[" + arpc + "]");
			}
			logger.debug("============================Balance=====================================");
			result = apduHandler.getData("9F79");
			logger.debug("============================QPBOC trade finished!=====================================");
			//报告
			genWordUtil.add("QPBOC trade finished!");
			return true;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			genWordUtil.add(e.getMessage());
			return false;
		}finally{
			genWordUtil.close();
			NDC.pop();
			NDC.remove();
			//apduHandler.close();
		}
	}

}
