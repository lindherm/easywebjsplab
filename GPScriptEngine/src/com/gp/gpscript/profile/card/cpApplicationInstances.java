package com.gp.gpscript.profile.card;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gp.gpscript.profile.ProfileNode;
import com.gp.gpscript.profile.xPathNode;

//import com.watchdata.wdcams.loader.Loader;
/**
 * The ApplicationInstances element is a placeholder for ApplicationInstance elements. The ApplicationInstance elements contains the detailed information for each application instance.
 */
public class cpApplicationInstances extends ProfileNode {
	private Logger log = Logger.getLogger(cpApplicationInstances.class);
	/**
	 * @see cpApplicationInstance
	 */
	public cpApplicationInstance ApplicationInstance[];

	public cpApplicationInstances(Node node) {
		super(node);
		try {
			String xpString = "ApplicationInstance";
			NodeList nl = xPathNode.getNodeList(xpString, node);
			ApplicationInstance = new cpApplicationInstance[nl.getLength()];
			for (int i = 0; i < nl.getLength(); i++) {

				ApplicationInstance[i] = new cpApplicationInstance(nl.item(i));
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("ApplicatinInstances " + e.getMessage());
		}

	}
}