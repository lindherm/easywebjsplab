package com.gp.gpscript.profile.loadfile;

import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gp.gpscript.profile.ProfileNode;
import com.gp.gpscript.profile.xPathNode;

//import com.watchdata.wdcams.loader.Loader;
/**
 * Module elements are used to describe separate application modules that exist within a Load File.
 */
public class lpModule extends ProfileNode {
	private Logger log = Logger.getLogger(lpModule.class);
	/**
	 * Unique identifier for the module, consisting of Application Developer��s OID + 5 bytes of unique data. Even if the Load File Profile has been changed, the ModuleID should not change unless the module has been updated by the Application Developer for the load file.
	 */
	public String ModuleID;
	/**
	 * GP standard naming for the application ID assigned to the module. Example: A123456789AB
	 */
	public String AID;
	/**
	 * @see lpDescription
	 */
	public lpDescription Description;

	public lpModule(Node node) {
		super(node);
		if (node.hasAttributes()) {
			NamedNodeMap map = node.getAttributes();
			Node attr;
			attr = map.getNamedItem("ModuleID");
			if (attr != null)
				ModuleID = attr.getNodeValue();
			attr = map.getNamedItem(" AID");
			if (attr != null)
				AID = attr.getNodeValue();
		}
		String xpString = "Description";
		NodeList nl;
		try {
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0) {
				Description = new lpDescription(nl.item(0));
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Description " + e.getMessage());
		}

	}
}