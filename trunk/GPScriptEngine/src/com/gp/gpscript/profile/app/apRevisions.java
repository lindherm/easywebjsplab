package com.gp.gpscript.profile.app;
import java.lang.*;

import org.apache.log4j.Logger;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
//import com.watchdata.wdcams.loader.Loader;
/**
A placeholder for the revision entries for a profile. Used by all profiles.
*/
public class apRevisions extends ProfileNode
{private Logger log = Logger.getLogger(apRevisions.class);
/**
@see apRevision
*/
public		apRevision Revision[];
	public apRevisions(Node node)
	{	super(node);
		String xpString="Revision";
		try{
				NodeList nl=xPathNode.getNodeList(xpString,node);
				Revision=new apRevision[nl.getLength()];
				Node nd;
				for(int i=0;i<nl.getLength();i++)
				{
				 nd=nl.item(i);
				 Revision[i]=new apRevision(nd);
				}
			}catch(Exception e)
			{
//                e.printStackTrace();
				log.error(e.getMessage());
			}

	}

}