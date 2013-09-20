package com.gp.gpscript.profile.card;
import  java.lang.*;

import org.apache.log4j.Logger;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
//import com.watchdata.wdcams.loader.Loader;
/**
CryptoEngine element defines the key types and algorithms supported by a smart card��s cryptographic engine.
CryptoEngine is used in the Card Profile.
*/
public class cpCryptoEngine extends ProfileNode
{private Logger log = Logger.getLogger(cpCryptoEngine .class);
/**
@see cpDescription
*/
public		cpDescription Description;
/**
@see cpKeyTypes
*/
public		cpKeyTypes KeyTypes;
/**
@see cpAlgorithm
*/
public		cpAlgorithm Algorithm;
	public cpCryptoEngine(Node node)
	{  	super(node);
		String xpString;
		NodeList nl;

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
			  	xpString="KeyTypes";
				nl=xPathNode.getNodeList(xpString,node);
			    if(nl.getLength()>0)KeyTypes=new cpKeyTypes(nl.item(0));
			}catch(Exception e)
			{
//                e.printStackTrace();
				log.error("KeyTypes "+e.getMessage());
			}
		try{
			  	xpString="Algorithm";
				nl=xPathNode.getNodeList(xpString,node);
			    if(nl.getLength()>0)Algorithm=new cpAlgorithm(nl.item(0));
			}catch(Exception e)
			{
//                e.printStackTrace();
				log.error("Algorithm "+e.getMessage());
			}

	}
}