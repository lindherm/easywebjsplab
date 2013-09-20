package com.gp.gpscript.profile.app;
import  java.lang.*;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;

/**
ConflictRules elements describe the compatibility checks and conflict rules that must be satisfied on any smart
card using the CardProfile, ApplicationProfile, KeyProfile or LoadFileProfile to which the rules are associated
with. During configuration, comparisons can be made between attributes in the source Profile and a target
Profile, or between a target value��s attribute and a defined value.
The intent is that for each profile, each of the rules are validated against all other profiles included in the
configuration request. Each conflict rule, therefore, contains references to the type of profile to compare with,
and not specific profiles. This is realistic given the fact that specific profiles used in a particular configuration
are not known by developers at the time of profile creation.
Therefore, for each rule, multiple comparisons may need to be performed given the number of profiles in the
configuration which either satisfy the Source of the Target attributes. For example, consider the case were a
configuration involves a smart card and the customization of four applications. If a conflict rule specified a Card
Profile attribute as the Source attribute, and an Application Profile as the Target attribute, the conflict rule will
need to be executed four times, once for each profile. As well, depending on the rule used, the attribute being
compared from each Application Profile may need to be considered in concert with the same attribute from the
other Application Profiles in the configuration. This is particularly true if <, >, <= or >= are specified in the
Rule attribute.
Dependencies are stated at an XML attribute level and can be between any two profiles, regardless of whether
they are Card, Application, Key, or Load File. However, since only one Card Profile is valid when running a
script, a Source and Target comparison where both values are attributes within a Card Profile would be invalid
and should generate an error or ignored during conflict determination process. Conflict rules can also specify as
a target a constant value.
*/
public class apConflictRule extends ProfileNode
{
/**Name of the attribute (or element) to which
the comparison rule should be made against
the attribute (or element) defined by Target.
The Source attribute always begins with the
parent element of the profile
(ApplicationProfile, CardProfile, KeyProfile, or
LoadFileProfile).
Specifically, the format corresponds to the
profile being specified and the location of the
attribute (or element) within the XML
document tree.
*/
public		String Source;
/**Fixed value or name of the attribute (or
element) to which the comparison rule should
be made against the attribute (or element)
defined by Source. For a fixed value, the
Target should contain this value.
*/
public		String Target;
/**Rule
Conflict determination rule to check Source
versus Target against. Possible value values
are:
> (greater than  target values)
< (less than  target values)
>= (Includes at least)
<= (Does not include)
==
!=
*/
public		String Rule;
	public apConflictRule(Node node)
	{	super(node);
	 if(node.hasAttributes())
	 {
	 	NamedNodeMap map=node.getAttributes();
	 	Node attr;
	 	attr=map.getNamedItem("Source");
	 	if(attr!=null)Source=attr.getNodeValue();
	 	attr=map.getNamedItem("Target");
	 	if(attr!=null)Target=attr.getNodeValue();
	 	attr=map.getNamedItem("Rule");
	 	if(attr!=null)Rule=attr.getNodeValue();
	 }
	}
}