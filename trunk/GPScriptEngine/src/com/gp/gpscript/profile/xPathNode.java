package com.gp.gpscript.profile;

import org.apache.xalan.xpath.XObject;
import org.apache.xalan.xpath.XPath;
import org.apache.xalan.xpath.XPathProcessor;
import org.apache.xalan.xpath.XPathProcessorImpl;
import org.apache.xalan.xpath.xml.PrefixResolver;
import org.apache.xalan.xpath.xml.PrefixResolverDefault;
import org.apache.xalan.xpath.xml.XMLParserLiaison;
import org.apache.xalan.xpath.xml.XMLParserLiaisonDefault;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class xPathNode {
	/**
	 * static method ,get child NodeList from parent node,
	 * 
	 * @param xpString
	 *            path of child, from parent node to child node in DOM tree
	 * @param node
	 *            parent node
	 */
	public static NodeList getNodeList(String xpString, Node node) throws Exception {
		// need to recreate a few helper objects
		XMLParserLiaison xpathSupport = new XMLParserLiaisonDefault();
		XPathProcessor xpathParser = new XPathProcessorImpl(xpathSupport);
		// PrefixResolver prefixResolver = new PrefixResolverDefault(source.getDocumentElement());
		PrefixResolver prefixResolver = new PrefixResolverDefault(node);
		// create the XPath and initialize it
		XPath xp = new XPath();
		xpathParser.initXPath(xp, xpString, prefixResolver);

		// now execute the XPath select statement
		// XObject list = xp.execute(xpathSupport, source.getDocumentElement(), prefixResolver);
		XObject list = xp.execute(xpathSupport, node, prefixResolver);
		// return the resulting node
		NodeList nl = list.nodeset();
		return (nl);

	}

}