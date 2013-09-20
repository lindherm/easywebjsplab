package com.gp.gpscript.profile.card;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.gp.gpscript.profile.ProfileNode;

/**
 * Specifies the memory resources originally present, prior to the loading or installation of any applications, on the smart card. If no Unit attribute is specified, it is assumed that the memory attributes are provided in bytes.
 */
public class cpResources extends ProfileNode {
	/**
	 * Unit used for the resources, such as bytes.
	 */
	public String Unit;
	/**
	 * Total Read Only Memory (ROM) present on the smart card. Example: 0
	 */
	public String ROM;
	/**
	 * Total Random Access Memory (RAM) present on the smart card. Example: 384
	 */
	public String RAM;
	/**
	 * Total EEPROM present on the smart card Example: 384
	 */
	public String EEPROM;
	/**
	 * Total Flash memory present on the smart card. Example: 0
	 */
	public String Flash;
	/**
	 * Whether some form of garbage collection is supported. Attribute necessary only if some form of garbage collection is required. Default is false if no attribute is provided. Example: true
	 */
	public String GarbageCollection;

	public cpResources(Node node) {
		super(node);
		if (node.hasAttributes()) {
			Node attr;
			NamedNodeMap map = node.getAttributes();
			attr = map.getNamedItem("Unit");
			if (attr != null)
				Unit = attr.getNodeValue();
			attr = map.getNamedItem("ROM");
			if (attr != null)
				ROM = attr.getNodeValue();
			attr = map.getNamedItem("RAM");
			if (attr != null)
				RAM = attr.getNodeValue();
			attr = map.getNamedItem("EEPROM");
			if (attr != null)
				EEPROM = attr.getNodeValue();
			attr = map.getNamedItem("Flash");
			if (attr != null)
				Flash = attr.getNodeValue();
			attr = map.getNamedItem("GarbageCollection");
			if (attr != null)
				GarbageCollection = attr.getNodeValue();

		}
	}
}