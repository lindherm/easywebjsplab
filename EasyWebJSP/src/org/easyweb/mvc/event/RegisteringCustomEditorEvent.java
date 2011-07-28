package org.easyweb.mvc.event;

import java.beans.PropertyEditor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easyweb.mvc.BoxServletRequestDataBinder;

import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.DataBinder;

public class RegisteringCustomEditorEvent extends AbstractBoxEvent {
	public RegisteringCustomEditorEvent(Object source, ApplicationContext applicationContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object action) {
		super(source, applicationContext, httpServletRequest, httpServletResponse, action);
	}

	public RegisteringCustomEditorEvent(Object source, ApplicationContext applicationContext, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object action, BoxServletRequestDataBinder binder) {
		super(source, applicationContext, httpServletRequest, httpServletResponse, action);
		this.binder = binder;
	}

	private BoxServletRequestDataBinder binder;

	public void registerEditor(Class requiredType, PropertyEditor propertyEditor) {
		binder.registerCustomEditor(requiredType, propertyEditor);
	}

	public void registerEditor(Class requiredType, PropertiesEditor propertyEditor, String fieldPath) {
		binder.registerCustomEditor(requiredType, fieldPath, propertyEditor);
	}

	public DataBinder getBinder() {
		return binder;
	}
}
