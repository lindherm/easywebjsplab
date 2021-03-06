package com.gp.gpscript.profile.loadfile;

import org.w3c.dom.Node;

import com.gp.gpscript.profile.ProfileNode;

/**
 * Optional signature element for ensuring authenticity of profiles. Present in the Revision element for each of the profiles. Signature is an open element. The definition of the signature is outside of the scope of this document, and depends on implementation.
 */
public class lpSignature extends ProfileNode {
	public String Signature;

	public lpSignature(Node node) {
		super(node);
		if (node.hasChildNodes()) {
			Signature = node.getFirstChild().getNodeValue();
		}
	}
}