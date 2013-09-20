package com.gp.gpscript.profile.app;

import org.w3c.dom.Node;

import com.gp.gpscript.profile.ProfileNode;

/**
 * Freeform text description element.
 */
public class apDescription extends ProfileNode {
	public String Description;

	public apDescription(Node node) {
		super(node);
		if (node.hasChildNodes()) {
			Description = node.getFirstChild().getNodeValue();
		}
	}
}