package org.easyweb.helloworld.manager;

import java.util.ArrayList;
import java.util.List;

import org.easyweb.helloworld.dao.HelloWorldDao;
import org.easyweb.helloworld.model.HelloWorld;

public class HelloWorldManager {
	HelloWorldDao helloWorldDao;

	public void save(HelloWorld helloWorld) {
		helloWorldDao.save(helloWorld);
	}

	public List<HelloWorld> getList(String pid) {
		return helloWorldDao.getList(pid);
	}

	public List<String> getListTest(ArrayList<String> list) {
		return list;
	}
	public HelloWorld getHelloWorldTest(HelloWorld helloWorld) {
		return helloWorld;
	}

	public HelloWorldDao getHelloWorldDao() {
		return helloWorldDao;
	}

	public void setHelloWorldDao(HelloWorldDao helloWorldDao) {
		this.helloWorldDao = helloWorldDao;
	}
}
