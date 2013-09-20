package com.gp.gpscript.profile.app;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.gp.gpscript.profile.ProfileNode;

/**
 * Declarations are used to specify the specific data requirements for a script. Any declaration��s Name attribute must match up with an identically named DataElement defined at the ApplicationProfile.DataElement branch of the XML document. It is possible to override certain attributes, with different values.
 */
public class apDeclaration extends ProfileNode {
	/**
	 * Name of the data element. This is the name referenced by the Scripts associated with an Application Profile. Any declaration is only valid if there exists a matching DataElement in the ApplicationProfile (at the ApplicationProfile.DataElement branch of the document). Example: CustomerName
	 */
	public String Name;
	/**
	 * When this value is TRUE, specifies whether this data element that must be populated from an external data source before the script is executed. When this value is FALSE, the data element could be a constant value or a variable used by the script during script execution. Example: true
	 */
	public String External;

	/**
	 * Valid values and their interpretation for each type are: ByteString Number Boolean HEX HEX Base16 1 n/a UTF8 UTF8 n/a n/a ASCII ASCII Base10 2 true 3 false 0 or 1 4 BASE64 BASE64 n/a n/a CN CN 5 n/a n/a n/a = Not Applicable for Type 1 Base16 representation of the Number 2 Base10 representation of the Number 3 true or false 4 0 is false, 1 is true 5 Compact Numeric (one nibble per digit) �C see [IS7811-6]
	 */

	public String Encoding;
	/**
	 * Values of constant data elements coded as specified in Encoding. This replaces any definition in the matching DataElement for the Value attribute. Example: Fred
	 */
	public String Value;
	/**
	 * Whether the data element value for the card holder for which the script is being executed is read only or can the script write to the data element as well. Useful for specifying external data or parameters which need to be updated during script execution for a particular card holder. As opposed to the Update flag, this updating of the data element will be in real time and is expected to create a data value for each cardholder. This replaces any definition in the matching DataElement for the ReadWrite attribute.
	 */
	public String ReadWrite;
	/**
	 * Whether the external environment should update the value of the data element with the value of the data element existing at the end of one Script execution iteration. Useful for specifying a data element value which will be used by subsequent script execution This replaces any definition in the matching DataElement for the Update attribute.
	 */
	public String Update;
	/**
	 * Whether the data element is optional at all? If yes, then this field will be set to true. If no, then the field will be set to false. Useful for specifying data which may or may not be required by the script. This replaces any definition in the matching DataElement for the Required attribute.
	 */
	public String Optional;
	/**
	 * MandatoryAudit Whether or not, if this flag is provided, audit is required for this data element. True means if audit is not possible, the Script should fail False means if audit is not possible, the Script doesn��t fail Note: if mandatoryAudit attribute is not present, there is no audit.
	 */
	public String MandatoryAudit;

	public apDeclaration(Node node) {
		super(node);
		if (node.hasAttributes()) {
			NamedNodeMap map = node.getAttributes();
			Node attr;
			attr = map.getNamedItem("Name");
			if (attr != null)
				Name = attr.getNodeValue();

			attr = map.getNamedItem("External");
			if (attr != null)
				External = attr.getNodeValue();

			attr = map.getNamedItem("Encoding");
			if (attr != null)
				Encoding = attr.getNodeValue();

			attr = map.getNamedItem("Value");
			if (attr != null)
				Value = attr.getNodeValue();

			attr = map.getNamedItem("ReadWrite");
			if (attr != null)
				ReadWrite = attr.getNodeValue();

			attr = map.getNamedItem("Update");
			if (attr != null)
				Update = attr.getNodeValue();

			attr = map.getNamedItem("Optional");
			if (attr != null)
				Optional = attr.getNodeValue();

			attr = map.getNamedItem("MandatoryAudit");
			if (attr != null)
				MandatoryAudit = attr.getNodeValue();

		}
	}
}