package org.easyweb.jsp.jsonrpc;

import java.util.ArrayList;
import java.util.List;

public class TestJsonRpc {
	public String getMessage(String name){
		return "Hello,"+name+"!";
	}
	public List<String> getMessage(){
		List<String> list=new ArrayList<String>();
		list.add("xiaoliya");
		list.add("");
		return list;
	}
}
