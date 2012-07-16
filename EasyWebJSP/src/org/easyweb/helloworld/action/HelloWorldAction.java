package org.easyweb.helloworld.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.easyweb.helloworld.manager.HelloWorldManager;
import org.easyweb.helloworld.model.HelloWorld;
import org.easyweb.helloworld.vo.OutExcelBean;
import org.easyweb.helloworld.vo.TreeNode;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class HelloWorldAction {
	HelloWorld helloWorld;
	HelloWorldManager helloWorldManager;
	String pid;
	HttpServletRequest request;
	HttpServletResponse response;

	public void setHttpRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setHttpResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public void setHelloWorldManager(HelloWorldManager helloWorldManager) {
		this.helloWorldManager = helloWorldManager;
	}

	public ModelAndView action() {
		// TODO Auto-generated method stub
		ModelAndView mv = new ModelAndView("/examples/jstlAction.jsp");
		mv.addObject("name", helloWorldManager.getList("0"));
		return mv;
	}

	public ModelAndView getJson() {
		// TODO Auto-generated method stub
		ModelAndView mv = new ModelAndView("/success.jsp");
		List<HelloWorld> list = new ArrayList<HelloWorld>();
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		if (StringUtils.hasText(pid)) {
			list = helloWorldManager.getList(pid);
		} else {
			list = helloWorldManager.getList("0");
		}
		for (HelloWorld helloWorld : list) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(helloWorld.getId());
			treeNode.setText(helloWorld.getMenuName());
			treeNode.setQtip(helloWorld.getMenuName());
			treeNode.setChecked(false);
			List<HelloWorld> list2 = helloWorldManager.getList(helloWorld.getId());
			if (!CollectionUtils.isEmpty(list2) && list2.size() > 0) {
				treeNode.setLeaf(false);
			} else {
				treeNode.setLeaf(true);
			}
			// treeNode.setExpanded(true);可用来做操作点记录
			treeNodeList.add(treeNode);
		}
		JSONArray jsonArray = JSONArray.fromObject(treeNodeList);
		mv.addObject("msg", jsonArray);
		return mv;
	}

	public ModelAndView save() {
		// TODO Auto-generated method stub
		ModelAndView mv = new ModelAndView(new RedirectView("helloWorldAction.box"));
		helloWorldManager.save(helloWorld);
		return mv;
	}

	public ModelAndView outWord() {
		// TODO Auto-generated method stub
		ModelAndView mv = new ModelAndView("/test.jsp");
		mv.addObject("name", "这是一个测试程序！成功了！");
		return mv;
	}

	private final static String TEMPLATE_PATH = "/WEB-INF/template/";

	public ModelAndView outExcel() throws IOException {
		String templateFileName = request.getSession().getServletContext().getRealPath(TEMPLATE_PATH + "fixedsizelist.xls");
		String destFileName = request.getSession().getServletContext().getRealPath(TEMPLATE_PATH + "destfixedsizelist.xls");
		InputStream is=new FileInputStream(new File(templateFileName));
		List<OutExcelBean> outExcelBeanList=new ArrayList<OutExcelBean>();
		
		List<HelloWorld> helloWorlds = new ArrayList<HelloWorld>();
		HelloWorld helloWorld=new HelloWorld();
		helloWorld.setId("1");
		helloWorld.setMenuName("你好！");
		helloWorld.setParentid("234");
		helloWorlds.add(helloWorld);
		HelloWorld helloWorld1=new HelloWorld();
		helloWorld1.setId("2");
		helloWorld1.setMenuName("你！");
		helloWorld1.setParentid("789");
		helloWorlds.add(helloWorld1);
		List<HelloWorld> hList = new ArrayList<HelloWorld>();
		HelloWorld helloWorld2=new HelloWorld();
		helloWorld.setId("1");
		helloWorld.setMenuName("你好！");
		helloWorld.setParentid("234");
		hList.add(helloWorld);
		HelloWorld helloWorld3=new HelloWorld();
		helloWorld1.setId("2");
		helloWorld1.setMenuName("你！");
		helloWorld1.setParentid("789");
		hList.add(helloWorld1);
		
		
		OutExcelBean outExcelBean=new OutExcelBean();
		outExcelBean.setList(helloWorlds);
		outExcelBeanList.add(outExcelBean);
		
		OutExcelBean outExcelBean1=new OutExcelBean();
		outExcelBean1.setList(hList);
		outExcelBeanList.add(outExcelBean1);
		
		Map beans = new HashMap();
		beans.put("outExcelBeanList", outExcelBeanList);
		
		XLSTransformer transformer = new XLSTransformer();
		transformer.markAsFixedSizeCollection("outExcelBean.list");
		Workbook workbook = null;
		try {
			workbook=transformer.transformXLS(is, beans);
		} catch (ParsePropertyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition","attachment;filename=test.xls"); 
        OutputStream os=response.getOutputStream();
        workbook.write(os);
		return null;
	}

	public HelloWorld getHelloWorld() {
		return helloWorld;
	}

	public void setHelloWorld(HelloWorld helloWorld) {
		this.helloWorld = helloWorld;
	}

	public HelloWorldManager getHelloWorldManager() {
		return helloWorldManager;
	}
}