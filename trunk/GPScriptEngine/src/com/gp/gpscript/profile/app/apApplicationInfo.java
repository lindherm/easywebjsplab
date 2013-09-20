package com.gp.gpscript.profile.app;

import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gp.gpscript.profile.ProfileNode;
import com.gp.gpscript.profile.xPathNode;

public class apApplicationInfo extends ProfileNode
{private static Logger log = Logger.getLogger(apApplicationInfo.class);
/**
Version of application conforming to GP
versioning specification. Version number is x.x.x
where each x is a decimal number. If the
version of the specification has no last x value,
then version number is x.x.0. Example: 1.0.0
*/
public	String Version;
/**Description of type of application.
Valid types are:
*
GP GlobalPlatform Application
*
MULTOS Multos Application
*
W4SC Microsoft W4SC
*
OTHER Other application type
*/
public	String Type;
/**
Additional type dependent subtype information
for the application.
Valid subtypes for GP are:
*
CM GP Card Manager
*
SD GP Security Domain
*
APP GP Compliant Application
*/
public	String SubType;
/**
Current owner of the application. Not
necessarily unique.
Example: ACME Bank
*/
public	String Owner;
/**
Name of the Application Developer. Not
necessarily unique.
Example: Bleinheim SoftwareWorks
*/
public	String Developer;
/**
Name of Application Provider who supplied the
application. Not necessarily unique.
Example: Bleinheim SoftwareWorks
*/
public	String Provider;
/**
Gives information about the exported functions
and what the application actually does (e.g. Card
Manager, Security Domain, or GSM application).
This is supplemental information to the Type
attribute in the ApplicationProfile element.
*/
public	String Domain;
/**
Same as the volatile data space limit. In bytes.
*/
public	String VolatileDataSpaceMin;
/**
Same as the non-volatile data space limit. In bytes.
*/
public	String NonVolatileDataSpaceMin;
/**
In bytes.
*/
public	String VolatileDataSpaceMax;
/**
In bytes.
*/
public	String NonVolatileDataSpaceMax;
/**
Application specific install parameters.
*/
public	String AppSpecificInstallParams;
/**
@see apPrivileges
*/
public	apPrivileges Privileges;
/**
@see apLifeCycles
*/
public	apLifeCycles LifeCycles;
/**
@see apCodes
*/
public	apCodes Codes;


public apApplicationInfo(Node node)
{
		super(node);

		if(node.hasAttributes())
		{
			NamedNodeMap map=node.getAttributes();
			Node attr;

			attr=map.getNamedItem("Version");
			if(attr!=null)Version=attr.getNodeValue();

			attr=map.getNamedItem("Type");
			if(attr!=null)Type=attr.getNodeValue();

			attr=map.getNamedItem("SubType");
			if(attr!=null)SubType=attr.getNodeValue();

			attr=map.getNamedItem("Owner");
			if(attr!=null)Owner=attr.getNodeValue();


			attr=map.getNamedItem("Developer");
			if(attr!=null)Developer=attr.getNodeValue();

			attr=map.getNamedItem("Provider");
			if(attr!=null)Provider=attr.getNodeValue();

			attr=map.getNamedItem("Domain");
			if(attr!=null)Domain=attr.getNodeValue();

			attr=map.getNamedItem("VolatileDataSpaceMin");
			if(attr!=null)VolatileDataSpaceMin=attr.getNodeValue();

			attr=map.getNamedItem("NonVolatileDataSpaceMin");
			if(attr!=null)NonVolatileDataSpaceMin=attr.getNodeValue();

			attr=map.getNamedItem("VolatileDataSpaceMax");
			if(attr!=null)VolatileDataSpaceMax=attr.getNodeValue();

			attr=map.getNamedItem("NonVolatileDataSpaceMax");
			if(attr!=null)NonVolatileDataSpaceMax=attr.getNodeValue();

			attr=map.getNamedItem("AppSpecificInstallParams");
			if(attr!=null)AppSpecificInstallParams=attr.getNodeValue();

		}


		String xpString;
		NodeList nl;
		try{
			xpString="Privileges";
			nl=xPathNode.getNodeList(xpString,node);
			if(nl.getLength()>0)
			{
				Privileges=new apPrivileges(nl.item(0));

			}
		}catch(Exception e)
		{
//            e.printStackTrace();
			log.error(e.getMessage());
		}

		try{
			xpString="LifeCycles";
			nl=xPathNode.getNodeList(xpString,node);
			if(nl.getLength()>0)
			{
				LifeCycles=new apLifeCycles(nl.item(0));

			}
		}catch(Exception e)
		{
//            e.printStackTrace();
            log.error(e.getMessage());
		}

		try{
			xpString="Codes";
			nl=xPathNode.getNodeList(xpString,node);
			if(nl.getLength()>0)
			{
				Codes=new apCodes(nl.item(0));

			}
		}catch(Exception e)
		{
//            e.printStackTrace();
            log.error(e.getMessage());
		}
	}


}



