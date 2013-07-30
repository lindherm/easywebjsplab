package com.watchdata.cardcheck.utils.wordutil;

import java.util.HashMap;
import java.util.Map;

public class APDUSendANDRes {
	private String sendAPDU;// 发送apdu
	private String des;// 发送apdu操作描述
	private String responseAPDU;// 收到的数据
	private Map<String, String> tagDes = new HashMap<String, String>();// 收到数据解析以及描述
	public String getSendAPDU() {
		return sendAPDU;
	}
	public void setSendAPDU(String sendAPDU) {
		this.sendAPDU = sendAPDU;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getResponseAPDU() {
		return responseAPDU;
	}
	public void setResponseAPDU(String responseAPDU) {
		this.responseAPDU = responseAPDU;
	}
	public Map<String, String> getTagDes() {
		return tagDes;
	}
	public void setTagDes(Map<String, String> tagDes) {
		this.tagDes = tagDes;
	}

}
