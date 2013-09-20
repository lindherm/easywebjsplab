package com.gp.gpscript.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import com.gp.gpscript.keymgr.util.encoders.Hex;

/**
 *
 * <p>Title: TLV</p>
 * <p>Description:
 * This class implements the TLV (a native object).
 * See [GP_SYS_SCR] 7.2.4 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: watchdata</p>
 * @author SunJinGang
 * @version 1.0
 */
public class NativeTLV extends IdScriptableObject
{
    private static final Object TLV_TAG = new Object();

    public static void init(Context cx, Scriptable scope, boolean sealed)
    {
        NativeTLV obj = new NativeTLV();
        obj.exportAsJSClass(MAX_PROTOTYPE_ID, scope, sealed);
    }

    public NativeTLV()
    {
    }

    //GP-constructor
    public NativeTLV(Number tag,NativeByteString value,Number encoding)
    {
        int Tag=(int)tag.intValue();
        String strValue=value.toString();
        byte[] Value=Hex.decode(strValue);
        byte Encoding=(byte)encoding.byteValue();
        int err=TLV.parseParameters(Tag, Value, Encoding);
        switch(err)
        {
            case -4:
                throw new EvaluatorException( ( new GPError("TLV",0, 0, "unsupport encodingMethod")).toString());
            case -2:
                throw new EvaluatorException( ( new GPError("TLV",0, 0, "value has zero length")).toString());
            case -3:
                throw new EvaluatorException( ( new GPError("TLV",0, 0, "tag does not match encodingMethod")).toString());
            case -1:
                throw new EvaluatorException( ( new GPError("TLV",0, 0, "value too large for the specific encodingMethod")).toString());
        }
        thisTLV = new TLV(Tag,Value,Encoding);
    }


    //name used in script
    public String getClassName() {
        return "TLV";
    }

    //
  protected void fillConstructorProperties(IdFunctionObject ctor)
  {
    final int attr = ScriptableObject.DONTENUM |
        ScriptableObject.PERMANENT |
        ScriptableObject.READONLY;

    ctor.defineProperty("EMV",
                        ScriptRuntime.wrapNumber(EMV),
                        attr);
    ctor.defineProperty("DGI",
                        ScriptRuntime.wrapNumber(DGI),
                        attr);
    ctor.defineProperty("L16",
                        ScriptRuntime.wrapNumber(L16),
                        attr);
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
        if (s.equals("size")) {
            return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_size);
        }
        return super.findInstanceIdInfo(s);
    }

    protected String getInstanceIdName(int id)
    {
        if (id == Id_encodingMode) { return "encodingMode"; }
        if (id == Id_size) { return "size"; }
        return super.getInstanceIdName(id);
    }

    protected Object getInstanceIdValue(int id)
    {
        if (id == Id_encodingMode) {
            return ScriptRuntime.wrapInt(thisTLV.encodingMode);
        }
        if (id == Id_size) {
            return ScriptRuntime.wrapInt(thisTLV.size);
        }
        return super.getInstanceIdValue(id);
    }


    public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope,
                             Scriptable thisObj, Object[] args)
    {
        if (!f.hasTag(TLV_TAG)) {
            return super.execIdCall(f, cx, scope, thisObj, args);
        }

        int id = f.methodId();
        switch (id)
        {
                case Id_constructor:
                    return jsConstructor(args, thisObj == null);

                case Id_toString:
                    return realThis(thisObj, f).jsFunction_toString();

                case Id_getL:
                    // use realthis(...) to realize thisobj
                    return realThis(thisObj, f).jsFunction_getL(cx, scope, args);
                case Id_getLV:
                    return realThis(thisObj, f).jsFunction_getLV(cx, scope, args);
                case Id_getTag:
                    return realThis(thisObj, f).js_getTag();   //Number
                case Id_getTLV:
                    return realThis(thisObj, f).jsFunction_getTLV(cx, scope, args);
                case Id_getTV:
                    return realThis(thisObj, f).jsFunction_getTV(cx, scope, args);
                case Id_getValue:
                    return realThis(thisObj, f).jsFunction_getValue(cx, scope, args);
         }
        throw new IllegalArgumentException(String.valueOf(id));
    }

    //thisObj
    private static NativeTLV realThis(Scriptable thisObj, IdFunctionObject f)
    {
        if (!(thisObj instanceof NativeTLV))
            throw incompatibleCallError(f);
        return (NativeTLV)thisObj;
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

    private static String jsStaticFunction_fromCharCode(Object[] args) {
        int N = args.length;
        if (N < 1)
            return "";
        StringBuffer s = new java.lang.StringBuffer(N);
        for (int i=0; i < N; i++) {
            s.append(ScriptRuntime.toUint16(args[i]));
        }
        return s.toString();
    }

    private static Object jsConstructor(Object[] args, boolean inNewExpr)
    {
        if( (args.length==3)&&(args[0] instanceof Number)&&(args[1] instanceof NativeByteString)&&(args[2] instanceof Number) )
            return new NativeTLV((Number)args[0],(NativeByteString)args[1],(Number)args[2]);
        else  if(args.length==0)
            return new NativeTLV();
        else//error
            throw new EvaluatorException( ( new GPError("TLV", GPError.INVALID_TYPE)).toString());
    }

    public String toString() {
        //return value.toString();
        return jsFunction_toString();
    }

    //for display the object
    private String jsFunction_toString()
    {
        //return value.toString();
        String strValue=new String(Hex.encode(thisTLV.getTLV()));

        return strValue;
    }

/////////////////////////for creat a new object////////////////
    public static NativeTLV newTLV(Context cx, Scriptable scope,NativeTLV newTLV)
    {
        scope = getTopLevelScope(scope);
        Scriptable result = ScriptRuntime.newObject(cx, scope, "TLV", null);
        byte[] ss={(byte)0x02,(byte)0x01,(byte)0x02};
        ((NativeTLV)result).thisTLV=new TLV(0x4F,newTLV.thisTLV.getTLV(),(byte)EMV);
        return (NativeTLV)result;
    }

    //used for NativeTLVList
    public static NativeTLV newTLV(Context cx, Scriptable scope,TLV newTLV)
    {
        scope = getTopLevelScope(scope);
        Scriptable result = ScriptRuntime.newObject(cx, scope, "TLV", null);
        byte[] ss={(byte)0x02,(byte)0x01,(byte)0x02};
        ((NativeTLV)result).thisTLV=newTLV;
        return (NativeTLV)result;
    }


   //GP-getL(..)
    private NativeByteString jsFunction_getL(Context cx, Scriptable scope,Object[] args)
    {
        String strL=new String(Hex.encode(thisTLV.getL()));
        Integer ee=new Integer(GPConstant.HEX);
        NativeByteString sNew=new NativeByteString(strL,ee);
        return NativeByteString.newByteString(cx,scope,sNew);
    }

    //GP-getLV(..)
    private NativeByteString jsFunction_getLV(Context cx, Scriptable scope,Object[] args)
    {
        String strLV=new String(Hex.encode(thisTLV.getLV()));
        Integer ee=new Integer(GPConstant.HEX);
        NativeByteString sNew=new NativeByteString(strLV,ee);
        return NativeByteString.newByteString(cx,scope,sNew);
    }

    //GP-getTag(..)
    public Number js_getTag()
    {
        int tag=thisTLV.getTag();
        Integer Tag=new Integer(tag);
        return Tag;
    }

    //GP-getTLV(..)
    private NativeByteString jsFunction_getTLV(Context cx, Scriptable scope,Object[] args)
    {
        String strTLV=new String(Hex.encode(thisTLV.getTLV()));
        Integer ee=new Integer(GPConstant.HEX);
        NativeByteString sNew=new NativeByteString(strTLV,ee);
        return NativeByteString.newByteString(cx,scope,sNew);
    }

    //GP-getTV(..)
    private NativeByteString jsFunction_getTV(Context cx, Scriptable scope,Object[] args)
    {
        String strTV=new String(Hex.encode(thisTLV.getTV()));
        Integer ee=new Integer(GPConstant.HEX);
        NativeByteString sNew=new NativeByteString(strTV,ee);
        return NativeByteString.newByteString(cx,scope,sNew);
    }

    //GP-getValue(..)
    private NativeByteString jsFunction_getValue(Context cx, Scriptable scope,Object[] args)
    {
        String strValue=new String(Hex.encode(thisTLV.getValue()));
        Integer ee=new Integer(GPConstant.HEX);
        NativeByteString sNew=new NativeByteString(strValue,ee);
        return NativeByteString.newByteString(cx,scope,sNew);
    }

    protected int maxInstanceId()
            { return MAX_INSTANCE_ID; }


    protected void initPrototypeId(int id)
    {
        String s;
        int arity;
        switch (id) {
                case Id_constructor:   arity = 3;s = "constructor"; break;
                case Id_toString:      arity = 0;s = "toString";break;
                case Id_getL:		arity = 0;s = "getL";break;
                case Id_getLV:		arity = 0;s = "getLV";break;
                case Id_getTag:		arity = 0;s = "getTag";break;
                case Id_getTLV:		arity = 0;s = "getTLV";break;
                case Id_getTV:	     	arity = 0;s = "getTV";break;
                case Id_getValue:     	arity = 0;s = "getValue";break;
          default: throw new IllegalArgumentException(String.valueOf(id));
        }
        initPrototypeMethod(TLV_TAG, id, s, arity);
    }


    protected int findPrototypeId(String s)
    {
        int id=0;
        String X = null;
        if(s.equals("getL")) { X="getL";id=Id_getL; }
        if(s.equals("size")) { X="size";id=Id_size; }
        if(s.equals("getLV")) { X="getLV";id=Id_getLV; }
        if(s.equals("getTV")) { X="getTV";id=Id_getTV; }
        if(s.equals("getTag")) { X="getTag";id=Id_getTag; }
        if(s.equals("getTLV")) { X="getTLV";id=Id_getTLV; }
        if(s.equals("toString")) {X="toString";id=Id_toString; }
        if(s.equals("getValue")) {X="getValue";id=Id_getValue; }
        if(s.equals("constructor")) {X="constructor";id=Id_constructor;}
        if(s.equals("encodingMode")) {X="encodingMode";id=Id_encodingMode;}

        return id;
    }


    private static final int
    Id_encodingMode 		     =  1,
    Id_size					     =  2,
    MAX_INSTANCE_ID              =  2;

    private static final int
    Id_constructor               =  MAX_INSTANCE_ID+1,
    Id_toString                  =  MAX_INSTANCE_ID+2,
    Id_getL			     	     =  MAX_INSTANCE_ID+3,
    Id_getLV		     	     =  MAX_INSTANCE_ID+4,
    Id_getTag	     	  		 =  MAX_INSTANCE_ID+5,
    Id_getTLV	     	  		 =  MAX_INSTANCE_ID+6,
    Id_getTV     	 	     	 =  MAX_INSTANCE_ID+7,
    Id_getValue	     		     =  MAX_INSTANCE_ID+8,
    MAX_PROTOTYPE_ID             =  MAX_INSTANCE_ID+8;


    public static final int EMV = 1;
    public static final int DGI = 2;
    public static final int L16 = 3;

    private TLV thisTLV;
}
