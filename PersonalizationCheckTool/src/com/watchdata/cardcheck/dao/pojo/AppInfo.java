package com.watchdata.cardcheck.dao.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AppInfo implements RowMapper{
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
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		AppInfo appinfo = new AppInfo();
		appinfo.setAid(rs.getString("AID"));
		appinfo.setDscrpt(rs.getString("DSCRPT"));
		appinfo.setVersion(rs.getString("VERSION"));
		return appinfo;
	}
}
