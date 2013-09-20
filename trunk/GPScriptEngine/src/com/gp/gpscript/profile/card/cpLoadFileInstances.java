package com.gp.gpscript.profile.card;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gp.gpscript.profile.ProfileNode;
import com.gp.gpscript.profile.xPathNode;

//import com.watchdata.wdcams.loader.Loader;
/**
 * The LoadFileInstances element is a placeholder for individual LoadFileInstance elements. The LoadFileInstance elements contain the detail for each of the load file instances installed.
 */
public class cpLoadFileInstances extends ProfileNode {
	private Logger log = Logger.getLogger(cpLoadFileInstances.class);
	/**
	 * @see cpLoadFileInstance
	 */
	public cpLoadFileInstance LoadFileInstance[];

	public cpLoadFileInstances(Node node) {
		super(node);

		NodeList nl;
		String xpString;
		try {
			xpString = "LoadFileInstance";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0) {
				LoadFileInstance = new cpLoadFileInstance[nl.getLength()];
				for (int i = 0; i < nl.getLength(); i++) {
					LoadFileInstance[i] = new cpLoadFileInstance(nl.item(i));
				}

			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("LoadFileInstance " + e.getMessage());
		}

	}
}
