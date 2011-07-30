package org.easyweb.helloworld.manager;

import java.util.List;

import org.easyweb.helloworld.dao.HelloWorldDao;
import org.easyweb.helloworld.model.HelloWorld;

public class HelloWorldManager {
	HelloWorldDao helloWorldDao;

	public void save(String name) {
		helloWorldDao.save(name);
	}
	public List<HelloWorld> getList() {
		return helloWorldDao.getList();
	}

	public HelloWorldDao getHelloWorldDao() {
		return helloWorldDao;
	}

	public void setHelloWorldDao(HelloWorldDao helloWorldDao) {
		this.helloWorldDao = helloWorldDao;
	}
}
