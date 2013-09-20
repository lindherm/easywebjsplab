package com.gp.gpscript.profile.card;

import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gp.gpscript.profile.ProfileNode;
import com.gp.gpscript.profile.xPathNode;

//import com.watchdata.wdcams.loader.Loader;
/**
 * ApplicationInstance elements describe the state of a particular ApplicationProfile on the card. The presence of this element does not necessarily indicate that the application is installed, as the Lifecycle attribute must be inspected in order to determine this.
 */
public class cpApplicationInstance extends ProfileNode {
	private Logger log = Logger.getLogger(cpApplicationInstance.class);
	/**
	 * UniqueID of the ApplicationProfile that the application on card corresponds to. Varialbe length value up to 119 bytes in length (refer to Table 4-4) Example: 0x00010203040506070809
	 */
	public String ProfileID;
	/**

 */
	public String Label;
	/**
	 * ISO7816 [IS7816-5] Application Identifier of the application instance. Hexadecimal value. Example: 0xA123456789AB
	 */
	public String AID;
	/**
	 * The name of the lifecycle (from the application profile) that corresponds to the lifecycle state of the application instance.
	 */
	public String LifeCycle;
	/**
	 * For a GlobalPlatform card: AID of the Security Domain (SD) associated with this application. The value is based on: ��The SD with which the Load File for the application was installed ��The SD to which the application was extradited after installation When absent, the Issuer Security Domain is assumed to be the security domain for the above cases. For a non-GlobalPlatform card: Reference to the UniqueID of the associated Application Profile containing the secure channel scripts.
	 */
	public String SecurityDomain;
	/**
	 * Value of the volatile data space limit system specific installation parameter that was used in the install command for this application instance.
	 */
	public String VolatileDataSpaceLimit;
	/**
	 * Value of the non-volatile data space limit system specific installation parameter that was used in the install command for this application instance.
	 */
	public String NonVolatileDataSpaceLimit;
	/**

 */
	public String AppSpecificInstallParams;
	/**
	 * The value of the application specific installation parameters that was used in the install command. This value is for the data for tag 0xC9 and does not include the tag or length.
	 */
	public String Other;
	/**
	 * @see cpPrivileges
	 */
	public cpPrivileges Privileges;

	public cpApplicationInstance(Node node) {
		super(node);

		if (node.hasAttributes()) {
			Node attr;
			NamedNodeMap map = node.getAttributes();
			attr = map.getNamedItem("ProfileID");
			if (attr != null)
				ProfileID = attr.getNodeValue();
			attr = map.getNamedItem("Label");
			if (attr != null)
				Label = attr.getNodeValue();
			attr = map.getNamedItem("AID");
			if (attr != null)
				AID = attr.getNodeValue();
			attr = map.getNamedItem("LifeCycle");
			if (attr != null)
				LifeCycle = attr.getNodeValue();
			attr = map.getNamedItem("SecurityDomain");
			if (attr != null)
				SecurityDomain = attr.getNodeValue();
			attr = map.getNamedItem("VolatileDataSpaceLimit");
			if (attr != null)
				VolatileDataSpaceLimit = attr.getNodeValue();
			attr = map.getNamedItem("NonVolatileDataSpaceLimit");
			if (attr != null)
				NonVolatileDataSpaceLimit = attr.getNodeValue();
			attr = map.getNamedItem("AppSpecificInstallParams");
			if (attr != null)
				AppSpecificInstallParams = attr.getNodeValue();
			attr = map.getNamedItem("Other");
			if (attr != null)
				Other = attr.getNodeValue();

		}
		String xpString;
		NodeList nl;

		try {
			xpString = "Privileges";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0) {
				Privileges = new cpPrivileges(nl.item(0));

			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Privileges " + e.getMessage());
		}

	}
}
