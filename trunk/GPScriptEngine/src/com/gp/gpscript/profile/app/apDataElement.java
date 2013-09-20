package com.gp.gpscript.profile.app;
import  java.lang.*;

import org.apache.log4j.Logger;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
//import com.watchdata.wdcams.loader.Loader;
/**
DataElement elements are used to define parameter and data requirements of the associated scripts for
Application Profiles. Parameters and data can be specified as constants using the DataElement, or alternatively,
be used to describe parameters and data provided by external systems. In the latter case, SCMS and/or systems
integrators can use the DataElements defined for an Application Profile and the written documentation for the
specification explaining the data requirements to ensure correct data mapping occurs with databases and
parameters. It is also possible, in a XML Schema, to provide documentation for each of the Data Elements, in
which case, the application developer may choose not to supply separate documentation for this purpose.
*/
public class apDataElement extends ProfileNode
{private Logger log = Logger.getLogger(apDataElement.class);
/**
Name of the data element. This is the name
referenced in the scripts for a data element,
regardless of whether the data element is
external or not. The respective data
element object in the Script Environment is
created with this case-sensitive name.
Should be uniquely named across all
DataElements for the Application Profile. As
such, the name of the key should not contain
any whitespace and should be a valid
variable name in ECMAScript.
Example: CustomerName
*/
public		String Name;
/**
When this value is TRUE, specifies whether
this data element that must be populated
from an external data source before the
script is executed. When this value is
FALSE, the data element could be a
constant value or a variable used by the
script during script execution.
*/
public		String External;
/**
Valid values are: ByteString
*/
public		String Type;
/**
Valid values and their interpretation for each
type are:
ByteString Number Boolean
HEX HEX Base16 1 n/a
UTF8 UTF8 n/a n/a
ASCII ASCII Base10 2 true 3
false
0 or 1
BASE64 BASE64 n/a n/a
CN CN 5 n/a n/a
n/a = Not Applicable for Type
1 Base16 representation of the Number
2 Base10 representation of the Number (one byte per digit)
3 true or false
4 0 is false, 1 is true
5 Compact Numeric (one nibble per digit) �C
see [IS7811-6]
*/
public		String Encoding;
/**
Whether the data element is a fixed length or
a variable length field. The length is
governed by the Length attribute. Valid
values are:
FIX (true)
VAR (false)
Valid and required only for Type of
ByteString.
*/
public		String FixedLength;
/**
Maximum length of data element in bytes. If
LengthType of FIX is specified, then the field
will be of this length. Otherwise, the field
cannot be longer than this length. Valid and
required only for Type of ByteString.
*/
public		String Length;
/**
Values of constant data elements coded as
specified in Encoding.
Example: Fred
*/
public		String Value;
/**
Tag value of the data element when the data
element is used to refer to a value inside a
provided personalization data file. This is a
hexadecimal value.
Example: 3F
*/
public		String Tag;
/**
Encoding method for the Tag. Only valid if a
valid Tag for the encoding method is
specified. TagEncoding can take one of two
values:
BER BER-TLV encoding
DGI Data Group Identifier encoding
The TagEncoding attribute determines how
to create the Tlv object in the GP Scripting
environment.
*/
public		String TagEncoding;
/**
Whether the data element value for the card
holder for which the script is being executed
is read only or can the script write to the data
element as well. Useful for specifying
external data or parameters which need to
be updated during script execution for a
particular card holder. As opposed to the
Update flag, this updating of the data
element will be in real time and is expected
to create a data value for each cardholder.
*/
public		String ReadWrite;
/**
Whether the external environment should
update the value of the data element with the
value of the data element existing at the end
of one Script execution iteration. Useful for
specifying a data element value which will be
used by subsequent script execution or
exported for future usage.
*/
public		String Update;
/**
Whether the data element is optional at all.
If yes, then this field will be set to true. If no,
then the field will be set to false. Useful for
specifying data which may or may not be
required by the script.
*/
public		String Optional;
/**
Whether or not, if this flag is provided, audit
is required for this data element.
True means if audit is not possible, the Script
should fail
False means if audit is not possible, the
Script doesn��t fail
Note: if mandatoryAudit attribute is not
present, there is no audit.
*/
public		String MandatoryAudit;
/**
@see apField
*/
public		apField Field[];
	public apDataElement(Node node)
	{	super(node);
		if(node.hasAttributes())
		{
			NamedNodeMap map=node.getAttributes();
			Node attr;
			attr=map.getNamedItem("Name");
			if(attr!=null)Name=attr.getNodeValue();

			attr=map.getNamedItem("External");
			if(attr!=null)External=attr.getNodeValue();

			attr=map.getNamedItem("Type");
			if(attr!=null)Type=attr.getNodeValue();

			attr=map.getNamedItem("Encoding");
			if(attr!=null)Encoding=attr.getNodeValue();

			attr=map.getNamedItem("FixedLength");
			if(attr!=null)FixedLength=attr.getNodeValue();

			attr=map.getNamedItem("Length");
			if(attr!=null)Length=attr.getNodeValue();

			attr=map.getNamedItem("Value");
			if(attr!=null)Value=attr.getNodeValue();

			attr=map.getNamedItem("Tag");
			if(attr!=null)Tag=attr.getNodeValue();

			attr=map.getNamedItem("TagEncoding");
			if(attr!=null)TagEncoding=attr.getNodeValue();

			attr=map.getNamedItem("ReadWrite");
			if(attr!=null)ReadWrite=attr.getNodeValue();

			attr=map.getNamedItem("Update");
			if(attr!=null)Update=attr.getNodeValue();

			attr=map.getNamedItem("Optional");
			if(attr!=null)Optional=attr.getNodeValue();

			attr=map.getNamedItem("MandatoryAudit");
			if(attr!=null)MandatoryAudit=attr.getNodeValue();

		}


		try{
				String xpString="Field";
				NodeList nl=xPathNode.getNodeList(xpString,node);
				if(nl.getLength()>0)
				{
						Field=new apField[nl.getLength()];
						for(int i=0;i<nl.getLength();i++)
					{
						Field[i]=new apField(nl.item(i));
					}
				}
			}catch(Exception e)
			{
//                e.printStackTrace();
				log.error(e.getMessage());
			}
	}
}