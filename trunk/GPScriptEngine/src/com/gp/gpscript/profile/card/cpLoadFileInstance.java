package com.gp.gpscript.profile.card;
import  java.lang.*;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;

/**
LoadFileInstance elements are used to describe Load Files that have been masked or loaded onto smart cards,
through the element��s attributes and via a reference to a Load File Profile.
*/
public class cpLoadFileInstance extends ProfileNode
{
/**
Unique ID attribute from the LoadFileProfile
element. This attribute refers to the
UniqueID attribute in Table 4-78 for the
LoadFileProfile.
Example: 0xDDDDDDDDDD0000000001
*/
public		String ProfileID;
/**
 Optional name assigned to the load file instance
*/
public		String Label;
/**
Specifies the current lifecycle state of this
Load File as defined by the Load File Profile.
Allowed states are:
LOADED DELETED
*/
public		String LifeCycle;
/**
Whether this Load File is masked onto the
smart card or not.
*/
public		String Masked;
/**
AID of the Security Domain the Load File
was loaded under. Hexadecimal in ASCII
representation.
Example: A123456789AB
*/
public		String SecurityDomainAID;
/**
Order that Load File was installed, starting
from a value of one. No two load file
instances should have the same Order attribute value.
*/
public		String Order;
/**
Value of the Non Volatile Code Space Limit
parameter used in the install command when
the Load File was installed onto the card.
*/
public		String NonVolatileCodeSpaceLimit;


	public cpLoadFileInstance(Node node)
	{	super(node);
		if(node.hasAttributes())
		{
			NamedNodeMap map=node.getAttributes();
			Node attr;
			attr=map.getNamedItem("ProfileID");
			if(attr!=null)ProfileID=attr.getNodeValue();
			attr=map.getNamedItem("Label");
			if(attr!=null)Label=attr.getNodeValue();
			attr=map.getNamedItem("LifeCycle");
			if(attr!=null)LifeCycle=attr.getNodeValue();
			attr=map.getNamedItem("Masked");
			if(attr!=null)Masked=attr.getNodeValue();
			attr=map.getNamedItem("SecurityDomainAID");
			if(attr!=null)SecurityDomainAID=attr.getNodeValue();
			attr=map.getNamedItem("Order");
			if(attr!=null)Order=attr.getNodeValue();
			attr=map.getNamedItem("NonVolatileCodeSpaceLimit");
			if(attr!=null)NonVolatileCodeSpaceLimit=attr.getNodeValue();

		}

	}
}
