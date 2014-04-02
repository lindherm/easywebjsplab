package com.javacard.cap.component;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import com.javacard.cap.Cap;
import com.javacard.cap.Formatter;
import com.watchdata.commons.lang.WDAssert;

public class DirectoryComponent extends Formatter{
	@Override
	public String format(String componentInfo) throws IOException {
		String headerFormat=read("directorycomponent");
		
		if (WDAssert.isNotEmpty(headerFormat)) {
			return padding(headerFormat, componentInfo);
		}
	
		return null;
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
					if (lineStr.indexOf("array_init_size")>0) {
						String buffer1=sb.toString();
						int po=buffer1.indexOf("array_init_count");
						if(po>0){
							buffer1=buffer1.substring(po+17, po+21);
							if (buffer1.equalsIgnoreCase("0x00")) {
								
							}else {
								lineStr = lineStr + ":" + readU2(hexReader) + lineSep;
							}
						}
					}else {
						lineStr = lineStr + ":" + readU2(hexReader) + lineSep;
					}
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
	
	public static void main(String[] args) throws IOException {
		Map<String, String> map=Cap.readCap("pse.cap");
		String a=new DirectoryComponent().format(map.get("Directory.cap"));
		System.out.println(a);
	}
}
