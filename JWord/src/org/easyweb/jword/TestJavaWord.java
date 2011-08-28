package org.easyweb.jword;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class TestJavaWord {
	public void helloWorld() throws ZipException, IOException, SAXException, ParserConfigurationException, TransformerException, TransformerFactoryConfigurationError {
		//模板文件位置
		ZipFile docxFile = new ZipFile(new File("c:/document.docx"));
		ZipEntry documentXML = docxFile.getEntry("word/document.xml");
		InputStream documentXMLIS = docxFile.getInputStream(documentXML);
		String s = "";
		//读取模板中的document.xml文件
		InputStreamReader reader = new InputStreamReader(documentXMLIS, "UTF-8");
		BufferedReader br = new BufferedReader(reader);
		String str = null;

		while ((str = br.readLine()) != null) {
			s = s + str;
		}
		//替换内容
		s = s.replaceAll("helloworld", "替换内容");
		s = s.replaceAll("pass", "替换内容测试");
		s = s.replaceAll("test", "替换内容肖");
		//System.out.println(s);
		reader.close();
		br.close();

		// ZipEntry imgFile = docxFile.getEntry("word/media/image1.png");

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		InputStream documentXMLIS1 = docxFile.getInputStream(documentXML);
		Document doc = dbf.newDocumentBuilder().parse(documentXMLIS1);
		Element docElement = doc.getDocumentElement();
		// assertEquals("w:document", docElement.getTagName());
		Element bodyElement = (Element) docElement.getElementsByTagName("w:body").item(0);
		// assertEquals("w:body", bodyElement.getTagName());
		Element pElement = (Element) bodyElement.getElementsByTagName("w:p").item(0);
		// assertEquals("w:p", pElement.getTagName());
		Element rElement = (Element) pElement.getElementsByTagName("w:r").item(0);
		// assertEquals("w:r", rElement.getTagName());
		Element tElement = (Element) rElement.getElementsByTagName("w:t").item(0);
		// assertEquals("w:t", tElement.getTagName());
		// assertEquals("这是第一个测试文档", tElement.getTextContent());
		// tElement.setTextContent("这是第一个用Java写的测试文档");
		//转换
		Transformer t = TransformerFactory.newInstance().newTransformer();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		t.transform(new DOMSource(doc), new StreamResult(baos));
		//目标文件地址
		ZipOutputStream docxOutFile = new ZipOutputStream(new FileOutputStream("c:/response.docx"));
		Enumeration<ZipEntry> entriesIter = (Enumeration<ZipEntry>) docxFile.entries();
		while (entriesIter.hasMoreElements()) {
			ZipEntry entry = entriesIter.nextElement();
			//System.out.println(entry.getName());

			if (entry.getName().equals("word/document.xml")) {
				//byte[] data = baos.toByteArray();
				//创建目录
				docxOutFile.putNextEntry(new ZipEntry(entry.getName()));
				byte[] datas = s.getBytes("UTF-8");
				docxOutFile.write(datas, 0, datas.length);
				// docxOutFile.write(data, 0, data.length);
				docxOutFile.closeEntry();
			} else if (entry.getName().equals("word/media/image1.png")) {
				InputStream incoming = new FileInputStream("c:/aaa.jpg");
				byte[] data = new byte[incoming.available()];
				int readCount = incoming.read(data, 0, data.length);
				docxOutFile.putNextEntry(new ZipEntry(entry.getName()));
				docxOutFile.write(data, 0, readCount);
				docxOutFile.closeEntry();
			} else {
				InputStream incoming = docxFile.getInputStream(entry);
				byte[] data = new byte[incoming.available()];
				int readCount = incoming.read(data, 0, data.length);
				docxOutFile.putNextEntry(new ZipEntry(entry.getName()));
				docxOutFile.write(data, 0, readCount);
				docxOutFile.closeEntry();
			}
		}
		docxOutFile.close();
	}

	public static void main(String[] args) throws ZipException, IOException, SAXException, ParserConfigurationException, TransformerException, TransformerFactoryConfigurationError {
		new TestJavaWord().helloWorld();
	}
}
