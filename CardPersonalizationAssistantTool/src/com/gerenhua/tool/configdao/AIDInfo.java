package com.gerenhua.tool.configdao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.gerenhua.tool.utils.Config;

public class AIDInfo {
	private String aid;
	private String dscrpt;
	private String version;

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getDscrpt() {
		return dscrpt;
	}

	public void setDscrpt(String dscrpt) {
		this.dscrpt = dscrpt;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<AIDInfo> getAidInfos(String aidSection) {
		List<AIDInfo> result = new ArrayList<AIDInfo>();
		Collection<String> aids = Config.getItems(aidSection);

		for (String aid : aids) {
			String itemV = Config.getValue(aidSection, aid);
			String res[] = itemV.split("\\|");
			AIDInfo aidInfo=new AIDInfo();
			aidInfo.setAid(aid);
			aidInfo.setDscrpt(res[0]);
			aidInfo.setVersion(res[1]);
			result.add(aidInfo);
		}
		return result;
	}
	
	public boolean add(String aidSection,AIDInfo aidInfo) {
		Config.addItem(aidSection, aidInfo.getAid());
		Config.setValue(aidSection, aidInfo.getAid(), aidInfo.getDscrpt()+"|"+aidInfo.getVersion());
		return true;
	}
	
	public boolean update(String aidSection,List<AIDInfo> aids) {
		for (AIDInfo aidInfo : aids) {
			Config.setValue(aidSection, aidInfo.getAid(), aidInfo.getDscrpt()+"|"+aidInfo.getVersion());
		}
		return true;
	}
	
	public boolean del(String aidSection,List<String> aids) {
		for (String aid : aids) {
			Config.delItem(aidSection, aid);
		}
		return true;
	}
}
