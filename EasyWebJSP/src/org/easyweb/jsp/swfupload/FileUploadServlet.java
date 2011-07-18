package org.easyweb.jsp.swfupload;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = -7825355637448948879L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置内存缓冲区，超过后写入临时文件
		factory.setSizeThreshold(10240000);
		// 设置临时文件存储位置
		String base =this.getServletContext().getRealPath("/uploadimages/");
		File file = new File(base);
		if (!file.exists())
			file.mkdirs();
		factory.setRepository(file);
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 设置单个文件的最大上传值
		upload.setFileSizeMax(10002400000l);
		// 设置整个request的最大值
		upload.setSizeMax(10002400000l);
		upload.setHeaderEncoding("UTF-8");

		try {
			List<?> items = upload.parseRequest(request);
			FileItem item = null;
			String fileName = null;
			for (int i = 0; i < items.size(); i++) {
				item = (FileItem) items.get(i);
				fileName = base + File.separator + item.getName();
				// 保存文件
				if (!item.isFormField() && item.getName().length() > 0) {
					item.write(new File(fileName));
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
