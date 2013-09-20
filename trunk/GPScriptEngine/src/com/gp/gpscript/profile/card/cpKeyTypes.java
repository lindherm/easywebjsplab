package com.gp.gpscript.profile.card;
import  java.lang.*;

import org.apache.log4j.Logger;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
//import com.watchdata.wdcams.loader.Loader;
/**
KeyTypes is a placeholder element for one or more KeyType elements.
*/
public class cpKeyTypes extends ProfileNode
{private Logger log = Logger.getLogger(cpKeyTypes.class);
/**
@see cpKeyType
*/
public		cpKeyType KeyType[];
	public cpKeyTypes(Node node)
	{	super(node);
		NodeList nl;
		String xpString;
	try{
		 	xpString="KeyType";
			nl=xPathNode.getNodeList(xpString,node);
		if(nl.getLength()>0)
		{
			KeyType=new cpKeyType[nl.getLength()];
			for(int i=0;i<nl.getLength();i++)
			{
				KeyType[i]=new cpKeyType(nl.item(i));
			}

		}
		}catch(Exception e)
		{
//            e.printStackTrace();
			log.error("KeyType "+e.getMessage());
		}
	}
}

