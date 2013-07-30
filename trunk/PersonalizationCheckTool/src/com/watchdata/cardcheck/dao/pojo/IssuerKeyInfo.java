/**
 * 
 */
package com.watchdata.cardcheck.dao.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author jia.hu
 *
 */
public class IssuerKeyInfo implements RowMapper{
    private int issuerId;
    private String issuerType;
    private String macKey;
    private String encKey;
    private String acKey;
    private int derive;
	public void setIssuerId(int issuerId) {
		this.issuerId = issuerId;
	}
	public int getIssuerId() {
		return issuerId;
	}
	public void setIssuerType(String issuerType) {
		this.issuerType = issuerType;
	}
	public String getIssuerType() {
		return issuerType;
	}
	public void setMacKey(String macKey) {
		this.macKey = macKey;
	}
	public String getMacKey() {
		return macKey;
	}
	public void setEncKey(String encKey) {
		this.encKey = encKey;
	}
	public String getEncKey() {
		return encKey;
	}
	public void setAcKey(String acKey) {
		this.acKey = acKey;
	}
	public String getAcKey() {
		return acKey;
	}
	public void setDerive(int derive) {
		this.derive = derive;
	}
	public int getDerive() {
		return derive;
	}
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		IssuerKeyInfo issuerkeyinfo = new IssuerKeyInfo();
		issuerkeyinfo.setIssuerId(rs.getInt("ISSUER_ID"));
		issuerkeyinfo.setIssuerType(rs.getString("ISSUER_TYPE"));
		issuerkeyinfo.setMacKey(rs.getString("MAC_KEY"));
		issuerkeyinfo.setEncKey(rs.getString("ENC_KEY"));
		issuerkeyinfo.setAcKey(rs.getString("AC_KEY"));

		return issuerkeyinfo;
	}
}
