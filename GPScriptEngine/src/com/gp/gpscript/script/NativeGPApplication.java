package com.gp.gpscript.script;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Scriptable;

import com.gp.gpscript.keymgr.util.encoders.Hex;
//import com.watchdata.wdcams.loader.Loader;

/**
 *
 * <p>Title: GPApplication</p>
 * <p>Description:
 * This class implements the GPApplication (a built-in object).
 * See [GP_SYS_SCR] 7.1.5 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: watchdata</p>
 * @author SunJinGang
 * @version 1.0
 */
public class NativeGPApplication extends IdScriptableObject
{private Logger log = Logger.getLogger(NativeGPApplication.class);
    private static final Object GPAPPLICATION_TAG = new Object();

    private static final NativeGPApplication prototypeGPApplication = new NativeGPApplication();

    public static void init(Context cx, Scriptable scope, boolean sealed)
    {
      prototypeGPApplication.activatePrototypeMap(MAX_PROTOTYPE_ID);
      prototypeGPApplication.setPrototype(getObjectPrototype(scope));
      prototypeGPApplication.setParentScope(scope);
      if (sealed) { prototypeGPApplication.sealObject(); }
    }


    public NativeGPApplication()
    {
    }

    // reset() should be called when A NativeGPAppliation Object is reuseed for more than one card Personlization
    public void reset()
    {
      p2_storeData = 0;
    }

    /**
     * Returns the name of this GP class, "GPApplication".
     */
    public String getClassName()
    {
        return "GPApplication";
    }

    public void setObjectPrototype()
    {
        setPrototype(prototypeGPApplication);
//        setParentScope(prototypeGPApplication.getParentScope());
    }

    protected int findInstanceIdInfo(String s)
    {
        if (s.equals("key")) {
            return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_key);
        }
        if (s.equals("data")) {
            return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_data);
        }
        if (s.equals("crypto")) {
            return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_crypto);
        }
        if (s.equals("profile")) {
            return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_profile);
        }
        if (s.equals("card")) {
            return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_card);
        }
//        if (s.equals("secureChannel")) {
//            return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_secureChannel);
//        }
        if (s.equals("securityDomain")) {
            return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_securityDomain);
        }
        return super.findInstanceIdInfo(s);
    }

    protected String getInstanceIdName(int id)
    {
        if (id == Id_key) { return "key"; }
        if (id == Id_data) { return "data"; }
        if (id == Id_crypto) { return "crypto"; }
        if (id == Id_profile) { return "profile"; }
        if (id == Id_card) { return "card"; }
//        if (id == Id_secureChannel) { return "secureChannel"; }
        if (id == Id_securityDomain) { return "securityDomain"; }

        return super.getInstanceIdName(id);
    }

    protected Object getInstanceIdValue(int id)
    {
        if(id==Id_key)     return key;  //return a key array
        if(id==Id_data)    return data;  //return a data array
        if(id==Id_crypto)	return crypto;
        if(id==Id_profile)  return profile;
        if(id==Id_card)  return card;
        if(id==Id_securityDomain)  return securityDomain;
//        if(id==Id_secureChannel)  return secureChannel;

        return super.getInstanceIdValue(id);
    }

    //no constant in this class
    private double getField(int fieldId)
    {
        switch (fieldId)
        {
            //should add the constant here
        }
        return 0; // Unreachable
    }


    public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope,
                             Scriptable thisObj, Object[] args)
    {
        if (!f.hasTag(GPAPPLICATION_TAG)) {
            return super.execIdCall(f, cx, scope, thisObj, args);
        }

        int id = f.methodId();
        switch (id)
        {
            case Id_getData:      return realThis(thisObj, f).jsFunction_getData(cx, scope, args);
            case Id_getStatus:    return realThis(thisObj, f).jsFunction_getStatus(cx, scope, args);
            case Id_putKey:       return realThis(thisObj, f).jsFunction_putKey(cx, scope, args);
            case Id_select:       return realThis(thisObj, f).jsFunction_select(cx, scope, args);
            case Id_sendApdu:     return realThis(thisObj, f).jsFunction_sendApdu(cx, scope, args);
            case Id_setStatus:    { realThis(thisObj, f).jsFunction_setStatus(cx, scope, args); return null;}
            case Id_storeData:    { realThis(thisObj, f).jsFunction_storeData(cx, scope, args); return null;}
//            case Id_openSecureChannel:    return realThis(thisObj, f).jsFunction_openSecureChannel(cx, scope, args);
        }
        throw new IllegalArgumentException(String.valueOf(id));
    }

    //thisObj
    private NativeGPApplication realThis(Scriptable thisObj, IdFunctionObject f)
    {
      if (! (thisObj instanceof NativeGPApplication))
        throw incompatibleCallError(f);
      return (NativeGPApplication) thisObj;
    }

/*************************************************/
/*GP function									 */
/*throw exception according to the parameter******/
/*************************************************/
/**
 * sendApdu through secure channel
 * @param cx
 * @param scope
 * @param args  maybe have 4/5/6/7 parameters
 * update the property of card(response without SW, SW, SW1, SW2)
 * @return the response of the APDU(have the SW)
 */
    private NativeByteString jsFunction_sendApdu(Context cx, Scriptable scope,Object[] args)
    {
        if(!((args[0]instanceof Number)&&(args[1]instanceof Number)&&(args[2]instanceof Number)&&(args[3]instanceof Number)))
            throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_TYPE)).toString());
        //define the default parameter
        NativeByteString defaultData=null;
        Number defaultLE=new Integer(-1);
        Number defaultSW=new Integer(0x9000);
        if(args.length==4)
        {
            NativeByteString sNew = this.securityDomain.wrapApdu(cx,scope,(Number)args[0],(Number)args[1],(Number)args[2],(Number)args[3],defaultData,defaultLE,defaultSW);
            return NativeByteString.newByteString(cx,scope,sNew);
        }
        else if(args.length==5)
        {
            if(!((args[4] instanceof NativeByteString)||(args[4]==null)))
            throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_TYPE)).toString());
            else
            {
                NativeByteString sNew = this.securityDomain.wrapApdu(cx,scope,(Number)args[0],(Number)args[1],(Number)args[2],(Number)args[3],(NativeByteString)args[4],defaultLE,defaultSW);
                return NativeByteString.newByteString(cx,scope,sNew);
            }
        }
        else if(args.length==6)
        {
            if(!(((args[4] instanceof NativeByteString)||(args[4]==null))&&(args[5]instanceof Number)))
              throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_TYPE)).toString());
            else
            {
                NativeByteString sNew = this.securityDomain.wrapApdu(cx,scope,(Number)args[0],(Number)args[1],(Number)args[2],(Number)args[3],(NativeByteString)args[4],(Number)args[5],defaultSW);
                return NativeByteString.newByteString(cx,scope,sNew);
            }
        }
        else if(args.length==7)
        {
            if(!(((args[4] instanceof NativeByteString)||(args[4]==null))&&(args[5]instanceof Number)&&(args[6]instanceof Number)))
              throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_TYPE)).toString());
            else
            {
                NativeByteString sNew = this.securityDomain.wrapApdu(cx,scope,(Number)args[0],(Number)args[1],(Number)args[2],(Number)args[3],(NativeByteString)args[4],(Number)args[5],(Number)args[6]);
                return NativeByteString.newByteString(cx,scope,sNew);
            }
        }
        else
              throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_ARGUMENTS)).toString());

    }

    /**
     * ByteString getData(Number tag)
     * ByteString getData(Number tag, Number sw)
     * @param cx context
     * @param scope scope
     * @param args tag sw
     * @return APDU response
     */
    private NativeByteString jsFunction_getData(Context cx, Scriptable scope,Object[] args)
    {
        int tag=0;
        Number SW=new Integer(0x9000);
        //analyse the parameter
        if((args.length<1)||(args.length>2))
            throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_TYPE)).toString());
        if(!(args[0] instanceof Number))
            throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_TYPE)).toString());
        if(args.length==2)
        {
            if(!(args[1] instanceof Number))
            throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_TYPE)).toString());
            SW=(Number)args[1];
        }
        tag=((Number)args[0]).intValue();
        Byte P1=new Byte((byte)(tag/256));
        Byte P2=new Byte((byte)(tag%256));
        //send command
        this.securityDomain.wrapApdu(cx,scope,new Integer(0x80),new Integer(0xCA),P1,P2,null,new Integer(0x00),SW);

        NativeByteString sNew = this.card.response;
        return sNew;
    }

    /**
     * ByteString getStatus(Number type,ByteString criteria,Boolean next)
     * ByteString getStatus(Number type,ByteString criteria,Boolean next,Number sw)
     * @param cx
     * @param scope
     * @param args
     * @return
     */
    private NativeByteString jsFunction_getStatus(Context cx, Scriptable scope,Object[] args)
    {
        Number SW=new Integer(0x9000);
        //analyse the parameter
        if((args.length<3)||(args.length>4))
            throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_TYPE)).toString());
        if((!(args[0] instanceof Number))||(!(args[1] instanceof NativeByteString))||(!(args[2] instanceof Boolean)))
            throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_TYPE)).toString());
        if(args.length==4)
        {
            if(!(args[3] instanceof Number))
            throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_TYPE)).toString());
            SW=(Number)args[3];
        }
        int type=((Number)args[0]).intValue();
        Integer P1=new Integer(0x00);
        Integer P2=new Integer(0x00);
        switch(type)
        {
            case GPConstant.CM_ONLY: P1=new Integer(0x80); break;
            case GPConstant.APPS_ONLY: P1=new Integer(0x40); break;
            case GPConstant.LF_ONLY: P1=new Integer(0x20); break;
            case GPConstant.LFE_ONLY: P1=new Integer(0x10); break;
        }
        if(((Boolean)args[2]).booleanValue()==true)      P2=new Integer(0x01);

        //send command
        this.securityDomain.wrapApdu(cx,scope,new Integer(0x80),new Integer(0xF2),P1,P2,(NativeByteString)args[1],new Integer(-1),SW);

        NativeByteString sNew = this.card.response;
        return sNew;
    }

    /**
     * putKey
     * @param cx
     * @param scope
     * @param args
     * args should be more and sw
     * more default is false
     * sw default is 0x9000
     * send APDU(0x80 0xD8 .....)
     * should encrypt key with the card KEK
     * @return
     */
    //modify by pm ,the ins changed to 0xE2 for watchdata card test.
    private NativeByteString jsFunction_putKey(Context cx, Scriptable scope,Object[] args)
    {
        Boolean More=new Boolean(false);
        Number SW=new Integer(0x9000);
        //analyse the parameter
        if((args.length%3)==1)
        {
            if(!(args[args.length-1] instanceof Boolean))
            throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_TYPE)).toString());
            More=(Boolean)args[args.length-1];
        }
        if((args.length%3)==2)
        {
            if((!(args[args.length-2] instanceof Boolean))||(!(args[args.length-1] instanceof Number)))
            throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_TYPE)).toString());
            More=(Boolean)args[args.length-2];
            SW=(Number)args[args.length-1];
        }
        if( (!(args[0] instanceof Number)) || (!(args[1] instanceof Number)) || (!(args[2] instanceof Number)))
            throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_TYPE)).toString());
        for(int i=1;i<(args.length/3);i++)
        {
            if((!(args[i*3] instanceof Number)) || (!(args[i*3+1] instanceof NativeKey)) ||(!(args[i*3+2] instanceof NativeByteString)))
            throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_TYPE)).toString());
        }


        Number P1=new Integer(0);
        Number P2=new Integer(0);
        //p1 is KeyVersion
        if(More.booleanValue()==true)  //more putKey
            P1=new Integer( ((Number)args[0]).intValue()|0x80);
        else  //the last putkey
            P1=new Integer( ((Number)args[0]).intValue()&0x7F);
        //p2 is KeyIndex
        if(args.length>=9)  //multiple key
            P2=new Integer(((Number)args[2]).intValue()|0x80);
        else  //single key
            P2=new Integer(((Number)args[2]).intValue());
        int newVersion=((Number)args[1]).intValue();
        String strNewVersion=Hex.byteToString((byte)newVersion);
        //get the all the data of the key(type/key/kcv)
        String strData=strNewVersion;
        for(int i=1;i<(args.length/3);i++)
        {
            int type=((Number)args[i*3]).intValue();
            NativeByteString keyBlob=((NativeKey)args[i*3+1]).getBlob();
            //key must encrypted by card kek(KDCkek), this key should be modified,
            //for example, KDCkek,PSK....
            //NativeKey KEK=(NativeKey)key.get("KDCkek",scope);//new NativeKey("KDCkek");
/*            NativeKey KEK=(NativeKey)key.get("Sec_KEK",scope);//new NativeKey("KDCkek");
            NativeByteString iv=new NativeByteString("0000000000000000",new Integer(GPConstant.HEX));
            NativeByteString encryptedKey =NativeCrypto.encrypt(KEK,new Integer(Crypto.DES_ECB),key,iv);
*/
            NativeByteString encryptedKey;
            if(securityDomain.secureChannel instanceof NativeGPScp01)
              encryptedKey = ((NativeGPScp01)securityDomain.secureChannel).encryptKek(cx, scope, keyBlob);
            else
              encryptedKey = ((NativeGPScp02)securityDomain.secureChannel).encryptKek(cx, scope, keyBlob);

            String strEncryptedKey=encryptedKey.toString();
            String kcv=((NativeByteString)args[i*3+2]).toString();

            String strType=Hex.byteToString((byte)type);
            String strLKey=Hex.byteToString((byte)(strEncryptedKey.length()/2));
            kcv=kcv.substring(0,2*3);
            strData=strData+strType+strLKey+strEncryptedKey+"03"+kcv;
        }
        NativeByteString comData=new NativeByteString(strData,new Integer(GPConstant.HEX));

        //send command
        // change INS from oxD8 to test watchdata card
        this.securityDomain.wrapApdu(cx,scope,new Integer(0x80),new Integer(0xD8),P1,P2,comData,new Integer(-1),SW);

        NativeByteString sNew = this.card.response;
        return sNew;
	}

    /**
     * select the application according to the AID of the card profile
     * @param cx
     * @param scope
     * @param args
     * args should be next and sw
     * next default is false
     * sw default is 0x9000
     * send APDU(0x00 0xA4 0x04 .....)
     * AID should be retrieved from the card profile
     * @return
     */
    private NativeByteString jsFunction_select(Context cx, Scriptable scope,Object[] args)
    {
        String strData="A0000000031010";//strAID;
        //String strData=GP_Global.getAIDfromCardProfile(this.card.profile,this.profile.ap);

        Number P2=new Integer(0x00);
        Number SW=new Integer(0x9000);
        if(args.length==1)
        {
            if(!(args[0] instanceof Boolean))
              throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_TYPE)).toString());
            if( ((Boolean)args[0]).booleanValue()==true)
                P2=new Integer(0x02);
            else
                P2=new Integer(0x00);
        }
        else if(args.length==2)
        {
            if((!(args[0] instanceof Boolean))||(!(args[1] instanceof Number)))
              throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_TYPE)).toString());
            if( ((Boolean)args[0]).booleanValue()==true)
                P2=new Integer(0x02);
            else
                P2=new Integer(0x00);
            SW=(Number)args[1];
        }

        NativeByteString comData=new NativeByteString(strData,new Integer(GPConstant.HEX));

        //send command
        this.securityDomain.wrapApdu(cx,scope,new Integer(0x00),new Integer(0xA4),new Integer(0x04),P2,comData,new Integer(-1),SW);

        NativeByteString sNew = this.card.response;
        return sNew;
    }

    /**
     * set status(********is diffrent from the GPSecurityDomain********)
     * @param cx
     * @param scope
     * @param args
     * args should be status and sw
     * sw default is 0x9000
     * send APDU(80 F0 80 status LC AID)
     * AID should be retrieved from the card profile
     * @return no return
     */
    private void jsFunction_setStatus(Context cx, Scriptable scope,Object[] args)
    {
        String strData="A0000000031010";//strAID;
        //String strData=PcscWrap.getAIDfromCardProfile(this.card.profile,this.profile.ap);

        log.debug("set Status:");
        Number P2=new Integer(0x00);  //status
        Number SW=new Integer(0x9000);
        if(args.length==1)
        {
            if(!(args[0] instanceof Number))
              throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_TYPE)).toString());
            P2=(Number)args[0];
        }
        else if(args.length==2)
        {
            if((!(args[0] instanceof Number))||(!(args[1] instanceof Number)))
              throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_TYPE)).toString());
            P2=(Number)args[0];
            SW=(Number)args[1];
        }
        NativeByteString comData=new NativeByteString(strData,new Integer(GPConstant.HEX));
        //send command
//        if(GP_Global.appStyle.equals("GPSEC"))
//            PcscWrap.sendApdu(GpSecureLevel,"GPApplication",cx,scope,new Integer(0x80),new Integer(0xF0),new Integer(0x80),P2,comData,new Integer(-1),SW);
//        else
            this.securityDomain.wrapApdu(cx,scope,new Integer(0x80),new Integer(0xF0),new Integer(0x40),P2,comData,new Integer(-1),SW);
    }

    /**
     * storeData(is used in any case, equal to appendRecord)
     * @param cx
     * @param scope
     * @param args
     * args should be status and sw
     * sw default is 0x9000
     * send APDU(00 E2 P1 P2 LC AID)  ???CLA is 0x00, not 0x80
     * P1=0x80(last block)  P1=0x00(more block)
     * AID should be retrieved from the card profile
     * @return no return
     */
    private void jsFunction_storeData(Context cx, Scriptable scope,Object[] args)
    {
    	Number Last=new Integer(0x00);
        Number SW=new Integer(0x9000);
        Number Init=new Integer(0x00);
        if(!(args[0] instanceof NativeByteString))
            throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_TYPE)).toString());
        if(args.length==2)
        {
            if(!(args[1] instanceof Number))
            throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_TYPE)).toString());
            Last=(Number)args[1];
        }
        else if(args.length==3)
        {
            if((!(args[1] instanceof Number))||(!(args[2] instanceof Number)))
            throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_TYPE)).toString());
            Last=(Number)args[1];
            SW=(Number)args[2];
        }
        else if(args.length==4)
        {
            if((!(args[1] instanceof Number))||(!(args[2] instanceof Number))||(!(args[3] instanceof Number)))
            throw new EvaluatorException( ( new GPError("GPApplication", GPError.INVALID_TYPE)).toString());
            Last=(Number)args[1];
            SW=(Number)args[2];
            Init=(Number)args[3];
            p2_storeData=Init.intValue();
        }

        Number P1=new Integer(0x00);
        Number P2=new Integer(0x00);
        if(Last.intValue() == 1)  //the last block
        {
        	P1=new Integer(0x80);
        }else if(Last.intValue() == 2){
        	P1=new Integer(0x60); //this block need SKUdek encrypt ICC Coefficient ...       	
        }else if(Last.intValue() == 3){
        	P1=new Integer(0x20); //hua da cos    encrypt pin  	
        }
        
        else
		P1=new Integer(0x00);
        P2=new Integer(p2_storeData);

        NativeByteString comData=(NativeByteString)args[0];

        //send command
        this.securityDomain.wrapApdu(cx,scope,new Integer(0x00),new Integer(0xE2),P1,P2,comData,new Integer(-1),SW);

        //update the p2
        if(Last.intValue() == 1)
            p2_storeData=Init.intValue();
        else
			p2_storeData++;

    }


    /**
     * execute the script of openSecureChannel
     * modify the Param of the script
     * @param cx
     * @param scope
     * @param args
     * @return
     */
/*    private Number jsFunction_openSecureChannel(Context cx, Scriptable scope,Object[] args)
    {
        Object result = null;
        int level=((Number)args[0]).intValue();
        String strLevel=((Number)args[0]).toString();
        cx.evaluateString(scope,  "level="+strLevel+";" + this.profile.SecureChannel.OpenSecureChannel.Script.Script, "OpenSecureChannel", 0, null);
        secureLevel=level;
        return new Integer(level);
*/
/*        String fileName=GP_Global.scriptDir+GP_Global.openSecureChannelScriptFile;
        //create the scripts
        try
        {
            String secureChannelScript="";
            secureChannelScript=secureChannelScript+"level="+strLevel+";";
            secureChannelScript=secureChannelScript+this.profile.SecureChannel.OpenSecureChannel.Script.Script;
            DataOutputStream out=new DataOutputStream(new FileOutputStream(fileName));
            out.writeBytes(secureChannelScript);
            out.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        //execute the script
        try
        {
            result = RunScript.processSource(cx, scope, fileName);
            System.out.println("openSecureChannel is successed!");
            GpSecureLevel=level;
            //GPApp will impact the command of the GPSecurityDomain
            securityDomain.GpSecDomainSecureLevel=level;
            return new Integer(level);
        }
        catch (GPError egErr)
        {
            egErr.printStackTrace();
            System.out.println(egErr.toString());
            // Exit from the context.
            Context.exit();
            return new Integer(-1);
        }
    }
*/

//**************************************************/

    protected int maxInstanceId() { return MAX_INSTANCE_ID; }

    protected void initPrototypeId(int id)
    {
        String s;
        int arity;
        switch (id) {
            //function
            case Id_getData:      arity = 2; s = "getData"; break;
            case Id_getStatus:    arity = 4; s = "getStatus"; break;
            case Id_putKey:       arity = 8; s = "putKey"; break;
            case Id_select:       arity = 3; s = "select"; break;
            case Id_sendApdu:     arity = 7; s = "sendApdu"; break;
            case Id_setStatus:    arity = 2;  s = "setStatus"; break;
            case Id_storeData:    arity = 3;  s = "storeData"; break;
//            case Id_openSecureChannel:  arity = 0;   s = "openSecureChannel"; break;
/*
            //constant
            case Id_card:      return "card";
            case Id_crypto:      return "crypto";
            case Id_data:      return "data";
            case Id_key:      return "key";
            case Id_profile:      return "profile";
            case Id_securityDomain:      return "securityDomain";
            case Id_secureChannel:      return "secureChannel";
*/
          default: throw new IllegalArgumentException(String.valueOf(id));
        }
        initPrototypeMethod(GPAPPLICATION_TAG, id, s, arity);
    }

    protected int findPrototypeId(String s)
    {
        int id=0;
        String X = null;
        if(s.equals("key")) {X="key";id=Id_key;}
        if(s.equals("card")) { X="card";id=Id_card;}
        if(s.equals("data")) { X="data";id=Id_data;}
        if(s.equals("putKey")) { X="putKey";id=Id_putKey;}
        if(s.equals("select")) { X="select";id=Id_select;}
        if(s.equals("crypto")) { X="crypto";id=Id_crypto;}
        if(s.equals("getData")) { X="getData";id=Id_getData;}
        if(s.equals("profile")) { X="profile";id=Id_profile;}
        if(s.equals("sendApdu")) { X="sendApdu";id=Id_sendApdu;}
        if(s.equals("setStatus")) { X="setStatus";id=Id_setStatus;}
        if(s.equals("storeData")) { X="storeData";id=Id_storeData;}
        if(s.equals("getStatus")) { X="getStatus";id=Id_getStatus;}
//        if(s.equals("secureChannel")) { X="secureChannel";id=Id_secureChannel;}
        if(s.equals("securityDomain")) { X="securityDomain";id=Id_securityDomain;}
//        if(s.equals("openSecureChannel")) { X="openSecureChannel";id=Id_openSecureChannel;}

        return id;
    }

    //define value for all function and constant
    private static final int
        Id_getData=1,
        Id_getStatus=2,
        Id_putKey=3,
        Id_select=4,
        Id_sendApdu=5,
        Id_setStatus=6,
        Id_storeData=7,
//        Id_openSecureChannel=8,
        LAST_METHOD_ID  = 7,
        MAX_PROTOTYPE_ID = 7;
        //the following should be contants
        private static final int
        Id_card	=	1,
        Id_crypto	=	2,
        Id_data	=	3,
        Id_key      =   4,
        Id_profile      =   5,
        Id_securityDomain      =   6,
//        Id_secureChannel      =   7,
        MAX_INSTANCE_ID = 6;

    public NativeCard card;
    public NativeCrypto crypto;
    //private ApplicationProfile profile;
    public Application profile;
    public NativeGPSecurityDomain securityDomain;
    //private NativeGPScp01 secureChannel;
   // public IdScriptableObject secureChannel;
    //key[]
    public NativeArray key;
//    public Scriptable KeyArray;
    //data[]
    public  NativeArray data;
//    public Scriptable DataArray;

    //use by store Data
    private int p2_storeData = 0;  //increment after each call,and reset to initValue_StoreData after a call with last==true
    private int p2_putKey = 0;

    /**
     * use by each command of the GPApplication
     * 0-No; 1-C_Mac;  3-C_Mac_Enc
     */
    public int secureLevel=0;

}
