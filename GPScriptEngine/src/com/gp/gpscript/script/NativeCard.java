package com.gp.gpscript.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import com.gp.gpscript.profile.card.CardProfile;
import com.gp.gpscript.utils.Hex;

/**
 * 
 * <p>
 * Title: Card
 * </p>
 * <p>
 * Description: This class implements the Card (a biult-in object). See [GP_SYS_SCR] 7.1.10
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
public class NativeCard extends IdScriptableObject {
	private static final Object CARD_TAG = new Object();
	private static final NativeCard prototypeCard = new NativeCard();

	public static void init(Context cx, Scriptable scope, boolean sealed) {
		prototypeCard.activatePrototypeMap(MAX_PROTOTYPE_ID);
		prototypeCard.setPrototype(getObjectPrototype(scope));
		prototypeCard.setParentScope(scope);
		final int attr = ScriptableObject.DONTENUM | ScriptableObject.PERMANENT | ScriptableObject.READONLY;

		// enable property used local
		prototypeCard.defineProperty("T0", new Integer(T0), attr);
		prototypeCard.defineProperty("T1", new Integer(T1), attr);
		prototypeCard.defineProperty("T14", new Integer(T14), attr);
		prototypeCard.defineProperty("OTHER", new Integer(OTHER), attr);
		prototypeCard.defineProperty("RESET_COLD", new Integer(RESET_COLD), attr);
		prototypeCard.defineProperty("RESET_WARM", new Integer(RESET_WARM), attr);

		if (sealed) {
			prototypeCard.sealObject();
		}
		ScriptableObject.defineProperty(scope, "Card", prototypeCard, ScriptableObject.DONTENUM);
	}

	public NativeCard() {

	}

	public String getClassName() {
		return "Card";
	}

	public void setObjectPrototype() {
		setPrototype(prototypeCard);
		// setParentScope(prototypeCard.getParentScope());
	}

	protected int getMaxInstanceId() {
		return MAX_INSTANCE_ID;
	}

	protected int findInstanceIdInfo(String s) {
		if (s.equals("response")) {
			return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_response);
		}
		if (s.equals("profile")) {
			return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_profile);
		}
		if (s.equals("SW")) {
			return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_SW);
		}
		if (s.equals("SW1")) {
			return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_SW1);
		}
		if (s.equals("SW2")) {
			return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_SW2);
		}
		return super.findInstanceIdInfo(s);
	}

	protected String getInstanceIdName(int id) {
		if (id == Id_response) {
			return "response";
		}
		if (id == Id_profile) {
			return "profile";
		}
		if (id == Id_SW) {
			return "SW";
		}
		if (id == Id_SW1) {
			return "SW1";
		}
		if (id == Id_SW2) {
			return "SW2";
		}
		return super.getInstanceIdName(id);
	}

	protected Object getInstanceIdValue(int id) {
		if (id == Id_response) {
			return response;
		}
		if (id == Id_profile) {
			return profile;
		}
		if (id == Id_SW) {
			return SW;
		}
		if (id == Id_SW1) {
			return SW1;
		}
		if (id == Id_SW2) {
			return SW2;
		}
		return super.getInstanceIdValue(id);
	}

	public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
		if (!f.hasTag(CARD_TAG)) {
			return super.execIdCall(f, cx, scope, thisObj, args);
		}

		int id = f.methodId();
		switch (id) {
		case Id_sendApdu:
			return realThis(thisObj, f).jsFunction_sendApdu(cx, scope, args);
		case Id_reset:
			return realThis(thisObj, f).js_reset(args).jsFunction_toByteString().toString();

		}
		throw new IllegalArgumentException(String.valueOf(id));
	}

	/***********************************************/
	/* all function */
	/***********************************************/
	/**
	 * set the property(response,sw...) according to the response
	 * 
	 * @param strResp
	 */
	public void updateSW(Context cx, Scriptable scope, String strResp) {
		String strSW = strResp.substring(strResp.length() - 2 * 2); // card sw
		if (strResp.length() > 4) // have Response
			response = NativeByteString.newByteString(cx, scope, new NativeByteString(strResp.substring(0, strResp.length() - 2 * 2), new Integer(GPConstant.HEX)));
		else
			// no response data
			response = new NativeByteString();
		byte[] sw = new byte[2];
		sw = Hex.decode(strSW);
		SW = new Integer(Hex.ByteToUnsigned(sw[0]) * 256 + Hex.ByteToUnsigned(sw[1]));
		SW1 = NativeByteString.newByteString(cx, scope, new NativeByteString(strSW.substring(0, 2), new Integer(GPConstant.HEX)));
		SW2 = NativeByteString.newByteString(cx, scope, new NativeByteString(strSW.substring(2, 4), new Integer(GPConstant.HEX)));
	}

	/**
	 * and throw exception according to the expected sw and the result sw this function is used by sendApdu
	 * 
	 * @param strResp
	 * @param sw
	 */
	private void SWException(String strResp, int sw) {
		String strSW;
		strSW = strResp.substring(strResp.length() - 2 * 2); // card sw
		if (sw == 0x9000) {
			if (!strSW.equals("9000"))
				throw new EvaluatorException((new GPError("Card", 0, 0, "Parameter sw[] not present and card SW<>0x9000---SW=" + strSW)).toString());
		} else if (sw == 0xffff) {

		} else {
			// exception
			int sw1 = sw / 256;
			int sw2 = sw - sw1 * 256;
			byte[] sw12 = { (byte) sw1, (byte) sw2 };
			String strSW12 = new String(Hex.encode(sw12)); // expect sw
			if (!strSW12.equals(strSW))
				throw new EvaluatorException((new GPError("GPSecurityDomain", 0, 0, "Parameter sw[]<>card SW")).toString());
		}
	}

	/**
	 * and throw exception according to the expected sw and the result sw this function is used by sendApdu
	 * 
	 * @param cx
	 * @param className
	 *            the class which send Apdu
	 * @param strResp
	 *            the response data of the command
	 * @param sw
	 *            SW1SW2
	 */
	public static void SWException(Context cx, String className, String strResp, int sw) {
		String strSW;
		strSW = strResp.substring(strResp.length() - 2 * 2); // card sw
		if (sw == 0x9000) {
			if (!strSW.equals("9000"))
				throw new EvaluatorException((new GPError(className, 0, 0, "Parameter sw[] not present and card SW<>0x9000---SW=" + strSW)).toString());
		} else {
			// exception
			int sw1 = sw / 256;
			int sw2 = sw - sw1 * 256;
			byte[] sw12 = { (byte) sw1, (byte) sw2 };
			String strSW12 = new String(Hex.encode(sw12)); // expect sw
			if (!strSW12.equals(strSW))
				throw new EvaluatorException((new GPError(className, 0, 0, "Parameter sw[]<>card SW")).toString());
		}
	}

	/**
	 * Send data to the card terminal.
	 * 
	 * @param toSendData
	 *            send APDU command
	 * @param len
	 *            the length of APDU command
	 * 
	 * 
	 * @return: the data returned from the card
	 * 
	 */
	public byte[] sendApdu(Context cx, Scriptable scope, byte[] toSendData, int len) {
		byte[] resp = new byte[500];
		resp = apduChannel.sendApdu(toSendData, len);
		String strResp = new String(Hex.encode(resp));

		// exception
		updateSW(cx, scope, strResp);
		return resp;
	}

	public byte[] sendApdu(Context cx, Scriptable scope, int CLA, int INS, int P1, int P2, byte[] data, int LE) {
		byte[] resp = new byte[500];
		resp = apduChannel.sendApdu(CLA, INS, P1, P2, data, LE);

		String strResp = new String(Hex.encode(resp));

		// exception
		updateSW(cx, scope, strResp);
		return resp;
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
	public NativeByteString sendApdu(Context cx, Scriptable scope, Number p1, Number p2, Number p3, Number p4, NativeByteString p5, Number p6, Number p7) {
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
		// send command
		byte[] resp = new byte[500];
		resp = apduChannel.sendApdu(CLA, INS, PP1, PP2, data, LE);
		String strResp = new String(Hex.encode(resp));

		// exception
		updateSW(cx, scope, strResp);
		SWException(strResp, sw);

		// return response
		Integer ee = new Integer(GPConstant.HEX);
		NativeByteString sNew = new NativeByteString(strResp, ee);
		return sNew;
	}

	/*************************************************/
	/* GP function */
	/* throw exception according to the parameter***** */
	/*************************************************/
	/**
	 * sendApdu
	 * 
	 * @param cx
	 * @param scope
	 * @param args
	 * @return
	 */
	private NativeByteString jsFunction_sendApdu(Context cx, Scriptable scope, Object[] args) {
		if (!((args[0] instanceof Number) && (args[1] instanceof Number) && (args[2] instanceof Number) && (args[3] instanceof Number)))
			throw new EvaluatorException((new GPError("Card", GPError.INVALID_TYPE)).toString());
		// define the default parameter
		NativeByteString defaultData = null;
		Number defaultLE = new Integer(-1);
		Number defaultSW = new Integer(0x9000);
		if (args.length == 4) {
			// NativeByteString sNew = sendApdu(cx,scope,(Number)args[0],(Number)args[1],(Number)args[2],(Number)args[3]);
			NativeByteString sNew = sendApdu(cx, scope, (Number) args[0], (Number) args[1], (Number) args[2], (Number) args[3], defaultData, defaultLE, defaultSW);
			return NativeByteString.newByteString(cx, scope, sNew);
		} else if (args.length == 5) {
			if (args[4] instanceof NativeByteString) {
				// NativeByteString sNew = sendApdu(cx,scope,(Number)args[0],(Number)args[1],(Number)args[2],(Number)args[3],(NativeByteString)args[4]);
				NativeByteString sNew = sendApdu(cx, scope, (Number) args[0], (Number) args[1], (Number) args[2], (Number) args[3], (NativeByteString) args[4], defaultLE, defaultSW);
				return NativeByteString.newByteString(cx, scope, sNew);
			} else if (args[4] instanceof Number) {
				NativeByteString sNew = sendApdu(cx, scope, (Number) args[0], (Number) args[1], (Number) args[2], (Number) args[3], null, (Number) args[4], defaultSW);
				return NativeByteString.newByteString(cx, scope, sNew);
			} else
				throw new EvaluatorException((new GPError("Card", GPError.INVALID_TYPE)).toString());
		} else if (args.length == 6) {
			if (!((args[4] instanceof NativeByteString) && (args[5] instanceof Number)))
				throw new EvaluatorException((new GPError("Card", GPError.INVALID_TYPE)).toString());
			else {
				// NativeByteString sNew = sendApdu(cx,scope,(Number)args[0],(Number)args[1],(Number)args[2],(Number)args[3],(NativeByteString)args[4],(Number)args[5]);
				NativeByteString sNew = sendApdu(cx, scope, (Number) args[0], (Number) args[1], (Number) args[2], (Number) args[3], (NativeByteString) args[4], (Number) args[5], defaultSW);
				return NativeByteString.newByteString(cx, scope, sNew);
			}
		} else if (args.length == 7) {
			if (!((args[5] instanceof Number) && (args[6] instanceof Number))) {
				throw new EvaluatorException((new GPError("Card", GPError.INVALID_TYPE)).toString());
			} else {
				if (args[4] instanceof Number) {
					NativeByteString sNew = sendApdu(cx, scope, (Number) args[0], (Number) args[1], (Number) args[2], (Number) args[3], null, (Number) args[4], (Number) args[6]);
					return NativeByteString.newByteString(cx, scope, sNew);

				} else if (args[4] instanceof NativeByteString) {
					NativeByteString sNew = sendApdu(cx, scope, (Number) args[0], (Number) args[1], (Number) args[2], (Number) args[3], (NativeByteString) args[4], (Number) args[5], (Number) args[6]);
					return NativeByteString.newByteString(cx, scope, sNew);
				} else {
					throw new EvaluatorException((new GPError("Card", GPError.INVALID_TYPE)).toString());
				}
			}

		} else
			throw new EvaluatorException((new GPError("Card", 0, 0, "Parameters number is wrong")).toString());

	}

	/**
	 * reset
	 * 
	 * @param args
	 * @return a Atr Object
	 */
	private NativeAtr js_reset(Object[] args) {
		if (!(args[0] instanceof Number))
			throw new EvaluatorException((new GPError("Card", GPError.INVALID_TYPE)).toString());
		else {
			byte[] ATR = new byte[100];
			ATR = apduChannel.reset();
			NativeAtr objAtr = new NativeAtr();
			if ((ATR[ATR.length - 2] == (byte) 0xFF) && (ATR[ATR.length - 1] == (byte) 0xFF))
				throw new EvaluatorException((new GPError("Card", 0, 0, "can't connect/reset smart card")).toString());
			else {
				if (ATR[0] != (byte) 0x3b)
					throw new EvaluatorException((new GPError("Card", 0, 0, "TS!=3B")).toString());
				else {
					objAtr.formatByte = ATR[1];

					int historyNum = (int) (ATR[1] & (byte) 0x0F);
					objAtr.historcalBytes = new byte[historyNum];
					System.arraycopy(ATR, ATR.length - historyNum, objAtr.historcalBytes, 0, historyNum);

					int interfaceNum = ATR.length - historyNum - 1 - 1;
					objAtr.interfaceBytes = new byte[interfaceNum];
					System.arraycopy(ATR, 2, objAtr.interfaceBytes, 0, interfaceNum);
					// compur tckByte;
					objAtr.tckByte = (byte) 0x00;
					for (int i = 0; i < ATR.length; i++)
						objAtr.tckByte = (byte) (objAtr.tckByte ^ ATR[i]);
					return objAtr;
				}
			}
		}
	}

	// **************************************************/
	// thisObj
	private NativeCard realThis(Scriptable thisObj, IdFunctionObject f) {
		if (!(thisObj instanceof NativeCard))
			throw incompatibleCallError(f);
		return (NativeCard) thisObj;
	}

	protected int maxInstanceId() {
		return MAX_INSTANCE_ID;
	}

	protected void initPrototypeId(int id) {
		if (id <= LAST_METHOD_ID) {
			String name;
			int arity;
			switch (id) {
			// function
			case Id_sendApdu:
				arity = 7;
				name = "sendApdu";
				break;
			case Id_reset:
				arity = 0;
				name = "reset";
				break;
			default:
				throw new IllegalStateException(String.valueOf(id));
			}
			initPrototypeMethod(CARD_TAG, id, name, arity);
		}
		/*
		 * else { String name; int x; //constant switch (id) { case Id_T0: x = T0; name = "T0"; break; case Id_T1: x = T1; name = "T1"; break; case Id_T14: x = T14; name = "T14"; break; case Id_OTHER: x = OTHER; name = "OTHER"; break; case Id_RESET_WARM: x = RESET_WARM; name = "RESET_WARM"; break; case Id_RESET_COLD: x = RESET_COLD; name = "RESET_COLD"; break; default: throw new IllegalStateException(String.valueOf(id)); }
		 * 
		 * initPrototypeValue(id, name, ScriptRuntime.wrapNumber(x), DONTENUM | READONLY | PERMANENT); }
		 */
	}

	protected int findPrototypeId(String s) {
		int id = 0;
		String X = null;
		/*
		 * if(s.equals("T0")) {X="T0";id=Id_T0;} if(s.equals("T1")) {X="T1";id=Id_T1;} if(s.equals("SW")) {X="SW";id=Id_SW;} if(s.equals("SW1")) {X="SW1";id=Id_SW1;} if(s.equals("SW2")) {X="SW2";id=Id_SW2;} if(s.equals("T14")) {X="T14";id=Id_T14;} if(s.equals("OTHER")) {X="OTHER";id=Id_OTHER;} if(s.equals("profile")) {X="profile";id=Id_profile;} if(s.equals("RESET_WARM")) {X="RESET_WARM";id=Id_RESET_WARM;} if(s.equals("RESET_COLD")) {X="RESET_COLD";id=Id_RESET_COLD;} if(s.equals("response")) {X="response";id=Id_response;}
		 */
		if (s.equals("reset")) {
			X = "reset";
			id = Id_reset;
		}
		if (s.equals("sendApdu")) {
			X = "sendApdu";
			id = Id_sendApdu;
		}

		return id;
	}

	public void setApduChannel(ApduChannel apduChannel) {
		this.apduChannel = apduChannel;
	}

	/*
	 * public ApduChannel getApduChannel() { return apduChannel; }
	 */
	// define value for all function and constant
	private static final int Id_sendApdu = 1, Id_reset = 2,

	LAST_METHOD_ID = 2, MAX_PROTOTYPE_ID = 2,
	// the following should be contants
			Id_T0 = LAST_METHOD_ID + 1, Id_T1 = LAST_METHOD_ID + 2, Id_T14 = LAST_METHOD_ID + 3, Id_OTHER = LAST_METHOD_ID + 4, Id_RESET_WARM = LAST_METHOD_ID + 5, Id_RESET_COLD = LAST_METHOD_ID + 6,
			// property
			Id_SW = LAST_METHOD_ID + 7, Id_SW1 = LAST_METHOD_ID + 8, Id_SW2 = LAST_METHOD_ID + 9, Id_response = LAST_METHOD_ID + 10, Id_profile = LAST_METHOD_ID + 11,

			MAX_INSTANCE_ID = LAST_METHOD_ID + 11;

	public static final int T0 = 1, T1 = 2, T14 = 3, OTHER = 4, RESET_WARM = 5, RESET_COLD = 6;

	public NativeByteString response;
	public CardProfile profile;
	public Number SW;
	public NativeByteString SW1;
	public NativeByteString SW2;

	private ApduChannel apduChannel;

}
