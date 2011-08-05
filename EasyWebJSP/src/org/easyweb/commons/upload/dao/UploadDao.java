package org.easyweb.commons.upload.dao;

import java.util.List;

import org.easyweb.commons.upload.model.UploadFile;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class UploadDao extends HibernateDaoSupport {
	public void save(UploadFile file) {
		this.getHibernateTemplate().save(file);
	}

	public UploadFile getFileById(String id) {
		return (UploadFile) this.getHibernateTemplate().get(UploadFile.class,id);
	}
	public List<UploadFile> getAllFile() {
		return this.getHibernateTemplate().find("from UploadFile");
	}
}
