package com.gp.gpscript.profile.card;
import  java.lang.*;

import org.apache.log4j.Logger;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
//import com.watchdata.wdcams.loader.Loader;
/**
Information about the smart card resources
and application specific fields used to manage the smart card
*/
public class cpCardInfo extends ProfileNode
{private Logger log = Logger.getLogger(cpCardInfo.class);
/**
@see cpResourcesAvailable
*/
public		cpResourcesAvailable  ResourcesAvailable;
/**
@see cpOnLoad
*/
public		cpOnLoad	OnLoad;

	public cpCardInfo(Node node)
	{
		super(node);
		NodeList nl;
		String xpString;
		try{
			    xpString="ResourcesAvailable";
				nl=xPathNode.getNodeList(xpString,node);
				if(nl.getLength()>0)
				{
					ResourcesAvailable=new cpResourcesAvailable(nl.item(0));
				}
			}catch(Exception e)
			{
//                e.printStackTrace();
				log.error("ResourcesAvailable "+e.getMessage());
			}
			

		try{
			    xpString="OnLoad";
				nl=xPathNode.getNodeList(xpString,node);
				if(nl.getLength()>0)
				{
					OnLoad=new cpOnLoad(nl.item(0));
				}
			}catch(Exception e)
			{
//                e.printStackTrace();
				log.error("OnLoad "+e.getMessage());
			}

	}
}