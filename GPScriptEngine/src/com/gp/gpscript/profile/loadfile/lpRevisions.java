package com.gp.gpscript.profile.loadfile;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gp.gpscript.profile.ProfileNode;
import com.gp.gpscript.profile.xPathNode;

//import com.watchdata.wdcams.loader.Loader;
/**
 * A placeholder for the revision entries for a profile. Used by all profiles.
 */
public class lpRevisions extends ProfileNode {
	private Logger log = Logger.getLogger(lpRevisions.class);
	/**
	 * @see lpRevision
	 */
	public lpRevision Revision[];

	public lpRevisions(Node node) {
		super(node);
		String xpString = "Revision";
		try {

			NodeList nl = xPathNode.getNodeList(xpString, node);
			Revision = new lpRevision[nl.getLength()];

			for (int i = 0; i < nl.getLength(); i++) {
				Revision[i] = new lpRevision(nl.item(i));
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Revisions " + e.getMessage());
		}

	}

}