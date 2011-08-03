package org.easyweb.jabsorb.action;

import org.springframework.web.servlet.ModelAndView;

public class JabSorbAction {
	public ModelAndView action(){
		ModelAndView mv=new ModelAndView("/examples/jabsorbAction.jsp");
		return mv;
	}
}
