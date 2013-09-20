package com.gp.gpscript.profile.card;
import  java.lang.*;

import org.apache.log4j.Logger;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
//import com.watchdata.wdcams.loader.Loader;
/**
ConflictRules is a placeholder for individual ConflictRule elements. ConflictRules are used in the Application
and Card Profiles.
*/
public class cpConflictRules extends ProfileNode
{private Logger log = Logger.getLogger(cpConflictRules.class);
/**
@see cpConflictRule
*/
public		cpConflictRule ConflictRule[];
	public cpConflictRules(Node node)
	{	super(node);
			String xpString="ConflictRule";
			NodeList nl;
	try{
			nl=xPathNode.getNodeList(xpString,node);
			if(nl.getLength()>0)
			{
				ConflictRule=new cpConflictRule[nl.getLength()];
				for(int i=0;i<nl.getLength();i++)
				{
					ConflictRule[i]=new cpConflictRule(nl.item(i));
				}
			}
		}catch(Exception e)
		{
//            e.printStackTrace();
			log.error("ConflictRule "+e.getMessage());
		}
	}

}

