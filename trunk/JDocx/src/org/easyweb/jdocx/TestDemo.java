package org.easyweb.jdocx;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.sun.org.apache.bcel.internal.generic.NEW;

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
		String srcFileName = request.getSession().getServletContext().getRealPath(FILE_PATH) + "/document.xml";
		//String srcFileName = request.getSession().getServletContext().getRealPath(FILE_PATH) + "/document.docx";
		String destFileName = request.getSession().getServletContext().getRealPath(FILE_PATH) + "/dest.docx";
		DOCXTransformer transformer = new DOCXTransformer();
		Map mapBeans = new HashMap();
		HelloWorld helloWorld = new HelloWorld();
		helloWorld.setName("hello,yes or not?这是啥啊？hehe");
		helloWorld.setPass("测试englishi");
		mapBeans.put("helloWorld", helloWorld);

		SystemUser systemUser = new SystemUser();
		systemUser.setUserName("肖利亚1234");
		systemUser.setPassWord("52013一世好");
		mapBeans.put("systemUser", systemUser);
		String s = "";
		try {
			s = transformer.transformDOCX(srcFileName, mapBeans, destFileName);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 到处下载流到页面
		/*File file=new File(destFileName);
		FileInputStream fis=new FileInputStream(file);
		file.length();
		byte[] bytes=new byte[(int)file.length()];
		fis.read(bytes,0,bytes.length);*/
		response.setContentType("application/msword");
		response.setHeader("Content-disposition", "attachment;filename=export.doc");
		ServletOutputStream out = response.getOutputStream();
		out.write(s.getBytes("UTF-8"));
		//out.write(bytes);
	}
}