package com.gp.gpscript.profile.app;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.gp.gpscript.profile.ProfileNode;

/**
 * Field elements are used to provide additional information about discrete length fields embedded within data described by a DataElement element. The fields inherit all the characteristics of the DataElement not specified as an attribute within the Field element. Field elements are only valid if the parent DataElement element has a Type attribute of BYTESTRING.
 */
public class apField extends ProfileNode {
	/**
	 * Name of the field within the DataElement. Should be uniquely named amongst all Field elements for the DataElement.
	 */
	public String Name;
	/**
	 * Valid values are: ByteString Number Boolean
	 */
	public String Type;
	/**
	 * Zero offset start position of the field within the DataElement. Example: An Offset attribute with a value of 0 means that the field starts from the leftmost byte of the DataElement.
	 */
	public String Offset;
	/**
	 * Length from and including the Offset position in bytes.
	 */
	public String Length;

	public apField(Node node) {
		super(node);
		if (node.hasAttributes()) {
			NamedNodeMap map = node.getAttributes();
			Node attr;
			attr = map.getNamedItem("Name");
			if (attr != null)
				Name = attr.getNodeValue();
			attr = map.getNamedItem("Type");
			if (attr != null)
				Type = attr.getNodeValue();
			attr = map.getNamedItem("Offset");
			if (attr != null)
				Offset = attr.getNodeValue();
			attr = map.getNamedItem("Length");
			if (attr != null)
				Length = attr.getNodeValue();
		}

	}

}
