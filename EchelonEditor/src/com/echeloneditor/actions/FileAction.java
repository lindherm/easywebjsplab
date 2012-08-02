package com.echeloneditor.actions;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.ByteOrderMarkDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class FileAction {
	private static final Logger log = Logger.getLogger(FileAction.class);

	CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance(); // A singleton.

	public FileAction() {
		// Add the implementations of info.monitorenter.cpdetector.io.ICodepageDetector:
		// This one is quick if we deal with unicode codepages:
		detector.add(new ByteOrderMarkDetector());
		// The first instance delegated to tries to detect the meta charset attribut in html pages.
		detector.add(new ParsingDetector(true)); // be verbose about parsing.
		// This one does the tricks of exclusion and frequency detection, if first implementation is
		// unsuccessful:
		detector.add(JChardetFacade.getInstance()); // Another singleton.
		detector.add(ASCIIDetector.getInstance()); // Fallback, see javadoc.
		detector.add(UnicodeDetector.getInstance()); // Fallback, see javadoc.
	}

	/**
	 * 
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws UnsupportedCharsetException
	 * @throws Exception
	 */
	public Map<String, String> open(String filePath) throws IOException, FileNotFoundException, UnsupportedEncodingException {
		String fileContent = "";
		Map<String, String> map = new HashMap<String, String>();
		File file = new File(filePath);
		// 文件大小
		map.put("fileSize", String.valueOf(file.length()));

		Charset charset = detector.detectCodepage(file.toURL());

		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		byte[] b = new byte[bis.available()];
		bis.read(b, 0, b.length);
		// 文件编码
		map.put("encode", charset.name());
		// 文件内容
		if (!charset.name().isEmpty() && !charset.name().equals("void")) {
			fileContent = new String(b, charset.name());
			log.debug("detect file's charset:" + charset.name());
		} else {
			log.debug("detect return void ,default charset:" + charset.name());
			fileContent = new String(b, "UTF-8");
		}
		map.put("fileContent", fileContent);
		// log.debug("open file done.");
		return map;
	}

	/**
	 * 
	 * @param filePath
	 * @param FileContent
	 * @param encode
	 * @return
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public void save(String filePath, String fileContent, String encode) throws IOException {
		File file = null;
		OutputStream os = null;
		OutputStreamWriter osw = null;
		Writer writer = null;
		try {
			// 创建文件
			file = new File(filePath);
			// 文件输出流
			os = new FileOutputStream(file);
			// 字符流通向字节流的桥梁
			osw = new OutputStreamWriter(os, encode);
			// 缓冲区
			writer = new BufferedWriter(osw);
			// 将字符写到文件中
			writer.write(fileContent);
			// 刷新缓冲区
			writer.flush();
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		} finally {
			if (os != null) {
				os.close();
			}

			if (osw != null) {
				osw.close();
			}

			if (writer != null) {
				writer.close();
			}
		}
	}
}
