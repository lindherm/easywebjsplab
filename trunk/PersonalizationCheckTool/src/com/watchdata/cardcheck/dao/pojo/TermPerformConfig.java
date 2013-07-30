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
public class TermPerformConfig implements RowMapper {
	private String termPerform;
	private String termId;

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getTermPerform() {
		return termPerform;
	}

	public void setTermPerform(String termPerform) {
		this.termPerform = termPerform;
	}

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		TermPerformConfig termPerform = new TermPerformConfig();
		termPerform.setTermId(rs.getString("TERM_ID"));
		termPerform.setTermPerform(rs.getString("TERM_PERFORM"));
		return termPerform;
	}

}
