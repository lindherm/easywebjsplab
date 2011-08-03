package org.easyweb.commons.upload.action;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easyweb.mvc.HttpServletAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

public class UploadAction extends FileUploadAction implements HttpServletAware {
	HttpServletRequest request;
	HttpServletResponse response;

	public ModelAndView action() {
		ModelAndView mv = new ModelAndView("/examples/swfUploadAction.jsp");
		return mv;
	}

	public ModelAndView saveFile() {
		ModelAndView mv = null;
		MultipartFile file = this.getFiledata();
		// 创建保存文件的目录
		String base = request.getSession().getServletContext().getRealPath("/uploadimages/");
		File filePath = new File(base);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		
		File destFile=new File(base + File.separator + file.getOriginalFilename());
		try {
			file.transferTo(destFile);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}

	public void setHttpRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

	public void setHttpResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response = response;
	}
}
