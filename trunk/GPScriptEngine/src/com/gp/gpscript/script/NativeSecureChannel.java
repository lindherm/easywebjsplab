package com.gp.gpscript.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.Scriptable;
/**
 *
 * <p>Title: SecureChannel</p>
 * <p>Description:
 * This class implements the SecureChannel (a built-in object).
 * See [GP_SYS_SCR] 7.1.7 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: watchdata</p>
 * @author SunJinGang
 * @version 1.0
 */
public class NativeSecureChannel extends IdScriptableObject
{
    static NativeSecureChannel prototypeSecureChannel = new NativeSecureChannel();

    public static void init(Context cx, Scriptable scope, boolean sealed)
    {
        prototypeSecureChannel.activatePrototypeMap(MAX_ID);
        prototypeSecureChannel.setPrototype(getObjectPrototype(scope));
        prototypeSecureChannel.setParentScope(scope);
        if (sealed) { prototypeSecureChannel.sealObject(); }
    }

    public NativeSecureChannel()
    {
    }

    public String getClassName() { return "SecureChannel"; }

    public void setObjectPrototype()
    {
        setPrototype(prototypeSecureChannel);
//        setParentScope(prototypeSecureChannel.getParentScope());
    }

    protected int findPrototypeId(String s)
    {
        int id=0;
        String X = null;
        if(s.equals("state")) {X="state";id=Id_state;}
        if(s.equals("crypto")) {X="crypto";id=Id_crypto;}

        return id;
    }

    //define value for all function and constant
    private static final int
        LAST_METHOD_ID  = 0,
		//the following should be contants
        Id_crypto 	=	 LAST_METHOD_ID+1,
        Id_state 	=	 LAST_METHOD_ID+2,
	MAX_INSTANCE_ID = LAST_METHOD_ID + 2,
        MAX_ID = MAX_INSTANCE_ID;

	//property
    private NativeCrypto crypto;
    private Number state;

}
