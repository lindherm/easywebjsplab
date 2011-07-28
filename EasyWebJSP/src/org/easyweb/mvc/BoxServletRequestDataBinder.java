package org.easyweb.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.web.bind.ServletRequestDataBinder;

public class BoxServletRequestDataBinder extends ServletRequestDataBinder {

	/**
	 * Create a new ServletRequestDataBinder instance, with default object name.
	 * 
	 * @param target
	 *            target object to bind onto
	 * @see #DEFAULT_OBJECT_NAME
	 */
	public BoxServletRequestDataBinder(Object target) {
		super(target);
	}

	/**
	 * Create a new ServletRequestDataBinder instance.
	 * 
	 * @param target
	 *            target object to bind onto
	 * @param objectName
	 *            objectName of the target object
	 */
	public BoxServletRequestDataBinder(Object target, String objectName) {
		super(target, objectName);
	}

	/**
	 * 注入依赖
	 * 
	 * @param request
	 */
	public void bindAdditionalData(HttpServletRequest request, HttpServletResponse response, ActionMessage actionMessage) {
		MutablePropertyValues mpvs = new MutablePropertyValues();
		mpvs.addPropertyValue("httpRequest", request);
		mpvs.addPropertyValue("httpResponse", response);
		mpvs.addPropertyValue("actionMessage", actionMessage);
		doBind(mpvs);
	}
}
