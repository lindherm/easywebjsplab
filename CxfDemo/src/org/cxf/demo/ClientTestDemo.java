package org.cxf.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientTestDemo {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		HelloWorld helloWorld = (HelloWorld) context.getBean("helloClient");
		System.out.println(helloWorld.sayHi("肖利亚"));
	}
}
