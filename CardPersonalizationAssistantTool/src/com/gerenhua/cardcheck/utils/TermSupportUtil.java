package com.gerenhua.cardcheck.utils;

import java.util.HashSet;
import java.util.List;

import com.gerenhua.cardcheck.configdao.AIDInfo;
import com.gerenhua.cardcheck.log.Log;
import com.gerenhua.cardcheck.panel.AtmPanel.TerminalSupportType;
import com.watchdata.commons.lang.WDStringUtil;

/**
 * 
 * @author peng.wang
 * 
 *         该类提供判断终端是否支持某一项终端性能
 * 
 */
public class TermSupportUtil {

	private String supportData;
	private HashSet<String> aidSet = new HashSet<String>(20);
	public static Log log = new Log();

	/**
	 * 
	 * @param supportData
	 *            终端性能数据，为二进制串
	 */
	public TermSupportUtil(String supportData, List<AIDInfo> aidList) {
		this.supportData = supportData;
		// 循环aid列表存进hashset
		for (AIDInfo aidInfo : aidList) {
			aidSet.add(aidInfo.getAid());
		}
	}

	/**
	 * 根据传入的参数，判断终端是否支持相应的终端性能
	 * 
	 * @param supportType
	 *            需要判断的功能
	 * @return
	 */
	public boolean isSupportTheFunction(TerminalSupportType supportType) {
		switch (supportType) {
		case TOUCHIC:
			return isSupport(supportData.substring(5, 6));
		case TRACK:
			return isSupport(supportData.substring(6, 7));
		case KEYBOARD:
			return isSupport(supportData.substring(7, 8));
		case CERTIFICATECHECK:
			return isSupport(supportData.substring(8, 9));
		case NOCVM:
			return isSupport(supportData.substring(11, 12));
		case SIGN:
			return isSupport(supportData.substring(13, 14));
		case LINKPIN:
			return isSupport(supportData.substring(14, 15));
		case ICPINCHECK:
			return isSupport(supportData.substring(15, 16));
		case SUPPORTCDA:
			return isSupport(supportData.substring(19, 20));
		case EATCARD:
			return isSupport(supportData.substring(21, 22));
		case SUPPORTDDA:
			return isSupport(supportData.substring(22, 23));
		case SUPPORTSDA:
			return isSupport(supportData.substring(23, 24));
		default:
			return false;
		}
	}

	public static void parse8E(String str8E) {
		String x = str8E.substring(0, 8);
		String y = str8E.substring(8, 16);
		String cvmCode = "";
		String cvmCondtionCode = "";

		x = x + "------金额X（二进制）";
		y = y + "------金额Y（二进制）";
		log.warn(x);
		log.warn(y);
		log.warn("---------------------------------------\n");
		int i = 16;
		while (i < str8E.length()) {
			cvmCode = str8E.substring(i, i + 2);
			cvmCondtionCode = str8E.substring(i + 2, i + 4);

			String binary = Integer.toBinaryString(Integer.parseInt(cvmCode, 16));
			binary = WDStringUtil.paddingHeadZero(binary, 8);

			cvmCode = cvmCode + "------" + Config.getValue("CVM_CODE", binary.substring(0, 2)) + ";" + Config.getValue("CVM_TYPE", binary.substring(2, 8));
			cvmCondtionCode = cvmCondtionCode + "------" + Config.getValue("CVM_Condition_Code", cvmCondtionCode);
			i += 4;
			log.warn(cvmCode);
			log.warn(cvmCondtionCode);
			log.warn("---------------------------------------");
		}
	}

	/**
	 * 判断具体某一项终端性能是否支持
	 * 
	 * @param supportData
	 *            终端性能的支持类别，为0或1
	 * @return
	 */
	private static boolean isSupport(String supportData) {
		if ("1".equals(supportData)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判读终端是否支持某一个AID
	 * 
	 * @param aid
	 * @return
	 */
	public boolean isSupportAID(String aid) {
		if (aidSet.contains(aid)) {
			return true;
		} else {
			return false;
		}
	}
}
