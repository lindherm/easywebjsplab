package com.gerenhua.tool.utils;

import java.math.BigInteger;
import java.util.Vector;

import org.apache.log4j.Logger;

public class CollectionUtil {
	private static Logger log=Logger.getLogger(CollectionUtil.class);
	/**
	 * 
	 * @param ruleliList
	 * @param index
	 * @param upordown
	 *            0:up 1:down
	 * @return
	 */
	public static Vector<Vector<String>> moveElement(Vector<Vector<String>> vector, int index, int upordown) {
		if (upordown == 0) {
			if (index - 1 >= 0) {
				Vector<String> elementVector = vector.get(index);
				vector.remove(index);
				vector.add(index - 1, elementVector);
			} else {
				log.debug("first element not allowed move up!");
				//System.out.println("first element not allowed move up!");
			}
		} else if (upordown == 1) {
			if (index + 1 <= vector.size()) {
				Vector<String> elementVector = vector.get(index);
				vector.remove(index);
				vector.add(index + 1, elementVector);
			} else {
				log.debug("last element not allowed move down!");
				//System.out.println("last element not allowed move down!");
			}
		}
		return vector;
	}

	/**
	 * 
	 * @param vector
	 * @return
	 */
	private static int getDyNum(Vector<Vector<String>> vector) {
		int num = 0;
		for (int i = 0; i < vector.size(); i++) {
			if (vector.get(i).get(0).equals("动态增长数")) {
				num += 1;
			}
		}
		return num;
	}

	/**
	 * 
	 * @param vector
	 * @return
	 */
	public static int checkData(Vector<Vector<String>> vector) {
		if (vector == null) {
			return -1;
		}

		int dyNum = getDyNum(vector);
		if (dyNum != 1) {
			return -2;
		}
		// "固定值", "动态增长数", "校检位", "随机数"
		for (int i = 0; i < vector.size(); i++) {
			String col0 = vector.get(i).get(0);
			String col1 = vector.get(i).get(1);
			String col2 = vector.get(i).get(2);
			String col3 = vector.get(i).get(3);
			
			BigInteger bigInteger=new BigInteger(col1);
			if (col0.equals("固定值")) {
				if (!StringUtil.isNumeric(col1)) {
					return i + 1;
				}
				if (bigInteger.longValue() > 50 || bigInteger.longValue() <= 0) {
					return i + 1;
				}
				if (col2.length() != Integer.valueOf(col1)) {
					return i + 1;
				}
				if (!StringUtil.isNumericOrLetter(col2)) {
					return i + 1;
				}
				if (!col3.equals("----")) {
					return i + 1;
				}
			} else if (col0.equals("动态增长数")) {
				if (!StringUtil.isNumeric(col1)) {
					return i + 1;
				}
				if (bigInteger.longValue() > 9 || bigInteger.longValue() <= 0) {
					return i + 1;
				}
				if (col3.length() != Integer.valueOf(col1) || Integer.valueOf(col3) <= Integer.valueOf(col2)) {
					return i + 1;
				}
				if (col2.length()>Integer.valueOf(col1)) {
					return i + 1;
				}
				if (!StringUtil.isNumeric(col2)) {
					return i + 1;
				}
				if (!StringUtil.isNumeric(col3)) {
					return i + 1;
				}
			} else if (col0.equals("随机数")) {
				if (!StringUtil.isNumeric(col1)) {
					return i + 1;
				}
				if (bigInteger.longValue() > 9 || bigInteger.longValue() <= 0) {
					return i + 1;
				}
				if (!col2.equals("----") || !col3.equals("----")) {
					return i + 1;
				}
			} else {
				if (!col1.equals("----") || !col2.equals("----") || !col3.equals("----")) {
					return i + 1;
				}
			}
		}
		return 0;
	}

	public static void main(String[] args) {
		/*
		 * List<String> rulesList=new ArrayList<String>(); rulesList.add("1|0|4|N204|N204"); rulesList.add("2|0|4|N204|N204"); rulesList.add("3|0|4|N204|N204"); rulesList.add("4|0|4|N204|N205");
		 * 
		 * rulesList=CollectionUtil.moveElement(rulesList, 2, 1);
		 * 
		 * for (String string : rulesList) { System.out.println(string); }
		 */
		/*
		 * if (CollectionUtil.isMatch("1-9")) { System.out.println("ok"); }else { System.out.println("no"); }
		 */
	}
}
