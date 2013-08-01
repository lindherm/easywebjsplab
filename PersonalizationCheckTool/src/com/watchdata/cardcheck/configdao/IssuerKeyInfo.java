package com.watchdata.cardcheck.configdao;

import com.watchdata.cardcheck.utils.Config;

public class IssuerKeyInfo {
	private String acKey;
	private String macKey;
	private String encKey;
	private int derive;

	public String getAcKey() {
		return acKey;
	}

	public void setAcKey(String acKey) {
		this.acKey = acKey;
	}

	public String getMacKey() {
		return macKey;
	}

	public void setMacKey(String macKey) {
		this.macKey = macKey;
	}

	public String getEncKey() {
		return encKey;
	}

	public void setEncKey(String encKey) {
		this.encKey = encKey;
	}

	public int getDerive() {
		return derive;
	}

	public void setDerive(int derive) {
		this.derive = derive;
	}
	
	public IssuerKeyInfo getIssuerKeyInfo(String sectionName){
		
		IssuerKeyInfo issuerKeyInfo=new IssuerKeyInfo();
		issuerKeyInfo.setDerive(Integer.parseInt(Config.getValue(sectionName, "Derive")));
		issuerKeyInfo.setAcKey(Config.getValue(sectionName, "UDKac"));
		issuerKeyInfo.setMacKey(Config.getValue(sectionName, "UDKmac"));
		issuerKeyInfo.setEncKey(Config.getValue(sectionName, "UDKenc"));
		
		return issuerKeyInfo;
	}
	
}
