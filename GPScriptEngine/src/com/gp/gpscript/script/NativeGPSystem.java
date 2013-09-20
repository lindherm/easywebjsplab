package com.gp.gpscript.script;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

//import com.watchdata.wdcams.loader.Loader;

/**
 * 
 * <p>
 * Title: GPSystem
 * </p>
 * <p>
 * Description: This class implements the GPSystem (a built-in object). See [GP_SYS_SCR] 7.1.3
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
public class NativeGPSystem extends IdScriptableObject {
	private Logger log = Logger.getLogger(NativeGPSystem.class);
	private static final Object GPSYSTEM_TAG = new Object();

	public static void init(Context cx, Scriptable scope, boolean sealed) {
		NativeGPSystem obj = new NativeGPSystem();
		obj.activatePrototypeMap(MAX_ID);
		obj.setPrototype(getObjectPrototype(scope));
		obj.setParentScope(scope);
		if (sealed) {
			obj.sealObject();
		}
		ScriptableObject.defineProperty(scope, "GPSystem", obj, ScriptableObject.DONTENUM);
	}

	public String getClassName() {
		return "GPSystem";
	}

	public String intToStr(int n, int len) // example: 9-->"09";12-->"12"
	{
		String ss = Integer.toString(n);
		while (ss.length() < len)
			ss = "0" + ss;
		return ss;
	}

	/**
	 * dataTimeByteString
	 * 
	 * @return a ByteString of date and time
	 */
	private NativeByteString jsFunction_dataTimeByteString(Context cx, Scriptable scope) {
		Calendar calendar = new GregorianCalendar();
		int nYear, nMonth, nDay, nHour, nMin, nSec;
		nYear = calendar.get(Calendar.YEAR);
		nMonth = calendar.get(Calendar.MONTH) + 1;
		nDay = calendar.get(Calendar.DATE);
		nHour = calendar.get(Calendar.HOUR_OF_DAY);
		nMin = calendar.get(Calendar.MINUTE);
		nSec = calendar.get(Calendar.SECOND);
		String ss = intToStr(nYear, 4) + intToStr(nMonth, 2) + intToStr(nDay, 2) + intToStr(nHour, 2) + intToStr(nMin, 2) + intToStr(nSec, 2);
		Integer ee = new Integer(GPConstant.HEX);
		NativeByteString sNew = new NativeByteString(ss, ee);
		return NativeByteString.newByteString(cx, scope, sNew);
	}

	/**
	 * getSystemID()
	 * 
	 * @return a ByteString containing the SystemID
	 */
	private NativeByteString jsFunction_getSystemID(Context cx, Scriptable scope) {
		String strID = "112233445566778899";
		Integer ee = new Integer(GPConstant.HEX);
		NativeByteString sNew = new NativeByteString(strID, ee);
		return NativeByteString.newByteString(cx, scope, sNew);
	}

	/**
	 * getVendorObject
	 * 
	 * @param args
	 *            name of the object to retrive
	 * @return
	 */
	private Object jsFunction_getVendorObject(Context cx, Scriptable scope, Object[] args) {
		if (!(args[0] instanceof String))
			throw new EvaluatorException((new GPError("GPSystem", GPError.INVALID_TYPE)).toString());
		String strObj = (String) args[0];
		strObj = strObj.toUpperCase();
		if (strObj.equals("GPSUPPORT")) {
			return null;
			/*
			 * NativeGPSupport ss=new NativeGPSupport(); return ss.newGPSupport(cx,scope);
			 */
		} else {
			log.error("There're no this object self-defined");
			return null;
		}
	}

	/**
	 * getVersion()
	 * 
	 * @return version
	 */
	private String jsFunction_getVersion() {
		return "1.0.0";
	}

	/**
	 * trace append args.toString to trace file
	 * 
	 * @param args
	 *            the retrive object
	 * @return
	 */
	private Boolean jsFunction_trace(Object[] args) {
		String str = "";
		if (args.length > 1)
			return Boolean.FALSE;
		if (args.length == 0)
			str = "";
		if (args.length == 1) {
			if (args[0] == null)
				str = "null";
			else
				str = args[0].toString();
		}

		// update the messageBox
		// AddMessageThread.addMsg(str);
		log.debug(str);

		return Boolean.FALSE;
	}

	/**
	 * wait pause a period of value milliseconds
	 * 
	 * @param args
	 *            number
	 */
	private void jsFunction_wait(Context cx, Scriptable scope, Object[] args) {
		if (!(args[0] instanceof Number))
			throw new EvaluatorException((new GPError("GPSystem", GPError.INVALID_TYPE)).toString());
		Integer Num = new Integer(ScriptRuntime.toInt32(args[0]));
		long num = (int) Num.intValue();
		Date today1 = new Date();
		long time1, time2;
		time1 = today1.getTime();
		// AddMessageThread.addMsg("Now waitting.....");
		do {
			Date today2 = new Date();
			time2 = today2.getTime();
		} while (num >= (time2 - time1));
	}

	protected void initPrototypeId(int id) {
		String name;
		int arity;
		switch (id) {
		case Id_wait:
			arity = 1;
			name = "wait";
			break;
		case Id_trace:
			arity = 1;
			name = "trace";
			break;
		case Id_getVersion:
			arity = 0;
			name = "getVersion";
			break;
		case Id_getSystemID:
			arity = 0;
			name = "getSystemID";
			break;
		case Id_getVendorObject:
			arity = 1;
			name = "getVendorObject";
			break;
		case Id_dateTimeByteString:
			arity = 0;
			name = "dateTimeByteString";
			break;
		default:
			throw new IllegalStateException(String.valueOf(id));
		}
		initPrototypeMethod(GPSYSTEM_TAG, id, name, arity);
	}

	public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
		if (!f.hasTag(GPSYSTEM_TAG)) {
			return super.execIdCall(f, cx, scope, thisObj, args);
		}
		int methodId = f.methodId();
		switch (methodId) {
		case Id_dateTimeByteString:
			return jsFunction_dataTimeByteString(cx, scope);
		case Id_getSystemID:
			return jsFunction_getSystemID(cx, scope);
		case Id_getVendorObject:
			return jsFunction_getVendorObject(cx, scope, args);
		case Id_getVersion:
			return jsFunction_getVersion();
		case Id_trace:
			return jsFunction_trace(args);
		case Id_wait:
			jsFunction_wait(cx, scope, args);
			return null;
		}
		return null;
	}

	// #string_id_map#

	protected int findPrototypeId(String s) {
		int id = 0;
		String X = null;
		if (s.equals("wait")) {
			X = "wait";
			id = Id_wait;
		}
		if (s.equals("trace")) {
			X = "trace";
			id = Id_trace;
		}
		if (s.equals("getVersion")) {
			X = "getVersion";
			id = Id_getVersion;
		}
		if (s.equals("getSystemID")) {
			X = "getSystemID";
			id = Id_getSystemID;
		}
		if (s.equals("getVendorObject")) {
			X = "getVendorObject";
			id = Id_getVendorObject;
		}
		if (s.equals("dateTimeByteString")) {
			X = "dateTimeByteString";
			id = Id_dateTimeByteString;
		}
		return id;
	}

	// define value for all function and constant
	private static final int Id_dateTimeByteString = 1, Id_getSystemID = 2, Id_getVendorObject = 3, Id_getVersion = 4, Id_trace = 5, Id_wait = 6, LAST_METHOD_ID = 6,
	// the following should be contants
			MAX_ID = 6;

}
