package org.easyweb.helloworld.action;

import java.util.List;

import org.easyweb.helloworld.manager.HelloWorldManager;
import org.easyweb.helloworld.model.HelloWorld;
import org.springframework.web.servlet.ModelAndView;

public class HelloWorldAction{
	HelloWorldManager helloWorldManager;

	public void setHelloWorldManager(HelloWorldManager helloWorldManager) {
		this.helloWorldManager = helloWorldManager;
	}

	public ModelAndView action(){
		// TODO Auto-generated method stub
		ModelAndView mv=new ModelAndView("/hello.jsp");
		String name="肖利亚";
		List<HelloWorld> list=helloWorldManager.getList();
		mv.addObject("name",list);
		return mv;
	}
	
	public ModelAndView query(){
		ModelAndView mv=new ModelAndView("/hello.jsp");
		
		mv.addObject("name","madam");
		return mv;
	}
}
