package com.gp.gpscript.profile.card;
import  java.lang.*;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;

/**
Freeform text description element.
*/
public class cpDescription extends ProfileNode
{
public		String Description;
	public cpDescription(Node node)
	{	super(node);
		if(node.hasChildNodes())
		{
			Description=node.getFirstChild().getNodeValue();
		}
	}
}