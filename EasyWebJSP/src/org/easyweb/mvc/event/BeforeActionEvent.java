package org.easyweb.mvc.event;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

public class BeforeActionEvent extends AbstractBoxEvent {
	private Method method;

	public Method getMethod() {
		return method;
	}

	public BeforeActionEvent(Object source, ApplicationContext applicationContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object action, Method method) {
		super(source, applicationContext, httpServletRequest, httpServletResponse, action);
		this.method = method;
	}

}
