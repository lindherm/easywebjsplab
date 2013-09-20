package com.gp.gpscript.script;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;

import com.gp.gpscript.keymgr.util.encoders.Hex;
//import com.watchdata.wdcams.loader.Loader;

/**
 *
 * <p>Title: TLVList</p>
 * <p>Description:
 * This class implements the TLVList (a native object).
 * See [GP_SYS_SCR] 7.2.5 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: watchdata</p>
 * @author SunJinGang
 * @version 1.0
 */
public class NativeTLVList extends IdScriptableObject
{private Logger log = Logger.getLogger(NativeTLVList.class);
    private static final Object TLVLIST_TAG = new Object();
    public static void init(Context cx, Scriptable scope, boolean sealed)
    {
        NativeTLVList obj = new NativeTLVList();
        obj.exportAsJSClass(MAX_PROTOTYPE_ID, scope, sealed);
    }

	public NativeTLVList()
	{
	}

    //GP-constructor
    public NativeTLVList(NativeByteString tlvStream,Number encoding)
    {
        String strBuffer=tlvStream.toString();
        byte[] tlvBuffer=new byte[1000];
        tlvBuffer =Hex.decode(strBuffer);
        byte Encoding=(byte)encoding.byteValue();
        int err=thisTLVList.parseParameters(tlvBuffer,Encoding);
        switch(err)
        {
            case 0:
                break;
            case -1:
                throw new EvaluatorException( ( new GPError("TLVList",0,0,"tlvStream is empty")).toString());
            case -2:
                throw new EvaluatorException( ( new GPError("TLVList",0, 0, "Invalid tag for the encoding method")).toString());
            case -3:
                throw new EvaluatorException( ( new GPError("TLV", 0, 0, "value too large for given encode method")).toString());
            case -4:
                throw new EvaluatorException( ( new GPError("TLV", 0, 0, "cannot decode tlvStream using the specified encoding method")).toString());
            case -5:
                throw new EvaluatorException ( ( new GPError("TLV",0, 0, "unsupported encoding method")).toString());
            case -6:
                throw new EvaluatorException( ( new GPError("TLV", 0, 0, "tlvStream contained two or more TLV's with the same Tag value (at the top level)")).toString());
        }
        thisTLVList = new TLVList(tlvBuffer,Encoding);
    }


    //name used in script
    public String getClassName() {
        return "TLVList";
    }

    protected int getMaxInstanceId()
    {
        return MAX_INSTANCE_ID;
    }

    protected int findInstanceIdInfo(String s)
    {
        if (s.equals("encodingMode")) {
            return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_encodingMode);
        }
        if (s.equals("length")) {
            return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_length);
        }
        return super.findInstanceIdInfo(s);
    }

    protected String getInstanceIdName(int id)
    {
        if (id == Id_encodingMode) { return "encodingMode"; }
        if (id == Id_length) { return "length"; }
        return super.getInstanceIdName(id);
    }

    protected Object getInstanceIdValue(int id)
    {
        if (id == Id_encodingMode) {
            return ScriptRuntime.wrapInt(thisTLVList.encodingMode);
        }
        if (id == Id_length) {
            return ScriptRuntime.wrapInt(thisTLVList.length);
        }
        return super.getInstanceIdValue(id);
    }


    public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope,
                             Scriptable thisObj, Object[] args)
    {
        if (!f.hasTag(TLVLIST_TAG)) {
            return super.execIdCall(f, cx, scope, thisObj, args);
        }

        int id = f.methodId();
        switch (id)
            {
                case Id_constructor:
                    return jsConstructor(args, thisObj == null);

		case Id_toString:
                    return realThis(thisObj, f).jsFunction_toString();

	     		 // use realthis(...) to realize thisobj
		case Id_append:
			realThis(thisObj, f).js_append(cx, scope, args);
                        return null;

		case Id_appendValue:
			realThis(thisObj, f).js_appendValue(cx, scope, args);
                        return null;

		case Id_appendValueIndex:
			realThis(thisObj, f).js_appendValueIndex(cx, scope, args);
                        return null;

		case Id_deleteByIndex:
			realThis(thisObj, f).js_deleteByIndex(cx, scope, args);
                        return null;

		case Id_deleteByTag:
			realThis(thisObj, f).js_deleteByTag(cx, scope, args);
                        return null;

		case Id_find:
                  return realThis(thisObj, f).jsFunction_find(cx, scope, args);

                case Id_findIndex:
			return realThis(thisObj, f).js_findIndex(cx, scope, args);

		case Id_index:
			return realThis(thisObj, f).jsFunction_index(cx, scope, args);

                case Id_toByteString:
                    return realThis(thisObj, f).jsFunction_toByteString(cx, scope, args);

        }
        throw new IllegalArgumentException(String.valueOf(id));
    }

    //thisObj
    private static NativeTLVList realThis(Scriptable thisObj, IdFunctionObject f)
    {
        if (!(thisObj instanceof NativeTLVList))
            throw incompatibleCallError(f);
        return (NativeTLVList)thisObj;
    }

/*
    private static RegExpProxy checkReProxy(Context cx) {
        RegExpProxy result = cx.getRegExpProxy();
        if (result == null) {
            throw cx.reportRuntimeError0("msg.no.regexp");
        }
        return result;
    }
*/

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

    private static Object jsConstructor(Object[] args, boolean inNewExpr)
    {
        if( (args.length==2)&&((args[0] instanceof NativeByteString)&&(args[1] instanceof Number)))
        	return new NativeTLVList((NativeByteString)args[0],(Number)args[1]);
        else  //error
            throw new EvaluatorException( ( new GPError("TLVList", GPError.INVALID_TYPE)).toString());

    }

    public String toString()
    {
        return jsFunction_toString();
    }

    //for display the object
    private String jsFunction_toString()
    {
        byte[] value=new byte[1024];
        value=thisTLVList.toByteString();
        String strValue=new String(Hex.encode(value));
        return strValue;
    }


/*************************************************/
/*GP function									 */
/*throw exception according to the parameter******/
/*************************************************/

    //GP-index(..)
    private NativeTLV jsFunction_index(Context cx, Scriptable scope,Object[] args)
    {
    	if(!(args[0]instanceof Number))
    		throw new EvaluatorException( ( new GPError("TLVList",GPError.INVALID_TYPE)).toString());
    	else
    	{
    		int Index=(int)((Number)args[0]).intValue();
    		if((Index<0)||(Index>=thisTLVList.length))
                   throw new EvaluatorException( ( new GPError("TLVList",0,0, "Invalid Index")).toString());
                byte[] value=new byte[256];
    		TLV MyTLV = new TLV(0x9f05,value,TLV.EMV);

    		MyTLV=thisTLVList.index(Index);
            return NativeTLV.newTLV(cx,scope,MyTLV);
    	}
    }


    //append()
	 public void js_append(Context cx, Scriptable scope,Object[] args)
	 {
           if(args.length==1)   //append(ByteString tlvStream)
           {
             if(!(args[0]instanceof NativeByteString))
                  throw new EvaluatorException( ( new GPError("TLVList", GPError.INVALID_TYPE)).toString());
             else
             {
                 String strValue=((NativeByteString)args[0]).toString();
                 byte[] value=new byte[512];
                 value=Hex.decode(strValue);
                 //parse the append stream
                 int err=thisTLVList.parseParameters(value,thisTLVList.encodingMode);
                 switch(err)
                 {
                     case 0:
                         break;
                     case -1:
                         throw new EvaluatorException( ( new GPError("TLVList", 0, 0, "tlvStream is empty")).toString());
                     case -2:
                         throw new EvaluatorException( ( new GPError("TLVList", 0, 0, "Invalid tag for the encoding method")).toString());
                     case -3:
                         throw new EvaluatorException( ( new GPError("TLVList", 0, 0, "value too large for given encode method")).toString());
                     case -4:
                         throw new EvaluatorException( ( new GPError("TLVList", 0, 0, "cannot decode tlvStream using the specified encoding method")).toString());
                     case -5:
                         throw new EvaluatorException( ( new GPError("TLVList", 0, 0, "unsupported encoding method")).toString());
                     case -6:
                         throw new EvaluatorException ( ( new GPError("TLVList", 0, 0, "tlvStream contained two or more TLV's with the same Tag value (at the top level)")).toString());
                 }
                 String strAllValue=this.toString()+strValue;//the appended string
                 byte[] allValue=new byte[512];
                 allValue=Hex.decode(strAllValue);
                 //parse the append stream+existed stream
                 err=thisTLVList.parseParameters(allValue,thisTLVList.encodingMode);
                 switch(err)
                 {
                     case 0:
                         break;
                     case -1:
                         throw new EvaluatorException ( ( new GPError("TLVList", 0, 0, "tlvStream is empty")).toString());
                     case -2:
                         throw new EvaluatorException ( ( new  GPError("TLVList",0, 0, "Invalid tag for the encoding method")).toString());
                     case -3:
                         throw new EvaluatorException ( ( new GPError("TLVList", 0, 0, "value too large for given encode method")).toString());
                     case -4:
                         throw new EvaluatorException ( ( new GPError("TLVList",0, 0, "cannot decode tlvStream using the specified encoding method")).toString());
                     case -5:
                         throw new EvaluatorException ( ( new GPError("TLVList",0, 0, "unsupported encoding method")).toString());
                     case -6:
                         throw new EvaluatorException ( ( new GPError("TLVList",0, 0, "tlvStream contained two or more TLV's with the same Tag value (at the top level)")).toString());
                 }
                 thisTLVList.append(value);
             }
         }
         else if(args.length==2)
         {
             if(!((args[0]instanceof Number)&&(args[1]instanceof NativeByteString)))
                     { log.error("ss");
                     throw new EvaluatorException ( ( new GPError("TLVList", GPError.INVALID_TYPE)).toString());
                 }
             else
             {
                 int tag=(int)((Number)args[0]).intValue();
                 String strValue=((NativeByteString)args[1]).toString();
                 byte[] value=new byte[512];
                 value=Hex.decode(strValue);
                 int err=thisTLVList.append(tag,value);
                 switch(err)
                 {
                     case 0:
                         break;
                     case -1:
                         throw new EvaluatorException ( ( new GPError("TLVList", 0, 0, "tlvStream is empty")).toString());
                     case -2:
                         throw new EvaluatorException ( ( new GPError("TLVList", 0, 0, "Invalid tag for the encoding method")).toString());
                     case -3:
                         throw new EvaluatorException ( ( new GPError("TLVList", 0, 0, "value too large for given encode method")).toString());
                     case -4:
                         throw new EvaluatorException ( ( new GPError("TLVList", 0, 0, "cannot decode tlvStream using the specified encoding method")).toString());
                     case -5:
                         throw new EvaluatorException ( ( new GPError("TLVList", 0, 0, "unsupported encoding method")).toString());
                     case -6:
                         throw new EvaluatorException ( ( new GPError("TLVList", 0, 0, "tlvStream contained two or more TLV's with the same Tag value (at the top level)")).toString());
                 }
             }
         }
         else  //para is more than 2
              throw new EvaluatorException ( ( new GPError("TLVList",GPError.INVALID_TYPE)).toString());
	 }

     /**
      * Append the data to the existing data for the specified tag
      * @param tag  Tag to be used to be added to the list
      * @param data Data to be added to the existing tag
      * @return
      *     -1 tag is not existing
      *     -2 resultant data value to large for given encoding method
      */
      public void js_appendValue(Context cx, Scriptable scope,Object[] args)
      {
          if(args.length==2)
          {
              if(!((args[0]instanceof Number)&&(args[1]instanceof NativeByteString)))
                  throw new EvaluatorException ( ( new  GPError("TLVList", GPError.INVALID_TYPE)).toString());
              else
              {
                  int tag=(int)((Number)args[0]).intValue();
                  String strValue=((NativeByteString)args[1]).toString();
                  byte[] value=new byte[500];
                  value=Hex.decode(strValue);
                  int err=thisTLVList.appendValue(tag,value);
                  switch(err)
                  {
                      case 0:   //ok
                          break;
                      case -1:
                          throw new EvaluatorException ( ( new GPError("TLVList", 0, 0, "tag is not existing")).toString());
                      case -2:
                          throw new EvaluatorException ( ( new GPError("TLVList", 0, 0, "resultant data value to large for given encoding method")).toString());
                  }
              }
          }
          else  //para is not equal to 2
              throw new EvaluatorException ( ( new GPError("TLVList", GPError.INVALID_TYPE)).toString());
      }


      /**
       * Append the data to the existing data for the specified zero-based index into the TLVStream
       *
       * @param index  Zero-based index into the TLVStream
       * @param data Data to be added to the existing tag
       * @return
       *     -1 tlvStream is empty
       *     -2 resultant data value to large for given encoding method
       */
      public void js_appendValueIndex(Context cx, Scriptable scope,Object[] args)
     {
         if(args.length==2)
         {
             if(!((args[0]instanceof Number)&&(args[1]instanceof NativeByteString)))
                 throw new EvaluatorException ( ( new GPError("TLVList", GPError.INVALID_TYPE)).toString());
             else
             {
                 int tag=(int)((Number)args[0]).intValue();
                 String strValue=((NativeByteString)args[1]).toString();
                 byte[] value=new byte[500];
                 value=Hex.decode(strValue);
                 int err=thisTLVList.appendValueIndex(tag,value);
                 switch(err)
                 {
                     case 0:   //ok
                         break;
                     case -1:
                         throw new EvaluatorException ( ( new GPError("TLVList", 0, 0, "tlvStream is empty")).toString());
                     case -2:
                         throw new EvaluatorException ( ( new GPError("TLVList", 0, 0, "resultant data value to large for given encoding method")).toString());
                 }
             }
         }
         else  //para is not equal to 2
             throw new EvaluatorException ( ( new GPError("TLVList", GPError.INVALID_TYPE)).toString());
      }

	 public void js_deleteByIndex(Context cx, Scriptable scope,Object[] args)
	 {
	 	if(!(args[0]instanceof Number))
    		throw new EvaluatorException ( ( new GPError("TLVList", GPError.INVALID_TYPE)).toString());
    	else
    	{
    		int index=(int)((Number)args[0]).intValue();
    		if(index<0)
                      throw new EvaluatorException ( ( new GPError("TLVList", GPError.INVALID_DATA)).toString());
                if(thisTLVList.length<1)
                      throw new EvaluatorException ( ( new GPError("TLVList", GPError.INVALID_DATA)).toString());
            thisTLVList.deleteByIndex(index);
    	}
	 }

	 public void js_deleteByTag(Context cx, Scriptable scope,Object[] args)
	 {
	 	if(!(args[0]instanceof Number))
                      throw new EvaluatorException ( ( new GPError("TLVList", GPError.INVALID_TYPE)).toString());
                else
                {
                  int tag=(int)((Number)args[0]).intValue();
                  thisTLVList.deleteByTag(tag);
                }
	 }


    private NativeTLV jsFunction_find(Context cx, Scriptable scope,Object[] args)
    {
    	if(!(args[0]instanceof Number))
                      throw new EvaluatorException ( ( new GPError("TLVList", GPError.INVALID_TYPE)).toString());
    	else
    	{
            int ttag=(int)((Number)args[0]).intValue();;
            byte[] value=new byte[200];
            TLV MyTLV = new TLV(0x9f05,value,TLV.EMV);
            MyTLV=thisTLVList.find(ttag);
            if(MyTLV!=null)
                return NativeTLV.newTLV(cx,scope,MyTLV);
            else
                return null;
    	}
    }


    public Number js_findIndex(Context cx, Scriptable scope,Object[] args)
	 {
	 	if(!(args[0]instanceof Number))
                      throw new EvaluatorException ( ( new GPError("TLVList", GPError.INVALID_TYPE)).toString());
                else
                {
                  int tag=(int)((Number)args[0]).intValue();

                  int index=thisTLVList.findIndex(tag);

                  Integer Index=new Integer(index);
                  return Index;
                }
	 }


     public NativeByteString jsFunction_toByteString(Context cx, Scriptable scope, Object[] args)
     {
          byte[] value=new byte[thisTLVList.length];
          value=thisTLVList.toByteString();
          Integer ee=new Integer(GPConstant.HEX);
          String strValue=new String(Hex.encode(value));
          NativeByteString sNew = new NativeByteString(strValue,ee);
          return NativeByteString.newByteString(cx,scope,sNew);
     }

/////////////////////////////////////////////////////////////////////////////

    protected int maxInstanceId()
    	{ return MAX_INSTANCE_ID; }


    protected void initPrototypeId(int id)
    {
        String s;
        int arity;
        switch (id) {
                case Id_constructor:      arity = 2;s = "constructor"; break;
                case Id_toString:         arity = 0;s = "toString"; break;
		case Id_append:		  arity = 2;s = "append"; break;
		case Id_appendValue:	  arity = 2;s = "appendValue"; break;
		case Id_appendValueIndex: arity = 2;s = "appendValueIndex"; break;
		case Id_deleteByIndex:    arity = 1;s = "deleteByIndex"; break;
		case Id_deleteByTag:	  arity = 1;s = "deleteByTag"; break;
		case Id_find:     	  arity = 1;s = "find"; break;
                case Id_findIndex:     	  arity = 1;s = "findIndex"; break;
		case Id_index:     	  arity = 1;s = "index"; break;
		case Id_toByteString:     arity = 0;s = "toByteString"; break;

                default: throw new IllegalArgumentException(String.valueOf(id));
        }
        initPrototypeMethod(TLVLIST_TAG, id, s, arity);
    }

    protected int findPrototypeId(String s)
    {
        int id=0;
        String X = null;
        if(s.equals("find")) { X="find";id=Id_find; }
        if(s.equals("index")) { X="index";id=Id_index; }
        if(s.equals("append")) { X="append";id=Id_append; }
        if(s.equals("toString")) {X="toString";id=Id_toString; }
        if(s.equals("findIndex")) {X="findIndex";id=Id_findIndex; }
        if(s.equals("constructor")) {X="constructor";id=Id_constructor; }
        if(s.equals("appendValue")) {X="appendValue";id=Id_appendValue; }
        if(s.equals("deleteByTag")) {X="deleteByTag";id=Id_deleteByTag;}
        if(s.equals("toByteString")) {X="toByteString";id=Id_toByteString; }
        if(s.equals("deleteByIndex")) {X="deleteByIndex";id=Id_deleteByIndex; }
        if(s.equals("appendValueIndex")) {X="appendValueIndex";id=Id_appendValueIndex; }

        if (X!=null && X!=s && !X.equals(s)) id = 0;
        return id;
    }



    private static final int
	Id_length                    = 1,
        Id_encodingMode              = 2,
        MAX_INSTANCE_ID              = 2;

    private static final int
        Id_constructor               =  MAX_INSTANCE_ID+1,
        Id_toString                  =  MAX_INSTANCE_ID+2,
	Id_append		     =  MAX_INSTANCE_ID+3,
	Id_appendValue	     	     =  MAX_INSTANCE_ID+4,
        Id_appendValueIndex          =  MAX_INSTANCE_ID+5,
        Id_deleteByIndex	     =  MAX_INSTANCE_ID+6,
	Id_deleteByTag   	     =  MAX_INSTANCE_ID+7,
	Id_find	     		     =  MAX_INSTANCE_ID+8,
	Id_findIndex	     	     =  MAX_INSTANCE_ID+9,
	Id_index	     	     =  MAX_INSTANCE_ID+10,
	Id_toByteString	     	     =  MAX_INSTANCE_ID+11,
        MAX_PROTOTYPE_ID             =  MAX_INSTANCE_ID+11;

    private TLVList thisTLVList;
}

