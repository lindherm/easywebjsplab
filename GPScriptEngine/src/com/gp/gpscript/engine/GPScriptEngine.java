package com.gp.gpscript.engine;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.ScriptRuntime;

import com.gp.gpscript.device.PcscCardReader;
import com.gp.gpscript.script.NativeApplication;
import com.gp.gpscript.script.NativeByteString;
import com.gp.gpscript.script.NativeGPApplication;
import com.gp.gpscript.script.NativeGPSecurityDomain;
import com.watchdata.kms.kmsi.IKmsException;

public class GPScriptEngine extends ScriptEngine{
	public GPScriptEngine(String selectedFragment,String secript,String cardProfilePath) throws Exception {
		super(selectedFragment, secript, cardProfilePath);
		apduChannel=new PcscCardReader("WatchData CRW-V Plus PC/SC Reader 0");
	}
	
	/**
	 * 脚本执行
	 * 
	 * @param selectedFragment
	 * @param appfile
	 * @param cardfile
	 * @throws IKmsException
	 * @throws Exception
	 */
	public String[] execEngineDP() throws Exception {

		String[] allPersonDatas = new String[count];
		String personData = "";
		try {
			prepareScriptContext(selectedFragment, secript, cardProfilePath);
		} catch (Exception e) {
			return new String[] { "编译脚本出错" + e };
		}

		try {
			for (int i = 0; i < count; i++) {
				personData = getIcInfo(i, varHashMap);
				allPersonDatas[i] = personData;
			}

			return allPersonDatas;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}
	}
	
	public boolean execEngineIssue() throws Exception {
		try {
			prepareScriptContext(selectedFragment, secript, cardProfilePath);
		} catch (Exception e) {
			return false;
		}
		//数据映射到脚本变量
				try {
					dataMapping(0);
				} catch (Exception e) {
					throw e;
				}
				
				/*NativeArray dataArray;
				if (gpApp instanceof NativeApplication)
					dataArray = ((NativeApplication) gpApp).data;
				else if (gpApp instanceof NativeGPApplication)
					dataArray = ((NativeGPApplication) gpApp).data;
				else if (gpApp instanceof NativeGPSecurityDomain)
					dataArray = ((NativeGPSecurityDomain) gpApp).data;
				else
					dataArray = null;
				NativeByteString snew = (NativeByteString) ScriptRuntime.getObjectElem(dataArray, "CPS_Output", cx);
				if (!succflag)
					throw new Exception("evaluateScript error.");

				HashMap<String,String> tagRecordMap = (HashMap<String,String>) varHashMap.get("" + i);
				String pan =tagRecordMap.get("pan");

				//clear back
				//varHashMap.put("" + i, null);
				//varHashMap.remove("" + i);
				//return (pan + "|" + snew.toString());
*/				//执行脚本
				boolean succflag = false;
				try {
					succflag = evaluateScript();
				} catch (Exception e) {
					throw new Exception("脚本引擎出错,请检查!");
				}
				
				return succflag;
	}
	
	

	/**
	 * 组织数据
	 * 
	 * @param i
	 * @throws Exception
	 */
	private String getIcInfo(int i, HashMap<String,HashMap<String, String>> varHashMap) throws Exception {
		//数据映射到脚本变量
		try {
			dataMapping(i);
		} catch (Exception e) {
			throw e;
		}
		//执行脚本
		boolean succflag = false;
		try {
			succflag = evaluateScript();
		} catch (Exception e) {
			throw new Exception("脚本引擎出错,请检查!");
		}

		NativeArray dataArray;
		if (gpApp instanceof NativeApplication)
			dataArray = ((NativeApplication) gpApp).data;
		else if (gpApp instanceof NativeGPApplication)
			dataArray = ((NativeGPApplication) gpApp).data;
		else if (gpApp instanceof NativeGPSecurityDomain)
			dataArray = ((NativeGPSecurityDomain) gpApp).data;
		else
			dataArray = null;
		NativeByteString snew = (NativeByteString) ScriptRuntime.getObjectElem(dataArray, "CPS_Output", cx);
		if (!succflag)
			throw new Exception("evaluateScript error.");

		HashMap<String,String> tagRecordMap = (HashMap<String,String>) varHashMap.get("" + i);
		String pan =tagRecordMap.get("pan");

		//clear back
		varHashMap.put("" + i, null);
		varHashMap.remove("" + i);
		return (pan + "|" + snew.toString());

	}
/*	
	public GPScriptEngine(String selectedFragment,String secript,String cardProfilePath,HashMap<String, HashMap<String, String>> dealdata,int dataCount) throws Exception{
		scriptEngine = new ScriptEngine(selectedFragment,secript,cardProfilePath,dealdata,dataCount);
	}*/
	
	/*public String[] execEngine() throws Exception{
		return scriptEngine.execEngine();
	}*/
	public static void main(String[] args) throws Exception {
		FileInputStream fis = new FileInputStream(new File(System.getProperty("user.dir") + "/hbyh.xml"));
		int len = fis.available();
		byte[] fileByte = new byte[len];

		fis.read(fileByte);
		fis.close();
		HashMap mapBean = new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> mapValues = new HashMap<String, String>();
		//mapValues.put("dgi0101", "706B571862A4C0FB7C8D3E1C86B4E73D02636A73BEC3026872A306679F1F4E253939363233303736303032373030303030303031385E20202020202020202020202020202020202020202020202020205E3232313132323030303030303030303030303030303030303030303F");
		//mapValues.put("dgi0102", "70055F20022020");
		//mapValues.put("dgi0201", "70465F24032211285A0A6230760027000000018F5F3401009F0702FF008E0C000000000000000002031F009F0D05D86004A8009F0E0500109800009F0F05D86804F8005F28020156");
		mapValues.put("CPS_Output", "01010670055F2002202002014770465F24032211285A0A6230760027000000018F5F3401009F0702FF008E0C000000000000000002031F009F0D05D86004A8009F0E0500109800009F0F05D86804F8005F28020156");
		/*
		 * mapValues.put("dgi0202", ""); mapValues.put("dgi0203", ""); mapValues.put("dgi0204", ""); mapValues.put("dgi0205", ""); mapValues.put("dgi0206", ""); mapValues.put("dgi0301", ""); mapValues.put("dgi0302", ""); mapValues.put("dgi0401", ""); mapValues.put("dgi0501", "");
		 */
		//mapValues.put("pan", "6230760027000000018F");
		mapBean.put("0", mapValues);
		//GPScriptEngine gpScriptEngine=new GPScriptEngine("VSDC Data Preparation", new String(fileByte), System.getProperty("user.dir") + "/profiles/GPCardProfile.xml");
		GPScriptEngine gpScriptEngine=new GPScriptEngine("PERSONALIZE", new String(fileByte), System.getProperty("user.dir") + "/profiles/GPCardProfile.xml");
		gpScriptEngine.setVarHashMap(mapBean);
		//gpScriptEngine.setCount(1);
		//String[] a = gpScriptEngine.execEngineDP();
		gpScriptEngine.execEngineIssue();
		//System.out.println(a[0]);
	}
}
