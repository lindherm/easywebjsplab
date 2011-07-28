package org.easyweb.mvc.event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

public class LoadingActionEvent extends AbstractBoxEvent {

	ModelAndView modelAndView;
	String actionName;
	String methodName;

	public LoadingActionEvent(Object source, ApplicationContext applicationContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object action, ModelAndView modelAndView, String actionName, String methodName) {
		super(source, applicationContext, httpServletRequest, httpServletResponse, action);
		this.modelAndView = modelAndView;
		this.actionName = actionName;
		this.methodName = methodName;
	}

	public ModelAndView getModelAndView() {
		return modelAndView;
	}

	public String getActionName() {
		return actionName;
	}

	public String getMethodName() {
		return methodName;
	}
}
