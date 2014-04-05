package com.gerenhua.tool.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import javax.swing.JOptionPane;

public class FileUtil {
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
}
