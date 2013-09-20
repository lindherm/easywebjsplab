package com.gp.gpscript.profile.app;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.gp.gpscript.profile.ProfileNode;

/**
 * Key used in Script
 */
public class apKey extends ProfileNode {
	/**
	 * Name of the key. The key object created in the GP Scripting language [GP_SYS_SCR] corresponds to this case-sensitive name. Should be uniquely named across all Keys for the Application Profile As such, the name of the key should not contain any whitespace and should be a valid variable name in ECMAScript. Example: KEK
	 */
	public String Name;
	/**
	 * Unique ID of the Key Profile that this key is controlled by.
	 */
	public String ProfileID;

	public String External;

	public apKey(Node node) {
		super(node);
		if (node.hasAttributes()) {
			NamedNodeMap map = node.getAttributes();
			Node attr;
			attr = map.getNamedItem("Name");
			if (attr != null)
				Name = attr.getNodeValue();
			attr = map.getNamedItem("ProfileID");
			if (attr != null)
				ProfileID = attr.getNodeValue();
			attr = map.getNamedItem("External");
			if (attr != null)
				External = attr.getNodeValue();
		}

	}

}
