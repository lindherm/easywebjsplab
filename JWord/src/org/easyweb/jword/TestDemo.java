package org.easyweb.jword;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easyweb.jword.transformer.WordTransformer;

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String fileName = "testtransformer.rtf";
		File file = new File(request.getSession().getServletContext().getRealPath(FILE_PATH + "/" + fileName));
		WordTransformer transformer = new WordTransformer();
		Map mapBeans = new HashMap();
		HelloWorld helloWorld = new HelloWorld();
		helloWorld.setName("xiaoliya");
		helloWorld.setPass("5201314");
		mapBeans.put("helloWorld", helloWorld);
		String str = "";
		try {
			str = transformer.transformWORD(file, mapBeans);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType("application/msword");
		response.setHeader("Content-disposition", "attachment;" + "filename=word.word");
		ServletOutputStream out = response.getOutputStream();
		OutputStreamWriter ow = new OutputStreamWriter(out);
		ow.write(str);
		ow.close();
	}
}