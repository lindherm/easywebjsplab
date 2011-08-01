package org.easyweb.helloworld.action;

import java.util.List;

import org.easyweb.helloworld.manager.HelloWorldManager;
import org.easyweb.helloworld.model.HelloWorld;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class HelloWorldAction{
	HelloWorld helloWorld;
	HelloWorldManager helloWorldManager;

	public void setHelloWorldManager(HelloWorldManager helloWorldManager) {
		this.helloWorldManager = helloWorldManager;
	}

	public ModelAndView action(){
		// TODO Auto-generated method stub
		ModelAndView mv=new ModelAndView("/hello.jsp");
		List<HelloWorld> list=helloWorldManager.getList();
		mv.addObject("name",list);
		return mv;
	}
	public ModelAndView save(){
		// TODO Auto-generated method stub
		ModelAndView mv=new ModelAndView(new RedirectView("helloWorldAction.box"));
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
