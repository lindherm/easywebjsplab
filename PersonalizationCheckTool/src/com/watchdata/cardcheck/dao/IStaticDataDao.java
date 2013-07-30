package com.watchdata.cardcheck.dao;

import java.util.List;
import java.util.Set;

import com.watchdata.cardcheck.dao.pojo.StaticData;

/**
 * IStaticDataDao.java
 * 
 * @description: 静态数据dao接口
 * 
 * @author: pei.li 2012-3-26
 * 
 *@version:1.0.0
 * 
 *@modify：fengxia.deng 2012-5-1
 * 
 *@Copyright：watchdata
 */
public interface IStaticDataDao {

	public boolean insertTag(StaticData staticData);

	public boolean editTag(StaticData staticData);

	public boolean delTag(StaticData staticData);

	public List<StaticData> searchStaticData();
	
	public Set<String> searchAppType();

	boolean delAllTag();
	
	public boolean batchInsert(List<StaticData> importDatas);
	
	public boolean batchDel(List<Integer> delDatas);
	
	public boolean batchSaveUpdate(List<StaticData> importDatas);
	public boolean batchCompareUpdate(List<StaticData> compareResult);

	public List<String> searchTagByAppType(String appType);

	int searchByTagAndAppType(String appType, String tag);

	int searchByTagAndAppTypeID(String appType, String tag, int ID);
}
