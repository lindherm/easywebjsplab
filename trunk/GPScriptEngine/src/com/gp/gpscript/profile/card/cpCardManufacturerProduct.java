package com.gp.gpscript.profile.card;

import java.lang.*;

import org.apache.log4j.Logger;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;

/**
 * CardManufacturerProduct elements are used to describe the ��product�� from a
 * Card Manufacturer��s standpoint, namely the platform on the card and the chip
 * features. This is different from a Card Issuer��s viewpoint of ��product,��
 * which is the smart card with a portfolio of applications on it.
 */
public class cpCardManufacturerProduct extends ProfileNode {
	private Logger log = Logger.getLogger(cpCardManufacturerProduct.class);
	/**
	 * Name of the product given by the card Manufacturer. Example: Acme
	 * Corporation��s MegaCard
	 */
	public String Name;
	/**
	 * Version of the product assigned by the Card Manufacturer. No standard
	 * convention for version is specified for Card Manufacturer��s Product
	 * Example: 1.0.0
	 */
	public String Version;
	/**
	 * @see cpDescription
	 */
	public cpDescription Description;
	/**
	 * @see cpCardManufacturer
	 */
	public cpCardManufacturer CardManufacturer;
	/**
	 * @see cpChip
	 */
	public cpChip Chip;
	/**
	 * @see cpPlatform
	 */
	public cpPlatform Platform;

	public cpCardManufacturerProduct(Node node) {
		super(node);
		if (node.hasAttributes()) {
			Node attr;
			NamedNodeMap map = node.getAttributes();
			attr = map.getNamedItem("Name");
			if (attr != null)
				Name = attr.getNodeValue();
			attr = map.getNamedItem("Version");
			if (attr != null)
				Version = attr.getNodeValue();

		}

		NodeList nl;
		String xpString;
		try {
			xpString = "Description";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0)
				Description = new cpDescription(nl.item(0));
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Description " + e.getMessage());
		}

		
		try {
			xpString = "CardManufacturer";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0)
				CardManufacturer = new cpCardManufacturer(nl.item(0));
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("CardManufacturer " + e.getMessage());
		}

		try {
			xpString = "Chip";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0)
				Chip = new cpChip(nl.item(0));
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Chip " + e.getMessage());
		}

		try {
			xpString = "Platform";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0)
				Platform = new cpPlatform(nl.item(0));
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Platform " + e.getMessage());
		}

	}
}