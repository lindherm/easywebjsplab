package com.watchdata.cardcheck.dao.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * StaticData.java
 * 
 * @description: 静态数据model类
 * 
 * @author: pei.li 2012-3-26
 * 
 *@version:1.0.0
 * 
 *@modify：
 * 
 *@Copyright：watchdata
 */
public class StaticData implements RowMapper, Comparable<StaticData> {

	private int id, length;
	private String appType, tag, oriValue, cardValue, isMatch, dscrpt;

	public int getId() {
		return id;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getOriValue() {
		return oriValue;
	}

	public void setOriValue(String oriValue) {
		this.oriValue = oriValue;
	}

	public String getCardValue() {
		return cardValue;
	}

	public void setCardValue(String cardValue) {
		this.cardValue = cardValue;
	}

	public String getIsMatch() {
		return isMatch;
	}

	public void setIsMatch(String isMatch) {
		this.isMatch = isMatch;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDscrpt() {
		return dscrpt;
	}

	public void setDscrpt(String dscrpt) {
		this.dscrpt = dscrpt;
	}

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		StaticData staticData = new StaticData();
		staticData.setId(rs.getInt("ID"));
		staticData.setAppType(rs.getString("APP_TYPE"));
		staticData.setCardValue(rs.getString("CARD_VALUE"));
		staticData.setDscrpt(rs.getString("DSCRPT"));
		staticData.setIsMatch(rs.getString("IS_MATCH"));
		staticData.setLength(rs.getInt("LENGTH"));
		staticData.setOriValue(rs.getString("ORI_VALUE"));
		staticData.setTag(rs.getString("TAG"));
		return staticData;
	}

	@Override
	public int compareTo(StaticData staticData) {
		// TODO Auto-generated method stub
		return this.getAppType().compareTo(staticData.getAppType());
	}

}
