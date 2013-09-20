package com.gp.gpscript.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import com.gp.gpscript.profile.key.KeyProfile;
import com.watchdata.commons.crypto.WD3DesCryptoUtil;
import com.watchdata.commons.jce.JceBase.Padding;

/**
 *
 * <p>Title: Key</p>
 * <p>Description:
 * This class implements the Key (a built-in object).
 * See [GP_SYS_SCR] 7.1.11 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: watchdata</p>
 * @author SunJinGang
 * @version 1.0
 */
public class NativeKey extends IdScriptableObject
{

  private static final Object KEY_TAG = new Object();
  private static NativeKey prototypeKey = new NativeKey();



/**
 * @param strBlob the strBlob to set
 */
public void setStrBlob(NativeByteString strBlob) {
    strBlob= strBlob;
}

/**
 * @param strBlob the strBlob to set
 */


public static void init(Context cx, Scriptable scope, boolean sealed)
  {
        prototypeKey.activatePrototypeMap(MAX_PROTOTYPE_ID);
        prototypeKey.setPrototype(getObjectPrototype(scope));
        prototypeKey.setParentScope(scope);
        final int attr = ScriptableObject.DONTENUM |
                       ScriptableObject.PERMANENT |
                       ScriptableObject.READONLY;

        prototypeKey.defineProperty("PERMANENT",
                          ScriptRuntime.wrapNumber(PERMANENT),
                          attr);
        prototypeKey.defineProperty("NONE",
                          ScriptRuntime.wrapNumber(NONE),
                          attr);
        prototypeKey.defineProperty("CRT_PRIVATE",
                          ScriptRuntime.wrapNumber(CRT_PRIVATE),
                          attr);
        prototypeKey.defineProperty("OTHER",
                          ScriptRuntime.wrapNumber(OTHER),
                          attr);
        prototypeKey.defineProperty("SECRET",
                          ScriptRuntime.wrapNumber(SECRET),
                          attr);
        prototypeKey.defineProperty("PRIVATE",
                          ScriptRuntime.wrapNumber(PRIVATE),
                          attr);
        prototypeKey.defineProperty("PUBLIC",
                          ScriptRuntime.wrapNumber(PUBLIC),
                          attr);
        prototypeKey.defineProperty("ENCRYPT",
                          ScriptRuntime.wrapNumber(ENCRYPT),
                          attr);
        prototypeKey.defineProperty("DECRYPT",
                          ScriptRuntime.wrapNumber(DECRYPT),
                          attr);
        prototypeKey.defineProperty("DECRYPT_ENCRYPT",
                          ScriptRuntime.wrapNumber(DECRYPT_ENCRYPT),
                          attr);
        prototypeKey.defineProperty("DERIVE",
                          ScriptRuntime.wrapNumber(DERIVE),
                          attr);
        prototypeKey.defineProperty("GENERATE_KEY",
                          ScriptRuntime.wrapNumber(GENERATE_KEY),
                          attr);
        prototypeKey.defineProperty("SIGN",
                          ScriptRuntime.wrapNumber(SIGN),
                          attr);
        prototypeKey.defineProperty("VERIFY",
                          ScriptRuntime.wrapNumber(VERIFY),
                          attr);
        prototypeKey.defineProperty("WRAP",
                          ScriptRuntime.wrapNumber(WRAP),
                          attr);
        prototypeKey.defineProperty("UNWRAP",
                          ScriptRuntime.wrapNumber(UNWRAP),
                          attr);
        prototypeKey.defineProperty("UNWRAP_WRAP",
                          ScriptRuntime.wrapNumber(UNWRAP_WRAP),
                          attr);
        prototypeKey.defineProperty("CRT_P",
                          ScriptRuntime.wrapNumber(CRT_P),
                          attr);
        prototypeKey.defineProperty("CRT_Q",
                          ScriptRuntime.wrapNumber(CRT_Q),
                          attr);
        prototypeKey.defineProperty("CRT_DP1",
                          ScriptRuntime.wrapNumber(CRT_DP1),
                          attr);
        prototypeKey.defineProperty("CRT_DQ1",
                          ScriptRuntime.wrapNumber(CRT_DQ1),
                          attr);
        prototypeKey.defineProperty("CRT_PQ",
                          ScriptRuntime.wrapNumber(CRT_PQ),
                          attr);
        prototypeKey.defineProperty("EXPONENT",
                          ScriptRuntime.wrapNumber(EXPONENT),
                          attr);
        prototypeKey.defineProperty("MODULUS",
                          ScriptRuntime.wrapNumber(MODULUS),
                          attr);
        prototypeKey.defineProperty("DES",
                      ScriptRuntime.wrapNumber(DES),
                      attr);
        if (sealed) { prototypeKey.sealObject(); }
        ScriptableObject.defineProperty(scope, "Key", prototypeKey,
                                        ScriptableObject.DONTENUM);
  }

   public NativeKey()
   {
   }

   //used when don't know blob
    public NativeKey(String index)
    {
        strIndex=index;
        strBlob="00000000000000000000000000000000";
        bExternal = false;
        setObjectPrototype();
    }


   public NativeKey(String index, String profileId, String blob, String external)
   {
       strIndex=index;
       strBlob=blob;
       if( external.equalsIgnoreCase("True"))
           bExternal = true;
       else
           bExternal = false;

       ProfileID = new NativeByteString(profileId, (new Integer(GPConstant.HEX)));
   }

   public NativeKey(String index,String blob, String external, KeyProfile keyProfileObj)
   {
       strIndex=index;
       strBlob=blob;
       if( external.equalsIgnoreCase("True"))
           bExternal = true;
       else
           bExternal = false;
       //init the property according to the keyProfile
       String strUpper="";
       if(keyProfileObj.Attribute.Permanent!=null)
       {
           strUpper=keyProfileObj.Attribute.Permanent.toUpperCase();
           if(strUpper.equals("TRUE"))
               Attribute=new Integer(PERMANENT);
           else
               Attribute=new Integer(0x00);
       }
       else
           Attribute=null;

       if(keyProfileObj.Blob.BlobFormat!=null)
       {
           strUpper=keyProfileObj.Blob.BlobFormat.toUpperCase();
           if(strUpper.equals("NONE"))
               BlobFormat=new Integer(NONE);
           else if (strUpper.equals("CRT_PRIVATE"))
               BlobFormat=new Integer(CRT_PRIVATE);
           else if (strUpper.equals("OTHER"))
               BlobFormat=new Integer(OTHER);
       }
       else
           BlobFormat=null;

       if(keyProfileObj.KeyInfo.Size!=null)
           Size=Integer.valueOf(keyProfileObj.KeyInfo.Size);
       else
           Size=null;

       if(keyProfileObj.KeyInfo.Type!=null)
       {
           strUpper=keyProfileObj.KeyInfo.Type.toUpperCase();
           if(strUpper.equals("SECRET"))
               Type=new Integer(SECRET);
           else if (strUpper.equals("PRIVATE"))
               Type=new Integer(PRIVATE);
           else if (strUpper.equals("PUBLIC"))
               Type=new Integer(PUBLIC);
       }
       else
           BlobFormat=null;

       int iUsage=0;
       if(keyProfileObj.Usage.Encrypt!=null)
           if(keyProfileObj.Usage.Encrypt.equals("true"))
               iUsage=iUsage|ENCRYPT;
       if(keyProfileObj.Usage.Decrypt!=null)
           if(keyProfileObj.Usage.Decrypt.equals("true"))
               iUsage=iUsage|DECRYPT;
       if(keyProfileObj.Usage.DecryptEncrypt!=null)
           if(keyProfileObj.Usage.DecryptEncrypt.equals("true"))
               iUsage=iUsage|DECRYPT_ENCRYPT;
       if(keyProfileObj.Usage.Derive!=null)
           if(keyProfileObj.Usage.Derive.equals("true"))
               iUsage=iUsage|DERIVE;
       if(keyProfileObj.Usage.Sign!=null)
           if(keyProfileObj.Usage.Sign.equals("true"))
               iUsage=iUsage|SIGN;
       if(keyProfileObj.Usage.Unwrap!=null)
           if(keyProfileObj.Usage.Unwrap.equals("true"))
               iUsage=iUsage|UNWRAP;
       if(keyProfileObj.Usage.UnwrapWrap!=null)
           if(keyProfileObj.Usage.UnwrapWrap.equals("true"))
               iUsage=iUsage|UNWRAP_WRAP;
       if(keyProfileObj.Usage.Verify!=null)
           if(keyProfileObj.Usage.Verify.equals("true"))
               iUsage=iUsage|VERIFY;
       if(keyProfileObj.Usage.Wrap!=null)
           if(keyProfileObj.Usage.Wrap.equals("true"))
               iUsage=iUsage|WRAP;
       Usage=new Integer(iUsage);

       if(keyProfileObj.KeyInfo.Version!=null)
           Version=Integer.valueOf(keyProfileObj.KeyInfo.Version);
       else
           Version=null;

       if(keyProfileObj.KeyInfo.StartDate!=null)
       {
           strUpper=keyProfileObj.KeyInfo.StartDate.replace('_','\0');
           StartDate=new NativeByteString(strUpper,new Integer(GPConstant.HEX));
       }
       else
           StartDate=null;

       if(keyProfileObj.KeyInfo.EndDate!=null)
       {
           strUpper=keyProfileObj.KeyInfo.EndDate.replace('_','\0');
           EndDate=new NativeByteString(strUpper,new Integer(GPConstant.HEX));
       }
       else
           EndDate=null;

       if(keyProfileObj.KeyInfo.Owner!=null)
           Owner=new NativeByteString(keyProfileObj.KeyInfo.Owner,new Integer(GPConstant.HEX));
       else
           Owner=null;

       if(keyProfileObj.UniqueID!=null)
           ProfileID=new NativeByteString(keyProfileObj.UniqueID,new Integer(GPConstant.HEX));
       else
           ProfileID=null;

   }

   public boolean isExternal()
   {
     return bExternal;
   }

   public String getClassName() { return "Key"; }

   public void setObjectPrototype()
   {
        setPrototype(prototypeKey);
//        setParentScope(prototypeKey.getParentScope());
   }


    public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope,
                             Scriptable thisObj, Object[] args)
    {
        if (!f.hasTag(KEY_TAG)) {
            return super.execIdCall(f, cx, scope, thisObj, args);
        }

        int id = f.methodId();
        switch (id)
        {
/*            case ConstructorId_fromCharCode:
                    return jsStaticFunction_fromCharCode(args);
            case Id_constructor:
                    return jsConstructor(args, thisObj == null);
*/          case Id_getAttribute: return realThis(thisObj, f).jsFunction_getAttribute(cx, scope, args);
            case Id_getBlob: return realThis(thisObj, f).jsFunction_getBlob(cx, scope, args);
            case Id_getBlobFormat: return realThis(thisObj, f).jsFunction_getBlobFormat(cx, scope, args);
            case Id_getEnd: return realThis(thisObj, f).jsFunction_getEnd(cx, scope, args);
            case Id_getComponent: return realThis(thisObj, f).jsFunction_getComponent(cx, scope, args);
            case Id_getKcv: return realThis(thisObj, f).jsFunction_getKcv(cx, scope, args);
            case Id_getOwner: return realThis(thisObj, f).jsFunction_getOwner(cx, scope, args);
            case Id_getProfileID: return realThis(thisObj, f).jsFunction_getProfileID(cx, scope, args);
            case Id_getSize: return realThis(thisObj, f).jsFunction_getSize(cx, scope, args);
            case Id_getStart: return realThis(thisObj, f).jsFunction_getStart(cx, scope, args);
            case Id_getType: return realThis(thisObj, f).jsFunction_getType(cx, scope, args);
            case Id_getUsage: return realThis(thisObj, f).jsFunction_getUsage(cx, scope, args);
            case Id_getVersion: return realThis(thisObj, f).jsFunction_getVersion(cx, scope, args);
            case Id_getWrapKey:
                realThis(thisObj, f).jsFunction_getWrapKey(cx, scope, args);
                return null;
            case Id_setAttribute:
                realThis(thisObj, f).jsFunction_setAttribute(cx, scope, args);
                return null;
            case Id_setBlobFormat:
                realThis(thisObj, f).jsFunction_setBlobFormat(cx, scope, args);
                return null;
            case Id_setEnd:
                realThis(thisObj, f).jsFunction_setEnd(cx, scope, args);
                return null;
            case Id_setComponent:
                realThis(thisObj, f).jsFunction_setComponent(cx, scope, args);
                return null;
            case Id_setOwner:
                realThis(thisObj, f).jsFunction_setOwner(cx, scope, args);
                return null;
            case Id_setSize:
                realThis(thisObj, f).jsFunction_setSize(cx, scope, args);
                return null;
            case Id_setStart:
                realThis(thisObj, f).jsFunction_setStart(cx, scope, args);
                return null;
            case Id_setType:
                realThis(thisObj, f).jsFunction_setType(cx, scope, args);
                return null;
            case Id_setUsage:
                realThis(thisObj, f).jsFunction_setUsage(cx, scope, args);
                return null;
            case Id_setVersion:
                realThis(thisObj, f).jsFunction_setVersion(cx, scope, args);
                return null;
            case Id_setWrapKey:
                realThis(thisObj, f).jsFunction_setWrapKey(cx, scope, args);
                return null;

        }
        throw new IllegalArgumentException(String.valueOf(id));
    }

    //thisObj
    private NativeKey realThis(Scriptable thisObj, IdFunctionObject f)
    {
      if (! (thisObj instanceof NativeKey))
        throw incompatibleCallError(f);
      return (NativeKey) thisObj;
    }

    private static String jsStaticFunction_fromCharCode(Object[] args)
    {
        int N = args.length;
        if (N < 1)
            return "";
        StringBuffer s = new java.lang.StringBuffer(N);
        for (int i=0; i < N; i++)
        {
            s.append(ScriptRuntime.toUint16(args[i]));
        }
        return s.toString();
    }
/*
    private static Object jsConstructor(Object[] args, boolean inNewExpr)
    {
        if(args.length==1)  //name
            return new NativeKey((String)args[0]);
        if(args.length==2)   //name+blob
           return new NativeKey((String)args[0],(String)args[1]);
        if(args.length==3)   //name+blob+keyProfile
           return new NativeKey((String)args[0],(String)args[1],(KeyProfile)args[2]);
        return new NativeKey();
    }

    //for return a new ByteString from a ByteString Object
    public static NativeKey newKey(Context cx, Scriptable scope,NativeKey sNew)
    {
        scope = getTopLevelScope(scope);
        Scriptable result = ScriptRuntime.newObject(cx, scope, "Key", null);
        NativeKey NewKey=(NativeKey)result;
        NewKey.strIndex=sNew.strIndex;
        return NewKey;
    }
*/

/*********************************************/
/*			all function					 */
/*********************************************/

    public NativeByteString getBlob()
    {
        //String str= GP_Global.getKey(strIndex);  //from DB
        String str= strBlob;  //from native property
        NativeByteString sNew=new NativeByteString(str,new Integer(GPConstant.HEX));
        return sNew;
    }

    public NativeByteString getComponent(int component)
    {
        NativeByteString sNew;
        switch(component)
        {
            case CRT_P:
                //here should call the interface of jinsong
                sNew = new NativeByteString("f75e80839b9b9379f1cf1128f321639757dba514642c206bbbd99f9a4846208b3e93fbbe5e0527cc59b1d4b929d9555853004c7c8b30ee6a213c3d1bb7415d03",new Integer(GPConstant.HEX));
                break;
            case CRT_Q:
                sNew = new NativeByteString("b892d9ebdbfc37e397256dd8a5d3123534d1f03726284743ddc6be3a709edb696fc40c7d902ed804c6eee730eee3d5b20bf6bd8d87a296813c87d3b3cc9d7947",new Integer(GPConstant.HEX));
                break;
            case CRT_DP1:
                sNew = new NativeByteString("1d1a2d3ca8e52068b3094d501c9a842fec37f54db16e9a67070a8b3f53cc03d4257ad252a1a640eadd603724d7bf3737914b544ae332eedf4f34436cac25ceb5",new Integer(GPConstant.HEX));
                break;
            case CRT_DQ1:
                sNew = new NativeByteString("6c929e4e81672fef49d9c825163fec97c4b7ba7acb26c0824638ac22605d7201c94625770984f78a56e6e25904fe7db407099cad9b14588841b94f5ab498dded",new Integer(GPConstant.HEX));
                break;
            case CRT_PQ:
                sNew = new NativeByteString("dae7651ee69ad1d081ec5e7188ae126f6004ff39556bde90e0b870962fa7b926d070686d8244fe5a9aa709a95686a104614834b0ada4b10f53197a5cb4c97339",new Integer(GPConstant.HEX));
                break;
            case EXPONENT:
                sNew = new NativeByteString("92e08f83cc9920746989ca5034dcb384a094fb9c5a6288fcc4304424ab8f56388f72652d8fafc65a4b9020896f2cde297080f2a540e7b7ce5af0b3446e1258d1dd7f245cf54124b4c6e17da21b90a0ebd22605e6f45c9f136d7a13eaac1c0f7487de8bd6d924972408ebb58af71e76fd7b012a8d0e165f3ae2e5077a8648e619",new Integer(GPConstant.HEX));
                break;
            case MODULUS:
                sNew = new NativeByteString("b259d2d6e627a768c94be36164c2d9fc79d97aab9253140e5bf17751197731d6f7540d2509e7b9ffee0a70a6e26d56e92d2edd7f85aba85600b69089f35f6bdbf3c298e05842535d9f064e6b0391cb7d306e0a2d20c4dfb4e7b49a9640bdea26c10ad69c3f05007ce2513cee44cfe01998e62b6c3637d3fc0391079b26ee36d5",new Integer(GPConstant.HEX));
                break;
            case DES:
                 sNew = new NativeByteString(strBlob,new Integer(GPConstant.HEX));
                  break;

            default:
                sNew=new NativeByteString("00",new Integer(GPConstant.HEX));
        }
        return sNew;
    }

    public void getWrapKey(NativeKey wrapKey)
    {
        //set the property of the wraped key
       wrapKey.strBlob = this.wrapKey.strBlob;
       wrapKey.ProfileID = this.wrapKey.ProfileID;
       wrapKey.bExternal = this.wrapKey.bExternal;
    }

    public void setWrapKey(NativeKey wrapKey)
    {
       this.wrapKey = wrapKey;
    }

    public String getProfileId()
    {
      return ProfileID.toString();
    }
/*************************************************/
/*GP function									 */
/*throw exception according to the parameter******/
/*************************************************/
    private Number jsFunction_getAttribute(Context cx, Scriptable scope,Object[] args)
    {
        return Attribute;
    }

    private NativeByteString jsFunction_getBlob(Context cx, Scriptable scope,Object[] args)
    {
        //String str= GP_Global.getKey(strIndex);  //from DB
        String str= strBlob;  //from native property
        NativeByteString sNew=new NativeByteString(str,new Integer(GPConstant.HEX));
        return NativeByteString.newByteString(cx,scope,sNew);
    }

    private Number jsFunction_getBlobFormat(Context cx, Scriptable scope,Object[] args)
    {
        return BlobFormat;
    }

    private NativeByteString jsFunction_getComponent(Context cx, Scriptable scope,Object[] args)
    {
        NativeByteString sNew=getComponent(((Number)args[0]).intValue());
        return NativeByteString.newByteString(cx,scope,sNew);
    }

    private NativeByteString jsFunction_getEnd(Context cx, Scriptable scope,Object[] args)
    {
        return NativeByteString.newByteString(cx,scope,EndDate);
    }

    private NativeByteString jsFunction_getKcv(Context cx, Scriptable scope,Object[] args)
    {
        //compute the kcv of this key
        Integer mech=new Integer(NativeCrypto.DES_ECB);
        NativeByteString data=new NativeByteString("0000000000000000",new Integer(GPConstant.HEX));
        NativeByteString iv=new NativeByteString("0000000000000000",new Integer(GPConstant.HEX));
       // NativeKey key=new NativeKey(strIndex);
       // NativeByteString sNew=NativeCrypto.encrypt(key,mech,data,iv);
        NativeByteString sNew=NativeCrypto.encrypt(this,mech,data,iv);
        return NativeByteString.newByteString(cx,scope,sNew);
    }
    
    public static String getKcv(String strDecryptedKey){
        //compute the kcv of this key
        //Integer mech=new Integer(NativeCrypto.DES_ECB);
        String data="0000000000000000";
       // NativeByteString iv=new NativeByteString("0000000000000000",new Integer(GPConstant.HEX));
       // NativeKey key=new NativeKey(strIndex);
       // NativeByteString sNew=NativeCrypto.encrypt(key,mech,data,iv);
        String sNew=WD3DesCryptoUtil.ecb_encrypt(strDecryptedKey, data, Padding.NoPadding);
        return sNew;
    }
    

    private NativeByteString jsFunction_getOwner(Context cx, Scriptable scope,Object[] args)
    {
        return NativeByteString.newByteString(cx,scope,Owner);
    }

    private NativeByteString jsFunction_getProfileID(Context cx, Scriptable scope,Object[] args)
    {
        return NativeByteString.newByteString(cx,scope,ProfileID);
    }

    private Number jsFunction_getSize(Context cx, Scriptable scope,Object[] args)
    {
        return Size;
    }

    private NativeByteString jsFunction_getStart(Context cx, Scriptable scope,Object[] args)
    {
        return NativeByteString.newByteString(cx,scope,StartDate);
    }

    private Number jsFunction_getType(Context cx, Scriptable scope,Object[] args)
    {
        return Type;
    }

    private Number jsFunction_getUsage(Context cx, Scriptable scope,Object[] args)
    {
        return Usage;
    }

    private Number jsFunction_getVersion(Context cx, Scriptable scope,Object[] args)
    {
        return Version;
    }

    //called by NativeCrypto.unwrap()/unwrapWrap()
    private void jsFunction_getWrapKey(Context cx, Scriptable scope,Object[] args)
    {
        if(!(args[0] instanceof NativeKey))
            throw new EvaluatorException( ( new GPError("Key", GPError.INVALID_KEY)).toString());
        else
           getWrapKey((NativeKey)args[0]);
    }

    private void jsFunction_setAttribute(Context cx, Scriptable scope,Object[] args)
    {
        if(!(args[0] instanceof Number))
            throw new EvaluatorException( ( new GPError("Key", GPError.INVALID_TYPE)).toString());
        if(((Number)args[0]).intValue()!=PERMANENT)
            throw new EvaluatorException ( ( new GPError("Key", 0, 0, "Invalid value")).toString());
        if(Attribute!=null)
            throw new EvaluatorException ( ( new GPError("Key", 0, 0, "Attibute has been defined in the key profile")).toString());
        Attribute=(Number)args[0];
    }

    private void jsFunction_setBlobFormat(Context cx, Scriptable scope,Object[] args)
    {
        if(!(args[0] instanceof Number))
            throw new EvaluatorException( ( new GPError("Key", GPError.INVALID_TYPE)).toString());
        if((((Number)args[0]).intValue()!=NONE)&(((Number)args[0]).intValue()!=CRT_PRIVATE)&(((Number)args[0]).intValue()!=OTHER))
            throw new EvaluatorException ( ( new GPError("Key", 0, 0, "Invalid value")).toString());
        if(BlobFormat!=null)
            throw new EvaluatorException ( ( new GPError("Key", 0, 0, "Attibute has been defined in the key profile")).toString());
        BlobFormat=(Number)args[0];
    }

    private void jsFunction_setComponent(Context cx, Scriptable scope,Object[] args)
    {
      if( args.length != 2 || !(args[0] instanceof Number) || !(args[1] instanceof NativeByteString))
        throw new EvaluatorException( ( new GPError("Key", GPError.INVALID_TYPE)).toString());

      switch(((Number)args[0]).intValue())
      {
          case DES:
            strBlob = ((NativeByteString)args[1]).toString();
          default:
             break;

      }
    }

    private void jsFunction_setEnd(Context cx, Scriptable scope,Object[] args)
    {
        if(!(args[0] instanceof NativeByteString))
            throw new EvaluatorException( ( new GPError("Key", GPError.INVALID_TYPE)).toString());
        if(((NativeByteString)args[0]).length!=4) //the format is not "yyyymmdd"
            throw new EvaluatorException( ( new GPError("Key", 0, 0, "Invalid value")).toString());
        if(EndDate!=null)
            throw new EvaluatorException( ( new GPError("Key", 0, 0, "Attibute has been defined in the key profile")).toString());
        EndDate=(NativeByteString)args[0];
    }

    private void jsFunction_setOwner(Context cx, Scriptable scope,Object[] args)
    {
        if(!(args[0] instanceof NativeByteString))
            throw new EvaluatorException( ( new GPError("Key", GPError.INVALID_TYPE)).toString());
        if(Owner!=null)
            throw new EvaluatorException( ( new GPError("Key", 0, 0, "Attibute has been defined in the key profile")).toString());
        Owner=(NativeByteString)args[0];
    }

    private void jsFunction_setSize(Context cx, Scriptable scope,Object[] args)
    {
        if(!(args[0] instanceof Number))
            throw new EvaluatorException( ( new GPError("Key", GPError.INVALID_KEY)).toString());
        if(Size!=null)
            throw new EvaluatorException( ( new GPError("Key", 0, 0, "Attibute has been defined in the key profile")).toString());
        Size=(Number)args[0];
    }

    private void jsFunction_setStart(Context cx, Scriptable scope,Object[] args)
    {
        if(!(args[0] instanceof NativeByteString))
            throw new EvaluatorException( ( new GPError("Key", GPError.INVALID_TYPE)).toString());
        if(((NativeByteString)args[0]).length!=4) //the format is not "yyyymmdd"
            throw new EvaluatorException(  ( new GPError("Key", 0, 0, "Invalid value")).toString());
        if(StartDate!=null)
            throw new EvaluatorException( ( new GPError("Key", 0, 0, "Attibute has been defined in the key profile")).toString());
        StartDate=(NativeByteString)args[0];
    }

    private void jsFunction_setType(Context cx, Scriptable scope,Object[] args)
    {
        if(!(args[0] instanceof Number))
            throw new EvaluatorException( ( new GPError("Key", GPError.INVALID_TYPE)).toString());
        if((((Number)args[0]).intValue()!=SECRET)&(((Number)args[0]).intValue()!=PRIVATE)&(((Number)args[0]).intValue()!=PUBLIC))
            throw new EvaluatorException( ( new GPError("Key", 0, 0, "Invalid value")).toString());
        if(Type!=null)
            throw new EvaluatorException( ( new GPError("Key", 0, 0, "Attibute has been defined in the key profile")).toString());
        Type=(Number)args[0];
    }

    private void jsFunction_setUsage(Context cx, Scriptable scope,Object[] args)
    {
        if(!(args[0] instanceof Number))
            throw new EvaluatorException( ( new GPError("Key", GPError.INVALID_TYPE)).toString());
        if(Usage!=null)
            throw new EvaluatorException( ( new GPError("Key", 0, 0, "Attibute has been defined in the key profile")).toString());
        Usage=(Number)args[0];
    }

    private void jsFunction_setVersion(Context cx, Scriptable scope,Object[] args)
    {
        if(!(args[0] instanceof Number))
            throw new EvaluatorException( ( new GPError("Key", GPError.INVALID_TYPE)).toString());
        if(Version!=null)
            throw new EvaluatorException( ( new GPError("Key", 0, 0, "Attibute has been defined in the key profile")).toString());
        Version=(Number)args[0];
    }

    private void jsFunction_setWrapKey(Context cx, Scriptable scope,Object[] args)
    {
      if(!(args[0] instanceof NativeKey))
          throw new EvaluatorException( ( new GPError("Key", GPError.INVALID_KEY)).toString());
      else
         setWrapKey((NativeKey)args[0]);
    }


//**************************************************/

    protected int maxInstanceId()
    {
        return 0;
    }

    protected void initPrototypeId(int id)
    {
        String s;
        int arity;
        switch (id)
        {
            //function
//            case ConstructorId_fromCharCode: arity = 0;s ="fromCharCode"; break;
//            case Id_constructor:             arity = 1;s ="constructor"; break;
            case Id_getAttribute: arity = 0;s ="getAttribute"; break;
            case Id_getBlob: arity = 0;s ="getBlob"; break;
            case Id_getBlobFormat: arity = 0;s = "getBlobFormat"; break;
            case Id_getEnd: arity = 0;s = "getEnd"; break;
            case Id_getComponent: arity = 1;s = "getComponent"; break;
            case Id_getKcv: arity = 1;s = "getKcv"; break;
            case Id_getOwner: arity = 0;s = "getOwner"; break;
            case Id_getProfileID: arity = 0;s = "getProfileID"; break;
            case Id_getSize: arity = 0;s = "getSize"; break;
            case Id_getStart: arity = 0;s ="getStart"; break;
            case Id_getType: arity = 0;s ="getType"; break;
            case Id_getUsage: arity = 0;s ="getUsage"; break;
            case Id_getVersion: arity = 0;s ="getVersion"; break;
            case Id_getWrapKey: arity = 1;s ="getWrapKey"; break;
            case Id_setAttribute: arity = 1;s ="setAttribute"; break;
            case Id_setBlobFormat: arity = 1;s ="setBlobFormat"; break;
            case Id_setEnd: arity = 1;s ="setEnd"; break;
            case Id_setComponent: arity = 2;s ="setComponent"; break;
            case Id_setOwner: arity = 1;s ="setOwner"; break;
            case Id_setSize: arity = 1;s ="setSize"; break;
            case Id_setStart: arity = 1;s ="setStart"; break;
            case Id_setType: arity = 1;s ="setType"; break;
            case Id_setUsage: arity = 1;s ="setUsage"; break;
            case Id_setVersion: arity = 1;s ="setVersion"; break;
            case Id_setWrapKey: arity = 1;s ="setWrapKey"; break;

          default: throw new IllegalArgumentException(String.valueOf(id));
        }
        initPrototypeMethod(KEY_TAG, id, s, arity);
    }


    protected int findPrototypeId(String s)
    {
        int id=0;
        String X = null;
        if(s.equals("getBlob")) {X="getBlob";id=Id_getBlob;}
        if(s.equals("getSize")) {X="getSize";id=Id_getSize;}
        if(s.equals("getType")) {X="getType";id=Id_getType;}
        if(s.equals("setSize")) {X="setSize";id=Id_setSize;}
        if(s.equals("setType")) {X="setType";id=Id_setType;}
        if(s.equals("getOwner")) {X="getOwner";id=Id_getOwner;}
        if(s.equals("getKcv")) {X="getKcv";id=Id_getKcv;}
        if(s.equals("getStart")) {X="getStart";id=Id_getStart;}
        if(s.equals("getUsage")) {X="getUsage";id=Id_getUsage;}
        if(s.equals("setOwner")) {X="setOwner";id=Id_setOwner;}
        if(s.equals("setStart")) {X="setStart";id=Id_setStart;}
        if(s.equals("setUsage")) {X="setUsage";id=Id_setUsage;}
        if(s.equals("getVersion")) {X="getVersion";id=Id_getVersion;}
        if(s.equals("getWrapKey")) {X="getWrapKey";id=Id_getWrapKey;}
        if(s.equals("setVersion")) {X="setVersion";id=Id_setVersion;}
        if(s.equals("setWrapKey")) {X="setWrapKey";id=Id_setWrapKey;}
//        if(s.equals("constructor")) {X="constructor";id=Id_constructor;}
        if(s.equals("getAttribute")) {X="getAttribute";id=Id_getAttribute;}
        if(s.equals("getComponent")) {X="getComponent";id=Id_getComponent;}
        if(s.equals("getProfileID")) {X="getProfileID";id=Id_getProfileID;}
        if(s.equals("setAttribute")) {X="setAttribute";id=Id_setAttribute;}
        if(s.equals("setComponent")) {X="setComponent";id=Id_setComponent;}
        if(s.equals("getBlobFormat")) {X="getBlobFormat";id=Id_getBlobFormat;}
        if(s.equals("setBlobFormat")) {X="setBlobFormat";id=Id_setBlobFormat;}

        return id;
    }



    //define value for all function and constant
    private static final int
//        ConstructorId_fromCharCode   =  0,
//        Id_constructor               =  1,
        Id_getAttribute	=	1,
        Id_getBlob	=	2,
        Id_getBlobFormat = 3,
        Id_getEnd = 4,
        Id_getComponent = 5,
        Id_getKcv = 6,
        Id_getOwner = 7,
        Id_getProfileID = 8,
        Id_getSize = 9,
        Id_getStart = 10,
        Id_getType = 11,
        Id_getUsage = 12,
        Id_getVersion = 13,
        Id_getWrapKey = 14,
        Id_setAttribute	=	15,
        Id_setBlobFormat = 16,
        Id_setEnd = 17,
        Id_setComponent = 18,
        Id_setOwner = 19,
        Id_setSize = 20,
        Id_setStart = 21,
        Id_setType = 22,
        Id_setUsage = 23,
        Id_setVersion = 24,
        Id_setWrapKey = 25,
        MAX_PROTOTYPE_ID = 25;



    //for note the strIndex of the key[]
    private String strIndex="";
    public String strBlob="";
    private NativeKey wrapKey;

    private boolean bExternal = false;

    //for the method
    private Number Attribute=null;
    private Number BlobFormat=null;
    private Number Size=null;
    private Number Type=null;
    private Number Usage=null;
    private Number Version=null;
    private NativeByteString StartDate=null;
    private NativeByteString EndDate=null;
    private NativeByteString Owner=null;
    private NativeByteString ProfileID=null;


    public static final int
       PERMANENT =  0x01,
       NONE =  0x01,
       CRT_PRIVATE =  0x02,
       OTHER =  0x04,
       SECRET =  0x01,
       PRIVATE =  0x02,
       PUBLIC =  0x04,
       ENCRYPT =  0x01,
       DECRYPT =  0x02,
       DECRYPT_ENCRYPT =  0x04,
       DERIVE =  0x08,
       GENERATE_KEY =  0x10,
       SIGN =  0x20,
       VERIFY =  0x40,
       WRAP =  0x80,
       UNWRAP =  0x100,
       UNWRAP_WRAP =  0x200,
       CRT_P =  0x01,
       CRT_Q =  0x02,
       CRT_DP1 =  0x04,
       CRT_DQ1 =  0x08,
       CRT_PQ =  0x10,
       EXPONENT =  0x20,
       MODULUS =  0x40,
       DES = 0x80;
    
    
    /*public static void main(String[] args) {
		System.out.println(NativeKey.getKcv("404142434445464748494A4B4C4D4E4F"));
	}*/
}
