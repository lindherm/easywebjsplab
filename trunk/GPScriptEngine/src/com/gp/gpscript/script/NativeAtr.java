package com.gp.gpscript.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.Scriptable;

import com.gp.gpscript.utils.Hex;

/**
 * 
 * <p>
 * Title: NativeAtr
 * </p>
 * <p>
 * Description: is created when the reset() method of the card object is executed. See [GP_SYS_SCR] 7.2.1
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: watchdata
 * </p>
 * 
 * @author SunJinGnag
 * @version 1.0
 */
public class NativeAtr extends IdScriptableObject {
	private static final Object ATR_TAG = new Object();

	public static void init(Context cx, Scriptable scope, boolean sealed) {
	}

	public NativeAtr() {
	}

	// name used in script
	public String getClassName() {
		return "Atr";
	}

	public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
		if (!f.hasTag(ATR_TAG)) {
			return super.execIdCall(f, cx, scope, thisObj, args);
		}

		int id = f.methodId();
		switch (id) {
		case Id_toByteString:
			return realThis(thisObj, f).jsFunction_toByteString();

		case Id_toString:
			return realThis(thisObj, f).jsFunction_toString();

		}
		throw new IllegalArgumentException(String.valueOf(id));
	}

	// thisObj
	private static NativeAtr realThis(Scriptable thisObj, IdFunctionObject f) {
		if (!(thisObj instanceof NativeAtr))
			throw incompatibleCallError(f);
		return (NativeAtr) thisObj;
	}

	/*
	 * private static RegExpProxy checkReProxy(Context cx) { RegExpProxy result = cx.getRegExpProxy(); if (result == null) { throw cx.reportRuntimeError0("msg.no.regexp"); } return result; }
	 */

	public String toString() {
		// return value.toString();
		return jsFunction_toString();
	}

	// for display the object
	private String jsFunction_toString() {
		// return value.toString();
		return this.jsFunction_toByteString().toString();
	}

	/*************************************************/
	/* GP function */
	/* throw exception according to the parameter***** */
	/*************************************************/

	// GP-getL(..)
	public NativeByteString jsFunction_toByteString() {
		byte[] str1 = new byte[1];
		byte[] str2 = new byte[1];
		str1[0] = formatByte;
		str2[0] = tckByte;
		String strL = "3B" + new String(Hex.encode(str1)) + new String(Hex.encode(interfaceBytes)) + new String(Hex.encode(historcalBytes)) + new String(Hex.encode(str2));
		Integer ee = new Integer(GPConstant.HEX);
		NativeByteString sNew = new NativeByteString(strL, ee);
		return sNew;
	}

	// ///////////////////////////////////////////////////////////////////////////

	protected int maxInstanceId() {
		return MAX_INSTANCE_ID;
	}

	protected void initPrototypeId(int id) {
		String s;
		int arity;
		switch (id) {
		case Id_toByteString:
			arity = 0;
			s = "toByteString";
			break;
		case Id_toString:
			arity = 0;
			s = "toString";
			break;

		default:
			throw new IllegalArgumentException(String.valueOf(id));
		}
		initPrototypeMethod(ATR_TAG, id, s, arity);
	}

	protected int findPrototypeId(String s) {
		int id = 0;
		String X = null;
		if (s.equals("toString")) {
			X = "toString";
			id = Id_toString;
		}
		if (s.equals("toByteString")) {
			X = "toByteString";
			id = Id_toByteString;
		}
		return id;
	}

	private static final int MAX_INSTANCE_ID = 0;

	private static final int Id_toByteString = MAX_INSTANCE_ID + 1, Id_toString = MAX_INSTANCE_ID + 2,

	MAX_PROTOTYPE_ID = MAX_INSTANCE_ID + 2;

	public byte formatByte = 0;
	public byte[] historcalBytes;
	public byte[] interfaceBytes;
	public byte tckByte = 0;
}
