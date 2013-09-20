package com.gp.gpscript.profile.key;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.gp.gpscript.profile.ProfileNode;

/**
 * General informantion which a key which references the Key Profile inherits
 */
public class kpKeyInfo extends ProfileNode {
	/**
	 * Valid values: SECRET (symmetric) PRIVATE (asymmetric) PUBLIC (asymmetric) RFU
	 */
	public String Type;
	/**
	 * Open field reserved for future use.
	 */
	public String SubType;
	/**
	 * Size in bits, including parity bits.
	 */
	public String Size;
	/**
	 * Exponent for RSA keys. Not valid for non-RSA keys.
	 */
	public String Exponent;
	/**
	 * Owner of the Key Profile.
	 */
	public String Owner;
	/**
	 * Version of the key.
	 */
	public String Version;
	/**
	 * Start date for the Key Profile in the format YYYYMMDD.
	 */
	public String StartDate;
	/**
	 * End date for the Key Profile in the format YYYYMMDD.
	 */
	public String EndDate;

	public kpKeyInfo(Node node) {
		super(node);
		if (node.hasAttributes()) {
			NamedNodeMap map = node.getAttributes();
			Node attr;
			attr = map.getNamedItem("Type");
			if (attr != null)
				Type = attr.getNodeValue();

			attr = map.getNamedItem("SubType");
			if (attr != null)
				SubType = attr.getNodeValue();

			attr = map.getNamedItem("Size");
			if (attr != null)
				Size = attr.getNodeValue();

			attr = map.getNamedItem("Exponent");
			if (attr != null)
				Exponent = attr.getNodeValue();

			attr = map.getNamedItem("Owner");
			if (attr != null)
				Owner = attr.getNodeValue();

			attr = map.getNamedItem("Version");
			if (attr != null)
				Version = attr.getNodeValue();

			attr = map.getNamedItem("StartDate");
			if (attr != null)
				StartDate = attr.getNodeValue();

			attr = map.getNamedItem("EndDate");
			if (attr != null)
				EndDate = attr.getNodeValue();
			// System.out.println("keyInfo ok");

		}
	}
}
