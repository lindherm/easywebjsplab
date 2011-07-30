package org.easyweb.helloworld.dao;

import java.util.List;
import java.util.Random;

import org.easyweb.helloworld.model.HelloWorld;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HelloWorldDao extends HibernateDaoSupport {
	public void save(String name) {
		HelloWorld helloWorld = new HelloWorld();
		Random random = new Random();
		helloWorld.setId(String.valueOf(random.nextInt()));
		helloWorld.setTitle(name);
		this.getHibernateTemplate().save(helloWorld);
	}
	public List<HelloWorld> getList(){
		List<HelloWorld> list=getHibernateTemplate().find("from HelloWorld");
		return list;
	}
}
