package org.easyweb.commons.upload.action;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.easyweb.commons.upload.manager.UploadManager;
import org.easyweb.commons.upload.model.UploadFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

public class UploadAction extends FileUploadAction{
	UploadManager uploadManager;
	public ModelAndView action() {
		ModelAndView mv = new ModelAndView("/examples/uploadAction.jsp");
		mv.addObject("filelist",uploadManager.getAllFile());
		return mv;
	}
	public ModelAndView saveFile() throws IOException {
		ModelAndView mv = null;
		String uuid=UUID.randomUUID().toString().replace("-", "");
		MultipartFile file = this.getFiledata();
		UploadFile uploadFile=new UploadFile();
		uploadFile.setId(uuid);
		String originalFilename=file.getOriginalFilename();
		uploadFile.setFileName(originalFilename);
		uploadFile.setFileType(originalFilename.substring(originalFilename.indexOf(".")+1,originalFilename.length()));
		uploadFile.setFileContentType(file.getContentType());
		uploadFile.setFileContent(file.getBytes());
		uploadFile.setFileSize(file.getSize());
		uploadFile.setUpdateTime(new Date());
		uploadManager.save(uploadFile);
		return mv;
	}
	
	public UploadManager getUploadManager() {
		return uploadManager;
	}

	public void setUploadManager(UploadManager uploadManager) {
		this.uploadManager = uploadManager;
	}
}
