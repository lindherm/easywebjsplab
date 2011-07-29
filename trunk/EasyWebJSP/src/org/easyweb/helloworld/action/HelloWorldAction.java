package org.easyweb.helloworld.action;

import org.easyweb.helloworld.manager.HelloWorldManager;
import org.springframework.web.servlet.ModelAndView;

public class HelloWorldAction{
	HelloWorldManager helloWorldManager;

	public HelloWorldManager getHelloWorldManager() {
		return helloWorldManager;
	}

	public void setHelloWorldManager(HelloWorldManager helloWorldManager) {
		this.helloWorldManager = helloWorldManager;
	}

	public ModelAndView action(){
		// TODO Auto-generated method stub
		ModelAndView mv=new ModelAndView("/hello.jsp");
		helloWorldManager.save();
		mv.addObject("name", "success ,");
		return mv;
	}
	
	public ModelAndView add(){
		ModelAndView mv=new ModelAndView("/hello.jsp");
		mv.addObject("name","madam");
		return mv;
	}
}
