package com.gp.gpscript.script;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;

import com.watchdata.commons.lang.WDBase64;
import com.watchdata.commons.lang.WDByteUtil;


/**
 *
 * <p>Title: ByteBuffer</p>
 * <p>Description:
 * This class implements the ByteBuffer (a native object).
 * See [GP_SYS_SCR] 7.2.2 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: watchdata</p>
 * @author SunJinGang
 * @version 1.0
 */
public class NativeByteBuffer extends IdScriptableObject
{private Logger log = Logger.getLogger(NativeByteBuffer.class);
    private static final Object BYTEBUFFER_TAG = new Object();
    public static void init(Context cx, Scriptable scope, boolean sealed)
    {
        NativeByteBuffer obj = new NativeByteBuffer();
        obj.exportAsJSClass(MAX_PROTOTYPE_ID, scope, sealed);
    }

    public NativeByteBuffer(int count)
    {
        value=new byte[count];
        this.length=count;
        this.count=count;
    }

	/**
     * Zero-parameter constructor
     */
    public NativeByteBuffer()
    {
        value=new byte[0];
        length=0;
        count=0;
    }

    /**
     * 1 parameter constructor
     * @param source a ByteString Object of GP
     */
    public NativeByteBuffer(NativeByteString source)
    {
      value=new byte[source.GetLength()];
      length=source.GetLength();
      count=source.GetLength();
      for(int i=0;i<source.GetLength();i++)  value[i]=source.ByteAt(i);
    }

    /**
     * 2 parameter constructor
     * @param str a String object
     * @param encoding a encoding method(HEX/ASCII/BASE64/UTF8/CN)
     */
    public NativeByteBuffer(String str,Number encoding)
    {
        switch((int)encoding.intValue())
        {
           case GPConstant.ASCII:
              strToASCII(str);
              break;
           case GPConstant.HEX:
              strToHEX(str);
              break;
           case GPConstant.UTF8:
              strToUTF8(str);
              break;
           case GPConstant.BASE64:
               strToBase64(str);
               break;
           case GPConstant.CN:
               strToCN(str);
               break;
           default:
              throw new EvaluatorException( (new GPError(getClassName(),GPError.INVALID_TYPE)).toString());
        }
    }

    //encode by ascII
    public void strToASCII(String str)
    {
    	   int strLen;
           strLen=str.length();
    	   value=new byte[strLen];
           for(int i=0;i<strLen;i++)
           {
           	char ch;
           	ch=str.charAt(i);
           	value[i]=(byte)ch;
           }
           length=strLen;
           count=strLen;
    }

    //encode by Hex
    public void strToHEX(String str)
    {
    	int strLen;
        strLen=str.length()/2;
    	value=new byte[strLen];
        value=WDByteUtil.HEX2Bytes(str);
    	length=strLen;
    	count=strLen;
    }


    //encode by BASE64
    public void strToBase64(String str)
    {
        byte[] buf=WDBase64.encode(str.getBytes());
        value=new byte[buf.length];
        System.arraycopy(buf,0,value,0,buf.length);
        length=buf.length;
        count=buf.length;
    }

    //encode by CN
    public void strToCN(String str)
    {
        int strLen;
        strLen=str.length();
        if(strLen%2==1)
        {
            strLen++;
            str=str+"?";
        }
        strLen=strLen/2;
        value=new byte[strLen];
        for(int i=0;i<strLen;i++)
        {
            byte ch1,ch2;
            ch1=(byte)(str.charAt(i*2));
            ch2=(byte)(str.charAt(i*2+1));
            value[i]=(byte)(((ch1<<4)&0xF0)|((ch2)&0x0F));
        }
        length=strLen;
        count=strLen;
    }

    //encode by UTF8
    public void strToUTF8(String str)
    {
        byte[] utf8=null;
        try
        {
            utf8 = str.getBytes("UTF8");
        }
        catch(Exception e)
        {
//            e.printStackTrace();
        	log.error(e.getMessage());
        }
        value=new byte[utf8.length];
        System.arraycopy(utf8,0,value,0,utf8.length);
        length=utf8.length;
        count=utf8.length;
    }


    //name used in script
    public String getClassName()
    {
        return "ByteBuffer";
    }

    protected int findInstanceIdInfo(String s)
    {
        if (s.equals("length")) {
            return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_length);
        }
        return super.findInstanceIdInfo(s);
    }

    protected String getInstanceIdName(int id)
    {
        if (id == Id_length) { return "length"; }
        return super.getInstanceIdName(id);
    }

    protected Object getInstanceIdValue(int id)
    {
        if (id == Id_length) {
            return ScriptRuntime.wrapInt(length);
        }
        return super.getInstanceIdValue(id);
    }

    protected void initPrototypeId(int id)
    {
        String s;
        int arity;
        switch (id) {
                case Id_constructor:   arity = 2;s ="constructor"; break;
                case Id_toString:      arity = 1;s = "toString";break;
                case Id_append:	      arity = 1;s = "append"; break;
                case Id_byteAt:		arity = 1;s = "byteAt"; break;
                case Id_clear:		arity = 0;s ="clear"; break;
                case Id_copy:		arity = 2;s ="copy"; break;
                case Id_find:	     	arity = 2;s = "find"; break;
                case Id_insert:	     	arity = 2;s = "insert"; break;
                case Id_toByteString:	arity = 2;s = "toByteString"; break;

          default: throw new IllegalArgumentException(String.valueOf(id));
        }
        initPrototypeMethod(BYTEBUFFER_TAG, id, s, arity);
    }

    public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope,
                             Scriptable thisObj, Object[] args)
    {
        if (!f.hasTag(BYTEBUFFER_TAG)) {
            return super.execIdCall(f, cx, scope, thisObj, args);
        }

        int id = f.methodId();
        switch (id)
            {
                case Id_constructor:
                    return jsConstructor(args, thisObj == null);

                case Id_toString:
                    return realThis(thisObj, f).jsFunction_toString();

                case Id_append:
                    // use realthis(...) to realize thisobj
                    return realThis(thisObj, f).js_append(cx, scope, args);
                case Id_byteAt:
                    return realThis(thisObj, f).js_byteAt(cx, scope, args);
                case Id_clear:
                    return realThis(thisObj, f).js_clear(cx, scope, args);
                case Id_copy:
                    return realThis(thisObj, f).js_copy(cx, scope, args);
                case Id_find:
                    return realThis(thisObj, f).js_find(cx, scope, args);
                case Id_insert:
                    return realThis(thisObj, f).js_insert(cx, scope, args);
                case Id_toByteString:
                    //return realThis(thisObj, f).js_toByteString(args);
                    return realThis(thisObj, f).jsFunction_toByteString(cx, scope, args);
            }
           throw new IllegalArgumentException(String.valueOf(id));
    }

    //thisObj
    private NativeByteBuffer realThis(Scriptable thisObj, IdFunctionObject f)
    {
        if (!(thisObj instanceof NativeByteBuffer))
            throw incompatibleCallError(f);
        return (NativeByteBuffer)thisObj;
    }
/*
    private static RegExpProxy checkReProxy(Context cx)
    {
        RegExpProxy result = cx.getRegExpProxy();
        if (result == null) {
            throw cx.reportRuntimeError0("msg.no.regexp");
        }
        return result;
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
*/
    private static Object jsConstructor(Object[] args, boolean inNewExpr)
    {
        if(args.length<1)
            return new NativeByteBuffer();
        else if((args[0] instanceof NativeByteString)&&(args.length==1))
            return new NativeByteBuffer((NativeByteString)args[0]);
        else if((args.length==2)&&((args[0] instanceof String)||(args[0] instanceof String))&&(args[1] instanceof Number))
        {
            Integer ee=new Integer(ScriptRuntime.toInt32(args[1]));
            return new NativeByteBuffer(ScriptRuntime.toString(args[0]),ee);
        }
        else  //error
            throw new EvaluatorException( (new GPError("ByteBuffer",GPError.INVALID_TYPE)).toString());
    }

    public String toString()
    {
        //return value.toString();
        return jsFunction_toString();
    }

    //for display the object
    private String jsFunction_toString()
      {
        //return value.toString();
          StringBuffer result = new StringBuffer();
          for(int i=0;i<this.count;i++)
          {
            //!!!!!!!!!should be improved here(too complex)
            String strHex;
            strHex=Integer.toHexString(value[i]).toUpperCase();
            if(strHex.length()==1)
              strHex="0"+strHex;
            if(strHex.length()>2)
              strHex=strHex.substring(strHex.length()-2,strHex.length());
            result.append(strHex);
          }
          return result.toString();
      }

/*********************************************/
/*			all function					 */
/*********************************************/
/**
 * Expand the capacity of the ByteBuffer
 * @param minimumCapacity the expected capacity
 */
    private void expandCapacity(int minimumCapacity)
    {
        int newCapacity;
        newCapacity = (minimumCapacity >count)? minimumCapacity : count;

        byte newValue[] = new byte[newCapacity];
        System.arraycopy(value, 0, newValue, 0, count);
        value = newValue;
        count=newCapacity;
        length=newCapacity;
    }

    /**
     * append(ByteBuffer source)
     * @param source a ByteBuffer object
     * @return this ByteBuffer object
     */
    private NativeByteBuffer append(NativeByteBuffer source)
    {
       int tempCount = count;
       expandCapacity(count+source.count);
       System.arraycopy(source.value, 0, value, tempCount, source.count);
       return this;
    }

    /**
     * append(ByteString source)
     * @param source a ByteString object
     * @return this ByteBuffer object
     */
    private NativeByteBuffer append(NativeByteString source)
    {
       int tempCount=count;
       expandCapacity(count+source.GetLength());
       for(int i=0;i<source.GetLength();i++)	value[tempCount+i]=source.ByteAt(i);
       return this;
    }

    /**
     * append(String source)
     * @param source a String Object(the encoding method is HEX default)
     * @return this ByteBuffer object
     */
    private NativeByteBuffer append(String source)
    {
    	Integer ee=new Integer(GPConstant.HEX);
        NativeByteBuffer ss = new NativeByteBuffer(source,ee);
        return append(ss);
    }

    /**
     * Number byteAt(Number offset)
     * @param offset the zero-based of this buffer
     * @return the unsigned value of the specified byte
     */
    private Number byteAt(Number offset)
    {
        int index=(int)offset.intValue();

        byte bb=value[index];
        int nn=(int)bb;
        if(nn<0) nn=nn+256;   //to unsigned value
        Integer result=new Integer(nn);
        return result;
    }

    /**
     * clear all
     * @return this byteBuffer
     */
    private NativeByteBuffer clear()
    {
       count=0;
       length=0;
       return this;
    }

    /**
     * NativeByteBuffer clear(Number offset)
     * @param offset the zero-based offset
     * @return this object
     */
    private NativeByteBuffer clear(Number offset)
    {
       int index=(int)offset.intValue();
       count=index;
       length=index;
       return this;
    }

    /**
     * NativeByteBuffer clear(Number offset,Number count)
     * @param offset the zero-based offset
     * @param count The number of bytes to delete
     * @return this object
     */
    private NativeByteBuffer clear(Number offset,Number count)
    {
       int start=(int)offset.intValue();
       int len=(int)count.intValue();

       System.arraycopy(value, start+len, value, start, this.count-start-len);
       this.count -= len;
       length -= len;

       return this;
    }

    /**
     * copy a ByteBuffer to the position sepecified by at
     * @param at zero-based index
     * @param source a ByteBuffer object
     * @return the same referenceto this ByteBuffer
     */
    private NativeByteBuffer copy(Number at,NativeByteBuffer source)
    {
       int offset = (int)at.intValue();

       if((offset+source.count)>this.count)
  	throw new EvaluatorException( (new GPError("ByteBuffer",0, 0, "at+count exceeds the length of the current buffer")).toString());

       System.arraycopy(source.value, 0, value, offset, source.count);
       return this;
    }

    /**
     * copy a ByteString to the position sepecified by at
     * @param at zero-based index
     * @param source a ByteString object
     * @return the same referenceto this ByteBuffer
     */
    private NativeByteBuffer copy(Number at,NativeByteString source)
    {
       int offset = (int)at.intValue();

       if((offset+source.GetLength())>this.count)
            throw new EvaluatorException ( ( new GPError("ByteBuffer",0,0,"at+count exceeds the length of the current buffer")).toString());

       for(int i=0;i<source.GetLength();i++)
       		value[offset+i]=source.ByteAt(i);
       return this;
    }

    /**
     * copy a String to the position sepecified by at
     * @param at zero-based index
     * @param source a String object
     * @return the same referenceto this ByteBuffer
     */
    private NativeByteBuffer copy(Number at,String source)
    {
    	Integer ee=new Integer(GPConstant.ASCII);
        NativeByteBuffer ss = new NativeByteBuffer(source,ee);

        return copy(at,ss);
    }

    /**
     * find(ByteString s1,Number offset)
     * @param s1 ByteString to find
     * @param offset zero-based offset
     * @return zero-base offset; -1: not found
     */
    Number find(NativeByteString s1,Number offset)
    {
        int start=(int)offset.intValue();
        if(start<0)  start=0;

        log.debug("start="+start);

        boolean ok=false;  //ok-if finded,false-not finded
        int i;
        for(i=start;i<this.count;i++)
        {
            if((this.count-i)<s1.GetLength())
                    {ok=false; break;}
            for(int j=i,k=0;j<i+s1.GetLength();j++,k++)
            {
                if(this.value[j]!=s1.ByteAt(k))
                    break;
                if(k==s1.GetLength()-1)
                {
                    ok=true;
                    break;
                }
            }
            if(ok==true)
                break;
        }
        if(ok==false)
            i=-1;
        Integer ss=new Integer(i);
        return ss;
    }

    Number find(NativeByteString s1)
    {
        Integer ss=new Integer(0);
        return find(s1,ss);
    }

    /**
     * insert(Number at,ByteBuffer source)
     * @param at offset
     * @param source a ByteBuffer object
     * @return the same reference of this byteBuffer
     */
    private NativeByteBuffer insert(Number at,NativeByteBuffer source)
    {
        expandCapacity(count+source.count);
        byte[] tmp = new byte[count];
        System.arraycopy(value, 0, tmp, 0, count);
        int offset = (int)at.intValue();
        System.arraycopy(tmp, offset, value, offset+source.count, count-offset-source.count);
        System.arraycopy(source.value, 0, value, offset, source.count);
        return this;
    }

    /**
     * insert(Number at,ByteString source)
     * @param at offset
     * @param source a ByteString object
     * @return the same reference of this byteBuffer
     */
    private NativeByteBuffer insert(Number at,NativeByteString source)
    {
        NativeByteBuffer ss = new NativeByteBuffer(source);

        return insert(at,ss);
    }

    /**
     * insert(Number at,String source)
     * @param at offset
     * @param source a String object
     * @return the same reference of this byteBuffer
     */
    private NativeByteBuffer insert(Number at,String source)
    {
    	Integer ee=new Integer(GPConstant.ASCII);
        NativeByteBuffer ss = new NativeByteBuffer(source,ee);

        return insert(at,ss);
    }

    /**
     * get the String from offset to offset+num
     * used by toByteString(...)
     * @param offset
     * @param num
     * @return
     */
    private String sunStr(int offset,int num)
    {
        StringBuffer result = new StringBuffer();
        for(int i=0;i<num;i++)
        {
            //!!!!!!!!!should be improved here(too complex)
            String strHex;
            strHex=Integer.toHexString(value[i+offset]).toUpperCase();
            if(strHex.length()==1)
                strHex="0"+strHex;
            if(strHex.length()>2)
                strHex=strHex.substring(strHex.length()-2,strHex.length());
            result.append(strHex);
        }
        return result.toString();
    }

    /**
     * toByteString(Number offset,Number num)
     * @return
     */
    private NativeByteString toByteString(Context cx, Scriptable scope,Number offset,Number num)
    {
    	int start=(int)offset.intValue();
    	int len=(int)num.intValue();
    	String str=sunStr(start,len);
    	Integer ee=new Integer(GPConstant.HEX);
    	NativeByteString sNew = new NativeByteString(str,ee);
    	return NativeByteString.newByteString(cx,scope,sNew);
    }
    /**
     * toByteString(Number offset)
     * @return
     */
    private NativeByteString toByteString(Context cx, Scriptable scope,Number offset)
    {
    	int start=(int)offset.intValue();
    	int len=count-start;
    	String str=sunStr(start,len);
    	Integer ee=new Integer(GPConstant.HEX);
    	NativeByteString sNew = new NativeByteString(str,ee);
    	return NativeByteString.newByteString(cx,scope,sNew);
    }
    /**
     * toByteString()
     * @return
     */
    private NativeByteString toByteString(Context cx, Scriptable scope)
    {
    	String str=sunStr(0,count);
    	Integer ee=new Integer(GPConstant.HEX);
    	NativeByteString sNew = new NativeByteString(str,ee);
    	return NativeByteString.newByteString(cx,scope,sNew);
    }



/*************************************************/
/*GP function									 */
/*throw exception according to the parameter******/
/*************************************************/

    //GP-append(..)
    private NativeByteBuffer js_append(Context cx, Scriptable scope,Object[] args)
    {
    	if(args.length!=1)
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_TYPE)).toString());
        else if(args[0] instanceof NativeByteString)
             return append((NativeByteString)args[0]);
        else if(args[0] instanceof NativeByteBuffer)
             return append((NativeByteBuffer)args[0]);
        else if(args[0] instanceof String)
             return append((String)args[0]);
        else //error
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_TYPE)).toString());
    }

    //GP-byteAt()
    private Number js_byteAt(Context cx, Scriptable scope,Object[] args)
    {
        if(args.length!=1)
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_TYPE)).toString());
        else if(args[0] instanceof Number)
        {
            if(ScriptRuntime.toInt32(args[0])<0)
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_DATA)).toString());
            if(ScriptRuntime.toInt32(args[0])>count)
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_DATA)).toString());

            Integer ee=new Integer(ScriptRuntime.toInt32(args[0]));
            return byteAt(ee);
        }
        else //error
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_TYPE)).toString());
    }

    //GP-clear()
    private NativeByteBuffer js_clear(Context cx, Scriptable scope,Object[] args)
    {
        if(args.length==0)
            return clear();
        else if(args.length==1)
        {
            if(!(args[0] instanceof Number))
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_TYPE)).toString());
            else if(ScriptRuntime.toInt32(args[0])<0)
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_DATA)).toString());
            else if(ScriptRuntime.toInt32(args[0])>count)
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_DATA)).toString());

            Integer p0=new Integer(ScriptRuntime.toInt32(args[0]));
            return clear(p0);
        }
        else if(args.length==2)
        {
            if(!(args[0] instanceof Number))
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_TYPE)).toString());
            else if(!(args[1] instanceof Number))
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_TYPE)).toString());
            else if(ScriptRuntime.toInt32(args[0])<0)
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_DATA)).toString());
            else if(ScriptRuntime.toInt32(args[1])<0)
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_DATA)).toString());
            else if((ScriptRuntime.toInt32(args[0])+ScriptRuntime.toInt32(args[0]))>count)
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_DATA)).toString());
            Integer p0=new Integer(ScriptRuntime.toInt32(args[0]));
            Integer p1=new Integer(ScriptRuntime.toInt32(args[1]));
            return clear(p0,p1);
        }
        else //error
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_TYPE)).toString());
    }


    private NativeByteBuffer js_copy(Context cx, Scriptable scope,Object[] args)
    {
        if(args.length!=2)
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_TYPE)).toString());
        else if(!(args[0] instanceof Number))  //not Number
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_TYPE)).toString());
        else
        {
            if(ScriptRuntime.toInt32(args[0])<0)
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_DATA)).toString());
            Integer at=new Integer(ScriptRuntime.toInt32(args[0]));
            if(args[1] instanceof NativeByteBuffer)
                return copy(at,(NativeByteBuffer)args[1]);
            else if(args[1] instanceof NativeByteString)
                return copy(at,(NativeByteString)args[1]);
            else if(args[1] instanceof String)
                return copy(at,(String)args[1]);
            else
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_TYPE)).toString());
        }
    }

    private Number js_find(Context cx, Scriptable scope,Object[] args)
    {
        if(!(args[0] instanceof NativeByteString))
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_TYPE)).toString());
        if(args.length==1)
            return find((NativeByteString)args[0]);
        else if(args.length==2)
        {
            if(!(args[1] instanceof Number))
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_TYPE)).toString());
            Integer offset=new Integer(ScriptRuntime.toInt32(args[1]));
            return find((NativeByteString)args[0],offset);
        }
        else
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_TYPE)).toString());
    }


    private NativeByteBuffer js_insert(Context cx, Scriptable scope,Object[] args)
    {
        if(args.length!=2)
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_TYPE)).toString());
        else if(!(args[0] instanceof Number))  //not Number
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_TYPE)).toString());
        else
        {
            if(ScriptRuntime.toInt32(args[0])<0)
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_DATA)).toString());
            if(ScriptRuntime.toInt32(args[0])>count)
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_DATA)).toString());
            Integer at=new Integer(ScriptRuntime.toInt32(args[0]));
            if(args[1] instanceof NativeByteBuffer)
                return insert(at,(NativeByteBuffer)args[1]);
            else if(args[1] instanceof NativeByteString)
                return insert(at,(NativeByteString)args[1]);
            else if(args[1] instanceof String)
                return insert(at,(String)args[1]);
            else
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_TYPE)).toString());
        }
    }

    private NativeByteString jsFunction_toByteString(Context cx, Scriptable scope,Object[] args)
    {
        if(args.length==0)
            return toByteString(cx,scope);
        else if(args.length==1)
        {
            if(!(args[0] instanceof Number))  //not Number
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_TYPE)).toString());
            else
            {
                if(ScriptRuntime.toInt32(args[0])<0)
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_DATA)).toString());
                Integer offset=new Integer(ScriptRuntime.toInt32(args[0]));
                return toByteString(cx,scope,offset);
            }
        }
        else if(args.length==2)
        {
            if(ScriptRuntime.toInt32(args[0])<0)
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_DATA)).toString());
            if(ScriptRuntime.toInt32(args[1])<0)
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_DATA)).toString());
            if(((ScriptRuntime.toInt32(args[0]))+(ScriptRuntime.toInt32(args[1])))>count)
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_DATA)).toString());
            Integer offset=new Integer(ScriptRuntime.toInt32(args[0]));
            Integer num=new Integer(ScriptRuntime.toInt32(args[1]));
            return toByteString(cx,scope,offset,num);
        }
        else
    	     throw new EvaluatorException( ( new GPError("ByteBuffer",GPError.INVALID_TYPE)).toString());
    }


/////////////////////////////////////////////////////////////////////////////

    protected int maxInstanceId()
    	{ return MAX_INSTANCE_ID; }


    protected int findPrototypeId(String s)
    {
        int id=0;
        String X = null;

        if (s.equals("copy")) { X="copy";id=Id_copy; }
        if (s.equals("find")) { X="find";id=Id_find; }
        if (s.equals("clear")) { X="clear";id=Id_clear; }
        if (s.equals("append")) { X="append";id=Id_append; }
        if (s.equals("byteAt")) { X="byteAt";id=Id_byteAt; }
        if (s.equals("insert")) { X="insert";id=Id_insert; }
        if (s.equals("toString")) {X="toString";id=Id_toString; }
        if (s.equals("constructor")) {X="constructor";id=Id_constructor;}
        if (s.equals("toByteString")) {X="toByteString";id=Id_toByteString;}

        return id;
    }


    private static final int
        Id_length            	     =  1,
        MAX_INSTANCE_ID              =  1;

    private static final int
        Id_constructor               =  MAX_INSTANCE_ID+1,
        Id_toString                  =  MAX_INSTANCE_ID+2,
	Id_append		     	     =  MAX_INSTANCE_ID+3,
	Id_byteAt		     	     =  MAX_INSTANCE_ID+4,
	Id_clear	     	  		 =  MAX_INSTANCE_ID+5,
	Id_copy		     	  		 =  MAX_INSTANCE_ID+6,
	Id_find	     	 	     	 =  MAX_INSTANCE_ID+7,
	Id_insert	     		     =  MAX_INSTANCE_ID+8,
	Id_toByteString	   		     =  MAX_INSTANCE_ID+9,
        MAX_PROTOTYPE_ID             =  MAX_INSTANCE_ID+9;


    //for constructure function
    private static final String defaultValue = "";
    private static final int defaultEncoding = 3;


 //   private String string;
    private boolean prototypeFlag;

    /** The value is used for byte storage. */
    private byte value[];
    /** The offset is the first index of the storage that is used. */
 //   private int offset;
    /** The count is the number of byte in the ByteString. */
    private int count;

    //properties
    private int length=0;  //used in  crc() method

}

