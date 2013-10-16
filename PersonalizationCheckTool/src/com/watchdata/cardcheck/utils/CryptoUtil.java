package com.watchdata.cardcheck.utils;

import java.security.interfaces.RSAPublicKey;

import org.apache.log4j.Logger;

import com.watchdata.commons.crypto.WDKeyUtil;
import com.watchdata.commons.crypto.WDRsaCryptoUtil;
import com.watchdata.commons.jce.JceBase.Padding;
import com.watchdata.commons.lang.WDAssert;
import com.watchdata.commons.lang.WDByteUtil;

/**
 * 安全算法帮助类
 * 
 * @author liya.xiao
 * 
 */
public class CryptoUtil {
	// log5j打日志
	private static Logger log = Logger.getLogger(CryptoUtil.class);

	/**
	 * rsa解密函数
	 * 
	 * @param modulus
	 *            公钥模值
	 * @param publicExponent
	 *            公钥指数
	 * @param decryptData
	 *            解密数据
	 * @return
	 */
	public static String rsa_decrypt(String modulus, String publicExponent, String decryptData) {
		// 入口数据校检
		if (WDAssert.isEmpty(modulus)) {
			log.error("modulus cann't be null.");
			return "";
		}

		if (WDAssert.isEmpty(publicExponent)) {
			log.error("publicExponent cann't be null.");
			return "";
		}

		if (!publicExponent.equals("03") && !publicExponent.equals("010001")) {
			log.error("mech is not support.");
			return "";
		}

		if (WDAssert.isEmpty(decryptData)) {
			log.error("decryptData cann't be null.");
			return "";
		}
		// 解密结果声明
		String result = "";
		// 公钥公钥模值数据补充
		StringBuilder sb = new StringBuilder();
		sb.append("00").append(modulus);
		// 生成公钥对象
		RSAPublicKey publicKey = WDKeyUtil.generateRSAPublicKey(WDByteUtil.HEX2Bytes(sb.toString()), WDByteUtil.HEX2Bytes(publicExponent));
		// 调用wd-coder中的rsa解密算法解密数据
		result = WDRsaCryptoUtil.rsa_decrypt(publicKey, decryptData, Padding.NoPadding);

		return result;
	}

	public static void main(String[] args) {
		/*String out = CryptoUtil.rsa_decrypt("C2ABE763CD75D57DDCD34CF632AA27F5E95A5204562C2D39E9460774C761B86573E9D4C1B5AC4DADA9F42F9217712B73D5A66E29EA8E0274085FF633CB8EBBFAFB13F8BC826384E1522FAB4FC4545818CB6F416585845E7E64B721A34BE48FAEF0B078DCBADEBE5FFA22A747FFABC8ECF62FE4B096949FAE88A331792873163BECD90D75D8F1570F47ED40F78690B7FB", "03", "472CE6B28C5F7D0E3AB519B58D37775DC31107A1C9AE7F9352326DB6FF3C3E70B137EBD559300DCF808B2B529F0EE5AE9DB09BDCBC55A15D4F14D80EB29D81138242E1D1AC0A6051321454F336D987B920DA7281C5B08A7D8FF332506AE06FFC8EB7FFC66A7D202B2FF03AD99F4C4B9B476A13E733EA69074E465970EA7358FF8F4B3CA8C7F36E5798390B8975144FBB");
		System.out.println(out);*/
	}
}
