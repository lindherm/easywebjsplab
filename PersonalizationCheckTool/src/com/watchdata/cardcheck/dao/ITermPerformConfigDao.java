package com.watchdata.cardcheck.dao;

import com.watchdata.cardcheck.dao.pojo.TermPerformConfig;

;
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
public interface ITermPerformConfigDao {
	public String getTermId() throws Exception;

	public TermPerformConfig findTermPerform();

	public boolean updateTermPerform(TermPerformConfig term);

}
