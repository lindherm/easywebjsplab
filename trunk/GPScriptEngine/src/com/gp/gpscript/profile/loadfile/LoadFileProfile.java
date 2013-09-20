package com.gp.gpscript.profile.loadfile;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gp.gpscript.profile.Profile;
import com.gp.gpscript.profile.xPathNode;

//import com.watchdata.wdcams.loader.Loader;
public class LoadFileProfile extends Profile {
	private static Logger log = Logger.getLogger(LoadFileProfile.class);
	/**
	 * Variable length Application Provider OID (up to 119 bytes) + 5 byte value assigned by Application Provider. This unique id field must be unique amongst all UniqueID attributes for the LoadFile element for all Application Profiles supplied by the Application Provider who has been assigned the OID used. Once the Load File Profile has been altered, the UniqueID changes to reflect the actor which has changed it. As well, an additional revision element is created to enable tracking of the modification. Example: 00010203040506070809 Where 0001020304 is the OID of the Application Provider.
	 */
	public String UniqueID;
	/**
	 * Version of the specification the Load File Profile adheres to, conforming to GP versioning conventions.
	 */
	public String ProfileVersion;
	/**
	 * @see lpDescription
	 */
	public lpDescription Description;
	/**
	 * @see lpRevisions
	 */
	public lpRevisions Revisions;
	/**
	 * @see lpConflictRules
	 */
	public lpConflictRules ConflictRules;
	/**
	 * @see lpLoadFileInfo
	 */
	public lpLoadFileInfo LoadFileInfo;
	/**
	 * @see lpModule
	 */
	public lpModule Module;

	private Element currElement;

	public LoadFileProfile(String xmlFile) {
		super(xmlFile);
		String xpString;
		NodeList nl;
		Node node;

		node = document.getDocumentElement();
		currElement = (Element) node;
		NamedNodeMap map;
		map = node.getAttributes();
		UniqueID = map.getNamedItem("UniqueID").getNodeValue();
		ProfileVersion = map.getNamedItem("ProfileVersion").getNodeValue();

		try {
			xpString = "/LoadFileProfile/Revisions";
			nl = xPathNode.getNodeList(xpString, document);
			if (nl.getLength() > 0) {
				Revisions = new lpRevisions(nl.item(0));
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Revisions Error");
		}

	}

	public String getNodeName() {
		return (currElement.getNodeName());
	}

	public String getAtrribute(String attr) {
		return (currElement.getAttribute(attr));
	}

	public void setAttribute(String attrName, String attr) {
		currElement.setAttribute(attrName, attr);
	}

	public static void main(String args[]) {
		log.debug("Start Program");
		LoadFileProfile lp = new LoadFileProfile(args[0]);
		log.debug(lp.ProfileVersion);
		for (int i = 0; i < lp.Revisions.Revision.length; i++) {
			log.debug(lp.Revisions.Revision[i].Date);
			log.debug(lp.Revisions.Revision[i].Time);
			log.debug(lp.Revisions.Revision[i].Description.Description);

		}

	}

}
