package com.gp.gpscript.profile.card;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gp.gpscript.profile.ProfileNode;
import com.gp.gpscript.profile.xPathNode;

//import com.watchdata.wdcams.loader.Loader;
/**
 * A placeholder element for application provided data used by the SCMS to manage an unique card holder��s smart card. If more than one Execute element is present, it is up to the discretion of the SCMS to choose the correct one. Also, there is a capability to specify a simple script, devoid of external data, parameter or key references, under the SimpleScript element.
 */
public class cpOnLoad extends ProfileNode {
	private Logger log = Logger.getLogger(cpOnLoad.class);
	/**
	 * @see cpSimpleScript
	 */
	public cpSimpleScript SimpleScript;
	/**
	 * @see cpExecute
	 */
	public cpExecute Execute[];

	public cpOnLoad(Node node) {
		super(node);
		if (node.hasChildNodes()) {
			NodeList nl;
			String xpString;
			try {
				xpString = "SimpleScript";
				nl = xPathNode.getNodeList(xpString, node);
				if (nl.getLength() > 0)
					SimpleScript = new cpSimpleScript(nl.item(0));
			} catch (Exception e) {
				// e.printStackTrace();
				log.error("SimpleScript " + e.getMessage());
			}
			try {
				xpString = "Execute";
				nl = xPathNode.getNodeList(xpString, node);
				Execute = new cpExecute[nl.getLength()];
				for (int i = 0; i < nl.getLength(); i++) {
					Execute[i] = new cpExecute(nl.item(i));
				}
			} catch (Exception e) {
				// e.printStackTrace();
				log.error("Execute " + e.getMessage());
			}
		}
	}
}
