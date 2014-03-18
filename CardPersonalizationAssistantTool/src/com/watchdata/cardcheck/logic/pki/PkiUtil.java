package com.watchdata.cardcheck.logic.pki;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.watchdata.cardcheck.configdao.PublicKeyInfo;
import com.watchdata.cardcheck.log.Log;
import com.watchdata.cardcheck.utils.CryptoUtil;
import com.watchdata.cardcheck.utils.DigitalTool;
import com.watchdata.commons.lang.WDAssert;
import com.watchdata.commons.lang.WDByteUtil;
import com.watchdata.commons.lang.WDEncodeUtil;

public class PkiUtil {
	public PkiUtil(){
		
	}
	// log5j日志
	private static Log log=new Log();
	private static PublicKeyInfo pk=new PublicKeyInfo();
	/**
	 * 发卡行公钥证书恢复
	 * 
	 * @param rid
	 * @param caKeyIndex
	 * @param cardIssuerKeyCert
	 * @param publicKeySurplus
	 *            发卡行公钥余项
	 * @param publicExponent
	 *            发卡行公钥指数
	 * @param pan
	 *            应用主帐户(PAN)
	 * @return
	 */
	public static CardissuerPublicKeyInfo cardIssuer_pubKey_recover(String rid, String caPubKeyIndex, String cardIssuerPubKeyCert, String publicKeySurplus, String publicExponent, String pan,List<String> caLogList) {
		CardissuerPublicKeyInfo cardissuerPublicKeyInfo = new CardissuerPublicKeyInfo();
		List<String> logList=new ArrayList<String>();
		// 入口参数合法性检查
		if (WDAssert.isEmpty(rid)) {
			log.error("rid cann't be null.");
			return null;
		}

		if (WDAssert.isEmpty(caPubKeyIndex)) {
			log.error("caPubKeyIndex cann't be null.");
			return null;
		}

		if (WDAssert.isEmpty(cardIssuerPubKeyCert)) {
			log.error("cardIssuerPubKeyCert cann't be null.");
			return null;
		}

		if (WDAssert.isEmpty(publicExponent)) {
			log.error("publicExponent cann't be null.");
			return null;
		}

		if (WDAssert.isEmpty(pan)) {
			log.error("pan cann't be null.");
			return null;
		}
		log.debug("CA Public Key Index['8F']:\n"+caPubKeyIndex);
		log.debug("RID:\n"+rid);
		// 通过rid，caPubKeyIndex查询数据库得到密钥对象
		//ICAPublicKeyConfigDao icaPublicKeyConfigDao = (ICAPublicKeyConfigDao) SpringUtil.getBean("iCAkeyconfigDao");
		//CAPublicKeyConfig caPublicKeyConfig = (CAPublicKeyConfig) icaPublicKeyConfigDao.getCAPublicKey(rid, caPubKeyIndex);
		String sectionName = rid+ "_CA_" +caPubKeyIndex;
		PublicKeyInfo publicKeyInfo=pk.getPK(sectionName, rid, caPubKeyIndex);
		
		// 如果密钥不存在 失败
		if (publicKeyInfo == null) {
			log.error("capublickey not exists.");
			cardissuerPublicKeyInfo.setErrorCode("F001");
			return cardissuerPublicKeyInfo;
		}
		log.debug("CA_PK_INFO:\n"+publicKeyInfo.toString());
		// 如果发卡行公钥证书的长度不同于获得的认证中心公钥模长度，那么动态数据认证失败
		if (cardIssuerPubKeyCert.length() != publicKeyInfo.getModule().length()) {
			log.error("capublickey length not equals to cardIssuerPubKeyCert length.");
			cardissuerPublicKeyInfo.setErrorCode("F002");
			return cardissuerPublicKeyInfo;
		}
		//输出ca word日志
		logList.add("CA Public Key Modulus:\n"+publicKeyInfo.getModule());
		logList.add("CA Public Key Exponet:\n"+publicKeyInfo.getExp());
		// 恢复证书（解密）
		String certText = CryptoUtil.rsa_decrypt(publicKeyInfo.getModule(), publicKeyInfo.getExp(), cardIssuerPubKeyCert);

		if (WDAssert.isEmpty(certText)) {
			log.error("rsa_decrypt cert error.");
			cardissuerPublicKeyInfo.setErrorCode("F003");
			return cardissuerPublicKeyInfo;
		}
		log.debug("Issuer Data Recovered :\n"+certText);

		// 截取证书各部分数据存进vo
		cardissuerPublicKeyInfo = changeCertTextToObject(certText);

		// 校检证书信息
		String errCode = checkCertInfo(cardissuerPublicKeyInfo, publicKeySurplus, publicExponent, pan);
		if (!WDAssert.isEmpty(errCode)) {
			if ("SOOO".equals(errCode)) {
				cardissuerPublicKeyInfo.setSuccess(true);
				cardissuerPublicKeyInfo.setErrorCode(errCode);
				logList.add("Check Issuer PK Certificate--PASS!");
				log.debug("Issuer Public Key Certificate Hash is verified!\n");
			} else {
				cardissuerPublicKeyInfo.setErrorCode(errCode);
				return cardissuerPublicKeyInfo;
			}
		}

		// 组装模值
		int Nca=publicKeyInfo.getModule().length()/2;
		String NrStr=cardissuerPublicKeyInfo.getPublicKeyLength();
		//发卡行公钥长度
		int Nr=DigitalTool.hexStringToAlgorism(NrStr);
		if (Nr<=Nca-36) {
			//去掉填充的NBB个字节的BB
			int Nbb=Nca-36-Nr;
			String tempMod=cardissuerPublicKeyInfo.getPubMod();
			tempMod=tempMod.substring(0, tempMod.length()-Nbb*2);
			cardissuerPublicKeyInfo.setPubMod(tempMod);
		}else {
			if (WDAssert.isEmpty(publicKeySurplus)) {
				log.error("publicKeySurplus is null");
				cardissuerPublicKeyInfo.setSuccess(false);
				cardissuerPublicKeyInfo.setErrorCode("F012");
			}
			StringBuilder pubMod = new StringBuilder();
			pubMod.append(cardissuerPublicKeyInfo.getPubMod()).append(publicKeySurplus);
			cardissuerPublicKeyInfo.setPubMod(pubMod.toString());
		}
		//填充数据
		for (int i = 0; i < logList.size(); i++) {
			caLogList.add("");
		}
		
		Collections.copy(caLogList, logList);

		return cardissuerPublicKeyInfo;
	}

	/**
	 * ic卡公钥证书恢复
	 * 
	 * @param icCardCert
	 * @param cardIssuerKeyPubMod
	 * @param icPublicKeySurplus
	 *            ic卡公钥余数
	 * @param  icPublicExponent
	 * 			ic卡公钥指数
	 * @param issurePublicExponent
	 *            发卡行公钥指数
	 * @param pan
	 *            主账号
	 * @return
	 */
	public static IcCardPublicKeyInfo icCard_pubKey_recover(String icCardCert, String cardIssuerPubKeyPubMod, String icPublicKeySurplus, String issurePublicExponent,String icPublicExponent, String pan, String sad,List<String> caLogList) {
		IcCardPublicKeyInfo icCardPublicKeyInfo = new IcCardPublicKeyInfo();
		List<String> logList=new ArrayList<String>();
		// 入口参数合法性检查
		if (WDAssert.isEmpty(cardIssuerPubKeyPubMod)) {
			log.error("cardIssuerPubKeyPubMod cann't be null.");
			return null;
		}

		if (WDAssert.isEmpty(issurePublicExponent)) {
			log.error("publicExponent cann't be null.");
			return null;
		}

		if (WDAssert.isEmpty(pan)) {
			log.error("pan cann't be null.");
			return null;
		}

		// 如果IC卡公钥证书的长度不同于在前面的章条中获得的发卡行公钥模长度
		if (icCardCert.length() != cardIssuerPubKeyPubMod.length()) {
			log.error("capublickey length not equals to cardIssuerPubKeyCert length.");
			icCardPublicKeyInfo.setErrorCode("F101");
			return icCardPublicKeyInfo;
		}

		// 恢复证书（解密）
		String certText = CryptoUtil.rsa_decrypt(cardIssuerPubKeyPubMod, issurePublicExponent, icCardCert);

		if (WDAssert.isEmpty(certText)) {
			log.error("rsa_decrypt cert error.");
			icCardPublicKeyInfo.setErrorCode("F102");
			return icCardPublicKeyInfo;
		}
		log.debug("ICC Data Recovered : \n"+certText);
		// 截取证书各部分数据存进vo
		icCardPublicKeyInfo = changeCertTextToObject_Ic(certText);

		// 校检证书信息
		String errCode = checkCertInfo_ic(icCardPublicKeyInfo, icPublicKeySurplus, icPublicExponent, pan, sad);
		if (!WDAssert.isEmpty(errCode)) {
			if ("SOOO".equals(errCode)) {
				icCardPublicKeyInfo.setSuccess(true);
				icCardPublicKeyInfo.setErrorCode(errCode);
				//word日志
				logList.add("Check ICC PK Certificate--PASS");
				log.debug("ICC Public Key Certificate Hash is verified!\n");
			} else {
				icCardPublicKeyInfo.setErrorCode(errCode);
				return icCardPublicKeyInfo;
			}
		}

		
		int Nr=cardIssuerPubKeyPubMod.length()/2;
		String NrStr=icCardPublicKeyInfo.getPublicKeyLength();
		//发卡行公钥长度
		int Nic=DigitalTool.hexStringToAlgorism(NrStr);
		if (Nic<=Nr-42) {
			//去掉填充的NBB个字节的BB
			int Nbb=Nr-42-Nic;
			String tempMod=icCardPublicKeyInfo.getPubMod();
			tempMod=tempMod.substring(0, tempMod.length()-Nbb*2);
			icCardPublicKeyInfo.setPubMod(tempMod);
		}else {
			if (WDAssert.isEmpty(icPublicKeySurplus)) {
				log.error("publicKeySurplus is null");
				icCardPublicKeyInfo.setSuccess(false);
				icCardPublicKeyInfo.setErrorCode("F112");
			}
			StringBuilder pubMod = new StringBuilder();
			pubMod.append(icCardPublicKeyInfo.getPubMod()).append(icPublicKeySurplus);
			icCardPublicKeyInfo.setPubMod(pubMod.toString());
		}

		for (int i = 0; i < logList.size(); i++) {
			caLogList.add("");
		}
		
		Collections.copy(caLogList, logList);
		
		return icCardPublicKeyInfo;
	}

	/**
	 * 将证书明文字符串转换成证书对象
	 * 
	 * @param certText
	 * @return
	 */
	private static CardissuerPublicKeyInfo changeCertTextToObject(String certText) {
		CardissuerPublicKeyInfo cardissuerPublicKeyInfo = new CardissuerPublicKeyInfo();
		// 6A BC
		cardissuerPublicKeyInfo.setHead(certText.substring(0, 2));
		cardissuerPublicKeyInfo.setRail(certText.substring(certText.length() - 2, certText.length()));
		// 去掉6A和BC
		String st = certText.substring(2, certText.length() - 2);
		int pos = 0;
		// 证书格式
		String certType = st.substring(pos, pos + 2);
		cardissuerPublicKeyInfo.setCertType(certType);
		pos += 2;
		// 发卡行标识
		String cid = st.substring(pos, pos + 8);
		cardissuerPublicKeyInfo.setCid(cid);
		pos += 8;
		// 证书失效日期
		String expireDate = st.substring(pos, pos + 4);
		cardissuerPublicKeyInfo.setExpireDate(expireDate);
		pos += 4;
		// 证书序列号
		String csid = st.substring(pos, pos + 6);
		cardissuerPublicKeyInfo.setCsid(csid);
		pos += 6;
		// 哈希算法标识
		String hashMech = st.substring(pos, pos + 2);
		cardissuerPublicKeyInfo.setHashMech(hashMech);
		pos += 2;
		// 发卡行公钥算法标识
		String publicKeyMech = st.substring(pos, pos + 2);
		cardissuerPublicKeyInfo.setPublicKeyMech(publicKeyMech);
		pos += 2;
		// 发卡行公钥长度
		String publicKeyLength = st.substring(pos, pos + 2);
		cardissuerPublicKeyInfo.setPublicKeyLength(publicKeyLength);
		pos += 2;
		// 发卡行公钥指数长度
		String publicExponentLength = st.substring(pos, pos + 2);
		cardissuerPublicKeyInfo.setPublicExponentLength(publicExponentLength);
		pos += 2;
		// 最左边字节
		String pubMod = st.substring(pos, st.length() - 40);
		cardissuerPublicKeyInfo.setPubMod(pubMod);
		pos += 2;

		// hash值
		String hash = st.substring(st.length() - 40, st.length());
		cardissuerPublicKeyInfo.setHash(hash);

		return cardissuerPublicKeyInfo;
	}

	/**
	 * 将证书明文字符串转换成ic卡证书对象
	 * 
	 * @param certText
	 * @return
	 */
	private static IcCardPublicKeyInfo changeCertTextToObject_Ic(String certText) {
		IcCardPublicKeyInfo icCardPublicKeyInfo = new IcCardPublicKeyInfo();
		// 6A BC
		icCardPublicKeyInfo.setHead(certText.substring(0, 2));
		icCardPublicKeyInfo.setRail(certText.substring(certText.length() - 2, certText.length()));
		// 去掉6A和BC
		String st = certText.substring(2, certText.length() - 2);
		int pos = 0;
		// 证书格式
		String certType = st.substring(pos, pos + 2);
		icCardPublicKeyInfo.setCertType(certType);
		pos += 2;
		// 发卡行标识
		String pan = st.substring(pos, pos + 20);
		icCardPublicKeyInfo.setPan(pan);
		pos += 20;
		// 证书失效日期
		String expireDate = st.substring(pos, pos + 4);
		icCardPublicKeyInfo.setExpireDate(expireDate);
		pos += 4;
		// 证书序列号
		String csid = st.substring(pos, pos + 6);
		icCardPublicKeyInfo.setCsid(csid);
		pos += 6;
		// 哈希算法标识
		String hashMech = st.substring(pos, pos + 2);
		icCardPublicKeyInfo.setHashMech(hashMech);
		pos += 2;
		// ic公钥算法标识
		String publicKeyMech = st.substring(pos, pos + 2);
		icCardPublicKeyInfo.setPublicKeyMech(publicKeyMech);
		pos += 2;
		// ic公钥长度
		String publicKeyLength = st.substring(pos, pos + 2);
		icCardPublicKeyInfo.setPublicKeyLength(publicKeyLength);
		pos += 2;
		// ic公钥指数长度
		String publicExponentLength = st.substring(pos, pos + 2);
		icCardPublicKeyInfo.setPublicExponentLength(publicExponentLength);
		pos += 2;
		// 最左边字节
		String pubMod = st.substring(pos, st.length() - 40);
		icCardPublicKeyInfo.setPubMod(pubMod);
		pos += 2;

		// hash值
		String hash = st.substring(st.length() - 40, st.length());
		icCardPublicKeyInfo.setHash(hash);

		return icCardPublicKeyInfo;
	}

	/**
	 * 校检恢复的证书数据
	 * 
	 * @param cardissuerPublicKeyInfo
	 * @return
	 */
	private static String checkCertInfo(CardissuerPublicKeyInfo cardissuerPublicKeyInfo, String publicKeySurplus, String publicExponent, String pan) {
		// 如果恢复数据的结尾不等于BC
		if (!"BC".equals(cardissuerPublicKeyInfo.getRail())) {
			log.error("cert must endWith BC.");
			return "F004";
		}

		// 检查恢复数据头。如果它不是6A
		if (!"6A".equals(cardissuerPublicKeyInfo.getHead())) {
			log.error("cert must startWith 6A.");
			return "F005";
		}
		// 检查证书格式
		if (!"02".equals(cardissuerPublicKeyInfo.getCertType())) {
			log.error("certType error.");
			return "F006";
		}
		// 签名的数据2-10项数据+公钥余项（可选）+公钥指数
		if (publicKeySurplus==null) {
			publicKeySurplus="";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(cardissuerPublicKeyInfo.getCertType()).append(cardissuerPublicKeyInfo.getCid()).append(cardissuerPublicKeyInfo.getExpireDate());
		sb.append(cardissuerPublicKeyInfo.getCsid()).append(cardissuerPublicKeyInfo.getHashMech()).append(cardissuerPublicKeyInfo.getPublicKeyMech());
		sb.append(cardissuerPublicKeyInfo.getPublicKeyLength()).append(cardissuerPublicKeyInfo.getPublicExponentLength()).append(cardissuerPublicKeyInfo.getPubMod());
		sb.append(publicKeySurplus).append(publicExponent);
		
		log.debug("Issuer Input Data for Hash :\n"+sb.toString());
		
		// hash算法标识
		String hashMech = cardissuerPublicKeyInfo.getHashMech();
		log.debug("HashMech :\n"+hashMech);
		log.debug("Issuer Hash retrived from Certificate:\n"+cardissuerPublicKeyInfo.getHash());
		
		// 按照指定的算法计算hash值
		if ("01".equals(hashMech)) {
			String wdhash = WDByteUtil.bytes2HEX(WDEncodeUtil.sha1(WDByteUtil.HEX2Bytes(sb.toString())));
			// 与证书中的比较
			if (!wdhash.equals(cardissuerPublicKeyInfo.getHash())) {
				log.error("hash is wrong.");
				return "F007";
			}
			log.debug("Issuer Hash Recomputed :\n"+wdhash);
		} else {
			log.error("hashmech not support.");
			return "F008";
		}

		// 检验发卡行识别号是否匹配主账号最左面的3-8个数字
		String cid = cardissuerPublicKeyInfo.getCid();
		// 去掉F
		int fpos = cid.indexOf("F");
		cid = cid.substring(0, fpos);

		// 检查cid
		if (!pan.startsWith(cid)) {
			log.error("cid is wrong.");
			return "F009";
		}

		// 检查证书失效日期
		String expireDate = cardissuerPublicKeyInfo.getExpireDate();
		String year = "20" + expireDate.substring(2);
		String month = expireDate.substring(0, 2);
		// 当前年和月
		Calendar calendar = Calendar.getInstance();
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth = calendar.get(Calendar.MONTH);

		if (Integer.valueOf(year) < nowYear) {
			log.error("cert is expired.");
			return "F010";
		} else if (Integer.valueOf(year) == nowYear) {
			if (Integer.valueOf(month) < nowMonth) {
				log.error("cert is expired.");
				return "F010";
			}
		}

		// 检查发卡行公钥算法标识
		if (!"01".equals(cardissuerPublicKeyInfo.getPublicKeyMech())) {
			log.error("publickeymech is not support.");
			return "F011";
		}

		return "SOOO";
	}

	/**
	 * 检查修复的证书数据ic
	 * 
	 * @param cardissuerPublicKeyInfo
	 * @param publicKeySurplus
	 * @param publicExponent
	 * @param pan
	 * @return
	 */
	private static String checkCertInfo_ic(IcCardPublicKeyInfo icCardPublicKeyInfo, String publicKeySurplus, String publicExponent, String pan, String sad) {
		// 如果恢复数据的结尾不等于BC
		if (!"BC".equals(icCardPublicKeyInfo.getRail())) {
			log.error("cert must endWith BC.");
			return "F103";
		}

		// 检查恢复数据头。如果它不是6A
		if (!"6A".equals(icCardPublicKeyInfo.getHead())) {
			log.error("cert must startWith 6A.");
			return "F104";
		}
		// 检查证书格式
		if (!"04".equals(icCardPublicKeyInfo.getCertType())) {
			log.error("certType error.");
			return "F105";
		}
		// 签名的数据2-10项数据+公钥余项（可选）+公钥指数
		if (publicKeySurplus==null) {
			publicKeySurplus="";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(icCardPublicKeyInfo.getCertType()).append(icCardPublicKeyInfo.getPan()).append(icCardPublicKeyInfo.getExpireDate());
		sb.append(icCardPublicKeyInfo.getCsid()).append(icCardPublicKeyInfo.getHashMech()).append(icCardPublicKeyInfo.getPublicKeyMech());
		sb.append(icCardPublicKeyInfo.getPublicKeyLength()).append(icCardPublicKeyInfo.getPublicExponentLength()).append(icCardPublicKeyInfo.getPubMod());
		sb.append(publicKeySurplus).append(publicExponent);
		sb.append(sad);
		log.debug("Input Data for Hash (for ICC Public Key Certificate) :\n"+sb.toString());
		// hash算法标识
		String hashMech = icCardPublicKeyInfo.getHashMech();
		log.debug("Hash Recovered from ICC Publick Key Certificate :\n"+icCardPublicKeyInfo.getHash());
		// 按照指定的算法计算hash值
		if ("01".equals(hashMech)) {
			String wdhash = WDByteUtil.bytes2HEX(WDEncodeUtil.sha1(WDByteUtil.HEX2Bytes(sb.toString())));
			// 与证书中的比较
			if (!wdhash.equals(icCardPublicKeyInfo.getHash())) {
				log.error("hash is wrong.");
				return "F106";
			}
			log.debug("ICC Hash Recomputed : \n"+wdhash);
		} else {
			log.error("hashmech not support.");
			return "F107";
		}

		// 比较恢复得到的主账号和从IC卡读出的应用主账号是否相同
		String certpan = icCardPublicKeyInfo.getPan();
		// 去掉F
		int fpos = certpan.indexOf("F");
		certpan = certpan.substring(0, fpos);
		// 比较
		if (!certpan.equals(pan)) {
			log.error("pan is wrong.");
			return "F108";
		}

		// 检查证书失效日期
		String expireDate = icCardPublicKeyInfo.getExpireDate();
		String year = "20" + expireDate.substring(2);
		String month = expireDate.substring(0, 2);
		// 当前年和月
		Calendar calendar = Calendar.getInstance();
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth = calendar.get(Calendar.MONTH) + 1;

		if (Integer.valueOf(year) < nowYear) {
			log.error("cert is expired.");
			return "F109";
		} else if (Integer.valueOf(year) == nowYear) {
			if (Integer.valueOf(month) < nowMonth) {
				log.error("cert is expired.");
				return "F109";
			}
		}

		// 检查发卡行公钥算法标识
		if (!"01".equals(icCardPublicKeyInfo.getPublicKeyMech())) {
			log.error("publickeymech is not support.");
			return "F110";
		}

		return "SOOO";
	}
}
