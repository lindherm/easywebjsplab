package com.watchdata.cardcheck.logic.apdu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.watchdata.cardcheck.logic.Constants;
import com.watchdata.cardcheck.logic.apdu.pcsc.PcscChannel;
import com.watchdata.commons.crypto.WD3DesCryptoUtil;
import com.watchdata.commons.crypto.pboc.WDPBOCUtil;
import com.watchdata.commons.jce.JceBase.Padding;
import com.watchdata.commons.lang.WDAssert;
import com.watchdata.commons.lang.WDStringUtil;

/**
 * 
 * @description: 普通指令模块封装
 * @author: juan.jiang 2012-3-21
 * @version: 1.0.0
 * @modify:
 * @Copyright: watchdata
 */
public class CommonAPDU extends AbstractAPDU {
	private IAPDUChannel apduChannel;
	private String secureityLevel;
	private String encKey;
	private String macKey;
	private String delKey;
	private String smac;

	public String getSmac() {
		return smac;
	}

	public void setSmac(String smac) {
		this.smac = smac;
	}

	public String getEncKey() {
		return encKey;
	}

	public void setEncKey(String encKey) {
		this.encKey = encKey;
	}

	public String getMacKey() {
		return macKey;
	}

	public void setMacKey(String macKey) {
		this.macKey = macKey;
	}

	public String getDelKey() {
		return delKey;
	}

	public void setDelKey(String delKey) {
		this.delKey = delKey;
	}

	public String getSecureityLevel() {
		return secureityLevel;
	}

	public void setSecureityLevel(String secureityLevel) {
		this.secureityLevel = secureityLevel;
	}

	public CommonAPDU() {
		apduChannel = new PcscChannel();
	}

	/**
	 * 复位指令
	 * 
	 * @return
	 */
	public HashMap<String, String> reset(String readerName) {
		HashMap<String, String> res = new HashMap<String, String>();
		boolean flag = apduChannel.init(readerName);
		if (flag) {
			String responseApdu = apduChannel.reset();
			String sw = responseApdu.substring(responseApdu.length() - 4, responseApdu.length());
			String atr = responseApdu.substring(0, responseApdu.length() - 4);
			if (("9000").equals(sw)) {
				res.put("sw", "9000");
				res.put("atr", atr);
			}
		}
		return res;
	}

	/**
	 * 选择指令
	 * 
	 * @param name
	 *            文件或目录名
	 * @return
	 */
	public HashMap<String, String> select(String name) {
		HashMap<String, String> result = new HashMap<String, String>();
		String commandApdu = packApdu("SELECT", name);
		String responseApdu = apduChannel.send(commandApdu);
		result = unpackApdu(responseApdu);
		result.put("apdu", commandApdu);
		result.put("res", responseApdu.substring(0, responseApdu.length() - 4));
		return result;
	}

	/**
	 * 读记录指令
	 * 
	 * @param sfi
	 *            短文件标识
	 * @param record
	 *            记录号
	 * @return
	 */
	public HashMap<String, String> readRecord(String sfi, String record) {
		HashMap<String, String> result = new HashMap<String, String>();

		String commandApdu = packApdu("READ_RECORD", "", record, sfi);
		String responseApdu = apduChannel.send(commandApdu);

		result = unpackApdu(responseApdu);

		result.put("apdu", commandApdu);
		result.put("res", responseApdu.substring(0, responseApdu.length() - 4));
		return result;

	}

	/**
	 * 读目录指令
	 * 
	 * @param sfi
	 * @return
	 */
	public List<HashMap<String, String>> readDir(String sfi) {
		List<HashMap<String, String>> dirList = new ArrayList<HashMap<String, String>>();
		int b = Integer.parseInt(sfi);
		b = (b << 3) + 4;
		int index = Integer.parseInt(sfi);
		String responseApdu = "";
		while (true) {
			String commandApdu = packApdu("READ_RECORD", "", WDStringUtil.paddingHeadZero(String.valueOf(index), 2), WDStringUtil.paddingHeadZero(Integer.toHexString(b), 2));
			responseApdu = apduChannel.send(commandApdu);
			if (Constants.SW_SUCCESS.equalsIgnoreCase(responseApdu.substring(responseApdu.length() - 4))) {
				dirList.add(unpackApdu(responseApdu));
			} else {
				break;
			}
			index++;
		}

		return dirList;
	}

	/**
	 * GPO指令
	 * 
	 * @param loadGPO
	 *            PDOL参数
	 * @return
	 */
	public HashMap<String, String> gpo(String loadGPO) {
		String commandApdu = packApdu("GPO", loadGPO);
		String responseApdu = apduChannel.send(commandApdu);
		HashMap<String, String> result = unpackApdu(responseApdu);
		if (Constants.SW_SUCCESS.equalsIgnoreCase(result.get("sw"))) {
			if (WDAssert.isNotEmpty(result.get("80"))) {
				result.put("82", result.get("80").substring(0, 4));
				result.put("94", result.get("80").substring(4));
			}
		}
		result.put("apdu", commandApdu);
		result.put("res", responseApdu.substring(0, responseApdu.length() - 4));

		return result;
	}

	/**
	 * 内部认证指令
	 * 
	 * @param random
	 *            终端随机数
	 * @return
	 */
	public HashMap<String, String> internalAuthenticate(String random) {
		HashMap<String, String> result = new HashMap<String, String>();
		String commandApdu = packApdu("INTERNAL_AUTHENTICATE", random);
		String responseApdu = apduChannel.send(commandApdu);
		result = unpackApdu(responseApdu);
		result.put("apdu", commandApdu);
		result.put("res", responseApdu.substring(0, responseApdu.length() - 4));
		return result;
	}

	/**
	 * 外部认证指令
	 * 
	 * @param iad
	 *            发卡行认证数据
	 * @return
	 */
	public HashMap<String, String> externalAuthenticate(String iad) {
		HashMap<String, String> result = new HashMap<String, String>();
		String commandApdu = packApdu("EXTERNAL_AUTHENTICATE", iad);
		String responseApdu = apduChannel.send(commandApdu);
		result = unpackApdu(responseApdu);
		result.put("apdu", commandApdu);
		result.put("res", responseApdu);
		return result;
	}

	/**
	 * 生成AC指令
	 * 
	 * @return
	 */
	public HashMap<String, String> generateAC(String data, String p1) {
		String commandApdu = packApdu("GENERATE_AC", data, p1);
		String responseApdu = apduChannel.send(commandApdu);
		HashMap<String, String> result = unpackApdu(responseApdu);
		if (Constants.SW_SUCCESS.equalsIgnoreCase(result.get("sw"))) {
			if (WDAssert.isNotEmpty(result.get("80"))) {
				result.put("9F27", result.get("80").substring(0, 2)); // CID
				result.put("9F36", result.get("80").substring(2, 6)); // ATC
				result.put("9F26", result.get("80").substring(6, 22)); // AC密文
				result.put("9F10", result.get("80").substring(22));// 发卡行应用数据
				result.put("apdu", commandApdu);
				result.put("res", responseApdu.substring(0, responseApdu.length() - 4));
			}
		}

		return result;
	}

	/**
	 * put data指令
	 * 
	 * @return
	 */
	public HashMap<String, String> putData(String data) {
		HashMap<String, String> result = new HashMap<String, String>();
		String responseApdu = apduChannel.send(data);
		result = unpackApdu(responseApdu);
		result.put("apdu", data);
		result.put("res", responseApdu);
		return result;
	}

	/**
	 * get data指令
	 * 
	 * @param tag
	 * @return
	 */
	public HashMap<String, String> getData(String tag) {
		String commandApdu = packApdu("GET_DATA", "", tag.substring(0, 2), tag.substring(2, 4));
		String responseApdu = apduChannel.send(commandApdu);
		return unpackApdu(responseApdu);
	}

	/**
	 * pin验证
	 * 
	 * @param pin
	 * @return
	 */
	public HashMap<String, String> verifyPin(String pin) {
		String commandApdu = packApdu("VERIFY", pin);
		String responseApdu = apduChannel.send(commandApdu);
		return unpackApdu(responseApdu);
	}

	/**
	 * externalAuthenticate
	 * 
	 * @param secureity_level
	 * @param keyVersion
	 * @param keyId
	 * @param kenc
	 * @param kmac
	 * @param kdek
	 */
	public void externalAuthenticate(String secureity_level, String keyVersion, String keyId, String kenc, String kmac, String kdek) {
		String data;
		String hostRandom = WDStringUtil.getRandomHexString(16);
		// initializeUpdate
		String strResp = apduChannel.send("8050" + keyVersion + keyId + "08" + hostRandom);

		String Rcard = strResp.substring(24, 40); // random of card
		String Rter = hostRandom; // random of terminal

		String resp = strResp.substring(24, 28);

		String encKey = WD3DesCryptoUtil.cbc_encrypt(kenc, "0182" + resp + "000000000000000000000000", Padding.NoPadding, "0000000000000000");
		String macKey = WD3DesCryptoUtil.cbc_encrypt(kmac, "0101" + resp + "000000000000000000000000", Padding.NoPadding, "0000000000000000");
		String dekKey = WD3DesCryptoUtil.cbc_encrypt(kdek, "0181" + resp + "000000000000000000000000", Padding.NoPadding, "0000000000000000");
		setEncKey(encKey);
		setMacKey(macKey);
		setDelKey(dekKey);
		// host
		data = Rcard + Rter + "8000000000000000";
		String Host = WD3DesCryptoUtil.cbc_encrypt(encKey, data, Padding.NoPadding, "0000000000000000");
		Host = Host.substring(Host.length() - 16);
		// Smac
		data = "8482" + secureity_level + "0010" + Host;
		data = data + "80";
		while (data.length() % 16 != 0) {
			data = data + "00";
		}
		String Smac = WDPBOCUtil.triple_des_mac(macKey, data, Padding.NoPadding, "0000000000000000");
		int lc = Host.length() + Smac.length();
		String lcStr = Integer.toHexString(lc / 2);
		resp = apduChannel.send("8482" + secureity_level + "00" + lcStr + Host + Smac);
		if (resp.equalsIgnoreCase("9000")) {
			setSecureityLevel(secureity_level);
			setSmac(Smac);
		}
	}

	/**
	 * send apdu command
	 * 
	 * @param apdu
	 * @return
	 */
	public String send(String apdu) {
		String encIv;
		if (getSecureityLevel().equalsIgnoreCase("00") || apdu.substring(0, 4).equalsIgnoreCase("00A4")) {
			return apduChannel.send(apdu);
		} else if (getSecureityLevel().equalsIgnoreCase("01")) {
			int lc = Integer.parseInt(apdu.substring(8, 10), 16);
			lc += 8;

			encIv = WDPBOCUtil.single_des_mac(getMacKey().substring(0, 16), getSmac(), Padding.NoPadding, "0000000000000000");

			String macData = apdu.substring(0, 8) + WDStringUtil.paddingHeadZero(Integer.toHexString(lc), 2) + apdu.substring(10);
			macData = macData + "80";
			while (macData.length() % 16 != 0) {
				macData = macData + "00";
			}
			String cMac = WDPBOCUtil.triple_des_mac(getMacKey(), macData.toUpperCase(), Padding.NoPadding, encIv);

			setSmac(cMac);

			apdu = apdu.substring(0, 8) + WDStringUtil.paddingHeadZero(Integer.toHexString(lc), 2) + apdu.substring(10) + cMac;

			return apduChannel.send(apdu.toUpperCase());
		} else if (getSecureityLevel().equalsIgnoreCase("03")) {

		}
		return apduChannel.send(apdu);
	}

	public void close() {
		apduChannel.close();
	}

	public static void main(String[] args) {
		CommonAPDU commonAPDU = new CommonAPDU();
		commonAPDU.reset("WatchData System CRW-VIuo 0");
		commonAPDU.select("A000000003000000");
		commonAPDU.externalAuthenticate("01", "00", "00", "404142434445464748494A4B4C4D4E4F", "404142434445464748494A4B4C4D4E4F", "404142434445464748494A4B4C4D4E4F");
		commonAPDU.send("84F28000024F00");
		commonAPDU.send("84F24000024F00");
		commonAPDU.send("84F22000024F00");
		commonAPDU.send("84F21000024F00");
	}
}
