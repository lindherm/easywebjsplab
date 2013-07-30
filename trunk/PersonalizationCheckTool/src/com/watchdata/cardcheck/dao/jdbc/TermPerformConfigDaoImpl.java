package com.watchdata.cardcheck.dao.jdbc;

import com.watchdata.cardcheck.dao.AbstractBaseDao;
import com.watchdata.cardcheck.dao.ITermPerformConfigDao;

import com.watchdata.cardcheck.dao.pojo.TermPerformConfig;

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
public class TermPerformConfigDaoImpl extends AbstractBaseDao implements
		ITermPerformConfigDao {
	private static final String TERMID_GET_SQL = "select top 1 TERM_ID from term_info";
	private static final String TERMPERFORM_SEARCH_SQL = "select TERM_ID,TERM_PERFORM from term_info ";
	private static final String TERMPERFORM_UPDATE_SQL = "update term_info set TERM_PERFORM=? where TERM_ID=?";

	@Override
	// 获取终端性能字段和终端ID值，并以TermPerformConfig对象返回
	public TermPerformConfig findTermPerform() {
		TermPerformConfig tp = new TermPerformConfig();
		try {
			tp = (TermPerformConfig) this.getJdbcTemplate().queryForObject(
					TERMPERFORM_SEARCH_SQL, new TermPerformConfig());
			return tp;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getTermId() throws Exception {
		return (String) this.getJdbcTemplate().queryForObject(TERMID_GET_SQL,
				null, String.class);
	}

	// 更新终端性能配置字段
	@Override
	public boolean updateTermPerform(TermPerformConfig term) {
		// TODO Auto-generated method stub
		final Object[] params = new Object[2];
		params[0] = term.getTermPerform();
		params[1] = term.getTermId();
		try {
			this.getJdbcTemplate().update(TERMPERFORM_UPDATE_SQL, params);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
