package com.watchdata.cardcheck.logic.impl;

import java.util.Map;

public class AppData {


	private String appType;
	private Map<String, Map<String, String>> readDataMap;
	private Map<String, String> getdataMap;

	
	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public Map<String, Map<String, String>> getReadDataMap() {
		return readDataMap;
	}

	public void setReadDataMap(Map<String, Map<String, String>> readDataMap) {
		this.readDataMap = readDataMap;
	}

	public Map<String, String> getGetdataMap() {
		return getdataMap;
	}

	public void setGetdataMap(Map<String, String> getdataMap) {
		this.getdataMap = getdataMap;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
