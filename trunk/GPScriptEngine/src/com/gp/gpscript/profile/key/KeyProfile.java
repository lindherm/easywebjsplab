package com.gp.gpscript.profile.key;

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
public class  KeyProfile extends Profile
{private Logger log = Logger.getLogger(KeyProfile.class);
/**
Variable length OID (up to 119 bytes)
assigned to Key Profile owner + 5 byte
value assigned by Key Profile owner. This
unique ID field must be unique amongst all
UniqueID attributes for the LoadFile
element for all Key Profiles supplied by the
Key Profile owner who has been assigned
the OID used.
Example: 1111110506070809
Where 111111 is the OID of the Key
Profile owner.
*/
public	String UniqueID;
/**
Version of profiles specification conforming
to GP versioning specification. Version
number is x.x.x where each x is a decimal
number. If the version of the specification
has no last x value, then version number is
x.x.0.Example: 1.0.0
*/
public	String ProfileVersion;

/**
@see kpBlob
*/
public	kpDescription Description;
/**
@see kpBlob
*/
public	kpRevisions Revisions;
/**
@see kpBlob
*/
public	kpKeyInfo KeyInfo;
/**
@see kpBlob
*/
public	kpAttribute Attribute;
/**
@see kpBlob
*/
public	kpUsage Usage;
/**
@see kpBlob
*/
public	kpBlob Blob;

private Element currElement;
public KeyProfile(String xmlFile)
{
	super(xmlFile);
	String xpString;
	NodeList nl;
	Node node;
	node=(Node)document.getDocumentElement();
	currElement=(Element)node;
	if(node.hasAttributes())
	{	Node attr;
		NamedNodeMap map;
		map=node.getAttributes();
		attr=map.getNamedItem("UniqueID");
		if(attr!=null)UniqueID=attr.getNodeValue();
		attr=map.getNamedItem("ProfileVersion");
		if(attr!=null)ProfileVersion=attr.getNodeValue();
	}



	try{
			xpString="Revisions";
			nl=xPathNode.getNodeList(xpString,node);
//			System.out.println(nl.getLength());
	//		System.out.println(nl.item(0).toString());

			if(nl.getLength()>0)
			{
				Revisions=new kpRevisions(nl.item(0));

			}
		}catch(Exception e)
		{
//            e.printStackTrace();
			log.error("Revisions "+e.getMessage());
		}

	try{
		xpString="KeyInfo";
		nl=xPathNode.getNodeList(xpString,node);
//			System.out.println(nl.getLength());
	//		System.out.println(nl.item(0).toString());

		if(nl.getLength()>0)
		{

			KeyInfo=new kpKeyInfo(nl.item(0));

		}
		}catch(Exception e)
		{
//            e.printStackTrace();
			log.error("KeyInfo "+e.getMessage());
		}

	try{
		xpString="Attribute";
		nl=xPathNode.getNodeList(xpString,node);
//			System.out.println(nl.getLength());
//			System.out.println(nl.item(0).toString());

		if(nl.getLength()>0)
		{
			Attribute=new kpAttribute(nl.item(0));

		}
		}catch(Exception e)
		{
//            e.printStackTrace();
			log.error("Attribute "+e);
		}

	try{
		xpString="Usage";
		nl=xPathNode.getNodeList(xpString,node);
//			System.out.println(nl.getLength());
//			System.out.println(nl.item(0).toString());
		if(nl.getLength()>0)
		{
			Usage=new kpUsage(nl.item(0));

		}
		}catch(Exception e)
		{
//            e.printStackTrace();
            log.error("Usage "+e);
		}

	try{
		xpString="Blob";
		nl=xPathNode.getNodeList(xpString,node);
//			System.out.println(nl.getLength());
//			System.out.println(nl.item(0).toString());
		if(nl.getLength()>0)
		{
			Blob=new kpBlob(nl.item(0));

		}
		}catch(Exception e)
		{
            e.printStackTrace();
            log.error("Blob "+e.getMessage());	
		}

	try{
		xpString="Description";
		nl=xPathNode.getNodeList(xpString,node);
//			System.out.println(nl.getLength());
//			System.out.println(nl.item(0).toString());
		if(nl.getLength()>0)
		{
			Description=new kpDescription(nl.item(0));

		}
		}catch(Exception e)
		{
//            e.printStackTrace();
			log.error("Description "+e);
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
// System.out.println("Start Program Key profile");
// System.out.println(args[0]);
 KeyProfile kp=new KeyProfile(args[0]);
// System.out.println(kp.ErrorCode);
 //System.out.println(kp.UniqueID);
 //System.out.println(kp.ProfileVersion);
 //System.out.println(kp.KeyInfo.Version);
// System.out.println(kp.Blob.BlobFormat);
// System.out.println(kp.Attribute.Permanent);


/* for(int i=0;i<kp.Revisions.Revision.length;i++)
 {
 	 System.out.println(kp.Revisions.Revision[i].Version);
 	System.out.println(kp.Revisions.Revision[i].Description.Description);
 }

/*
int r=kp.Revisions.Revision.length;
for(int j=0;j<r;j++)
System.out.println(kp.KeyInfo.EndDate);

System.out.println(kp.Revisions.Revision.length);
System.out.println(kp.Revisions.Revision[0].Version);
System.out.println(kp.Revisions.Revision[0].Date);
System.out.println(kp.Usage.Decrypt);
System.out.println(kp.Revisions.Revision[0].Digest);
System.out.println(kp.Revisions.Revision[0].Descripton.value);

*/
//tribute[0]=new kpAttribte();

}
}
