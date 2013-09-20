package com.gp.gpscript.profile.app;
import  java.lang.*;
import org.w3c.dom.*;
import org.apache.log4j.Logger;
import org.apache.xalan.xpath.*;
import org.apache.xalan.xpath.xml.*;

import com.gp.gpscript.profile.*;
//import com.watchdata.wdcams.loader.Loader;
/**
valid only if application is a Security Domain
*/
public class apSecureChannel extends ProfileNode
{private Logger log = Logger.getLogger(apSecureChannel.class);
/**
Type of secure channel. Valid values are:
NONE SCP01 SCP02 OTHER
*/
public 		String SecureChannel;
/**
Secure channel option.
*/
public 		String Option;
/**
SecurityLevel supported by the secure
channel corresponds to Table E-10 in
GlobalPlatform Card Specification 2.1.
C_MAC_R_MAC_ENC
for a value of 0x13
C_MAC_R_MAC
for a value of 0x11
R_MAC
for a value of 0x10
C_MAC_ENC
for a value of 0x03
C_MAC
for a value of 0x01
NO_SECURITY_LEVEL
for a value of 0x00
*/
public 		String SecurityLevel;
/**
@see apOpenSecureChannel
*/
public		apOpenSecureChannel OpenSecureChannel = null;
/**
@see apCloseSecureChannel
*/
public		apCloseSecureChannel CloseSecureChannel = null;
/**
@see apWrap
*/
public		apWrap Wrap = null;
	public apSecureChannel(Node node)
	{	super(node);

			if(node.hasAttributes())
		{
			NamedNodeMap map=node.getAttributes();
			Node attr;
			attr=map.getNamedItem("SecureChannel");
			if(attr!=null)SecureChannel=attr.getNodeValue();
			attr=map.getNamedItem("Option");
			if(attr!=null)Option=attr.getNodeValue();
			attr=map.getNamedItem("SecurityLevel");
			if(attr!=null)SecurityLevel=attr.getNodeValue();

		}
		int i;
		String xpString;
		NodeList nl;

		try{
		xpString="OpenSecureChannel";
		nl=xPathNode.getNodeList(xpString,node);
		if(nl.getLength()>0)
		{
			OpenSecureChannel=new apOpenSecureChannel(nl.item(0));
		}

	}catch(Exception e)
	{
//        e.printStackTrace();
		log.error(e.getMessage());
	}
	try{

		xpString="CloseSecureChannel";
		nl=xPathNode.getNodeList(xpString,node);

		if(nl.getLength()>0)
		{
			CloseSecureChannel=new apCloseSecureChannel(nl.item(0));

		}
	}catch(Exception e)
	{
//        e.printStackTrace();
		log.error(e.getMessage());
	}
	try{
		xpString="Wrap";
		nl=xPathNode.getNodeList(xpString,node);

		if(nl.getLength()>0)
		{
			Wrap=new apWrap(nl.item(0));
		}
	}catch(Exception e)
	{
//        e.printStackTrace();
		log.error(e.getMessage());
	}

	}

}