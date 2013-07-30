package com.watchdata.cardcheck.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.watchdata.cardcheck.dao.AbstractBaseDao;
import com.watchdata.cardcheck.dao.IStaticDataDao;
import com.watchdata.cardcheck.dao.pojo.StaticData;

/**
 * StaticDataDaoImpl.java
 * 
 * @description: 静态数据dao实现类
 * 
 * @author: pei.li 2012-3-26
 * 
 *@version:1.0.0
 * 
 *@modify：fengxia.deng 2012-5-2
 * 
 *@Copyright：watchdata
 */
public class StaticDataDaoImpl extends AbstractBaseDao implements
		IStaticDataDao {

	private static Logger log = Logger.getLogger(StaticDataDaoImpl.class);
	private static final String STATICDATA_INSERT_SQL = "insert into STATIC_DATA_DETECT (APP_TYPE,TAG,LENGTH,ORI_VALUE,DSCRPT)values(?,?,?,?,?)";
	private static final String STATICDATA_DEL_SQL = "delete from STATIC_DATA_DETECT where ID = ?";
	private static final String STATICDATA_UPDATE_SQL = "update STATIC_DATA_DETECT set APP_TYPE=?, TAG=?,LENGTH=? ,ORI_VALUE =?,DSCRPT=? where ID = ?";
	private static final String STATICDATA_SEARCH_SQL = "select * from STATIC_DATA_DETECT";
	private static final String STATICDATA_SEARCHAPPTYPE_SQL = "select distinct APP_TYPE from STATIC_DATA_DETECT";
	private static final String STATICDATA_DELALL_SQL = "delete from STATIC_DATA_DETECT";
	private static final String STATICDATA_COMPARE_UPDATE_SQL ="update STATIC_DATA_DETECT set CARD_VALUE = ?,IS_MATCH =? where ID = ?";
    private static final String STATICDATA_TAGSEARCH_SQL = "select ID, TAG from STATIC_DATA_DETECT where APP_TYPE=?";
	private static final String STATICDATA_TAGAPPTYPESEARCH_SQL = "select count(*) from STATIC_DATA_DETECT where APP_TYPE=? and TAG=?";
	private static final String STATICDATA_TAGAPPTYPEIDSEARCH_SQL = "select count(*) from STATIC_DATA_DETECT where APP_TYPE=? and TAG=? and ID=?";
	
    @SuppressWarnings("unchecked")
	@Override
	public List<StaticData> searchStaticData() {
		try {
			List<StaticData> sdList = (List<StaticData>) this.getJdbcTemplate()
					.query(STATICDATA_SEARCH_SQL, new StaticData());
			return sdList;
		} catch (Exception e) {
			log.info("StaticData search failed.");
			return null;
		}

	}

	@Override
	public boolean delTag(StaticData staticData) {
		// TODO Auto-generated method stub
		Object[] param = new Object[1];
		param[0] = staticData.getId();
		try {
			this.getJdbcTemplate().update(STATICDATA_DEL_SQL, param);
			return true;
		} catch (Exception e) {
			log.info("delete StaticData failed.");
			return false;
		}

	}

	@Override
	public boolean editTag(StaticData staticData) {
		// TODO Auto-generated method stub
		Object[] params = new Object[6];
		params[0] = staticData.getAppType();
		params[1] = staticData.getTag();
		params[2] = staticData.getLength();
		params[3] = staticData.getOriValue();
		params[4] = staticData.getDscrpt();
		params[5] = staticData.getId();
		try {
			this.getJdbcTemplate().update(STATICDATA_UPDATE_SQL, params);
			return true;
		} catch (Exception e) {
			log.info("save the edited StaticData failed.");
			return false;
		}
	}

	@Override
	public boolean insertTag(StaticData staticData) {
		// TODO Auto-generated method stub
		Object[] params = new Object[5];
		params[0] = staticData.getAppType();
		params[1] = staticData.getTag();
		params[2] = staticData.getLength();
		params[3] = staticData.getOriValue();
		params[4] = staticData.getDscrpt();
		try {
			this.getJdbcTemplate().update(STATICDATA_INSERT_SQL, params);
			return true;
		} catch (Exception e) {
			log.info("insert StaticData failed.");
			return false;
		}

	}

	@Override
	public boolean delAllTag() {
		try {
			this.getJdbcTemplate().update(STATICDATA_DELALL_SQL);
			return true;
		} catch (Exception e) {
			log.info("delete all StaticData failed.");
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> searchAppType() {
		Set<String> comboData = new HashSet<String>();
		try {
			List<Map> appType = this.getJdbcTemplate().queryForList(
					STATICDATA_SEARCHAPPTYPE_SQL);
			for (int i = 0; i < appType.size(); i++) {
				comboData.add(appType.get(i).get("APP_TYPE").toString());
			}
			return comboData;
		} catch (Exception e) {
			log.info("search appType failed.");
			/*e.printStackTrace();*/
			return null;
		}
	}

	@Override
	public boolean batchDel(final List<Integer> delDatas) {
		boolean success = true;
		int[] result = new int[delDatas.size()];
		try {
			result = this.getJdbcTemplate().batchUpdate(STATICDATA_DEL_SQL,
					new BatchPreparedStatementSetter() {
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							ps.setInt(1, delDatas.get(i));
						}

						public int getBatchSize() {
							return delDatas.size();
						}
					});
		} catch (Exception e) {
			/*e.printStackTrace();*/
			log.info("batch delete staticData failed.");
			return false;
		}

		for (int i = 0; i < result.length; i++) {
			success = (result[i] == 1 ? true : false);
		}

		return success;
	}

	@Override
	public boolean batchInsert(final List<StaticData> importDatas) {
		boolean success = true;
		int[] result = new int[importDatas.size()];
		try {
			result = this.getJdbcTemplate().batchUpdate(STATICDATA_INSERT_SQL,
					new BatchPreparedStatementSetter() {
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							String appType = importDatas.get(i).getAppType();
							String tag = importDatas.get(i).getTag();
							int length = importDatas.get(i).getLength();
							String value = importDatas.get(i).getOriValue();
							String dscrpt = importDatas.get(i).getDscrpt();
							ps.setString(1, appType);
							ps.setString(2, tag);
							ps.setInt(3, length);
							ps.setString(4, value);
							ps.setString(5, dscrpt);

						}

						public int getBatchSize() {
							return importDatas.size();
						}
					});
		} catch (Exception e) {
			log.info("batch insert staticData failed.");
			/*e.printStackTrace();*/
			return false;
		}
		for (int i = 0; i < result.length; i++) {
			success = (result[i] == 1 ? true : false);
		}
		return success;
	}
	
	@Override
	public boolean batchSaveUpdate(final List<StaticData> importDatas) {
		boolean success = true;
		int[] result = new int[importDatas.size()];
		try {
			result = this.getJdbcTemplate().batchUpdate(STATICDATA_UPDATE_SQL,
					new BatchPreparedStatementSetter() {
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							String appType = importDatas.get(i).getAppType();
							String tag = importDatas.get(i).getTag();
							int length = importDatas.get(i).getLength();
							String value = importDatas.get(i).getOriValue();
							String dscrpt = importDatas.get(i).getDscrpt();
							int id = importDatas.get(i).getId();
							ps.setString(1, appType);
							ps.setString(2, tag);
							ps.setInt(3, length);
							ps.setString(4, value);
							ps.setString(5, dscrpt);
							ps.setInt(6, id);
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
	@Override
	public boolean batchCompareUpdate(final List<StaticData> compareResult) {
		boolean success = true;
		int[] result = new int[compareResult.size()];
		try {
			result = this.getJdbcTemplate().batchUpdate(STATICDATA_COMPARE_UPDATE_SQL,
					new BatchPreparedStatementSetter() {
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							ps.setString(1, compareResult.get(i).getCardValue());
							ps.setString(2, compareResult.get(i).getIsMatch());
							ps.setInt(3, compareResult.get(i).getId());
						}

						public int getBatchSize() {
							return compareResult.size();
						}
					});
		} catch (Exception e) {
			log.info("batch compare and update staticData failed.",e);
			return false;
		}

		for (int i = 0; i < result.length; i++) {
			success = (result[i] == 1 ? true : false);
		}

		return success;
	}
   
	@SuppressWarnings("unchecked")
	public List<String> searchTagByAppType(String appType){
		Object[] param = new Object[1];
		param[0] = appType;
		try{
			List<String> tagList = (List<String>) this.getJdbcTemplate().queryForList(STATICDATA_TAGSEARCH_SQL, param);
			return tagList;
		}catch (Exception e) {
			log.info("Search tag by appType failed.");
			return null;
		}	
	}
	
	@Override
	public int searchByTagAndAppType(String appType, String tag){
		Object[] param = new Object[2];
		param[0] = appType;
		param[1] = tag;
		try{
			int tagList = this.getJdbcTemplate().queryForInt(STATICDATA_TAGAPPTYPESEARCH_SQL, param);
			return tagList;
		}catch (Exception e) {
			log.info("Search by tag appType failed.");
			/*e.printStackTrace();*/
			return -1;
		}
	}
	
	@Override
	public int searchByTagAndAppTypeID(String appType, String tag, int ID){
		Object[] param = new Object[3];
		param[0] = appType;
		param[1] = tag;
		param[2] = ID;
		try{
			int tagList = this.getJdbcTemplate().queryForInt(STATICDATA_TAGAPPTYPEIDSEARCH_SQL, param);
			return tagList;
		}catch (Exception e) {
			log.info("Search by tag appType id failed.");
			/*e.printStackTrace();*/
			return -1;
		}
	}
	

}
