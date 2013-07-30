package com.watchdata.cardcheck.dao.jdbc;

import org.apache.log4j.Logger;

import com.watchdata.cardcheck.dao.AbstractBaseDao;
import com.watchdata.cardcheck.dao.ITermInfoDao;
import com.watchdata.cardcheck.dao.pojo.TermInfo;

/**
 * TermInfoDaoImpl.java
 * 
 * @description: 终端信息dao实现类
 * 
 * @author: pei.li 2012-3-23
 * 
 *@version:1.0.0
 * 
 *@modify：
 * 
 *@Copyright：watchdata
 */
public class TermInfoDaoImpl extends AbstractBaseDao implements ITermInfoDao {

	private static Logger log = Logger.getLogger(TermInfoDaoImpl.class);
	private static final String TERMID_GETID_SQL = "select top 1 TERM_ID from term_info";
	private static final String TERMTYPE_INSERT_SQL = "update term_info set COUNTRY_CODE=?,CURR_CODE=? ,TERM_TYPE =?,TRADE_TYPE=? where TERM_ID = ?";

	private static final String TERMLIMIT_INSERT_SQL = "INSERT INTO TERM_INFO( TAC_REJECT, TAC_ONLINE, TAC_ACCESS,LOW_LIMITATION,TERM_CODE)VALUES (?,?,?,?,?)";
	private static final String TERMLIMIT_SEARCH_SQL = "select * from TERM_INFO";
	private static final String TERMLIMIT_UPDATE_SQL = "UPDATE TERM_INFO SET TAC_REJECT=?,TAC_ONLINE=? ,TAC_ACCESS=?,LOW_LIMITATION=?,TERM_CODE=?";

	@Override
	// 获取默认终端ID
	public String getTermId() throws Exception {
		return (String) this.getJdbcTemplate().queryForObject(TERMID_GETID_SQL,
				null, String.class);
	}

	@Override
	// 更新保存终端类型
	public boolean updateTermType(Object[] params) {
		// TODO Auto-generated method stub
		try {
			this.getJdbcTemplate().update(TERMTYPE_INSERT_SQL, params);
			return true;
		} catch (Exception e) {
			log.info("insert terminal type failed.");
			return false;
		}
	}

	@Override
	// 保存终端限制
	public boolean insertTermLimit(TermInfo term) {

		final Object[] params = new Object[5];
		params[0] = term.getTacReject();
		params[1] = term.getTacOnline();
		params[2] = term.getTacAccess();
		params[3] = term.getLowLimitation();
		params[4] = term.getTermCode();
		try {
			this.getJdbcTemplate().update(TERMLIMIT_INSERT_SQL, params);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	// 从表中获取终端限制
	public TermInfo findTermLimt() {
		TermInfo tl = null;
		try {
			tl = (TermInfo) this.getJdbcTemplate().queryForObject(
					TERMLIMIT_SEARCH_SQL, new TermInfo());
			return tl;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	// 更新终端限制信息
	public boolean updateTermLimit(TermInfo term) {

		final Object[] params = new Object[5];
		
		params[0] = term.getTacReject();
		params[1] = term.getTacOnline();
		params[2] = term.getTacAccess();
		params[3] = term.getLowLimitation();
		params[4] = term.getTermCode();
		try {
			this.getJdbcTemplate().update(TERMLIMIT_UPDATE_SQL, params);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

}
