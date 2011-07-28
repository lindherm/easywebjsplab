package org.easyweb;

import org.springframework.web.servlet.ModelAndView;

public class HelloWorldAction{

	public ModelAndView action(){
		// TODO Auto-generated method stub
		ModelAndView mv=new ModelAndView("/hello.jsp");
		mv.addObject("name", "sir ,");
		return mv;
	}
	
	public ModelAndView add(){
		ModelAndView mv=new ModelAndView("/hello.jsp");
		mv.addObject("name","madam");
		return mv;
	}
}
