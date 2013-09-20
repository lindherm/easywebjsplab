package com.gp.gpscript.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Undefined;

///////////////////////////////////////////////////////////////////////////////
/**
 * 
 * <p>
 * Title: Crypto
 * </p>
 * <p>
 * Description: This class implements the Crypto (a native object). See [GP_SYS_SCR] 7.1.2
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
public class NativeCrypto extends IdScriptableObject {
	private static final Object CRYPTO_TAG = new Object();
	private static final NativeCrypto prototypeCrypto = new NativeCrypto();

	public static void init(Context cx, Scriptable scope, boolean sealed) {
		prototypeCrypto.activatePrototypeMap(MAX_PROTOTYPE_ID);
		prototypeCrypto.setPrototype(getObjectPrototype(scope));
		prototypeCrypto.setParentScope(scope);

		// enable property used local
		final int attr = ScriptableObject.DONTENUM | ScriptableObject.PERMANENT | ScriptableObject.READONLY;
		
		prototypeCrypto.defineProperty("DES_ECB", ScriptRuntime.wrapNumber(DES_ECB), attr);
		prototypeCrypto.defineProperty("DES_CBC", ScriptRuntime.wrapNumber(DES_CBC), attr);
		prototypeCrypto.defineProperty("RSA", ScriptRuntime.wrapNumber(RSA), attr);
		prototypeCrypto.defineProperty("SHA_1", ScriptRuntime.wrapNumber(SHA_1), attr);
		prototypeCrypto.defineProperty("MD5", ScriptRuntime.wrapNumber(MD5), attr);
		prototypeCrypto.defineProperty("DES_KEY_GEN", ScriptRuntime.wrapNumber(DES_KEY_GEN), attr);
		prototypeCrypto.defineProperty("DES2_KEY_GEN", ScriptRuntime.wrapNumber(DES2_KEY_GEN), attr);
		prototypeCrypto.defineProperty("RSA_KEY_PAIR_GEN", ScriptRuntime.wrapNumber(RSA_KEY_PAIR_GEN), attr);
		prototypeCrypto.defineProperty("ISO9797_METHOD_1", ScriptRuntime.wrapNumber(ISO9797_METHOD_1), attr);
		prototypeCrypto.defineProperty("ISO9797_METHOD_2", ScriptRuntime.wrapNumber(ISO9797_METHOD_2), attr);
		prototypeCrypto.defineProperty("EMV_PAD", ScriptRuntime.wrapNumber(EMV_PAD), attr);
		prototypeCrypto.defineProperty("NONE", ScriptRuntime.wrapNumber(NONE), attr);
		prototypeCrypto.defineProperty("DES_MAC", ScriptRuntime.wrapNumber(DES_MAC), attr);
		prototypeCrypto.defineProperty("DES_MAC_EMV", ScriptRuntime.wrapNumber(DES_MAC_EMV), attr);
		prototypeCrypto.defineProperty("DES3_MAC", ScriptRuntime.wrapNumber(DES3_MAC), attr);
		prototypeCrypto.defineProperty("DES3_MAC_EMV", ScriptRuntime.wrapNumber(DES3_MAC), attr);
		prototypeCrypto.defineProperty("DES_CBC_PAD", ScriptRuntime.wrapNumber(DES_CBC_PAD), attr);
		
		prototypeCrypto.defineProperty("LMK_DES_ECB", ScriptRuntime.wrapNumber(LMK_DES_ECB), attr);
		prototypeCrypto.defineProperty("LMK_DES_ECB_LP", ScriptRuntime.wrapNumber(LMK_DES_ECB_LP), attr);
		prototypeCrypto.defineProperty("LMK_DES_ECB_P", ScriptRuntime.wrapNumber(LMK_DES_ECB_P), attr);
		prototypeCrypto.defineProperty("LMK_DES_CBC", ScriptRuntime.wrapNumber(LMK_DES_CBC), attr);
		prototypeCrypto.defineProperty("LMK_DES_CBC_LP", ScriptRuntime.wrapNumber(LMK_DES_CBC_LP), attr);
		prototypeCrypto.defineProperty("LMK_DES_CBC_P", ScriptRuntime.wrapNumber(LMK_DES_CBC_P), attr);
		
		prototypeCrypto.defineProperty("LMK_SINGLE_LENGTH_KEY", ScriptRuntime.wrapNumber(LMK_SINGLE_LENGTH_KEY), attr);
		prototypeCrypto.defineProperty("LMK_DOUBLE_LENGTH_KEY", ScriptRuntime.wrapNumber(LMK_DOUBLE_LENGTH_KEY), attr);
		prototypeCrypto.defineProperty("ZMK", ScriptRuntime.wrapNumber(ZMK), attr);
		
		prototypeCrypto.defineProperty("ENCRYPT", ScriptRuntime.wrapNumber(ENCRYPT), attr);
		prototypeCrypto.defineProperty("DECRYPT", ScriptRuntime.wrapNumber(DECRYPT), attr);
		prototypeCrypto.defineProperty("ABC_DES_ECB", ScriptRuntime.wrapNumber(ABC_DES_ECB), attr);
		prototypeCrypto.defineProperty("ABC_DES_CBC", ScriptRuntime.wrapNumber(ABC_DES_CBC), attr);
		if (sealed) {
			prototypeCrypto.sealObject();
		}
		ScriptableObject.defineProperty(scope, "Crypto", prototypeCrypto, ScriptableObject.DONTENUM);
	}

	public NativeCrypto() {
	}

	public String getClassName() {
		return "Crypto";
	}

	public void setObjectPrototype() {
		setPrototype(prototypeCrypto);
		// setParentScope(prototypeCrypto.getParentScope());
	}

	public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
		if (!f.hasTag(CRYPTO_TAG)) {
			return super.execIdCall(f, cx, scope, thisObj, args);
		}

		int id = f.methodId();
		switch (id) {
		case Id_decrypt:
			return jsFunction_decrypt(cx, scope, args);
		case Id_decryptEncrypt:
			return jsFunction_decryptEncrypt(cx, scope, args);
		case Id_deriveKey:
			jsFunction_deriveKey(cx, scope, args);
			return null;
		case Id_deriveOddKey:
			jsFunction_deriveOddKey(cx, scope, args);
			return null;
		case Id_digest:
			return jsFunction_digest(cx, scope, args);
		case Id_encrypt:
			return jsFunction_encrypt(cx, scope, args);
		case Id_generateKey:
			jsFunction_generateKey(cx, scope, args);
			return null;
		case Id_generateKeyPair:
			jsFunction_generateKeyPair(cx, scope, args);
			return null;
		case Id_generateRandom:
			return jsFunction_generateRandom(cx, scope, args);
		case Id_sign:
			return jsFunction_sign(cx, scope, args);
		case Id_verify:
			return jsFunction_verify(cx, scope, args);
		case Id_wrap:
			jsFunction_wrap(cx, scope, args);
			return null;
		case Id_unwrap:
			jsFunction_unwrap(cx, scope, args);
			return null;
		case Id_unwrapWrap:
			jsFunction_unwrapWrap(cx, scope, args);
			return null;
		case Id_decryptEncryptLmk:
			return jsFunction_decryptEncryptLmk(cx, scope, args);
		case Id_deriveKeyLmk:
			return jsFunction_deriveKeyLmk(cx, scope, args);
		case Id_wrapToLmk:
			return jsFunction_wrapToLmk(cx, scope, args);
		case Id_edk:
			return jsFunction_edk(cx, scope, args);
		case Id_inputKey:
			return jsFunction_inputKey(cx, scope, args);
		case Id_getKey:
			return jsFunction_getKey(cx, scope, args);
		case Id_encryptConnByPan:
			return jsFunction_encryptConnByPan(cx, scope, args);
		case Id_decryptConnByPan:
			return jsFunction_decryptConnByPan(cx, scope, args);
		case Id_deriveKeyConnByPan:
			return jsFunction_deriveKeyConnByPan(cx, scope, args);
		
		case Id_decryptEncryptConnByPan:
			return jsFunction_decryptEncryptConnByPan(cx, scope, args);
		case Id_wrapKeyConnByPan:
			return jsFunction_wrapKeyConnByPan(cx, scope, args);
		case Id_connectByPan:
			return jsFunction_connectByPan(cx, scope, args);
		case Id_disconnByPan:
			return jsFunction_disconnByPan(cx, scope, args);
		
		}
		throw new IllegalArgumentException(String.valueOf(id));
	}

	public static NativeByteString decrypt(NativeKey p1, Number p2, NativeByteString p3, NativeByteString p4) {
		return cryptoEngine.get().decrypt(p1, p2, p3, p4);
	}

	public static NativeByteString decryptEncrypt(NativeKey p1, Number p2, NativeKey p3, Number p4, NativeByteString p5, NativeByteString p6, NativeByteString p7) {
		return cryptoEngine.get().decryptEncrypt(p1, p2, p3, p4, p5, p6, p7);
	}

	public static void deriveKey(NativeKey p1, Number p2, NativeByteString p3, NativeKey p4) {
		cryptoEngine.get().deriveKey(p1, p2, p3, p4);

	}

	public static void deriveOddKey(NativeKey p1, Number p2, NativeByteString p3, NativeKey p4) {
		cryptoEngine.get().deriveOddKey(p1, p2, p3, p4);

	}

	public static NativeByteString digest(Number p1, NativeByteString p2) {
		return cryptoEngine.get().digest(p1, p2);

	}

	public static NativeByteString encrypt(NativeKey p1, Number p2, NativeByteString p3, NativeByteString p4) {
		return cryptoEngine.get().encrypt(p1, p2, p3, p4);

	}

	public static void generateKey(Number p1, NativeKey p2) {
		cryptoEngine.get().generateKey(p1, p2);

	}

	public static void generateKeyPair(Number p1, NativeKey p2, NativeKey p3) {
		cryptoEngine.get().generateKeyPair(p1, p2, p3);

	}

	public static NativeByteString generateRandom(Number p1) {
		return cryptoEngine.get().generateRandom(p1);

	}

	public static NativeByteString sign(NativeKey p1, Number p2, NativeByteString p3, NativeByteString p4) {
		return cryptoEngine.get().sign(p1, p2, p3, p4);

	}

	public static boolean verify(NativeKey p1, Number p2, NativeByteString p3, NativeByteString p4) {
		return cryptoEngine.get().verify(p1, p2, p3, p4);

	}

	public static void wrap(NativeKey p1, Number p2, NativeKey p3, NativeKey p4, NativeByteString p5) {
		p4.setWrapKey(p1);
		cryptoEngine.get().wrap(p1, p2, p3, p4, p5);

	}

	public static void unwrap(Number p1, NativeKey p2, NativeKey p3, NativeByteString p4) {
		cryptoEngine.get().unwrap(p1, p2, p3, p4);
	}

	public static void unwrapWrap(Number p1, NativeKey p2, Number p3, NativeKey p4, NativeKey p5, NativeByteString p6, NativeByteString p7) {
		cryptoEngine.get().unwrapWrap(p1, p2, p3, p4, p5, p6, p7);
	}

	public static NativeByteString decryptEncryptLmk(Number p1, Number p2, Number p3, NativeByteString p4, NativeByteString p5, Number p6, Number p7, Number p8, NativeByteString p9, NativeByteString p10, NativeByteString p11) {
		return cryptoEngine.get().decryptEncryptLmk(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11);
	}

	// return jsFunction_wrapToLmk(cx, scope, args);

	public static NativeByteString deriveKeyLmk(Number p1, Number p2, Number p3, NativeByteString p4, NativeByteString p5, NativeByteString p6) {
		return cryptoEngine.get().deriveKeyLmk(p1, p2, p3, p4, p5, p6);
	}

	public static NativeByteString wrapToLmk(Number p1, Number p2, Number p3, NativeByteString p4, Number p5, Number p6, NativeByteString p7, NativeByteString p8) {
		return cryptoEngine.get().wrapToLmk(p1, p2, p3, p4, p5, p6, p7, p8);
	}

	public static NativeByteString edk(Number p1, NativeByteString p2, NativeByteString p3) {
		return cryptoEngine.get().edk(p1, p2, p3);
	}

	public static NativeByteString inputKey(NativeByteString p1, NativeByteString p2, NativeByteString p3) {
		return cryptoEngine.get().inputKey(p1, p2, p3);
	}

	public static NativeByteString getKey(NativeKey p1) {
		return cryptoEngine.get().getKey(p1);
	}
	//
	public static NativeByteString macConnByPan(NativeKey p1,Number p2,NativeByteString p3,NativeByteString p4,String p5){
		return cryptoEngine.get().macConnByPan(p1, p2, p3, p4, p5);
	}
	public static NativeByteString decryptConnByPan(NativeKey p1,Number p2,NativeByteString p3,NativeByteString p4,String p5){
		return cryptoEngine.get().decryptConnByPan(p1, p2, p3, p4, p5);
	}
	
	public static NativeByteString encryptConnByPan(NativeByteString p1,Number p2,NativeByteString p3,NativeByteString p4,String p5){
		return cryptoEngine.get().encryptConnByPan(p1, p2, p3, p4, p5);
	}
	
	public static NativeByteString deriveKeyConnByPan(NativeKey p1,Number p2,NativeByteString p3,NativeByteString p4,String p5,NativeKey p6){
		return cryptoEngine.get().deriveKeyConnByPan(p1, p2, p3, p4, p5,p6);
	}
	
	public static NativeByteString decryptEncryptConnByPan(NativeKey p1, Number p2, NativeByteString p3, Number p4,NativeByteString p5, NativeByteString p6, NativeByteString p7, String p8){
		return cryptoEngine.get().decryptEncryptConnByPan(p1, p2, p3, p4, p5, p6, p7, p8);
	}
	public static NativeByteString wrapKeyConnByPan(NativeKey p1, Number p2, NativeByteString p3, NativeByteString p4, String p5){
		return cryptoEngine.get().wrapKeyConnByPan(p1, p2, p3, p4, p5);
	}
	public static NativeByteString connectByPan(String p1){
		return cryptoEngine.get().connectByPan(p1);
	}
	
	public static NativeByteString disconnByPan(String p1){
		return cryptoEngine.get().disconnByPan(p1);
	}
	
	private NativeByteString jsFunction_encryptConnByPan(Context cx, Scriptable scope, Object[] args) {
		if ((args[0] instanceof Undefined) || (args[1] instanceof Undefined) || (args[2] instanceof Undefined)|| (args[3] instanceof Undefined) || (args[4] instanceof Undefined))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		if ((!(args[0] instanceof NativeByteString)) || (!(args[1] instanceof Number)) || (!(args[2] instanceof NativeByteString))|| (!(args[3] instanceof NativeByteString))|| (!(args[4] instanceof String)))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		NativeByteString sNew = encryptConnByPan((NativeByteString) args[0], (Number) args[1], (NativeByteString) args[2], (NativeByteString) args[3], (String) args[4]);
		return NativeByteString.newByteString(cx, scope, sNew);
	}
	private NativeByteString jsFunction_decryptConnByPan(Context cx, Scriptable scope, Object[] args) {
		if ((args[0] instanceof Undefined) || (args[1] instanceof Undefined) || (args[2] instanceof Undefined)|| (args[3] instanceof Undefined) || (args[4] instanceof Undefined))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		if ((!(args[0] instanceof NativeKey)) || (!(args[1] instanceof Number)) || (!(args[2] instanceof NativeByteString))|| (!(args[3] instanceof NativeByteString))|| (!(args[4] instanceof String)))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		NativeByteString sNew = decryptConnByPan((NativeKey) args[0], (Number) args[1], (NativeByteString) args[2], (NativeByteString) args[3], (String) args[4]);
		return NativeByteString.newByteString(cx, scope, sNew);
	}
	private NativeByteString jsFunction_deriveKeyConnByPan(Context cx, Scriptable scope, Object[] args) {
		if ((args[0] instanceof Undefined) || (args[1] instanceof Undefined) || (args[2] instanceof Undefined)|| (args[3] instanceof Undefined) || (args[4] instanceof Undefined)|| (args[5] instanceof Undefined))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		if ((!(args[0] instanceof NativeKey)) || (!(args[1] instanceof Number)) || (!(args[2] instanceof NativeByteString))|| (!(args[3] instanceof NativeByteString))|| (!(args[4] instanceof String))||(!(args[5] instanceof NativeKey)))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		NativeByteString sNew = deriveKeyConnByPan((NativeKey) args[0], (Number) args[1], (NativeByteString) args[2], (NativeByteString) args[3], (String) args[4],(NativeKey) args[5]);
		return NativeByteString.newByteString(cx, scope, sNew);
	}
	private NativeByteString jsFunction_wrapKeyConnByPan(Context cx, Scriptable scope, Object[] args) {
		if ((args[0] instanceof Undefined) || (args[1] instanceof Undefined) || (args[2] instanceof Undefined) || (args[3] instanceof Undefined)|| (args[4] instanceof Undefined))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		if ((!(args[0] instanceof NativeKey)) || (!(args[1] instanceof Number)) || (!(args[2] instanceof NativeByteString))|| (!(args[3] instanceof NativeByteString))|| (!(args[4] instanceof String)))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		NativeByteString sNew = wrapKeyConnByPan((NativeKey) args[0], (Number) args[1], (NativeByteString) args[2], (NativeByteString) args[3],(String) args[4]);
		return NativeByteString.newByteString(cx, scope, sNew);
	}
	private NativeByteString jsFunction_decryptEncryptConnByPan(Context cx, Scriptable scope, Object[] args) {
		if ((args[0] instanceof Undefined) || (args[1] instanceof Undefined) || (args[2] instanceof Undefined) || (args[3] instanceof Undefined)|| (args[4] instanceof Undefined)||(args[5] instanceof Undefined) || (args[6] instanceof Undefined) || (args[7] instanceof Undefined) )
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		if ((!(args[0] instanceof NativeKey)) || (!(args[1] instanceof Number)) || (!(args[2] instanceof NativeByteString))|| (!(args[3] instanceof Number))|| (!(args[4] instanceof NativeByteString))|| (!(args[5] instanceof NativeByteString))|| (!(args[6] instanceof NativeByteString))|| (!(args[7] instanceof String)))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		NativeByteString sNew = decryptEncryptConnByPan((NativeKey) args[0], (Number) args[1], (NativeByteString) args[2], (Number) args[3],(NativeByteString) args[4],(NativeByteString) args[5],(NativeByteString) args[6],(String) args[7]);
		return NativeByteString.newByteString(cx, scope, sNew);
	}
	private NativeByteString jsFunction_connectByPan(Context cx, Scriptable scope, Object[] args) {
		if ((args[0] instanceof Undefined))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		if ((!(args[0] instanceof String)))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		NativeByteString sNew = connectByPan((String) args[0]);
		return NativeByteString.newByteString(cx, scope, sNew);
	}
	private NativeByteString jsFunction_disconnByPan(Context cx, Scriptable scope, Object[] args) {
		if ((args[0] instanceof Undefined))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		if ((!(args[0] instanceof String)))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		NativeByteString sNew = disconnByPan((String) args[0]);
		return NativeByteString.newByteString(cx, scope, sNew);
	}
	// lmk包裹密钥的转加密
	private NativeByteString jsFunction_decryptEncryptLmk(Context cx, Scriptable scope, Object[] args) {
		if ((args[0] instanceof Undefined) || (args[1] instanceof Undefined) || (args[2] instanceof Undefined) || (args[3] instanceof Undefined) || (args[4] instanceof Undefined) || (args[5] instanceof Undefined) || (args[6] instanceof Undefined) || (args[7] instanceof Undefined) || (args[8] instanceof Undefined) || (args[9] instanceof Undefined) || (args[10] instanceof Undefined))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());

		NativeByteString sNew = decryptEncryptLmk((Number) args[0], (Number) args[1], (Number) args[2], (NativeByteString) args[3], (NativeByteString)args[4], (Number) args[5], (Number) args[6], (Number) args[7], (NativeByteString) args[8], (NativeByteString)args[9], (NativeByteString) args[10]);
		return NativeByteString.newByteString(cx, scope, sNew);
	}

	private NativeByteString jsFunction_deriveKeyLmk(Context cx, Scriptable scope, Object[] args) {
		NativeByteString sNew = deriveKeyLmk((Number) args[0], (Number) args[1], (Number) args[2], (NativeByteString) args[3], (NativeByteString) args[4], (NativeByteString) args[5]);
		return NativeByteString.newByteString(cx, scope, sNew);
	}

	private NativeByteString jsFunction_wrapToLmk(Context cx, Scriptable scope, Object[] args) {
		NativeByteString sNew = wrapToLmk((Number) args[0], (Number) args[1], (Number) args[2], (NativeByteString) args[3], (Number) args[4], (Number) args[5], (NativeByteString) args[6], (NativeByteString) args[7]);
		return NativeByteString.newByteString(cx, scope, sNew);
	}

	private NativeByteString jsFunction_edk(Context cx, Scriptable scope, Object[] args) {
		NativeByteString sNew = edk((Number) args[0], (NativeByteString) args[1], (NativeByteString) args[2]);
		return NativeByteString.newByteString(cx, scope, sNew);
	}

	private NativeByteString jsFunction_inputKey(Context cx, Scriptable scope, Object[] args) {
		NativeByteString sNew = inputKey((NativeByteString) args[0], (NativeByteString) args[1], (NativeByteString) args[2]);
		return NativeByteString.newByteString(cx, scope, sNew);
	}
	
	private NativeByteString jsFunction_getKey(Context cx, Scriptable scope, Object[] args) {
		NativeByteString sNew = getKey((NativeKey) args[0]);
		return NativeByteString.newByteString(cx, scope, sNew);
	}

	// the following just check the parameter mainly
	private NativeByteString jsFunction_decrypt(Context cx, Scriptable scope, Object[] args) {
		if ((args[0] instanceof Undefined) || (args[1] instanceof Undefined) || (args[2] instanceof Undefined))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		if ((!(args[0] instanceof NativeKey)) || (!(args[1] instanceof Number)) || (!(args[2] instanceof NativeByteString)))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		int mech = ((Number) args[1]).intValue();
		if ((mech != DES_ECB) && (mech != DES_CBC) && (mech != RSA))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		if (args.length > 4)
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		else if (args.length == 3) {
			Integer ee = new Integer(GPConstant.HEX);
			NativeByteString p4 = new NativeByteString("0000000000000000", ee);
			NativeByteString sNew = decrypt((NativeKey) args[0], (Number) args[1], (NativeByteString) args[2], p4);
			return NativeByteString.newByteString(cx, scope, sNew);
		} else if (args.length == 4) {
			if ((!(args[3] instanceof NativeByteString)))
				throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_TYPE)).toString());
			NativeByteString sNew = decrypt((NativeKey) args[0], (Number) args[1], (NativeByteString) args[2], (NativeByteString) args[3]);
			return NativeByteString.newByteString(cx, scope, sNew);
		} else
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_DATA)).toString());
	}

	private NativeByteString jsFunction_decryptEncrypt(Context cx, Scriptable scope, Object[] args) {
		if ((args[0] instanceof Undefined) || (args[1] instanceof Undefined) || (args[2] instanceof Undefined) || (args[3] instanceof Undefined) || (args[4] instanceof Undefined))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		if ((!(args[0] instanceof NativeKey)) || (!(args[1] instanceof Number)) || (!(args[2] instanceof NativeKey)) || (!(args[3] instanceof Number)) || (!(args[4] instanceof NativeByteString)))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		int mech = ((Number) args[1]).intValue();
		if ((mech != DES_ECB) && (mech != DES_CBC) && (mech != RSA))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		mech = ((Number) args[3]).intValue();
		if ((mech != DES_ECB) && (mech != DES_CBC) && (mech != RSA))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());

		NativeByteString defaultV = new NativeByteString("0000000000000000", new Integer(GPConstant.HEX));
		if (args.length == 5) {
			NativeByteString sNew = decryptEncrypt((NativeKey) args[0], (Number) args[1], (NativeKey) args[2], (Number) args[3], (NativeByteString) args[4], defaultV, defaultV);
			return NativeByteString.newByteString(cx, scope, sNew);
		} else if (args.length == 6) {
			if ((!(args[5] instanceof NativeByteString)))
				throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_TYPE)).toString());
			NativeByteString sNew = decryptEncrypt((NativeKey) args[0], (Number) args[1], (NativeKey) args[2], (Number) args[3], (NativeByteString) args[4], (NativeByteString) args[5], defaultV);
			return NativeByteString.newByteString(cx, scope, sNew);
		} else if (args.length == 7) {
			if ((!(args[5] instanceof NativeByteString)))
				throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_TYPE)).toString());
			if ((!(args[6] instanceof NativeByteString)))
				throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_TYPE)).toString());
			NativeByteString sNew = decryptEncrypt((NativeKey) args[0], (Number) args[1], (NativeKey) args[2], (Number) args[3], (NativeByteString) args[4], (NativeByteString) args[5], (NativeByteString) args[6]);
			return NativeByteString.newByteString(cx, scope, sNew);
		} else
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
	}

	private void jsFunction_deriveKey(Context cx, Scriptable scope, Object[] args) {
		if ((args[0] instanceof Undefined) || (args[1] instanceof Undefined) || (args[2] instanceof Undefined) || (args[3] instanceof Undefined))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		if ((!(args[0] instanceof NativeKey)) || (!(args[1] instanceof Number)) || (!(args[3] instanceof NativeKey)) || (!(args[2] instanceof NativeByteString)))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		int mech = ((Number) args[1]).intValue();
		if ((mech != DES_ECB) && (mech != DES_CBC))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());

		deriveKey((NativeKey) args[0], (Number) args[1], (NativeByteString) args[2], (NativeKey) args[3]);
	}

	private void jsFunction_deriveOddKey(Context cx, Scriptable scope, Object[] args) {
		if ((args[0] instanceof Undefined) || (args[1] instanceof Undefined) || (args[2] instanceof Undefined) || (args[3] instanceof Undefined))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		if ((!(args[0] instanceof NativeKey)) || (!(args[1] instanceof Number)) || (!(args[3] instanceof NativeKey)) || (!(args[2] instanceof NativeByteString)))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		int mech = ((Number) args[1]).intValue();
		if ((mech != DES_ECB) && (mech != DES_CBC))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		deriveOddKey((NativeKey) args[0], (Number) args[1], (NativeByteString) args[2], (NativeKey) args[3]);
	}

	private NativeByteString jsFunction_digest(Context cx, Scriptable scope, Object[] args) {
		if (args.length != 2)
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		else if (!(args[0] instanceof Number))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_TYPE)).toString());
		else if (!(args[1] instanceof NativeByteString))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_TYPE)).toString());
		else {
			int mech = ((Number) args[0]).intValue();
			if ((mech != SHA_1) && (mech != MD5))
				throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
			NativeByteString sNew = digest((Number) args[0], (NativeByteString) args[1]);
			return NativeByteString.newByteString(cx, scope, sNew);
		}
	}

	private NativeByteString jsFunction_encrypt(Context cx, Scriptable scope, Object[] args) {
		if ((args[0] instanceof Undefined) || (args[1] instanceof Undefined) || (args[2] instanceof Undefined))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		if ((!(args[0] instanceof NativeKey)) || (!(args[1] instanceof Number)) || (!(args[2] instanceof NativeByteString)))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_TYPE)).toString());
		int mech = ((Number) args[1]).intValue();
		if ((mech != DES_ECB) && (mech != DES_CBC) && (mech != RSA))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		if (args.length > 4)
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		else if (args.length == 3) {
			Integer ee = new Integer(GPConstant.HEX);
			NativeByteString p4 = new NativeByteString("0000000000000000", ee);
			NativeByteString sNew = encrypt((NativeKey) args[0], (Number) args[1], (NativeByteString) args[2], p4);
			return NativeByteString.newByteString(cx, scope, sNew);
		} else if (args.length == 4) {
			NativeByteString sNew = encrypt((NativeKey) args[0], (Number) args[1], (NativeByteString) args[2], (NativeByteString) args[3]);
			return NativeByteString.newByteString(cx, scope, sNew);
		} else
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
	}

	private void jsFunction_generateKey(Context cx, Scriptable scope, Object[] args) {
		if (!(args[0] instanceof Number))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		int mech = ((Number) args[0]).intValue();
		if ((mech != DES_KEY_GEN) && (mech != DES2_KEY_GEN))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		if (!(args[1] instanceof NativeKey))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_TYPE)).toString());
		generateKey((Number) args[0], (NativeKey) args[1]);
	}

	private void jsFunction_generateKeyPair(Context cx, Scriptable scope, Object[] args) {
		if ((!(args[0] instanceof Number)) || (!(args[1] instanceof NativeKey)) || (!(args[2] instanceof NativeKey)))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		int mech = ((Number) args[0]).intValue();
		if (mech != RSA_KEY_PAIR_GEN)
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		generateKeyPair((Number) args[0], (NativeKey) args[1], (NativeKey) args[2]);
	}

	private NativeByteString jsFunction_generateRandom(Context cx, Scriptable scope, Object[] args) {
		if (args.length == 1) {
			if (!(args[0] instanceof Number))
				throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_TYPE)).toString());
			else {
				NativeByteString sNew = generateRandom((Number) args[0]);
				return NativeByteString.newByteString(cx, scope, sNew);
			}
		} else
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
	}

	private NativeByteString jsFunction_sign(Context cx, Scriptable scope, Object[] args) {
		if ((args[0] instanceof Undefined) || (args[1] instanceof Undefined) || (args[2] instanceof Undefined))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		if ((!(args[0] instanceof NativeKey)) || (!(args[1] instanceof Number)) || (!(args[2] instanceof NativeByteString)))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_TYPE)).toString());
		int mech = ((Number) args[1]).intValue();
		if ((mech != DES_MAC) && (mech != DES_MAC_EMV) && (mech != RSA))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		if (args.length == 3) {
			NativeByteString p4 = new NativeByteString("0000000000000000", new Integer(GPConstant.HEX));
			NativeByteString sNew = sign((NativeKey) args[0], (Number) args[1], (NativeByteString) args[2], p4);
			return NativeByteString.newByteString(cx, scope, sNew);
		} else if (args.length == 4) {
			NativeByteString sNew = sign((NativeKey) args[0], (Number) args[1], (NativeByteString) args[2], (NativeByteString) args[3]);
			return NativeByteString.newByteString(cx, scope, sNew);
		} else
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
	}

	private Boolean jsFunction_verify(Context cx, Scriptable scope, Object[] args) {
		if ((args[0] instanceof Undefined) || (args[1] instanceof Undefined) || (args[2] instanceof Undefined) || (args[3] instanceof Undefined))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		if ((!(args[0] instanceof NativeKey)) || (!(args[1] instanceof Number)) || (!(args[2] instanceof NativeByteString)) || (!(args[3] instanceof NativeByteString)))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		int mech = ((Number) args[1]).intValue();
		if ((mech != DES_MAC) && (mech != DES3_MAC) && (mech != DES3_MAC_EMV) && (mech != RSA))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		if (args.length == 4) {
			boolean result = verify((NativeKey) args[0], (Number) args[1], (NativeByteString) args[2], (NativeByteString) args[3]);
			return new Boolean(result);
		} else
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
	}

	private void jsFunction_wrap(Context cx, Scriptable scope, Object[] args) {
		if ((args[0] instanceof Undefined) || (args[1] instanceof Undefined) || (args[2] instanceof Undefined) || (args[3] instanceof Undefined))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		if ((!(args[0] instanceof NativeKey)) || (!(args[1] instanceof Number)) || (!(args[2] instanceof NativeKey)) || (!(args[3] instanceof NativeKey)))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		int mech = ((Number) args[1]).intValue();
		if ((mech != DES_ECB) && (mech != DES_CBC) && (mech != RSA))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());

		if (args.length == 4) {
			Integer ee = new Integer(GPConstant.HEX);
			NativeByteString p5 = new NativeByteString("0000000000000000", ee);
			wrap((NativeKey) args[0], (Number) args[1], (NativeKey) args[2], (NativeKey) args[3], p5);
		} else if (args.length == 5)
			wrap((NativeKey) args[0], (Number) args[1], (NativeKey) args[2], (NativeKey) args[3], (NativeByteString) args[4]);
		else
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
	}

	private void jsFunction_unwrap(Context cx, Scriptable scope, Object[] args) {
		if ((!(args[0] instanceof NativeKey)) || (!(args[1] instanceof NativeKey)))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());

		if (args.length == 2) {
			Integer ee = new Integer(GPConstant.HEX);
			Number p1 = new Integer(DES_ECB);
			NativeByteString p4 = new NativeByteString("0000000000000000", ee);
			unwrap(p1, (NativeKey) args[0], (NativeKey) args[1], p4);
		} else
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
	}

	private void jsFunction_unwrapWrap(Context cx, Scriptable scope, Object[] args) {
		if ((args[0] instanceof Undefined) || (args[1] instanceof Undefined) || (args[2] instanceof Undefined) || (args[3] instanceof Undefined))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
		if ((!(args[0] instanceof NativeKey)) || (!(args[1] instanceof Number)) || (!(args[2] instanceof NativeKey)) || (!(args[3] instanceof NativeKey)))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());

		int mech = ((Number) args[1]).intValue();
		if ((mech != DES_ECB) && (mech != DES_CBC) && (mech != RSA))
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());

		Integer p1 = new Integer(DES_ECB);
		Integer ee = new Integer(GPConstant.HEX);
		NativeByteString p6 = new NativeByteString("0000000000000000", ee);
		NativeByteString p7 = new NativeByteString("0000000000000000", ee);
		if (args.length == 4) {
			unwrapWrap(p1, (NativeKey) args[0], (Number) args[1], (NativeKey) args[2], (NativeKey) args[3], p6, p7);
		} else if (args.length == 5) {
			unwrapWrap(p1, (NativeKey) args[0], (Number) args[1], (NativeKey) args[2], (NativeKey) args[3], p6, (NativeByteString) args[4]);
		} else
			throw new EvaluatorException((new GPError("Crypto", GPError.INVALID_ARGUMENTS)).toString());
	}

	// end of function

	protected int maxInstanceId() {
		return 0;
	}

	protected void initPrototypeId(int id) {
		String s;
		int arity;
		switch (id) {
		// function
		case Id_decrypt:
			arity = 4;
			s = "decrypt";
			break;
		case Id_decryptEncrypt:
			arity = 7;
			s = "decryptEncrypt";
			break;
		case Id_deriveKey:
			arity = 4;
			s = "deriveKey";
			break;
		case Id_deriveOddKey:
			arity = 4;
			s = "deriveOddKey";
			break;
		case Id_digest:
			arity = 2;
			s = "digest";
			break;
		case Id_encrypt:
			arity = 4;
			s = "encrypt";
			break;
		case Id_generateKey:
			arity = 2;
			s = "generateKey";
			break;
		case Id_generateKeyPair:
			arity = 3;
			s = "generateKeyPair";
			break;
		case Id_generateRandom:
			arity = 1;
			s = "generateRandom";
			break;
		case Id_sign:
			arity = 4;
			s = "sign";
			break;
		case Id_unwrap:
			arity = 2;
			s = "unwrap";
			break;
		case Id_unwrapWrap:
			arity = 5;
			s = "unwrapWrap";
			break;
		case Id_verify:
			arity = 4;
			s = "verify";
			break;
		case Id_wrap:
			arity = 5;
			s = "wrap";
			break;
		case Id_decryptEncryptLmk:
			arity = 5;
			s = "decryptEncryptLmk";
			break;//
		case Id_deriveKeyLmk:
			arity = 5;
			s = "deriveKeyLmk";
			break;//
		case Id_wrapToLmk:
			arity = 5;
			s = "wrapToLmk";
			break;//
		case Id_edk:
			arity = 5;
			s = "edk";
			break;//
		case Id_inputKey:
			arity = 5;
			s = "inputKey";
			break;//
		case Id_getKey:
			arity = 5;
			s = "getKey";
			break;//
		case Id_encryptConnByPan:
			arity = 5;
			s = "encryptConnByPan";
			break;//
		case Id_decryptConnByPan:
			arity = 5;
			s = "decryptConnByPan";
			break;//
		case Id_deriveKeyConnByPan:
			arity = 5;
			s = "deriveKeyConnByPan";
			break;//
		case Id_decryptEncryptConnByPan:
			arity = 8;
			s = "decryptEncryptConnByPan";
			break;//
		case Id_wrapKeyConnByPan:
			arity = 6;
			s = "wrapKeyConnByPan";
			break;//
		case Id_connectByPan:
			arity = 1;
			s = "connectByPan";
			break;//
		case Id_disconnByPan:
			arity = 1;
			s = "disconnByPan";
			break;//
		default:
			throw new IllegalArgumentException(String.valueOf(id));
		}
		initPrototypeMethod(CRYPTO_TAG, id, s, arity);
	}

	protected int findPrototypeId(String s) {
		int id = 0;
		String X = null;
		if (s.equals("sign")) {
			X = "sign";
			id = Id_sign;
		}
		if (s.equals("wrap")) {
			X = "wrap";
			id = Id_wrap;
		}
		if (s.equals("digest")) {
			X = "digest";
			id = Id_digest;
		}
		if (s.equals("verify")) {
			X = "verify";
			id = Id_verify;
		}
		if (s.equals("unwrap")) {
			X = "unwrap";
			id = Id_unwrap;
		}
		if (s.equals("decrypt")) {
			X = "decrypt";
			id = Id_decrypt;
		}
		if (s.equals("encrypt")) {
			X = "encrypt";
			id = Id_encrypt;
		}
		if (s.equals("deriveKey")) {
			X = "deriveKey";
			id = Id_deriveKey;
		}
		if (s.equals("deriveOddKey")) {
			X = "deriveOddKey";
			id = Id_deriveOddKey;
		}
		if (s.equals("unwrapWrap")) {
			X = "unwrapWrap";
			id = Id_unwrapWrap;
		}
		if (s.equals("generateKey")) {
			X = "generateKey";
			id = Id_generateKey;
		}
		if (s.equals("generateRandom")) {
			X = "generateRandom";
			id = Id_generateRandom;
		}
		if (s.equals("decryptEncrypt")) {
			X = "decryptEncrypt";
			id = Id_decryptEncrypt;
		}
		if (s.equals("generateKeyPair")) {
			X = "generateKeyPair";
			id = Id_generateKeyPair;
		}
		if (s.equals("decryptEncryptLmk")) {
			X = "decryptEncryptLmk";
			id = Id_decryptEncryptLmk;
		}
		if (s.equals("deriveKeyLmk")) {
			X = "deriveKeyLmk";
			id = Id_deriveKeyLmk;
		}
		if (s.equals("wrapToLmk")) {
			X = "wrapToLmk";
			id = Id_wrapToLmk;
		}
		if (s.equals("edk")) {
			X = "edk";
			id = Id_edk;
		}
		if (s.equals("inputKey")) {
			X = "inputKey";
			id = Id_inputKey;
		}
		
		if (s.equals("getKey")) {
			X = "getKey";
			id = Id_getKey;
		}
		if (s.equals("encryptConnByPan")) {
			X = "encryptConnByPan";
			id = Id_encryptConnByPan;
		}
		if (s.equals("decryptConnByPan")) {
			X = "decryptConnByPan";
			id = Id_decryptConnByPan;
		}
		if (s.equals("deriveKeyConnByPan")) {
			X = "deriveKeyConnByPan";
			id = Id_deriveKeyConnByPan;
		}
		if (s.equals("decryptEncryptConnByPan")) {
			X = "decryptEncryptConnByPan";
			id = Id_decryptEncryptConnByPan;
		}
		if (s.equals("wrapKeyConnByPan")) {
			X = "wrapKeyConnByPan";
			id = Id_wrapKeyConnByPan;
		}
		if (s.equals("connectByPan")) {
			X = "connectByPan";
			id = Id_connectByPan;
		}
		if (s.equals("disconnByPan")) {
			X = "disconnByPan";
			id = Id_disconnByPan;
		}
		return id;
	}

	// define value for all function and constant
	private static final int Id_decrypt = 1, Id_decryptEncrypt = 2, Id_deriveKey = 3, Id_digest = 4, Id_encrypt = 5, Id_generateKey = 6, Id_generateKeyPair = 7, Id_generateRandom = 8, Id_sign = 9, Id_unwrap = 10, Id_unwrapWrap = 11, Id_verify = 12, Id_wrap = 13, Id_deriveOddKey = 14, Id_decryptEncryptLmk = 15, Id_deriveKeyLmk = 16, Id_wrapToLmk = 17, Id_edk = 18, Id_inputKey = 19,Id_getKey=20, Id_encryptConnByPan=21,Id_decryptConnByPan=22,Id_deriveKeyConnByPan=23,Id_decryptEncryptConnByPan=24,Id_wrapKeyConnByPan = 25,Id_connectByPan=26,Id_disconnByPan = 27,LAST_METHOD_ID = 27,
	// the following should be contants

			MAX_PROTOTYPE_ID = LAST_METHOD_ID;

	public static final int DES_ECB = 1, DES_CBC = 2, RSA = 3, SHA_1 = 4, MD5 = 5, DES_KEY_GEN = 6, DES2_KEY_GEN = 7, RSA_KEY_PAIR_GEN = 8, ISO9797_METHOD_1 = 9, ISO9797_METHOD_2 = 10, EMV_PAD = 11, NONE = 12, DES_MAC = 13, DES_MAC_EMV = 14, DES3_MAC = 15, DES3_MAC_EMV = 16, DES_CBC_PAD = 17;

	public static final int LMK_DES_ECB=0,LMK_DES_ECB_LP=1,LMK_DES_ECB_P=2,LMK_DES_CBC=3,LMK_DES_CBC_LP=4,LMK_DES_CBC_P=5;
	
	public static final int LMK_SINGLE_LENGTH_KEY=0,LMK_DOUBLE_LENGTH_KEY=1;
	
	public static final int ZMK=0;
	
	public static final int ENCRYPT=0,DECRYPT=1;
	//ALGORITHM TYPE for Agricultural Bank
	public static final int ABC_DES_ECB = 20,ABC_DES_CBC = 17;
	
	//public static GPKeyCryptoEngine cryptoEngine = null;
	
	public static ThreadLocal<GPKeyCryptoEngine> cryptoEngine = new ThreadLocal<GPKeyCryptoEngine>() { 

		public GPKeyCryptoEngine initialValue() { // 初始化，默认是返回 null 

		return null; 

		} 

		};

}
