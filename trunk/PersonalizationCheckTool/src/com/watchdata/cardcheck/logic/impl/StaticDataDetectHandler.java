package com.watchdata.cardcheck.logic.impl;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.RtfWriter2;
import com.watchdata.cardcheck.dao.IStaticDataDao;
import com.watchdata.cardcheck.dao.pojo.StaticData;
import com.watchdata.cardcheck.logic.Constants;
import com.watchdata.cardcheck.logic.apdu.CommonAPDU;
import com.watchdata.cardcheck.logic.apdu.CommonHelper;
import com.watchdata.cardcheck.utils.Configuration;
import com.watchdata.cardcheck.utils.DigitalTool;
import com.watchdata.cardcheck.utils.SpringUtil;
import com.watchdata.commons.lang.WDStringUtil;


public class StaticDataDetectHandler {

	private static final String SPLITSTR = "[|]";
	private static final String TAGSPLIT = ";";
	private static final String SPECIAL_TAG_LIST = "61|6F|A5|BF0C|77|70";
	private static final String SW_APPLICATIN_NOT_FOUND = "6A82";
	private  final String IS = "是";
	private  final String NOT = "否";
	private static HashMap<String,String> errCode = new HashMap<String,String>();;
	private static Logger log = Logger.getLogger(StaticDataDetectHandler.class);
	

	private Configuration config ;
	private BaseHandler baseHandler = new BaseHandler();
	private CommonAPDU comAPDU = (CommonAPDU)SpringUtil.getBean("apduHandler");
	private IStaticDataDao staticDataDao = (IStaticDataDao) SpringUtil.getBean("staticDataDao");
	private String cardReader=null;
	private boolean isStop = false;
	private String checkReportPath=null;
	public boolean isStop() {
		return isStop;
	}

	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}
	static{
		errCode.put("6283", "Card Life Cycle State is CARD_LOCKED");
		errCode.put("6882", "Secure messaging not supported");
		errCode.put("6A81", "Function not supported e.g. card Life Cycle State is CARD_LOCKED");
		errCode.put("6A82", "Selected Application / file not found");
		errCode.put("6A83", "record Number is not exist");
		errCode.put("6985", "conditions are not satisfied");
		errCode.put("6A88", "can not get data! ");
	}
	
	public String getCheckReportPath() {
		return checkReportPath;
	}

	public void setCheckReportPath(String checkReportPath) {
		this.checkReportPath = checkReportPath;
	}
	public String getCardReader() {
		return cardReader;
	}

	public void setCardReader(String cardReader) {
		this.cardReader = cardReader;
	}

	public StaticDataDetectHandler(){
		
	}

	/**
	 * 选择PSE或PPSE，根据读取的结果返回应用名称
	 * @param pseOrPpseAid
	 * @param aidSet
	 * @return 错误代码
	 */
	public int getAppNameByPseOrPpse(String pseOrPpseAid,Set<String> aidSet) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String sfi = null;// 短文件标识符
		int recordNum = 1;// 记录号
		String aid = null;
		
		if(isStop){//stop
			return 0;
		}
		HashMap<String, String> resetInfo = comAPDU.reset(cardReader);
		if(!Constants.SW_SUCCESS.equals(resetInfo.get("sw"))){
			 JOptionPane.showMessageDialog(null, "选择支付环境,复位信息出错","提示框", 
						JOptionPane.ERROR_MESSAGE);
			log.error("select："+pseOrPpseAid+",reset :"+resetInfo.get("sw"));
			return 0;//
		}
		//选择PSE 或者PPSE
		 resultMap = comAPDU.select(pseOrPpseAid);
		
		
		 if (SW_APPLICATIN_NOT_FOUND.equalsIgnoreCase((resultMap.get("sw")))) {//找不到应用
			log.error(resultMap.get("sw"));
			return 2;
		}else if(!Constants.SW_SUCCESS.equals(resetInfo.get("sw"))){
			log.error(resultMap.get("sw"));
			return 0;
		}

		if(Constants.PSE.equals(pseOrPpseAid)){
			sfi = resultMap.get("88");//接触or非接sfi = resultMap.get("61");
			if(sfi==null||"".equals(sfi.trim())){
				 JOptionPane.showMessageDialog(null, "找不到应用","提示框", 
							JOptionPane.ERROR_MESSAGE);
				 log.debug("can't get TAG88");
				 return 2;
			}
			int b = Integer.parseInt(sfi);
			b = (b<<3) + 4;
			resultMap.clear();
			//循环读取数据，根据读取的结果找到应用名称
			while (true) {
				if(isStop){//stop
					return 0;
				}
				 resultMap = comAPDU.readRecord(WDStringUtil.paddingHeadZero(Integer.toHexString(b), 2), DigitalTool.algorismToHEXString(recordNum,2));
				if(!Constants.SW_FILE_NOT_FIND.equalsIgnoreCase(resultMap.get("sw"))){
					 aid = resultMap.get("4F");
						if (aid != null && !"".equals(aid)) {
							log.info("aid:" + aid);
							aidSet.add(aid);
						}/* else {
							log.error("get aid:" + aid + ",read error!");
							return 2;
						}*/
						recordNum++;
				}else{
					break;
				}
			}
		}else{
			aidSet.add(resultMap.get("4F"));
		}
		return 1;
	}
	
	/**
	 * 获取静态数据
	 * @param tradeDataMap
	 * @throws Exception
	 */
	public Map<String, Map<String, String>> getPersonData(Set<String> pseOrPpseAidSet,HashMap<String, String> tradeDataMap,String appType)
			{
		Map<String, Map<String, String>> appMap = new HashMap<String, Map<String, String>>();
		// read pse data 
		Iterator<String> it = pseOrPpseAidSet.iterator();
		while (it.hasNext()) {
			if(isStop){//stop
				return null;
			}
			String aid = it.next();
			int resultType =readAppData(aid,appMap,tradeDataMap,appType);
			if(resultType == 0){
				return null;
			}else{
				continue;
			}
		}
		return appMap;
	}
	
/**
 * 读每个应用里的数据
 * @param aid
 * @param appMap
 * @param tradeDataMap
 * @return 错误类型
 */
	public int readAppData(String aid,Map<String, Map<String, String>> appMap,HashMap<String, String> tradeDataMap,String appType){
		Map<String, String> fCIMap = new HashMap<String, String>();
		Map<String, String> resultMap = new HashMap<String, String>();
		String pdol = null;
		String pdolLV = null;
		String gpoResult;
		String afl;// AFL
		
		if(isStop){//stop
			return 0;
		}
		HashMap<String, String> resetInfo = comAPDU.reset(cardReader);
		if(!Constants.SW_SUCCESS.equals(resetInfo.get("sw"))){
			 JOptionPane.showMessageDialog(null, "select aid:"+aid+",复位信息出错","提示框", 
						JOptionPane.ERROR_MESSAGE);
			log.error("static data check,reset :"+resetInfo.get("sw"));
			return 0;
		}
		fCIMap = comAPDU.select(aid);
		if (SW_APPLICATIN_NOT_FOUND.equals(fCIMap.get("sw"))) {
			log.error("aid:"+aid+"status:"+fCIMap.get("sw"));
			JOptionPane.showMessageDialog(null, appType+"：应用或文件找不到！","提示框", 
					JOptionPane.ERROR_MESSAGE);
			log.error(errCode.get(fCIMap.get("sw")));
			return 2;
		}else if(!Constants.SW_SUCCESS.equals(fCIMap.get("sw"))){
			log.error("aid:"+aid+"status:"+fCIMap.get("sw"));
			JOptionPane.showMessageDialog(null, appType+"：选择应用出错！","提示框", 
					JOptionPane.ERROR_MESSAGE);
			return 2;
		}
		pdol = fCIMap.get("9F38");
		resultMap.clear();
		if (pdol == null || "".equals(pdol)) {// 卡片不存在PDOL
			resultMap = comAPDU.gpo("8300");
		} else {// 卡片存在PDOL
			try {
				pdolLV = baseHandler.loadDolData(pdol, tradeDataMap);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, appType+":"+"获取PDOL列表出错", "提示框",
						JOptionPane.ERROR_MESSAGE);
				log.error(e.getMessage() + e);
				return 2;
			}

			resetInfo.clear();
			if(isStop){//stop
				return 0;
			}
			resetInfo = comAPDU.reset(cardReader);
			if (!"9000".equals(resetInfo.get("sw"))) {
				JOptionPane.showMessageDialog(null, "select aid:" + aid
						+ ",复位信息出错", "提示框", JOptionPane.ERROR_MESSAGE);
				log.error("static data check,reset :" + resetInfo.get("sw"));
				return 0;
			}
			resultMap = comAPDU.gpo("83" + CommonHelper.getLVData(pdolLV, 1));
		}
			if (!Constants.SW_SUCCESS.equals(resultMap.get("sw"))) {
				JOptionPane.showMessageDialog(null, appType+",aid:"+aid+",GPO指令执行错误","提示框", 
						JOptionPane.ERROR_MESSAGE);
				log.error("GPO command is error,sw is "
						+ resultMap.get("sw"));
				return 2;
			} else {
				
				//qpboc afl直接通过94获取
				afl = resultMap.get("94");
				log.info("afl:"+afl);
				if(afl==null){
					JOptionPane.showMessageDialog(null, appType+"：获取不到AFL","提示框", 
							JOptionPane.ERROR_MESSAGE);
					return 2;
				}
				log.info("afl:"+afl);
				Map<String,String> dgiMap =  readAFL(afl);
				if(dgiMap ==null){
					return 2;
				}else{
					appMap.put(aid,dgiMap);
				}
				
				fCIMap.put("82", resultMap.get("82"));
				fCIMap.put("94", resultMap.get("94"));
				fCIMap.put("4F", aid);
				appMap.put(aid+"FCI", fCIMap);
			}
		
		return 1;
	}
	
	public Map<String,String> readAFL(String afl){
		Map<String,String> dgiMap = new HashMap<String,String>();
		List<String> sfiAndRecordList = CommonHelper.parseAppFileLocation(afl);
		if(sfiAndRecordList==null||sfiAndRecordList.size()==0){
			JOptionPane.showMessageDialog(null, "解析AFL出错","提示框", 
					JOptionPane.ERROR_MESSAGE);
			log.error("parse AFL error!");
			return null;
		}
		HashMap<String, String> resetInfo = comAPDU.reset(cardReader);
		
		Map<String,String> recordRet=new HashMap<String,String>();
		for(String sfiAndRecord:sfiAndRecordList){
			if(isStop){//stop
				return null;
			}
			resetInfo.clear();
			 resetInfo = comAPDU.reset(cardReader);
			if(!Constants.SW_SUCCESS.equals(resetInfo.get("sw"))){
				 JOptionPane.showMessageDialog(null, "读取AFL,复位信息出错","提示框", 
							JOptionPane.ERROR_MESSAGE);
				log.error("static data check,reset :"+resetInfo.get("sw"));
				return null;
			}
			recordRet = comAPDU.readRecord(sfiAndRecord.substring(0, 2), sfiAndRecord.substring(2,4));
			if(recordRet==null||recordRet.size()==0){
				JOptionPane.showMessageDialog(null,"读取AFL记录出错", "提示框", 
						JOptionPane.ERROR_MESSAGE);
				log.error("read sfiAndRecord error");
				return null;
			}else if(Constants.SW_FILE_NOT_FIND.equals(recordRet.get("sw"))){
				JOptionPane.showMessageDialog(null, "根据AFL读取记录，文件找不到","提示框", 
						JOptionPane.ERROR_MESSAGE);
				log.error(errCode.get(Constants.SW_FILE_NOT_FIND));
				return null;
			}
			int tempsfi=Integer.parseInt(sfiAndRecord.substring(0, 2),16);
			String dgi = DigitalTool.algorismToHEXString(tempsfi>>3,2) + sfiAndRecord.substring(2);
			dgiMap.put(dgi, mapToString(recordRet));
		}
		return dgiMap;
	}
	
	
	/**
	 * 将Map中的数据转成一个字符串，并以";"分割
	 * @param resultMap
	 * @return 转换后的字符串
	 */
	public String mapToString(Map<String,String> resultMap){
		StringBuffer resultSTr = new StringBuffer();
		Iterator<String> it = resultMap.keySet().iterator();
		while(it.hasNext()){
			String key = (String) it.next();
			if("sw".equalsIgnoreCase(key)||"apdu".equalsIgnoreCase(key)||"res".equalsIgnoreCase(key)){
				continue;
			}
			resultSTr.append(key);
			resultSTr.append(resultMap.get(key));
			resultSTr.append(TAGSPLIT);
			
		}
		return resultSTr.toString();
	}
	
	/**
	 * 通过get data指令获取数据
	 * @return
	 */
	public Map<String, String> getData(String getdataTag) {
		log.debug("getdata read data");
		Map<String, String> getdataMap = new HashMap<String, String>();
		Map<String, String> resultMap = new HashMap<String, String>();
		config = new Configuration();
		String getDataTag = config.getValue(getdataTag);
		String tags[];
		StringBuffer value = new StringBuffer();
		if (getDataTag == null || "".equals(getDataTag)) {
			JOptionPane.showMessageDialog(null, "配置文件标签"+getdataTag+"配置错误","提示框", 
					JOptionPane.ERROR_MESSAGE);
			log.error("geting data tag from config.properties error!");
			return null;
		} else {
			tags = getDataTag.split(SPLITSTR);
			if (tags == null || tags.length == 0) {
				log.error("split getdatatag error!");
				return null;
			} else {
				if(isStop){//stop
					return null;
				}
				HashMap<String, String> resetInfo = comAPDU.reset(cardReader);
				if(!Constants.SW_SUCCESS.equals(resetInfo.get("sw"))){
					 JOptionPane.showMessageDialog(null, "Getdata,复位信息出错","提示框", 
								JOptionPane.ERROR_MESSAGE);
					log.error("static data check,reset :"+resetInfo.get("sw"));
					return null;
				}
				for (int i = 0; i < tags.length; i++) {
					if(isStop){//stop
						return null;
					}
					log.info("getdata--tag"+tags[i]);
					resultMap.clear();
					resultMap = comAPDU.getData(tags[i]);
					if (!Constants.SW_SUCCESS
							.equals(resultMap.get("sw"))) {
						log.error(errCode.get(resultMap.get("sw")));
						continue;
					} else {
						value.append(tags[i]);
						value.append(resultMap.get(tags[i]));
						value.append(TAGSPLIT);
					}
				}
				getdataMap.put("GETDATA", value.toString());
			}
		}
		return getdataMap;
	}
	
 /**
 * 获取应用的全部数据，包括可以读的和通过getdata获取的
 * @return 应用数据List
 */
	public List<AppData> getAllAppData(){
		int tradeMount = 100;
		Map<String, Map<String, String>> appsMap = new HashMap<String, Map<String, String>>();
		HashMap<String,String> param = new HashMap<String,String>();
		//PBOC
		HashMap<String,String> paramPBOC = new HashMap<String,String>();
		paramPBOC.put("9F02", WDStringUtil.paddingHeadZero(
				String.valueOf(Integer.toHexString(tradeMount)), 12));
		paramPBOC.put("9F7A", "00");
		//qPBOC
		HashMap<String,String> paramQPBOC = new HashMap<String,String>();
		paramQPBOC.put("9F02", WDStringUtil.paddingHeadZero(
				String.valueOf(Integer.toHexString(tradeMount)), 12));
				
		Date dateTime = new Date();
		paramQPBOC.put("9A",baseHandler.getFormatDate(dateTime,Constants.FORMAT_SHORT_DATE));
		paramQPBOC.put("9F21",baseHandler.getFormatDate(dateTime,Constants.FORMAT_TIME));
		String termRandom = WDStringUtil.getRandomHexString(8);
		paramQPBOC.put("9F37", termRandom);//终端随机数
		//电子现金
		HashMap<String,String> paramElect = new HashMap<String,String>();
		paramElect.put("9F02", WDStringUtil.paddingHeadZero(
				String.valueOf(Integer.toHexString(tradeMount)), 12));
		paramElect.put("9F7A", "01");
		config = new Configuration();
		int appCount = config.getIntValue("appCount",0);
		if(appCount==0){
			JOptionPane.showMessageDialog(null, "配置文件配置的应用个数为0","提示框", 
					JOptionPane.ERROR_MESSAGE);
			log.error("the count of application is 0");
			return null;
		}else{
			String appConfig="";
			String configs[] = null;
			String pseOrPpseAid = null;
			AppData appData ;
			List<AppData> appList=new ArrayList<AppData>();
			for(int i=1;i<=appCount;i++){
				appConfig = config.getValue(String.valueOf(i),"");
				if("".equals(appConfig)){
					JOptionPane.showMessageDialog(null, "获取不到应用","提示框", 
							JOptionPane.ERROR_MESSAGE);
					log.error("can't find the application: "+i);
				}
				configs = appConfig.split(SPLITSTR);
				if(configs.length<2||configs.length>3){
					JOptionPane.showMessageDialog(null, "应用配置错误","提示框", 
							JOptionPane.ERROR_MESSAGE);
					log.error("the application: "+i+" config error!");
					return null;
				}
				pseOrPpseAid = findpseOrPpse(configs[1]);
				if(pseOrPpseAid == null){
					return null;
				}
				
				appData= new AppData();
				//读取应用AID
				Set<String> pseOrPpseAidSet = new HashSet();
				 int errorType= getAppNameByPseOrPpse(pseOrPpseAid,pseOrPpseAidSet);
				 if(errorType == 0){
					 return null;
				 }else if(errorType ==2){
					 log.error("can't find application,enviroment is "+pseOrPpseAid);
						continue; 
				 }
				
				 /**************应用的类型不同，参数不同***************/
				log.debug("Application type："+configs[0]);
				param.clear();
				 if("电子现金".equals(configs[0])){
					 param=paramElect;
				 }else if("标准借贷记".equals(configs[0])){
					 param=paramPBOC;
				 }else if("QPBOC".equals(configs[0])){
					 param=paramQPBOC;
				 }
				//<aid,dgiMap<>>
				Map<String, Map<String, String>> perDataMap = getPersonData(pseOrPpseAidSet,param,configs[0]);
				if(perDataMap == null){
					return null;
				}
				
				appData.setAppType(configs[0]);
				appData.setReadDataMap(perDataMap);
				if(configs.length==3){
					log.debug("getdatatag:"+configs[2]);
					Map<String, String> getdataMap = getData(configs[2]);
					if(getdataMap==null){
						return null;
					}
					log.info("getdata.size" +getdataMap.size());
					appData.setGetdataMap(getdataMap);
				}else{//configs.length==2
					appData.setGetdataMap(null);
				}
				appList.add(appData);
			}
			return appList;
		}
				
	
	}
	
	
	/**
	 * 从数据库中读取卡片数据
	 * @return Map<String, Map<String, StaticData>>，key为应用的类型，value为应用下的tag数据
	 */
	public Map<String, Map<String, StaticData>> getCardDataFromDatabase(){
		Map<String, Map<String, StaticData>> staticDataMap = new HashMap<String, Map<String, StaticData>>();
		IStaticDataDao staticDataDao = (IStaticDataDao)SpringUtil.getBean("staticDataDao");
		
		List<StaticData> sDataList =staticDataDao.searchStaticData();
		if(sDataList==null){
			log.error("can't get static data from database!");
			return null;
		}
		
		for(StaticData sData:sDataList){
			if(isStop){//stop
				return null;
			}
			String appType = sData.getAppType();
			if(staticDataMap.get(appType)==null){
				Map<String, StaticData> tagValueMap = new HashMap<String, StaticData>();
				sData.setIsMatch(NOT);
				sData.setCardValue("");
				tagValueMap.put(sData.getTag(), sData);
				staticDataMap.put(appType, tagValueMap);
			}else{
				sData.setCardValue("");
				sData.setIsMatch(NOT);
				staticDataMap.get(appType).put(sData.getTag(), sData);
			}
		}
		return staticDataMap;
	}

	public Map<String, Map<String, StaticData>> check(List<AppData> allAppData,Map<String, Map<String, StaticData>> staticDataMap) {
		if (allAppData == null || allAppData.size() == 0) {
			log.error("application data is empty");
			return null;
		} else {
			if (staticDataMap == null||staticDataMap.size()==0) {
				log.error("unimportData");
				return unimportData(allAppData);
			} else {
				return compareImportData(allAppData, staticDataMap);
			}
			//return compareImportData(allAppData, staticDataMap);
		}
	}
	
	/**
	 * 数据库中不存在数据时
	 * @param allAppData
	 */
	public Map<String, Map<String, StaticData>> unimportData(List<AppData> allAppData){
		String pan = "";
		//List <StaticData> staticDataList = new ArrayList<StaticData>();
		Map<String, Map<String, StaticData>> staticDataMap = new HashMap<String, Map<String, StaticData>>();
		for(AppData appData:allAppData){
			String appType = appData.getAppType();
			Map<String, Map<String, String>> readableData = appData.getReadDataMap();
			Iterator<String> it = readableData.keySet().iterator();
			while(it.hasNext()){
				if(isStop){//stop
					return null;
				}
				String key = (String) it.next();
				Map<String, String> dgiMap = readableData.get(key);
				Iterator<String> dgiIt = dgiMap.keySet().iterator();
				while(dgiIt.hasNext()){//对比每一个tag和value
					if(isStop){//stop
						return null;
					}
					String dgiKey = (String) dgiIt.next();
					String tagValue = (String) dgiMap.get(dgiKey);
					String tag = "";
					String value="";
					//AIDFCI
					if(key.endsWith("FCI")){
						tag = dgiKey;
						value = tagValue;
						if("sw".equalsIgnoreCase(tag)||"apdu".equalsIgnoreCase(tag)||"res".equalsIgnoreCase(tag)){
							continue;
						}
						if(SPECIAL_TAG_LIST.indexOf(tag)!=-1){
							continue;
						}
						StaticData stcData = new StaticData();
						stcData.setAppType(appType);
						stcData.setCardValue(value);
						stcData.setIsMatch(NOT);
						stcData.setTag(tag);
						
						log.debug("apptype:" + appType +"tag:"+tag +"value:"+value);
						if(staticDataMap.get(appType)==null){
							Map<String, StaticData> tagObject = new HashMap<String, StaticData>();
							tagObject.put(tag, stcData);
							staticDataMap.put(appType, tagObject);
						}else{
							staticDataMap.get(appType).put(tag, stcData);
						}
						//staticDataList.add(stcData);
					}else{
						String tagValues[] = tagValue.split("[;]");
						for(int j=0;j<tagValues.length;j++){
							tag = tagValues[j].substring(0, 2);
							if("sw".equalsIgnoreCase(tag)||"apdu".equalsIgnoreCase(tag)||"res".equalsIgnoreCase(tag)){
								continue;
							}
							if((Integer.parseInt(tag,16)& 0x1F) == 0x1F) {
								tag += tagValues[j].substring(2, 4);
							}
							if(SPECIAL_TAG_LIST.indexOf(tag)!=-1){
								continue;
							}
							value = tagValues[j].substring(tag.length(),tagValues[j].length());
					
						StaticData stcData = new StaticData();
						stcData.setAppType(appType);
						stcData.setCardValue(value);
						stcData.setIsMatch(NOT);
						stcData.setTag(tag);
						if("5A".equalsIgnoreCase(tag)){
							pan=value;
						}
						log.debug("apptype:" + appType +"tag:"+tag +"value:"+value);
						if(staticDataMap.get(appType)==null){
							Map<String, StaticData> tagObject = new HashMap<String, StaticData>();
							tagObject.put(tag, stcData);
							staticDataMap.put(appType, tagObject);
						}else{
							staticDataMap.get(appType).put(tag, stcData);
						}
						//staticDataList.add(stcData);
					}
					}
				}			
			}
			
			Map<String, String> getData = appData.getGetdataMap();
			log.debug(appType + "begin getdata" + getData);
			if(getData == null){
				continue;
			}
			Iterator<String> getDataIt = getData.keySet().iterator();
			while(getDataIt.hasNext()){
				if(isStop){//stop
					return null;
				}
				String key = (String) getDataIt.next();
				String getDataValue = getData.get(key);
				if(getDataValue==null||"".equals(getDataValue)){
					continue;
				}
					String tagValues[] = getDataValue.split("[;]");
					
					for(int j=0;j<tagValues.length;j++){
						String tag = tagValues[j].substring(0, 2);
						if((Integer.parseInt(tag,16)& 0x1F) == 0x1F) {
							tag += tagValues[j].substring(2, 4);
						}
						if(SPECIAL_TAG_LIST.indexOf(tag)!=-1){
							continue;
						}
						String value = tagValues[j].substring(tag.length(),tagValues[j].length());
						StaticData stcData = new StaticData();
						stcData.setAppType(appType);
						stcData.setCardValue(value);
						stcData.setIsMatch(NOT);
						stcData.setTag(tag);
						log.debug("apptype:" + appType +"tag:"+tag +"value:"+value);
						if(staticDataMap.get(appType)==null){
							Map<String, StaticData> tagObject = new HashMap<String, StaticData>();
							tagObject.put(tag, stcData);
							staticDataMap.put(appType, tagObject);
						}else{
							staticDataMap.get(appType).put(tag, stcData);
						}
						//staticDataList.add(stcData);
					}
					
				}			
			}
		/*if(pan!=null||"".equals(pan)){
			System.out.println("pan"+pan);
			generateReport(staticDataList,pan);//生成报告文件
		}else{
			log.info("can get pan");
		}*/
		
		return staticDataMap;
	}
	
	/**
	 * 数据库中存在数据时
	 * @param allAppData
	 */
	public Map<String, Map<String, StaticData>> compareImportData(List<AppData> allAppData,Map<String, Map<String, StaticData>> staticDataMap){
		String pan = "";
		List <StaticData> staticDataList = new ArrayList<StaticData>();
		for(AppData appData:allAppData){
			String appType = appData.getAppType();
			Map<String, Map<String, String>> readableData = appData.getReadDataMap();
			Iterator<String> it = readableData.keySet().iterator();
			while(it.hasNext()){
				if(isStop){//stop
					return null;
				}
				String key = (String) it.next();
				Map<String, String> dgiMap = readableData.get(key);
				Iterator<String> dgiIt = dgiMap.keySet().iterator();
				while(dgiIt.hasNext()){//对比每一个tag和value
					if(isStop){//stop
						return null;
					}
					String dgiKey = (String) dgiIt.next();
					String tagValue = (String) dgiMap.get(dgiKey);
					String tag = "";
					String value="";
					//AIDFCI
					if(key.endsWith("FCI")){
						tag = dgiKey;
						value = tagValue;
						if("sw".equalsIgnoreCase(tag)||"apdu".equalsIgnoreCase(tag)||"res".equalsIgnoreCase(tag)){
							continue;
						}
						if(SPECIAL_TAG_LIST.indexOf(tag)!=-1){
							continue;
						}
						//对比
						if(staticDataMap.get(appType)==null){
							log.error("can't find the application,the application type is "+appType);
						}else if(staticDataMap.get(appType).get(tag)==null){
							
						}else{
							StaticData staticData = staticDataMap.get(appType).get(tag);

							staticData.setCardValue(value);
							if(value.equals(staticData.getOriValue())){
								staticData.setIsMatch(IS);
							}else{
								staticData.setIsMatch(NOT);
							}
							staticDataList.add(staticData);
						}
					}else{
					String tagValues[] = tagValue.split("[;]");
					
					for(int j=0;j<tagValues.length;j++){
						if(isStop){//stop
							return null;
						}
						tag = tagValues[j].substring(0, 2);
						/*if("sw".equalsIgnoreCase(tag)||"apdu".equalsIgnoreCase(tagValues[j].substring(0, 4))||"res".equalsIgnoreCase(tagValues[j].substring(0, 3))){
							continue;
						}*/
						if((Integer.parseInt(tag,16)& 0x1F) == 0x1F) {
							tag += tagValues[j].substring(2, 4);
						}
						
						if(SPECIAL_TAG_LIST.indexOf(tag)!=-1){
							continue;
						}
						value = tagValues[j].substring(tag.length(),tagValues[j].length());
						
						
						//对比
						if(staticDataMap.get(appType)==null){
							
							log.error("can't find the application,the application type is "+appType);
						}else if(staticDataMap.get(appType).get(tag)==null){
							
						}else{
							StaticData staticData = staticDataMap.get(appType).get(tag);

							staticData.setCardValue(value);
							if(value.equals(staticData.getOriValue())){
								staticData.setIsMatch(IS);
							}else{
								staticData.setIsMatch(NOT);
							}
							
							staticDataList.add(staticData);
						}
					}
					
				}			
			}
			}
			Map<String, String> getData = appData.getGetdataMap();
			log.debug(appType + "开始getdata" + getData);
			if(getData == null||getData.size()==0){
				continue;
			}
			Iterator<String> getDataIt = getData.keySet().iterator();
			log.info("getData的大小"+getData.size());
			while(getDataIt.hasNext()){
				if(isStop){//stop
					return null;
				}
				String key = (String) getDataIt.next();
				String getDataValue = getData.get(key);
				
					String tagValues[] = getDataValue.split("[;]");
					
					for(int j=0;j<tagValues.length;j++){
						if(isStop){//stop
							return null;
						}
						String tag = tagValues[j].substring(0, 2);
						if((Integer.parseInt(tag,16)& 0x1F) == 0x1F) {
							tag += tagValues[j].substring(2, 4);
						}
						if(SPECIAL_TAG_LIST.indexOf(tag)!=-1){
							continue;
						}
						String value = tagValues[j].substring(tag.length(),tagValues[j].length());
						if(staticDataMap.get(appType)==null){
							log.error("can't find the application,the application type is "+appType);
						}else if(staticDataMap.get(appType).get(tag)==null){
							
						}else{
							StaticData staticData = staticDataMap.get(appType).get(tag);

							staticData.setCardValue(value);
							if(value.equals(staticData.getOriValue())){
								staticData.setIsMatch(IS);
							}else{
								staticData.setIsMatch(NOT);
							}
							staticDataList.add(staticData);
						}
					}
					
				}
		}
		/*if(pan!=null||!"".equals(pan)){
			System.out.println("pan"+pan);
			generateReport(staticDataList,pan);//生成报告文件
		}else{
			log.info("can get pan");
		}*/
		if(staticDataList.size()==0){
			JOptionPane.showMessageDialog(null, "数据库中不存在与卡上tag相同的记录！","提示框", 
					JOptionPane.ERROR_MESSAGE);
			log.error("compareImportData：staticDataList.size()："+staticDataList.size());
			return null;
		}
		boolean result = staticDataDao.batchCompareUpdate(staticDataList);
		if(!result){
			log.error("updata database error");
			JOptionPane.showMessageDialog(null, "检测结果保存数据库过程中出错！","提示框", 
					JOptionPane.ERROR_MESSAGE);
		}
		return staticDataMap;
	}
	

	/**
	 * 字符串常量PSE与值之间的转换
	 * @param type
	 * @return
	 */
	public String findpseOrPpse(String type){
		if("PSE".equalsIgnoreCase(type)){
			return Constants.PSE;
		}else if("PPSE".equalsIgnoreCase(type)){
			return Constants.PPSE;
		}else{
			JOptionPane.showMessageDialog(null, "应用的支付环境配置错误", "提示框",
					JOptionPane.ERROR_MESSAGE);
		log.error("pay enviroment configs error!");
		return null;
		}
	} 
	
	public boolean generateReport(List <StaticData> staticDataList,String pan){
		String filePath = System.getProperty("user.dir") + "/report/staticDataCheck";
		File dirFile = new File(filePath);
		if (!(dirFile.exists()) && !(dirFile.isDirectory())) {
			boolean creadok = dirFile.mkdirs();
			if (!creadok) {
				JOptionPane.showMessageDialog(null, "创建检测报告文件夹失败！","提示框", 
						JOptionPane.ERROR_MESSAGE);
				log.error("create report file dir error！");
				return false;
			}
		}
		Date curTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(curTime);
		try {
			String columnTitle[] ={"应用类型","标签","原值","卡值","描述","匹配"};
			if(pan == null||"".equals(pan.trim())){
				checkReportPath = filePath+"//"+dateString+".doc";
			}else{
				checkReportPath = filePath+"//"+pan+"_"+dateString+".doc";
			}
			
			createReportContext(checkReportPath,columnTitle,staticDataList);
			return true;
		} catch (DocumentException e) {
			JOptionPane.showMessageDialog(null, "生成检测报告失败！", "提示框",
					JOptionPane.ERROR_MESSAGE);
			log.error(e.getMessage(), e);
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "生成检测报告失败！", "提示框",
					JOptionPane.ERROR_MESSAGE);
			log.error(e.getMessage(), e);
			e.printStackTrace();
			return false;
		}
	
	}
	
	 public void createReportContext(String file,String columnTitle[],List <StaticData> staticDataList)throws DocumentException, IOException{   
	        //设置纸张大小   
	        Document document = new Document(PageSize.A4);   
	        //建立一个书写器，与document对象关联   
	        RtfWriter2.getInstance(document, new FileOutputStream(file));   
	        document.open();   
	        //设置中文字体   
	        BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);   
	        //标题字体风格   
	        Font titleFont = new Font(bfChinese,17,Font.BOLD);  
	      //标题字体风格   
	        Font tableHeadFont = new Font(bfChinese,13,Font.BOLD); 
	        //正文字体风格   
	        Font contextFont = new Font(bfChinese,10,Font.NORMAL);   
	        Paragraph title = new Paragraph("静态数据检测报告");   
	        //设置标题格式对齐方式   
	        title.setAlignment(Element.ALIGN_CENTER);   
	        title.setFont(titleFont);   
	        document.add(title);   
	      
	        //设置Table表格,创建一个三列的表格   
	        Table table = new Table(columnTitle.length);   
	        int width[] = {12,8,30,30,12,8};//设置每列宽度比例   
	        table.setWidths(width);  
	        table.setWidth(100);//占页面宽度比例   
	        table.setAlignment(Element.ALIGN_CENTER);//居中   
	        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中   
	        table.setAutoFillEmptyCells(true);//自动填满   
	        table.setBorderWidth(1);//边框宽度   
	        //设置表头   
	        
	        for(int i=0;i<columnTitle.length;i++){
	        	Cell headerCell = new Cell(new Paragraph(columnTitle[i],tableHeadFont)); 
	        	headerCell.setBackgroundColor(new Color(100,149,237));
	        	headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
	  	        headerCell.setHeader(true);   
	  	        table.addCell(headerCell); 
	        }
	        table.endHeaders();   
	           
	        Font fontChinese = new Font(bfChinese,11,Font.NORMAL,Color.BLACK);   
	        
	        for(StaticData staticData:staticDataList){
	        	if(isStop){//stop
	        		document.close();
					return ;
				}
	        	Cell cellAppType = new Cell(new Paragraph(staticData.getAppType(),fontChinese)); 
	        	
		       // cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
	        	table.addCell(cellAppType);
	        	Cell cellTag=new Cell(new Paragraph(staticData.getTag(),fontChinese));
	        	 table.addCell(cellTag);  
	        	 Cell cellOriValue= new Cell(new Paragraph(staticData.getOriValue(),fontChinese));
		        table.addCell(cellOriValue);  
		        Cell cellCardValue= new Cell(new Paragraph(staticData.getCardValue(),fontChinese));
		        table.addCell(cellCardValue); 
		        Cell cellDscrpt= new Cell(new Paragraph(staticData.getDscrpt(),fontChinese));
		        table.addCell(cellDscrpt);  
		        Cell cellIsMatch = new Cell(new Paragraph(staticData.getIsMatch(),fontChinese));
		        table.addCell(cellIsMatch);  
		        if(staticData.getIsMatch()==null||"".equals(staticData.getIsMatch().trim())||staticData.getIsMatch().equals("否")){
	        		cellAppType.setBackgroundColor(new Color(238,233,233 ));
	        		cellTag.setBackgroundColor(new Color(238,233,233 ));
	        		cellOriValue.setBackgroundColor(new Color(238,233,233 ));
	        		cellDscrpt.setBackgroundColor(new Color(238,233,233 ));
	        		cellIsMatch.setBackgroundColor(new Color(238,233,233 ));
	        		cellCardValue.setBackgroundColor(new Color(238,233,233 ));
	        	}
	        }
	        document.add(table);   
	        document.close();   
	               
	    }   
	public static void main(String args[]){
		
	}
}
