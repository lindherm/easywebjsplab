package com.gp.gpscript.profile.loadfile;
import  java.lang.*;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
/**
Freeform text description element.
*/
public class lpDescription extends ProfileNode
{
public		String Description;
	public lpDescription(Node node)
	{	super(node);
		if(node.hasChildNodes())
		{
			Description=node.getFirstChild().getNodeValue();
		}
	}
}