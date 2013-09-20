package com.gp.gpscript.profile.key;

import org.w3c.dom.Node;

import com.gp.gpscript.profile.ProfileNode;

public class kpDescription extends ProfileNode {
	public String Description;

	public kpDescription(Node node) {
		super(node);
		if (node.hasChildNodes()) {
			Description = node.getFirstChild().getNodeValue();
		}
	}
}