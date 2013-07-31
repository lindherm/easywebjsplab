package com.watchdata.cardcheck.configdao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.watchdata.cardcheck.utils.Config;

public class CAInfo {
	private String Rid;
	public String getRid() {
		return Rid;
	}
	public void setRid(String rid) {
		Rid = rid;
	}
	public String getCaType() {
		return CaType;
	}
	public void setCaType(String caType) {
		CaType = caType;
	}
	private String CaType;
	
	public List<CAInfo> getCaInfos(String aidSection) {
		List<CAInfo> result=new ArrayList<CAInfo>();
		Collection<String> rids = Config.getItems(aidSection);

		for (String rid : rids) {
			String caType = Config.getValue(aidSection, rid);
			CAInfo caInfo=new CAInfo();
			caInfo.setRid(rid);
			caInfo.setCaType(caType);
			result.add(caInfo);
		}
		return result;
	}
	public List<AIDInfo> getPublicKey(String aidSection) {
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
}
