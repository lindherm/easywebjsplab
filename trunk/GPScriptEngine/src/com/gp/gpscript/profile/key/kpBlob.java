package com.gp.gpscript.profile.key;
import java.lang.*;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
/**
The Blob element is used to specify the format of the key value (or Blob) and the permanent key value, if desired.
The Blob element can represent any data, depending on the requirements of the implementing host system or the
creator of the key profile. Optionally, the value of the Blob could be specified in hexadecimal representation. If
the value of the key is provided, then this is the permanent value of the key.
Example:
<Blob BlobFormat=��NONE��>010101010202020203030304</Blob>
*/
public class kpBlob extends ProfileNode
{
/**
For keys with no discernable format, then
NONE should be specified for BlobFormat.
For example, DES keys would have a value
of NONE. However, for public or private
keys, the BlobFormat should be
representative of the format of the key value
(or Blob). OTHER specifies a format which is
vendor dependent.
Valid values are:
NONE ASN1
CRT_PRIVATE
OTHER
*/
 public	String BlobFormat;
/**
the permanent value of the key.
*/
 public	String Blob;

 public kpBlob(Node node)
  {	super(node);

 	if(node.hasAttributes())
 	{	Node attr;
	 	NamedNodeMap map=node.getAttributes();
		attr=map.getNamedItem("BlobFormat");
		if(attr!=null)BlobFormat=attr.getNodeValue();
	//	System.out.println(BlobFormat);
	}
  	if(node.hasChildNodes())
	{
		Blob=node.getFirstChild().getNodeValue();
	}
  }

}
