package com.gp.gpscript.profile.card;

import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gp.gpscript.profile.ProfileNode;
import com.gp.gpscript.profile.xPathNode;

//import com.watchdata.wdcams.loader.Loader;
/**
 * Features specific to the ��contactless�� conmponent of a smart card. If the Unit attribute is unspecified, assume that BaudRateMin and BaudRateMax are specified in bits per second (bps).
 */
public class cpContactless extends ProfileNode {
	private Logger log = Logger.getLogger(cpContactless.class);
	/**
	 * Units for the baud rate. An example of a valid value would be bps.
	 */
	public String Unit;
	/**
	 * Minimum baud rate supported in bps, if no Unit is specified Example: 1200
	 */
	public String BaudRateMin;
	/**
	 * Maximum baud rate supported in bps., if no Unit is specified. Example: 9600
	 */
	public String BaudRateMax;
	/**
	 * @see cpProtocols
	 */
	public cpProtocols Protocols;

	public cpContactless(Node node) {
		super(node);
		if (node.hasAttributes()) {
			Node attr;
			NamedNodeMap map = node.getAttributes();

			attr = map.getNamedItem("Unit");
			if (attr != null)
				Unit = attr.getNodeValue();
			attr = map.getNamedItem("BaudRateMin");
			if (attr != null)
				BaudRateMin = attr.getNodeValue();
			attr = map.getNamedItem("BaudRateMax");
			if (attr != null)
				BaudRateMax = attr.getNodeValue();

		}
		try {
			String xpString = "Protocols";
			NodeList nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0)
				Protocols = new cpProtocols(nl.item(0));
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Protocols");
		}
	}
}