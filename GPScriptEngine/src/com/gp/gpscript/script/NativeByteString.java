package com.gp.gpscript.script;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import com.gp.gpscript.utils.HexStr;
import com.gp.gpscript.utils.Base64;
import com.gp.gpscript.utils.Hex;
import com.watchdata.commons.lang.WDBase64;
import com.watchdata.commons.lang.WDByteUtil;

/**
 * 
 * <p>
 * Title: ByteString
 * </p>
 * <p>
 * Description: This class implements the ByteString (a native object). See [GP_SYS_SCR] 7.2. 3
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: watchdata
 * </p>
 * 
 * @author SunJinGang
 * @version 1.0
 */
public class NativeByteString extends IdScriptableObject {
	private static final Object BYTESTRING_TAG = new Object();

	public static void init(Context cx, Scriptable scope, boolean sealed) {
		NativeByteString obj = new NativeByteString();
		obj.exportAsJSClass(MAX_PROTOTYPE_ID, scope, sealed);
	}

	// Zero-parameter constructor: self-defined,not GP-function
	public NativeByteString() {
		value = new byte[0];
		length = 0;
		count = 0;
		offset = 0;
	}

	// create from byte[]
	public NativeByteString(byte[] buf) {
		value = new byte[buf.length];
		field = new byte[buf.length];
		System.arraycopy(buf, 0, value, 0, buf.length);
		System.arraycopy(buf, 0, field, 0, buf.length);
		length = buf.length;
		count = buf.length;
		offset = 0;
	}

	// GP-constructor
	/**
	 * constructor
	 * 
	 * @param str
	 *            a String object
	 * @param encoding
	 *            (HEX/ASCII/BASE64/UTF8/CN)
	 */
	public NativeByteString(String str, Number encoding) {
		switch ((int) encoding.intValue()) {
		case GPConstant.ASCII:
			strToASCII(str);
			field = new byte[length];
			System.arraycopy(value, 0, field, 0, length);
			break;
		case GPConstant.HEX:
			strToHEX(str);
			field = new byte[length];
			System.arraycopy(value, 0, field, 0, length);
			break;
		case GPConstant.UTF8:
			strToUTF8(str);
			field = new byte[length];
			System.arraycopy(value, 0, field, 0, length);
			break;
		case GPConstant.BASE64:
			byte[] buf = Base64.encode(str);
			value = new byte[buf.length];
			field = new byte[buf.length];
			System.arraycopy(buf, 0, value, 0, buf.length);
			System.arraycopy(buf, 0, field, 0, buf.length);
			length = buf.length;
			count = buf.length;
			offset = 0;
			break;
		case GPConstant.CN:
			strToCN(str);
			field = new byte[length];
			System.arraycopy(value, 0, field, 0, length);
			break;
		default:
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_DATA)).toString());
		}
	}

	// encode by ascII
	public void strToASCII(String str) {
		int strLen;
		strLen = str.length();
		value = new byte[strLen];
		for (int i = 0; i < strLen; i++) {
			char ch;
			ch = str.charAt(i);
			value[i] = (byte) ch;
		}
		length = strLen;
		count = strLen;
		offset = 0;
	}

	// encode by hex
	public void strToHEX(String str) {
		if ((str.length() % 2) != 0)
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_DATA)).toString());
		;
		int strLen;
		strLen = str.length() / 2;
		value = new byte[strLen];
		length = strLen;
		count = strLen;

		int intVal, intResult;

		for (int i = 0; i < strLen; i++) {
			intVal = 0;
			intResult = 0;
			for (int j = 0; j < 2; j++) {
				char ch;
				ch = str.charAt(i * 2 + j);
				switch (ch) {
				case '0':
					intVal = 0;
					break;
				case '1':
					intVal = 1;
					break;
				case '2':
					intVal = 2;
					break;
				case '3':
					intVal = 3;
					break;
				case '4':
					intVal = 4;
					break;
				case '5':
					intVal = 5;
					break;
				case '6':
					intVal = 6;
					break;
				case '7':
					intVal = 7;
					break;
				case '8':
					intVal = 8;
					break;
				case '9':
					intVal = 9;
					break;
				case 'A':
				case 'a':
					intVal = 10;
					break;
				case 'B':
				case 'b':
					intVal = 11;
					break;
				case 'C':
				case 'c':
					intVal = 12;
					break;
				case 'D':
				case 'd':
					intVal = 13;
					break;
				case 'E':
				case 'e':
					intVal = 14;
					break;
				case 'F':
				case 'f':
					intVal = 15;
					break;
				// default: //erorr when not 0~F
				// throw new GPError(getClassName(),INVALID_DATA);
				// break;

				}
				if (j == 0)
					intResult = intVal * 16;
				else
					intResult = intResult + intVal;
			}

			value[i] = (byte) intResult;
		}

	}

	// encode by UTF8
	public void strToUTF8(String str) {
		byte[] utf8 = null;
		try {
			utf8 = str.getBytes("UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		value = new byte[utf8.length];
		System.arraycopy(utf8, 0, value, 0, utf8.length);
		length = utf8.length;
		count = utf8.length;
	}

	// encode by CN
	public void strToCN(String str) {
		int strLen;
		strLen = str.length();
		if (strLen % 2 == 1) {
			strLen++;
			str = str + "?";
		}
		strLen = strLen / 2;
		value = new byte[strLen];
		for (int i = 0; i < strLen; i++) {
			byte ch1, ch2;
			ch1 = (byte) (str.charAt(i * 2));
			ch2 = (byte) (str.charAt(i * 2 + 1));
			value[i] = (byte) (((ch1 << 4) & 0xF0) | ((ch2) & 0x0F));
		}
		length = strLen;
		count = strLen;
	}

	// name used in script
	public String getClassName() {
		return "ByteString";
	}

	// for NativeByteBuffer or other class
	public int GetLength() {
		return length;
	}

	// for NativeByteBuffer or other class
	public byte ByteAt(int index) {
		return value[index];
	}

	protected void fillConstructorProperties(IdFunctionObject ctor) {
		final int attr = ScriptableObject.DONTENUM | ScriptableObject.PERMANENT | ScriptableObject.READONLY;

		ctor.defineProperty("XOR", ScriptRuntime.wrapNumber(XOR), attr);

		addIdFunctionProperty(ctor, BYTESTRING_TAG, ConstructorId_fromCharCode, "fromCharCode", 1);
		super.fillConstructorProperties(ctor);
	}

	protected int getMaxInstanceId() {
		return MAX_INSTANCE_ID;
	}

	protected int findInstanceIdInfo(String s) {
		if (s.equals("length")) {
			return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_length);
		}
		return super.findInstanceIdInfo(s);
	}

	protected String getInstanceIdName(int id) {
		if (id == Id_length) {
			return "length";
		}
		return super.getInstanceIdName(id);
	}

	protected Object getInstanceIdValue(int id) {
		if (id == Id_length) {
			return ScriptRuntime.wrapInt(length);
		}
		return super.getInstanceIdValue(id);
	}

	protected void initPrototypeId(int id) {
		String s;
		int arity;
		switch (id) {
		case Id_constructor:
			arity = 2;
			s = "constructor";
			break;

		case Id_toString:
			arity = 1;
			s = "toString";
			break;

		case Id_and:
			arity = 1;
			s = "and";
			break;
		case Id_bytes:
			arity = 2;
			s = "bytes";
			break;
		case Id_concat:
			arity = 1;
			s = "concat";
			break;
		case Id_crc:
			arity = 1;
			s = "crc";
			break;
		case Id_equals:
			arity = 1;
			s = "equals";
			break;
		case Id_find:
			arity = 2;
			s = "find";
			break;
		case Id_getL:
			arity = 1;
			s = "getL";
			break;
		case Id_getLV:
			arity = 1;
			s = "getLV";
			break;
		case Id_left:
			arity = 1;
			s = "left";
			break;
		case Id_neg:
			arity = 1;
			s = "neg";
			break;
		case Id_not:
			arity = 0;
			s = "not";
			break;
		case Id_or:
			arity = 1;
			s = "or";
			break;
		case Id_pad:
			arity = 2;
			s = "pad";
			break;
		case Id_right:
			arity = 1;
			s = "right";
			break;
		case Id_startsWith:
			arity = 1;
			s = "startsWith";
			break;
		case Id_toBase64:
			arity = 1;
			s = "toBase64";
			break;
		case Id_toBcd:
			arity = 1;
			s = "toBcd";
			break;
		case Id_toHex:
			arity = 1;
			s = "toHex";
			break;
		case Id_toSigned:
			arity = 1;
			s = "toSigned";
			break;
		case Id_toUnsigned:
			arity = 1;
			s = "toUnsigned";
			break;
		case Id_xor:
			arity = 1;
			s = "xor";
			break;
		case Id_hex2String:
			arity = 1;
			s = "hex2String";
			break;
		case Id_deleteEightZero:
			arity = 1;
			s = "deleteEightZero";
			break;
		default:
			throw new IllegalArgumentException(String.valueOf(id));
		}
		initPrototypeMethod(BYTESTRING_TAG, id, s, arity);
	}

	public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
		if (!f.hasTag(BYTESTRING_TAG)) {
			return super.execIdCall(f, cx, scope, thisObj, args);
		}

		int id = f.methodId();
		switch (id) {
		case ConstructorId_fromCharCode:
			return jsStaticFunction_fromCharCode(args);

		case Id_constructor:
			return jsConstructor(args, thisObj == null);

		case Id_toString:
			return realThis(thisObj, f).jsFunction_toString();
			// return jsFunction_toString(cx, thisObj, args);
		case Id_and: // GP-add(...)
			// use realthis(...) to realize thisobj
			// return realThis(thisObj, f).js_add(args).jsFunction_toString();
			return realThis(thisObj, f).jsFunction_and(cx, scope, args);
		case Id_bytes: // GP-bytes(...)
			return realThis(thisObj, f).jsFunction_bytes(cx, scope, args);
		case Id_concat: // GP-concat(...)
			return realThis(thisObj, f).jsFunction_concat(cx, scope, args);
		case Id_crc:
			return realThis(thisObj, f).jsFunction_crc(cx, scope, args);
		case Id_equals:
			return realThis(thisObj, f).js_equals(cx, scope, args);
		case Id_find:
			return realThis(thisObj, f).js_find(cx, scope, args);
		case Id_getL:
			return realThis(thisObj, f).jsFunction_getL(cx, scope, args);
		case Id_getLV:
			return realThis(thisObj, f).jsFunction_getLV(cx, scope, args);
		case Id_left:
			return realThis(thisObj, f).jsFunction_left(cx, scope, args);
		case Id_neg:
			return realThis(thisObj, f).jsFunction_neg(cx, scope, args);
		case Id_not:
			return realThis(thisObj, f).jsFunction_not(cx, scope, args);
		case Id_or:
			return realThis(thisObj, f).jsFunction_or(cx, scope, args);
		case Id_pad:
			return realThis(thisObj, f).jsFunction_pad(cx, scope, args);
		case Id_right:
			return realThis(thisObj, f).jsFunction_right(cx, scope, args);
		case Id_startsWith:
			return realThis(thisObj, f).js_startsWith(cx, scope, args);
		case Id_toBase64:
			return realThis(thisObj, f).jsFunction_toBase64(cx, scope, args);
		case Id_toBcd:
			return realThis(thisObj, f).jsFunction_toBcd(cx, scope, args);
		case Id_toHex:
			return realThis(thisObj, f).jsFunction_toHex(cx, scope, args);
		case Id_toSigned:
			return realThis(thisObj, f).jsFunction_toSigned(cx, scope, args);
		case Id_toUnsigned:
			return realThis(thisObj, f).jsFunction_toUnsigned(cx, scope, args);
		case Id_xor:
			return realThis(thisObj, f).jsFunction_xor(cx, scope, args);
		case Id_hex2String:
			return realThis(thisObj, f).jsFunction_hex2String(cx, scope, args);
		case Id_deleteEightZero:
			return realThis(thisObj, f).jsFunction_deleteEightZero(cx, scope, args);
		}
		throw new IllegalArgumentException(String.valueOf(id));
	}

	// thisObj
	private static NativeByteString realThis(Scriptable thisObj, IdFunctionObject f) {
		if (!(thisObj instanceof NativeByteString))
			throw incompatibleCallError(f);
		return (NativeByteString) thisObj;
	}

	/*
	 * private static RegExpProxy checkReProxy(Context cx) { RegExpProxy result = cx.getRegExpProxy(); if (result == null) { throw cx.reportRuntimeError0("msg.no.regexp"); } return result; }
	 */
	private static String jsStaticFunction_fromCharCode(Object[] args) {
		int N = args.length;
		if (N < 1)
			return "";
		StringBuffer s = new java.lang.StringBuffer(N);
		for (int i = 0; i < N; i++) {
			s.append(ScriptRuntime.toUint16(args[i]));
		}
		return s.toString();
	}

	private static Object jsConstructor(Object[] args, boolean inNewExpr) {
		// get the first para---stringVaue
		if (args.length == 0) {
			return new NativeByteString();
		} else {
			if ((!(args[0] instanceof String)) || (!(args[1] instanceof Number)))
				throw new EvaluatorException((new GPError("ByteString", GPError.INVALID_TYPE)).toString());
			;
			String s = args.length >= 1 ? ScriptRuntime.toString(args[0]) : defaultValue;
			// get the second para---encoding
			int e = args.length >= 2 ? ScriptRuntime.toInt32(args[1]) : defaultEncoding;
			if (inNewExpr) {
				// new ByteString(val) creates a new ByteString object.
				Integer ee = new Integer(e);
				return new NativeByteString(s, ee);
			} else
				return new NativeByteString();
		}
	}

	public String toString() {
		return jsFunction_toString();
	}

	// for display the object
	private String jsFunction_toString() {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < this.length; i++) {
			// !!!!!!!!!should be improved here(too complex)
			String strHex;
			byte elem = ((Byte) get(i, this)).byteValue();
			strHex = Integer.toHexString(elem).toUpperCase();
			if (strHex.length() == 1)
				strHex = "0" + strHex;
			if (strHex.length() > 2)
				strHex = strHex.substring(strHex.length() - 2, strHex.length());
			result.append(strHex);
		}
		return result.toString();
	}

	/**
	 * put is used by setElem()
	 * 
	 * @param index
	 *            zero-based
	 * @param start
	 *            scope
	 * @param value
	 *            byte
	 */
	public void put(int index, Scriptable start, Object value) {
		if (start instanceof NativeByteString) {
			NativeByteString obj = (NativeByteString) start;
			if (obj.length <= index)
				obj.length = index + 1; // avoid overflowing index!

			if (obj.value != null && 0 <= index && index < obj.value.length) {
				obj.value[index] = ((Byte) value).byteValue();
				return;
			}
		}
		super.put(index, start, value);
	}

	public Object get(int index, Scriptable start) {
		if (start instanceof NativeByteString) {
			NativeByteString obj = (NativeByteString) start;
			if (obj.value != null && 0 <= index && index < obj.value.length)
				return new Byte(obj.value[index]);
		}
		return super.get(index, start);
	}

	/**
	 * for return a new ByteString from a ByteString Object
	 * 
	 * @param cx
	 * @param scope
	 * @param sNew
	 * @return a new byteString (can be operated by script)
	 */
	public static NativeByteString newByteString(Context cx, Scriptable scope, NativeByteString sNew) {
		scope = getTopLevelScope(scope);
		Scriptable result = ScriptRuntime.newObject(cx, scope, "ByteString", null);

		((NativeByteString) result).value = new byte[sNew.length];
		System.arraycopy(sNew.value, 0, ((NativeByteString) result).value, 0, sNew.length);
		((NativeByteString) result).length = sNew.length;

		/*
		 * Object temp; for(int i=0;i<sNew.length;i++) { temp=new Byte(sNew.value[i]); ((NativeByteString)result).value[i] = ((Byte)temp).byteValue();
		 * 
		 * // put(i, result, temp); }
		 */return (NativeByteString) result;
	}

	// return field as ByteString[]
	NativeByteString[] GetByteStringField(Context cx, Scriptable scope) {
		scope = getTopLevelScope(scope);
		Scriptable result[] = new Scriptable[this.length];
		byte para1;
		for (int i = 0; i < this.length; i++) {
			para1 = ((Byte) get(i, this)).byteValue();
			result[i] = ScriptRuntime.newObject(cx, scope, "ByteString", null);
			put(0, result[i], new Byte(para1));
		}
		return (NativeByteString[]) result;
	}

	// ///////////////////GP_Function///////////////////
	/**
	 * ByteString and(ByteString value)
	 * 
	 * @return a new ByteString(The bitwise AND of the two object)
	 */
	private NativeByteString jsFunction_and(Context cx, Scriptable scope, Object[] args) {
		if (!(args[0] instanceof NativeByteString))
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_TYPE)).toString());
		NativeByteString s1 = new NativeByteString();
		s1 = (NativeByteString) (args[0]);
		if (s1.length != this.length)
			throw new EvaluatorException((new GPError(getClassName(), 0, 0, "two length of ByteString is not equal")).toString());
		else { // create a new object,the length is equal to the input object
			scope = getTopLevelScope(scope);
			Scriptable result = ScriptRuntime.newObject(cx, scope, "ByteString", null);
			((NativeByteString) result).value = new byte[this.length];
			byte para1, para2;
			Object temp = null;
			for (int i = 0; i < this.length; i++) {
				para1 = ((Byte) get(i, this)).byteValue();
				para2 = ((Byte) get(i, s1)).byteValue();
				temp = new Byte((byte) (para1 & para2));
				put(i, result, temp);
			}
			return (NativeByteString) result;
		}
	}

	/**
	 * ByteString bytes(Number offset) ByteString bytes(Number offset,Number count)
	 * 
	 * @return a new ByteString
	 */
	private NativeByteString jsFunction_bytes(Context cx, Scriptable scope, Object[] args) {
		int p1 = args.length >= 1 ? ScriptRuntime.toInt32(args[0]) : 0;
		if (p1 < 0)
			throw new EvaluatorException((new GPError(getClassName(), 0, 0, "offsetByte is negative")).toString());
		// get the second para---encoding
		int p2 = args.length >= 2 ? ScriptRuntime.toInt32(args[1]) : this.length - p1;
		if (p2 < 0)
			throw new EvaluatorException((new GPError(getClassName(), 0, 0, "countByte is negative")).toString());

		scope = getTopLevelScope(scope);
		Scriptable result = ScriptRuntime.newObject(cx, scope, "ByteString", null);
		((NativeByteString) result).value = new byte[p2];
		byte para1;
		Object temp = null;
		for (int i = p1, j = 0; i < p1 + p2; i++, j++) {
			para1 = ((Byte) get(i, this)).byteValue();
			temp = new Byte((byte) para1);
			put(j, result, temp);
		}
		return (NativeByteString) result;
	}

	/**
	 * ByteString concat(ByteString value)
	 * 
	 * @return a new ByteString
	 */
	private NativeByteString jsFunction_concat(Context cx, Scriptable scope, Object[] args) {
		if (!(args[0] instanceof NativeByteString))
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_TYPE)).toString());
		NativeByteString s1 = new NativeByteString();
		s1 = (NativeByteString) (args[0]);
		scope = getTopLevelScope(scope);
		Scriptable result = ScriptRuntime.newObject(cx, scope, "ByteString", null);
		((NativeByteString) result).value = new byte[this.length];
		((NativeByteString) result).value = new byte[this.length + s1.length];

		byte para1;
		Object temp = null;
		for (int i = 0; i < this.length; i++) {
			para1 = ((Byte) get(i, this)).byteValue();
			temp = new Byte((byte) para1);
			put(i, result, temp);
		}
		for (int i = 0, j = this.length; i < s1.length; i++, j++) {
			para1 = ((Byte) get(i, s1)).byteValue();
			temp = new Byte((byte) para1);
			put(j, result, temp);
		}
		return (NativeByteString) result;
	}

	/**
	 * ByteString crc(Number type)
	 * 
	 * @return a new ByteString
	 */
	private NativeByteString jsFunction_crc(Context cx, Scriptable scope, Object[] args) {
		if (!(args[0] instanceof Number))
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_TYPE)).toString());
		int p1 = ((Number) args[0]).intValue();
		if (p1 != XOR)
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_DATA)).toString());
		scope = getTopLevelScope(scope);
		Scriptable result = ScriptRuntime.newObject(cx, scope, "ByteString", null);
		((NativeByteString) result).value = new byte[1];
		byte para1 = (byte) 0x00;
		for (int i = 0; i < this.length; i++) {
			para1 = (byte) (para1 ^ ((Byte) get(i, this)).byteValue());
		}
		Object temp = new Byte((byte) para1);
		put(0, result, temp);
		return (NativeByteString) result;
	}

	/**
	 * Boolean equals(ByteString value)
	 * 
	 * @return true-equals/false-diffrent
	 */
	private Boolean js_equals(Context cx, Scriptable scope, Object[] args) {
		if (!(args[0] instanceof NativeByteString))
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_TYPE)).toString());

		NativeByteString s1 = new NativeByteString();
		s1 = (NativeByteString) (args[0]);
		// check length
		if (this.length != s1.length) {
			Boolean result = new Boolean(false);
			return result;
		}
		// check value
		byte para1, para2;
		for (int i = 0; i < this.length; i++) {
			para1 = ((Byte) get(i, this)).byteValue();
			para2 = ((Byte) get(i, s1)).byteValue();
			if (para1 != para2) {
				Boolean result = new Boolean(false);
				return result;
			}
		}
		Boolean result = new Boolean(true);
		return result;
	}

	/**
	 * Number find(ByteString value) Number find(ByteString value,Number offset)
	 * 
	 * @return zero-base offset
	 */
	private Number js_find(Context cx, Scriptable scope, Object[] args) {
		if (!(args[0] instanceof NativeByteString))
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_TYPE)).toString());

		NativeByteString s1 = new NativeByteString();
		s1 = (NativeByteString) (args[0]);
		// get the second para---encoding
		this.offset = args.length >= 2 ? ScriptRuntime.toInt32(args[1]) : 0;
		if (this.offset < 0)
			this.offset = 0;

		boolean ok = false; // ok-if finded,false-not finded
		int i;
		byte para1, para2;
		for (i = this.offset; i < this.length; i++) {
			if ((this.length - i) < s1.length) {
				ok = false;
				break;
			}
			for (int j = i, k = 0; j < i + s1.length; j++, k++) {
				para1 = ((Byte) get(j, this)).byteValue();
				para2 = ((Byte) get(k, s1)).byteValue();
				if (para1 != para2)
					break;
				if (k == s1.length - 1) {
					ok = true;
					break;
				}
			}
			if (ok == true)
				break;
		}
		if (ok == true)
			return new Integer(i);
		else
			return new Integer(-1);
	}

	/**
	 * ByteString getL(Number encoding)
	 * 
	 * @return a ByteString
	 */
	private NativeByteString jsFunction_getL(Context cx, Scriptable scope, Object[] args) {
		if ((args.length > 0) && (!(args[0] instanceof Number)))
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_TYPE)).toString());

		byte encoding = args.length >= 1 ? (byte) ((Number) args[0]).byteValue() : 0;

		TLV tlv = new TLV(0x0000, this.value, encoding);

		String strL = WDByteUtil.bytes2HEX(tlv.getL());
		Integer ee = new Integer(GPConstant.HEX);
		NativeByteString sNew = new NativeByteString(strL, ee);
		return NativeByteString.newByteString(cx, scope, sNew);

	}

	/**
	 * ByteString getLV(Number encoding)
	 * 
	 * @return a ByteString
	 */
	private NativeByteString jsFunction_getLV(Context cx, Scriptable scope, Object[] args) {
		if ((args.length > 0) && (!(args[0] instanceof Number)))
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_TYPE)).toString());

		byte encoding = args.length >= 1 ? (byte) ((Number) args[0]).byteValue() : 0;

		TLV tlv = new TLV(0x0000, this.value, encoding);

		String strLV = WDByteUtil.bytes2HEX(tlv.getLV());
		Integer ee = new Integer(GPConstant.HEX);
		NativeByteString sNew = new NativeByteString(strLV, ee);
		return NativeByteString.newByteString(cx, scope, sNew);

	}

	/**
	 * ByteString left(Number value)
	 * 
	 * @return a new ByteString
	 */
	private NativeByteString jsFunction_left(Context cx, Scriptable scope, Object[] args) {
		if (!(args[0] instanceof Number))
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_TYPE)).toString());
		int p1 = args.length >= 1 ? ScriptRuntime.toInt32(args[0]) : 0;
		if (p1 < 0)
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_DATA)).toString());
		if (p1 > this.length)
			p1 = this.length;

		scope = getTopLevelScope(scope);
		Scriptable result = ScriptRuntime.newObject(cx, scope, "ByteString", null);
		((NativeByteString) result).value = new byte[p1];
		Object temp;
		for (int i = 0; i < p1; i++) {
			temp = new Byte(((Byte) get(i, this)).byteValue());
			put(i, result, temp);
		}
		return (NativeByteString) result;
	}

	/**
	 * ByteString neg() ByteString neg(Number value)
	 * 
	 * @return
	 */
	private NativeByteString jsFunction_neg(Context cx, Scriptable scope, Object[] args) {
		if (args.length == 1)
			if (!(args[0] instanceof Number))
				throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_TYPE)).toString());
		int p1 = args.length >= 1 ? ScriptRuntime.toInt32(args[0]) : this.length;
		if (p1 < this.length)
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_DATA)).toString());
		scope = getTopLevelScope(scope);
		Scriptable result = ScriptRuntime.newObject(cx, scope, "ByteString", null);
		((NativeByteString) result).value = new byte[p1];
		Object temp;
		for (int i = 0; i < p1; i++) {
			if (i < this.length)
				temp = new Byte((byte) (-(((Byte) get(i, this)).byteValue())));
			else
				temp = new Byte((byte) 0x00);
			put(i, result, temp);
		}
		return (NativeByteString) result;
	}

	/**
	 * ByteString not()
	 * 
	 * @return a new ByteString
	 */
	private NativeByteString jsFunction_not(Context cx, Scriptable scope, Object[] args) {
		scope = getTopLevelScope(scope);
		Scriptable result = ScriptRuntime.newObject(cx, scope, "ByteString", null);
		((NativeByteString) result).value = new byte[this.length];

		Object temp;
		for (int i = 0; i < this.length; i++) {
			temp = new Byte((byte) (~(((Byte) get(i, this)).byteValue())));
			put(i, result, temp);
		}
		return (NativeByteString) result;
	}

	/**
	 * ByteString or(ByteString value)
	 * 
	 * @return a new ByteString
	 */
	private NativeByteString jsFunction_or(Context cx, Scriptable scope, Object[] args) {
		if (!(args[0] instanceof NativeByteString))
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_TYPE)).toString());
		NativeByteString s1 = new NativeByteString();
		s1 = (NativeByteString) (args[0]);
		if (s1.length != this.length)
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_DATA)).toString());
		else {
			// create a new object,the length is equal to the input object
			scope = getTopLevelScope(scope);
			Scriptable result = ScriptRuntime.newObject(cx, scope, "ByteString", null);
			((NativeByteString) result).value = new byte[this.length];
			Object temp;
			byte para1, para2;
			for (int i = 0; i < this.length; i++) {
				para1 = ((Byte) get(i, this)).byteValue();
				para2 = ((Byte) get(i, s1)).byteValue();
				temp = new Byte((byte) (para1 | para2));
				put(i, result, temp);
			}
			return (NativeByteString) result;
		}
	}

	/**
	 * ByteString pad(Number padMethod)
	 * 
	 * @return a new ByteString
	 */
	private NativeByteString jsFunction_pad(Context cx, Scriptable scope, Object[] args) {
		if (!(args[0] instanceof Number))
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_TYPE)).toString());
		int padMethod = args.length >= 1 ? ScriptRuntime.toInt32(args[0]) : 0;
		byte[] b80 = { (byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
		scope = getTopLevelScope(scope);
		Scriptable result = ScriptRuntime.newObject(cx, scope, "ByteString", null);
		Object temp;
		switch (padMethod) {
		// method1: The Data string D to be input to the MAC algorithm shall be right-padded with as few (possibly none) ��0�� bit as necessary to obtain a data string whose length (in bits) is a positive integer multiple of N
		case NativeCrypto.ISO9797_METHOD_1: {
			int AllLen = this.length + 8 - (this.length) % 8;
			((NativeByteString) result).value = new byte[AllLen];

			for (int i = 0; i < AllLen; i++) {
				if (i < this.length)
					temp = new Byte(((Byte) get(i, this)).byteValue());
				else
					temp = new Byte((byte) 0);
				put(i, result, temp);
			}
			return (NativeByteString) result;
		}

		// method2: The Data string D to be input to the MAC algorithm shall be right-padded a single ��1�� bit.
		// The resulting string shall then be right-padded with as few (possibly none) ��0�� bit as necessary to obtain a data string whose length (in bits) is a positive integer multiple of N.
		case NativeCrypto.ISO9797_METHOD_2: {
			if ((this.length + 1) % 8 == 0) // add 0x80 first
			{
				((NativeByteString) result).value = new byte[this.length];
				for (int i = 0; i < this.length; i++) {
					temp = new Byte(((Byte) get(i, this)).byteValue());
					put(i, result, temp);
				}
				put(this.length, result, new Byte((byte) 0x80));
				return (NativeByteString) result;
			} else {
				int AllLen = this.length + 1 + 8 - (this.length + 1) % 8;
				((NativeByteString) result).value = new byte[AllLen];
				for (int i = 0; i < AllLen; i++) {
					if (i < this.length)
						temp = new Byte(((Byte) get(i, this)).byteValue());
					else
						temp = new Byte(b80[i - this.length]);
					put(i, result, temp);
				}
				return (NativeByteString) result;
			}
		}

		case NativeCrypto.EMV_PAD: {
			if (this.length % 8 == 0) {
				int AllLen = this.length + 8;
				((NativeByteString) result).value = new byte[AllLen];
				for (int i = 0; i < AllLen; i++) {
					if (i < this.length)
						temp = new Byte(((Byte) get(i, this)).byteValue());
					else
						temp = new Byte(b80[i - this.length]);
					put(i, result, temp);
				}
				return (NativeByteString) result;
			} else {
				int AllLen = this.length + 8 - this.length % 8;
				((NativeByteString) result).value = new byte[AllLen];
				for (int i = 0; i < AllLen; i++) {
					if (i < this.length)
						temp = new Byte(((Byte) get(i, this)).byteValue());
					else
						temp = new Byte(b80[i - this.length]);
					put(i, result, temp);
				}
				return (NativeByteString) result;
			}
		}

		case NativeCrypto.NONE: {
			((NativeByteString) result).value = new byte[this.length];
			for (int i = 0; i < this.length; i++) {
				if (i < this.length)
					temp = new Byte(((Byte) get(i, this)).byteValue());
				else
					temp = new Byte(b80[i - this.length]);
				put(i, result, temp);
			}
			return (NativeByteString) result;
		}
		default:
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_TYPE)).toString());
		}

	}

	/**
	 * ByteString Right(Number value)
	 * 
	 * @return a new ByteString
	 */
	private NativeByteString jsFunction_right(Context cx, Scriptable scope, Object[] args) {
		if (!(args[0] instanceof Number))
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_TYPE)).toString());
		int p1 = args.length >= 1 ? ScriptRuntime.toInt32(args[0]) : 0;
		if (p1 > this.length)
			p1 = this.length;
		if (p1 < 0)
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_DATA)).toString());
		scope = getTopLevelScope(scope);
		Scriptable result = ScriptRuntime.newObject(cx, scope, "ByteString", null);
		((NativeByteString) result).value = new byte[p1];
		Object temp;
		byte para1;
		for (int i = 0; i < p1; i++) {
			para1 = ((Byte) get(this.length - p1 + i, this)).byteValue();
			temp = new Byte(para1);
			put(i, result, temp);
		}
		return (NativeByteString) result;
	}

	/**
	 * Number startsWith(ByteString value)
	 * 
	 * @return zero-base offset
	 */
	private Number js_startsWith(Context cx, Scriptable scope, Object[] args) {
		if (!(args[0] instanceof NativeByteString))
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_TYPE)).toString());
		NativeByteString s1 = new NativeByteString();
		s1 = (NativeByteString) (args[0]);

		int L;
		L = (this.length < s1.length) ? this.length : s1.length;

		int i;
		byte para1, para2;
		for (i = 0; i < L; i++) {
			para1 = ((Byte) get(i, this)).byteValue();
			para2 = ((Byte) get(i, s1)).byteValue();
			if (para1 != para2)
				break;
		}
		return new Integer(i);
	}

	/**
	 * ??????????????? ByteString toBase64() ByteString toBase64(boolean lineBreak)
	 * 
	 * @return
	 */
	private NativeByteString jsFunction_toBase64(Context cx, Scriptable scope, Object[] args) {
		if (args.length == 1)
			if (!(args[0] instanceof Boolean))
				throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_TYPE)).toString());
		boolean lineBreak = args.length >= 1 ? ((Boolean) (args[0])).booleanValue() : false;

		byte[] base64 = null;
		base64 = WDBase64.encode(this.value);

		NativeByteString sNew = new NativeByteString(base64);
		return newByteString(cx, scope, sNew);
	}

	/**
	 * ByteString toBcd() ByteString toBcd(Number countBytes)
	 * 
	 * @return a new ByteString
	 */
	private NativeByteString jsFunction_toBcd(Context cx, Scriptable scope, Object[] args) {
		if (args.length == 1)
			if (!(args[0] instanceof Number))
				throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_TYPE)).toString());
		int p1 = args.length >= 1 ? ScriptRuntime.toInt32(args[0]) : this.length;
		if (p1 < 0)
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_DATA)).toString());

		int bcdValue = 0;
		byte para1;
		for (int i = 0; i < this.length; i++) {
			para1 = ((Byte) get(i, this)).byteValue();
			bcdValue = bcdValue + Hex.ByteToUnsigned(para1) * (int) (Math.pow((double) 256, (double) (this.length - i - 1)));
		}
		Integer bcdObj = new Integer(bcdValue);
		String strBcd = bcdObj.toString();
		if (strBcd.length() % 2 != 0)
			strBcd = "0" + strBcd;
		if (strBcd.length() < 2 * p1)
			while (strBcd.length() < 2 * p1)
				strBcd = "00" + strBcd; // countByte is large
		Integer ee = new Integer(GPConstant.HEX);
		NativeByteString sNew = new NativeByteString(strBcd, ee);
		return newByteString(cx, scope, sNew);
	}

	/**
	 * ByteString toHex() ByteString toHex(Number countBytes)
	 * 
	 * @return a new ByteString
	 */
	private NativeByteString jsFunction_toHex(Context cx, Scriptable scope, Object[] args) {
		if (args.length == 1)
			if (!(args[0] instanceof Number))
				throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_TYPE)).toString());
		int p1 = args.length >= 1 ? ScriptRuntime.toInt32(args[0]) : this.length;
		if (p1 % 2 != 0)
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_DATA)).toString());
		String strNew = this.toString();
		if (p1 < this.length)
			strNew = strNew.substring(0, p1 * 2);
		Integer ee = new Integer(GPConstant.ASCII);
		NativeByteString sNew = new NativeByteString(strNew, ee);
		return newByteString(cx, scope, sNew);
	}

	/**
	 * ByteString toSigned() ByteString toSigned(boolean littleEndian)
	 * 
	 * @return
	 */
	private Number jsFunction_toSigned(Context cx, Scriptable scope, Object[] args) {
		if (args.length == 1)
			if (!(args[0] instanceof Number))
				throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_TYPE)).toString());
		double unValue = 0;
		byte para1;
		if (this.length > 1) {
			for (int i = 1; i < this.length; i++) {
				para1 = ((Byte) get(i, this)).byteValue();
				unValue = unValue + Hex.ByteToUnsigned(para1) * (int) (Math.pow((double) 256, (double) (this.length - i - 1)));
			}
		}
		para1 = ((Byte) get(0, this)).byteValue();
		if (para1 >= (byte) 0) // deal with the most significant byte
			unValue = unValue + (int) para1 * (int) (Math.pow((double) 256, (double) (this.length - 1)));
		else
			unValue = -(unValue + (int) (para1 ^ (byte) 0x80) * (int) (Math.pow((double) 256, (double) (this.length - 1))));
		if ((unValue > Integer.MAX_VALUE) || (unValue < Integer.MIN_VALUE))
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_DATA)).toString());
		Integer Obj = new Integer((int) unValue);
		return Obj;
	}

	/**
	 * ByteString toUnsigned() ByteString toUnsigned(boolean littleEndian)
	 * 
	 * @return unsigned value
	 */
	private Number jsFunction_toUnsigned(Context cx, Scriptable scope, Object[] args) {
		boolean LittleIndian = true;
		if (args.length == 1) {
			if (!(args[0] instanceof Boolean))
				throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_TYPE)).toString());
			LittleIndian = ((Boolean) args[0]).booleanValue();
		}
		double unValue = 0;
		byte para1;
		if (LittleIndian == true) // little indian
		{
			for (int i = 0; i < this.length; i++) {
				para1 = ((Byte) get(i, this)).byteValue();
				unValue = unValue + Hex.ByteToUnsigned(para1) * (int) (Math.pow((double) 256, (double) (this.length - i - 1)));
			}
		} else // big indian
		{
			for (int i = 0; i < this.length; i++) {
				para1 = ((Byte) get(i, this)).byteValue();
				unValue = unValue + Hex.ByteToUnsigned(para1) * (int) (Math.pow((double) 256, (double) (i)));
			}
		}
		if (unValue > Integer.MAX_VALUE)
			throw new EvaluatorException((new GPError(getClassName(), 0, 0, "can't be expressed by32-bit integer")).toString());
		return new Integer((int) unValue);
	}

	/**
	 * ByteString xor(ByteString value)
	 * 
	 * @return
	 */
	private NativeByteString jsFunction_xor(Context cx, Scriptable scope, Object[] args) {
		if (!(args[0] instanceof NativeByteString))
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_TYPE)).toString());
		NativeByteString s1 = new NativeByteString();
		s1 = (NativeByteString) (args[0]);
		if (s1.length != this.length)
			throw new EvaluatorException((new GPError(getClassName(), GPError.INVALID_DATA)).toString());
		else {
			// create a new object,the length is equal to the input object
			scope = getTopLevelScope(scope);
			Scriptable result = ScriptRuntime.newObject(cx, scope, "ByteString", null);
			((NativeByteString) result).value = new byte[this.length];
			Object temp;
			byte para1, para2;
			for (int i = 0; i < this.length; i++) {
				para1 = ((Byte) get(i, this)).byteValue();
				para2 = ((Byte) get(i, s1)).byteValue();
				temp = new Byte((byte) (para1 ^ para2));
				put(i, result, temp);
			}
			return (NativeByteString) result;
		}
	}

	/**
	 * ByteString hex2String(ByteString value)
	 * 
	 * @return
	 */
	private NativeByteString jsFunction_hex2String(Context cx, Scriptable scope, Object[] args) {
		NativeByteString s1 = new NativeByteString();
		s1 = (NativeByteString) (args[0]);
		String strNew = s1.toString();
		strNew = HexStr.hexToString(strNew);
		Integer ee = new Integer(GPConstant.HEX);
		NativeByteString sNew = new NativeByteString(strNew, ee);
		return newByteString(cx, scope, sNew);

	}

	/**
	 * delete 80[000000000...]
	 * 
	 * @return
	 */
	private NativeByteString jsFunction_deleteEightZero(Context cx, Scriptable scope, Object[] args) {
		NativeByteString s1 = new NativeByteString();
		s1 = (NativeByteString) (args[0]);
		String strNew = s1.toString();
		// 获取80的索引
		int eightIndex = strNew.lastIndexOf("80");
		// 当存在80时，对数据进行判断和截取
		// 否则直接返回原来的数据
		if (eightIndex > 0) {
			// 截取80后面的数据
			String strEight = strNew.substring(eightIndex + 2);
			Pattern pattern = Pattern.compile("[0]*");
			Matcher matcher = pattern.matcher(strEight);
			boolean flag = false;
			// 判断80后的数据是否为全0
			if (matcher.matches()) {
				// 判断80后的数据长度是否大于14，如小于14，说明是对数据进行解密的时候添加上的，现在需要去除
				if (strEight.length() > 14) {
					flag = false;
				} else {
					// 80后的数据在14个范围之内
					flag = true;
				}
			} else {
				// 80后的数据不全为0
				flag = false;
			}
			if (flag) {
				// 截取80前面的数据作为新的数据
				strNew = strNew.substring(0, eightIndex);
			}
		}
		Integer ee = new Integer(GPConstant.HEX);
		NativeByteString sNew = new NativeByteString(strNew, ee);
		return newByteString(cx, scope, sNew);
	}

	protected int maxInstanceId() {
		return MAX_INSTANCE_ID;
	}

	protected int findPrototypeId(String s) {
		int id = 0;
		String X = null;
		if (s.equals("or")) {
			X = "or";
			id = Id_or;
		}
		if (s.equals("and")) {
			X = "and";
			id = Id_and;
		}
		if (s.equals("xor")) {
			X = "xor";
			id = Id_xor;
		}
		if (s.equals("crc")) {
			X = "crc";
			id = Id_crc;
		}
		if (s.equals("neg")) {
			X = "neg";
			id = Id_neg;
		}
		if (s.equals("not")) {
			X = "not";
			id = Id_not;
		}
		if (s.equals("pad")) {
			X = "pad";
			id = Id_pad;
		}
		if (s.equals("find")) {
			X = "find";
			id = Id_find;
		}
		if (s.equals("getL")) {
			X = "getL";
			id = Id_getL;
		}
		if (s.equals("left")) {
			X = "left";
			id = Id_left;
		}
		if (s.equals("bytes")) {
			X = "bytes";
			id = Id_bytes;
		}
		if (s.equals("getLV")) {
			X = "getLV";
			id = Id_getLV;
		}
		if (s.equals("right")) {
			X = "right";
			id = Id_right;
		}
		if (s.equals("toHex")) {
			X = "toHex";
			id = Id_toHex;
		}
		if (s.equals("toBcd")) {
			X = "toBcd";
			id = Id_toBcd;
		}
		if (s.equals("concat")) {
			X = "concat";
			id = Id_concat;
		}
		if (s.equals("equals")) {
			X = "equals";
			id = Id_equals;
		}
		if (s.equals("toString")) {
			X = "toString";
			id = Id_toString;
		}
		if (s.equals("toSigned")) {
			X = "toSigned";
			id = Id_toSigned;
		}
		if (s.equals("toBase64")) {
			X = "toBase64";
			id = Id_toBase64;
		}
		if (s.equals("startsWith")) {
			X = "startsWith";
			id = Id_startsWith;
		}
		if (s.equals("toUnsigned")) {
			X = "toUnsigned";
			id = Id_toUnsigned;
		}
		if (s.equals("constructor")) {
			X = "constructor";
			id = Id_constructor;
		}
		if (s.equals("hex2String")) {
			X = "hex2String";
			id = Id_hex2String;
		}
		if (s.equals("deleteEightZero")) {
			X = "deleteEight";
			id = Id_deleteEightZero;
		}

		return id;
	}

	private static final int Id_length = 1, MAX_INSTANCE_ID = 1;

	private static final int Id_constructor = MAX_INSTANCE_ID + 1, Id_toString = MAX_INSTANCE_ID + 2, Id_and = MAX_INSTANCE_ID + 3, Id_bytes = MAX_INSTANCE_ID + 4, Id_concat = MAX_INSTANCE_ID + 5, Id_crc = MAX_INSTANCE_ID + 6, Id_equals = MAX_INSTANCE_ID + 7, Id_find = MAX_INSTANCE_ID + 8, Id_getL = MAX_INSTANCE_ID + 9, Id_getLV = MAX_INSTANCE_ID + 10, Id_left = MAX_INSTANCE_ID + 11, Id_neg = MAX_INSTANCE_ID + 12, Id_not = MAX_INSTANCE_ID + 13, Id_or = MAX_INSTANCE_ID + 14, Id_pad = MAX_INSTANCE_ID + 15, Id_right = MAX_INSTANCE_ID + 16, Id_startsWith = MAX_INSTANCE_ID + 17, Id_toBase64 = MAX_INSTANCE_ID + 18, Id_toBcd = MAX_INSTANCE_ID + 19, Id_toHex = MAX_INSTANCE_ID + 20, Id_toSigned = MAX_INSTANCE_ID + 21, Id_toUnsigned = MAX_INSTANCE_ID + 22, Id_xor = MAX_INSTANCE_ID + 23, Id_hex2String = MAX_INSTANCE_ID + 24, Id_deleteEightZero = MAX_INSTANCE_ID + 25,

	MAX_PROTOTYPE_ID = MAX_INSTANCE_ID + 25;

	private static final int ConstructorId_fromCharCode = MAX_PROTOTYPE_ID + 1;

	// for constructure function
	private static final String defaultValue = "";
	private static final int defaultEncoding = 3;

	// private String string;

	private boolean prototypeFlag;

	/** The value is used for byte storage. */
	private byte value[];
	/** The offset is the first index of the storage that is used. */
	private int offset;
	/** The count is the number of byte in the ByteString. */
	private int count;

	// properties
	public int length = 0;
	public byte[] field;
	// constant
	public static final int XOR = 111; // used in crc() method

	public static void main(String args[]) {
		NativeByteString test = new NativeByteString("3031323334", GPConstant.CN);
		String aa = "3031323334";
		for (int i = 0; i < aa.length() / 2; i++) {
			System.out.println(Integer.parseInt(aa.substring(i, i + 2), 16));
		}
		// System.out.println("test:"+test.toString());

	}

}
