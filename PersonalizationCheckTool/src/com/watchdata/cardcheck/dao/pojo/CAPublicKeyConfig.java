package com.watchdata.cardcheck.dao.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * 
 * @description:
 * 
 * @author: Desheng.Xu March 29, 2012
 * 
 * @version: 1.0.0
 * 
 * @modify:
 * 
 * @Copyright: watchdata
 */
public class CAPublicKeyConfig implements RowMapper {
	private int capkId;
	private String Rid;
	private String CaType;
	private String Index;
	private String Exp;
	private String Arith;
	private String HashArith;
	private String Module;
	private String SaveTime;

	public int GetcapkId() {
		return capkId;
	}

	public void SetcapkId(int capkId) {
		this.capkId = capkId;
	}

	public String GetRid() {
		return Rid;
	}

	public void SetRid(String Rid) {
		this.Rid = Rid;
	}

	public String GetCaType() {
		return CaType;
	}

	public void SetCaType(String CaType) {
		this.CaType = CaType;
	}

	public String GetIndex() {
		return Index;
	}

	public void SetIndex(String Index) {
		this.Index = Index;
	}

	public String GetExp() {
		return Exp;
	}

	public void SetExp(String Exp) {
		this.Exp = Exp;
	}

	public String GetArith() {
		return Arith;
	}

	public void SetArith(String Arith) {
		this.Arith = Arith;
	}

	public String GetHashArith() {
		return HashArith;
	}

	public void SetHashArith(String HashArith) {
		this.HashArith = HashArith;
	}

	public String GetModule() {
		return Module;
	}

	public void SetModule(String Module) {
		this.Module = Module;
	}
	public String getSaveTime() {
		return SaveTime;
	}

	public void setSaveTime(String saveTime) {
		SaveTime = saveTime;
	}
	
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		CAPublicKeyConfig caPkConfig = new CAPublicKeyConfig();
		caPkConfig.SetcapkId(rs.getInt("CA_PK_ID"));
		caPkConfig.SetRid(rs.getString("RID"));
		caPkConfig.SetCaType(rs.getString("CA_TYPE"));
		caPkConfig.SetIndex(rs.getString("INDEX"));
		caPkConfig.SetExp(rs.getString("EXP"));
		caPkConfig.SetArith(rs.getString("ARITH"));
		caPkConfig.SetHashArith(rs.getString("HASH_ARITH"));
		caPkConfig.SetModule(rs.getString("MODULE"));
		caPkConfig.setSaveTime(rs.getString("SAVETIME"));
		return caPkConfig;
	}

}
