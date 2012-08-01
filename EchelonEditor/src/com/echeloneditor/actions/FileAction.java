package com.echeloneditor.actions;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.ByteOrderMarkDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
		detector.add(new ParsingDetector(false)); // be verbose about parsing.
		// This one does the tricks of exclusion and frequency detection, if first implementation is
		// unsuccessful:
		detector.add(JChardetFacade.getInstance()); // Another singleton.
		detector.add(ASCIIDetector.getInstance()); // Fallback, see javadoc.
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
	public Map<String, String> open(String filePath) throws FileNotFoundException, IOException, UnsupportedCharsetException, Exception {
		// log.debug("open file...");
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
		log.debug("file charset:" + charset.name());
		// 文件内容
		if (!charset.name().isEmpty() && !charset.name().equals("void")) {
			fileContent = new String(b, charset.name());
		} else {
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
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public void save(String filePath, String fileContent, String encode) {
		File file = new File(filePath);
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);

			fileContent = new String(fileContent.getBytes(), encode);

			bw.write(fileContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				if (bw != null) {
					bw.close();
				}
				if (fw != null) {
					fw.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
