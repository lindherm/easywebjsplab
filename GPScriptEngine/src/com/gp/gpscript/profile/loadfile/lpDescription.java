package com.gp.gpscript.profile.loadfile;

import org.w3c.dom.Node;

import com.gp.gpscript.profile.ProfileNode;

/**
 * Freeform text description element.
 */
public class lpDescription extends ProfileNode {
	public String Description;

	public lpDescription(Node node) {
		super(node);
		if (node.hasChildNodes()) {
			Description = node.getFirstChild().getNodeValue();
		}
	}
}