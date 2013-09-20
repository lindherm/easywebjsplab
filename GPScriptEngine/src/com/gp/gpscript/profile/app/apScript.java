package com.gp.gpscript.profile.app;
import  java.lang.*;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
/**
Contains script code for one particular task.
Example:
<Script>
<![CDATA[
this.sendApdu(0x00, 0xE2, 0x00, sfi << 3, data)
]]>
</Script>
*/
public class apScript extends ProfileNode
{
public		String Script;
private Node currNode;
	public apScript(Node node)
	{	super(node);
		currNode=node;
		if(node.hasChildNodes())
		{
			Script=node.getFirstChild().getNodeValue();
		}
	}
}
