package com.watchdata.cardcheck.configdao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.watchdata.cardcheck.utils.Config;

public class StaticDataInfo{

	private String dgi;
	private String tag;
	private String value;
	private int result;
	public String getDgi() {
		return dgi;
	}
	public void setDgi(String dgi) {
		this.dgi = dgi;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	
	public List<StaticDataInfo> getStaticDataInfos(String StaticDataSection) {
		List<StaticDataInfo> result = new ArrayList<StaticDataInfo>();
		Collection<String> sdatas = Config.getItems(StaticDataSection);

		for (String stdata : sdatas) {
			String itemV = Config.getValue(StaticDataSection, stdata);
			StaticDataInfo staticDataInfo=new StaticDataInfo();
			staticDataInfo.setDgi(itemV);
			staticDataInfo.setTag(stdata);
			
			result.add(staticDataInfo);
		}
		return result;
	}
	
	public boolean add(String staticDataSection,StaticDataInfo staticDataInfo) {
		Config.addItem(staticDataSection, staticDataInfo.getTag());
		Config.setValue(staticDataSection, staticDataInfo.getTag(), staticDataInfo.getDgi());
		return true;
	}
	
	public boolean update(String aidSection,List<AIDInfo> aids) {
		for (AIDInfo aidInfo : aids) {
			Config.setValue(aidSection, aidInfo.getAid(), aidInfo.getDscrpt()+"|"+aidInfo.getVersion());
		}
		return true;
	}
	
	public boolean del(String staticDataSection,List<String> tags) {
		for (String tag : tags) {
			Config.delItem(staticDataSection, tag);
		}
		return true;
	}
}
