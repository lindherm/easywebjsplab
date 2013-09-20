package com.gp.gpscript.profile.card;
import  java.lang.*;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
/**
An element describing the range of clock speed support for the smart card. If the unit is unspecified, the clock
speed minimum and maximum are assumed to be express in megahertz (MHz).
*/
public class cpClock extends ProfileNode
{
/**
Units for the clock, such as MHz. Examples
of valid values include Hz, KHz, and MHz.
*/
public		String Unit;
/**
Minimum clock speed supported in the units
specified by Unit
*/
public		String Min;
/**
Maximum clock speed supported in the units
specified by Unit
*/
public		String Max;
	public cpClock(Node node)
	{	super(node);
		if(node.hasAttributes())
		{
			Node attr;
			NamedNodeMap map=node.getAttributes();
			attr=map.getNamedItem("Unit");
			if(attr!=null)Unit=attr.getNodeValue();
			attr=map.getNamedItem("Min");
			if(attr!=null)Min=attr.getNodeValue();
			attr=map.getNamedItem("Max");
			if(attr!=null)Max=attr.getNodeValue();

		}
	}
}