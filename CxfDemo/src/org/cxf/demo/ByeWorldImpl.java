package org.cxf.demo;

import javax.jws.WebService;

@WebService(endpointInterface="org.cxf.demo.ByeWorld")
public class ByeWorldImpl implements ByeWorld {

	public String sayBye(String name) {
		// TODO Auto-generated method stub
		System.out.println("bye"+name);
		return "bye"+name;
	}

}
