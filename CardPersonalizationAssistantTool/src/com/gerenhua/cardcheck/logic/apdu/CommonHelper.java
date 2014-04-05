package com.gerenhua.cardcheck.logic.apdu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.watchdata.commons.crypto.WD3DesCryptoUtil;
import com.watchdata.commons.jce.JceBase.Padding;
import com.watchdata.commons.lang.WDAssert;
import com.watchdata.commons.lang.WDByteUtil;
import com.watchdata.commons.lang.WDStringUtil;

/**
 * 
 * @description: 通用辅助类
 * @author: juan.jiang Apr 10, 2012
 * @version: 1.0.0
 * @modify:
 * @Copyright: watchdata
 */
public class CommonHelper {
	/**
	 * 拷贝map数据
	 * 
	 * @param deptData
	 *            目的数据
	 * @param sourceData
	 *            源数据
	 * @throws Exception
	 */
	public static void copyMapData(HashMap<String, String> deptData, HashMap<String, String> sourceData) throws Exception {
		Iterator<String> it = sourceData.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			if (WDAssert.isEmpty(deptData.get(key))) {
				deptData.put(key, sourceData.get(key));
			} else {
				// throw new Exception("map data is exist of key[" + key +"]");
			}

		}
	}

	/**
	 * 将data填充成LV类型，l为L的长度字节
	 * 
	 * @param data
	 * @param l字节的L
	 * @return
	 */
	public static String getLVData(String data, int l) {
		if (data == null || data.length() % 2 != 0)
			throw new IllegalArgumentException("command data is null or length error");
		String dataLen = Integer.toHexString(data.length() / 2);// Integer.parseInt(String.valueOf(data.length()/2), 16);
		return WDStringUtil.paddingHeadZero(dataLen, l * 2) + data;

	}

	/**
	 * 解析TL格式的数据，如PDOL/DDOL/CDOL等
	 * 
	 * @param data
	 * @return
	 */
	public static ArrayList<String> parseTLData(String data) {
		ArrayList<String> tagList = new ArrayList<String>();
		StringBuffer apdu = new StringBuffer(data);
		while (true) {
			String tag = apdu.substring(0, 2);
			apdu.delete(0, 2);
			if ((Integer.parseInt(tag, 16) & 0x1F) == 0x1F) {
				tag += apdu.substring(0, 2);
				apdu.delete(0, 2);
			}
			tagList.add(tag);
			// length
			apdu.delete(0, 2);
			if (apdu.length() <= 0)
				break;
		}

		return tagList;
	}
	
	/**
	 * 解析TL格式的数据，如PDOL/DDOL/CDOL等
	 * 
	 * @param data
	 * @return
	 */
	public static ArrayList<String> parseTLDataCommon(String data) {
		ArrayList<String> tagList = new ArrayList<String>();
		StringBuffer apdu = new StringBuffer(data);
		while (true) {
			String tag = apdu.substring(0, 2);
			apdu.delete(0, 2);
			if ((Integer.parseInt(tag, 16) & 0x1F) == 0x1F) {
				tag += apdu.substring(0, 2);
				apdu.delete(0, 2);
			}
			// length
			tag += apdu.substring(0, 2);
			apdu.delete(0, 2);
			
			tagList.add(tag);
			if (apdu.length() <= 0)
				break;
		}

		return tagList;
	}

	/**
	 * 解析AFL
	 * 
	 * @param afl
	 * @return
	 */
	public static ArrayList<String> parseAppFileLocation(String afl) {
		ArrayList<String> fileList = new ArrayList<String>();
		int index = 0;
		while (true) {
			int sfi = Integer.parseInt(afl.substring(index, index + 2), 16);
			int begin = Integer.parseInt(afl.substring(index + 2, index + 4), 16);
			int end = Integer.parseInt(afl.substring(index + 4, index + 6), 16);
			int staticDataIndex = Integer.parseInt(afl.substring(index + 6, index + 8), 16);

			String sfiStr = WDByteUtil.byte2HEX((byte) (sfi + 4));
			for (int i = begin; i <= end; i++) {
				if (staticDataIndex == 1 && i == begin) {
					fileList.add(sfiStr + WDStringUtil.paddingHeadZero(String.valueOf(i), 2) + "T");
				} else {
					fileList.add(sfiStr + WDStringUtil.paddingHeadZero(String.valueOf(i), 2));
				}

			}
			index += 8;
			if (index >= afl.length())
				break;

		}

		return fileList;
	}

	/**
	 * AFL文件个数
	 * 
	 * @param afl
	 * @return
	 */
	public static int getFileCount(String afl) {
		int count = 0;
		int index = 0;
		while (true) {
			int begin = Integer.parseInt(afl.substring(index + 2, index + 4), 16);
			int end = Integer.parseInt(afl.substring(index + 4, index + 6), 16);

			count += end - begin + 1;
			index += 8;
			if (index >= afl.length())
				break;
		}
		return count;
	}

	/**
	 * 转换afl+4成DGI头
	 * 
	 * @param hex
	 * @return
	 */
	public static String getDgiHead(String hex) {
		// 16进制转10进制数字
		int t = Integer.parseInt(hex, 16);
		// -4
		t -= 4;
		// 十进制转二进制01码
		String bInt = Integer.toBinaryString(t);
		// 补齐到8位
		while (bInt.length() < 8) {
			bInt = "0" + bInt;
		}
		// 取前5位
		bInt = bInt.substring(0, 5);
		// 二进制转10进制
		int i = Integer.parseInt(bInt, 2);
		// 10进制转
		String res = Integer.toHexString(i);
		if (res.length() < 2) {
			res = "0" + res;
		}
		return res;
	}

	public static boolean parse8E(String str8E) {
		int i = 16;
		while (i < str8E.length()) {
			String cvm = str8E.substring(i, i + 4);
			if (cvm.endsWith("103")) {
				return true;
			}
			i += 4;
		}
		return false;
	}
	/**
	 * calculate checkvalue of the KeyComponent specified
	 * 
	 * @param KeyComponent
	 * @return
	 * @throws Exception
	 */
	public static String getCheckValue(String keyComponent) throws Exception {
		String res = "";
		if (WDAssert.isEmpty(keyComponent)) {
			throw new Exception("key is null.");
		}
		String dataInhex = "0000000000000000";
		res = WD3DesCryptoUtil.ecb_encrypt(keyComponent, dataInhex, Padding.NoPadding);
		System.out.println("encrypt:" + res);

		res = res.substring(0, 8);
		System.out.println("checkvalue:" + res);
		return res;
	}

	/**
	 * updateUI
	 * 
	 * @param com
	 * @param target
	 */
	public static void updateUI(final JTextField com, final String target) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				com.setText(target);
			}
		});
	}
	public static void main(String[] args) {
		int b = Integer.parseInt("b",16);
		b = (b << 3) + 4;
		System.out.println(Integer.toHexString(b));
	}
}
