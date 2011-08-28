package org.easyweb.jword.transformer;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class WordTransformer {
	@SuppressWarnings("unchecked")
	public void transformWORD(String srcFileName, Map map, String destFileName) throws ZipException, IOException, SAXException, ParserConfigurationException, TransformerException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		// 读取模板文件
		String templateStr = readDocTemplate(srcFileName);
		// 遍历map替换值
		String s = replaceContentWidthMap(templateStr, map);
		// 生成docx文件
		generateDocFile(srcFileName, s, destFileName);
	}

	public String readDocTemplate(String srcFileName) throws ZipException, IOException {
		// 模板文件位置
		ZipFile docxFile = new ZipFile(new File(srcFileName));
		ZipEntry documentXML = docxFile.getEntry("word/document.xml");
		InputStream documentXMLIS = docxFile.getInputStream(documentXML);
		String s = "";
		// 读取模板中的document.xml文件
		InputStreamReader reader = new InputStreamReader(documentXMLIS, "UTF-8");
		BufferedReader br = new BufferedReader(reader);
		String str = null;
		// 按行读取模板文件
		while ((str = br.readLine()) != null) {
			s = s + str;
		}
		documentXMLIS.close();
		reader.close();
		br.close();
		return s;
	}

	public String replaceContentWidthMap(String templateStr, Map map) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
			Entry entry = (Entry) it.next();
			Object object = (Object) entry.getValue();
			Class c = Class.forName(object.getClass().getName());
			Method method[] = c.getDeclaredMethods();
			for (int i = 0; i < method.length; i++) {
				String methodName = method[i].getName();
				if (methodName.startsWith("get")) {
					String filedName = methodName.substring(3);
					String desStr = entry.getKey().toString() + "." + filedName.substring(0, 1).toLowerCase() + filedName.substring(1);
					String replaceStr = method[i].invoke(object, new Object[] {}).toString();
					templateStr = templateStr.replaceAll(desStr, replaceStr);
				}
			}
		}
		return templateStr;
	}

	public void generateDocFile(String srcFileName, String s, String destFileName) throws ZipException, IOException, SAXException, ParserConfigurationException, TransformerException {
		ZipFile docxFile = new ZipFile(new File(srcFileName));
		ZipEntry documentXML = docxFile.getEntry("word/document.xml");
		// ZipEntry imgFile = docxFile.getEntry("word/media/image1.png");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		InputStream documentXMLIS1 = docxFile.getInputStream(documentXML);
		Document doc = dbf.newDocumentBuilder().parse(documentXMLIS1);
		/*Element docElement = doc.getDocumentElement();
		// System.out.println(docElement.getTagName());
		Element bodyElement = (Element) docElement.getElementsByTagName("w:body").item(0);
		// System.out.println(bodyElement.getTagName());
		Element pElement = (Element) bodyElement.getElementsByTagName("w:p").item(0);
		// System.out.println(pElement.getTagName());
		Element rElement = (Element) pElement.getElementsByTagName("w:r").item(0);
		// System.out.println(rElement.getTagName());
		Element tElement = (Element) rElement.getElementsByTagName("w:t").item(0);
		// System.out.println(tElement.getTagName());
*/		// 转换
		Transformer t = TransformerFactory.newInstance().newTransformer();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		t.transform(new DOMSource(doc), new StreamResult(baos));
		// 目标文件地址
		ZipOutputStream docxOutFile = new ZipOutputStream(new FileOutputStream(destFileName));
		Enumeration<ZipEntry> entriesIter = (Enumeration<ZipEntry>) docxFile.entries();
		while (entriesIter.hasMoreElements()) {
			ZipEntry entry = entriesIter.nextElement();
			// System.out.println(entry.getName());

			if (entry.getName().equals("word/document.xml")) {
				// byte[] data = baos.toByteArray();
				// 创建目录
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
}
