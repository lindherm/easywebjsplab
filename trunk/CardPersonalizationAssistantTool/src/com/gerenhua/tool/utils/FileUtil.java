package com.gerenhua.tool.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

public class FileUtil {
	private static Logger log=Logger.getLogger(FileUtil.class);
	public static String boradFile = "C:\\windows\\SCRCenter.ini";
	public static boolean updateBoradFile(String ip, String port) {
		RandomAccessFile filew = null;
		try {
			File file = new File(boradFile);
			if (!file.exists()) {
				file.createNewFile();
			}
			filew = new RandomAccessFile(boradFile, "rw");
			filew.setLength(0);

			writeFile("[RParameter]", boradFile);
			writeFile("USB1=" + ip, boradFile);
			writeFile("USB1Port=" + port, boradFile);
			return true;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "update SCRCenter.ini failed");
			return false;
		} finally {
			try {
				filew.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void writeFile(String cnt, String fileName) {
		FileWriter ow = null;
		try {
			ow = new FileWriter(fileName, true);

			ow.write(cnt);
			ow.write(System.getProperties().getProperty("line.separator"));
		} catch (Exception ex) {

			ex.printStackTrace();
		} finally {
			try {
				ow.close();
			} catch (Exception e) {
			}
		}
	}
	
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeVector(Vector<Vector<String>> vector) {
		try {
			File file = new File(System.getProperty("user.dir") + "/config.txt");
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bWriter = new BufferedWriter(fileWriter);

			for (int i = 0; i < vector.size(); i++) {
				StringBuilder sb = new StringBuilder();
				//sb.append(i + 1).append("|");
				for (int j = 0; j < vector.get(i).size(); j++) {
					String temp = vector.get(i).get(j);
					if (j == 0) {// "固定值", "动态增长数", "校检位", "随机数"
						if (temp.equals("固定值")) {
							temp = "0";
						} else if (temp.equals("动态增长数")) {
							temp = "1";
						} else if (temp.equals("校检位")) {
							temp = "2";
						} else if (temp.equals("随机数")) {
							temp = "3";
						}
					}
					sb.append(temp);
					if (j < vector.get(i).size() - 1) {
						sb.append("|");
					}
				}
				bWriter.write(sb.toString());
				bWriter.newLine();
			}

			bWriter.flush();
			bWriter.close();
		} catch (IOException e) {
			log.debug("file io error!");
			//System.out.println("file io error!");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 * @param strLines
	 */
	/*
	 * public static void writeFile(List<String> rulesList) { try { File file = new File(System.getProperty("user.dir") + "/config.txt"); FileWriter fileWriter = new FileWriter(file); BufferedWriter bWriter = new BufferedWriter(fileWriter);
	 * 
	 * for (String ruleLine : rulesList) { bWriter.write(ruleLine); bWriter.newLine(); } bWriter.flush(); bWriter.close(); } catch (IOException e) { System.out.println("file io error!"); e.printStackTrace(); }
	 * 
	 * }
	 */

	/**
	 * 
	 * 
	 * @param strLines
	 */
	public static List<String> readeFile() {
		List<String> ruleList = new ArrayList<String>();
		try {
			File file = new File(System.getProperty("user.dir") + "/config.txt");
			if (!file.exists()) {
				return ruleList;
			}
			FileReader fileReader = new FileReader(file);
			BufferedReader bReader = new BufferedReader(fileReader);
			String s1 = null;
			while ((s1 = bReader.readLine()) != null) {
				if (s1.length()>0) {
					ruleList.add(s1);
				}
				// System.out.println(s1);
			}
			bReader.close();
			bReader.close();
		} catch (IOException e) {
			log.debug("file io error!");
			//System.out.println("file io error!");
			e.printStackTrace();
		}

		return ruleList;
	}

	/**
	 * 
	 * @return
	 */

	public static Vector<Vector<String>> getConfigFromFile() {
		// String[][] data = new String[ruleList.size()][5];
		Vector<Vector<String>> data = new Vector<Vector<String>>();

		/*List<String> rulesList = readeFile();
		if (rulesList.isEmpty()) {
			return data;
		}
		for (int i = 0; i < rulesList.size(); i++) {
			Vector<String> ruleList = new Vector<String>();
			String[] temp = rulesList.get(i).split("[|]");
			// System.out.println(temp.toString());
			for (int j = 0; j < temp.length; j++) {
				String maskText = temp[j];
				if (j == 0) {
					int idx = Integer.valueOf(temp[j]);
					switch (idx) {
					case 0:
						maskText = "固定值";
						break;

					case 1:
						maskText = "动态增长数";
						break;
					case 2:
						maskText = "校检位";
						break;
					case 3:
						maskText = "随机数";// "固定值", "动态增长数", "校检位", "随机数"
						break;
					default:
						break;
					}
				}
				// System.out.println(i+":"+maskText);
				// data[i][j - 1] = maskText;
				ruleList.add(maskText);
			}
			data.add(ruleList);*/
		//}
		return data;
	}

	/*
	 * public static String[][] getConfigFromFile() { List<String> ruleList = readeFile(); String[][] data = new String[ruleList.size()][5]; for (int i = 0; i < ruleList.size(); i++) { String[] temp = ruleList.get(i).split("[|]"); // System.out.println(temp.toString()); for (int j = 1; j < temp.length; j++) { String maskText=temp[j]; if (j == 2) { int idx = Integer.valueOf(temp[j]); switch (idx) { case 0: maskText="固定值"; break;
	 * 
	 * case 1: maskText="动态增长数"; break; case 2: maskText="校检位"; break; case 3: maskText="随机数";//"固定值", "动态增长数", "校检位", "随机数" break; default: break; } } //System.out.println(i+":"+maskText); data[i][j - 1] = maskText; } } return data; }
	 */

	public static void printVector(Vector<Vector<String>> vector) {
		for (Vector<String> vector2 : vector) {
			for (String string : vector2) {
				//log.debug(string + ",");
				System.out.print(string + ",");
			}
			System.out.println();
		}
		//log.debug("__________________________________________");
		System.out.println("__________________________________________");
	}
}
