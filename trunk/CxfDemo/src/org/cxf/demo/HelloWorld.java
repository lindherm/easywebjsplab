package org.cxf.demo;

import javax.jws.WebService;

@WebService
public interface HelloWorld {
	public String sayHi(String name);
}
