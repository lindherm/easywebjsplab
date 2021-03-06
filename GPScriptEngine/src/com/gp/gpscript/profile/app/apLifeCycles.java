package com.gp.gpscript.profile.app;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gp.gpscript.profile.ProfileNode;
import com.gp.gpscript.profile.xPathNode;

public class apLifeCycles extends ProfileNode {
	private Logger log = Logger.getLogger(apLifeCycles.class);
	/**
	 * @see apLifeCycle
	 */
	public apLifeCycle LifeCycle[];

	public apLifeCycles(Node node) {
		super(node);
		try {
			String xpString = "LifeCycle";
			NodeList nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0) {
				LifeCycle = new apLifeCycle[nl.getLength()];
				for (int i = 0; i < nl.getLength(); i++) {
					LifeCycle[i] = new apLifeCycle(nl.item(i));
				}
			}
		} catch (Exception e) {

			// e.printStackTrace();
			log.error(e.getMessage());
		}
	}
}
