package com.gp.gpscript.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * 
 * <p>
 * Title: GPError
 * </p>
 * <p>
 * Description: This is used to display the error infomation when GP encounter an exception
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: watchdata
 * </p>
 * 
 * @author Sun JinGang
 * @version 1.0
 */

public class GPError extends IdScriptableObject {

	private static final Object GPERROR_TAG = new Object();

	public static void init(Context cx, Scriptable scope, boolean sealed) {
		GPError obj = new GPError();
		/*
		 * ScriptableObject.putProperty(obj, "name", "GPError"); ScriptableObject.putProperty(obj, "message", ""); ScriptableObject.putProperty(obj, "className", ""); ScriptableObject.putProperty(obj, "error", new Integer(0) ); ScriptableObject.putProperty(obj, "reason", new Integer(0) );
		 */obj.exportAsJSClass(MAX_PROTOTYPE_ID, scope, sealed);

	}

	/**
	 * consturctor
	 * 
	 * @param className
	 * @param message
	 * @param error
	 * @param reason
	 */
	public GPError() {
		this.className = "";
		this.message = "";
		this.error = 0;
		this.reason = 0;
	}

	/**
	 * consturctor
	 * 
	 * @param className
	 * @param message
	 * @param error
	 * @param reason
	 */
	public GPError(String className, int error, int reason, String message) {
		this.className = className;
		this.message = message;
		this.error = error;
		this.reason = reason;
	}

	/**
	 * constructor
	 * 
	 * @param className
	 * @param error
	 */
	public GPError(String className, int error) {
		this.className = className;
		this.error = error;

		switch (error) {
		case KEY_NOT_FOUND:
			this.message = "The key is not recognized by the cryptographic device.";
			break;

		case DEVICE_ERROR:
			this.message = "The dedive has encounter a error.";
			break;

		case INVALID_DATA:
			this.message = "The data supplied is invalid.";
			break;

		case INVALID_MECH:
			this.message = "The mechanism or algrithm specified is not supported by the cryptographic device.";
			break;

		case INVALID_KEY:
			this.message = "The Key is not valid for the operation specified.";
			break;

		case INVALID_TYPE:
			this.message = "Invalid data type for parameter supplied.";
			break;

		case INVALID_USAGE:
			this.message = "The key does not have the usage specified for the operation specified.";
			break;

		case USER_DEFINED:
			this.message = "An user defined error.";
			break;

		case UNDEFINED:
			this.message = "An undefined Error.";
			break;

		case UNSUPPORTED:
			this.message = "The device does not support the requested functionally.";
			break;

		case OBJECTCREATIONFAILED:
			this.message = "Object could not be created.";
			break;

		}

		this.error = error;
		this.reason = 1;
	}

	private static final int Id_className = 1, Id_error = 2, Id_message = 3, Id_name = 4, Id_reason = 5,

	MAX_INSTANCE_ID = 5;

	protected int getMaxInstanceId() {
		return MAX_INSTANCE_ID;
	}

	protected int findInstanceIdInfo(String s) {
		if (s.equals("className")) {
			return instanceIdInfo(DONTENUM, Id_className);
		}
		if (s.equals("error")) {
			return instanceIdInfo(DONTENUM, Id_error);
		}
		if (s.equals("message")) {
			return instanceIdInfo(DONTENUM, Id_message);
		}
		if (s.equals("name")) {
			return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_name);
		}
		if (s.equals("reason")) {
			return instanceIdInfo(DONTENUM, Id_reason);
		}
		return super.findInstanceIdInfo(s);
	}

	protected String getInstanceIdName(int id) {
		if (id == Id_className) {
			return "className";
		}
		if (id == Id_error) {
			return "error";
		}
		if (id == Id_message) {
			return "message";
		}
		if (id == Id_name) {
			return "name";
		}
		if (id == Id_reason) {
			return "reason";
		}
		return super.getInstanceIdName(id);
	}

	protected Object getInstanceIdValue(int id) {
		if (id == Id_className) {
			return className;
		}
		if (id == Id_error) {
			return ScriptRuntime.wrapInt(error);
		}
		if (id == Id_message) {
			return message;
		}
		if (id == Id_name) {
			return name;
		}
		if (id == Id_reason) {
			return ScriptRuntime.wrapInt(reason);
		}
		return super.getInstanceIdValue(id);
	}

	public String getClassName() {
		return "GPError";
	}

	protected void fillConstructorProperties(IdFunctionObject ctor) {
		final int attr = ScriptableObject.DONTENUM | ScriptableObject.PERMANENT | ScriptableObject.READONLY;

		ctor.defineProperty("KEY_NOT_FOUND", ScriptRuntime.wrapNumber(KEY_NOT_FOUND), attr);
		ctor.defineProperty("DEVICE_ERROR", ScriptRuntime.wrapNumber(DEVICE_ERROR), attr);
		ctor.defineProperty("INVALID_DATA", ScriptRuntime.wrapNumber(INVALID_DATA), attr);
		ctor.defineProperty("INVALID_MECH", ScriptRuntime.wrapNumber(INVALID_MECH), attr);
		ctor.defineProperty("INVALID_KEY", ScriptRuntime.wrapNumber(INVALID_KEY), attr);
		ctor.defineProperty("INVALID_TYPE", ScriptRuntime.wrapNumber(INVALID_TYPE), attr);
		ctor.defineProperty("INVALID_USAGE", ScriptRuntime.wrapNumber(INVALID_USAGE), attr);
		ctor.defineProperty("USER_DEFINED", ScriptRuntime.wrapNumber(USER_DEFINED), attr);
		ctor.defineProperty("UNDEFINED", ScriptRuntime.wrapNumber(UNDEFINED), attr);
		ctor.defineProperty("UNSUPPORTED", ScriptRuntime.wrapNumber(UNSUPPORTED), attr);
		ctor.defineProperty("OBJECTCREATIONFAILED", ScriptRuntime.wrapNumber(OBJECTCREATIONFAILED), attr);

		super.fillConstructorProperties(ctor);
	}

	static GPError js_construct(Context cx, Scriptable scope, IdFunctionObject ctorObj, Object[] args) {
		if (args.length != 4)
			throw new EvaluatorException((new GPError("GPError", GPError.INVALID_ARGUMENTS)).toString());
		Scriptable proto = (Scriptable) (ctorObj.get("prototype", ctorObj));

		GPError obj = new GPError();
		obj.setPrototype(proto);
		obj.setParentScope(scope);

		if (args.length >= 1) {
			obj.className = ScriptRuntime.toString(args[0]);
			if (args.length >= 2) {
				obj.error = ScriptRuntime.toInt32(args[1]);
				if (args.length >= 3) {
					obj.reason = ScriptRuntime.toInt32(args[2]);
					if (args.length >= 4) {
						obj.message = ScriptRuntime.toString(args[3]);
					}
				}
			}
		}
		return obj;
	}

	protected void initPrototypeId(int id) {
		String s;
		int arity;
		switch (id) {
		case Id_constructor:
			arity = 1;
			s = "constructor";
			break;
		case Id_toString:
			arity = 0;
			s = "toString";
			break;

		default:
			throw new IllegalArgumentException(String.valueOf(id));
		}
		initPrototypeMethod(GPERROR_TAG, id, s, arity);
	}

	public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
		if (!f.hasTag(GPERROR_TAG)) {
			return super.execIdCall(f, cx, scope, thisObj, args);
		}
		int id = f.methodId();
		switch (id) {
		case Id_constructor:
			return js_construct(cx, scope, f, args);

		case Id_toString:
			return js_toString(thisObj);

		}
		throw new IllegalArgumentException(String.valueOf(id));
	}

	/**
	 * Return a string representation of the error, which currently consists of the name of the error together with the message.
	 */

	public String toString() {
		return js_toString(this);
	}

	private static String js_toString(Scriptable thisObj) {
		StringBuffer buf = new StringBuffer();
		buf.append("\nGPError: ClassName [");
		buf.append(getString(thisObj, "className"));
		buf.append("] message[");
		buf.append(getString(thisObj, "message"));
		buf.append("] error[");
		buf.append(getString(thisObj, "error"));
		buf.append("] reason[");
		buf.append(getString(thisObj, "reason"));
		buf.append("]");

		return buf.toString();
	}

	private static String getString(Scriptable obj, String id) {
		Object value = ScriptableObject.getProperty(obj, id);
		if (value == NOT_FOUND)
			return "";
		return ScriptRuntime.toString(value);
	}

	protected int findPrototypeId(String s) {
		int id;
		// #string_id_map#
		// #generated# Last update: 2004-03-17 13:35:15 CET
		L0: {
			id = 0;
			String X = null;
			int c;
			int s_length = s.length();
			if (s_length == 8) {
				X = "toString";
				id = Id_toString;
			} else if (s_length == 11) {
				X = "constructor";
				id = Id_constructor;
			}
			if (X != null && X != s && !X.equals(s))
				id = 0;
		}
		// #/generated#
		return id;
	}

	private String className;
	private String message;
	private String name = "GPError";
	private int error;
	private int reason;

	public static final int KEY_NOT_FOUND = 1030;
	public static final int DEVICE_ERROR = 1031;
	public static final int INVALID_DATA = 1032;
	public static final int INVALID_MECH = 1033;
	public static final int INVALID_KEY = 1034;
	public static final int INVALID_TYPE = 1035;
	public static final int INVALID_USAGE = 1036;
	public static final int USER_DEFINED = 1037;
	public static final int UNDEFINED = 1038;
	public static final int UNSUPPORTED = 1039;
	public static final int OBJECTCREATIONFAILED = 1040;
	public static final int INVALID_ARGUMENTS = 1041;

	private static final int Id_constructor = 1, Id_toString = 2,

	MAX_PROTOTYPE_ID = 2;

}
