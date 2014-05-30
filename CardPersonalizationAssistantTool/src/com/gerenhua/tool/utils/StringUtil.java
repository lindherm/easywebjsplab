package com.gerenhua.tool.utils;

import java.util.regex.Pattern;

public class StringUtil {
	private static String[] IllegalChars = new String[]{"\\","/",":","*","?","\"","<",">","|"};
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
	    Pattern pattern = Pattern.compile("[0-9]*");
	    return pattern.matcher(str).matches();   
	 } 
	public static boolean isNumericOrLetter(String str){
	    Pattern pattern = Pattern.compile("[0-9a-zA-Z]*");
	    return pattern.matcher(str).matches();   
	 } 
	public static boolean containsIllegalChar(String str){
		for (String illegalChar : IllegalChars) {
			if(str.indexOf(illegalChar)!=-1) return true;
			
		}
	    return false;
	 } 
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(StringUtil.containsIllegalChar("35456465"));
	}
}
