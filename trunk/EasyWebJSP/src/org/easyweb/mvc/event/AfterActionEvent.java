package org.easyweb.mvc.event;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

public class AfterActionEvent extends BeforeActionEvent {
	private ModelAndView actionResult;

	public AfterActionEvent(Object source, ApplicationContext applicationContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object action, Method method, ModelAndView actionResult) {
		super(source, applicationContext, httpServletRequest, httpServletResponse, action, method);
		this.actionResult = actionResult;
	}

	public ModelAndView getActionResult() {
		return actionResult;
	}
}
