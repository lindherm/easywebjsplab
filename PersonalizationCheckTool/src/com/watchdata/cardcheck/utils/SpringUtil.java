package com.watchdata.cardcheck.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * spring框架帮助类
 * @author liya.xiao
 *
 */
public final class SpringUtil {

	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	/**
	 * 获得bean实例的功用方法
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
		return ctx.getBean(beanName);
	}
}
