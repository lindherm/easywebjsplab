package com.gerenhua.tool.logic.issuer.local;

import org.apache.log4j.Logger;

import com.gerenhua.tool.configdao.IssuerKeyInfo;
import com.gerenhua.tool.logic.issuer.IIssuerDao;
import com.watchdata.commons.crypto.WD3DesCryptoUtil;
import com.watchdata.commons.crypto.WDKeyUtil;
import com.watchdata.commons.crypto.pboc.WDPBOCUtil;
import com.watchdata.commons.jce.JceBase.Block;
import com.watchdata.commons.jce.JceBase.Padding;
import com.watchdata.commons.lang.WDByteUtil;
import com.watchdata.commons.lang.WDStringUtil;
/**
 * 
 * @description: 发卡行接口实现
 * @author: juan.jiang Apr 25, 2012
 * @version: 1.0.0
 * @modify:
 * @Copyright: watchdata
 */
public class IssuerDaoImpl implements IIssuerDao{
	private static Logger logger = Logger.getLogger(IssuerDaoImpl.class);	
	private static String authRespCode = "3030000000000000"; 
	private static String iv = "0000000000000000";
	private static boolean isDerive = false;
	
	private IssuerKeyInfo issuerKeyInfo=new IssuerKeyInfo();
	
	@Override
	public String[] generateLoadIssuerScript(String pan, String panSerial, String atc, String arqc, String balance,int tradeAmount) {
		IssuerKeyInfo issuerKey = issuerKeyInfo.getIssuerKeyInfo("ApplicationKey");
		isDerive = issuerKey.getDerive()==0?false:true;
		String sk = calcSessionKey(genCardKey(issuerKey.getMacKey(),pan,panSerial), atc);    
	    int ecBalance = Integer.valueOf(balance);
	    ecBalance += tradeAmount;
	    balance = WDStringUtil.paddingHeadZero(String.valueOf(ecBalance), 12);
	    String macData = "04DA9F790A" + atc + arqc + balance + "80";
	    logger.debug("macData:"+ macData);
	    String[] scripts = new String[1];
	    scripts[0] = "04DA9F790A" + balance + WDPBOCUtil.triple_des_mac(sk,macData,Padding.ZeroBytePadding,iv).substring(0,8);
	    return scripts;
	}

	

	private String genCardKey(String mainKey, String pan, String panSerial) {
		if(isDerive){//分散卡片密钥
			String cardDiverser = pan + panSerial;
			cardDiverser = cardDiverser.substring(cardDiverser.length()-16);
			return WDKeyUtil.deriveKey(mainKey, Block.ECB, cardDiverser);
		}else {//不分散
			return mainKey;
		}
		
	}



	@Override
	public boolean validateArqc(String pan, String panSerial, String cdolData, String aip, String atc, String iad, String arqc) {
		IssuerKeyInfo issuerKey = issuerKeyInfo.getIssuerKeyInfo("ApplicationKey");
		isDerive = issuerKey.getDerive()==0?false:true;
		String sk = calcSessionKey(genCardKey(issuerKey.getAcKey(),pan,panSerial), atc);    
		String acData =  cdolData.substring(0,58) + aip + atc + iad.substring(6, 14);//卡片验证结果
		
		String myAC = WDPBOCUtil.triple_des_mac(sk, acData + "80",Padding.ZeroBytePadding,iv);
		
		if(!arqc.equalsIgnoreCase(myAC)){
			logger.error("arqc validate failed!");
			return false;
		}
		return true;
	}

	@Override
	public String requestArpc(String pan, String panSerial, String cdolData, String aip, String atc, String iad, String arqc) throws Exception {
		IssuerKeyInfo issuerKey = issuerKeyInfo.getIssuerKeyInfo("ApplicationKey");
		isDerive = issuerKey.getDerive()==0?false:true;
		String sk = calcSessionKey(genCardKey(issuerKey.getAcKey(),pan,panSerial), atc);    
		String acData =  cdolData.substring(0,58) + aip + atc + iad.substring(6, 14);//卡片验证结果
		
		String myAC = WDPBOCUtil.triple_des_mac(sk, acData + "80",Padding.ZeroBytePadding,iv);
		
		if(!arqc.equalsIgnoreCase(myAC)){
			logger.error("arpc failed!");
			throw new Exception("arpc failed!");
		}
		
		String tempArqc = xor(arqc,authRespCode);
		String arpc = WD3DesCryptoUtil.ecb_encrypt(sk, tempArqc,Padding.ZeroBytePadding);
		
		return arpc;
	}
	/**
	 * 计算过程密钥
	 * @param mainKey
	 * @param atc
	 * @return
	 */
	private String calcSessionKey(String mainKey,String atc){
		String atc2 = xor("FFFF",atc);
		String str1 = WDKeyUtil.deriveKey(mainKey, Block.ECB, "000000000000" + atc);
		String str2 = WDKeyUtil.deriveKey(mainKey, Block.ECB, "000000000000" + atc2);
	    return  str1+str2;
	}
	
	/**
	 * 字符串异或运算
	 * @param s1
	 * @param s2
	 * @return
	 */
	private String xor(String s1,String s2){
		byte s1B[] = WDByteUtil.HEX2Bytes(s1);
		byte s2B[] = WDByteUtil.HEX2Bytes(s2);
		
		for (int j = 0; j < s1B.length; j++) {
			s1B[j] ^=  s2B[j];
		}
		
		return WDByteUtil.bytes2HEX(s1B);
	}
}
