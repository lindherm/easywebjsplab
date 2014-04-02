package com.javacard.cap.component;

import java.io.IOException;
import java.util.Map;

import com.javacard.cap.Cap;
import com.javacard.cap.Formatter;
import com.watchdata.commons.lang.WDAssert;

public class HeaderComponent extends Formatter {
	@Override
	public String format(String componentInfo) throws IOException {
		String headerFormat=read("headercomponent");
		
		if (WDAssert.isNotEmpty(headerFormat)) {
			return padding(headerFormat, componentInfo);
		}
	
		return null;
	}
	public static void main(String[] args) throws IOException {
		Map<String, String> map=Cap.readCap("pse.cap");
		String a=new HeaderComponent().format(map.get("Header.cap"));
		System.out.println(a);
	}
}
