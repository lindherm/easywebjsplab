package com.watchdata.cardcheck.dao;

import com.watchdata.cardcheck.dao.pojo.IssuerKeyInfo;

public interface IIssuerKeyConfigDao {
	
	public boolean insertIssuerkey(IssuerKeyInfo issuerinfo);
	public IssuerKeyInfo findIssuerKey(String issuerType);
	public boolean deleteIssuerKey();
	public boolean updateIssuerKey(IssuerKeyInfo issuerinfo);

}
