package org.easyweb.helloworld.dao;

import org.easyweb.helloworld.model.HelloWorld;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HelloWorldDao extends HibernateDaoSupport {
	public void save() {
		HelloWorld helloWorld=new HelloWorld();
		helloWorld.setId("12345679646131");
		helloWorld.setTitle("测试");
		this.getHibernateTemplate().save(helloWorld);
	}
}
