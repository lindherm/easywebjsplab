package com.gp.gpscript.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Scriptable;

import com.gp.gpscript.keymgr.util.encoders.Hex;

/**
 * 
 * <p>
 * Title: Application
 * </p>
 * <p>
 * Description: This class implements the Application (a built-in object). See [GP_SYS_SCR] 7.1.4 This class should be created before executing script. when ApplicationInfo Type="OTHER"
 * 
 * @see GP Script Specification
 *      </p>
 *      <p>
 *      Copyright: Copyright (c) 2002
 *      </p>
 *      <p>
 *      Company: watchdata
 *      </p>
 * @author SunJingang
 * @version 1.0
 */
public class NativeApplication extends IdScriptableObject {
	private static final Object APPLICATION_TAG = new Object();
	private static final NativeApplication prototypeApplication = new NativeApplication();

	public static void init(Context cx, Scriptable scope, boolean sealed) {
		prototypeApplication.activatePrototypeMap(MAX_PROTOTYPE_ID);
		prototypeApplication.setPrototype(getObjectPrototype(scope));
		prototypeApplication.setParentScope(scope);
		if (sealed) {
			prototypeApplication.sealObject();
		}
	}

	public NativeApplication() {
	}

	// reset() should be called when A NativeGPAppliation Object is reuseed for more than one card Personlization
	public void reset() {
	}

	/**
	 * Returns the name of this GP class, "Application".
	 */
	public String getClassName() {
		return "Application";
	}

	public void setObjectPrototype() {
		setPrototype(prototypeApplication);
		// setParentScope(prototypeApplication.getParentScope());
	}

	protected int findInstanceIdInfo(String s) {
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
		if (s.equals("secureChannel")) {
			return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_secureChannel);
		}
		return super.findInstanceIdInfo(s);
	}

	protected String getInstanceIdName(int id) {
		if (id == Id_key) {
			return "key";
		}
		if (id == Id_data) {
			return "data";
		}
		if (id == Id_crypto) {
			return "crypto";
		}
		if (id == Id_profile) {
			return "profile";
		}
		if (id == Id_card) {
			return "card";
		}
		if (id == Id_secureChannel) {
			return "secureChannel";
		}

		return super.getInstanceIdName(id);
	}

	protected Object getInstanceIdValue(int id) {
		if (id == Id_key)
			return key; // return a key array
		if (id == Id_data)
			return data; // return a data array
		if (id == Id_crypto)
			return crypto;
		if (id == Id_profile)
			return profile;
		if (id == Id_card)
			return card;
		if (id == Id_secureChannel)
			return secureChannel;

		return super.getInstanceIdValue(id);
	}

	public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
		if (!f.hasTag(APPLICATION_TAG)) {
			return super.execIdCall(f, cx, scope, thisObj, args);
		}

		int id = f.methodId();
		switch (id) {
		case Id_select:
			return realThis(thisObj, f).jsFunction_select(cx, scope, args);
		case Id_sendApdu:
			return realThis(thisObj, f).jsFunction_sendApdu(cx, scope, args);
		case Id_openSecureChannel:
			return realThis(thisObj, f).jsFunction_openSecureChannel(cx, scope, args);
		}
		throw new IllegalArgumentException(String.valueOf(id));
	}

	// thisObj
	private NativeApplication realThis(Scriptable thisObj, IdFunctionObject f) {
		if (!(thisObj instanceof NativeApplication))
			throw incompatibleCallError(f);
		return (NativeApplication) thisObj;
	}

	/**
	 * execute the script of openSecureChannel modify the Param of the script
	 * 
	 * @param cx
	 * @param scope
	 * @param args
	 * @return
	 */
	private Number jsFunction_openSecureChannel(Context cx, Scriptable scope, Object[] args) {
		Object result = null;
		int level = ((Number) args[0]).intValue();
		String strLevel = ((Number) args[0]).toString();
		StringBuffer strWrap = new StringBuffer();

		cx.evaluateString(scope, "level=" + strLevel + ";" + this.profile.SecureChannel.OpenSecureChannel.Script.Script, "OpenSecureChannel", 0, null);
		secureLevel = level;

		if (wrapFunction == null && this.profile.SecureChannel.Wrap != null) // compile function first time.
		{
			strWrap.append("ByteString function SecureChannelWrap(");
			strWrap.append(this.profile.SecureChannel.Wrap.Param);
			strWrap.append(")\r\n");
			strWrap.append("{\r\n");
			strWrap.append(this.profile.SecureChannel.Wrap.Script.Script);
			strWrap.append("}\r\n");
			wrapFunction = cx.compileFunction(scope, strWrap.toString(), "SecureChannelWrap", 1, null);
		}
		// have secure channel , then compile a wrap function for APDU wrap later.

		return new Integer(level);
		/*
		 * String fileName=GP_Global.scriptDir+GP_Global.openSecureChannelScriptFile; //create the scripts try { String ecureChannelScript=""; ecureChannelScript=ecureChannelScript+"level="+strLevel+";"; ecureChannelScript=ecureChannelScript+this.profile.SecureChannel.OpenSecureChannel.Script.Script; DataOutputStream out=new DataOutputStream(new FileOutputStream(fileName)); out.writeBytes(ecureChannelScript); out.close(); } catch(Exception e) { e.printStackTrace(); System.out.println(e.getMessage()); throw new EvaluatorException( (new GPError("Application", 0, 0, "create openSecure Script error")).toString()); } //execute the script try { result = RunScript.processSource(cx, scope, fileName); System.out.println("openSecureChannel is successed!"); AppSecureLevel=level; return new Integer(level); } catch (GPError egErr) { egErr.printStackTrace(); System.out.println(egErr.toString()); result = null; // Exit from the context. Context.exit(); return new Integer(-1); }
		 */

	}

	/**
	 * sendApdu through secure channel
	 * 
	 * @param cx
	 * @param scope
	 * @param args
	 *            maybe have 4/5/6/7 parameters update the property of card(response without SW, SW, SW1, SW2)
	 * @return the response of the APDU(have the SW)
	 */
	private NativeByteString jsFunction_sendApdu(Context cx, Scriptable scope, Object[] args) {
		if (!((args[0] instanceof Number) && (args[1] instanceof Number) && (args[2] instanceof Number) && (args[3] instanceof Number)))
			throw new EvaluatorException((new GPError("Application", GPError.INVALID_TYPE)).toString());
		// define the default parameter
		NativeByteString defaultData = null;
		Number defaultLE = new Integer(-1);
		Number defaultSW = new Integer(0x9000);
		if (args.length == 4) {
			NativeByteString sNew = this.card.sendApdu(cx, scope, (Number) args[0], (Number) args[1], (Number) args[2], (Number) args[3], defaultData, defaultLE, defaultSW);
			return NativeByteString.newByteString(cx, scope, sNew);
		} else if (args.length == 5) {
			if (!((args[4] instanceof NativeByteString) || (args[4] == null)))
				throw new EvaluatorException((new GPError("Application", GPError.INVALID_TYPE)).toString());
			else {
				NativeByteString sNew = this.card.sendApdu(cx, scope, (Number) args[0], (Number) args[1], (Number) args[2], (Number) args[3], (NativeByteString) args[4], defaultLE, defaultSW);
				return NativeByteString.newByteString(cx, scope, sNew);
			}
		} else if (args.length == 6) {
			if (!(((args[4] instanceof NativeByteString) || (args[4] == null)) && (args[5] instanceof Number)))
				throw new EvaluatorException((new GPError("Application", GPError.INVALID_TYPE)).toString());
			else {
				NativeByteString sNew = this.card.sendApdu(cx, scope, (Number) args[0], (Number) args[1], (Number) args[2], (Number) args[3], (NativeByteString) args[4], (Number) args[5], defaultSW);
				return NativeByteString.newByteString(cx, scope, sNew);
			}
		} else if (args.length == 7) {
			if (!(((args[4] instanceof NativeByteString) || (args[4] == null)) && (args[5] instanceof Number) && (args[6] instanceof Number)))
				throw new EvaluatorException((new GPError("Application", GPError.INVALID_TYPE)).toString());
			else {
				NativeByteString sNew = this.card.sendApdu(cx, scope, (Number) args[0], (Number) args[1], (Number) args[2], (Number) args[3], (NativeByteString) args[4], (Number) args[5], (Number) args[6]);
				return NativeByteString.newByteString(cx, scope, sNew);
			}
		} else
			throw new EvaluatorException((new GPError("Application", GPError.INVALID_ARGUMENTS)).toString());

	}

	/**
	 * select the application according to the AID of the card profile
	 * 
	 * @param cx
	 * @param scope
	 * @param args
	 *            args should be next and sw next default is false sw default is 0x9000 send APDU(0x00 0xA4 0x04 .....) AID should be retrieved from the card profile
	 * @return
	 */
	private NativeByteString jsFunction_select(Context cx, Scriptable scope, Object[] args) {
		String strData = "A0000000031010";// strAID;
		// String strData=GP_Global.getAIDfromCardProfile(this.card.profile,this.profile.ap);

		Number P2 = new Integer(0x00);
		Number SW = new Integer(0x9000);
		if (args.length == 1) {
			if (!(args[0] instanceof Boolean))
				throw new EvaluatorException((new GPError("Application", GPError.INVALID_TYPE)).toString());
			if (((Boolean) args[0]).booleanValue() == true)
				P2 = new Integer(0x02);
			else
				P2 = new Integer(0x00);
		} else if (args.length == 2) {
			if ((!(args[0] instanceof Boolean)) || (!(args[1] instanceof Number)))
				throw new EvaluatorException((new GPError("Application", GPError.INVALID_TYPE)).toString());
			if (((Boolean) args[0]).booleanValue() == true)
				P2 = new Integer(0x02);
			else
				P2 = new Integer(0x00);
			SW = (Number) args[1];
		}

		NativeByteString comData = new NativeByteString(strData, new Integer(GPConstant.HEX));

		// send command
		this.card.sendApdu(cx, scope, new Integer(0x00), new Integer(0xA4), new Integer(0x04), P2, comData, new Integer(-1), SW);

		NativeByteString sNew = this.card.response;
		return sNew;
	}

	// **************************************************/

	protected int maxInstanceId() {
		return MAX_INSTANCE_ID;
	}

	protected void initPrototypeId(int id) {
		String s;
		int arity;
		switch (id) {
		// function
		case Id_select:
			arity = 3;
			s = "select";
			break;
		case Id_sendApdu:
			arity = 7;
			s = "sendApdu";
			break;
		case Id_openSecureChannel:
			arity = 0;
			s = "openSecureChannel";
			break;
		/*
		 * case Id_card: return "card"; case Id_crypto: return "crypto"; case Id_data: return "data"; case Id_key: return "key"; case Id_profile: return "profile"; case Id_secureChannel: return "secureChannel";
		 */
		default:
			throw new IllegalArgumentException(String.valueOf(id));
		}
		initPrototypeMethod(APPLICATION_TAG, id, s, arity);
	}

	protected int findPrototypeId(String s) {
		int id = 0;
		String X = null;

		if (s.equals("key")) {
			X = "key";
			id = Id_key;
		}
		if (s.equals("card")) {
			X = "card";
			id = Id_card;
		}
		if (s.equals("data")) {
			X = "data";
			id = Id_data;
		}
		if (s.equals("select")) {
			X = "select";
			id = Id_select;
		}
		if (s.equals("crypto")) {
			X = "crypto";
			id = Id_crypto;
		}
		if (s.equals("profile")) {
			X = "profile";
			id = Id_profile;
		}
		if (s.equals("sendApdu")) {
			X = "sendApdu";
			id = Id_sendApdu;
		}
		if (s.equals("secureChannel")) {
			X = "secureChannel";
			id = Id_secureChannel;
		}
		if (s.equals("openSecureChannel")) {
			X = "openSecureChannel";
			id = Id_openSecureChannel;
		}

		return id;
	}

	/**
	 * sendApdu
	 * 
	 * @param cx
	 * @param scope
	 * @param p1
	 *            CLA
	 * @param p2
	 *            INS
	 * @param p3
	 *            PP1
	 * @param p4
	 *            PP2
	 * @param p5
	 *            commdata (default=null)
	 * @param p6
	 *            LE(default is -1)
	 * @param p7
	 *            SW(default is 0x9000)
	 * @return the response of APDU
	 */
	public NativeByteString WrapApdu(Context cx, Scriptable scope, Number p1, Number p2, Number p3, Number p4, NativeByteString p5, Number p6, Number p7) {
		int CLA, INS, PP1, PP2, LE, sw;
		CLA = (int) p1.intValue();
		INS = (int) p2.intValue();
		PP1 = (int) p3.intValue();
		PP2 = (int) p4.intValue();
		LE = (int) p6.intValue();
		sw = (int) p7.intValue();
		// get comdata
		byte[] data = null;
		if (p5 instanceof NativeByteString) {
			int LC = p5.GetLength();
			data = new byte[LC];
			for (int i = 0; i < LC; i++)
				data[i] = p5.ByteAt(i);
		}

		byte[] comm = new byte[300];
		int n = 0, commlen = 4;
		comm[0] = (byte) CLA;
		comm[1] = (byte) INS;
		comm[2] = (byte) PP1;
		comm[3] = (byte) PP2;
		if (data != null) {
			comm[4] = (byte) data.length;
			commlen = 5;
			for (n = 0; n < data.length; n++)
				comm[5 + n] = data[n];
			commlen = 5 + n;
			if (LE != -1) {
				comm[5 + n] = (byte) LE;
				commlen = 6 + n;
			}
		} else {
			if (LE != -1) {
				comm[4] = (byte) LE;
				commlen = 5;
			}
		}

		byte[] command = new byte[commlen];
		for (n = 0; n < commlen; n++)
			command[n] = comm[n];

		NativeByteString apdu = new NativeByteString(command);
		NativeByteString unwrapedApdu = NativeByteString.newByteString(cx, scope, apdu);
		Object[] args = { unwrapedApdu };
		Object result = wrapFunction.call(cx, scope, scope, args);
		if (!(result instanceof NativeByteString))
			throw new EvaluatorException((new GPError("GPApplication", 0, 0, "Wrap function should return ByteString")).toString());

		String strResult = ((NativeByteString) result).toString();
		byte[] resp = this.card.sendApdu(cx, scope, Hex.decode(strResult), strResult.length());
		NativeByteString strResp = new NativeByteString(resp);
		return NativeByteString.newByteString(cx, scope, strResp);
	}

	// define value for all function and constant
	private static final int Id_openSecureChannel = 1, Id_select = 2, Id_sendApdu = 3, LAST_METHOD_ID = 3, MAX_PROTOTYPE_ID = 3;

	// the following should be contants
	private static final int Id_card = 1, Id_crypto = 2, Id_data = 3, Id_key = 4, Id_profile = 5, Id_secureChannel = 6, MAX_INSTANCE_ID = LAST_METHOD_ID + 6;

	public NativeCard card;
	public NativeCrypto crypto;
	// private ApplicationProfile profile;
	public Application profile;
	// private NativeGPScp01 secureChannel;
	public IdScriptableObject secureChannel;
	// key[]
	public NativeArray key;
	// public Scriptable KeyArray;
	// data[]
	public NativeArray data;
	// public Scriptable DataArray;

	private static org.mozilla.javascript.Function wrapFunction = null;
	/**
	 * use by each command of the GPApplication 0-No; 1-C_Mac; 3-C_Mac_Enc
	 */
	public int secureLevel = 0;

}
