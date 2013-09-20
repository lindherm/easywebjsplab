package com.gp.gpscript.profile.loadfile;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gp.gpscript.profile.ProfileNode;
import com.gp.gpscript.profile.xPathNode;

//import com.watchdata.wdcams.loader.Loader;
/**
 * ConflictRules is a placeholder for individual ConflictRule elements. ConflictRules are used in the Application and Card Profiles.
 */
public class lpConflictRules extends ProfileNode {
	private Logger log = Logger.getLogger(lpConflictRules.class);
	/**
	 * @see lpConflictRule
	 */
	public lpConflictRule ConflictRule[];

	public lpConflictRules(Node node) {
		super(node);
		String xpString = "ConflictRule";
		NodeList nl;
		try {
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0) {
				ConflictRule = new lpConflictRule[nl.getLength()];
				for (int i = 0; i < nl.getLength(); i++) {
					ConflictRule[i] = new lpConflictRule(nl.item(i));
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("ConflictRule " + e.getMessage());
		}
	}

}
