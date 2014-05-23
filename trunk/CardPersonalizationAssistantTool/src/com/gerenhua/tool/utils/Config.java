package com.gerenhua.tool.utils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.dtools.ini.BasicIniFile;
import org.dtools.ini.IniFile;
import org.dtools.ini.IniFileReader;
import org.dtools.ini.IniFileWriter;
import org.dtools.ini.IniItem;
import org.dtools.ini.IniSection;

public class Config {
	// **********************************************************************
	// default config file path
	// **********************************************************************

	public static String configPath = System.getProperty("user.dir") + "\\resources\\config\\";

	public static File configFile = null;
	public static IniFile ini = null;

	static {
		
		configFile = new File(configPath + "/config.ini");

		checkFile(configFile);

		ini = new BasicIniFile();

		// **********************************************************************
		// Create the IniFileReader object.
		// **********************************************************************
		IniFileReader reader = new IniFileReader(ini, configFile);

		try {
			reader.read();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	public static void addSection(String sectionName) {
		ini.addSection(sectionName);
		write();
	}
	public static Collection<IniSection> getSections() {
		return ini.getSections();
	}
	

	public static void setValue(String SectionName, String itemName, String itemValue) {

		if (!ini.hasSection(SectionName)) {
			ini.addSection(SectionName);

		}
		IniSection iniSection = ini.getSection(SectionName);

		if (!iniSection.hasItem(itemName)) {
			iniSection.addItem(itemName);
		}
		String oldValue=iniSection.getItem(itemName).getValue();
		if (!oldValue.equals(itemName)) {
			iniSection.getItem(itemName).setValue(itemValue);
			write();
		}
	}

	public static String getValue(String SectionName, String itemName) {

		if (!ini.hasSection(SectionName)) {
			try {
				throw new IOException("section is not exists.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		IniSection iniSection = ini.getSection(SectionName);

		if (!iniSection.hasItem(itemName)) {
			return null;
		}
		return iniSection.getItem(itemName).getValue();

	}

	public static Collection<String> getItems(String sectionName) {

		if (!ini.hasSection(sectionName)) {
			try {
				throw new IOException("section is not exists.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		IniSection iniSection = ini.getSection(sectionName);
		Collection<String> items = iniSection.getItemNames();

		return items;

	}
	
	public static IniItem getItem(String SectionName, String itemName) {
		if (!ini.hasSection(SectionName)) {
			return null;
		}
		IniSection iniSection = ini.getSection(SectionName);

		if (iniSection.hasItem(itemName)) {
			return iniSection.getItem(itemName);
		}else {
			return null;
		}
	}
	
	public static void addItem(String SectionName, String itemName) {

		if (!ini.hasSection(SectionName)) {
			try {
				throw new IOException("section is not exists.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		IniSection iniSection = ini.getSection(SectionName);

		if (iniSection.hasItem(itemName)) {
			try {
				throw new IOException("item is exists.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		iniSection.addItem(itemName);
		
		write();
	}
	
	public static void delItem(String SectionName, String itemName) {

		if (!ini.hasSection(SectionName)) {
			try {
				throw new IOException("section is not exists.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		IniSection iniSection = ini.getSection(SectionName);

		if (!iniSection.hasItem(itemName)) {
			try {
				throw new IOException("item is not exists.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		iniSection.removeItem(itemName);
		
		write();
	}
	// check file
	public static void checkFile(File file) {
		if (!file.canRead()) {
			if (!file.setReadable(true)) {
				try {
					throw new IOException("file is not readable.");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} else if (!file.canWrite()) {
			if (!file.setWritable(true)) {
				try {
					throw new IOException("file is not writeable.");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	public static void write(){
		IniFileWriter writer = new IniFileWriter(ini, configFile);
		try {
			writer.write();
		} catch (IOException e) {
			// exception thrown as an input\output exception occured
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Collection<String>items=Config.getItems("FILE_TYPE");
		for (String string : items) {
			System.out.print(string+"\t");
			System.out.println(Config.getValue("FILE_TYPE", string));
		}
		
		Config.setValue("hello", "name", " 中国人最好 ");
		System.out.println(Config.getValue("hello","name"));
	}
}
