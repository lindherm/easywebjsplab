package com.gp.gpscript.profile.app;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.gp.gpscript.profile.ProfileNode;

/**
 * LifeCycle element is used to describe a lifecycle state and value, either for the lifecycle of the application on the card or for a state in application processing.
 */
public class apLifeCycle extends ProfileNode {
	/**
	 * Name of the application lifecycle. This will include the default lifecycle states as defined by the platform. These names need to be unique for each Application. Examples: INSTALLED Application State SELECTABLE Application State PRE_PERSOPREP Processing State POST_PERSOPREP Processing State
	 */
	public String Name;
	/**
	 * Label This is a text description of the application states which the application can exist in.
	 */
	public String Label;
	/**
	 * Value attributable to this lifecycle. For lifecycle states representing application lifecycles, Value is mandatory and must represent the value returned from a GET STATUS APDU command. Each application life cycle should have an unique value. Example: 0x03 For the lifecycle state INSTALLED 0x07 For the lifecycle state SELECTABLE For lifecycle states representing processing states, a Value shouldn��t be specified.
	 */
	public String Value;

	public apLifeCycle(Node node) {
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
