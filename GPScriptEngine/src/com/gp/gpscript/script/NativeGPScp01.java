package com.gp.gpscript.script;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.Scriptable;

import com.gp.gpscript.utils.Hex;

/**
 * 
 * <p>
 * Title: GPScp01
 * </p>
 * <p>
 * Description: This class implements the GPScp01 (a built-in object). See [GP_SYS_SCR] 7.1.8
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
public class NativeGPScp01 extends IdScriptableObject {
	private Logger log = Logger.getLogger(NativeGPScp01.class);
	private static final Object SCP01_TAG = new Object();
	private static final NativeGPScp01 prototypeGPScp01 = new NativeGPScp01();

	public static void init(Context cx, Scriptable scope, boolean sealed) {
		prototypeGPScp01.activatePrototypeMap(MAX_PROTOTYPE_ID);
		prototypeGPScp01.setPrototype(getObjectPrototype(scope));
		prototypeGPScp01.setParentScope(scope);
		if (sealed) {
			prototypeGPScp01.sealObject();
		}

	}

	public NativeGPScp01() {
	}

	public NativeGPScp01(Context cx, Scriptable scope, NativeCard card) {
		scope = getTopLevelScope(scope);
		kekKey = new NativeKey();
		kekKey.setObjectPrototype();
		encKey = new NativeKey();
		encKey.setObjectPrototype();
		macKey = new NativeKey();
		macKey.setObjectPrototype();

		crypto = new NativeCrypto();
		crypto.setObjectPrototype();

		this.card = card;
	}

	public String getClassName() {
		return "GPScp01";
	}

	public void setObjectPrototype() {
		setPrototype(prototypeGPScp01);
		// setParentScope(prototypeGPScp01.getParentScope());
	}

	protected int findInstanceIdInfo(String s) {
		if (s.equals("state")) {
			return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_state);
		}
		if (s.equals("securityLevel")) {
			return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_securityLevel);
		}
		if (s.equals("mac")) {
			return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_mac);
		}
		if (s.equals("keyVersion")) {
			return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_keyVersion);
		}
		if (s.equals("keyIndex")) {
			return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_keyIndex);
		}
		if (s.equals("hostChallenge")) {
			return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_hostChallenge);
		}
		if (s.equals("diversificationData")) {
			return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_diversificationData);
		}
		if (s.equals("cardCryptogram")) {
			return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_cardCryptogram);
		}
		if (s.equals("cardChallenge")) {
			return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_cardChallenge);
		}
		if (s.equals("crypto")) {
			return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_crypto);
		}
		/*
		 * if (s.equals("card")) { return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_card); } if (s.equals("data")) { return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_data); } if (s.equals("key")) { return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_key); } if (s.equals("profile")) { return instanceIdInfo(DONTENUM | READONLY | PERMANENT, Id_profile); }
		 */
		return super.findInstanceIdInfo(s);
	}

	protected String getInstanceIdName(int id) {
		// if (id == Id_card ) { return "card"; }
		// if (id == Id_data ) { return "data"; }
		// if (id == Id_key ) { return "key"; }
		// if (id == Id_profile ) { return "profile"; }
		if (id == Id_crypto) {
			return "crypto";
		}
		if (id == Id_cardChallenge) {
			return "cardChallenge";
		}
		if (id == Id_cardCryptogram) {
			return "cardCryptogram";
		}
		if (id == Id_diversificationData) {
			return "diversificationData";
		}
		if (id == Id_hostChallenge) {
			return "hostChallenge";
		}
		if (id == Id_keyIndex) {
			return "keyIndex";
		}
		if (id == Id_keyVersion) {
			return "keyVersion";
		}
		if (id == Id_mac) {
			return "mac";
		}
		if (id == Id_securityLevel) {
			return "securityLevel";
		}
		if (id == Id_state) {
			return "state";
		}

		return super.getInstanceIdName(id);
	}

	protected Object getInstanceIdValue(int id) {
		// if(id==Id_card) return card;
		// if(id==Id_data) return data;
		// if(id==Id_key) return key;
		// if(id==Id_profile) return profile;
		if (id == Id_crypto)
			return crypto;
		if (id == Id_cardChallenge)
			return cardChallenge;
		if (id == Id_cardCryptogram)
			return cardCryptogram;
		if (id == Id_diversificationData)
			return diversificationData;
		if (id == Id_hostChallenge)
			return hostChallenge;
		if (id == Id_keyIndex)
			return keyIndex;
		if (id == Id_keyVersion)
			return keyVersion;
		if (id == Id_mac)
			return mac;
		if (id == Id_securityLevel)
			return securityLevel;
		if (id == Id_state)
			return state;
		return super.getInstanceIdValue(id);
	}

	public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
		if (!f.hasTag(SCP01_TAG)) {
			return super.execIdCall(f, cx, scope, thisObj, args);
		}

		int id = f.methodId();
		switch (id) {
		case Id_initializeUpdate:
			return realThis(thisObj, f).jsFunction_initializeUpdate(cx, scope, args);
		case Id_externalAuthenticate:
			realThis(thisObj, f).jsFunction_externalAuthenticate(cx, scope, args);
			return "";
		case Id_decryptEncryptKek:
			return realThis(thisObj, f).jsFunction_decryptEncryptKek(cx, scope, args);
		case Id_encryptKek:
			return realThis(thisObj, f).jsFunction_encryptKek(cx, scope, args);
		case Id_getKekKey:
			return realThis(thisObj, f).jsFunction_getKekKey(cx, scope, args);
		case Id_setKekKey:
			realThis(thisObj, f).jsFunction_setKekKey(cx, scope, args);
			return null;
		case Id_setEncKey:
			realThis(thisObj, f).jsFunction_setEncKey(cx, scope, args);
			return null;
		case Id_setMacKey:
			realThis(thisObj, f).jsFunction_setMacKey(cx, scope, args);
			return null;
		case Id_unwrapWrapKek:
			realThis(thisObj, f).jsFunction_unwrapWrapKek(cx, scope, args);
			return null;
		}
		throw new IllegalArgumentException(String.valueOf(id));
	}

	// thisObj
	private NativeGPScp01 realThis(Scriptable thisObj, IdFunctionObject f) {
		if (!(thisObj instanceof NativeGPScp01))
			throw incompatibleCallError(f);
		return (NativeGPScp01) thisObj;
	}

	/*********************************************/
	/* all function */
	/*********************************************/

	/**
	 * send Initialize Update APDU and update the property of the secureChannel ,Card, and the dataBase
	 * 
	 * @param cx
	 * @param scope
	 * @param p1
	 *            Key version
	 * @param p2
	 *            key index
	 * @return the response of apdu
	 */
	private NativeByteString initializeUpdate(Context cx, Scriptable scope, Number p1, Number p2) {
		Integer ee = new Integer(GPConstant.HEX);
		byte[] hostRandom = new byte[8];
		hostRandom = Hex.decode(NativeCrypto.generateRandom(new Integer(8)).toString());
		/*
		 * try { hostRandom = Crypto.generaterandom(8); } catch(CryptoException e) { e.printStackTrace();
		 * 
		 * }
		 */
		// send Initialize Update command
		byte[] resp = new byte[500];
		resp = this.card.sendApdu(cx, scope, 0x80, 0x50, p1.intValue(), 0x00, hostRandom, -1);
		String strResp = new String(Hex.encode(resp));
		// String strResp="0102030405060708090A111221222324252627283132333435363738";
		initRes = strResp; // save the response for external-authenticate
		this.card.SWException(cx, "GPScp01", strResp, 0x9000);
		// update the property
		cardChallenge = NativeByteString.newByteString(cx, scope, new NativeByteString(strResp.substring(24, 40), ee));
		cardCryptogram = NativeByteString.newByteString(cx, scope, new NativeByteString(strResp.substring(40, 56), ee));
		diversificationData = NativeByteString.newByteString(cx, scope, new NativeByteString(strResp.substring(0, 20), ee));
		String strHostChallenge = new String(Hex.encode(hostRandom));
		hostChallenge = NativeByteString.newByteString(cx, scope, new NativeByteString(strHostChallenge, ee));
		keyIndex = p1;
		keyVersion = p2;
		state = new Integer(GPConstant.SC_INITIALIZE);
		// update the card property
		this.card.response = NativeByteString.newByteString(cx, scope, new NativeByteString(strResp, new Integer(GPConstant.HEX)));
		// return
		NativeByteString sNew = new NativeByteString(strResp, ee);
		return sNew;
	}

	private void externalAuthenticate(Context cx, Scriptable scope, Number p1) {
		String data;
		String Rcard = cardChallenge.toString(); // random of card
		String Rter = hostChallenge.toString(); // random of terminal
		String Host = "";
		String Smac = "";
		byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0 };

		Integer ee = new Integer(GPConstant.HEX);
		Integer mech = new Integer(NativeCrypto.DES_ECB);

		// host
		data = Rcard + Rter;
		// Host=(NativeCrypto.sign(Sec_ENC,new Integer(NativeCrypto.DES_MAC),new NativeByteString(data,ee))).toString();
		Host = (NativeCrypto.sign(encKey, new Integer(NativeCrypto.DES_MAC), new NativeByteString(data, ee), new NativeByteString("0000000000000000", ee))).toString();
		// Smac
		byte[] level = new byte[1];
		level[0] = (byte) (p1.intValue());
		String strLevel = new String(Hex.encode(level));
		data = "8482" + strLevel + "0010" + Host;
		// Smac=(NativeCrypto.sign(Sec_MAC,new Integer(NativeCrypto.DES_MAC),new NativeByteString(data,ee))).toString();
		Smac = (NativeCrypto.sign(macKey, new Integer(NativeCrypto.DES_MAC_EMV), new NativeByteString(data, ee), new NativeByteString("0000000000000000", ee))).toString();
		// save the current mac value for the next command
		// PcscWrap.SMAC=Smac;
		mac = new NativeByteString(Smac, new Integer(GPConstant.HEX));

		// send command to card
		byte[] resp = new byte[500];
		resp = this.card.sendApdu(cx, scope, 0x84, 0x82, p1.intValue(), 0x00, Hex.decode(Host + Smac), -1);
		String strResp = new String(Hex.encode(resp));
		this.card.SWException(cx, "GPScp01", strResp, 0x9000);
		state = new Integer(GPConstant.SC_OPEN);
		securityLevel = p1;
	}

	private NativeByteString decryptEncryptKek(Context cx, Scriptable scope, NativeKey p1, Number p2, NativeByteString p3, NativeByteString p4) {
		NativeByteString bdecryptKey = p1.getBlob();
		byte[] decryptKey = new byte[bdecryptKey.GetLength()];
		for (int i = 0; i < bdecryptKey.GetLength(); i++)
			decryptKey[i] = bdecryptKey.ByteAt(i);

		int decryptMech = (int) p2.intValue();

		byte[] dataToDecrypt = new byte[p3.GetLength()];
		for (int i = 0; i < p3.GetLength(); i++)
			dataToDecrypt[i] = p3.ByteAt(i);

		byte[] decryptIv = new byte[p4.GetLength()];
		for (int i = 0; i < p4.GetLength(); i++)
			decryptIv[i] = p4.ByteAt(i);

		// decrypt
		byte[] dataToEncrypt = null;
		dataToEncrypt = Hex.decode((NativeCrypto.decrypt(p1, p2, p3, p4)).toString());

		// encrypt use KEK
		// NativeKey KekKey=(NativeKey)key.get("Sec_KEK",scope);//new NativeKey("Sec_KEK");//("KDCkek");
		NativeByteString bwrapKey = kekKey.getBlob();
		byte[] wrapKey = new byte[bwrapKey.GetLength()];
		for (int i = 0; i < bwrapKey.GetLength(); i++)
			wrapKey[i] = bwrapKey.ByteAt(i);
		// mech is DES_ECB when scp01
		int encryptMech = NativeCrypto.DES_ECB;
		byte[] encryptIV = new byte[8];
		for (int i = 0; i < 8; i++)
			encryptIV[i] = (byte) 0;

		byte[] out = null;

		out = Hex.decode((NativeCrypto.encrypt(kekKey, new Integer(encryptMech), new NativeByteString(dataToEncrypt), new NativeByteString(encryptIV))).toString());
		// out=Crypto.encrypt(wrapKey,encryptMech,dataToEncrypt,encryptIV);

		// return
		String str = new String(Hex.encode(out));
		Integer ee = new Integer(GPConstant.HEX);
		NativeByteString sNew = new NativeByteString(str, ee);
		return sNew;
	}

	public NativeByteString encryptKek(Context cx, Scriptable scope, NativeByteString p1) {
		// wrapkey is KEK
		// NativeKey KekKey=(NativeKey)key.get("Sec_KEK",scope);//new NativeKey("Sec_KEK");//("KDCkek");
		NativeByteString bwrapKey = kekKey.getBlob();
		byte[] wrapKey = new byte[bwrapKey.GetLength()];
		for (int i = 0; i < bwrapKey.GetLength(); i++)
			wrapKey[i] = bwrapKey.ByteAt(i);

		// mech is DES_ECB when scp01
		int mech = NativeCrypto.DES_ECB;

		byte[] dataToEncrypt = new byte[p1.GetLength()];
		for (int i = 0; i < p1.GetLength(); i++)
			dataToEncrypt[i] = p1.ByteAt(i);

		byte[] iv = new byte[8];
		for (int i = 0; i < 8; i++)
			iv[i] = (byte) 0;

		byte[] out = null;
		out = Hex.decode((NativeCrypto.encrypt(kekKey, new Integer(mech), new NativeByteString(dataToEncrypt), new NativeByteString(iv))).toString());
		/*
		 * try {
		 * 
		 * out=Crypto.encrypt(wrapKey,mech,dataToEncrypt,iv); } catch (CryptoException e) { System.out.println(e.getMessage()); }
		 */
		// return
		String str = new String(Hex.encode(out));
		Integer ee = new Integer(GPConstant.HEX);
		NativeByteString sNew = new NativeByteString(str, ee);
		return sNew;
	}

	/**
	 * 加密密钥采用Lmk保护的密钥
	 * 
	 * @param @param cx
	 * @param @param scope
	 * @param @param p1
	 * @param @return
	 * @return NativeByteString
	 * @throws Exception
	 */
	public NativeByteString encryptKekLmk(Context cx, Scriptable scope, NativeByteString p1) {
		// wrapkey is KEK
		// NativeKey KekKey=(NativeKey)key.get("Sec_KEK",scope);//new NativeKey("Sec_KEK");//("KDCkek");
		NativeByteString bwrapKey = kekKey.getBlob();
		log.debug("encryted KSCkek（encrypt CDK:）" + kekKey.getBlob());
		log.debug("LMK CDK:" + p1);
		byte[] wrapKey = new byte[bwrapKey.GetLength()];
		for (int i = 0; i < bwrapKey.GetLength(); i++)
			wrapKey[i] = bwrapKey.ByteAt(i);

		// mech is DES_ECB when scp01
		int mech = 0;

		byte[] dataToEncrypt = new byte[p1.GetLength()];
		for (int i = 0; i < p1.GetLength(); i++)
			dataToEncrypt[i] = p1.ByteAt(i);

		byte[] iv = new byte[8];
		for (int i = 0; i < 8; i++)
			iv[i] = (byte) 0;

		String out = "";
		// out = Hex.decode(NativeCrypto.encrypt(kekKey, new Integer(mech), new NativeByteString(dataToEncrypt), new NativeByteString(iv)).toString());
		out = NativeCrypto.wrapToLmk(mech, NativeCrypto.ZMK, NativeCrypto.LMK_DOUBLE_LENGTH_KEY, new NativeByteString(wrapKey), NativeCrypto.ZMK, NativeCrypto.LMK_DOUBLE_LENGTH_KEY, new NativeByteString(dataToEncrypt), new NativeByteString(iv)).toString();
		// return
		log.debug("out:" + out);
		String str = out;
		Integer ee = new Integer(GPConstant.HEX);
		NativeByteString sNew = new NativeByteString(str, ee);
		return sNew;
	}

	/*************************************************/
	/* GP function */
	/* throw exception according to the parameter***** */
	/*************************************************/

	private NativeByteString jsFunction_initializeUpdate(Context cx, Scriptable scope, Object[] args) {
		if (!(args[0] instanceof Number))
			throw new EvaluatorException((new GPError("GPScp01", GPError.INVALID_TYPE)).toString());
		if (!(args[1] instanceof Number))
			throw new EvaluatorException((new GPError("GPScp01", GPError.INVALID_TYPE)).toString());
		NativeByteString sNew = initializeUpdate(cx, scope, (Number) args[0], (Number) args[1]);
		return NativeByteString.newByteString(cx, scope, sNew);
	}

	private void jsFunction_externalAuthenticate(Context cx, Scriptable scope, Object[] args) {
		if (!(args[0] instanceof Number))
			throw new EvaluatorException((new GPError("GPScp01", GPError.INVALID_TYPE)).toString());

		externalAuthenticate(cx, scope, (Number) args[0]);
	}

	private NativeByteString jsFunction_decryptEncryptKek(Context cx, Scriptable scope, Object[] args) {
		if (args.length > 4)
			throw new EvaluatorException((new GPError("GPScp01", GPError.INVALID_TYPE)).toString());
		else if (args.length == 3) {
			Integer ee = new Integer(GPConstant.HEX);
			NativeByteString p4 = new NativeByteString("0000000000000000", ee);
			NativeByteString sNew = decryptEncryptKek(cx, scope, (NativeKey) args[0], (Number) args[1], (NativeByteString) args[2], p4);
			return NativeByteString.newByteString(cx, scope, sNew);
		} else if (args.length == 4) {
			NativeByteString sNew = decryptEncryptKek(cx, scope, (NativeKey) args[0], (Number) args[1], (NativeByteString) args[2], (NativeByteString) args[3]);
			return NativeByteString.newByteString(cx, scope, sNew);
		} else
			throw new EvaluatorException((new GPError("GPScp01", GPError.INVALID_TYPE)).toString());
	}

	private NativeByteString jsFunction_encryptKek(Context cx, Scriptable scope, Object[] args) {
		if (args.length != 1)
			throw new EvaluatorException((new GPError("GPScp01", GPError.INVALID_TYPE)).toString());
		else {
			if (args[0] instanceof NativeByteString) {
				NativeByteString sNew = encryptKek(cx, scope, (NativeByteString) args[0]);
				return NativeByteString.newByteString(cx, scope, sNew);
			} else
				throw new EvaluatorException((new GPError("GPScp01", GPError.INVALID_TYPE)).toString());
		}

	}

	// ////////////
	private NativeKey jsFunction_getKekKey(Context cx, Scriptable scope, Object[] args) {
		return kekKey;
	}

	public NativeKey getEncKey(Context cx, Scriptable scope) {
		return encKey;
	}

	public NativeKey getMacKey(Context cx, Scriptable scope) {
		return macKey;
	}

	public String getSmac(Context cx, Scriptable scope) {
		return mac.toString();
	}

	public void setSmac(Context cx, Scriptable scope, String mac) {
		this.mac = NativeByteString.newByteString(cx, scope, new NativeByteString(mac, new Integer(GPConstant.HEX)));
	}

	// ////////////
	private void jsFunction_setKekKey(Context cx, Scriptable scope, Object[] args) {
		if (args[0] instanceof NativeKey) {
			NativeKey myKey = (NativeKey) args[0];
			kekKey = myKey;
		} else
			throw new EvaluatorException((new GPError("GPScp01", GPError.INVALID_TYPE)).toString());
	}

	private void jsFunction_setEncKey(Context cx, Scriptable scope, Object[] args) {
		if (args[0] instanceof NativeKey) {
			NativeKey myKey = (NativeKey) args[0];
			encKey.strBlob = myKey.getBlob().toString();
		} else
			throw new EvaluatorException((new GPError("GPScp01", GPError.INVALID_TYPE)).toString());
	}

	private void jsFunction_setMacKey(Context cx, Scriptable scope, Object[] args) {
		if (args[0] instanceof NativeKey) {
			NativeKey myKey = (NativeKey) args[0];
			macKey.strBlob = myKey.getBlob().toString();
		} else
			throw new EvaluatorException((new GPError("GPScp01", GPError.INVALID_TYPE)).toString());
	}

	private void jsFunction_unwrapWrapKek(Context cx, Scriptable scope, Object[] args) {
		int unwrapMech = (int) ((Number) args[0]).intValue();
		int wrapMech = NativeCrypto.DES_ECB;

		NativeByteString bKeyToUnwrap = ((NativeKey) args[1]).getBlob();
		byte[] keyToUnwrap = new byte[bKeyToUnwrap.GetLength()];
		for (int i = 0; i < bKeyToUnwrap.GetLength(); i++)
			keyToUnwrap[i] = bKeyToUnwrap.ByteAt(i);

		// wrapkey is KEK
		NativeByteString bwrapKey = kekKey.getBlob();
		byte[] wrapKey = new byte[bwrapKey.GetLength()];
		for (int i = 0; i < bwrapKey.GetLength(); i++)
			wrapKey[i] = bwrapKey.ByteAt(i);

		byte[] unwrapIV = new byte[8];
		for (int i = 0; i < 8; i++)
			unwrapIV[i] = (byte) 0x00;
		byte[] wrapIV = new byte[8];
		for (int i = 0; i < 8; i++)
			wrapIV[i] = (byte) 0x00;

		// get the unwrapKey
		NativeKey unwrapKey1 = new NativeKey("");
		((NativeKey) args[1]).getWrapKey(unwrapKey1);
		NativeByteString bunwrapKey = unwrapKey1.getBlob();
		byte[] unwrapKey = new byte[bunwrapKey.GetLength()];
		for (int i = 0; i < bunwrapKey.GetLength(); i++)
			unwrapKey[i] = bunwrapKey.ByteAt(i);

		// decrypt
		byte[] out = null;
		out = Hex.decode((NativeCrypto.decrypt(unwrapKey1, new Integer(unwrapMech), new NativeByteString(keyToUnwrap), new NativeByteString(unwrapIV))).toString());
		/*
		 * try {
		 * 
		 * out=Crypto.decrypt(unwrapKey,unwrapMech,keyToUnwrap,unwrapIV); } catch (CryptoException e) { throw new EvaluatorException ( ( new GPError("GPScp01", 0, 0, e.getMessage())).toString()); }
		 */
		// encrypt
		byte[] out1 = null;
		out1 = Hex.decode(NativeCrypto.encrypt(kekKey, new Integer(wrapMech), new NativeByteString(out), new NativeByteString(wrapIV)).toString());
		/*
		 * try {
		 * 
		 * out1=Crypto.encrypt(wrapKey,wrapMech,out,wrapIV); } catch (CryptoException e) { throw new EvaluatorException ( ( new GPError("GPScp01", 0, 0, e.getMessage())).toString()); }
		 */
		// update database
		String strKeyResult = new String(Hex.encode(out1));
		((NativeKey) args[2]).strBlob = strKeyResult;
		// GP_Global.setKey(((NativeKey)args[2]).strIndex,strKeyResult);
	}

	// **************************************************/

	protected int maxInstanceId() {
		return MAX_INSTANCE_ID;
	}

	protected void initPrototypeId(int id) {
		String s;
		int arity;
		switch (id) {
		case Id_decryptEncryptKek:
			arity = 4;
			s = "decryptEncryptKek";
			break;
		case Id_encryptKek:
			arity = 1;
			s = "encryptKek";
			break;
		case Id_externalAuthenticate:
			arity = 1;
			s = "externalAuthenticate";
			break;
		case Id_getKekKey:
			arity = 1;
			s = "getKekKey";
			break;
		case Id_initializeUpdate:
			arity = 2;
			s = "initializeUpdate";
			break;
		case Id_setEncKey:
			arity = 1;
			s = "setEncKey";
			break;
		case Id_setKekKey:
			arity = 1;
			s = "setKekKey";
			break;
		case Id_setMacKey:
			arity = 1;
			s = "setMacKey";
			break;
		case Id_unwrapWrapKek:
			arity = 2;
			s = "Id_unwrapWrapKek";
			break;

		default:
			throw new IllegalArgumentException(String.valueOf(id));
		}
		initPrototypeMethod(SCP01_TAG, id, s, arity);
	}

	protected int findPrototypeId(String s) {
		int id = 0;
		String X = null;
		if (s.equals("getKekKey")) {
			X = "getKekKey";
			id = Id_getKekKey;
		}
		if (s.equals("setKekKey")) {
			X = "setKekKey";
			id = Id_setKekKey;
		}
		if (s.equals("setEncKey")) {
			X = "setEncKey";
			id = Id_setEncKey;
		}
		if (s.equals("setMacKey")) {
			X = "setMacKey";
			id = Id_setMacKey;
		}
		if (s.equals("encryptKek")) {
			X = "encryptKek";
			id = Id_encryptKek;
		}
		if (s.equals("unwrapWrapKek")) {
			X = "unwrapWrapKek";
			id = Id_unwrapWrapKek;
		}
		if (s.equals("initializeUpdate")) {
			X = "initializeUpdate";
			id = Id_initializeUpdate;
		}
		if (s.equals("decryptEncryptKek")) {
			X = "decryptEncryptKek";
			id = Id_decryptEncryptKek;
		}
		if (s.equals("externalAuthenticate")) {
			X = "externalAuthenticate";
			id = Id_externalAuthenticate;
		}

		return id;
	}

	// define value for all function and constant
	private static final int Id_decryptEncryptKek = 1, Id_encryptKek = 2, Id_externalAuthenticate = 3, Id_getKekKey = 4, Id_initializeUpdate = 5, Id_setEncKey = 6, Id_setKekKey = 7, Id_setMacKey = 8, Id_unwrapWrapKek = 9,

	LAST_METHOD_ID = 9, MAX_PROTOTYPE_ID = 9;

	// the following should be contants
	private static final int Id_cardChallenge = 1, Id_cardCryptogram = 2, Id_crypto = 3, Id_diversificationData = 4, Id_hostChallenge = 5, Id_keyIndex = 6, Id_keyVersion = 7, Id_mac = 8, Id_securityLevel = 9, Id_state = 10, MAX_INSTANCE_ID = 10;

	// property
	private NativeCrypto crypto;
	private NativeCard card;

	private NativeKey kekKey;
	private NativeKey macKey;
	private NativeKey encKey;

	private NativeByteString cardChallenge;
	private NativeByteString cardCryptogram;
	private NativeByteString diversificationData;
	private NativeByteString hostChallenge;
	private Number keyIndex;
	private Number keyVersion;
	private NativeByteString mac;
	private Number securityLevel = new Integer(0);
	private Number state = new Integer(GPConstant.SC_CLOSE);

	// self-defined property
	private String initRes = ""; // record the response of initialize update command

}
