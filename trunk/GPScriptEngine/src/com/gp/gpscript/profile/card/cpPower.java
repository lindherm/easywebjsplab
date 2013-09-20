package com.gp.gpscript.profile.card;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.gp.gpscript.profile.ProfileNode;

/**
 * Power contains a specific voltage value as an integer. Power support is typically expressed in volts (V). This is the Vcc value as defined in [IS7816-3]. This is an integer value. The V units symbol for volts does not need to be specified, as the Unit attribute indicates the voltage units applicable
 */
public class cpPower extends ProfileNode {
	/**
	 * Units of power, such as V for volts. Examples of valid values include V and mV.
	 */
	public String Unit;
	/**
	 * Power level supported.
	 */
	public String Value;

	public cpPower(Node node) {
		super(node);
		if (node.hasAttributes()) {
			Node attr;
			NamedNodeMap map = node.getAttributes();
			attr = map.getNamedItem("Unit");
			if (attr != null)
				Unit = attr.getNodeValue();
			attr = map.getNamedItem("Value");
			if (attr != null)
				Value = attr.getNodeValue();
		}
	}
}