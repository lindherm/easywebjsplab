package com.watchdata.cardcheck.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.watchdata.cardcheck.dao.AbstractBaseDao;
import com.watchdata.cardcheck.dao.IAppInfoDao;
import com.watchdata.cardcheck.dao.pojo.AppInfo;
import com.watchdata.cardcheck.dao.pojo.StaticData;

public class AppInfoDaoImpl extends AbstractBaseDao implements IAppInfoDao{
	private static Logger log = Logger.getLogger(StaticDataDaoImpl.class);
	private static final String APPINFO_INSERT_SQL = "INSERT INTO APP_INFO( AID,DSCRPT, VERSION)VALUES (?,?,?)";
	private static final String APPINFO_SEARCH_SQL = "select * from APP_INFO where AID=?";
	private static final String APPINFO_DELETE_SQL = "delete from APP_INFO where AID=?";
	private static final String APPINFO_UPDATE_SQL = "UPDATE APP_INFO SET DSCRPT=?,VERSION=? where AID=?";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AppInfo> findAppInfo() {
		List<AppInfo> sdList = null;
		sdList = (List<AppInfo>)this.getJdbcTemplate().query("select * from APP_INFO ",new AppInfo());
		return sdList;
	}	
	
	
	@Override
	public String getAppId() {
		return (String)this.getJdbcTemplate().queryForObject("select top 1 aid from app_info",null,String.class);
		 
	}

	@Override
	public boolean deleteAppinfo() {
		// TODO Auto-generated method stub
		try {
			this.getJdbcTemplate().update(APPINFO_DELETE_SQL);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public AppInfo findAppinfo(String appid) {
		// TODO Auto-generated method stub
		final Object[] params =  {appid};
		AppInfo tl = null;
		try {
			tl = (AppInfo) this.getJdbcTemplate().queryForObject(APPINFO_SEARCH_SQL, params, new AppInfo());
			return tl;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean insertAppinfo(AppInfo appinfo) {
		// TODO Auto-generated method stub
		final Object[] params = new Object[3];
		params[0] = appinfo.getAid();
		params[1] = appinfo.getDscrpt();
		params[2] = appinfo.getVersion();
		try {
			this.getJdbcTemplate().update(APPINFO_INSERT_SQL, params);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean updateAppinfo(AppInfo appinfo) {
		// TODO Auto-generated method stub
		final Object[] params = new Object[3];
		params[0] = appinfo.getAid();
		params[1] = appinfo.getDscrpt();
		params[2] = appinfo.getVersion();
		try {
			this.getJdbcTemplate().update(APPINFO_UPDATE_SQL, params);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	@Override
	public boolean batchDel(final List<String> delDatas) {
		boolean success = true;
		int[] result = new int[delDatas.size()];
		try {
			result = this.getJdbcTemplate().batchUpdate(APPINFO_DELETE_SQL,
					new BatchPreparedStatementSetter() {
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							ps.setString(1, delDatas.get(i));
						}

						public int getBatchSize() {
							return delDatas.size();
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			log.info("batch delete staticData failed.");
			return false;
		}

		for (int i = 0; i < result.length; i++) {
			success = (result[i] == 1 ? true : false);
		}

		return success;
	}	
	@Override
	public boolean batchSaveUpdate(final List<AppInfo> importDatas) {
		boolean success = true;
		int[] result = new int[importDatas.size()];
		try {
			result = this.getJdbcTemplate().batchUpdate(APPINFO_UPDATE_SQL,
					new BatchPreparedStatementSetter() {
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							String aid = importDatas.get(i).getAid();
							String discript = importDatas.get(i).getDscrpt();
							String version = importDatas.get(i).getVersion();
							ps.setString(3, aid);
							ps.setString(1, discript);
							ps.setString(2, version);
						}

						public int getBatchSize() {
							return importDatas.size();
						}
					});
		} catch (Exception e) {
			log.info("batch insert staticData failed.");
			return false;
		}
		for (int i = 0; i < result.length; i++) {
			success = (result[i] == 1 ? true : false);
		}
		return success;
	}
}
