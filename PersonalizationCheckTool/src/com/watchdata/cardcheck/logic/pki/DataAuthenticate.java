package com.watchdata.cardcheck.logic.pki;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.watchdata.cardcheck.log.Log;
import com.watchdata.cardcheck.utils.CryptoUtil;
import com.watchdata.commons.lang.WDByteUtil;
import com.watchdata.commons.lang.WDEncodeUtil;

public class DataAuthenticate {
	// log5j打日志
	private static Log log = new Log();
	// 恢复数据头
	private static final String RECOVER_DATA_HEAD = "6A";
	// 签名数据格式(静态数据认证)
	private static final String SIGN_DATA_STYLE_SDA = "03";
	// 签名数据格式(动态数据认证)
	private static final String SIGN_DATA_STYLE_DDA = "05";
	// 恢复数据结尾
	private static final String RECOVER_DATA_TAIL = "BC";
	
	//应用ID
	private String rID;
	//CA公钥索引
	private String caPKIndex;
	//发卡行公钥证书
	private String issuerCert;
	//发卡行公钥余项
	private String issuerPKSurplus;
	//发卡行公钥指数
	private String issuerPKExponent;
	//主账号
	private String pan;
	//静态数据列表
	private String staticDataList;
	
	/**
	 * @author peng.wang 
	 * 
	 * @param rID 
	 * @param caPKIndex CA公钥索引
	 * @param issuerCert 发卡行公钥证书
	 * @param issuerPKSurplus 发卡行公钥余项
	 * @param issuerPKExponent 发卡行公钥指数
	 * @param pan 主帐号
	 * @param staticDataList 静态数据列表
	 */
	public DataAuthenticate(String rID, String caPKIndex, String issuerCert,
			String issuerPKSurplus, String issuerPKExponent, String pan,String staticDataList) {
		this.rID = rID;
		this.caPKIndex = caPKIndex;
		this.issuerCert = issuerCert;
		this.issuerPKSurplus = issuerPKSurplus;
		this.issuerPKExponent = issuerPKExponent;
		this.pan = pan;
		this.staticDataList = staticDataList;
	}

	/**
	 * @author peng.wang 
	 * 
	 * @param icCert IC卡公钥证书
	 * @param icPKSurplus IC卡公钥余项
	 * @param icPKExponent IC卡公钥指数
	 * @param signedDynamicData 签名的动态数据
	 * @param ddolDataList 动态数据列表
	 * @throws Exception 
	 */
	public boolean dynamicDataAuthenticate(String icCert,String icPKSurplus,String icPKExponent,String signedDynamicData,String ddolDataList,List<String> list) throws Exception {
		
		//收集日志信息以备写入监测报告
		List<String> logList=new ArrayList<String>();
		List<String> caLogList=new ArrayList<String>();
		
		//发卡行公钥信息
		CardissuerPublicKeyInfo issuerPKInfo = PkiUtil.cardIssuer_pubKey_recover(rID, caPKIndex, issuerCert, issuerPKSurplus, issuerPKExponent, pan,caLogList);
		if(!issuerPKInfo.isSuccess()){
			String errorCode = issuerPKInfo.getErrorCode();
			log.error("dynamicDataAuthenticate to get cardissuerPublicKeyInfo error the code is: " + errorCode);
			return false;
		}
		initLogList(logList);
		printLog(logList,"Issuer Public Key Certificate[90]:\n"+issuerCert);
		//printLog(logList,"CA Public Key index:"+caPKIndex);
		printLog(logList,"Issuer_PK_Exponent[9F32]:\n"+issuerPKExponent);
		//printLog(logList,"【Issuer】"+issuerPKInfo.toString());
		printLog(logList,"Issuer Public Key Remainder[92]:\n"+issuerPKSurplus);
		
		//获取IC卡公钥信息
		IcCardPublicKeyInfo icPKInfo = PkiUtil.icCard_pubKey_recover(icCert, issuerPKInfo.getPubMod(), icPKSurplus, issuerPKExponent,icPKExponent, pan, staticDataList,caLogList);
		if(!icPKInfo.isSuccess()){
			String errorCode = icPKInfo.getErrorCode();
			log.error("dynamicDataAuthenticate to get icCardPublicKeyInfo error the code is: " + errorCode);
			return false;
		}
		
		for (String calog : caLogList) {
			logList.add(calog);
		}
		printLog(logList,"ICC Public Key Certificate('9F 46'):\n"+icCert);
		printLog(logList,"ICC Public Key Modulus:\n"+icPKInfo.getPubMod());
		printLog(logList,"ICC Public Key Remainder[9F48]:\n"+icPKSurplus);
		printLog(logList,icPKInfo.toString());
		printLog(logList,"ICC Public Key Exponent ('9F 47') :\n"+icPKExponent);
		
		
		//判断IC卡公钥模长度与动态数据长度是否相等
		if (icPKInfo.getPubMod().length() != signedDynamicData.length()) {
			log.error("dynamicDataAuthenticate length is not equals!");
			return false;
		}
		//恢复签名的动态数据
		String resultData = CryptoUtil.rsa_decrypt(icPKInfo.getPubMod(),icPKExponent, signedDynamicData);
		printLog(logList,"Recovered signedDDA data:"+resultData);
		//判断恢复数据的开头
		if (!resultData.startsWith(RECOVER_DATA_HEAD + SIGN_DATA_STYLE_DDA)) {
			log.error("dynamicDataAuthenticate not start with 6A05!");
			return false;
		}
		////判断恢复数据的结尾
		if (!resultData.endsWith(RECOVER_DATA_TAIL)) {
			log.error("dynamicDataAuthenticate not end with BC!");
			return false;
		}
		//哈希算法标识
		String hashStyle = resultData.substring(4,6);
		//log.debug("dynamicDataAuthenticate hash style：" + hashStyle);
		printLog(logList,"hash style："+hashStyle);
		//需要计算哈希的数据
		String toHashData = resultData.substring(2, resultData.length() - 42) + ddolDataList;
		//log.debug("dynamicDataAuthenticate to hash：" + toHashData);
		printLog(logList,"DDA hash data：" + toHashData);
		String hashData = "";
		//判断哈希算法标识并计算哈希
		try{
			if("01".equals(hashStyle)){
				hashData = WDByteUtil.bytes2HEX(WDEncodeUtil.sha1(WDByteUtil.HEX2Bytes(toHashData)));
			}else if("02".equals(hashStyle)){
				hashData = WDByteUtil.bytes2HEX(WDEncodeUtil.sha256(WDByteUtil.HEX2Bytes(toHashData)));
			}else if("03".equals(hashStyle)){
				hashData = WDByteUtil.bytes2HEX(WDEncodeUtil.sha512(WDByteUtil.HEX2Bytes(toHashData)));
			}else{
				log.error("dynamicDataAuthenticate" + hashStyle + " Hash style is not support");
				return false;
			}
		}catch(Exception e){
			log.error("dynamicDataAuthenticate to get hash has a exception" + e.getMessage());
			throw new Exception("静态数据认证计算Hash的过程中出现异常" + e.getMessage());
			//return false; 
		}
		//log.debug("dynamicDataAuthenticate get hash：" + hashData);
		printLog(logList,"calcuate hash：" + hashData);
		//恢复数据中的哈希数据
		String resultHashData = resultData.substring(resultData.length() - 42,resultData.length() - 2);
		//log.debug("dynamicDataAuthenticate get hash from recovered data：" + resultHashData);
		printLog(logList,"recovered data hash：" + resultHashData);
		//比较两个哈希值
		if (!hashData.equalsIgnoreCase(resultHashData)) {
			log.error("Hash is not equals!");
			return false;
		}
		//填充数据
		for (int i = 0; i < logList.size(); i++) {
			list.add("");
		}
		//拷贝数据
		Collections.copy(list, logList);
		
		return true;
	}

	/**
	 * @author peng.wang
	 * 
	 * @param signedStaticData 签名的静态数据
	 * @throws Exception 
	 */
	public boolean staticDataAuthenticate(String signedStaticData) throws Exception {

		//发卡行公钥信息
		CardissuerPublicKeyInfo issuerPKInfo = PkiUtil.cardIssuer_pubKey_recover(rID, caPKIndex, issuerCert, issuerPKSurplus, issuerPKExponent, pan,null);
		if(!issuerPKInfo.isSuccess()){
			String errorCode = issuerPKInfo.getErrorCode();
			log.error("staticDataAuthenticate to get cardissuerPublicKeyInfo error the code is: " + errorCode);
			return false;
		}
		//判断发卡行公钥模长度与静态数据长度是否相等
		if(issuerPKInfo.getPubMod().length() != signedStaticData.length()){
			log.error("staticDataAuthenticate length is not equals!");
			return false;
		}
		//恢复出来的静态数据
		String resultData = CryptoUtil.rsa_decrypt(issuerPKInfo.getPubMod(),issuerPKExponent, signedStaticData);
		log.debug("staticDataAuthenticate recovered staticData:" + resultData);
		//判断恢复数据的开头
		if (!resultData.startsWith(RECOVER_DATA_HEAD + SIGN_DATA_STYLE_SDA)) {
			log.error("staticDataAuthenticate not start with 6A03!");
			return false;
		}
		//判断恢复数据结尾
		if (!resultData.endsWith(RECOVER_DATA_TAIL)) {
			log.error("staticDataAuthenticate not end with BC!");
			return false;
		}
		//哈希算法标识
		String hashStyle = resultData.substring(4,6);
		log.debug("staticDataAuthenticate hash style:" + hashStyle);
		//需要计算哈希的数据
		String toHashData = resultData.substring(2, resultData.length() - 42) + staticDataList;
		log.debug("staticDataAuthenticate to hash:" + toHashData);
		////如果静态数据认证标签列表存在，并且其包含非“82”的标签，那么静态数据认证失败。
		String hashData = "";
		//判断哈希算法标识并计算哈希
		try{
			if("01".equals(hashStyle)){
				hashData = WDByteUtil.bytes2HEX(WDEncodeUtil.sha1(WDByteUtil.HEX2Bytes(toHashData)));
			}else if("02".equals(hashStyle)){
				hashData = WDByteUtil.bytes2HEX(WDEncodeUtil.sha256(WDByteUtil.HEX2Bytes(toHashData)));
			}else if("03".equals(hashStyle)){
				hashData = WDByteUtil.bytes2HEX(WDEncodeUtil.sha512(WDByteUtil.HEX2Bytes(toHashData)));
			}else{
				log.error("staticDataAuthenticate" + hashStyle + " Hash style is not support");
				return false;
			}
		}catch(Exception e){
			log.error("staticDataAuthenticate to get hash has a exception" + e.getMessage());
			throw new Exception("静态数据认证计算Hash的过程中出现异常" + e.getMessage());
		}
		log.debug("staticDataAuthenticate get hash：" + hashData);
		//恢复数据中的哈希数据
		String resultHashData = resultData.substring(resultData.length() - 42,resultData.length() - 2);
		log.debug("staticDataAuthenticate get hash from recovered data:" + resultHashData);
		//比较两个哈希值
		if (!hashData.equalsIgnoreCase(resultHashData)) {
			log.error("staticDataAuthenticate Hash is not equals!");
			return false;
		}
		return true;
	}
	//print and put loginfo into list
	public void printLog(List<String> logList,String info){
		logList.add(info);
		log.debug(info);
	}
	//init loglist to null
	public void initLogList(List<String> logList){
		for (String calog : logList) {
			logList.add(calog);
		}
		logList.clear();
	}
}
