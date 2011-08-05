package org.easyweb.commons.upload.manager;

import java.util.List;

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
	public UploadFile getFileById(String id){
		return uploadDao.getFileById(id);
	}
	public List<UploadFile> getAllFile(){
		return uploadDao.getAllFile();
	}
}
