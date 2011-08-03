package org.easyweb.jstl.action;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class JSTLAction {
	public ModelAndView action(){
		ModelAndView mv=new ModelAndView(new RedirectView("helloWorldAction.box"));
		return mv;
	}
}
