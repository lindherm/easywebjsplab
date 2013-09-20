package com.gp.gpscript.profile.app;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.gp.gpscript.profile.ProfileNode;

public class apCode extends ProfileNode {
	public String ProfileID;
	public String ModuleID;

	public apCode(Node node) {
		super(node);
		if (node.hasAttributes()) {
			NamedNodeMap map = node.getAttributes();
			Node attr;
			attr = map.getNamedItem("ProfileID");
			if (attr != null)
				ProfileID = attr.getNodeValue();
			attr = map.getNamedItem("ModuleID");
			if (attr != null)
				ModuleID = attr.getNodeValue();
		}
	}

}
