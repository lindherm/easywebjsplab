package org.easyweb.mvc.event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

public class AbstractBoxEvent extends ApplicationEvent {
	private ApplicationContext applicationContext;

	private HttpServletRequest httpServletRequest;

	private HttpServletResponse httpServletResponse;

	private Object action;

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public HttpServletRequest getRequest() {
		return httpServletRequest;
	}

	public HttpServletResponse getResponse() {
		return httpServletResponse;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
		this.httpServletResponse = httpServletResponse;
	}

	public Object getAction() {
		return action;
	}

	public void setAction(Object action) {
		this.action = action;
	}

	public AbstractBoxEvent(Object source, ApplicationContext applicationContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object action) {
		super(source);
		this.applicationContext = applicationContext;
		this.httpServletRequest = httpServletRequest;
		this.httpServletResponse = httpServletResponse;
		this.action = action;
	}
}
