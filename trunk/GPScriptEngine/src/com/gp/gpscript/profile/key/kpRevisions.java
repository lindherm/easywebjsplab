package com.gp.gpscript.profile.key;
import java.lang.*;

import org.apache.log4j.Logger;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
//import com.watchdata.wdcams.loader.Loader;
/**
A placeholder for the revision entries for a profile. Used by all profiles.
*/
public class kpRevisions extends ProfileNode
{private Logger log = Logger.getLogger(kpRevisions.class);
/**
@see kpRevision
*/
public		kpRevision Revision[];
	public kpRevisions(Node node)
	{
		super(node);

		try{
				String xpString="Revision";
				NodeList nl=xPathNode.getNodeList(xpString,node);
				Revision=new kpRevision[nl.getLength()];
				for(int i=0;i<nl.getLength();i++)
				{
					 Revision[i]=new kpRevision(nl.item(i));
				}
			}catch(Exception e)
			{
//                e.printStackTrace();
				log.error("Revision "+e.getMessage());
			}

	}

}