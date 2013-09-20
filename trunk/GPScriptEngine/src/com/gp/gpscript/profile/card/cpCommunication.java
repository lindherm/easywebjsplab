package com.gp.gpscript.profile.card;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gp.gpscript.profile.ProfileNode;
import com.gp.gpscript.profile.xPathNode;

//import com.watchdata.wdcams.loader.Loader;
/**
 * A placeholder element for the communication features of the contact and contactless smart card components.
 */
public class cpCommunication extends ProfileNode {
	private Logger log = Logger.getLogger(cpCommunication.class);
	/**
	 * @see cpContact
	 */
	public cpContact Contact;
	/**
	 * @see cpContactless
	 */
	public cpContactless Contactless;

	public cpCommunication(Node node) {
		super(node);
		NodeList nl;
		String xpString;
		try {
			xpString = "Contact";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0)
				Contact = new cpContact(nl.item(0));
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Contact " + e.getMessage());
		}
		try {

			xpString = "Contactless";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0)
				Contactless = new cpContactless(nl.item(0));
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Contactless " + e.getMessage());
		}

	}
}