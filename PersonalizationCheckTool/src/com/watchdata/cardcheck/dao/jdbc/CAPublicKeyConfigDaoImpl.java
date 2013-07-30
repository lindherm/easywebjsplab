package com.watchdata.cardcheck.dao.jdbc;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import com.spinn3r.log5j.Logger;
import com.watchdata.cardcheck.dao.AbstractBaseDao;
import com.watchdata.cardcheck.dao.ICAPublicKeyConfigDao;
import com.watchdata.cardcheck.dao.pojo.CAPublicKeyConfig;
import com.watchdata.commons.lang.WDAssert;

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
public class CAPublicKeyConfigDaoImpl extends AbstractBaseDao implements
		ICAPublicKeyConfigDao {
	// log5j日志管理
	private static Logger log = Logger.getLogger();

	private static final String CAPKCONFIG_INSERT_SQL = "INSERT INTO CA_PK_INFO (RID,CA_TYPE,INDEX,EXP,ARITH,HASH_ARITH,MODULE,SAVETIME)VALUES(?,?,?,?,?,?,?,now())";
	private static final String CAPKCONFIG_UPDATE_SQL = "UPDATE CA_PK_INFO SET CA_TYPE=?,EXP=?,ARITH=?,HASH_ARITH=?,MODULE=?,SAVETIME=now() WHERE INDEX=? and RID=?";
	private static final String CAPKCONFIG_UPDATE_RID_SQL = "UPDATE CA_PK_INFO SET CA_TYPE=?,EXP=?,ARITH=?,HASH_ARITH=?,MODULE=?,INDEX=?,SAVETIME=now() WHERE RID=?";
	private static final String CAPKCONFIG_SEARCH_SQL = "SELECT * FROM CA_PK_INFO";
	private static final String CAPKCONFIG_SEARCH_DEFAULT_SQL = "SELECT * FROM CA_PK_INFO ORDER BY SAVETIME DESC";
	private static final String CAPKCONFIG_KEYEXIST_SQL = "SELECT * FROM CA_PK_INFO WHERE RID=?";
	private static final String CAPKCONFIG_GETCAKEY_SQL = "SELECT * FROM CA_PK_INFO WHERE RID=? AND INDEX=?";
	private static final String CAPKCONFIG_DELETE_SQL = "DELETE FROM CA_PK_INFO WHERE RID=? AND INDEX=?";
	private static final String CAPKCONFIG_DELETE_RID_SQL = "DELETE FROM CA_PK_INFO WHERE RID=?";

	@Override
	// 查找出表中所有CA密钥并返回
	public List<CAPublicKeyConfig> findCAPublicKeyConfig() {
		// TODO Auto-generated method stub
		List<CAPublicKeyConfig> capkconfigList = null;
		try {
			capkconfigList = (List<CAPublicKeyConfig>) this.getJdbcTemplate()
					.query(CAPKCONFIG_SEARCH_SQL, new CAPublicKeyConfig());
			return capkconfigList;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	// 查找出表中所有CA密钥并返回
	public List<CAPublicKeyConfig> findDefaultCAPublicKeyConfig() {
		// TODO Auto-generated method stub
		List<CAPublicKeyConfig> capkconfigList = null;
		try {
			capkconfigList = (List<CAPublicKeyConfig>) this.getJdbcTemplate()
					.query(CAPKCONFIG_SEARCH_DEFAULT_SQL, new CAPublicKeyConfig());
			return capkconfigList;
		} catch (Exception e) {
			return null;
		}
	}	
	
	@Override
	// 插入CA密钥，相关字段的值通过CAPublicKeyConfig对象传入
	public boolean insertCAPublicKeyConfig(CAPublicKeyConfig caPkConfig) {
		// TODO Auto-generated method stub
		final Object[] params = new Object[7];
		params[0] = caPkConfig.GetRid();
		params[1] = caPkConfig.GetCaType();
		params[2] = caPkConfig.GetIndex();
		params[3] = caPkConfig.GetExp();
		params[4] = caPkConfig.GetArith();
		params[5] = caPkConfig.GetHashArith();
		params[6] = caPkConfig.GetModule();
		try {
			this.getJdbcTemplate().update(CAPKCONFIG_INSERT_SQL, params);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	// 当index!=null时更新CA密钥，相关字段的值通过CAPublicKeyConfig对象传入
	public boolean updateCAPublicKeyConfig(CAPublicKeyConfig caPkConfig) {
		// TODO Auto-generated method stub
		final Object[] params = new Object[7];
		// params[0] = caPkConfig.GetRid();
		params[0] = caPkConfig.GetCaType();
		params[1] = caPkConfig.GetExp();
		params[2] = caPkConfig.GetArith();
		params[3] = caPkConfig.GetHashArith();
		params[4] = caPkConfig.GetModule();
		params[5] = caPkConfig.GetIndex();
		params[6] = caPkConfig.GetRid();
		try {
			this.getJdbcTemplate().update(CAPKCONFIG_UPDATE_SQL, params);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	// 当index为null时，更新CA密钥，相关字段的值通过CAPublicKeyConfig对象传入
	public boolean updateCAPublicKeyConfig2(CAPublicKeyConfig caPkConfig) {
		// TODO Auto-generated method stub
		final Object[] params = new Object[7];
		// params[0] = caPkConfig.GetRid();
		params[0] = caPkConfig.GetCaType();
		params[1] = caPkConfig.GetExp();
		params[2] = caPkConfig.GetArith();
		params[3] = caPkConfig.GetHashArith();
		params[4] = caPkConfig.GetModule();
		params[5] = caPkConfig.GetIndex();
		params[6] = caPkConfig.GetRid();
		try {
			this.getJdbcTemplate().update(CAPKCONFIG_UPDATE_RID_SQL, params);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// @Override
	// // 查找数据库中RID为形参RID的CA密钥，并返回
	// public CAPublicKeyConfig getCAPublicKey(String RID) {
	//
	// CAPublicKeyConfig capkconfig = null;
	// final Object[] params = { RID };
	// try {
	// capkconfig = (CAPublicKeyConfig)
	// this.getJdbcTemplate().queryForObject(CAPKCONFIG_KEYEXIST_SQL, params,
	// new CAPublicKeyConfig());
	// return capkconfig;
	//
	// } catch (Exception e) {
	// //e.printStackTrace();
	// return null;
	// }
	//
	// }

	@Override
	/**
	 * 根据rid
	 * 从数据库中查找对应的ca对象列表
	 */
	public List<CAPublicKeyConfig> getCAPublicKeyList(String RID) {

		List<CAPublicKeyConfig> capkconfig = null;
		final Object[] params = { RID };
		try {
			capkconfig = (List<CAPublicKeyConfig>) this.getJdbcTemplate()
					.query(CAPKCONFIG_KEYEXIST_SQL, params,
							new CAPublicKeyConfig());
			return capkconfig;

		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}

	}

	@Override
	/**
	 * 查找ca公钥 2012年4月12日 by liya.xiao
	 * 
	 * @param rid
	 *            应用id
	 * @param keyIndex
	 *            公钥索引
	 * @return
	 */
	public CAPublicKeyConfig getCAPublicKey(String rid, String caKeyIndex) {
		// 入口参数合法性校检
		if (WDAssert.isEmpty(rid)) {
			log.error("rid cann't be null.");
			return null;
		}
		if (WDAssert.isEmpty(caKeyIndex)) {
			log.error("keyIndex cann't be null.");
			return null;
		}
		// 查询参数声明
		final Object[] params = new Object[2];
		params[0] = rid;
		params[1] = caKeyIndex;

		Object object = null;
		try {
			// 执行查询
			object = this.getJdbcTemplate().queryForObject(
					CAPKCONFIG_GETCAKEY_SQL, params, new CAPublicKeyConfig());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		// 转成目标vo
		CAPublicKeyConfig caPublicKeyConfig = (CAPublicKeyConfig) object;
		// 返回查询结果
		return caPublicKeyConfig;
	}

	@Override
	// 删除CA密钥
	public boolean delCAPublicKeyConfig(String rid) {
		// TODO Auto-generated method stub
		final Object[] params = new Object[1];
		params[0] = rid;
		try {
			this.getJdbcTemplate().update(CAPKCONFIG_DELETE_RID_SQL, params);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delCAPublicKeyConfig(String rid, String caKeyIndex) {
		// TODO Auto-generated method stub
		final Object[] params = new Object[2];
		params[0] = rid;
		params[1] = caKeyIndex;
		try {
			this.getJdbcTemplate().update(CAPKCONFIG_DELETE_SQL, params);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
