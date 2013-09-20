package com.gp.gpscript.profile.key;
import java.lang.*;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
public class kpDescription extends ProfileNode
{
public		 String Description;
 public kpDescription(Node node )
 {	super(node);
	 if(node.hasChildNodes())
 	{
 		Description=node.getFirstChild().getNodeValue();
	}
 }
}