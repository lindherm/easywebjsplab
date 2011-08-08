package org.cxf.demo;

import javax.jws.WebService;

@WebService
public interface ByeWorld {
	public String sayBye(String name);
}
