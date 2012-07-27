package com.echeloneditor.actions;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

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
			  StringWriter out = new StringWriter(rSyntaxTextArea.getDocument().getLength());
	            StringReader reader = new StringReader(rSyntaxTextArea.getText());
	            InputSource src = new InputSource(reader);
	            Document doc = getDocBuilder().parse(src);
	            //Setup indenting to "pretty print"
	            getTransformer().transform(new DOMSource(doc), new StreamResult(out));
	            rSyntaxTextArea.setText(out.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isSuccess;
	}
	  public static Transformer getTransformer() {
	            TransformerFactory tfactory = TransformerFactory.newInstance();
	            
	            Transformer transformer;
	            try {
	                transformer = tfactory.newTransformer();
	            } catch (TransformerConfigurationException ex) {
	                throw new IllegalArgumentException("Unable to create transformer. ", ex);
	            }
	        return transformer;
	    }
	  
	  public static DocumentBuilderFactory getDocBuilderFactory() {
		  DocumentBuilderFactory docBuilderFactory;
	            docBuilderFactory = DocumentBuilderFactory.newInstance();
	        return docBuilderFactory;
	    }

	    public static DocumentBuilder getDocBuilder() {
	    	DocumentBuilder docBuilder;
	            try {
	                docBuilder = getDocBuilderFactory().newDocumentBuilder();
	            } catch (ParserConfigurationException ex) {
	                throw new IllegalArgumentException("Unable to create document builder", ex);
	            }
	        return docBuilder;
	    }
}
