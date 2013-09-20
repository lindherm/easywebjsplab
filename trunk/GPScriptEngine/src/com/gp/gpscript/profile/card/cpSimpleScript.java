package com.gp.gpscript.profile.card;

import java.lang.*;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
/**
A place to specify a simple script, devoid of external data, parameter or key references. This would be useful to
perform any necessary operations on a smart card after the initial smart card access by a device. APDU
commands can only be sent through the Card object in the Scripting Language.
*/
public class cpSimpleScript extends ProfileNode
{
public		String SimpleScript;
	public cpSimpleScript(Node node)
	{	super(node);
		if(node.hasChildNodes())
		{
			SimpleScript=node.getFirstChild().getNodeValue();
		}
	}
}