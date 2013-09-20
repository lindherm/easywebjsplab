package com.gp.gpscript.profile.card;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gp.gpscript.profile.ProfileNode;
import com.gp.gpscript.profile.xPathNode;

//import com.watchdata.wdcams.loader.Loader;
/**
 * Powers is a placeholder element for one or more Power elements.
 */
public class cpPowers extends ProfileNode {
	private Logger log = Logger.getLogger(cpPowers.class);
	/**
	 * @see cpPower
	 */
	public cpPower Power[];

	public cpPowers(Node node) {
		super(node);
		NodeList nl;
		String xpString;
		try {
			xpString = "Power";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0) {
				Power = new cpPower[nl.getLength()];
				for (int i = 0; i < nl.getLength(); i++) {
					Power[i] = new cpPower(nl.item(i));
				}

			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Power " + e.getMessage());
		}
	}
}
