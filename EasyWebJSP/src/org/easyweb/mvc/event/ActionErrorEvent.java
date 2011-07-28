package org.easyweb.mvc.event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

public class ActionErrorEvent extends AbstractBoxEvent {
	Throwable exception;

	ModelAndView modelAndView;

	public ActionErrorEvent(Object source, ApplicationContext applicationContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object action, ModelAndView modelAndView, Throwable exception) {
		super(source, applicationContext, httpServletRequest, httpServletResponse, action);
		this.exception = exception;
		this.modelAndView = modelAndView;
	}

	public Throwable getException() {
		return exception;
	}

	public ModelAndView getModelAndView() {
		return modelAndView;
	}

}
