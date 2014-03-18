package com.watchdata.cardcheck.configdao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.dtools.ini.IniSection;

import com.watchdata.cardcheck.utils.Config;

public class PublicKeyInfo {
	private String rid;
	private String Index;
	private String Exp;
	private String Arith;
	private String HashArith;
	private String Module;
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getIndex() {
		return Index;
	}
	public void setIndex(String index) {
		Index = index;
	}
	public String getExp() {
		return Exp;
	}
	public void setExp(String exp) {
		Exp = exp;
	}
	public String getArith() {
		return Arith;
	}
	public void setArith(String arith) {
		Arith = arith;
	}
	public String getHashArith() {
		return HashArith;
	}
	public void setHashArith(String hashArith) {
		HashArith = hashArith;
	}
	public String getModule() {
		return Module;
	}
	public void setModule(String module) {
		Module = module;
	}
	
	
	public List<PublicKeyInfo> getPKInfos(String aidSection) {
		List<PublicKeyInfo> result = new ArrayList<PublicKeyInfo>();
		
		List<CAInfo> caInfos=new CAInfo().getCaInfos(aidSection);
		Collection<IniSection> publicKeyInfos=Config.getSections();
		
		for (IniSection iniSection : publicKeyInfos) {
			for (CAInfo caInfo : caInfos) {
				String sectionName=iniSection.getName();
				if (sectionName.startsWith(caInfo.getRid())) {
					PublicKeyInfo publicKeyInfo=new PublicKeyInfo();
					publicKeyInfo.setRid(caInfo.getRid());
					publicKeyInfo.setIndex(sectionName.substring(sectionName.length()-2, sectionName.length()));
					publicKeyInfo.setArith(Config.getValue(sectionName, "Algorithm"));
					publicKeyInfo.setExp(Config.getValue(sectionName, "CA_PK_Exponent"));
					publicKeyInfo.setHashArith(Config.getValue(sectionName, "Hash_Algorithm"));
					publicKeyInfo.setModule(Config.getValue(sectionName, "CA_PK_Modulus"));
					result.add(publicKeyInfo);
				}
			}
		}
		return result;
	}
	
	public PublicKeyInfo getPK(String sectionName,String rid,String index) {
		PublicKeyInfo publicKeyInfo=new PublicKeyInfo();
		publicKeyInfo.setRid(rid);
		publicKeyInfo.setIndex(index);
		publicKeyInfo.setArith(Config.getValue(sectionName, "Algorithm"));
		publicKeyInfo.setExp(Config.getValue(sectionName, "CA_PK_Exponent"));
		publicKeyInfo.setHashArith(Config.getValue(sectionName, "Hash_Algorithm"));
		publicKeyInfo.setModule(Config.getValue(sectionName, "CA_PK_Modulus"));
		return publicKeyInfo;
	}
	public boolean add(String sectionName,PublicKeyInfo publicKeyInfo) {
		Config.addSection(sectionName);
		Config.addItem(sectionName, "Algorithm");
		Config.addItem(sectionName, "CA_PK_Exponent");
		Config.addItem(sectionName, "Hash_Algorithm");
		Config.addItem(sectionName, "CA_PK_Modulus");
		
		Config.setValue(sectionName,"Algorithm", publicKeyInfo.getArith());
		Config.setValue(sectionName,"CA_PK_Exponent", publicKeyInfo.getArith());
		Config.setValue(sectionName,"Hash_Algorithm", publicKeyInfo.getArith());
		Config.setValue(sectionName,"CA_PK_Modulus", publicKeyInfo.getArith());
		
		return true;
	}
	
	public boolean del(List<String> publicKeyInfos) {
		for (String sectionName : publicKeyInfos) {
			Config.ini.removeSection(sectionName);
			Config.write();
		}
		return true;
	}
	@Override
	public String toString() {
		return "RID=" + rid + "\nCA_PK_Exponent=" + Exp + "\nCA_PK_Arith=" + Arith + "\nCA_PK_HashArith=" + HashArith + "\nCA_PK_Modulus=" + Module + "\n";
	}
	
}
