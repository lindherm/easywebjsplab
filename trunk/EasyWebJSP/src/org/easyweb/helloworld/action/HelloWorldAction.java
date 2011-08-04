package org.easyweb.helloworld.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.easyweb.helloworld.manager.HelloWorldManager;
import org.easyweb.helloworld.model.HelloWorld;
import org.easyweb.helloworld.vo.TreeNode;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class HelloWorldAction {
	HelloWorld helloWorld;
	HelloWorldManager helloWorldManager;
	String pid;

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
