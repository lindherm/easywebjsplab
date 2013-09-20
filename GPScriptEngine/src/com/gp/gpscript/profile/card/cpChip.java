package com.gp.gpscript.profile.card;
import  java.lang.*;

import org.apache.log4j.Logger;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
//import com.watchdata.wdcams.loader.Loader;
/**
Chip elements are used to describe identification information and the physical features of the chip on a smart
card. This is different that the ChipRequirements element that is used in Application Profiles to describe the
requirements an application has for a chip.
*/
public class cpChip extends ProfileNode
{private Logger log = Logger.getLogger( cpChip.class);
/**
Non-unique identifier provided by the Card
Manufacturer for the Chip. The format is
specific to the Card Manufacturer.
Example: SuperChip 10000
*/
public		String Name;
/**
Manufacturer specific model identification.
Example: 10000
*/
public		String Model;
/**
Manufacturer specific version identification.
Example: 1.0.0
*/
public		String Version;
/**
Name of the manufacturer of the chip.
Example: Superchip Company
*/
public		String ChipManufacturer;
/**
@see cpDescription
*/
public		cpDescription Description;
/**
@see cpResources
*/
public		cpResources Resources;
/**
@see cpPowers
*/
public		cpPowers Powers;
/**
@see cpClock
*/
public		cpClock Clock;
/**
@see cpCommunication
*/
public		cpCommunication Communication;
/**
@see cpCryptoEngine
*/
public		cpCryptoEngine CryptoEngine;
	public cpChip(Node node)
	{	super(node);
		if(node.hasAttributes())
		{
			Node attr;
			NamedNodeMap map=node.getAttributes();
			attr=map.getNamedItem("Name");
			if(attr!=null) Name=attr.getNodeValue();
			attr=map.getNamedItem("Model");
			if(attr!=null)Model=attr.getNodeValue();
			attr=map.getNamedItem("Version");
			if(attr!=null)Version=attr.getNodeValue();
			attr=map.getNamedItem("ChipManufacturer");
			if(attr!=null)ChipManufacturer=attr.getNodeValue();
		}

			NodeList nl;
			String xpString;
		try{
			    xpString="Description";
				nl=xPathNode.getNodeList(xpString,node);
				if(nl.getLength()>0)Description=new cpDescription(nl.item(0));
			}catch(Exception e)
			{
//                e.printStackTrace();
				log.error("Description "+e.getMessage());
			}

		try{
			    xpString="Resources";
				nl=xPathNode.getNodeList(xpString,node);
				if(nl.getLength()>0)Resources=new cpResources(nl.item(0));
			}catch(Exception e)
			{
//                e.printStackTrace();
				log.error("Resources "+e.getMessage());
			}
		try{
			    xpString="Powers";
				nl=xPathNode.getNodeList(xpString,node);
				if(nl.getLength()>0)Powers=new cpPowers(nl.item(0));
			}catch(Exception e)
			{
//                e.printStackTrace();
				log.error("Powers "+e.getMessage());
			}
			try{
			    xpString="Clock";
				nl=xPathNode.getNodeList(xpString,node);
				if(nl.getLength()>0)Clock=new cpClock(nl.item(0));
			}catch(Exception e)
			{
//                e.printStackTrace();
				log.error("Clock "+e.getMessage());
			}
			try{
			    xpString="Communication";
				nl=xPathNode.getNodeList(xpString,node);
				if(nl.getLength()>0)Communication=new cpCommunication(nl.item(0));
			}catch(Exception e)
			{
//                e.printStackTrace();
				log.error("Communication "+e.getMessage());
			}
			try{
			    xpString="CryptoEngine";
				nl=xPathNode.getNodeList(xpString,node);
				if(nl.getLength()>0)CryptoEngine=new cpCryptoEngine(nl.item(0));
			}catch(Exception e)
			{
//                e.printStackTrace();
				log.error("CryptoEngine "+e.getMessage());
			}

	}
}