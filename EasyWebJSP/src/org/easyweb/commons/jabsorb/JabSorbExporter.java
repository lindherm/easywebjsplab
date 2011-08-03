package org.easyweb.commons.jabsorb;

import org.jabsorb.JSONRPCBridge;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;

public class JabSorbExporter implements InitializingBean, BeanNameAware {
	private Object serviceBean;
	private Object serviceName;
	public JabSorbExporter() {
		
	}

	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		JSONRPCBridge.getGlobalBridge().registerObject(serviceName, serviceBean);
	}

	public void setBeanName(String name) {
		// TODO Auto-generated method stub
		this.serviceName=name;
	}

	public Object getServiceBean() {
		return serviceBean;
	}

	public void setServiceBean(Object serviceBean) {
		this.serviceBean = serviceBean;
	}

}
