package com.gp.gpscript.profile.card;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.gp.gpscript.profile.ProfileNode;

/**
 * LifeCycle element is used to describe a lifecycle state and value.
 */
public class cpLifeCycle extends ProfileNode {
	/**
	 * Name of the application lifecycle. This will include the default lifecycle states as defined by the platform.
	 */
	public String Name;
	/**
	 * This is a text description of the application states which the application can exist in.
	 */
	public String Label;
	/**
	 * Value attributable to this lifecycle.
	 */
	public String Value;

	public cpLifeCycle(Node node) {
		super(node);
		if (node.hasAttributes()) {
			NamedNodeMap map = node.getAttributes();
			Node attr;
			attr = map.getNamedItem("Name");
			if (attr != null)
				Name = attr.getNodeValue();
			attr = map.getNamedItem("Label");
			if (attr != null)
				Label = attr.getNodeValue();
			attr = map.getNamedItem("Value");
			if (attr != null)
				Value = attr.getNodeValue();

		}

	}
}
