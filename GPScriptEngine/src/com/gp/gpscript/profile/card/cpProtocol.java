package com.gp.gpscript.profile.card;
import  java.lang.*;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
/**
Protocol support offered by the chip
*/
public class cpProtocol extends ProfileNode
{
/**
Protocol supported. Valid values are:
T0 T1 T14 TYPEA TYPEB OTHER
*/
public		String Type;
/**
Size of maximum APDU size supported. In bytes
*/
public		String MaxAPDULen;

	public cpProtocol(Node node)
	{	super(node);
		if(node.hasAttributes())
		{
			Node attr;
			NamedNodeMap map=node.getAttributes();
			attr=map.getNamedItem("Type");
			if(attr!=null)Type=attr.getNodeValue();
			attr=map.getNamedItem("MaxAPDULen");
			if(attr!=null)MaxAPDULen=attr.getNodeValue();
		}
	}
}