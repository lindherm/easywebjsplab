package org.easyweb.jsp.jsonrpc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestJsonRpc {
	public String getMessage(String name){
		return "Hello,"+name+"!";
	}
	public List<String> getList(){
		List<String> list=new ArrayList<String>();
		list.add("xiaoliya");
		list.add("duwenbin");
		list.add("yaoming");
		list.add("keji");
		list.add("jiaotongren");
		return list;
	}
	public Map<String, String> getMap(){
		Map<String, String> map=new HashMap<String, String>();
		map.put("bird", "鸟");
		map.put("dog", "狗");
		map.put("pig", "猪");
		map.put("fish", "鱼");
		return map;
	}
}
