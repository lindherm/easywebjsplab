/**
 * @Title Debug.java
 * @Package com.test
 * @Description 用一句话描述该文件做什么
 * @author 狂奔的蜗牛 672308444@163.com  
 * @date 2011-9-13 下午11:54:01
 * @version V1.0  
*/
package com.demo.utility;

/**
 * @ClassName Debug
 * @Description 这里用一句话描述这个类的作用
 * @author 狂奔的蜗牛 672308444@163.com  
 * @date 2011-9-13 下午11:54:01
 *
 */
public class Debug{
    public static void print(Object message) {
		System.err.println(message);
	}
    public static void info(Object message){
    	System.out.println(message);
    }
}
