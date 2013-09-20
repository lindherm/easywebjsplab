package com.gp.gpscript.profile.loadfile;
import  java.lang.*;

import org.apache.log4j.Logger;
import  org.w3c.dom.*;

import com.gp.gpscript.profile.*;

/**
Modules is a placeholder element for one or more Module elements.
*/
public class lpModules extends ProfileNode
{private Logger log = Logger.getLogger(lpModules.class);
/**
@see lpModule
*/
public		lpModule Module[];
	public lpModules(Node node)
	{
			String xpString="Module";
			NodeList nl;
	try{
			nl=xPathNode.getNodeList(xpString,node);
			if(nl.getLength()>0)
			{
				Module=new lpModule[nl.getLength()];
				for(int i=0;i<nl.getLength();i++)
				{
					Module[i]=new lpModule(nl.item(i));
				}
			}
		}catch(Exception e)
		{
//            e.printStackTrace();
			log.error("Module "+e.getMessage());
		}
	}
	
}

