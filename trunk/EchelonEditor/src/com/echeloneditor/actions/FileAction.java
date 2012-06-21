package com.echeloneditor.actions;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.ByteOrderMarkDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.Map;

public class FileAction {
	CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance(); // A singleton.

	public FileAction() {
		// Add the implementations of info.monitorenter.cpdetector.io.ICodepageDetector:
		// This one is quick if we deal with unicode codepages:
		detector.add(new ByteOrderMarkDetector());
		// The first instance delegated to tries to detect the meta charset attribut in html pages.
		detector.add(new ParsingDetector(false)); // be verbose about parsing.
		// This one does the tricks of exclusion and frequency detection, if first implementation is
		// unsuccessful:
		detector.add(JChardetFacade.getInstance()); // Another singleton.
		detector.add(ASCIIDetector.getInstance()); // Fallback, see javadoc.
	}

	public Map<String, String> open(String filePath) throws FileNotFoundException, IOException, UnsupportedCharsetException, Exception {
		String fileContent = "";
		Map<String, String> map = new HashMap<String, String>();
		File file = new File(filePath);
		// 文件大小
		map.put("fileSize", String.valueOf(file.length()));

		Charset charset = detector.detectCodepage(file.toURL());

		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		byte[] b = new byte[in.available()];
		in.read(b, 0, b.length);
		// 文件编码
		map.put("encode", charset.name());
		// 文件内容
		if (!charset.name().isEmpty() && !charset.name().equals("void")) {
			fileContent = new String(b, charset.name());
		} else {
			fileContent = new String(b, "UTF-8");
		}
		map.put("fileContent", fileContent);
		return map;
	}

	/**
	 * 获取文件中最长的列
	 * 
	 * @param fileContent
	 * @return
	 */
	public int getFileMaxColumn(String fileContent) {
		int maxColumn = 0;
		String[] fileLines = fileContent.split("\n");

		for (String string : fileLines) {
			if (maxColumn < string.length()) {
				try {
					maxColumn = string.getBytes("GB2312").length;
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return maxColumn;
	}
}
