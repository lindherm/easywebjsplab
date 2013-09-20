package com.gp.gpscript.engine;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

public class GPScriptEngine {
	public static void main(String[] args) throws Exception {
		FileInputStream fis=new FileInputStream(new File(System.getProperty("user.dir")+"/hbyh.xml"));
		int len=fis.available();
		byte[] fileByte=new byte[len];
		
		fis.read(fileByte);
		fis.close();
		HashMap mapBean=new HashMap<String, HashMap<String, String>>();
		HashMap<String,String> mapValues=new HashMap<String, String>();
		mapValues.put("dgi0101",  "706B571862A4C0FB7C8D3E1C86B4E73D02636A73BEC3026872A306679F1F4E253939363233303736303032373030303030303031385E20202020202020202020202020202020202020202020202020205E3232313132323030303030303030303030303030303030303030303F");
		mapValues.put("dgi0102",  "70055F20022020");
		mapValues.put("dgi0201",  "70465F24032211285A0A6230760027000000018F5F3401009F0702FF008E0C000000000000000002031F009F0D05D86004A8009F0E0500109800009F0F05D86804F8005F28020156");
		/*mapValues.put("dgi0202",  "");
		mapValues.put("dgi0203",  "");
		mapValues.put("dgi0204",  "");
		mapValues.put("dgi0205",  "");
		mapValues.put("dgi0206",  "");
		mapValues.put("dgi0301",  "");
		mapValues.put("dgi0302",  "");
		mapValues.put("dgi0401",  "");
		mapValues.put("dgi0501",  "");*/
		mapValues.put("pan",  "6230760027000000018F");
		mapBean.put(""+0, mapValues);
		ScriptEngine se = new ScriptEngine("VSDC Data Preparation", new String(fileByte), System.getProperty("user.dir")+"/profiles/GPCardProfile.xml", mapBean, 0, 1);
		
		String[] a=se.execEngine();
		System.out.println(a[0]);
	}
}
