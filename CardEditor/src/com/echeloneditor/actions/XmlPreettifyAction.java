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
		Document document;
		try {
			document = DocumentHelper.parseText(rSyntaxTextArea.getText());

			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setIndent("\t");
			format.setNewLineAfterDeclaration(false);
			StringWriter stringWriter = new StringWriter();
			XMLWriter writer = new XMLWriter(stringWriter, format);
			writer.write(document);
			rSyntaxTextArea.setText(stringWriter.toString());
			writer.close();

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
