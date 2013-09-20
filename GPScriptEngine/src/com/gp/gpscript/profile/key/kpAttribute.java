package com.gp.gpscript.profile.key;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.gp.gpscript.profile.ProfileNode;

/**
 * The Attribute element is used to describe whether the key is permanent or not.
 */
public class kpAttribute extends ProfileNode {
	/**
	 * Key can��t change (read only key). Thus, the key can��t be generated nor can it be derived.
	 */
	public String Permanent;

	public kpAttribute(Node node) {
		super(node);
		Node attr;
		if (node.hasAttributes()) {
			NamedNodeMap map = node.getAttributes();
			attr = map.getNamedItem("Permanent");
			if (attr != null)
				Permanent = attr.getNodeValue();
			// System.out.println(Permanent);
		}
	}
}
