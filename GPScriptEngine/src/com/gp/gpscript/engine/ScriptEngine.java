package com.gp.gpscript.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;

import com.gp.gpscript.keymgr.util.encoders.Hex;
import com.gp.gpscript.profile.app.ApplicationProfile;
import com.gp.gpscript.profile.app.apDataElement;
import com.gp.gpscript.profile.card.CardProfile;
import com.gp.gpscript.script.ApduChannel;
import com.gp.gpscript.script.Application;
import com.gp.gpscript.script.GPConstant;
import com.gp.gpscript.script.GPError;
import com.gp.gpscript.script.NativeApplication;
import com.gp.gpscript.script.NativeAtr;
import com.gp.gpscript.script.NativeByteBuffer;
import com.gp.gpscript.script.NativeByteString;
import com.gp.gpscript.script.NativeCard;
import com.gp.gpscript.script.NativeCrypto;
import com.gp.gpscript.script.NativeGPApplication;
import com.gp.gpscript.script.NativeGPScp01;
import com.gp.gpscript.script.NativeGPScp02;
import com.gp.gpscript.script.NativeGPSecurityDomain;
import com.gp.gpscript.script.NativeGPSystem;
import com.gp.gpscript.script.NativeKey;
import com.gp.gpscript.script.NativeSecureChannel;
import com.gp.gpscript.script.NativeTLV;
import com.gp.gpscript.script.NativeTLVList;
import com.gp.gpscript.script.TLV;
import com.watchdata.kms.kmsi.IKms;

/**
 * <p>ScriptEngine</p>
 * <p>Description:  This class implements the parse Script </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: WATCHDATA</p>
 * @author luhch
 * @date Jul 18, 2011
 * @version 1.0
 */

/**
 * modify: SetDataElement() and getValue() add type==3 HuatengData) who modify: peng.wang when modify:2012-03-16
 * 
 */
public class ScriptEngine {

	private Logger log = Logger.getLogger(ScriptEngine.class);
	private ArrayList<String> paraList;
	private int count = 0;
	private String cardfile = "";
	private String appfile = "";
	private String selectedFragment = "";
	private HashMap<String,HashMap<String, String>> varHashMap;
	private ApduChannel apduChannel;
	private Script script;
	private ApplicationProfile appProfile;
	private CardProfile cardProfile;
	private Scriptable gpApp;
	private Scriptable scope;
	private Context cx;
	private static Scriptable sharedScope;
	public ArrayList<String> textList = new ArrayList<String>();
	public String track1Data = "";
	public String track2Data = "";
	public String track3Data = "";
	private IKms kmsi;

	public ScriptEngine(String selectedFragment, String appfile, String cardfile, HashMap<String,HashMap<String, String>> varHashMap,int count) throws Exception {
		apduChannel = null;
		script = null;
		appProfile = null;
		cardProfile = null;
		gpApp = null;
		scope = null;
		cx = null;
		this.varHashMap = varHashMap;
		this.selectedFragment = selectedFragment;
		this.appfile = appfile;
		this.cardfile = cardfile;
		this.count = count;
	}

	/**
	 * 脚本执行
	 * 
	 * @param selectedFragment
	 * @param appfile
	 * @param cardfile
	 * @throws IKmsException
	 * @throws Exception
	 */
	public String[] execEngine() throws Exception {

		String[] allPersonDatas = new String[count];
		String personData = "";
		try {
			prepareScriptContext(selectedFragment, appfile, cardfile);
		} catch (Exception e) {
			return new String[] { "编译脚本出错" + e };
		}

		try {
			for (int i = 0; i < count; i++) {
				personData = getIcInfo(i, varHashMap);
				allPersonDatas[i] = personData;
			}

			return allPersonDatas;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}
	}

	/**
	 * 组织数据
	 * 
	 * @param i
	 * @throws Exception
	 */
	private String getIcInfo(int i, HashMap<String,HashMap<String, String>> varHashMap) throws Exception {
		//数据映射到脚本变量
		try {
			dataMapping(i);
		} catch (Exception e) {
			throw e;
		}
		//执行脚本
		boolean succflag = false;
		try {
			succflag = evaluateScript();
		} catch (Exception e) {
			throw new Exception("脚本引擎出错,请检查!");
		}

		NativeArray dataArray;
		if (gpApp instanceof NativeApplication)
			dataArray = ((NativeApplication) gpApp).data;
		else if (gpApp instanceof NativeGPApplication)
			dataArray = ((NativeGPApplication) gpApp).data;
		else if (gpApp instanceof NativeGPSecurityDomain)
			dataArray = ((NativeGPSecurityDomain) gpApp).data;
		else
			dataArray = null;
		NativeByteString snew = (NativeByteString) ScriptRuntime.getObjectElem(dataArray, "CPS_Output", cx);
		if (!succflag)
			throw new Exception("evaluateScript error.");

		HashMap<String,String> tagRecordMap = (HashMap<String,String>) varHashMap.get("" + i);
		String pan =tagRecordMap.get("pan");

		//clear back
		varHashMap.put("" + i, null);
		varHashMap.remove("" + i);
		return (pan + "|" + snew.toString());

	}

	/**
	 * 准备上下文
	 * 
	 * @param selectedFragment
	 * @param appfile
	 * @param cardfile
	 * @return
	 * @throws JavaScriptException
	 */
	public boolean prepareScriptContext(String selectedFragment, String appfile, String cardfile) throws Exception {
		//创建rhino js脚本环境
		cx = Context.enter();
		if (sharedScope == null) {
			sharedScope = cx.initStandardObjects();
			//初始化GP对象
			InitGPObject(cx, sharedScope);
			
			Scriptable jsArgs = Context.toObject(System.out, sharedScope);
			sharedScope.put("out", sharedScope, jsArgs);
		}
		scope = cx.newObject(sharedScope);
		scope.setPrototype(sharedScope);
		scope.setParentScope(null);
		
		//初始化card和app profile object
		try {
			cardProfile = new CardProfile(cardfile);
			appProfile = new ApplicationProfile(appfile, 0);
		} catch (Exception e) {
			Context.exit();
			e.printStackTrace();
			throw e;
		}
		//创建应用OBJECT
		Application app = new Application(appfile, cx, scope, 0);
		gpApp = createApplication(cx, scope, app, cardProfile);
		if (gpApp == null) {
			log.debug("create Application or GPApplication or GPSecurityDomain failed!");
			Context.exit();
			return false;
		}
		//编译脚本片段
		script = createScript(cx, appProfile, selectedFragment);
		if (script == null) {
			log.debug("create script object failed!");
			Context.exit();
			return false;
		}
		return true;
	}

	public boolean evaluateScript() throws Exception {
		boolean SUCCESS = true;
		try {
			script.exec(cx, gpApp);
		} catch (Exception egErr) {
			egErr.printStackTrace();
			SUCCESS = false;
			throw egErr;
		}
		return SUCCESS;
	}

	/**
	 * 创建应用
	 * 
	 * @param cx
	 * @param scope
	 * @param app
	 * @param cardProfile
	 * @return
	 */
	private Scriptable createApplication(Context cx, Scriptable scope, Application app, CardProfile cardProfile) {
		Scriptable gpscope = null;
		log.debug("start create Applicaiton/GPApplication/GPSecurityDomain object:");
		String type = appProfile.ApplicationInfo.Type;
		String subType = appProfile.ApplicationInfo.SubType;
		try {
			if (type.equals("OP") && subType.equals("APP")) {
				log.debug("This is a GPApplication");
				NativeGPApplication GPApp = new NativeGPApplication();
				GPApp.setObjectPrototype();
				gpscope = GPApp;
				initGPAppObjects(cx, scope, GPApp, app, cardProfile);
			} else if (type.equals("OP") && (subType.equals("CM") || subType.equals("SD"))) {
				log.debug("This is a GPSecurityDomain");
				NativeGPSecurityDomain GPSec = new NativeGPSecurityDomain();
				GPSec.setObjectPrototype();
				gpscope = GPSec;
				initGPSecurityDomainObjects(cx, scope, GPSec, app, cardProfile);
			} else if (type.equals("OTHER")) {
				log.debug("This is a Appication");
				NativeApplication App = new NativeApplication();
				App.setObjectPrototype();
				initAppObjects(cx, scope, App, app, cardProfile);
			} else {
				throw new EvaluatorException((new GPError("RunScript", 0, 0, "The Application Profile is a invalid application type")).toString());
			}
		} catch (Exception err) {
			err.printStackTrace();
			Context.exit();
			return null;
		}
		log.debug("create object OK!");
		return gpscope;
	}

	/**
	 * 编译脚本
	 * 
	 * @param cx
	 * @param profile
	 * @param strFragment
	 * @return
	 */
	private Script createScript(Context cx, ApplicationProfile profile, String strFragment) {
		Script script = cx.compileString(getScriptString(profile, strFragment), strFragment, 1, null);
		return script;
	}

	/**
	 * 数据映射
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public boolean dataMapping(int index) throws Exception {
		NativeArray dataArray;
		NativeArray keyArray;
		Application app;
		try {
			if (gpApp instanceof NativeApplication) {
				dataArray = ((NativeApplication) gpApp).data;
				keyArray = ((NativeApplication) gpApp).key;
				app = ((NativeApplication) gpApp).profile;
				((NativeApplication) gpApp).reset();
			} else if (gpApp instanceof NativeGPApplication) {
				dataArray = ((NativeGPApplication) gpApp).data;
				keyArray = ((NativeGPApplication) gpApp).key;
				app = ((NativeGPApplication) gpApp).profile;
				((NativeGPApplication) gpApp).reset();
			} else if (gpApp instanceof NativeGPSecurityDomain) {
				dataArray = ((NativeGPSecurityDomain) gpApp).data;
				keyArray = ((NativeGPSecurityDomain) gpApp).key;
				app = ((NativeGPSecurityDomain) gpApp).profile;
				((NativeGPSecurityDomain) gpApp).reset();
			} else {
				dataArray = null;
				keyArray = null;
				app = null;
			}
			//设置声明的数据元素值
			if (app.ap.DataElement != null) {
				for (int i = 0; i < app.ap.DataElement.length; i++) {
					SetDataElement(cx, scope, index, dataArray, app.ap.DataElement[i]);
				}

			} else {
				throw new Exception("you should define the data element in application profile");
			}
			if (app.ap.Key != null) {
				for (int i = 0; i < app.ap.Key.length; i++)
					SetKeyElement(cx, scope, keyArray, app.ap.Key[i].Name, app.ap.Key[i].ProfileID, app.ap.Key[i].External);
			} else {
				throw new Exception("you should define the key in application profile");
			}
		} catch (Exception e) {
			throw e;
		}
		return true;
	}

	/**
	 * 获取脚本字符表示
	 * 
	 * @param profile
	 * @param strFragment
	 * @return
	 */
	public String getScriptString(ApplicationProfile profile, String strFragment) {
		String scriptArray[] = null;
		scriptArray = parseSelectedScript(strFragment);
		StringBuffer scriptString = new StringBuffer();
		if (profile.Function != null && profile.Function.length > 0) {
			for (int i = 0; i < profile.Function.length; i++) {
				scriptString.append("function " + profile.Function[i].Name + "(" + profile.Function[i].Param + ")\r\n");
				scriptString.append("{\r\n");
				scriptString.append(profile.Function[i].Script.Script + "\r\n");
				scriptString.append("}\r\n");
			}

		}
		int index = -1;
		for (int j = 0; j < scriptArray.length; j++) {
			index = getIndexOfScriptFragment(profile, scriptArray[j]);
			if (index != -1) {
				scriptString.append("\r\n//------------------" + scriptArray[j] + "-----------------\r\n");
				scriptString.append(profile.ScriptFragment[index].Script.Script);
			} else {
				throw new EvaluatorException((new GPError("GPScriptEngine", 0, 0, "No this scriptfragment, the option is invalid")).toString());
			}
		}

		return scriptString.toString();
	}

	/**
	 * 解析选择脚本
	 * 
	 * @param selectedScript
	 * @return 脚本元素
	 */
	public static String[] parseSelectedScript(String selectedScript) {
		Vector<String> vt = new Vector<String>();
		selectedScript = selectedScript.trim();
		int startIndex = 0;
		int index = 0;
		int i = 0;
		for (index = selectedScript.indexOf(',', startIndex); index >= 0; index = selectedScript.indexOf(',', startIndex)) {
			vt.add(selectedScript.substring(startIndex, index));
			startIndex = index + 1;
			i++;
		}
		vt.add(selectedScript.substring(startIndex, selectedScript.length()));
		String result[] = new String[++i];
		for (int j = 0; j < i; j++) {
			result[j] = vt.elementAt(j).toString();
		}

		return result;
	}

	public int getIndexOfScriptFragment(ApplicationProfile ap, String name) {
		int index = -1;
		for (int i = 0; i < ap.ScriptFragment.length; i++)
			if (ap.ScriptFragment[i].Name.equals(name))
				index = i;

		return index;
	}

	/**
	 * 获取应用脚本片段列表
	 * 
	 * @param appFile
	 * @return
	 */
	public String[] getAppProfileScriptFragmentList(String appFile) {
		ApplicationProfile appProfile = new ApplicationProfile(appFile);
		if (appProfile == null)
			return null;
		if (appProfile.ScriptFragment == null)
			return null;
		if (appProfile.ScriptFragment.length <= 0)
			return null;
		String fragList[] = new String[appProfile.ScriptFragment.length];
		for (int i = 0; i < appProfile.ScriptFragment.length; i++)
			fragList[i] = appProfile.ScriptFragment[i].Name;

		return fragList;
	}

	/**
	 * 初始化GP对象
	 * 
	 * @param cx
	 * @param scope
	 */
	public void InitGPObject(Context cx, Scriptable scope) {
		GPConstant.init(cx, scope, false);
		GPError.init(cx, scope, false);
		NativeGPSystem.init(cx, scope, false);
		NativeByteString.init(cx, scope, false);
		NativeByteBuffer.init(cx, scope, false);
		NativeTLV.init(cx, scope, false);
		NativeTLVList.init(cx, scope, false);
		NativeAtr.init(cx, scope, false);
		NativeCard.init(cx, scope, false);
		NativeKey.init(cx, scope, false);
		NativeCrypto.init(cx, scope, false);
		NativeSecureChannel.init(cx, scope, false);
		NativeGPScp01.init(cx, scope, false);
		NativeGPScp02.init(cx, scope, false);
		NativeApplication.init(cx, scope, false);
		NativeGPApplication.init(cx, scope, false);
		NativeGPSecurityDomain.init(cx, scope, false);
	}

	/**
	 * 初始化应用对象
	 * 
	 * @param cx
	 * @param scope
	 * @param na
	 * @param app
	 * @param cardProfile
	 */
	public void initAppObjects(Context cx, Scriptable scope, NativeApplication na, Application app, CardProfile cardProfile) {
		scope = NativeApplication.getTopLevelScope(scope);
		na.profile = app;
		na.key = (NativeArray) ScriptRuntime.newObject(cx, scope, "Array", null);
		na.data = (NativeArray) ScriptRuntime.newObject(cx, scope, "Array", null);
		na.card = new NativeCard();
		na.card.setObjectPrototype();
		na.card.profile = cardProfile;
		na.card.setApduChannel(apduChannel);
		na.crypto = new NativeCrypto();
		CryptoEngineForKmsKey cefk = new CryptoEngineForKmsKey();
		// cefk.setKmsi(kmsi);
		// cefk.setKmsServer(kmsServer);
		NativeCrypto.cryptoEngine.set(cefk);
		na.crypto.setObjectPrototype();
		if (na.profile.SecureChannel.SecureChannel.equals("SCP01")) {
			na.secureChannel = new NativeGPScp01(cx, scope, na.card);
			((NativeGPScp01) na.secureChannel).setObjectPrototype();
		} else if (na.profile.SecureChannel.SecureChannel.equals("SCP02")) {
			na.secureChannel = new NativeGPScp02(cx, scope, na.card);
			((NativeGPScp02) na.secureChannel).setObjectPrototype();
		} else {
			na.secureChannel = new NativeSecureChannel();
		}
	}

	/**
	 * 初始化GP 应用对象
	 * 
	 * @param cx
	 * @param scope
	 * @param ngpa
	 * @param app
	 * @param cardProfile
	 */
	public void initGPAppObjects(Context cx, Scriptable scope, NativeGPApplication ngpa, Application app, CardProfile cardProfile) {
		scope = NativeGPApplication.getTopLevelScope(scope);
		ngpa.profile = app;
		ngpa.key = (NativeArray) ScriptRuntime.newObject(cx, scope, "Array", null);
		ngpa.data = (NativeArray) ScriptRuntime.newObject(cx, scope, "Array", null);
		ngpa.card = new NativeCard();
		ngpa.card.setObjectPrototype();
		ngpa.card.setApduChannel(apduChannel);
		ngpa.card.profile = cardProfile;
		ngpa.crypto = new NativeCrypto();
		CryptoEngineForClearTextKey cefk = new CryptoEngineForClearTextKey();
		// cefk.setKmsi(kmsi);
		// cefk.setKmsServer(kmsServer);
		NativeCrypto.cryptoEngine.set(cefk);
		ngpa.crypto.setObjectPrototype();
		ngpa.securityDomain = new NativeGPSecurityDomain();
		initGPSecurityDomainObjects(cx, scope, ngpa.securityDomain, app, cardProfile);
	}

	/**
	 * 初始化安全域对象
	 * 
	 * @param cx
	 * @param scope
	 * @param sd
	 * @param app
	 * @param cardProfile
	 */
	public void initGPSecurityDomainObjects(Context cx, Scriptable scope, NativeGPSecurityDomain sd, Application app, CardProfile cardProfile) {
		scope = NativeGPSecurityDomain.getTopLevelScope(scope);
		sd.profile = app;
		sd.key = (NativeArray) ScriptRuntime.newObject(cx, scope, "Array", null);
		sd.data = (NativeArray) ScriptRuntime.newObject(cx, scope, "Array", null);
		sd.card = new NativeCard();
		sd.card.setObjectPrototype();
		sd.card.setApduChannel(apduChannel);
		sd.card.profile = cardProfile;
		sd.crypto = new NativeCrypto();
		CryptoEngineForClearTextKey cefk = new CryptoEngineForClearTextKey();
		// cefk.setKmsi(kmsi);
		// cefk.setKmsServer(kmsServer);
		NativeCrypto.cryptoEngine.set(cefk);

		sd.crypto.setObjectPrototype();
		if (sd.profile.SecureChannel.SecureChannel.equals("SCP01")) {
			log.debug("Gpscp01");
			sd.secureChannel = new NativeGPScp01(cx, scope, sd.card);
			((NativeGPScp01) sd.secureChannel).setObjectPrototype();
		} else {
			log.debug("Gpscp02");
			sd.secureChannel = new NativeGPScp02(cx, scope, sd.card);
			((NativeGPScp02) sd.secureChannel).setObjectPrototype();
		}
		sd.securityDomain = sd;
	}

	/**
	 * 设置数据
	 * 
	 * @param cx
	 * @param scope
	 * @param index
	 * @param objArray
	 * @param dataElement
	 * @throws Exception
	 */
	public void SetDataElement(Context cx, Scriptable scope, int index, Scriptable objArray, apDataElement dataElement) throws Exception {
		String strName = dataElement.Name;
		String className = dataElement.Type;
		String external = dataElement.External;
		String encoding = dataElement.Encoding;
		int encodeV = 1000;
		if (encoding.equalsIgnoreCase("HEX"))
			encodeV = 1000;
		else if (encoding.equalsIgnoreCase("UTF8"))
			encodeV = 1001;
		else if (encoding.equalsIgnoreCase("ASCII"))
			encodeV = 1002;
		else if (encoding.equalsIgnoreCase("BASE64"))
			encodeV = 1003;
		else if (encoding.equalsIgnoreCase("CN"))
			encodeV = 1004;
		if (className.equalsIgnoreCase("BYTESTRING")) {
			if (external.equals("true")) {
				String allDGI = getValue(index, strName);

				if (allDGI == null) {
					ScriptRuntime.setObjectElem(objArray, strName, null, cx);
				} else {
					NativeByteString sNew = new NativeByteString(allDGI, new Integer(encodeV));

					Object temp = NativeByteString.newByteString(cx, scope, sNew);
					ScriptRuntime.setObjectElem(objArray, strName, temp, cx);
				}
			} else {
				ScriptRuntime.setObjectElem(objArray, strName, null, cx);
			}
		} else if (className.equalsIgnoreCase("ByteString (Tag Specified)")) {
			if (external.equals("true")) {
				String allDGI = getValue(index, strName);
				if (allDGI == null) {
					ScriptRuntime.setObjectElem(objArray, strName, null, cx);
				} else {
					byte dgi[] = Hex.decode(allDGI);
					int tag = dgi[0] * 256 + dgi[1];
					byte value[] = new byte[dgi.length - 3];
					System.arraycopy(dgi, 3, value, 0, dgi.length - 3);
					TLV MyTLV = new TLV(tag, value, (byte) 2);
					Object temp = NativeTLV.newTLV(cx, scope, MyTLV);
					ScriptRuntime.setObjectElem(objArray, strName, temp, cx);
				}
			} else {
				ScriptRuntime.setObjectElem(objArray, strName, null, cx);
			}
		} else if (className.equalsIgnoreCase("BOOLEAN")) {
			if (external.equals("true")) {
				boolean bValue = true;
				Object para[] = { new Boolean(bValue) };
				Scriptable result = ScriptRuntime.newObject(cx, scope, "Boolean", para);
				Object temp = result;
				ScriptRuntime.setObjectElem(objArray, strName, temp, cx);
			} else {
				ScriptRuntime.setObjectElem(objArray, strName, null, cx);
			}
		} else if (className.equalsIgnoreCase("NUMBER")) {
			if (external.equals("true")) {
				String value = "11.1";
				Number ss = new Double(value);
				Object para[] = { ss };
				Scriptable result = ScriptRuntime.newObject(cx, scope, "Number", para);
				Object temp = result;
				ScriptRuntime.setObjectElem(objArray, strName, temp, cx);
			} else {
				ScriptRuntime.setObjectElem(objArray, strName, null, cx);
			}
		} else {
			throw new EvaluatorException((new GPError("GPScriptEngine", 0, 0, className + " is a valid type")).toString());
		}
	}

	/**
	 * 设置密钥
	 * 
	 * @param cx
	 * @param scope
	 * @param objArray
	 * @param strName
	 * @param strProfileID
	 * @param External
	 */
	public void SetKeyElement(Context cx, Scriptable scope, Scriptable objArray, String strName, String strProfileID, String External) {
		String blob = "";
		NativeKey myKey = null;
		if (External.equalsIgnoreCase("True")) {
			blob = getKey(0, strName);
		}
		myKey = new NativeKey(strName, strProfileID, blob, External);
		myKey.setObjectPrototype();
		ScriptRuntime.setObjectElem(objArray, strName, myKey, cx);
	}

	public String getKey(int index, String name) {
		return "";
	}

	public String getValue(int index, String data) throws Exception {
		return new DataMaping().getDataElement(index, data, varHashMap);
	}

	public ArrayList<String> getParaList() {
		return paraList;
	}

	public void setParaList(ArrayList<String> paraList) {
		this.paraList = paraList;
	}

	public IKms getKmsi() {
		return kmsi;
	}

	public void setKmsi(IKms kmsi) {
		this.kmsi = kmsi;
	}

}
