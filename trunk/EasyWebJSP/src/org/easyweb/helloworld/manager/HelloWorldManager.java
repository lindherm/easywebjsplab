package org.easyweb.helloworld.manager;

import org.easyweb.helloworld.dao.HelloWorldDao;

public class HelloWorldManager {
	HelloWorldDao helloWorldDao;
public void save(){
	helloWorldDao.save();
}
public HelloWorldDao getHelloWorldDao() {
	return helloWorldDao;
}
public void setHelloWorldDao(HelloWorldDao helloWorldDao) {
	this.helloWorldDao = helloWorldDao;
}
}
