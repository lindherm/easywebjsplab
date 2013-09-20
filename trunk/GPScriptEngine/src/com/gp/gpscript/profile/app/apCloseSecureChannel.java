package com.gp.gpscript.profile.app;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gp.gpscript.profile.ProfileNode;
import com.gp.gpscript.profile.xPathNode;

/**
 * The CloseSecureChannel contains a script. The script operates on the object and has access to the keys and data created for the Application Profile in the scripting environment. For example, if the Application Profile identifies a GlobalPlatform Security Domain (Type = OP, Subtype = SD or CM), then the script is written for this object.
 */
public class apCloseSecureChannel extends ProfileNode {
	private static Logger log = Logger.getLogger(apCloseSecureChannel.class);
	/**
	 * @see apDeclaration
	 */
	public apDeclaration Declaration[];
	/**
	 * @see apKey
	 */
	public apKey Key[];
	/**
	 * @see apScript
	 */
	public apScript Script;

	public apCloseSecureChannel(Node node) {
		super(node);
		int i;
		String xpString;
		NodeList nl;
		try {
			xpString = "Declaration";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0) {
				Declaration = new apDeclaration[nl.getLength()];
				for (i = 0; i < nl.getLength(); i++) {
					Declaration[i] = new apDeclaration(nl.item(i));
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}

		try {
			xpString = "Key";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0) {
				Key = new apKey[nl.getLength()];
				for (i = 0; i < nl.getLength(); i++) {
					Key[i] = new apKey(nl.item(i));
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}

		try {

			xpString = "Script";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0) {
				Script = new apScript(nl.item(0));
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}

	}
}