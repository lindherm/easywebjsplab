package com.javacard.cap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Formatter {
	public static String lineSep = System.getProperty("line.separator");

	public abstract String format(String componentInfo) throws IOException;

	public static String read(String fFileName) throws IOException {
		String filePath = "/com/javacard/formatter/";
		filePath += fFileName;
		filePath += ".format";
		InputStream is = Formatter.class.getResourceAsStream(filePath);
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		StringBuilder sb = new StringBuilder();
		String str = "";
		while ((str = br.readLine()) != null) {
			sb.append(str).append(lineSep);
		}

		return sb.toString();
	}

	public static String padding(String formatter, String hex) throws IOException {
		StringReader hexReader = new StringReader(hex);

		StringBuilder sb = new StringBuilder();
		String[] line = formatter.split(lineSep);

		for (String lineStr : line) {
			int u1pos = lineStr.indexOf("u1");
			int u2pos = lineStr.indexOf("u2");
			int u4pos = lineStr.indexOf("u4");
			if (u1pos > 0) {
				int start = lineStr.indexOf("[");
				int end = lineStr.indexOf("]");
				if (start > 0 && end > 0) {
					String key = lineStr.substring(start + 1, end);
					if (isNumeric(key)) {
						// int arrayCount = getArrayCount(key, sb.toString());
						lineStr = lineStr + ":" + readU1Array(hexReader, Integer.parseInt(key, 16)) + lineSep;
					} else {
						int arrayCount = getArrayCount(key, sb.toString());
						lineStr = lineStr + ":" + readU1Array(hexReader, arrayCount) + lineSep;
					}

				} else {
					lineStr = lineStr + ":" + readU1(hexReader) + lineSep;
				}
			} else if (u2pos > 0) {
				int start = lineStr.indexOf("[");
				int end = lineStr.indexOf("]");
				if (start > 0 && end > 0) {
					String key = lineStr.substring(start + 1, end);

					if (isNumeric(key)) {
						// int arrayCount = getArrayCount(key, sb.toString());
						lineStr = lineStr + ":" + readU2Array(hexReader, Integer.parseInt(key, 16)) + lineSep;
					} else {
						int arrayCount = getArrayCount(key, sb.toString());
						lineStr = lineStr + ":" + readU2Array(hexReader, arrayCount) + lineSep;
					}
				} else {
					lineStr = lineStr + ":" + readU2(hexReader) + lineSep;
				}
			} else if (u4pos > 0) {
				int start = lineStr.indexOf("[");
				int end = lineStr.indexOf("]");
				if (start > 0 && end > 0) {
					String key = lineStr.substring(start + 1, end);

					if (isNumeric(key)) {
						// int arrayCount = getArrayCount(key, sb.toString());
						lineStr = lineStr + ":" + readU4Array(hexReader, Integer.parseInt(key, 16)) + lineSep;
					} else {
						int arrayCount = getArrayCount(key, sb.toString());
						lineStr = lineStr + ":" + readU4Array(hexReader, arrayCount) + lineSep;
					}

				} else {
					lineStr = lineStr + ":" + readU4(hexReader) + lineSep;
				}
			}
			sb.append(lineStr + lineSep);
		}

		return sb.toString();
	}

	public static String readU1(StringReader hexReader) throws IOException {
		char[] u1 = new char[2];
		hexReader.read(u1);
		return toHexStyle(String.valueOf(u1));
	}

	public static String readU2(StringReader hexReader) throws IOException {
		char[] u2 = new char[4];
		hexReader.read(u2);
		return toHexStyle(String.valueOf(u2));
	}

	public static String readU4(StringReader hexReader) throws IOException {
		char[] u4 = new char[8];
		hexReader.read(u4);
		return toHexStyle(String.valueOf(u4));
	}

	public static String readU1Array(StringReader hexReader, int num) throws IOException {
		char[] u1 = new char[2 * num];
		hexReader.read(u1);
		return toHexStyle(String.valueOf(u1));
	}

	public static String readU2Array(StringReader hexReader, int num) throws IOException {
		char[] u2 = new char[4 * num];
		hexReader.read(u2);
		return toHexStyle(String.valueOf(u2));
	}

	public static String readU4Array(StringReader hexReader, int num) throws IOException {
		char[] u4 = new char[8 * num];
		hexReader.read(u4);
		return toHexStyle(String.valueOf(u4));
	}

	public static String toHexStyle(String hex) {
		StringBuilder sb = new StringBuilder();
		hex = hex.toUpperCase();
		if (hex.length() % 2 != 0) {
			return null;
		}
		int pos = 0;
		while (pos < hex.length()) {
			sb.append("0x" + hex.substring(pos, pos + 2) + " ");
			pos += 2;
		}
		return sb.toString();
	}

	public static int getArrayCount(String key, String buffer) {
		int pos = buffer.indexOf(key);
		pos += key.length();
		if (pos > 0) {
			String hex = buffer.substring(pos + 1, buffer.indexOf(lineSep, pos + 1));
			hex = hex.replaceAll("0x", "");
			int count = Integer.parseInt(hex.trim(), 16);
			return count;
		}
		return -1;
	}
	
	 public static boolean isNumeric(String str)
     {
           Pattern pattern = Pattern.compile("[0-9]*");
           Matcher isNum = pattern.matcher(str);
           if(!isNum.matches() )
           {
                 return false;
           }
           return true;
     }



	public static void main(String[] args) throws IOException {
		System.out.println(Formatter.read("headercomponent"));
		System.out.println(Formatter.toHexStyle("0021"));
		System.out.println("2".matches("/d"));
	}
}
