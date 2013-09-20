package com.gp.gpscript.profile;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Profile Node is a basic class, all class about profile inherit from ProfileNode
 */
public class ProfileNode {
	private Element currElement = null;

	/**
	 * default construct, the element node not be initialized, request user call setElement to initialize inside Element node of ProfileNode
	 */
	public ProfileNode() {
	}

	/**
	 * auto initialize inside Element node of ProfileNode
	 * 
	 * @param node
	 *            current Node in DOM
	 */
	public ProfileNode(Node node) {
		currElement = (Element) node;
	}

	/**
	 * initialize inside Element node of ProfileNode
	 * 
	 * @param element
	 *            ,current element node
	 */
	public void setElement(Element element) {
		currElement = element;
	}

	/**
	 * @return Element Node name ,if Element node not be initialized return value is null
	 */
	public String getNodeName() {
		if (currElement != null) {
			return (currElement.getNodeName());
		}
		return (null);
	}

	/**
	 * get Element Node attribute by attribute name
	 * 
	 * @param attr
	 *            attribute name
	 * @return Element Node attribute ,if Element node not be initialized return value is null
	 */
	public String getAtrribute(String attr) {
		if (currElement != null) {
			return (currElement.getAttribute(attr));
		}
		return (null);

	}

	/**
	 * @param attrNmae
	 *            attriubte name
	 * @param attr
	 *            attribute value
	 */
	public void setAttribute(String attrName, String attr) {
		if (currElement != null) {
			currElement.setAttribute(attrName, attr);
		}

	}

	/**
	 * get Element node value,if element node has a TEXT_NODE child node, return the child node value, or return null
	 * 
	 * @return Element node value or null
	 */
	public String getNodeValue() {
		if (currElement != null) {
			if (currElement.getFirstChild().getNodeType() == currElement.TEXT_NODE) {
				return (currElement.getFirstChild().getNodeValue());
			}
		}
		return (null);
	}

	/**
	 * if node has child node and the child node type is TEXT_NODE , replace the value of child node
	 * 
	 * @param value
	 *            new value of node
	 */
	public void setNodeValue(String value) {
		if (currElement != null) {
			currElement.getFirstChild().setNodeValue(value);
		}
	}
}