package org.easyweb.helloworld.action;

import net.sf.json.JSONArray;

import org.easyweb.helloworld.manager.HelloWorldManager;
import org.easyweb.helloworld.model.HelloWorld;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class HelloWorldAction {
	HelloWorld helloWorld;
	HelloWorldManager helloWorldManager;

	public void setHelloWorldManager(HelloWorldManager helloWorldManager) {
		this.helloWorldManager = helloWorldManager;
	}

	public ModelAndView action() {
		// TODO Auto-generated method stub
		ModelAndView mv = new ModelAndView("/examples/jstlAction.jsp");
		mv.addObject("name", helloWorldManager.getList());
		return mv;
	}

	public ModelAndView getJson() {
		// TODO Auto-generated method stub
		ModelAndView mv = new ModelAndView("/success.jsp");
		JSONArray jsonArray = JSONArray.fromObject(helloWorldManager.getList());
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
