package org.easyweb.jdocx;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.core.util.Base64Encoder;

/**
 * Servlet implementation class for Servlet: TestDemo
 * 
 */
public class TestDemo extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	static final long serialVersionUID = 1L;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public TestDemo() {
		super();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	private final static String FILE_PATH = "/WEB-INF/template/";

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String srcFileName = request.getSession().getServletContext().getRealPath(FILE_PATH) + "\\document.docx";
		String imagePath = request.getSession().getServletContext().getRealPath(FILE_PATH) + "\\4-20-15810-20-363-26-20070920070459.jpg";
		// 图片
		BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
		String imageFormat = imagePath.substring(imagePath.lastIndexOf(".") + 1);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, imageFormat, bos);

		byte[] imagebyte = bos.toByteArray();
		String imageStr = new Base64Encoder().encode(imagebyte);
		String destFileName = request.getSession().getServletContext().getRealPath(FILE_PATH) + "\\dest.docx";
		DOCXTransformer transformer = new DOCXTransformer();
		Map mapBeans = new HashMap();
		HelloWorld helloWorld = new HelloWorld();
		helloWorld.setReplaceImageName(imageStr);
		helloWorld.setPass("测试englishi世好人不偿命");
		mapBeans.put("helloWorld", helloWorld);

		SystemUser systemUser = new SystemUser();
		systemUser.setUserName("肖利亚1234");
		systemUser.setPassWord("52013一世好人不偿命");
		mapBeans.put("systemUser", systemUser);
		String s = transformer.transformDOCX(srcFileName, mapBeans, destFileName);
		// 到处下载流到页面
		/*
		 * File file=new File(destFileName); FileInputStream fis=new
		 * FileInputStream(file); file.length(); byte[] bytes=new
		 * byte[(int)file.length()]; fis.read(bytes,0,bytes.length);
		 */
		response.setContentType("application/msword");
		response.setHeader("Content-disposition", "attachment;filename=" + new String("word导出".getBytes("gb2312"), "iso8859-1") + ".doc");
		ServletOutputStream out = response.getOutputStream();
		out.write(s.getBytes("UTF-8"));
		// out.write(bytes);
	}
}