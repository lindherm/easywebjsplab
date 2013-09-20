package com.gp.gpscript.profile.app;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gp.gpscript.profile.ProfileNode;
import com.gp.gpscript.profile.xPathNode;

//import com.watchdata.wdcams.loader.Loader;
/**
 * ScriptFragment elements are used to describe the script code for a particular task associated with an Application Profile. There needs to be one Script for each stage. Scripts will use data elements as described in an ApplicationProfile��s DataElement elements, and keys as described in the ApplicationProfile��s Key elements. The Declaration child element will instruct which attributes and data elements defined in the Application Profiles that the Script operates with which need to be deserialized at a minimum in the Script execution environment. Likewise, the Key child element will do the same for the keys that are required at a minimum in the Script execution environment for the Script in question.
 */
public class apScriptFragment extends ProfileNode {
	private Logger log = Logger.getLogger(apScriptFragment.class);
	/**
	 * Application specific name for the script fragment.Application specific script fragment names are not defined here. The use of application specific Scripts should be defined in the the written documentation for the application. Each script name should be unique within the profile. This is an ASCII representation of the name.
	 */
	public String Name;
	/**
	 * Corresponds to the Name attribute of the LifeCycle element for the state of the application expected prior to executing the script code. For a data preparation script, for example: PRE_PERSOPREP State must be in PRE_PERSOPREP before executing this script For a script to make an application instance selectable: INSTALLED State of the application must be in INSTALLED before executing this script
	 */
	public String StartLifeCycle;
	/**
	 * Corresponds to the Name attribute of the LifeCycle element for the state of the application expected after executing the script code succesfully. For a data preparation script, for example: POST_PERSOPREP State must be in POST_PERSOPREP after executing this script For a script to make an application instance selectable: SELECTABLE State of the application must be in SELECTABLE after executing this script
	 */
	public String EndLifeCycle;
	/**
	 * For secure channel scripts, type of secure channel required by the script fragment . Valid values are: NONE SCP01 SCP02 OTHER
	 */
	public String SecureChannel;
	/**
	 * Secure channel security level required. SecurityLevel corresponds to Table E-10 in GlobalPlatform Card Specification 2.1. C_MAC_R_MAC_ENC for a value of 0x13 C_MAC_R_MAC for a value of 0x11 R_MAC for a value of 0x10 C_MAC_ENC for a value of 0x03 C_MAC for a value of 0x01 NO_SECURITY_LEVEL for a value of 0x00
	 */
	public String SecurityLevel;
	/**
	 * Active Indicates whether the Script Fragment should be used.
	 */
	public String Active;

	/**
	 * @see apDescription
	 */
	public apDescription Description;
	/**
	 * @see apConflictRules
	 */
	public apConflictRules ConflictRules;
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

	private Node currNode;

	public apScriptFragment(Node node) {
		super(node);
		currNode = node;
		if (node.hasAttributes()) {
			NamedNodeMap map = node.getAttributes();
			Node attr;
			attr = map.getNamedItem("Name");
			if (attr != null)
				Name = attr.getNodeValue().toString();
			attr = map.getNamedItem("StartLifeCycle");
			if (attr != null)
				StartLifeCycle = attr.getNodeValue().toString();
			attr = map.getNamedItem("EndLifeCycle");
			if (attr != null)
				EndLifeCycle = attr.getNodeValue().toString();
			attr = map.getNamedItem("SecureChannel");
			if (attr != null)
				SecureChannel = attr.getNodeValue().toString();
			attr = map.getNamedItem("SecurityLevel");
			if (attr != null)
				SecurityLevel = attr.getNodeValue().toString();
			attr = map.getNamedItem("Active");
			if (attr != null)
				Active = attr.getNodeValue().toString();

		}
		int i;
		String xpString;
		NodeList nl;

		xpString = "Declaration";
		try {
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
			xpString = "Description";
			nl = xPathNode.getNodeList(xpString, node);

			if (nl.getLength() > 0) {
				Description = new apDescription(nl.item(0));
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
			xpString = "ConflictRules";
			nl = xPathNode.getNodeList(xpString, node);

			if (nl.getLength() > 0) {
				ConflictRules = new apConflictRules(nl.item(0));
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}

	}

	/**
	 * add Declaration into ScriptFragment
	 * 
	 * @param name
	 *            name of Declaration
	 * @see apDeclaration
	 */
	public void addDeclaration(String name) {
		String xpath = "Declaration";
		try {
			NodeList list = xPathNode.getNodeList(xpath, currNode);
			if (list.getLength() > 0) {
				Element declaration = (Element) list.item(0).cloneNode(true);
				declaration.setAttribute("Name", name);
				Node nextNode = list.item(list.getLength() - 1).getNextSibling();
				if (nextNode != null) {
					currNode.insertBefore(declaration, nextNode);
				} else {
					currNode.appendChild(declaration);
				}

			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}

	}

	/**
	 * Delete Declaration from ScriptFragment
	 * 
	 * @param name
	 *            name of Declaration
	 * @see apDeclaration
	 */
	public void deleteDeclaration(String name) {
		String xpath = "Declaration[@Name='" + name + "']";

		try {
			NodeList list = xPathNode.getNodeList(xpath, currNode);
			if (list.getLength() > 0) {
				currNode.removeChild(list.item(0));
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	/**
	 * Add Key into ScriptFragment
	 * 
	 * @param name
	 *            ,Name of Key
	 * @profileID ,ProfileID of Key
	 * @see apKey
	 */
	public void addKey(String name, String profileID) {
		String xpath = "Key";

		try {
			NodeList list = xPathNode.getNodeList(xpath, currNode);
			if (list.getLength() > 0) {
				Element key = (Element) list.item(0).cloneNode(true);
				key.setAttribute("Name", name);
				key.setAttribute("ProfileID", profileID);
				Node nextNode = list.item(list.getLength() - 1).getNextSibling();
				if (nextNode != null) {
					currNode.insertBefore(key, nextNode);
				} else {
					currNode.appendChild(key);
				}

			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	/**
	 * Delete a Key of ScriptFragment the Key specifed by Name
	 * 
	 * @param name
	 *            Name of Key
	 * @see apKey
	 */
	public void deleteKey(String name) {
		String xpath = "Key[@Name='" + name + "']";

		try {
			NodeList list = xPathNode.getNodeList(xpath, currNode);
			if (list.getLength() > 0) {
				currNode.removeChild(list.item(0));
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}
	}

}