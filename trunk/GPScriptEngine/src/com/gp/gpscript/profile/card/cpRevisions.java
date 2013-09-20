package com.gp.gpscript.profile.card;
import java.lang.*;

import org.apache.log4j.Logger;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
//import com.watchdata.wdcams.loader.Loader;
/**
A placeholder for the revision entries for a profile. Used by all profiles.
*/
public class cpRevisions extends ProfileNode
{private Logger log = Logger.getLogger(cpRevisions.class);
public		cpRevision Revision[];
	public cpRevisions(Node node)
	{
			super(node);

		try{
				String xpString="Revision";
				NodeList nl=xPathNode.getNodeList(xpString,node);
				Revision=new cpRevision[nl.getLength()];
				for(int i=0;i<nl.getLength();i++)
				{
					 Revision[i]=new cpRevision(nl.item(i));
				}
			}catch(Exception e)
			{
//                e.printStackTrace();
				log.error("Revision "+e.getMessage());
			}

	}

}