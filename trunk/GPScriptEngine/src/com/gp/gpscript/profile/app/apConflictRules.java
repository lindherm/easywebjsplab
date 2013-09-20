package com.gp.gpscript.profile.app;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gp.gpscript.profile.ProfileNode;
import com.gp.gpscript.profile.xPathNode;

//import com.watchdata.wdcams.loader.Loader;
/**
 * ConflictRules is a placeholder for individual ConflictRule elements. ConflictRules are used in the Application and Card Profiles.
 */
public class apConflictRules extends ProfileNode {
	private Logger log = Logger.getLogger(apConflictRules.class);
	/**
	 * @see apConflictRule
	 */
	public apConflictRule ConflictRule[];

	public apConflictRules(Node node) {
		super(node);
		try {
			String xpString = "ConflictRule";
			NodeList nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0) {
				ConflictRule = new apConflictRule[nl.getLength()];
				for (int i = 0; i < nl.getLength(); i++) {
					ConflictRule[i] = new apConflictRule(nl.item(i));
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}
	}
}
