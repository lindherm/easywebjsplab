package com.gp.gpscript.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;

import com.gp.gpscript.profile.app.ApplicationProfile;
import com.gp.gpscript.profile.app.apApplicationInfo;
import com.gp.gpscript.profile.app.apConflictRules;
import com.gp.gpscript.profile.app.apCryptoEngine;
import com.gp.gpscript.profile.app.apDataElement;
import com.gp.gpscript.profile.app.apDescription;
import com.gp.gpscript.profile.app.apFunction;
import com.gp.gpscript.profile.app.apKey;
import com.gp.gpscript.profile.app.apLifeCycle;
import com.gp.gpscript.profile.app.apLifeCycles;
import com.gp.gpscript.profile.app.apRevisions;
import com.gp.gpscript.profile.app.apScriptFragment;
import com.gp.gpscript.profile.app.apSecureChannel;

/**
ApplicationProfile elements are the parent or root XML element containing an Application Profile for a smart
card application. As the Application Profile represents a smart card application independent of a smart card, no
card specific application instance information, such as whether the application is installed on a smart card or the
current state of the application, is contained within ApplicationProfile XML. The specific application instance
information is described in the Card Profile for the smart card or maintained by a SCMS in conjunction with the
Card Profile.
*/
/**
 *
 * <p>Title: Appliaction Profile</p>
 * <p>Description: ApplicationProfile elements are the parent or root XML element containing an Application Profile for a smart
 * card application. As the Application Profile represents a smart card application independent of a smart card, no
 * card specific application instance information, such as whether the application is installed on a smart card or the
 * current state of the application, is contained within ApplicationProfile XML. The specific application instance
 * information is described in the Card Profile for the smart card or maintained by a SCMS in conjunction with the
 * Card Profile.(This is used for nameIndex, the subClass is used for intIndex)</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: watchdata</p>
 * @author YangGuiLong and SunJinGang
 * @version 1.0
 */
public class  Application
{
    /**
Application Provider OID + 5 byte value
assigned by Application Provider. This
unique ID field must be unique amongst all
UniqueID attributes for the ApplicationProfile
element for all Application Profiles supplied
by the Application Provider who has been
assigned the OID. UniqueID is variable
length (up to 119 bytes). Once the
ApplicationProfile has been changed by
another actor, the UniqueID attribute must be
updated and a new Revision element must
be created.
Example: 0x00010203040506070809 where
0x0001020304 represents the OID value of
the Application Provider.
*/
    public		String UniqueID;
    /**
Version of profiles specification conforming
to GP versioning specification. Version
number is x.x.x where each x is a decimal
number. If the version of the specification
has no last x value, then version number is
x.x.0.Example:
1.0.0 1.0.1 1.1.0 1.1.2
*/
    public		String ProfileVersion;
    /**
@see apDescription
*/
    public		apDescription Description;

    /**
@see apRevisions
*/
    public		apRevisions Revisions;

    /**
@see apConflictRules
*/
    public		apConflictRules ConflictRules;

    /**
@see apApplicationInfo
*/
    public		apApplicationInfo ApplicationInfo;
    /**
@see apCryptoEngine
*/
    public		apCryptoEngine	CryptoEngine;
    /**
@see apSecureChannel
*/
    public	apSecureChannel SecureChannel;
    /**
@see apKey
*/
    public 	NativeArray	Key;
    /**
@see apDataElement
*/
    public NativeArray DataElement;
    /**
@see apFunction
*/
    public NativeArray Function;

    /**
@see apScriptFragment
*/
    public NativeArray ScriptFragment;
    /**
@see apLifeCycles
*/
    public MyLifeCycles LifeCycles;


    /**
     * used for every array index by name
     */
    public static Scriptable dataArray;

    /**
     * used for common Profile that index by int
     */
    public ApplicationProfile ap;

    /**
     * constructor
     * @param xmlFile the profile name
     * @param cx context
     * @param scope the scope
     */
    public Application(String xmlFile,Context cx,Scriptable scope)
    {
        ap=new ApplicationProfile(xmlFile);  //used for intIndex
        UniqueID=ap.UniqueID;
        ProfileVersion=ap.ProfileVersion;
        Description=ap.Description;
        Revisions=ap.Revisions;
        ConflictRules=ap.ConflictRules;
        ApplicationInfo=ap.ApplicationInfo;
        SecureChannel=ap.SecureChannel;

        //deal LifeCycles
        LifeCycles=new MyLifeCycles(ap,cx,scope);

        //deal key
        if(ap.Key!=null)
        {
            apKey KeyArray[]=ap.Key;
            dataArray = ScriptRuntime.newObject(cx, scope, "Array", null);
            for(int i=0;i<KeyArray.length;i++)
            {
                  ScriptRuntime.setObjectElem(dataArray,KeyArray[i].Name,KeyArray[i],cx); //just contain Name and ProfileID
//                KeyProfile keyProfileObj=new KeyProfile(GP_Global.ProfileDir+"KMC.xml");
//                KeyProfile keyProfileObj=new KeyProfile("KMC.xml");
//                ScriptRuntime.setObjectElem(dataArray,KeyArray[i].Name,keyProfileObj,cx); //contain all infomation of the keyProfile
            }
            Key=(NativeArray)dataArray;
        }

        //dataElement
        apDataElement	DataElementArray[]=ap.DataElement;
        dataArray = ScriptRuntime.newObject(cx, scope, "Array", null);
        for(int i=0;i<DataElementArray.length;i++)
        {
            ScriptRuntime.setObjectElem(dataArray,DataElementArray[i].Name,DataElementArray[i],cx);
        }
        DataElement=(NativeArray)dataArray;

        //Function
        if(ap.Function!=null)
        {
            apFunction FunctionArray[]=ap.Function;
            dataArray = ScriptRuntime.newObject(cx, scope, "Array", null);
            for(int i=0;i<FunctionArray.length;i++)
            {
                ScriptRuntime.setObjectElem(dataArray,FunctionArray[i].Name,FunctionArray[i],cx);
            }
            Function=(NativeArray)dataArray;
        }

        //scriptFragment
        if(ap.ScriptFragment!=null)
        {
            apScriptFragment ScriptFragmentArray[]=ap.ScriptFragment;
            dataArray = ScriptRuntime.newObject(cx, scope, "Array", null);
            for(int i=0;i<ScriptFragmentArray.length;i++)
            {
                ScriptRuntime.setObjectElem(dataArray,ScriptFragmentArray[i].Name,ScriptFragmentArray[i],cx);
            }
            ScriptFragment=(NativeArray)dataArray;
        }

    }

    /**
     * constructor
     * @param xmlFile the profile name
     * @param cx context
     * @param scope the scope
     */
    public Application(String xmlFile,Context cx,Scriptable scope,int flag)
    {
        ap=new ApplicationProfile(xmlFile,0);  //used for intIndex
        UniqueID=ap.UniqueID;
        ProfileVersion=ap.ProfileVersion;
        Description=ap.Description;
        Revisions=ap.Revisions;
        ConflictRules=ap.ConflictRules;
        ApplicationInfo=ap.ApplicationInfo;
        SecureChannel=ap.SecureChannel;

        //deal LifeCycles
        LifeCycles=new MyLifeCycles(ap,cx,scope);

        //deal key
        if(ap.Key!=null)
        {
            apKey KeyArray[]=ap.Key;
            dataArray = ScriptRuntime.newObject(cx, scope, "Array", null);
            for(int i=0;i<KeyArray.length;i++)
            {
                  ScriptRuntime.setObjectElem(dataArray,KeyArray[i].Name,KeyArray[i],cx); //just contain Name and ProfileID
//                KeyProfile keyProfileObj=new KeyProfile(GP_Global.ProfileDir+"KMC.xml");
//                KeyProfile keyProfileObj=new KeyProfile("KMC.xml");
//                ScriptRuntime.setObjectElem(dataArray,KeyArray[i].Name,keyProfileObj,cx); //contain all infomation of the keyProfile
            }
            Key=(NativeArray)dataArray;
        }

        //dataElement
        apDataElement	DataElementArray[]=ap.DataElement;
        dataArray = ScriptRuntime.newObject(cx, scope, "Array", null);
        for(int i=0;i<DataElementArray.length;i++)
        {
            ScriptRuntime.setObjectElem(dataArray,DataElementArray[i].Name,DataElementArray[i],cx);
        }
        DataElement=(NativeArray)dataArray;

        //Function
        if(ap.Function!=null)
        {
            apFunction FunctionArray[]=ap.Function;
            dataArray = ScriptRuntime.newObject(cx, scope, "Array", null);
            for(int i=0;i<FunctionArray.length;i++)
            {
                ScriptRuntime.setObjectElem(dataArray,FunctionArray[i].Name,FunctionArray[i],cx);
            }
            Function=(NativeArray)dataArray;
        }

        //scriptFragment
        if(ap.ScriptFragment!=null)
        {
            apScriptFragment ScriptFragmentArray[]=ap.ScriptFragment;
            dataArray = ScriptRuntime.newObject(cx, scope, "Array", null);
            for(int i=0;i<ScriptFragmentArray.length;i++)
            {
                ScriptRuntime.setObjectElem(dataArray,ScriptFragmentArray[i].Name,ScriptFragmentArray[i],cx);
            }
            ScriptFragment=(NativeArray)dataArray;
        }

    }
    //sub-class for LifeCycles
    private static class MyLifeCycles
    {
        public static NativeArray LifeCycle;
        public MyLifeCycles(ApplicationProfile ap,Context cx,Scriptable scope)
        {
            if(ap.ScriptFragment!=null)
            {
                apLifeCycle[] LifeCycleArray=ap.ApplicationInfo.LifeCycles.LifeCycle;
                dataArray = ScriptRuntime.newObject(cx, scope, "Array", null);
                for(int i=0;i<LifeCycleArray.length;i++)
                {
                    ScriptRuntime.setObjectElem(dataArray,LifeCycleArray[i].Name,LifeCycleArray[i],cx);
                }
                LifeCycle=(NativeArray)dataArray;
            }
        }
    }


}