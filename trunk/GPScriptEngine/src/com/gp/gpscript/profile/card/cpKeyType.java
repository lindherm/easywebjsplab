package com.gp.gpscript.profile.card;
import  java.lang.*;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;

/**
KeyType is used to represent a key supported by the smart cards cryptographic engine.
*/
public class cpKeyType extends ProfileNode
{
/**
Name of the key type as specified by the
operating system or platform specification.
*/
public		String Name;
/**
Maximum key length expressed as a number
of bits. For DES keys, the number of bits
includes partiy bits. For RSA or DSA keys,
the number of bits indicates the size of the
public modulus.
*/
public		String MaxLength;
	public cpKeyType(Node node)
	{	super(node);
		if(node.hasAttributes())
		{
			Node attr;
			NamedNodeMap map=node.getAttributes();
			attr=map.getNamedItem("Name");
			if(attr!=null)Name=attr.getNodeValue();
			attr=map.getNamedItem("MaxLength");
			if(attr!=null)MaxLength=attr.getNodeValue();

		}
	}
}