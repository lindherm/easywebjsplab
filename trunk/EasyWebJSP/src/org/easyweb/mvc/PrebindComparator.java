package org.easyweb.mvc;

import java.util.Comparator;

/**
 * MVC框架控制器-预绑定排序器
 * 
 * @author TeedyWang
 * 
 */
public class PrebindComparator implements Comparator<String> {

	public int compare(String str1, String str2) {
		Integer cont1 = str1.split("\\.").length;
		Integer cont2 = str2.split("\\.").length;
		return cont1.compareTo(cont2);
	}

}
