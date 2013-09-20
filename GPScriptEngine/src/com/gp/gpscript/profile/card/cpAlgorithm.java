package com.gp.gpscript.profile.card;
import java.lang.*;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
/**
Algorithm elements are used to describe the algorithms or modes that are supported by the crypto engine
supported on the smart card. It is part of the Card Profile.
*/
public class cpAlgorithm extends ProfileNode
{
/**
Name of the algorithm as specified by the
operating system or platform specification.
These should match the algorithm names
specified for the Crypto methods in the GP
Scripting language [GP_SYS_SCR] .
*/
public	  String Name;

  public cpAlgorithm(Node node)
  {	super(node);
 	Node attr;
 	if(node.hasAttributes())
 	{
	 	NamedNodeMap map=node.getAttributes();
		attr=map.getNamedItem("Name");
		if(attr!=null)Name=attr.getNodeValue();
	}

  }
}
