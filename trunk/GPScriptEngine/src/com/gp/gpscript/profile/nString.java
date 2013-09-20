package com.gp.gpscript.profile;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Vector;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class nString extends Dictionary {
	private String name = null;
	private Vector keys = new Vector();
	private Vector values = new Vector();

	public nString(String n) {
		name = n;
	}

	public String toString() {
		return name;
	}

	public int size() {
		return keys.size();
	}

	public boolean isEmpty() {
		return keys.isEmpty();
	}

	public Object put(Object key, Object value) {
		keys.addElement(key);
		values.addElement(value);
		return key;
	}

	public Object get(Object key) {
		int index = keys.indexOf(key);
		if (index == -1)
			return null;
		return values.elementAt(index);
	}

	public Object remove(Object key) {
		int index = keys.indexOf(key);
		if (index == -1)
			return null;
		keys.removeElementAt(index);
		Object returnval = values.elementAt(index);
		values.remove(index);
		return returnval;
	}

	public Enumeration keys() {
		return keys.elements();
	}

	public Enumeration elements() {
		return values.elements();
	}
}