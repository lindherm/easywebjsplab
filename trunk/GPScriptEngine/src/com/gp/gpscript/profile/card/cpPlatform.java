package com.gp.gpscript.profile.card;
import  java.lang.*;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;

/**
Platform elements are used to describe the operating system and platform resident on the smart card
*/
public class cpPlatform extends ProfileNode
{
/**
The operating system supported by the Card.
Examples include GP, MULTOS, etc.
Example: GP
*/
public		String Type;
/**
GP specific version information, if any. Could
be the same as Version if the platform is
bundled with the operating system
Example: 2.0.1
*/
public		String Version;
/**
Describes the type of platform supported by
the associated profile. Potential values are:
JAVA MULTOS W4SC OTHER
Example: W4SC
*/
public		String OSPlatform;
/**
Describes the version of the platform that the
Card Profile supports.
Example: 2.1.1
*/
public		String OSVersion;
/**
Party responsible for implementing the
platform for the smart card.
Example: IBM
*/
public		String Implementor;

	public cpPlatform(Node node)
	{	super(node);
		if(node.hasAttributes())
		{
			Node attr;
			NamedNodeMap map=node.getAttributes();
			attr=map.getNamedItem("Type");
			if(attr!=null)Type=attr.getNodeValue();
			attr=map.getNamedItem("Version");
			if(attr!=null)Version=attr.getNodeValue();
			attr=map.getNamedItem("OSPlatform");
			if(attr!=null)OSPlatform=attr.getNodeValue();
			attr=map.getNamedItem("OSVersion");
			if(attr!=null)OSVersion=attr.getNodeValue();
			attr=map.getNamedItem("Implementor");
			if(attr!=null)Implementor=attr.getNodeValue();

		}
	}
}