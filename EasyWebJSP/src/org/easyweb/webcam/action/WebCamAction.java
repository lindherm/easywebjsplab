package org.easyweb.webcam.action;

import org.springframework.web.servlet.ModelAndView;

public class WebCamAction {
	public ModelAndView action(){
		ModelAndView mv=new ModelAndView("/examples/webCamAction.jsp");
		mv.addObject("name", "rest");
		return mv;
	}
}
