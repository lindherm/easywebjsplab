package org.easyweb.index;

import org.springframework.web.servlet.ModelAndView;

public class IndexAction {
	public ModelAndView action() {
		ModelAndView mv = new ModelAndView("/index/index.jsp");
		mv.addObject("name", "god");
		return mv;
	}
}
