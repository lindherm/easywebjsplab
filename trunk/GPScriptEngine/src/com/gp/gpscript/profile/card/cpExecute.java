package com.gp.gpscript.profile.card;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.gp.gpscript.profile.ProfileNode;

/**
 * Execute elements describe application specific card resident data references used by a SCMS to identify information about the cardholder with which the SCMS will use to manage the unique cardholder��s smart card. The Execute element could be utilized in conjunction with the Card Profile to form a complete image of an unique cardholder��s smart card. If an Execute element is specified, a ScriptFragment could be supplied, the name of which is specified as an attribute of the Execute element, in the ApplicationProfile for the application to retrieve the required information from the smart card.
 */
public class cpExecute extends ProfileNode {
	/**
	 * Name of the data element defined in the Application Profile that is being used to manage the card..
	 */
	public String Name;
	/**
	 * Name of script fragment.
	 */
	public String ScriptName;

	public cpExecute(Node node) {
		super(node);
		if (node.hasAttributes()) {
			Node attr;
			NamedNodeMap map = node.getAttributes();
			attr = map.getNamedItem("Name");
			if (attr != null)
				Name = attr.getNodeValue();
			attr = map.getNamedItem("ScriptName");
			if (attr != null)
				ScriptName = attr.getNodeValue();

		}
	}
}