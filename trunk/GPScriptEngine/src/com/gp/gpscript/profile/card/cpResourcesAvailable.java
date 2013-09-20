package com.gp.gpscript.profile.card;
import  java.lang.*;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
/**
ResourcesAvailable refer to the amount of space remaining on the specific smart card referred to.
*/
public class cpResourcesAvailable extends ProfileNode
{
/**
Units used for the resources, such as bytes.
*/
public		String Unit;
/**
RAM available on the smart card after taking
into account the RAM consumption of the
applications and load files specified
elsewhere in the Card Profile.
Example: 384
*/
public		String RAM;
/**
EEPROM available on the smart card after
taking into account the EEPROM
consumption of the applications and load files
specified elsewhere in the Card Profile.
Example: 384
*/
public		String EEPROM;
/**
Flash memory in bytes available on the smart
card after taking into account the flash
memory consumption of the applications and
load files specified elsewhere in the Card Profile
Example: 0
*/
public		String Flash;

	public cpResourcesAvailable(Node node)
	{	super(node);
		if(node.hasAttributes())
		{
			Node attr;
			NamedNodeMap map=node.getAttributes();
			attr=map.getNamedItem("Unit");
			if(attr!=null)Unit=attr.getNodeValue();
			attr=map.getNamedItem("RAM");
			if(attr!=null)RAM=attr.getNodeValue();
			attr=map.getNamedItem("EEPROM");
			if(attr!=null)EEPROM=attr.getNodeValue();
			attr=map.getNamedItem("Flash");
			if(attr!=null)Flash=attr.getNodeValue();

		}
	}
}