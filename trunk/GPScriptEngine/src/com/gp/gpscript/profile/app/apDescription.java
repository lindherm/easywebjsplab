package com.gp.gpscript.profile.app;
import  java.lang.*;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
/**
	Freeform text description element.
*/
public class apDescription extends ProfileNode
{
public		String Description;
	public apDescription(Node node)
	{	super(node);
		if(node.hasChildNodes())
		{
			Description=node.getFirstChild().getNodeValue();
		}
	}
}