package com.watchdata.cardcheck.dao;

import com.watchdata.cardcheck.dao.pojo.CAPublicKeyConfig;
import java.util.List;

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

public interface ICAPublicKeyConfigDao {
	public List<CAPublicKeyConfig> findCAPublicKeyConfig();
	
	public List<CAPublicKeyConfig> findDefaultCAPublicKeyConfig();

	public boolean insertCAPublicKeyConfig(CAPublicKeyConfig caPkConfig);

	public boolean updateCAPublicKeyConfig(CAPublicKeyConfig caPkConfig);

	public boolean updateCAPublicKeyConfig2(CAPublicKeyConfig caPkConfig);

	// public CAPublicKeyConfig getCAPublicKey(String RID);
	// 添加查找ca公钥的接口 @author: liya.xiao 2012年4月12日
	public CAPublicKeyConfig getCAPublicKey(String rid, String caKeyIndex);

	public List<CAPublicKeyConfig> getCAPublicKeyList(String rid);

	public boolean delCAPublicKeyConfig(String rid, String caKeyIndex);

	public boolean delCAPublicKeyConfig(String rid);
}
