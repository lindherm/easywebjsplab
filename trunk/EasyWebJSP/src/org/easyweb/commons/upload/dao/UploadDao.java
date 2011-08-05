package org.easyweb.commons.upload.dao;

import org.easyweb.commons.upload.model.UploadFile;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class UploadDao extends HibernateDaoSupport {
	public void save(UploadFile file){
		this.getHibernateTemplate().save(file);
	}
}
