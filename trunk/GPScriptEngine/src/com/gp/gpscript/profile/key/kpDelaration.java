package com.gp.gpscript.profile.key;
import  java.lang.*;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
/**
Freeform text description element.
*/
public class kpDelaration extends ProfileNode
{
public		String Name;
public		String External;
public		String Encoding;
public		String Value;
public		String ReadWrite;
public		String Update;
public		String Optional;
public		String MandatoryAudit;
	public kpDelaration(Node node)
	{	super(node);
		if(node.hasAttributes())
		{
			NamedNodeMap map=node.getAttributes();
			Node attr;
			attr=map.getNamedItem("Name");
			if(attr!=null)Name=attr.getNodeValue();

			attr=map.getNamedItem("External");
			if(attr!=null)External=attr.getNodeValue();


			attr=map.getNamedItem("Encoding");
			if(attr!=null)Encoding=attr.getNodeValue();


			attr=map.getNamedItem("Value");
			if(attr!=null)Value=attr.getNodeValue();

			attr=map.getNamedItem("ReadWrite");
			if(attr!=null)ReadWrite=attr.getNodeValue();

			attr=map.getNamedItem("Update");
			if(attr!=null)Update=attr.getNodeValue();

			attr=map.getNamedItem("Optional");
			if(attr!=null)Optional=attr.getNodeValue();

			attr=map.getNamedItem("MandatoryAudit");
			if(attr!=null)MandatoryAudit=attr.getNodeValue();

		}
	}
}