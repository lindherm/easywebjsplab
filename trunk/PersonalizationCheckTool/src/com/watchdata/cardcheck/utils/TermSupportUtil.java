package com.watchdata.cardcheck.utils;

import java.util.HashSet;
import java.util.List;

import com.watchdata.cardcheck.dao.pojo.AppInfo;
import com.watchdata.cardcheck.panel.TradePanel.TerminalSupportType;

/**
 * 
 * @author peng.wang
 * 
 * 该类提供判断终端是否支持某一项终端性能
 *
 */
public class TermSupportUtil {
	
	private String supportData;
	private HashSet<String> aidSet=new HashSet<String>(20);
	/**
	 * 
	 * @param supportData 终端性能数据，为二进制串
	 */
	public TermSupportUtil(String supportData,List<AppInfo> aidList){
		this.supportData = supportData;
		//循环aid列表存进hashset
		for (AppInfo appInfo : aidList) {
			aidSet.add(appInfo.getAid());
		}
	}

	/**
	 * 根据传入的参数，判断终端是否支持相应的终端性能
	 * @param supportType 需要判断的功能
	 * @return
	 */
	public boolean isSupportTheFunction(TerminalSupportType supportType) {
		switch (supportType) {
		case TOUCHIC:
			return isSupport(supportData.substring(5,6));
		case TRACK:
			return isSupport(supportData.substring(6,7));
		case KEYBOARD:
			return isSupport(supportData.substring(7,8));
		case CERTIFICATECHECK:
			return isSupport(supportData.substring(8,9));
		case NOCVM:
			return isSupport(supportData.substring(11,12));
		case SIGN:
			return isSupport(supportData.substring(13,14));
		case LINKPIN:
			return isSupport(supportData.substring(14,15));
		case ICPINCHECK:
			return isSupport(supportData.substring(15,16));
		case SUPPORTCDA:
			return isSupport(supportData.substring(19,20));
		case EATCARD:
			return isSupport(supportData.substring(21,22));
		case SUPPORTDDA:
			return isSupport(supportData.substring(22,23));
		case SUPPORTSDA:
			return isSupport(supportData.substring(23,24));
		default:
			return false;
		}
	}

	/**
	 * 判断具体某一项终端性能是否支持
	 * @param supportData 终端性能的支持类别，为0或1
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
	 * @param aid
	 * @return
	 */
	public boolean isSupportAID(String aid){
		if (aidSet.contains(aid)) {
			return true;
		}else {
			return false;
		}
	}
}
