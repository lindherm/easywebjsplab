package org.easyweb.commons.upload.manager;

import org.easyweb.commons.upload.dao.UploadDao;
import org.easyweb.commons.upload.model.UploadFile;

public class UploadManager {
	UploadDao uploadDao;
	public UploadDao getUploadDao() {
		return uploadDao;
	}
	public void setUploadDao(UploadDao uploadDao) {
		this.uploadDao = uploadDao;
	}
	public void save(UploadFile file){
		uploadDao.save(file);
	}
}
