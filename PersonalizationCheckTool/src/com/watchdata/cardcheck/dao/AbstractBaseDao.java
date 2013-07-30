package com.watchdata.cardcheck.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;


/**
 * 抽象Dao基类 支持SQL命名参数(:paraName)和普通JdbcTemplate
 * 
 * @description: 所有Dao类都继承该类 在Spring配置文件中只需要集中为配置一次DataSource
 * @author: hao.zheng May 5, 2010
 * @version: 1.0.0
 * @modify:
 * @Copyright: watchdata
 */
public abstract class AbstractBaseDao extends NamedParameterJdbcDaoSupport {

	
	

}
