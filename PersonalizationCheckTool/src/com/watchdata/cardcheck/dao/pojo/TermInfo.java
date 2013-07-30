
package com.watchdata.cardcheck.dao.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author ya.he
 * @Created on 2012-3-21上午09:00:22
 */
public class TermInfo implements RowMapper{
	private int termId;
	public int getTermId() {
		return termId;
	}
	public void setTermId(int termId) {
		this.termId = termId;
	}
	public String getTermType() {
		return termType;
	}
	public void setTermType(String termType) {
		this.termType = termType;
	}
	public String getTermPerform() {
		return termPerform;
	}
	public void setTermPerform(String termPerform) {
		this.termPerform = termPerform;
	}
	public String getTermAddPerform() {
		return termAddPerform;
	}
	public void setTermAddPerform(String termAddPerform) {
		this.termAddPerform = termAddPerform;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCurrCode() {
		return currCode;
	}
	public void setCurrCode(String currCode) {
		this.currCode = currCode;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getTacReject() {
		return tacReject;
	}
	public void setTacReject(String tacReject) {
		this.tacReject = tacReject;
	}
	public String getTacOnline() {
		return tacOnline;
	}
	public void setTacOnline(String tacOnline) {
		this.tacOnline = tacOnline;
	}
	public String getTacAccess() {
		return tacAccess;
	}
	public void setTacAccess(String tacAccess) {
		this.tacAccess = tacAccess;
	}
	public String getLowLimitation() {
		return lowLimitation;
	}
	public void setLowLimitation(String lowLimitation) {
		this.lowLimitation = lowLimitation;
	}
	private String termType;
	private String termPerform;
	private String termAddPerform;
	private String countryCode;
	private String currCode;
	private String tradeType;
	private String tacReject;
	private String tacOnline;
	private String tacAccess;
	private String lowLimitation;
	private String termCode;
	
	public String getTermCode() {
		return termCode;
	}
	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		TermInfo termInfo = new TermInfo();
		termInfo.setTermId(rs.getInt("TERM_ID"));
		termInfo.setTacReject(rs.getString("TAC_REJECT"));
		termInfo.setTacOnline(rs.getString("TAC_ONLINE"));
		termInfo.setTacAccess(rs.getString("TAC_ACCESS"));
		termInfo.setLowLimitation(rs.getString("LOW_LIMITATION"));
		termInfo.setTermCode(rs.getString("TERM_CODE"));
		termInfo.setTermType(rs.getString("TERM_TYPE"));
		termInfo.setCountryCode(rs.getString("COUNTRY_CODE"));
		termInfo.setCurrCode(rs.getString("CURR_CODE"));
		termInfo.setTradeType(rs.getString("TRADE_TYPE"));
		return termInfo;
	}
	

}
