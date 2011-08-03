package org.easyweb.helloworld.dao;

import java.util.List;

import org.easyweb.helloworld.model.HelloWorld;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HelloWorldDao extends HibernateDaoSupport {
	public void save(HelloWorld helloWorld) {
		this.getHibernateTemplate().save(helloWorld);
	}

	public List<HelloWorld> getList() {
		List<HelloWorld> list = this.getHibernateTemplate().find("from HelloWorld");
		return list;
	}
}