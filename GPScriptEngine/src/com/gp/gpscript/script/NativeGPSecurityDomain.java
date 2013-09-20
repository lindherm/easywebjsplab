// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 2010-1-20 9:45:10
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   NativeGPSecurityDomain.java

package com.gp.gpscript.script;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import com.gp.gpscript.keymgr.util.encoders.Hex;
import com.gp.gpscript.util.CryptoUtil;

// Referenced classes of package com.watchdata.gpscript.script:
//            GPError, NativeByteString, NativeKey, NativeGPScp01, 
//            NativeGPScp02, NativeCard, Application, NativeCrypto

public class NativeGPSecurityDomain extends IdScriptableObject {
	private Logger log = Logger.getLogger(NativeGPSecurityDomain.class);

	public static void init(Context cx, Scriptable scope, boolean sealed) {
		prototypeSecurityDomain.activatePrototypeMap(MAX_PROTOTYPE_ID);
		prototypeSecurityDomain.setPrototype(ScriptableObject
				.getObjectPrototype(scope));
		prototypeSecurityDomain.setParentScope(scope);
		if (sealed)
			prototypeSecurityDomain.sealObject();
	}

	public NativeGPSecurityDomain() {
		p2_storeData = 0;
		secureLevel = 0;
	}

	public String getClassName() {
		return "GPSecurityDomain";
	}

	public void reset() {
		p2_storeData = 0;
	}

	public void setObjectPrototype() {
		setPrototype(prototypeSecurityDomain);
		setParentScope(prototypeSecurityDomain.getParentScope());
	}

	protected int findInstanceIdInfo(String s) {
		if (s.equals("key"))
			return IdScriptableObject.instanceIdInfo(7, 4);
		if (s.equals("data"))
			return IdScriptableObject.instanceIdInfo(7, 5);
		if (s.equals("crypto"))
			return IdScriptableObject.instanceIdInfo(7, 3);
		if (s.equals("profile"))
			return IdScriptableObject.instanceIdInfo(7, 6);
		if (s.equals("card"))
			return IdScriptableObject.instanceIdInfo(7, 7);
		if (s.equals("secureChannel"))
			return IdScriptableObject.instanceIdInfo(7, 1);
		if (s.equals("securityDomain"))
			return IdScriptableObject.instanceIdInfo(7, 2);
		else
			return super.findInstanceIdInfo(s);
	}

	public static void main(String[] args) {
		// << 16 | id;
	}

	protected String getInstanceIdName(int id) {
		if (id == 4)
			return "key";
		if (id == 5)
			return "data";
		if (id == 3)
			return "crypto";
		if (id == 6)
			return "profile";
		if (id == 7)
			return "card";
		if (id == 1)
			return "secureChannel";
		if (id == 2)
			return "securityDomain";
		else
			return super.getInstanceIdName(id);
	}

	protected Object getInstanceIdValue(int id) {
		if (id == 4)
			return key;
		if (id == 5)
			return data;
		if (id == 3)
			return crypto;
		if (id == 6)
			return profile;
		if (id == 7)
			return card;
		if (id == 1)
			return secureChannel;
		if (id == 2)
			return securityDomain;
		else
			return super.getInstanceIdValue(id);
	}

	public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope,
			Scriptable thisObj, Object args[]) {
		
		if (!f.hasTag(SECURITYDOMAIN_TAG))
			return super.execIdCall(f, cx, scope, thisObj, args);
		int id = f.methodId();
		switch (id) {
		case 1: // '\001'
			return realThis(thisObj, f).jsFunction_sendApdu(cx, scope, args);

		case 2: // '\002'
			return realThis(thisObj, f).jsFunction_getData(cx, scope, args);

		case 3: // '\003'
			return realThis(thisObj, f).jsFunction_getStatus(cx, scope, args);

		case 5: // '\005'
			return realThis(thisObj, f).jsFunction_installForInstall(cx, scope,
					args);

		case 4: // '\004'
			return realThis(thisObj, f).jsFunction_installForExtradition(cx,
					scope, args);

		case 6: // '\006'
			return realThis(thisObj, f)
					.jsFunction_installForInstallAndSelectable(cx, scope, args);

		case 7: // '\007'
			return realThis(thisObj, f).jsFunction_installForLoad(cx, scope,
					args);

		case 8: // '\b'
			return realThis(thisObj, f).jsFunction_installForPersonalization(
					cx, scope, args);

		case 9: // '\t'
			return realThis(thisObj, f).jsFunction_load(cx, scope, args);

		case 10: // '\n'
			return realThis(thisObj, f).jsFunction_loadByName(cx, scope, args);

		case 12: // '\f'
			return realThis(thisObj, f).jsFunction_select(cx, scope, args);

		case 13: // '\r'
			return realThis(thisObj, f).jsFunction_deleteAID(cx, scope, args);

		case 14: // '\016'
			return realThis(thisObj, f).jsFunction_putKey(cx, scope, args);

		case 15: // '\017'
			realThis(thisObj, f).jsFunction_setStatus(cx, scope, args);
			return null;

		case 16: // '\020'
			realThis(thisObj, f).jsFunction_storeData(cx, scope, args);
			return null;

		case 17: // '\021'
			realThis(thisObj, f).jsFunction_storeEmvData(cx, scope, args);
			return null;

		case 11: // '\013'
			return realThis(thisObj, f).jsFunction_openSecureChannel(cx, scope,
					args);
		case 18: // '\021'
			realThis(thisObj, f).jsFunction_closeSecureChannel(cx, scope, args);
			return null;
		
		case 20:
			return realThis(thisObj, f).jsFunction_putKeyLmk(cx, scope, args);
		case 21:
			return realThis(thisObj, f).jsFunction_putKeyStd(cx, scope, args);
		case 22:
			 realThis(thisObj, f).jsFunction_storeDataLmk(cx, scope, args);
			 return null;
		case 23:
			 realThis(thisObj, f).jsFunction_setPan(cx, scope, args);
			 return null;
		}
		throw new IllegalArgumentException(String.valueOf(id));
	}

	private NativeGPSecurityDomain realThis(Scriptable thisObj,
			IdFunctionObject f) {
		if (!(thisObj instanceof NativeGPSecurityDomain))
			throw IdScriptableObject.incompatibleCallError(f);
		else
			return (NativeGPSecurityDomain) thisObj;
	}

	private NativeByteString jsFunction_sendApdu(Context cx, Scriptable scope,
			Object args[]) {
		if (!(args[0] instanceof Number) || !(args[1] instanceof Number)
				|| !(args[2] instanceof Number) || !(args[3] instanceof Number))
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1035)).toString());
		NativeByteString defaultData = null;
		Number defaultLE = new Integer(-1);
		Number defaultSW = new Integer(36864);
		if (args.length == 4) {
			NativeByteString sNew = wrapApdu(cx, scope, (Number) args[0],
					(Number) args[1], (Number) args[2], (Number) args[3],
					defaultData, defaultLE, defaultSW);
			return NativeByteString.newByteString(cx, scope, sNew);
		}
		if (args.length == 5)
			if (!(args[4] instanceof NativeByteString)) {
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						1035)).toString());
			} else {
				NativeByteString sNew = wrapApdu(cx, scope, (Number) args[0],
						(Number) args[1], (Number) args[2], (Number) args[3],
						(NativeByteString) args[4], defaultLE, defaultSW);
				return NativeByteString.newByteString(cx, scope, sNew);
			}
		if (args.length == 6)
			if (!(args[4] instanceof NativeByteString)
					|| !(args[5] instanceof Number)) {
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						1035)).toString());
			} else {
				NativeByteString sNew = wrapApdu(cx, scope, (Number) args[0],
						(Number) args[1], (Number) args[2], (Number) args[3],
						(NativeByteString) args[4], (Number) args[5], defaultSW);
				return NativeByteString.newByteString(cx, scope, sNew);
			}
		if (args.length == 7) {
			if (!(args[4] instanceof NativeByteString)
					|| !(args[5] instanceof Number)
					|| !(args[6] instanceof Number)) {
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						1035)).toString());
			} else {
				NativeByteString sNew = wrapApdu(cx, scope, (Number) args[0],
						(Number) args[1], (Number) args[2], (Number) args[3],
						(NativeByteString) args[4], (Number) args[5],
						(Number) args[6]);
				return NativeByteString.newByteString(cx, scope, sNew);
			}
		} else {
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1041)).toString());
		}
	}

	private NativeByteString jsFunction_getData(Context cx, Scriptable scope,
			Object args[]) {
		int tag = 0;
		Number SW = new Integer(36864);
		if (args.length < 1 || args.length > 2)
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1035)).toString());
		if (!(args[0] instanceof Number))
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1035)).toString());
		if (args.length == 2) {
			if (!(args[1] instanceof Number))
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						1035)).toString());
			SW = (Number) args[1];
		}
		tag = ((Number) args[0]).intValue();
		Byte P1 = new Byte((byte) (tag / 256));
		Byte P2 = new Byte((byte) (tag % 256));
		wrapApdu(cx, scope, new Integer(128), new Integer(202), P1, P2, null,
				new Integer(0), SW);
		NativeByteString sNew = card.response;
		return sNew;
	}

	private NativeByteString jsFunction_getStatus(Context cx, Scriptable scope,
			Object args[]) {
		Number SW = new Integer(36864);
		if (args.length < 3 || args.length > 4)
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1035)).toString());
		if (!(args[0] instanceof Number)
				|| !(args[1] instanceof NativeByteString)
				|| !(args[2] instanceof Boolean))
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1035)).toString());
		if (args.length == 4) {
			if (!(args[3] instanceof Number))
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						1035)).toString());
			SW = (Number) args[3];
		}
		int type = ((Number) args[0]).intValue();
		Integer P1 = new Integer(0);
		Integer P2 = new Integer(0);
		switch (type) {
		case 1005:
			P1 = new Integer(128);
			break;

		case 1006:
			P1 = new Integer(64);
			break;

		case 1007:
			P1 = new Integer(32);
			break;

		case 1008:
			P1 = new Integer(16);
			break;
		}
		if (((Boolean) args[2]).booleanValue())
			P2 = new Integer(1);
		wrapApdu(cx, scope, new Integer(128), new Integer(242), P1, P2,
				(NativeByteString) args[1], new Integer(-1), SW);
		NativeByteString sNew = card.response;
		return sNew;
	}

	private NativeByteString jsFunction_installForExtradition(Context cx,
			Scriptable scope, Object args[]) {
		Number SW = new Integer(36864);
		if (!(args[0] instanceof NativeByteString)
				|| !(args[1] instanceof NativeByteString)
				|| !(args[2] instanceof NativeByteString))
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1035)).toString());
		if (args.length == 3)
			SW = new Integer(36864);
		else if (args.length == 4)
			SW = (Number) args[3];
		else
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1035)).toString());
		String secDomAID = ((NativeByteString) args[0]).toString();
		String appAID = ((NativeByteString) args[1]).toString();
		String token = ((NativeByteString) args[2]).toString();
		String strCommData = Hex.byteToString((byte) (secDomAID.length() / 2))
				+ secDomAID + "00"
				+ Hex.byteToString((byte) (appAID.length() / 2)) + appAID
				+ "00" + "00" + Hex.byteToString((byte) (token.length() / 2))
				+ token;
		NativeByteString comData = new NativeByteString(strCommData,
				new Integer(1000));
		wrapApdu(cx, scope, new Integer(128), new Integer(230),
				new Integer(32), new Integer(0), comData, new Integer(-1), SW);
		NativeByteString sNew = card.response;
		return sNew;
	}

	private NativeByteString jsFunction_installForLoad(Context cx,
			Scriptable scope, Object args[]) {
		Number SW = new Integer(36864);
		if (!(args[0] instanceof NativeByteString)
				|| !(args[1] instanceof NativeByteString)
				|| !(args[2] instanceof NativeByteString)
				|| !(args[3] instanceof NativeByteString)
				|| !(args[4] instanceof NativeByteString))
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1035)).toString());
		if (args.length == 5)
			SW = new Integer(36864);
		else if (args.length == 6)
			SW = (Number) args[5];
		else
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1041)).toString());
		String loadFileAID = ((NativeByteString) args[0]).toString();
		String secDomAID = ((NativeByteString) args[1]).toString();
		String loadDBHash = ((NativeByteString) args[2]).toString();
		String loadParam = ((NativeByteString) args[3]).toString();
		String loadToken = ((NativeByteString) args[4]).toString();
		String strCommData = Hex
				.byteToString((byte) (loadFileAID.length() / 2))
				+ loadFileAID
				+ Hex.byteToString((byte) (secDomAID.length() / 2))
				+ secDomAID
				+ Hex.byteToString((byte) (loadDBHash.length() / 2))
				+ loadDBHash
				+ Hex.byteToString((byte) (loadParam.length() / 2))
				+ loadParam
				+ Hex.byteToString((byte) (loadToken.length() / 2)) + loadToken;
		NativeByteString comData = new NativeByteString(strCommData,
				new Integer(1000));
		wrapApdu(cx, scope, new Integer(128), new Integer(230), new Integer(2),
				new Integer(0), comData, new Integer(-1), SW);
		NativeByteString sNew = card.response;
		return sNew;
	}

	private NativeByteString jsFunction_installForPersonalization(Context cx,
			Scriptable scope, Object args[]) {
		Number SW = new Integer(36864);
		if (!(args[0] instanceof NativeByteString))
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1035)).toString());
		if (args.length == 1)
			SW = new Integer(36864);
		else if (args.length == 2)
			SW = (Number) args[1];
		else
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1041)).toString());
		String appAID = ((NativeByteString) args[0]).toString();
		String strCommData = "0000"
				+ Hex.byteToString((byte) (appAID.length() / 2)) + appAID
				+ "000000";
		NativeByteString comData = new NativeByteString(strCommData,
				new Integer(1000));
		wrapApdu(cx, scope, new Integer(128), new Integer(230),
				new Integer(32), new Integer(0), comData, new Integer(-1), SW);
		NativeByteString sNew = card.response;
		return sNew;
	}

	private NativeByteString jsFunction_installForInstall(Context cx,
			Scriptable scope, Object args[]) {
		Number SW = new Integer(36864);
		if (!(args[0] instanceof NativeByteString)
				|| !(args[1] instanceof NativeByteString)
				|| !(args[2] instanceof NativeByteString)
				|| !(args[3] instanceof NativeByteString)
				|| !(args[4] instanceof NativeByteString)
				|| !(args[5] instanceof NativeByteString))
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1035)).toString());
		if (args.length == 6)
			SW = new Integer(36864);
		else if (args.length == 7)
			SW = (Number) args[6];
		else
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1041)).toString());
		String execLFAID = ((NativeByteString) args[0]).toString();
		String execModAID = ((NativeByteString) args[1]).toString();
		String execInsAID = ((NativeByteString) args[2]).toString();
		String privileges = ((NativeByteString) args[3]).toString();
		String installParam = ((NativeByteString) args[4]).toString();
		String installToken = ((NativeByteString) args[5]).toString();
		String strCommData = Hex.byteToString((byte) (execLFAID.length() / 2))
				+ execLFAID
				+ Hex.byteToString((byte) (execModAID.length() / 2))
				+ execModAID
				+ Hex.byteToString((byte) (execInsAID.length() / 2))
				+ execInsAID + "01" + privileges
				+ Hex.byteToString((byte) (installParam.length() / 2))
				+ installParam
				+ Hex.byteToString((byte) (installToken.length() / 2))
				+ installToken;
		NativeByteString comData = new NativeByteString(strCommData,
				new Integer(1000));
		wrapApdu(cx, scope, new Integer(128), new Integer(230),
				new Integer(12), new Integer(0), comData, new Integer(-1), SW);
		NativeByteString sNew = card.response;
		return sNew;
	}

	private NativeByteString jsFunction_installForInstallAndSelectable(
			Context cx, Scriptable scope, Object args[]) {
		Number SW = new Integer(36864);
		if (!(args[0] instanceof NativeByteString)
				|| !(args[1] instanceof NativeByteString)
				|| !(args[2] instanceof NativeByteString)
				|| !(args[3] instanceof NativeByteString)
				|| !(args[4] instanceof NativeByteString)
				|| !(args[5] instanceof NativeByteString))
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1035)).toString());
		if (args.length == 6)
			SW = new Integer(36864);
		else if (args.length == 7)
			SW = (Number) args[6];
		else
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1041)).toString());
		String execLFAID = ((NativeByteString) args[0]).toString();
		String execModAID = ((NativeByteString) args[1]).toString();
		String execInsAID = ((NativeByteString) args[2]).toString();
		String privileges = ((NativeByteString) args[3]).toString();
		String installParam = ((NativeByteString) args[4]).toString();
		String installToken = ((NativeByteString) args[5]).toString();
		String strCommData = Hex.byteToString((byte) (execLFAID.length() / 2))
				+ execLFAID
				+ Hex.byteToString((byte) (execModAID.length() / 2))
				+ execModAID
				+ Hex.byteToString((byte) (execInsAID.length() / 2))
				+ execInsAID + "01" + privileges
				+ Hex.byteToString((byte) (installParam.length() / 2))
				+ installParam
				+ Hex.byteToString((byte) (installToken.length() / 2))
				+ installToken;
		NativeByteString comData = new NativeByteString(strCommData,
				new Integer(1000));
		wrapApdu(cx, scope, new Integer(128), new Integer(230),
				new Integer(12), new Integer(0), comData, new Integer(-1), SW);
		NativeByteString sNew = card.response;
		return sNew;
	}

	private NativeByteString jsFunction_load(Context cx, Scriptable scope,
			Object args[]) {
		Number SW = new Integer(36864);
		if (!(args[0] instanceof NativeByteString))
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1035)).toString());
		String arrayBlock[] = null;
		arrayBlock = parseFileStructure((NativeByteString) args[0]);
		if (arrayBlock == null)
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1035)).toString());
		for (int i = 0; i < arrayBlock.length; i++) {
			log.debug(arrayBlock[i]);
			String strCommData = arrayBlock[i];
			NativeByteString comData = new NativeByteString(strCommData,
					new Integer(1000));
			if (i < arrayBlock.length - 1)
				wrapApdu(cx, scope, new Integer(128), new Integer(232),
						new Integer(0), new Integer(i), comData,
						new Integer(-1), SW);
			else
				wrapApdu(cx, scope, new Integer(128), new Integer(232),
						new Integer(128), new Integer(i), comData, new Integer(
								-1), SW);
		}

		NativeByteString sNew = card.response;
		return sNew;
	}

	private String[] parseFileStructure(NativeByteString bs) {
		Vector ss = new Vector();
		int length = bs.length;
		byte dd[] = new byte[length];
		for (int i = 0; i < length; i++)
			dd[i] = bs.ByteAt(i);

		int blockNumber = 0;
		for (int i = 0; i < length;) {
			if (dd[i] != -30 && dd[i] != -60)
				return null;
			int blockEndIndex = i + dd[i + 1] + 2;
			if (blockEndIndex > length)
				return null;
			try {
				byte temp[] = new byte[blockEndIndex - i];
				System.arraycopy(dd, i, temp, 0, blockEndIndex - i);
				ss.add(blockNumber, new String(Hex.encode(temp)));
			} catch (Exception e) {
				// e.printStackTrace();
				log.error("encode error" + e.getMessage());
			}
			i = i + dd[i + 1] + 2;
			blockNumber++;
		}

		String result[] = new String[blockNumber];
		for (int i = 0; i < blockNumber; i++)
			result[i] = (String) ss.get(i);

		return result;
	}

	private NativeByteString jsFunction_loadByName(Context cx,
			Scriptable scope, Object args[]) {
		Number SW = new Integer(36864);
		if (!(args[0] instanceof NativeByteString))
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1035)).toString());
		String strName = ((NativeByteString) args[0]).toString();
		byte bName[] = Hex.decode(strName);
		String filePathName = Hex.HexToASC(bName, 0, bName.length);
		log.debug("filePathName=" + filePathName);
		String strFileContent = "";
		try {
			strFileContent = extractJarFileAndGetContent(filePathName);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
			throw new EvaluatorException((new GPError("GPSecurityDomain", 0, 0,
					"extract jar file error!")).toString());
		}
		log.debug("content=" + strFileContent);
		NativeByteString byteContent = new NativeByteString(strFileContent,
				new Integer(1000));
		String arrayBlockFromFile[] = null;
		arrayBlockFromFile = parseFileStructure(byteContent);
		int numBlockFromPara = (args.length - 1) / 2;
		String arrayBlock[] = new String[arrayBlockFromFile.length
				+ numBlockFromPara];
		System.arraycopy(arrayBlockFromFile, 0, arrayBlock, 0,
				arrayBlockFromFile.length);
		for (int j = 0; j < numBlockFromPara; j++) {
			NativeByteString bsAID = (NativeByteString) args[j * 2 + 1];
			NativeByteString bsDAP = (NativeByteString) args[j * 2 + 2];
			byte bAID[] = new byte[bsAID.GetLength() + 1];
			byte bDAP[] = new byte[bsDAP.GetLength() + 1];
			byte block[] = new byte[bsAID.GetLength() + 1 + bsDAP.GetLength()
					+ 1 + 2];
			bAID[0] = (byte) bsAID.GetLength();
			bDAP[0] = (byte) bsDAP.GetLength();
			if (j < numBlockFromPara - 1)
				block[0] = -30;
			else
				block[0] = -60;
			block[1] = (byte) (bsAID.GetLength() + 1 + bsDAP.GetLength() + 1);
			for (int i = 0; i < bsAID.GetLength(); i++)
				bAID[i + 1] = bsAID.ByteAt(i);

			for (int i = 0; i < bsDAP.GetLength(); i++)
				bDAP[i + 1] = bsDAP.ByteAt(i);

			System.arraycopy(bDAP, 0, block, 2, bDAP.length);
			System.arraycopy(bAID, 0, block, 2 + bDAP.length, bAID.length);
			arrayBlock[arrayBlockFromFile.length + j] = new String(Hex
					.encode(block));
		}

		for (int j = 0; j < arrayBlock.length; j++)
			log.debug("arrayBlock[" + j + "]=" + arrayBlock[j]);

		for (int i = 0; i < arrayBlock.length; i++) {
			log.debug("arrayBlock[" + i + "]=" + arrayBlock[i]);
			String strCommData = arrayBlock[i];
			NativeByteString comData = new NativeByteString(strCommData,
					new Integer(1000));
			if (i < arrayBlock.length - 1)
				wrapApdu(cx, scope, new Integer(128), new Integer(232),
						new Integer(0), new Integer(i), comData,
						new Integer(-1), SW);
			else
				wrapApdu(cx, scope, new Integer(128), new Integer(232),
						new Integer(128), new Integer(i), comData, new Integer(
								-1), SW);
		}

		NativeByteString sNew = card.response;
		return sNew;
	}

	private String extractJarFileAndGetContent(String filePathName) {
		if (!filePathName.endsWith(".jar") && !filePathName.endsWith(".cap"))
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1035)).toString());
		File f = new File(filePathName);
		if (!f.exists())
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1035)).toString());
		String strFileContent = "";
		try {
			int BUFFER = 2048;
			BufferedOutputStream dest = null;
			FileInputStream fI = new FileInputStream(f);
			JarInputStream jis = new JarInputStream(new BufferedInputStream(fI));
			JarEntry jarentry;
			while ((jarentry = jis.getNextJarEntry()) != null) {
				log.debug("Extracting: " + jarentry);
				byte data[] = new byte[BUFFER];
				FileOutputStream fos = new FileOutputStream(jarentry.getName());
				dest = new BufferedOutputStream(fos, BUFFER);
				int i;
				while ((i = jis.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, i);
					strFileContent = strFileContent.concat(Hex.HexToASC(data,
							0, i));
				}
				dest.flush();
				dest.close();
			}
			jis.close();
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("file error");
			throw new EvaluatorException((new GPError("GPSecurityDomain", 0, 0,
					"extract jar file error")).toString());
		}
		return strFileContent;
	}

	private NativeByteString jsFunction_select(Context cx, Scriptable scope,
			Object args[]) {
		String strData = "A0000000031010";
		if (args.length == 1) {
			strData = (String) args[0];
		}
		Number P2 = new Integer(0);
		Number SW = new Integer(36864);
		if (args.length == 2) {
			if (!(args[1] instanceof Boolean))
				throw new EvaluatorException((new GPError("GPSecurityDoamin",
						1035)).toString());
			if (((Boolean) args[1]).booleanValue())
				P2 = new Integer(2);
			else
				P2 = new Integer(0);
		} else if (args.length == 3) {
			if (!(args[0] instanceof Boolean) || !(args[1] instanceof Number))
				throw new EvaluatorException((new GPError("GPSecurityDoamin",
						1035)).toString());
			if (((Boolean) args[1]).booleanValue())
				P2 = new Integer(2);
			else
				P2 = new Integer(0);
			SW = (Number) args[2];
		}
		NativeByteString comData = new NativeByteString(strData, new Integer(
				1000));
		wrapApdu(cx, scope, new Integer(0), new Integer(164), new Integer(4),
				P2, comData, new Integer(-1), SW);
		NativeByteString sNew = card.response;
		return sNew;
	}

	private NativeByteString jsFunction_deleteAID(Context cx, Scriptable scope,
			Object args[]) {
		Number SW = new Integer(36864);
		NativeByteString AID;
		if (args.length == 1) {
			if (!(args[0] instanceof NativeByteString))
				throw new EvaluatorException((new GPError("GPSecurityDoamin",
						1035)).toString());
			AID = (NativeByteString) args[0];
		} else if (args.length == 2) {
			if (!(args[0] instanceof NativeByteString)
					|| !(args[1] instanceof Number))
				throw new EvaluatorException((new GPError("GPSecurityDoamin",
						1035)).toString());
			AID = (NativeByteString) args[0];
			SW = (Number) args[1];
		} else {
			throw new EvaluatorException(
					(new GPError("GPSecurityDoamin", 1032)).toString());
		}
		String L = Hex.byteToString((byte) AID.GetLength());
		String strData = "4F" + L + AID;
		NativeByteString comData = new NativeByteString(strData, new Integer(
				1000));
		wrapApdu(cx, scope, new Integer(128), new Integer(228), new Integer(0),
				new Integer(0), comData, new Integer(-1), SW);
		NativeByteString sNew = card.response;
		return sNew;
	}

	private NativeByteString jsFunction_putKey(Context cx, Scriptable scope,
			Object args[]) {
		Boolean More = new Boolean(false);
		Number SW = new Integer(36864);
		if (args.length % 3 == 1) {
			if (!(args[args.length - 1] instanceof Boolean))
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						1035)).toString());
			More = (Boolean) args[args.length - 1];
		}
		if (args.length % 3 == 2) {
			if (!(args[args.length - 2] instanceof Boolean)
					|| !(args[args.length - 1] instanceof Number))
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						1035)).toString());
			More = (Boolean) args[args.length - 2];
			SW = (Number) args[args.length - 1];
		}
		if (!(args[0] instanceof Number) || !(args[1] instanceof Number)
				|| !(args[2] instanceof Number))
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1035)).toString());
		for (int i = 1; i < args.length / 3; i++)
			if (!(args[i * 3] instanceof Number)
					|| !(args[i * 3 + 1] instanceof NativeKey)
					|| !(args[i * 3 + 2] instanceof NativeByteString))
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						1035)).toString());

		Number P1 = new Integer(0);
		Number P2 = new Integer(0);
		if (More.booleanValue())
			P1 = new Integer(((Number) args[0]).intValue() | 0x80);
		else
			P1 = new Integer(((Number) args[0]).intValue() & 0x7f);
		if (args.length >= 9)
			P2 = new Integer(((Number) args[2]).intValue() | 0x80);
		else
			P2 = new Integer(((Number) args[2]).intValue());
		int newVersion = ((Number) args[1]).intValue();
		String strNewVersion = Hex.byteToString((byte) newVersion);
		String strData = strNewVersion;
		for (int i = 1; i < args.length / 3; i++) {
			int type = ((Number) args[i * 3]).intValue();
			NativeByteString keyBlob = ((NativeKey) args[i * 3 + 1]).getBlob();
			NativeByteString encryptedKey;
			if (secureChannel instanceof NativeGPScp01)
				encryptedKey = ((NativeGPScp01) secureChannel).encryptKek(cx,
						scope, keyBlob);
			else
				encryptedKey = ((NativeGPScp02) secureChannel).encryptKek(cx,
						scope, keyBlob);
			String strEncryptedKey = encryptedKey.toString();
			String kcv = ((NativeByteString) args[i * 3 + 2]).toString();
			String strType = Hex.byteToString((byte) type);
			String strLKey = Hex
					.byteToString((byte) (strEncryptedKey.length() / 2));
			kcv = kcv.substring(0, 6);
			strData = strData + strType + strLKey + strEncryptedKey + "03"
					+ kcv;
		}

		NativeByteString comData = new NativeByteString(strData, new Integer(
				GPConstant.HEX));

		// send command
		// change INS from oxD8 to test watchdata card
		this.securityDomain.wrapApdu(cx, scope, new Integer(0x80), new Integer(
				0xD8), P1, P2, comData, new Integer(-1), SW);

		NativeByteString sNew = card.response;
		return sNew;
	}
	
    private void jsFunction_setPan(Context cx, Scriptable scope,Object[] args)
    {
        if(args[0] instanceof String)
        {
            this.pan=(String)args[0];
            
        }
        else
            throw new EvaluatorException( ( new GPError("GPScp02", GPError.INVALID_TYPE)).toString());
    }
	
	/**
	 * putKey的三把密钥均为采用lms保护的
	 * @param @param cx
	 * @param @param scope
	 * @param @param args
	 * @return void
	 * @throws Exception
	 */
	private NativeByteString jsFunction_putKeyLmk(Context cx, Scriptable scope,
			Object args[]) {
		Boolean More = new Boolean(false);
		Number SW = new Integer(36864);
		if (args.length % 3 == 1) {
			if (!(args[args.length - 1] instanceof Boolean))
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						1035)).toString());
			More = (Boolean) args[args.length - 1];
		}
		if (args.length % 3 == 2) {
			if (!(args[args.length - 2] instanceof Boolean)
					|| !(args[args.length - 1] instanceof Number))
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						1035)).toString());
			More = (Boolean) args[args.length - 2];
			SW = (Number) args[args.length - 1];
		}
		if (!(args[0] instanceof Number) || !(args[1] instanceof Number)
				|| !(args[2] instanceof Number))
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1035)).toString());
		for (int i = 1; i < args.length / 3; i++)
			if (!(args[i * 3] instanceof Number)
					|| !(args[i * 3 + 1] instanceof NativeKey)
					|| !(args[i * 3 + 2] instanceof NativeByteString))
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						1035)).toString());

		Number P1 = new Integer(0);
		Number P2 = new Integer(0);
		if (More.booleanValue())
			P1 = new Integer(((Number) args[0]).intValue() | 0x80);
		else
			P1 = new Integer(((Number) args[0]).intValue() & 0x7f);
		if (args.length >= 9)
			P2 = new Integer(((Number) args[2]).intValue() | 0x80);
		else
			P2 = new Integer(((Number) args[2]).intValue());
		int newVersion = ((Number) args[1]).intValue();
		String strNewVersion = Hex.byteToString((byte) newVersion);
		String strData = strNewVersion;
		for (int i = 1; i < args.length / 3; i++) {
			int type = ((Number) args[i * 3]).intValue();
			NativeByteString keyBlob = ((NativeKey) args[i * 3 + 1]).getBlob();
			NativeByteString encryptedKey;
			log.debug("keyBlob:" + keyBlob);
			if (secureChannel instanceof NativeGPScp01)
				encryptedKey = ((NativeGPScp01) secureChannel).encryptKekLmk(cx,
						scope, keyBlob);
			else
				encryptedKey = ((NativeGPScp02) secureChannel).encryptKekLmk(cx,
						scope, keyBlob);
			String strEncryptedKey = encryptedKey.toString();
			log.debug("encrypto key:" + strEncryptedKey);
			Integer ee = new Integer(GPConstant.HEX);
			NativeByteString p4 = new NativeByteString("0000000000000000", ee);
			log.debug("MingwenKSCkeka:" + getSecureChannelMacKey(cx,scope).strBlob);
			String strDecryptedKey = NativeCrypto.decrypt(getSecureChannelMacKey(cx,scope) , new Integer(NativeCrypto.DES_ECB), new NativeByteString(strEncryptedKey,ee),p4).toString();
			log.debug("decrypto key:" + strDecryptedKey);
			
			String kcv = NativeKey.getKcv(strDecryptedKey);
			log.debug("KCV:" + kcv);
			String strType = Hex.byteToString((byte) type);
			String strLKey = Hex
					.byteToString((byte) (strEncryptedKey.length() / 2));
			kcv = kcv.substring(0, 6);
			strData = strData + strType + strLKey + strEncryptedKey + "03"
					+ kcv;
			log.debug("strData:" + strData);
		}

		NativeByteString comData = new NativeByteString(strData, new Integer(
				GPConstant.HEX));

		// send command
		// change INS from oxD8 to test watchdata card
		this.securityDomain.wrapApdu(cx, scope, new Integer(0x80), new Integer(
				0xD8), P1, P2, comData, new Integer(-1), SW );

		NativeByteString sNew = card.response;
		return sNew;
	}
	/**天津农行定制
	 * putKey的三把密钥是session key加密过的。
	 * @param @param cx
	 * @param @param scope
	 * @param @param args
	 * @return void
	 * @throws Exception
	 */
	private NativeByteString jsFunction_putKeyStd(Context cx, Scriptable scope,
			Object args[]) {
		Boolean More = new Boolean(false);
		Number SW = new Integer(36864);
		if (args.length % 3 == 1) {
			if (!(args[args.length - 1] instanceof Boolean))
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						1035)).toString());
			More = (Boolean) args[args.length - 1];
		}
		if (args.length % 3 == 2) {
			if (!(args[args.length - 2] instanceof Boolean)
					|| !(args[args.length - 1] instanceof Number))
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						1035)).toString());
			More = (Boolean) args[args.length - 2];
			SW = (Number) args[args.length - 1];
		}
		if (!(args[0] instanceof Number) || !(args[1] instanceof Number)
				|| !(args[2] instanceof Number))
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1035)).toString());
		for (int i = 1; i < args.length / 3; i++)
			if (!(args[i * 3] instanceof Number)
					|| !(args[i * 3 + 1] instanceof NativeKey)
					|| !(args[i * 3 + 2] instanceof NativeByteString))
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						1035)).toString());

		Number P1 = new Integer(0);
		Number P2 = new Integer(0);
		if (More.booleanValue())
			P1 = new Integer(((Number) args[0]).intValue() | 0x80);
		else
			P1 = new Integer(((Number) args[0]).intValue() & 0x7f);
		if (args.length >= 9)
			P2 = new Integer(((Number) args[2]).intValue() | 0x80);
		else
			P2 = new Integer(((Number) args[2]).intValue());
		int newVersion = ((Number) args[1]).intValue();
		String strNewVersion = Hex.byteToString((byte) newVersion);
		String strData = strNewVersion;
		for (int i = 1; i < args.length / 3; i++) {
			int type = ((Number) args[i * 3]).intValue();
			NativeByteString keyBlob = ((NativeKey) args[i * 3 + 1]).getBlob();
			String strEncryptedKey = keyBlob.toString();
			String kcv = ((NativeByteString) args[i * 3 + 2]).toString();
			String strType = Hex.byteToString((byte) type);
			String strLKey = Hex
					.byteToString((byte) (strEncryptedKey.length() / 2));
			kcv = kcv.substring(0, 6);
			strData = strData + strType + strLKey + strEncryptedKey + "03"
					+ kcv;
		}

		NativeByteString comData = new NativeByteString(strData, new Integer(
				GPConstant.HEX));

		// send command
		// change INS from oxD8 to test watchdata card
		this.securityDomain.wrapApduLmk(cx, scope, new Integer(0x80), new Integer(
				0xD8), P1, P2, comData, new Integer(-1), SW);

		NativeByteString sNew = card.response;
		return sNew;
	}
	private void jsFunction_setStatus(Context cx, Scriptable scope,
			Object args[]) {
		Number P2 = new Integer(0);
		Number SW = new Integer(36864);
		NativeByteString AID;
		if (args.length == 2) {
			if (!(args[0] instanceof NativeByteString)
					|| !(args[1] instanceof Number))
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						1035)).toString());
			AID = (NativeByteString) args[0];
			P2 = (Number) args[1];
		} else if (args.length == 3) {
			if (!(args[0] instanceof NativeByteString)
					|| !(args[1] instanceof Number)
					|| !(args[2] instanceof Number))
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						1035)).toString());
			AID = (NativeByteString) args[0];
			P2 = (Number) args[1];
			SW = (Number) args[2];
		} else {
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1035)).toString());
		}
		NativeByteString comData = AID;
		wrapApdu(cx, scope, new Integer(128), new Integer(240),
				new Integer(128), P2, comData, new Integer(-1), SW);
	}

	/**
	 *农行,session key is lmk
	 * @param cx
	 * @param scope
	 * @param args
	 */
	private void jsFunction_storeDataLmk(Context cx, Scriptable scope,
			Object[] args) {
		Number Last = new Integer(0x00);
		Number SW = new Integer(0x9000);
		Number Init = new Integer(0x00);
		String pan = "";
		if (!(args[0] instanceof NativeByteString))
			throw new EvaluatorException((new GPError("GPSecurityDomain",
					GPError.INVALID_TYPE)).toString());
		if (args.length == 2) {
			if (!(args[1] instanceof Number))
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						GPError.INVALID_TYPE)).toString());
			Last = (Number) args[1];
		} else if (args.length == 3) {
			if ((!(args[1] instanceof Number))
					|| (!(args[2] instanceof Number)))
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						GPError.INVALID_TYPE)).toString());
			Last = (Number) args[1];
			SW = (Number) args[2];
		} else if (args.length == 4) {
			if ((!(args[1] instanceof Number))
					|| (!(args[2] instanceof Number))
					|| (!(args[3] instanceof Number)))
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						GPError.INVALID_TYPE)).toString());
			Last = (Number) args[1];
			SW = (Number) args[2];
			Init = (Number) args[3];
			// p2_storeData=Init.intValue();
		}

		Number P1 = new Integer(0x00);
		Number P2 = new Integer(0x00);

		// *************************************************************************
		// we don't know how to deal with the encrypted DGI(1101,9102,)
		// so we use inti parameter to express encrypted DGI
		/*
		 * if(args.length==4) { if(Last.booleanValue()==true) P1=new
		 * Integer(Init.intValue()); else P1=new Integer(Init.intValue()); }
		 * else {
		 */
		// **************************************************************************
		if (Last.intValue() == 1) { // the last block
			P1 = new Integer(0x80);
		} else if (Last.intValue() == 2) {
			P1 = new Integer(0x60);
		} else if (Last.intValue() == 3) {
			P1 = new Integer(0x20);
		} 
		else
			P1 = new Integer(0x00);
		// }

		P2 = new Integer(p2_storeData);

		NativeByteString comData = (NativeByteString) args[0];

		// send command
		wrapApduLmk(cx, scope, new Integer(0x80), new Integer(0xE2), P1, P2,
				comData, new Integer(-1), SW);

		// update the p2
		if (Last.intValue() == 1)
			p2_storeData = Init.intValue();
		else
			p2_storeData++;

	}

	
	private void jsFunction_storeData(Context cx, Scriptable scope,
			Object[] args) {
		Number Last = new Integer(0x00);
		Number SW = new Integer(0x9000);
		Number Init = new Integer(0x00);
		if (!(args[0] instanceof NativeByteString))
			throw new EvaluatorException((new GPError("GPSecurityDomain",
					GPError.INVALID_TYPE)).toString());
		if (args.length == 2) {
			if (!(args[1] instanceof Number))
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						GPError.INVALID_TYPE)).toString());
			Last = (Number) args[1];
		} else if (args.length == 3) {
			if ((!(args[1] instanceof Number))
					|| (!(args[2] instanceof Number)))
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						GPError.INVALID_TYPE)).toString());
			Last = (Number) args[1];
			SW = (Number) args[2];
		} else if (args.length == 4) {
			if ((!(args[1] instanceof Number))
					|| (!(args[2] instanceof Number))
					|| (!(args[3] instanceof Number)))
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						GPError.INVALID_TYPE)).toString());
			Last = (Number) args[1];
			SW = (Number) args[2];
			Init = (Number) args[3];
			//p2_storeData=Init.intValue();
		}

		Number P1 = new Integer(0x00);
		Number P2 = new Integer(0x00);

		// *************************************************************************
		// we don't know how to deal with the encrypted DGI(1101,9102,)
		// so we use inti parameter to express encrypted DGI
		/*
		 * if(args.length==4) { if(Last.booleanValue()==true) P1=new
		 * Integer(Init.intValue()); else P1=new Integer(Init.intValue()); }
		 * else {
		 */
		// **************************************************************************
		if (Last.intValue() == 1) { // the last block
			P1 = new Integer(0x80);
		} else if (Last.intValue() == 2) {
			P1 = new Integer(0x60);
		} else if (Last.intValue() == 3) {
			P1 = new Integer(0x20);
		} 
		else
			P1 = new Integer(0x00);
		// }

		P2 = new Integer(p2_storeData);

		NativeByteString comData = (NativeByteString) args[0];

		// send command
		wrapApdu(cx, scope, new Integer(0x80), new Integer(0xE2), P1, P2,
				comData, new Integer(-1), SW);

		// update the p2
		if (Last.intValue() == 1)
			p2_storeData = Init.intValue();
		else
			p2_storeData++;

	}
	private void jsFunction_storeEmvData(Context cx, Scriptable scope,
			Object args[]) {
		Boolean Last = new Boolean(false);
		Number SW = new Integer(36864);
		Number Init = new Integer(0);
		if (!(args[0] instanceof NativeByteString))
			throw new EvaluatorException(
					(new GPError("GPSecurityDomain", 1035)).toString());
		if (args.length == 2) {
			if (!(args[1] instanceof Boolean))
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						1035)).toString());
			Last = (Boolean) args[1];
		} else if (args.length == 3) {
			if (!(args[1] instanceof Boolean) || !(args[2] instanceof Number))
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						1035)).toString());
			Last = (Boolean) args[1];
			SW = (Number) args[2];
		} else if (args.length == 4) {
			if (!(args[1] instanceof Boolean) || !(args[2] instanceof Number)
					|| !(args[3] instanceof Number))
				throw new EvaluatorException((new GPError("GPSecurityDomain",
						1035)).toString());
			Last = (Boolean) args[1];
			SW = (Number) args[2];
			Init = (Number) args[3];
		}
		Number P1 = new Integer(0);
		Number P2 = new Integer(0);
		if (Last.booleanValue())
			P1 = new Integer(128);
		else
			P1 = new Integer(0);
		P2 = new Integer(p2_storeData);
		NativeByteString comData = (NativeByteString) args[0];
		wrapApdu(cx, scope, new Integer(128), new Integer(226), P1, P2,
				comData, new Integer(-1), SW);
		if (Last.booleanValue())
			p2_storeData = Init.intValue();
		else
			p2_storeData++;
	}

	public byte[] stringToByteArray(String str) {
		byte byteArray[] = new byte[str.length() / 2];
		int val = Integer.parseInt(str, 10);
		for (int i = 0; i < byteArray.length; i++)
			byteArray[i] = (byte) (val >>> (byteArray.length - i) * 8 & 0xff);

		return byteArray;
	}

	private Number jsFunction_openSecureChannel(Context cx, Scriptable scope,
			Object args[]) {
		int level = ((Number) args[0]).intValue();
		String strLevel = ((Number) args[0]).toString();
		String strSecureChannel = "level=" + strLevel + ";"
				+ profile.SecureChannel.OpenSecureChannel.Script.Script;
		Object result = cx.evaluateString(scope, strSecureChannel,
				"OpenSecureChannel", 0, null);
		secureLevel = level;
		return new Integer(level);
	}

	public void jsFunction_closeSecureChannel(Context cx, Scriptable scope,
			Object args[]) {
		int level = 0;
		if (args.length > 0) {
			level = ((Number) args[0]).intValue();
		}
		secureLevel = level;
		log.debug("secureLevel=" + secureLevel);
	}

	protected int maxInstanceId() {
		return 8;
	}

	protected void initPrototypeId(int id) {
		String s;
		int arity;
		switch (id) {
		case 1: // '\001'
			arity = 7;
			s = "sendApdu";
			break;

		case 2: // '\002'
			arity = 2;
			s = "getData";
			break;

		case 3: // '\003'
			arity = 4;
			s = "getStatus";
			break;

		case 4: // '\004'
			arity = 4;
			s = "installForExtradition";
			break;

		case 5: // '\005'
			arity = 6;
			s = "installForInstall";
			break;

		case 6: // '\006'
			arity = 6;
			s = "installForInstallAndSelectable";
			break;

		case 7: // '\007'
			arity = 6;
			s = "installForLoad";
			break;

		case 8: // '\b'
			arity = 2;
			s = "installForPersonalization";
			break;

		case 9: // '\t'
			arity = 4;
			s = "load";
			break;

		case 10: // '\n'
			arity = 2;
			s = "loadByName";
			break;

		case 12: // '\f'
			arity = 3;
			s = "select";
			break;

		case 13: // '\r'
			arity = 2;
			s = "deleteAID";
			break;

		case 14: // '\016'
			arity = 6;
			s = "putKey";
			break;

		case 15: // '\017'
			arity = 3;
			s = "setStatus";
			break;

		case 16: // '\020'
			arity = 3;
			s = "storeData";
			break;

		case 17: // '\021'
			arity = 3;
			s = "storeEmvData";
			break;

		case 11: // '\013'
			arity = 0;
			s = "openSecureChannel";
			break;
		case 18: // '\013'
			arity = 0;
			s = "closeSecureChannel";
			break;
		case 20:
			arity = 6;
			s = "putKeyLmk";
			break;
		case 21:
			arity = 6;
			s = "putKeyStd";
			break;
		case 22:
			arity = 5;
			s = "storeDataLmk";
			break;
		case 23:
			arity = 1;
			s = "setPan";
			break;
		default:
			throw new IllegalArgumentException(String.valueOf(id));
		}
		initPrototypeMethod(SECURITYDOMAIN_TAG, id, s, arity);
	}

	protected int findPrototypeId(String s) {
		int id = 0;
		String X = null;
		if (s.equals("key")) {
			X = "key";
			id = 4;
		}
		if (s.equals("data")) {
			X = "data";
			id = 5;
		}
		if (s.equals("card")) {
			X = "card";
			id = 7;
		}
		if (s.equals("load")) {
			X = "load";
			id = 9;
		}
		if (s.equals("crypto")) {
			X = "crypto";
			id = 3;
		}
		if (s.equals("hsm")) {
			X = "hsm";
			id = 8;
		}
		if (s.equals("select")) {
			X = "select";
			id = 12;
		}
		if (s.equals("putKey")) {
			X = "putKey";
			id = 14;
		}
		if (s.equals("profile")) {
			X = "profile";
			id = 6;
		}
		if (s.equals("getData")) {
			X = "getData";
			id = 2;
		}
		if (s.equals("sendApdu")) {
			X = "sendApdu";
			id = 1;
		}
		if (s.equals("getStatus")) {
			X = "getStatus";
			id = 3;
		}
		if (s.equals("deleteAID")) {
			X = "deleteAID";
			id = 13;
		}
		if (s.equals("setStatus")) {
			X = "setStatus";
			id = 15;
		}
		if (s.equals("storeData")) {
			X = "storeData";
			id = 16;
		}
		if (s.equals("storeEmvData")) {
			X = "storeEmvData";
			id = 17;
		}
		if (s.equals("loadByName")) {
			X = "loadByName";
			id = 10;
		}
		if (s.equals("secureChannel")) {
			X = "secureChannel";
			id = 1;
		}
		if (s.equals("securityDomain")) {
			X = "securityDomain";
			id = 2;
		}
		if (s.equals("installForLoad")) {
			X = "installForLoad";
			id = 7;
		}
		if (s.equals("installForInstall")) {
			X = "installForInstall";
			id = 5;
		}
		if (s.equals("openSecureChannel")) {
			X = "openSecureChannel";
			id = 11;
		}
		if (s.equals("closeSecureChannel")) {
			X = "closeSecureChannel";
			id = 18;
		}
		if (s.equals("installForExtradition")) {
			X = "installForExtradition";
			id = 4;
		}
		if (s.equals("installForPersonalization")) {
			X = "installForPersonalization";
			id = 8;
		}
		if (s.equals("installForInstallAndSelectable")) {
			X = "installForInstallAndSelectable";
			id = 6;
		}
		if (s.equals("putKeyLmk")) {
			X = "putKeyLmk";
			id = 20;
		}
		if (s.equals("putKeyStd")) {
			X = "putKeyStd";
			id = 21;
		}
		if (s.equals("storeDataLmk")) {
			X = "storeDataLmk";
			id = 22;
		}
		if (s.equals("setPan")) {
			X = "setPan";
			id = 23;
		}
		return id;
	}

	/**
	 * Wrap Apdu with secureLevel, this function should be realized in GP
	 * Profile by Wrap scriptfragment accroding to GP Script and Profile
	 * specification. so it is not a corrent soluation. only for version
	 * inherit.
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
	public NativeByteString wrapApdu(Context cx, Scriptable scope, Number p1,
			Number p2, Number p3, Number p4, NativeByteString p5, Number p6,
			Number p7) {
		int CLA, INS, PP1, PP2, LE, sw;
		CLA = (int) p1.intValue();
		INS = (int) p2.intValue();
		PP1 = (int) p3.intValue();
		PP2 = (int) p4.intValue();
		LE = (int) p6.intValue();
		sw = (int) p7.intValue();

		// get comdata
		byte[] data = null;
		int LC = 0;
		if ((p5 != null) && (p5 instanceof NativeByteString) && p5.length > 0) {
			LC = p5.GetLength();
			data = new byte[LC];
			for (int i = 0; i < LC; i++)
				data[i] = p5.ByteAt(i);
		}

		// deal with command data according to the securityLevel
		byte[] theLastCommData = null;
		byte[] macData = null;
		byte[] macKey = new byte[16];
		byte[] macResult = new byte[8];
		byte[] IV = { 0, 0, 0, 0, 0, 0, 0, 0 };
		Integer ee = new Integer(GPConstant.HEX);
		byte[] encResult = null;

		// the select command (00 A4....) is special (can't be compute MAC or
		// DES)
		if ((secureLevel == GPConstant.NO_SECUREITY_LEVEL)
				|| ((CLA == 0x00) && (INS == 0xA4))) {
			if (data == null)
				theLastCommData = null;
			else {
				theLastCommData = new byte[data.length];
				System.arraycopy(data, 0, theLastCommData, 0, data.length);
			}
		} else {
			switch (secureLevel) {
			/*
			 * case NO_SECUREITY_LEVEL: if(data==null) theLastCommData=null;
			 * else { theLastCommData=new byte[data.length];
			 * System.arraycopy(data,0,theLastCommData,0,data.length); } break;
			 */
			case GPConstant.C_MAC:
				CLA = (CLA & 0xF0) | 0x04;
				macData = new byte[LC + 5];
				macData[0] = (byte) CLA;
				macData[1] = (byte) INS;
				macData[2] = (byte) PP1;
				macData[3] = (byte) PP2;
				macData[4] = (byte) (LC + 8);
				if (data != null)
					System.arraycopy(data, 0, macData, 5, data.length);
				try {
					macKey = Hex.decode(getSecureChannelMacKey(cx, scope)
							.getBlob().toString());
					log.debug("Key=" + new String(Hex.encode(macKey)));
				} catch (Exception err) {
					// err.printStackTrace();
					log.error(err.getMessage());
				}
				try {
					IV = Hex.decode(getSecureChannelSmac(cx, scope));
					log.debug("macData=" + new String(Hex.encode(macData)));
					log.debug("IV=" + new String(Hex.encode(IV)));
					byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0 };
					String enIv = CryptoUtil.singleDESEncrypt(new String(Hex
							.encode(macKey)), new String(Hex.encode(IV)));
					log.debug("加密后的IV=" + enIv);
					IV = Hex.decode(enIv);
					log.debug("Hex.decode(enIv)" + enIv);
					macResult = Hex.decode((NativeCrypto.sign(
							getSecureChannelMacKey(cx, scope), new Integer(
									NativeCrypto.DES_MAC_EMV),
							new NativeByteString(macData),
							new NativeByteString(IV))).toString());

					// macResult = Crypto.DESsign(macKey, Crypto.DES_MAC,
					// macData, IV);
					setSecureChannelSmac(cx, scope, new String(Hex
							.encode(macResult)));
					log.debug("macResult=" + new String(Hex.encode(macResult)));
				} catch (Exception e) {
					// e.printStackTrace();
					log.error(e.getMessage());
				}
				if (data != null) {
					theLastCommData = new byte[data.length + 8];
					System.arraycopy(data, 0, theLastCommData, 0, data.length);
					System.arraycopy(macResult, 0, theLastCommData,
							data.length, 8);
				} else {
					theLastCommData = new byte[8];
					System.arraycopy(macResult, 0, theLastCommData, 0, 8);
				}
				break;
			case GPConstant.C_MAC_ENC: // comput MAC first, then encrypt
				CLA = (CLA & 0xF0) | 0x04;
				macData = new byte[LC + 5];
				macData[0] = (byte) CLA;
				macData[1] = (byte) INS;
				macData[2] = (byte) PP1;
				macData[3] = (byte) PP2;
				macData[4] = (byte) (LC + 8);
				if (data != null)
					System.arraycopy(data, 0, macData, 5, data.length);
				try {
					macKey = Hex.decode(getSecureChannelMacKey(cx, scope)
							.getBlob().toString());
					log.debug("Key=" + new String(Hex.encode(macKey)));
				} catch (Exception err) {
					// err.printStackTrace();
					log.error(err.getMessage());
				}
				try {
					IV = Hex.decode(getSecureChannelSmac(cx, scope));
					log.debug("macData=" + new String(Hex.encode(macData)));
					log.debug("IV=" + new String(Hex.encode(IV)));
					byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0 };
					String enIv = CryptoUtil.singleDESEncrypt(new String(Hex
							.encode(macKey)), new String(Hex.encode(IV)));
					log.debug("加密后的IV=" + enIv);
					IV = Hex.decode(enIv);

					macResult = Hex.decode((NativeCrypto.sign(
							getSecureChannelMacKey(cx, scope), new Integer(
									NativeCrypto.DES_MAC_EMV),
							new NativeByteString(macData),
							new NativeByteString(IV))).toString());
					// macResult = Crypto.DESsign(macKey, Crypto.DES_MAC,
					// macData, IV);
					setSecureChannelSmac(cx, scope, new String(Hex
							.encode(macResult)));
					log.debug("macResult=" + new String(Hex.encode(macResult)));
				} catch (Exception e) {
					// e.printStackTrace();
					log.error(e.getMessage());
				}

				// macResult[] is OK now
				// DES(L+data+padding data) if data is not null
				if (data != null) {
					int L = data.length;
					int desLength = 0;
					if (((L) % 8) == 0)
						desLength = L + 8;
					else
						desLength = (L) + 8 - (L) % 8;
					byte[] dataToEncrypt = new byte[desLength];
					dataToEncrypt[0] = (byte) L;
					log.debug("data:" + new String(Hex.encode(data)));
					System.arraycopy(data, 0, dataToEncrypt, 0, L);
					byte[] paddingData = { (byte) 0x80, 0, 0, 0, 0, 0, 0, 0 };
					if (((L) % 8) != 0) {
						System.arraycopy(paddingData, 0, dataToEncrypt, L,
								desLength - L);
					} else {
						System.arraycopy(paddingData, 0, dataToEncrypt, L,
								desLength - L);
					}

					byte[] encKey = new byte[16];
					try {
						encKey = Hex.decode(getSecureChannelEncKey(cx, scope)
								.getBlob().toString());
						log.debug("encKey=" + new String(Hex.encode(encKey)));
					} catch (Exception err) {
						// err.printStackTrace();
						log.error(err.getMessage());
					}
					byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0 };
					try {
						log.debug("encdata="
								+ new String(Hex.encode(dataToEncrypt)));
						encResult = Hex.decode(NativeCrypto.encrypt(
								getSecureChannelEncKey(cx, scope),
								new Integer(NativeCrypto.DES_CBC),
								new NativeByteString(dataToEncrypt),
								new NativeByteString(iv)).toString());
						log.debug("encResult="
								+ new String(Hex.encode(encResult)));
						// encResult = Crypto.encrypt(encKey, Crypto.DES_CBC,
						// dataToEncrypt, iv);
					} catch (Exception e) {
						e.printStackTrace();
						throw new EvaluatorException((new GPError("Crypto", 0,
								0, e.getMessage())).toString());
					}
				}

				// get the last command data

				if (data != null) {
					theLastCommData = new byte[encResult.length + 8];
					System.arraycopy(encResult, 0, theLastCommData, 0,
							encResult.length);
					System.arraycopy(macResult, 0, theLastCommData,
							encResult.length, 8);
				} else {
					theLastCommData = new byte[8];
					System.arraycopy(macResult, 0, theLastCommData, 0, 8);
				}
				log.debug("theLastCommData="
						+ new String(Hex.encode(theLastCommData)));
				break;
			default:
				throw new EvaluatorException((new GPError("GP_Global", 0, 0,
						"level value is invalid")).toString());
			}
		}

		// send command
		byte[] resp = new byte[500];
		resp = this.card.sendApdu(cx, scope, CLA, INS, PP1, PP2,
				theLastCommData, LE);

		if (resp[0] == 0x6C) { // 6CXX would resend command with correct length
								// when T=0
			LE = (int) resp[1];
			resp = this.card.sendApdu(cx, scope, CLA, INS, PP1, PP2, data, LE);
		}
		String strResp = new String(Hex.encode(resp));
		// exception
		this.card.updateSW(cx, scope, strResp);
		this.card.SWException(cx, "GPSecurityDomain", strResp, sw);

		// return response
		NativeByteString sNew = new NativeByteString(strResp, ee);
		return sNew;
	}

	
	/**天津农行，修改计算mac的方法，sessionkey is lmk
	 * Wrap Apdu with secureLevel, this function should be realized in GP
	 * Profile by Wrap scriptfragment accroding to GP Script and Profile
	 * specification. so it is not a corrent soluation. only for version
	 * inherit.
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
	public NativeByteString wrapApduLmk(Context cx, Scriptable scope, Number p1,
			Number p2, Number p3, Number p4, NativeByteString p5, Number p6,
			Number p7) {
		int CLA, INS, PP1, PP2, LE, sw;
		CLA = (int) p1.intValue();
		INS = (int) p2.intValue();
		PP1 = (int) p3.intValue();
		PP2 = (int) p4.intValue();
		LE = (int) p6.intValue();
		sw = (int) p7.intValue();

		// get comdata
		byte[] data = null;
		int LC = 0;
		if ((p5 != null) && (p5 instanceof NativeByteString) && p5.length > 0) {
			LC = p5.GetLength();
			data = new byte[LC];
			for (int i = 0; i < LC; i++)
				data[i] = p5.ByteAt(i);
		}

		// deal with command data according to the securityLevel
		byte[] theLastCommData = null;
		byte[] macData = null;
		byte[] macKey = new byte[16];
		byte[] macResult = new byte[8];
		byte[] IV = { 0, 0, 0, 0, 0, 0, 0, 0 };
		Integer ee = new Integer(GPConstant.HEX);
		byte[] encResult = null;

		// the select command (00 A4....) is special (can't be compute MAC or
		// DES)
		if ((secureLevel == GPConstant.NO_SECUREITY_LEVEL)
				|| ((CLA == 0x00) && (INS == 0xA4))) {
			if (data == null)
				theLastCommData = null;
			else {
				theLastCommData = new byte[data.length];
				System.arraycopy(data, 0, theLastCommData, 0, data.length);
			}
		} else {
			switch (secureLevel) {
			/*
			 * case NO_SECUREITY_LEVEL: if(data==null) theLastCommData=null;
			 * else { theLastCommData=new byte[data.length];
			 * System.arraycopy(data,0,theLastCommData,0,data.length); } break;
			 */
			case GPConstant.C_MAC:
				CLA = (CLA & 0xF0) | 0x04;
				macData = new byte[LC + 5];
				macData[0] = (byte) CLA;
				macData[1] = (byte) INS;
				macData[2] = (byte) PP1;
				macData[3] = (byte) PP2;
				macData[4] = (byte) (LC + 8);
				if (data != null)
					System.arraycopy(data, 0, macData, 5, data.length);
				try {
					macKey = Hex.decode(getSecureChannelMacKey(cx, scope)
							.getBlob().toString());
					log.debug("Key=" + new String(Hex.encode(macKey)));
				} catch (Exception err) {
					// err.printStackTrace();
					log.error(err.getMessage());
				}
				try {
					IV = Hex.decode(getSecureChannelSmac(cx, scope));
					log.debug("macData=" + new String(Hex.encode(macData)));
					log.debug("IV=" + new String(Hex.encode(IV)));
					//byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0 };
					/*String enIv = CryptoUtil.singleDESEncrypt(new String(Hex
							.encode(macKey)), new String(Hex.encode(IV)));*/
					System.out.println("pan:"+pan);
					NativeByteString iv = new NativeByteString("0000000000000000",ee);
					String enIv = (NativeCrypto.encryptConnByPan(new NativeByteString(getSecureChannelMacKey(cx, scope).strBlob.substring(0, 16),ee),
							NativeCrypto.ABC_DES_CBC,new NativeByteString(new String(Hex.encode(IV)),ee),iv,pan)).toString();
					log.debug("加密后的IV=" + enIv);
					/*byte[] paddedData = CryptoUtil.getEncryptBlock(getSecureChannelSmac(cx, scope));
					String enIv = (NativeCrypto.encryptConnByPan(new NativeByteString(getSecureChannelMacKey(cx, scope).strBlob.substring(0, 16),ee),
					NativeCrypto.ABC_DES_CBC,new NativeByteString(new String(Hex.encode(paddedData)),ee),iv,pan)).toString().substring(0,16);*/
					
					IV = Hex.decode(enIv);

					macResult = Hex.decode((NativeCrypto.macConnByPan(getSecureChannelMacKey(cx, scope), 1, new NativeByteString(macData), new NativeByteString(IV), pan)).toString());
					
					setSecureChannelSmac(cx, scope, new String(Hex
							.encode(macResult)));
					log.debug("macResult=" + new String(Hex.encode(macResult)));
				} catch (Exception e) {
					// e.printStackTrace();
					log.error(e.getMessage());
				}
				if (data != null) {
					theLastCommData = new byte[data.length + 8];
					System.arraycopy(data, 0, theLastCommData, 0, data.length);
					System.arraycopy(macResult, 0, theLastCommData,
							data.length, 8);
				} else {
					theLastCommData = new byte[8];
					System.arraycopy(macResult, 0, theLastCommData, 0, 8);
				}
				break;
			case GPConstant.C_MAC_ENC: // comput MAC first, then encrypt
				CLA = (CLA & 0xF0) | 0x04;
				macData = new byte[LC + 5];
				macData[0] = (byte) CLA;
				macData[1] = (byte) INS;
				macData[2] = (byte) PP1;
				macData[3] = (byte) PP2;
				macData[4] = (byte) (LC + 8);
				if (data != null)
					System.arraycopy(data, 0, macData, 5, data.length);
				try {
					macKey = Hex.decode(getSecureChannelMacKey(cx, scope)
							.getBlob().toString());
					log.debug("Key=" + new String(Hex.encode(macKey)));
				} catch (Exception err) {
					// err.printStackTrace();
					log.error(err.getMessage());
				}
				try {
					IV = Hex.decode(getSecureChannelSmac(cx, scope));
					log.debug("macData=" + new String(Hex.encode(macData)));
					log.debug("IV=" + new String(Hex.encode(IV)));
					byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0 };
					String enIv = CryptoUtil.singleDESEncrypt(new String(Hex
							.encode(macKey)), new String(Hex.encode(IV)));
					log.debug("加密后的IV=" + enIv);
					IV = Hex.decode(enIv);

					macResult = Hex.decode((NativeCrypto.sign(
							getSecureChannelMacKey(cx, scope), new Integer(
									NativeCrypto.DES_MAC_EMV),
							new NativeByteString(macData),
							new NativeByteString(IV))).toString());
					// macResult = Crypto.DESsign(macKey, Crypto.DES_MAC,
					// macData, IV);
					setSecureChannelSmac(cx, scope, new String(Hex
							.encode(macResult)));
					log.debug("macResult=" + new String(Hex.encode(macResult)));
				} catch (Exception e) {
					// e.printStackTrace();
					log.error(e.getMessage());
				}

				// macResult[] is OK now
				// DES(L+data+padding data) if data is not null
				if (data != null) {
					int L = data.length;
					int desLength = 0;
					if (((L) % 8) == 0)
						desLength = L + 8;
					else
						desLength = (L) + 8 - (L) % 8;
					byte[] dataToEncrypt = new byte[desLength];
					dataToEncrypt[0] = (byte) L;
					log.debug("data:" + new String(Hex.encode(data)));
					System.arraycopy(data, 0, dataToEncrypt, 0, L);
					byte[] paddingData = { (byte) 0x80, 0, 0, 0, 0, 0, 0, 0 };
					if (((L) % 8) != 0) {
						System.arraycopy(paddingData, 0, dataToEncrypt, L,
								desLength - L);
					} else {
						System.arraycopy(paddingData, 0, dataToEncrypt, L,
								desLength - L);
					}

					byte[] encKey = new byte[16];
					try {
						encKey = Hex.decode(getSecureChannelEncKey(cx, scope)
								.getBlob().toString());
						log.debug("encKey=" + new String(Hex.encode(encKey)));
					} catch (Exception err) {
						// err.printStackTrace();
						log.error(err.getMessage());
					}
					byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0 };
					try {
						log.debug("encdata="
								+ new String(Hex.encode(dataToEncrypt)));
						encResult = Hex.decode(NativeCrypto.encrypt(
								getSecureChannelEncKey(cx, scope),
								new Integer(NativeCrypto.DES_CBC),
								new NativeByteString(dataToEncrypt),
								new NativeByteString(iv)).toString());
						log.debug("encResult="
								+ new String(Hex.encode(encResult)));
						// encResult = Crypto.encrypt(encKey, Crypto.DES_CBC,
						// dataToEncrypt, iv);
					} catch (Exception e) {
						e.printStackTrace();
						throw new EvaluatorException((new GPError("Crypto", 0,
								0, e.getMessage())).toString());
					}
				}

				// get the last command data

				if (data != null) {
					theLastCommData = new byte[encResult.length + 8];
					System.arraycopy(encResult, 0, theLastCommData, 0,
							encResult.length);
					System.arraycopy(macResult, 0, theLastCommData,
							encResult.length, 8);
				} else {
					theLastCommData = new byte[8];
					System.arraycopy(macResult, 0, theLastCommData, 0, 8);
				}
				log.debug("theLastCommData="
						+ new String(Hex.encode(theLastCommData)));
				break;
			default:
				throw new EvaluatorException((new GPError("GP_Global", 0, 0,
						"level value is invalid")).toString());
			}
		}

		// send command
		byte[] resp = new byte[500];
		resp = this.card.sendApdu(cx, scope, CLA, INS, PP1, PP2,
				theLastCommData, LE);

		if (resp[0] == 0x6C) { // 6CXX would resend command with correct length
								// when T=0
			LE = (int) resp[1];
			resp = this.card.sendApdu(cx, scope, CLA, INS, PP1, PP2, data, LE);
		}
		String strResp = new String(Hex.encode(resp));
		// exception
		this.card.updateSW(cx, scope, strResp);
		this.card.SWException(cx, "GPSecurityDomain", strResp, sw);

		// return response
		NativeByteString sNew = new NativeByteString(strResp, ee);
		return sNew;
	}
	private NativeKey getSecureChannelMacKey(Context cx, Scriptable scope) {
		if (secureChannel instanceof NativeGPScp01)
			return ((NativeGPScp01) secureChannel).getMacKey(cx, scope);
		if (secureChannel instanceof NativeGPScp02)
			return ((NativeGPScp02) secureChannel).getMacKey(cx, scope);
		else
			return null;
	}

	private NativeKey getSecureChannelEncKey(Context cx, Scriptable scope) {
		if (secureChannel instanceof NativeGPScp01)
			return ((NativeGPScp01) secureChannel).getEncKey(cx, scope);
		if (secureChannel instanceof NativeGPScp02)
			return ((NativeGPScp02) secureChannel).getEncKey(cx, scope);
		else
			return null;
	}

	private String getSecureChannelSmac(Context cx, Scriptable scope) {
		if (secureChannel instanceof NativeGPScp01)
			return ((NativeGPScp01) secureChannel).getSmac(cx, scope);
		if (secureChannel instanceof NativeGPScp02)
			return ((NativeGPScp02) secureChannel).getSmac(cx, scope);
		else
			return "";
	}

	private void setSecureChannelSmac(Context cx, Scriptable scope, String smac) {
		if (secureChannel instanceof NativeGPScp01)
			((NativeGPScp01) secureChannel).setSmac(cx, scope, smac);
		else if (secureChannel instanceof NativeGPScp02)
			((NativeGPScp02) secureChannel).setSmac(cx, scope, smac);
	}
	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	private static final Object SECURITYDOMAIN_TAG = new Object();
	private static final NativeGPSecurityDomain prototypeSecurityDomain = new NativeGPSecurityDomain();
	private static final int Id_sendApdu = 1;
	private static final int Id_getData = 2;
	private static final int Id_getStatus = 3;
	private static final int Id_installForExtradition = 4;
	private static final int Id_installForInstall = 5;
	private static final int Id_installForInstallAndSelectable = 6;
	private static final int Id_installForLoad = 7;
	private static final int Id_installForPersonalization = 8;
	private static final int Id_load = 9;
	private static final int Id_loadByName = 10;
	private static final int Id_openSecureChannel = 11;
	private static final int Id_select = 12;
	private static final int Id_deleteAID = 13;
	private static final int Id_putKey = 14;
	private static final int Id_putKeyStd = 21;
	private static final int Id_setStatus = 15;
	private static final int Id_storeData = 16;
	private static final int Id_storeDataLmk = 22;
	private static final int Id_storeEmvData = 17;
	private static final int Id_setPan = 23;
	private static final int LAST_METHOD_ID = 23;
	private static final int MAX_PROTOTYPE_ID = 23;
	private static final int Id_secureChannel = 1;
	private static final int Id_securityDomain = 2;
	private static final int Id_crypto = 3;
	private static final int Id_key = 4;
	private static final int Id_data = 5;
	private static final int Id_profile = 6;
	private static final int Id_card = 7;
	private static final int Id_hsm = 8;
	private static final int Id_closeSecureChannel = 18;
	private static final int Id_putKeyLmk = 20;
	private static final int MAX_INSTANCE_ID = 8;
	public NativeCard card;
	public NativeCrypto crypto;
	public Application profile;
	public IdScriptableObject secureChannel;
	public NativeGPSecurityDomain securityDomain;
	public NativeArray key;
	public NativeArray data;
	public String strAID;
	private int p2_storeData;
	public int secureLevel;
	private String pan;

	
}