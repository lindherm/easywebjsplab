package com.gp.gpscript.profile.app;

import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gp.gpscript.profile.ProfileNode;
import com.gp.gpscript.profile.xPathNode;

//import com.watchdata.wdcams.loader.Loader;
/**
 * Copyright �� 2002 GlobalPlatform Inc. All Rights Reserved. The technology provided or described herein is subject to updates, revisions, and extensions by GlobalPlatform. Use of this information is governed by the GlobalPlatform license agreement and any use inconsistent with that agreement is strictly prohibited Function elements contain Script code for functions that are used by the Script Fragments associated with this application and which are not provided by the Scripting API. The function code forms the content of the Function XML element itself, and is accompanied by the two attributes listed in Table 4-14. The Name attribute is mandatory and contains the name of the function as well as the parameters required by the function. The Script environment will create the necessary function within the object in which the script is being executed using the code specified. The following is an example of the Function XML element within an Application Profile for a function named
 * appendRecord with the two parameters "SFI" and "data". <Function Name="appendRecord" Param=��data, sfi��> <Script> <![CDATA[ this.sendApdu(0x00, 0xE2, 0x00, sfi << 3, data) ]]> </Script> </Function> Functions need to be available for every script defined in the ApplicationProfile in a Script element. Thus, they are a convenient place to specify vendor supplied functions common across two or more of the scripts defined for the ApplicationProfile.
 */
public class apFunction extends ProfileNode {
	private Logger log = Logger.getLogger(apFunction.class);
	/**
	 * Name of the function defined. The Script environment needs to create the function. The expected parameters are data typed by the characteristics of the Declaration and/or DataElement elements. Example: appendRecord
	 */
	public String Name;
	/**
	 * Parameters for the function in the order that they are required. Example: sfi, data
	 */
	public String Param;
	/**
	 * @see apDeclaration
	 */
	public apDeclaration Declaration[];
	/**
	 * @see apKey
	 */
	public apKey Key[];
	/**
	 * @see apScript
	 */
	public apScript Script;
	/**
	 * @see apDescription
	 */
	public apDescription Description;

	public apFunction(Node node) {
		super(node);
		if (node.hasAttributes()) {
			Node attr;
			NamedNodeMap nl = node.getAttributes();
			attr = nl.getNamedItem("Name");
			if (attr != null)
				Name = attr.getNodeValue();
			attr = nl.getNamedItem("Param");
			if (attr != null)
				Param = attr.getNodeValue();

		}

		int i;
		String xpString;
		NodeList nl;
		try {
			xpString = "Declaration";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0) {
				Declaration = new apDeclaration[nl.getLength()];
				for (i = 0; i < nl.getLength(); i++) {
					Declaration[i] = new apDeclaration(nl.item(i));
				}
			}

		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}
		try {

			xpString = "Key";
			nl = xPathNode.getNodeList(xpString, node);

			if (nl.getLength() > 0) {
				Key = new apKey[nl.getLength()];
				for (i = 0; i < nl.getLength(); i++) {
					Key[i] = new apKey(nl.item(i));
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}
		try {
			xpString = "Script";
			nl = xPathNode.getNodeList(xpString, node);

			if (nl.getLength() > 0) {
				Script = new apScript(nl.item(0));
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}

		try {
			xpString = "Description";
			nl = xPathNode.getNodeList(xpString, node);

			if (nl.getLength() > 0) {
				Description = new apDescription(nl.item(0));
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}

	}

}