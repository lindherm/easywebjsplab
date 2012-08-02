package com.echeloneditor.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
	private Properties propertie;
	private FileInputStream inputFile;
	private FileOutputStream outputFile;
	public static String configPath = System.getProperty("user.dir") + "\\resources\\";

	public Config() {
		propertie = new Properties();
		try {
			inputFile = new FileInputStream(configPath+"laf_config.properties");
			propertie.load(inputFile);
			inputFile.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public Config(String fileName) {
		propertie = new Properties();
		try {
			inputFile = new FileInputStream(configPath+fileName);
			propertie.load(inputFile);
			inputFile.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public String getValue(String key) {
		if (propertie.containsKey(key)) {
			String value = propertie.getProperty(key);// 得到某一属性的值
			return value;
		} else
			return "";
	}

	public String getValue(String key, String defValue) {
		if (propertie.containsKey(key)) {
			return propertie.getProperty(key);
		} else {
			return defValue;
		}
	}

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

	public void clear() {
		propertie.clear();
	}

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
	}

}
