package com.gp.gpscript.profile;
import java.lang.*;
import org.w3c.dom.*;
import org.apache.log4j.Logger;
import org.apache.xalan.xpath.*;
import org.apache.xalan.xpath.xml.*;

import com.gp.gpscript.profile.*;
import com.gp.gpscript.profile.app.*;
import com.gp.gpscript.profile.card.*;
import com.gp.gpscript.profile.loadfile.*;

public class ConflictCheck
{
	private static Logger log = Logger.getLogger(ConflictCheck.class);
/** Ensuring that the card Profile is compatible with the all Application profile(s) using the conflict rules defined in each
*	@param  cp card profile
    @param  ap[] application profile(s)
    @return boolean  conflict check passed or not
*/
public boolean Check(CardProfile cp,ApplicationProfile ap[])
	{
/* Conflict Rule Check Flow
   1.Check rules associated with Card Profile
   	 1.1.check rules with source type of ApplicationProfile
   	 	1.1.1 check  rules with target type of Cardprofile
   	 	1.1.2 regardless rules with target type of ApplicationProfile/KeyProfile /LoadFileProfile
   	 	1.1.3 check rules with target type of Constant value
     1.2 check rules with source type of CardProfile
     	1.2.1 check rules with target type of ApplicationProfile
     	1.2.2 regardless rules with target type of CardProfile/KeyProfile/LoadFileProfile
     	1.2.3 check rule of target of constant value
    2.check rules associated with Application Profile
     2.1 check rules with soure type of ApplicationProfile
 	 	2.1.1 check  rules with target type of Cardprofile
 	 	2.1.2 regardless rules with target type of ApplicationProfile/KeyProfile /LoadFileProfile
   	 	2.1.3 check rules with target type of Constant value
     2.2 check rules with source type of CardProfile
     	2.2.1 check rules with target type of ApplicatinProfile
     		   chekc target value in all ApplicationProfile
     		   compare source value with all target each other ApplicationProfile
     	2.2.2 regardless rules with target type of CardProfile/KeyProfile/LoadFileProfile
     	2.2.3 check rule of target of constant value

*/
		String source;
		String rule;
		String target;
		String sourceValue[];
		String targetValue[];
		boolean result;
	//	System.out.println("Conflict Check Start");
		if(cp.ConflictRules!=null)
		{
			if(cp.ConflictRules.ConflictRule!=null)
			{
		//check rules in card profile
		cpConflictRule cpcr[]=cp.ConflictRules.ConflictRule;
	//	System.out.println("****** Card Profile Conflict Rules Check ******");
		for(int i=0;i<cpcr.length;i++)
		{
			source=cpcr[i].Source;
			target=cpcr[i].Target;
			rule=cpcr[i].Rule;
		//	System.out.println(source+rule+target);

			// check rule about ApplicationProfile
			if(source.startsWith("ApplicationProfile"))
			{	for(int j=0;j<ap.length;j++)
				{
					sourceValue=ap[i].getxPathValue(source);
					if(sourceValue==null)return(false);
					if(target.startsWith("CardProfile"))
					{
							targetValue=cp.getxPathValue(target);	//attribue(or element)
							if(targetValue==null)return(false);
					}
					else
					{
							if(target.startsWith("ApplicationProfile")||target.startsWith("KeyProfile")||target.startsWith("LoadFileProfile"))
							{
					//			System.out.println("Target profile do not exist");
								continue;	//do nothing
							}
							else
							{
								targetValue=new String[1];
								targetValue[0]=target;		//fixed value
							}
					}
					for(int si=0;si<sourceValue.length;si++)
					{
							for(int ti=0;ti<targetValue.length;ti++)
							{
								result=ruleCheck(sourceValue[si],rule,targetValue[ti]);	//compare all sources and targets
								if(!result)return(false);
							}

					}
				}
			}
			//check rule about cardprofile
			if(source.startsWith("CardProfile"))
			{
				for(int j=0;j<ap.length;j++)
				{
					sourceValue=cp.getxPathValue(source);
					if(sourceValue==null)return(false);		//source do not exist

					if(target.startsWith("ApplicationProfile"))
					{
						targetValue=ap[j].getxPathValue(target);	//attribue(or element)
						if(targetValue==null)return(false);	//target do not exist
					}
					else
					{
							if(target.startsWith("CardProfile")||target.startsWith("KeyProfile")||target.startsWith("LoadFileProfile"))
							{
								log.error("Target profile do not exist");
								continue;	//do nothing
							}
							else
							{	targetValue=new String[1];
								targetValue[0]=target;		//fixed value
							}
					}
						for(int si=0;si<sourceValue.length;si++)
						{
							for(int ti=0;ti<targetValue.length;ti++)
							{
								result=ruleCheck(sourceValue[si],rule,targetValue[ti]);
								if(!result)return(false);
							}
						}
					}

				}


		}
	}
}

	//check rule in Application Profile(s)

		for(int i=0;i<ap.length;i++)
		{
			if(ap[i].ConflictRules!=null)
			{
				if(ap[i].ConflictRules.ConflictRule!=null)
				{
					apConflictRule apcr[]=ap[i].ConflictRules.ConflictRule;
						log.debug("****** Application Profile("+i+ ") Conflict Rules Check ******");
					for(int j=0;j<apcr.length;j++)
					{
						source=apcr[j].Source;
						target=apcr[j].Target;
						rule=apcr[j].Rule;
						log.debug(source+rule+target);
						if(source.startsWith("ApplicationProfile"))
						{	sourceValue=ap[i].getxPathValue(source);
							if(sourceValue==null)return(false);

							if(target.startsWith("CardProfile"))
							{
								targetValue=cp.getxPathValue(target);
								if(targetValue==null)return(false);
							}
							else
							{
								if(target.startsWith("ApplicationProfile")||target.startsWith("KeyProfile")||target.startsWith("LoadFileProfile"))
								{
									log.error("Target profile do not exist");
									continue;	//do nothing

								}
								else
								{
									targetValue=new String[1];
									targetValue[0]=target;		//fixed value
								}
							}
							for(int si=0;si<sourceValue.length;si++)
							{
								for(int ti=0;ti<targetValue.length;ti++)
								{
									result=ruleCheck(sourceValue[si],rule,targetValue[ti]);
									if(!result)return(false);
								}
							}
						}
						if(source.startsWith("CardProfile"))
						{
							sourceValue=cp.getxPathValue(source);
							if(sourceValue==null)return(false);

							if(target.startsWith("ApplicationProfile"))
							{

							//All rules with a target type of ApplicationProfile must be compared against all Application
							//Profiles to ensure that no conflicts exist or that any required dependencies are present.
							for(int api=0;api<ap.length;api++)
							{
								targetValue=ap[api].getxPathValue(target);
								if(targetValue==null)return(false);

								for(int si=0;si<sourceValue.length;si++)
								{
									for(int ti=0;ti<targetValue.length;ti++)
									{
										result=ruleCheck(sourceValue[si],rule,targetValue[ti]);
										if(!result)return(false);
									}
								}
							}

							}
							else
							{
								if(target.startsWith("CardProfile")||target.startsWith("KeyProfile")||target.startsWith("LoadFileProfile"))
								{
									log.error("Target profile do not exist");
									continue;	//do nothing

								}
								else
								{
									targetValue=new String[1];
									targetValue[0]=target;		//fixed value

									for(int si=0;si<sourceValue.length;si++)
									{
										for(int ti=0;ti<targetValue.length;ti++)
										{
											result=ruleCheck(sourceValue[si],rule,targetValue[ti]);
											if(!result)return(false);
										}
									}
								}
							}

						}

				}
				}
			}
		}
	log.debug("Conflict Check End");
		return(true);
	}

/** @param source String
	@param rule String ">","<", ">=","<=","==","!="
	@param target String
*/
 boolean ruleCheck(String source,String rule,String target)
	{
		log.debug(source+rule+target);
		//value compare
		if(rule.equals(">"))
		{
			if(Integer.parseInt(source)>Integer.parseInt(target))return(true);
		}
		if(rule.equals("<"))
		{
			if(Integer.parseInt(source)<Integer.parseInt(target))return(true);
		}
		if(rule.equals(">="))
		{
			if(Integer.parseInt(source)>=Integer.parseInt(target))return(true);
		}
		if(rule.equals("<="))
		{
			if(Integer.parseInt(source)<=Integer.parseInt(target))return(true);
		}
		//object compare
		if(rule.equals("=="))
		{
			if(source.equals(target))return(true);
		}
		if(rule.equals("!="))
		{
			if(source.equals(target))return(false);
		}

		return(false);	//rule not exist,return false
	}
	public static void main(String args[])
	{
		String appfile1="D:\\j2sdk14\\GP-Scripts\\CPSdemonstratorApplicationProfile.xml";
		String appfile2="D:\\j2sdk14\\GP-Scripts\\CPSdemonstratorApplicationProfile2.xml";
		String cardfile="D:\\j2sdk14\\GP-Scripts\\OP-DES16CardProfile.xml";
		ApplicationProfile ap[]=new ApplicationProfile[2];
		ap[0]=new ApplicationProfile(appfile1);
		ap[1]=new ApplicationProfile(appfile2);
		CardProfile cp=new CardProfile(cardfile);
		ConflictCheck cc=new ConflictCheck();
		boolean res=cc.Check(cp,ap);
		log.debug(res);

	}
}