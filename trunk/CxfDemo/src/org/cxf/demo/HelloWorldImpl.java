package org.cxf.demo;

import javax.jws.WebService;
@WebService(endpointInterface="org.cxf.demo.HelloWorld")
public class HelloWorldImpl implements HelloWorld {
	public String sayHi(String name) {
		System.out.println("sayHi called");
		return "Hello " + name;
	}

}
