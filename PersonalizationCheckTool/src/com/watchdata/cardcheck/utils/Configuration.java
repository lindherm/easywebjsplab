/**
 * 
 * <p>Title:读取资源文件</p>
 * <p>Description:读取资源文件类</p>
 * <p>Copyright: Beijing Watchdata Copyright (c)2010</p>
 * <p>Company: Beijing Watchdata CO,.Ltd</p>
 * @author baizhichao
 * @version 1.0
 */
package com.watchdata.cardcheck.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Configuration {
	private Properties propertie;
	private FileInputStream inputFile;
	private FileOutputStream outputFile;
	public static String configPath = System.getProperty("user.dir")
			+ "\\resource\\config.properties";

	/**
	 * 初始化Configuration类
	 */
	public Configuration() {
		propertie = new Properties();
		try {
			inputFile = new FileInputStream(configPath);
			propertie.load(inputFile);
			inputFile.close();
		} catch (FileNotFoundException ex) {
			// System.out.println("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在");
			ex.printStackTrace();
		} catch (IOException ex) {
			// System.out.println("装载文件--->失败!");
			ex.printStackTrace();
		}

	}

	/**
	 * 初始化Configuration类
	 * 
	 * @param inputFile
	 *            文件流
	 */
	public Configuration(InputStream inputFile) {
		propertie = new Properties();
		try {
			inputFile = inputFile;
			propertie.load(inputFile);
			inputFile.close();
		} catch (FileNotFoundException ex) {
			System.out.println("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("装载文件--->失败!");
			ex.printStackTrace();
		}
	}

	/**
	 * 初始化Configuration类
	 * 
	 * @param filePath
	 *            要读取的配置文件的路径+名称
	 */
	public Configuration(String filePath) {
		propertie = new Properties();
		try {
			inputFile = new FileInputStream(filePath);
			propertie.load(inputFile);
			inputFile.close();
		} catch (FileNotFoundException ex) {
			System.out.println("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("装载文件--->失败!");
			ex.printStackTrace();
		}
	}// end ReadConfigInfo(...)

	/**
	 * 重载函数，得到key的值
	 * 
	 * @param key
	 *            取得其值的键
	 * @return key的值
	 */
	public String getValue(String key) {
		if (propertie.containsKey(key)) {
			String value = propertie.getProperty(key);// 得到某一属性的值
			return value;
		} else
			return "";
	}// end getValue(...)

	/**
	 * 重载函数，得到key的值
	 * 
	 * @param key
	 *            取得其值的键
	 * @param defValue
	 *            默认值
	 * @return key的值
	 */
	public String getValue(String key, String defValue) {
		if (propertie.containsKey(key)) {
			return propertie.getProperty(key);
		} else {
			return defValue;
		}
	}

	/**
	 * 重载函数，得到key的值
	 * 
	 * @param key
	 *            取得其值的键
	 * @param defValue
	 *            默认值
	 * @return key的值
	 */
	public int getIntValue(String key, int defValue) {
		if (propertie.containsKey(key)) {
			int ret = 5;
			try {
				ret = Integer.parseInt(propertie.getProperty(key));
			} catch (Exception e) {
				ret = defValue;
			}
			return ret;
		} else {
			return defValue;
		}
	}

	/**
	 * 清除properties文件中所有的key和其值
	 */
	public void clear() {
		propertie.clear();
	}// end clear();

	/**
	 * 改变或添加一个key的值，当key存在于properties文件中时该key的值被value所代替， 当key不存在时，该key的值是value
	 * 
	 * @param key
	 *            要存入的键
	 * @param value
	 *            要存入的值
	 */
	public boolean setValue(String key, String value) {
		propertie.setProperty(key, value);

		try {
			outputFile = new FileOutputStream(configPath);
			propertie.store(outputFile, null);
			outputFile.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 将更改后的文件数据存入指定的文件中，该文件可以事先不存在。
	 * 
	 * @param fileName
	 *            文件路径+文件名称
	 * @param description
	 *            对该文件的描述
	 */
	public void saveFile(String fileName, String description) {
		try {
			outputFile = new FileOutputStream(fileName);
			propertie.store(outputFile, description);
			outputFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}// end saveFile(...)

	public static void main(String[] args) {
		

	}

}
