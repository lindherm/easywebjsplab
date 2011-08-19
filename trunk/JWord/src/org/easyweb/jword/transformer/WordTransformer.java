package org.easyweb.jword.transformer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class WordTransformer {
	@SuppressWarnings("unchecked")
	public String transformWORD(File file, Map map) throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
		String templateStr = rtfToStr(file);
		for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
			Entry entry = (Entry) it.next();
			Object object = (Object) entry.getValue();
			Class c = Class.forName(object.getClass().getName());
			Method method[] = c.getDeclaredMethods();
			for (int i = 0; i < method.length; i++) {
				String methodName = method[i].getName();
				if (methodName.startsWith("get")) {
					/*
					 * System.out.println(method[i].getName());
					 * System.out.println(method[i].invoke(object,new
					 * Object[]{}));
					 */
					String srcStr = "{" + entry.getKey() + "." + methodName.substring(3).toLowerCase() + "}";
					System.out.println(srcStr);
					String targetStr = chineseToRtr(method[i].invoke(object, new Object[] {}).toString());

					int pos = srcStr.indexOf(targetStr);
					String retstring = srcStr;
					while (pos != -1) {
						retstring = retstring.substring(0, pos) + templateStr + retstring.substring(pos + templateStr.length(), retstring.length());
						pos = retstring.indexOf(templateStr);
					}
				}
			}
		}
		return templateStr;
	}

	public String rtfToStr(File file) throws IOException {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			byte[] inbuffer = new byte[(int) file.length()];
			is.read(inbuffer);
			return new String(inbuffer);
		} catch (Exception e) {
			// 打印错误信息
			e.printStackTrace();
			return null;
		} finally {
			// 关闭流
			if (is != null) {
				is.close();
			}
		}
	}

	public String chineseToRtr(String str) throws UnsupportedEncodingException {
		byte[] b = str.getBytes("gb2312");
		StringBuffer sb = new StringBuffer();
		for (int i = 0, j = b.length; i < j; i++) {
			sb.append("\\\'");
			String v = Integer.toHexString(b[i]);
			if (v.length() > 1) { // 排除空格，空格的b[i]为10，char为a，长度只为1
				sb.append(v.substring(v.length() - 2, v.length()));
			}
		}
		return sb.toString();
	}
}
