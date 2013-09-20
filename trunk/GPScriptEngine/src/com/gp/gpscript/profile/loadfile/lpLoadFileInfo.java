package com.gp.gpscript.profile.loadfile;
import  java.lang.*;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
/**
LoadFile elements are used to describe the Load Files that may be loaded and installed onto a smart card. Each
Load File may contain references to one or more Load Modules, which in turn represent individual applications
contained within a Load File. Table 4-77 defines LoadFile. Alternatively, an application could be spread across
one or more load files, in which case, three is more than one Load File Profile associated with the Application
Profile.
*/
public class lpLoadFileInfo extends ProfileNode
{
/**
Name of the Filename reference to the
application load file. No directory or path
information is expected.
Example: EasyChipPay.cap
*/
public		String Filename;
/**
GP standard AID for the Load File.
Hexadecimal value.
Example: 0xA123456789AB
*/
public		String AID;
/**
Version of load file conforming to GP
versioning specification. Version number is
x.x.x where each x is a decimal number. If
the version of the specification has no last x
value, then version number is x.x.0.
Example: 1.0.0
*/
public		String Version;
/**
Amount of non-volatile code space in bytes
occupied by the Load File once on the smart
card. This is an upper limit to be used for
card configuration purposes. Integer.
Example: 1024
*/
public		String NonVolatileCodeSpaceLimit;
/**
Minimum non-volatile code space requirements.In bytes
*/
public		String NonVolatileCodeSpaceMin;
/**
Minimum volatile data space requirements in bytes.
*/
public		String VolatileDataSpaceMin;

	public lpLoadFileInfo(Node node)
	{	super(node);
		if(node.hasAttributes())
		{
			Node attr;
			NamedNodeMap map=node.getAttributes();
			attr=map.getNamedItem("Filename");
			if(attr!=null)Filename=attr.getNodeValue();
			attr=map.getNamedItem("AID");
			if(attr!=null)AID=attr.getNodeValue();
			attr=map.getNamedItem("Version");
			if(attr!=null)Version=attr.getNodeValue();
			attr=map.getNamedItem("NonVolatileCodeSpaceLimit");
			if(attr!=null)NonVolatileCodeSpaceLimit=attr.getNodeValue();
			attr=map.getNamedItem("NonVolatileCodeSpaceMin");
			if(attr!=null)NonVolatileCodeSpaceMin=attr.getNodeValue();
			attr=map.getNamedItem("VolatileDataSpaceMin");
			if(attr!=null)VolatileDataSpaceMin=attr.getNodeValue();

		}
	}
}