package org.easyweb.commons.upload.action;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easyweb.commons.upload.manager.UploadManager;
import org.easyweb.commons.upload.model.UploadFile;
import org.springframework.web.servlet.ModelAndView;

public class DownLoadAction {
	String id;
	UploadManager uploadManager;
	HttpServletRequest request;
	HttpServletResponse response;

	public ModelAndView action() {
		UploadFile file = uploadManager.getFileById(id);
		if (file == null) {
			return null;
		}
		BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(response.getOutputStream());
			response.setContentType(file.getFileContentType() + ";charset=utf8");
			response.addHeader("Content-Disposition", "filename=" + URLEncoder.encode(file.getFileName(), "UTF-8"));
			out.write(file.getFileContent());
			response.setStatus(HttpServletResponse.SC_OK);
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (out!=null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public void setHttpRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setHttpResponse(HttpServletResponse response) {
		this.response = response;
	}

	public UploadManager getUploadManager() {
		return uploadManager;
	}

	public void setUploadManager(UploadManager uploadManager) {
		this.uploadManager = uploadManager;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
