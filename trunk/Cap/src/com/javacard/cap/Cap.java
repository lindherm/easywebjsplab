package com.javacard.cap;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.watchdata.commons.lang.WDByteUtil;

public class Cap {
	// read pkg cap
	public static Map<String, String> readCap(String capFilePath) throws IOException {
		Map<String, String> mapBean = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();

		ZipFile zipFile = new ZipFile(capFilePath);
		Enumeration<?> en = zipFile.entries();
		do {
			// 清空
			sb.setLength(0);

			if (!en.hasMoreElements())
				break;
			ZipEntry ze = (ZipEntry) en.nextElement();
			String zeName = ze.getName();

			if (!zeName.equals("META-INF/MANIFEST.MF") && zeName.endsWith(".cap")) {
				InputStream in = zipFile.getInputStream(ze);
				byte buf[] = new byte[10240];
				do {
					int length = in.read(buf, 0, buf.length);
					if (length != -1) {
						sb.append(WDByteUtil.bytes2HEX(buf, 0, length));
					} else {
						break;
					}
				} while (true);
				zeName = zeName.substring(zeName.lastIndexOf("/") + 1);
				mapBean.put(zeName, sb.toString());
			}
		} while (true);
		zipFile.close();

		return mapBean;
	}

	public String checkNull(String str) {
		return str == null ? "" : str;
	}
	
	public static void main(String[] args) throws IOException {
		Map<String, String> mapPse=Cap.readCap("pse.cap");
		Map<String, String> mapPpse=Cap.readCap("ppse.cap");
		Map<String, String> mapPboc=Cap.readCap("pboc.cap");
		
		System.out.println(mapPse);
		
		Iterator<?> iterator=mapPse.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry=(Entry<String, String>)iterator.next();
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}
	}
}
