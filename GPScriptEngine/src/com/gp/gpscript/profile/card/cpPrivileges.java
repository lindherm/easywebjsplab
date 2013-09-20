package com.gp.gpscript.profile.card;
import  java.lang.*;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
/**
Privileges elements are used to describe the default privileges the application has (as specified in an
ApplicationProfile) and the specific privileges an application instance has, in line with those specificed in the GP
Card Specification v2.0.1.
*/
public class cpPrivileges extends ProfileNode
{
/**
Is the application a Security Domain? Default
is false
*/
public		String SecurityDomain;
/**
Whether or not he application requires a
Security Domain with Data Authentication
Pattern verification capability. IDefault is false
*/
public		String DAPVerification;
/**
Is the application capable of delegated
management? Default is false
*/
public		String DelegateManagement;
/**
Whether or not the capability to lock the Card
Manager application is required by this
application. Default is false
*/
public		String CardManagerLock;
/**
Whether or not the capability to terminate the
card is required by this application. Possible
values are true or false. Default is false
*/
public		String CardTerminate;
/**
Whether or not the application is the default-
selected application on the card after Card
Personalization. Default is false
*/
public		String DefaultSelected;
/**
Whether or not the application is capable of
altering the card��s PIN. Default is false
*/
public		String CVMChange;
/**
Does the application require that the DAP be
verified? Default is false
*/
public		String MandatedDAPVerification;

	public cpPrivileges(Node node)
	{	super(node);
		if(node.hasAttributes())
		{
			NamedNodeMap map=node.getAttributes();
			Node attr;
			attr=map.getNamedItem("SecurityDomain");
			if(attr!=null)SecurityDomain=attr.getNodeValue();

			attr=map.getNamedItem("DAPVerification");
			if(attr!=null)DAPVerification=attr.getNodeValue();


			attr=map.getNamedItem("DelegateManagement");
			if(attr!=null)DelegateManagement=attr.getNodeValue();


			attr=map.getNamedItem("CardManagerLock");
			if(attr!=null)CardManagerLock=attr.getNodeValue();


			attr=map.getNamedItem("CardTerminate");
			if(attr!=null)CardTerminate=attr.getNodeValue();

			attr=map.getNamedItem("DefaultSelected");
			if(attr!=null)DefaultSelected=attr.getNodeValue();

			attr=map.getNamedItem("CVMChange");
			if(attr!=null)CVMChange=attr.getNodeValue();

			attr=map.getNamedItem("MandatedDAPVerification");
			if(attr!=null)MandatedDAPVerification=attr.getNodeValue();

		}
	}
}