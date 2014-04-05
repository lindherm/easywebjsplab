package com.gerenhua.tool.configdao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.gerenhua.tool.utils.Config;

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
}
