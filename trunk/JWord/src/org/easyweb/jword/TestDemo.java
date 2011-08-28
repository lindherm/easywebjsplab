package org.easyweb.jword;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.easyweb.jword.transformer.WordTransformer;
import org.xml.sax.SAXException;

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

		// TODO Auto-generated method stub String
		String fileName = "c:/document.docx";
		WordTransformer transformer = new WordTransformer();
		Map mapBeans = new HashMap();
		HelloWorld helloWorld = new HelloWorld();
		helloWorld.setName("测试");
		helloWorld.setPass("测试englishi");
		mapBeans.put("helloWorld", helloWorld);

		SystemUser systemUser=new SystemUser();
		systemUser.setUserName("肖利亚");
		systemUser.setPassWord("52013一世");
		mapBeans.put("systemUser", systemUser);
		
		try {
			transformer.transformWORD(fileName, mapBeans);
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
	/*	
		// 到处下载流到页面
		response.setContentType("application/msword");
		response.setHeader("Content-disposition", "attachment;" + "filename=word.doc");
		ServletOutputStream out = response.getOutputStream();*/
	}
}