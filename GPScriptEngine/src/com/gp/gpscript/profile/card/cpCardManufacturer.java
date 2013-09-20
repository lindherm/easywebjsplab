package com.gp.gpscript.profile.card;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.gp.gpscript.profile.ProfileNode;

/**
 * CardManufacturer elements are used to provide information, including PlasticStockID, from the Card Manufacturer.
 */
public class cpCardManufacturer extends ProfileNode {
	/**
	 * Name of the card Manufacturer. Example: ACMECard
	 */
	public String Name;
	/**
	 * Manufacturer specific stock ID from which the specified card product is made from. Example: StockID123456
	 */
	public String PlasticStockID;

	public cpCardManufacturer(Node node) {
		super(node);

		if (node.hasAttributes()) {
			Node attr;
			NamedNodeMap map = node.getAttributes();
			attr = map.getNamedItem("Name");
			if (attr != null)
				Name = attr.getNodeValue();
			attr = map.getNamedItem("PlasticStockID");
			if (attr != null)
				PlasticStockID = attr.getNodeValue();
		}

	}
}
