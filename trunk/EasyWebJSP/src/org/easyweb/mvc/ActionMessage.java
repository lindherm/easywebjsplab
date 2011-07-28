package org.easyweb.mvc;

import java.util.HashMap;
import java.util.Map;

public class ActionMessage {
	private Map messageMap = new HashMap();

	public Map getMessageMap() {
		return messageMap;
	}

	public void setMessageMap(Map messageMap) {
		this.messageMap = messageMap;
	}

	public void addMessage(String key, String message) {
		messageMap.put(key, message);
	}
}
