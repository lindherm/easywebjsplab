package com.watchdata.cardcheck.dao;

import com.watchdata.cardcheck.dao.pojo.TermInfo;

/**
 * ITermInfoDao.java
 * 
 * @description: 终端信息dao接口
 * 
 * @author: pei.li 2012-3-23
 * 
 *@version:1.0.0
 * 
 *@modify：
 * 
 *@Copyright：watchdata
 */
public interface ITermInfoDao {

	public String getTermId() throws Exception;

	public boolean updateTermType(Object[] params);

	public boolean insertTermLimit(TermInfo term);

	public TermInfo findTermLimt();

	public boolean updateTermLimit(TermInfo term);

}
