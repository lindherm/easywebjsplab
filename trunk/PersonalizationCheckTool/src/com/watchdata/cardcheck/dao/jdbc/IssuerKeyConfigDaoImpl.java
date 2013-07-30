package com.watchdata.cardcheck.dao.jdbc;

import com.watchdata.cardcheck.dao.AbstractBaseDao;
import com.watchdata.cardcheck.dao.IIssuerKeyConfigDao;
import com.watchdata.cardcheck.dao.pojo.IssuerKeyInfo;

public class IssuerKeyConfigDaoImpl extends AbstractBaseDao implements IIssuerKeyConfigDao {
	
	private static final String ISSUERKEYCONFIG_INSERT_SQL = "INSERT INTO ISSUER_INFO( MAC_KEY, ENC_KEY, AC_KEY, ISSUER_TYPE)VALUES (?,?,?,?)";
	private static final String ISSUERKEYCONFIG_SEARCH_SQL = "select * from ISSUER_INFO where ISSUER_TYPE=?";
	private static final String ISSUERKEYCONFIG_DELETE_SQL = "delete from ISSUER_INFO ";
	private static final String ISSUERKEYCONFIG_UPDATE_SQL = "UPDATE ISSUER_INFO SET MAC_KEY=?,ENC_KEY=? ,AC_KEY=? where ISSUER_TYPE=?";

	@Override
	public boolean insertIssuerkey(IssuerKeyInfo issuerinfo) {

		final Object[] params = new Object[4];
		params[0] = issuerinfo.getMacKey();
		params[1] = issuerinfo.getEncKey();
		params[2] = issuerinfo.getAcKey();
		params[3] = issuerinfo.getIssuerType();
		try {
			this.getJdbcTemplate().update(ISSUERKEYCONFIG_INSERT_SQL, params);
			return true;
		} catch (Exception e) {
			return false;
		}

	}	
	
	@Override
	public boolean deleteIssuerKey() {
		try {
			this.getJdbcTemplate().update(ISSUERKEYCONFIG_DELETE_SQL);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	@Override
	public IssuerKeyInfo findIssuerKey(String issuerType) {
		final Object[] params =  {issuerType};
		IssuerKeyInfo tl = null;
		try {
			tl = (IssuerKeyInfo) this.getJdbcTemplate().queryForObject(ISSUERKEYCONFIG_SEARCH_SQL, params, new IssuerKeyInfo());
			return tl;
		} catch (Exception e) {
			return null;
		}
	}
	@Override
	public boolean updateIssuerKey(IssuerKeyInfo issuerinfo){

		final Object[] params = new Object[4];
		params[0] = issuerinfo.getMacKey();
		params[1] = issuerinfo.getEncKey();
		params[2] = issuerinfo.getAcKey();
		params[3] = issuerinfo.getIssuerType();
		try {
			this.getJdbcTemplate().update(ISSUERKEYCONFIG_UPDATE_SQL, params);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
