package com.gp.gpscript.profile.app;
import  java.lang.*;
import org.w3c.dom.*;

import org.apache.log4j.Logger;
import org.apache.xalan.xpath.*;
import org.apache.xalan.xpath.xml.*;

import com.gp.gpscript.profile.*;
//import com.watchdata.wdcams.loader.Loader;
/**
The OpenSecureChannel contains a script. The script operates on the object and has access to the keys and data
created for the Application Profile in the scripting environment. For example, if the Application Profile identifies
a GlobalPlatform Security Domain (Type = OP, Subtype = SD or CM), then the script is written for this object.
*/
public class apOpenSecureChannel extends ProfileNode
{private Logger log = Logger.getLogger(apOpenSecureChannel.class);
/**
Parameters for the secure channel (in the
order level, version, index).
*/
public		String Param;
/**
@see apDeclaration
*/
public		apDeclaration Declaration[];
/**
@see apKey
*/
public		apKey Key[];
/**
@see apScript
*/
public		apScript Script;
	public apOpenSecureChannel(Node node)
	{	super(node);
		if(node.hasAttributes())
		{
			Node attr;
			NamedNodeMap map=node.getAttributes();
			attr=map.getNamedItem("Param");
			if(attr!=null)Param=attr.getNodeValue();
		}
		int i;
		String xpString;
		NodeList nl;

		xpString="Declaration";
		try{
		nl=xPathNode.getNodeList(xpString,node);
		if(nl.getLength()>0)
		{
			Declaration=new apDeclaration[nl.getLength()];
			for(i=0;i<nl.getLength();i++)
			{
				Declaration[i]=new apDeclaration(nl.item(i));
			}
		}
			}catch(Exception e)
		{
//            e.printStackTrace();
			log.error(e.getMessage());
		}
		xpString="Key";
		try{
		nl=xPathNode.getNodeList(xpString,node);
		if(nl.getLength()>0)
		{
			Key=new apKey[nl.getLength()];
			for(i=0;i<nl.getLength();i++)
			{
				Key[i]=new apKey(nl.item(i));
			}
		}
				}catch(Exception e)
		{
//            e.printStackTrace();
			log.error(e.getMessage());
		}
		xpString="Script";
		try{
		nl=xPathNode.getNodeList(xpString,node);
		if(nl.getLength()>0)
		{
			Script=new apScript(nl.item(0));
		}

		}catch(Exception e)
		{
//            e.printStackTrace();
			log.error(e.getMessage());
		}
	}
}