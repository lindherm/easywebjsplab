package com.gp.gpscript.profile.card;

import java.lang.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.apache.log4j.Logger;
import org.apache.xalan.xpath.*;
import org.apache.xalan.xpath.xml.*;

import com.gp.gpscript.profile.*;
//import com.watchdata.wdcams.loader.Loader;
/**
CardProfile elements describe non-Cardholder specific features of a card. Card Manufacturers will provide them.
Each CardProfile contains a UniqueID. It is strongly recommended that this information be placed on GP 2.0.1
and GP 2.1 cards as tag 6 inside tag ��EE��.
*/
public class  CardProfile extends Profile
{private Logger log = Logger.getLogger(CardProfile.class);
/**
Variable length byte field consisting of
variable length OID (as assigned by ISO for
the Card Manufacturer and up to 119 bytes
long) and five-byte manufacturer unique field
for each of their Card Profiles. It is left up to
the Card Manufacturer to ensure that these
last five bytes are unique so that all
UniqueID��s for all the Card Profiles produced
by the Card Manufacturer assigned the OID
used are unique.
This information shall be placed on GP 2.0.1
and GP 2.1 cards as tag 6 inside tag'00' 'EE'
that can be retrieved using a GET DATA
APDU.
Once the Card Profile has been changed by
an actor other than the CardManufacturer,
the UniqueID attribute and Revisions
element must be updated to reflect this
modification.
Example: FFFFFFFFFF0000000002
Where FFFFFFFFFF is the OID of the Card
Manufacturer.
*/
public	String UniqueID;
/**
Version of profiles specification conforming
to GP versioning specification. Version
number is x.x.x where each x is a decimal
number. If the version of the specification
has no last x value, then version number is x.x.0.
Example: 1.0.0
*/
public	String ProfileVersion;
/**
@see cpDescription
*/
public	cpDescription Description;
/**
@see cpRevisions
*/
public	cpRevisions Revisions;
/**
@see cpCardManufacturerProduct
*/
public	cpCardManufacturerProduct CardManufacturerProduct;
/**
@see cpConflictRules
*/
public	cpConflictRules ConflictRules;
/**
@see cpLoadFileInstances
*/
public	cpLoadFileInstances	LoadFileInstances;
/**
@see cpApplicationInstances
*/
public	cpApplicationInstances ApplicationInstances;
/**
@see cpCardInfo
*/
public	cpCardInfo	CardInfo;

private Element currElement;
public CardProfile(String xmlFile)
{
	super(xmlFile);
	String xpString;
	NodeList nl;
	Node node;
	node=document.getDocumentElement();
	currElement=(Element)node;


	if(node.hasAttributes())
	{	Node attr;
		NamedNodeMap map=node.getAttributes();
		attr=map.getNamedItem("UniqueID");
		if(attr!=null)UniqueID=attr.getNodeValue();
		attr=map.getNamedItem("ProfileVersion");
		if(attr!=null)ProfileVersion=attr.getNodeValue();
	}

	try{
			xpString="Description";
			nl=xPathNode.getNodeList(xpString,node);
			if(nl.getLength()>0)
			{
				Description=new cpDescription(nl.item(0));
			}
		}catch(Exception e)
		{
//            e.printStackTrace();
			log.error("Description "+e.getMessage());
		}

	try{
			xpString="Revisions";
			nl=xPathNode.getNodeList(xpString,node);
			if(nl.getLength()>0)
			{
				Revisions=new cpRevisions(nl.item(0));
			}
		}catch(Exception e)
		{
//            e.printStackTrace();
			log.error("Revisions "+e.getMessage());
		}

	try{
			xpString="CardManufacturerProduct";
			nl=xPathNode.getNodeList(xpString,node);
			if(nl.getLength()>0)
			{
				CardManufacturerProduct=new cpCardManufacturerProduct(nl.item(0));
			}
		}catch(Exception e)
		{
//            e.printStackTrace();
			log.error("CardManufacturerProduct "+e.getMessage());
		}

	try{
			xpString="CardInfo";
			nl=xPathNode.getNodeList(xpString,node);
		if(nl.getLength()>0)
		{
			CardInfo=new cpCardInfo(nl.item(0));
		}
		}catch(Exception e)
		{
//            e.printStackTrace();
			log.error("CardInfo "+e.getMessage());
		}

	try{
		xpString="ConflictRules";
		nl=xPathNode.getNodeList(xpString,node);
		if(nl.getLength()>0)ConflictRules=new cpConflictRules(nl.item(0));
		}catch(Exception e)
		{
//            e.printStackTrace();
			log.error("Attribute "+e.getMessage());
		}

	try{
			xpString="LoadFileInstances";
			nl=xPathNode.getNodeList(xpString,node);
			if(nl.getLength()>0)LoadFileInstances=new cpLoadFileInstances(nl.item(0));
		}catch(Exception e)
		{
//            e.printStackTrace();
			log.error("LoadFileInstances "+e.getMessage());
		}

	try{
		xpString="ApplicationInstances";
		nl=xPathNode.getNodeList(xpString,node);
		if(nl.getLength()>0)ApplicationInstances=new cpApplicationInstances(nl.item(0));
		}catch(Exception e)
		{
//            e.printStackTrace();
			log.error("ApplicationInstances "+e.getMessage());
		}

}



 public String getNodeName()
{
	return(currElement.getNodeName());
}


public String getAtrribute(String attr)
{
	return(currElement.getAttribute(attr));
}

public void setAttribute(String attrName,String attr)
{
	currElement.setAttribute(attrName,attr);
}


public static void main(String args[])
{
// System.out.println("Start Program");
 CardProfile cp=new CardProfile(args[0]);

int r=cp.Revisions.Revision.length;
//for(int j=0;j<r;j++)

//System.out.println(cp.Revisions.Revision.length);
//System.out.println(cp.Revisions.Revision[0].Version);
//System.out.println(cp.Revisions.Revision[0].Date);

//System.out.println(cp.Revisions.Revision[0].Digest);
//System.out.println(cp.Revisions.Revision[0].Description.Description);

//tribute[0]=new cpAttribte();

}
}
