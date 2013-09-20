package com.gp.gpscript.profile.card;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gp.gpscript.profile.ProfileNode;
import com.gp.gpscript.profile.xPathNode;

//import com.watchdata.wdcams.loader.Loader;
/**
 * Protocols is a placeholder element for one or more Protocol elements.
 */
public class cpProtocols extends ProfileNode {
	private Logger log = Logger.getLogger(cpProtocols.class);
	/**
	 * @see cpProtocol
	 */
	public cpProtocol Protocol[];

	public cpProtocols(Node node) {
		super(node);
		NodeList nl;
		String xpString;
		try {
			xpString = "Protocol";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0) {
				Protocol = new cpProtocol[nl.getLength()];
				for (int i = 0; i < nl.getLength(); i++) {
					Protocol[i] = new cpProtocol(nl.item(i));
				}

			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Protocol " + e.getMessage());
		}
	}

}
