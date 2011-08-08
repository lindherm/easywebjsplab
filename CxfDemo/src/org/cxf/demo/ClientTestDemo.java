package org.cxf.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientTestDemo {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		HelloWorld helloWorld = (HelloWorld) context.getBean("helloClient");
		ByeWorld byeWorld = (ByeWorld) context.getBean("byeClient");
		System.out.println(helloWorld.sayHi("肖利亚"));
		System.out.println(byeWorld.sayBye("肖利亚"));
	}
}
