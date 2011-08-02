package org.easyweb.commons.upload.action;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadAction {
	public FileUploadAction() {
	}

	public MultipartFile getFiledata() {
		return Filedata;
	}

	public void setFiledata(MultipartFile filedata) {
		Filedata = filedata;
	}

	private MultipartFile Filedata;
}
