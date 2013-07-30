package com.watchdata.cardcheck.logic.pki;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.watchdata.commons.lang.WDByteUtil;
import com.watchdata.commons.lang.WDEncodeUtil;

public class RSAUtil {
	public static final String RSA_ECB_NOPADDING = "RSA/ECB/NoPadding";

	public static byte[] Decrypt(byte[] mess, byte[] modulus, byte[] exponent, String transformation) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		Cipher cipher = Cipher.getInstance(transformation);
		RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(new BigInteger(1, modulus), new BigInteger(1, exponent));
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
		cipher.init(2, privateKey);
		byte[] result = cipher.doFinal(mess);
		return result;
	}

	public static byte[] Encrypt(byte[] mess, byte[] modulus, byte[] exponent, String transformation) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		Cipher cipher = Cipher.getInstance(transformation);
		RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(1, modulus), new BigInteger(1, exponent));
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
		cipher.init(1, publicKey);
		byte[] result = cipher.doFinal(mess);
		return result;
	}

	public static void main(String[] args) throws Exception {
		String caPKModule = "EB374DFC5A96B71D2863875EDA2EAFB96B1B439D3ECE0B1826A2672EEEFA7990286776F8BD989A15141A75C384DFC14FEF9243AAB32707659BE9E4797A247C2F0B6D99372F384AF62FE23BC54BCDC57A9ACD1D5585C303F201EF4E8B806AFB809DB1A3DB1CD112AC884F164A67B99C7D6E5A8A6DF1D3CAE6D7ED3D5BE725B2DE4ADE23FA679BF4EB15A93D8A6E29C7FFA1A70DE2E54F593D908A3BF9EBBD760BBFDC8DB8B54497E6C5BE0E4A4DAC29E5";
		String caPKExp = "03";

		// 90
		String issuerPKCert = "BC56F18EBB9BDD64ED5F18FFBABBB0DA88FCE7BAFA6373E9CDFC1D146A2683194F5383832ACE1665AD393C03BA0692B231DDDE9C42FEF3E1375B6C28FE2AC86FF6007278D20D28B62E0985B1E9BDDBFF04B8A3FD6ADFEB38636251E28E050EE1A48CFF4CB2CB1775EC33FAC23460B391664FD9906BAB8EB54E49051B81E6864B8DBDC3E48D4754F8F66794895351C8C90CC113254DE578B94868F5D93DD42B0CD9C1A27CED4620D8CF05FC347AA38F31";
		// 92
		String issuerPKReminder = "0A4328A298037D181C0E716025C2F5DED26E342F240F8E04EDE5884AA6FD14564B912359";// tag92

		// 9F46
		String icPKCert = "93D0B9BF08D9CE59C3966B6A3D1C7E53C1BEDFF351235EC5039B803DF05C3E43FCD5179C0F3B43E67316BC71B62E5F05D81AB20A4B84AFCB75E6B03B1821363ED7B70DAAEDE2C2B6ED69EA9DE0D2560EA410752C077EE6772FB50D508CE8381069DB5FCB70CD5DF2A91FEBC64C94E48B4B4917D4D07A30BE6EFBD73AE54381C10DF79AB057B186A06319B413E574BC898C81379D218AD03A33C1F5A7E000D7B2E2C0570A960B81C8C3D6B78FA85C613C";
		// 9F47
		String icPKExp = "03";
		// 9F48
		String icPKReminder = "450549787C351479462F";

		// 恢复发卡行公钥

		String recovedIssurePKCert = WDByteUtil.bytes2HEX(RSAUtil.Decrypt(WDByteUtil.HEX2Bytes(issuerPKCert), WDByteUtil.HEX2Bytes(caPKModule), WDByteUtil.HEX2Bytes(caPKExp), RSA_ECB_NOPADDING));
		System.out.println("恢复后的发卡行公钥证书 - " + recovedIssurePKCert);
		String issuerPKModule = recovedIssurePKCert.substring(30, recovedIssurePKCert.length() - 42) + issuerPKReminder;
		System.out.println("发卡行公钥模 - " + issuerPKModule);

		// 恢复IC卡公钥
		String recovedICPKCert = WDByteUtil.bytes2HEX(RSAUtil.Decrypt(WDByteUtil.HEX2Bytes(icPKCert), WDByteUtil.HEX2Bytes(issuerPKModule), WDByteUtil.HEX2Bytes(icPKExp), RSA_ECB_NOPADDING));
		System.out.println("恢复后的IC公钥证书 - " + recovedICPKCert);
		String icPKModule = recovedICPKCert.substring(42, recovedIssurePKCert.length() - 42) + icPKReminder;
		System.out.println("IC公钥模 - " + icPKModule);

		// SDA
		// 93
		String signedStaticData = "5F57680771CBF057ACCD278EF2142F5856A26F00A31699C486854CDD3C4EC4ED0F1FC6494261C8821AA174E60D564668BBE1DD4A6B4C858C2CFD3E0B5C62D2A2ECCEC73E1D44CF0BB43BE2A3BB053AA11C82E9B2FBF2579088992E6194A976B0A635150ECD5CB042F22FCE6A261FB491A84F8CC4913EF7C5CE6E2F54E807D0FD8BD1DAFAA252A8BC07EC64B240E2850BF9D3D84DAC5AF3E3F645B223B719D766044E753E907910FFE08074170B766292";
		// +7C00
		String staticDataList = "5F24031608315A096205220000000009395F3401009F0702FF008E0E0000000000000000420301031F009F0D05D8609CA8009F0E0500100000009F0F05D8689CF8005F280201567C00";

		String recovedStaticData = WDByteUtil.bytes2HEX(RSAUtil.Decrypt(WDByteUtil.HEX2Bytes(signedStaticData), WDByteUtil.HEX2Bytes(issuerPKModule), WDByteUtil.HEX2Bytes(icPKExp), RSA_ECB_NOPADDING));
		System.out.println("恢复后的SAD - " + recovedStaticData);
		String oriHashData = recovedStaticData.substring(2, recovedStaticData.length() - 42) + staticDataList;
		System.out.println("SAD hash data - " + oriHashData);
		String hash = WDByteUtil.bytes2HEX(WDEncodeUtil.sha1(WDByteUtil.HEX2Bytes(oriHashData)));
		System.out.println("SAD hash - " + hash);

		// DDA
		String signedDynmicData = "973FA5981298B170945A1A4878BFCAFFB0211062EFA5661CC6E0798A723D4CF4E883C196A656A090AD713DDDA526193B9137E6A3025657FBD85D86905B5ECA43A33E83932D86B548689E43FAE99C28C69D6F7B17AB119BFC56CE06BA726DCDF46FFFD79EB88B11E49C658CB94C10457A77365BA3DDF153B5918024C1B28DF3FCABE31227903990CD181385FA8571F059";
		String dDolList = "D8A5E2B0";// 终端随机数
		String recovedDynmicData = WDByteUtil.bytes2HEX(RSAUtil.Decrypt(WDByteUtil.HEX2Bytes(signedDynmicData), WDByteUtil.HEX2Bytes(icPKModule), WDByteUtil.HEX2Bytes(icPKExp), RSA_ECB_NOPADDING));
		System.out.println("恢复后的DAD - " + recovedDynmicData);
		oriHashData = recovedDynmicData.substring(2, recovedDynmicData.length() - 42) + dDolList;
		System.out.println("DAD hash data - " + oriHashData);
		hash = WDByteUtil.bytes2HEX(WDEncodeUtil.sha1(WDByteUtil.HEX2Bytes(oriHashData)));
		System.out.println("DAD hash - " + hash);

	}
}