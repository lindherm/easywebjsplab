package com.echeloneditor.actions;

import java.io.IOException;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

public class XmlPreettifyAction {
	/**
	 * xml格式化
	 * 
	 * @param rSyntaxTextArea
	 * @return
	 */
	public static boolean format(RSyntaxTextArea rSyntaxTextArea) {
		boolean isSuccess = false;
		try {
			System.out.println("before:"+rSyntaxTextArea.getText());
			Document doc = DocumentHelper.parseText(rSyntaxTextArea.getText());
			StringWriter writer = new StringWriter();
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			// format.setIndent("    ");
			format.setIndent(false);
			format.setNewlines(false);
			// 如果这个为true,那么空格和换行都被去掉，都在一行
			format.setTrimText(false);
			// 如果这个为true，那么element的起始和结束不对齐
			format.setPadText(false);
			// format.setLineSeparator(xmlfi.lineSeparator);

			XMLWriter xmlwriter = new XMLWriter(writer, format);
			xmlwriter.write(doc);

			rSyntaxTextArea.setText(doc.asXML());
			System.out.println("after:"+doc.asXML());
			isSuccess = true;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isSuccess;
	}
}
