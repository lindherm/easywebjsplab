package com.gp.gpscript.profile.card;

import org.w3c.dom.Node;

import com.gp.gpscript.profile.ProfileNode;

/**
 * Freeform text description element.
 */
public class cpDescription extends ProfileNode {
	public String Description;

	public cpDescription(Node node) {
		super(node);
		if (node.hasChildNodes()) {
			Description = node.getFirstChild().getNodeValue();
		}
	}
}