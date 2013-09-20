package com.gp.gpscript.script;

import org.mozilla.javascript.*;

/**
 *
 * <p>Title: GP_Constant</p>
 * <p>Description: This is a interface of all GP object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: watchdata</p>
 * @author SunJingang
 * @version 1.0
 */
public class GPConstant
{

  public static void init(Context cx, Scriptable scope, boolean sealed)
  {
        ScriptableObject.defineProperty(scope, "HEX", new Integer(HEX),ScriptableObject.DONTENUM);
        ScriptableObject.defineProperty(scope, "UTF8",new Integer(UTF8),ScriptableObject.DONTENUM);
        ScriptableObject.defineProperty(scope, "ASCII",new Integer(ASCII),ScriptableObject.DONTENUM);
        ScriptableObject.defineProperty(scope, "BASE64", new Integer(BASE64),ScriptableObject.DONTENUM);
        ScriptableObject.defineProperty(scope, "CN",new Integer(CN),ScriptableObject.DONTENUM);
        ScriptableObject.defineProperty(scope, "CM_ONLY", new Integer(CM_ONLY),ScriptableObject.DONTENUM);
        ScriptableObject.defineProperty(scope, "APPS_ONLY",new Integer(APPS_ONLY),ScriptableObject.DONTENUM);
        ScriptableObject.defineProperty(scope, "LF_ONLY", new Integer(LF_ONLY),ScriptableObject.DONTENUM);
        ScriptableObject.defineProperty(scope, "LFE_ONLY", new Integer(LFE_ONLY),ScriptableObject.DONTENUM);
        ScriptableObject.defineProperty(scope, "SC_CLOSE", new Integer(SC_CLOSE),ScriptableObject.DONTENUM);
        ScriptableObject.defineProperty(scope, "SC_OPEN", new Integer(SC_OPEN),ScriptableObject.DONTENUM);
        ScriptableObject.defineProperty(scope, "SC_INITIALIZE", new Integer(SC_INITIALIZE),ScriptableObject.DONTENUM);
        ScriptableObject.defineProperty(scope, "FIRST_OR_ALL_OCCURENCES", new Integer(FIRST_OR_ALL_OCCURENCES),ScriptableObject.DONTENUM);
        ScriptableObject.defineProperty(scope, "NEXT_OCCURENCES", new Integer(NEXT_OCCURENCES),ScriptableObject.DONTENUM);
  }
	/**
    * GP_Constant
    */
    public static final int HEX =        1000;
    public static final int UTF8 =       1001;
    public static final int ASCII =      1002;
    public static final int BASE64 =     1003;
    public static final int CN =         1004;
    public static final int CM_ONLY =    1005;
    public static final int APPS_ONLY =  1006;
    public static final int LF_ONLY =    1007;
    public static final int LFE_ONLY =   1008;
    public static final int SC_CLOSE =   1009;
    public static final int SC_OPEN =    1010;
    public static final int SC_INITIALIZE =     1011;
    public static final int FIRST_OR_ALL_OCCURENCES =     1012;
    public static final int NEXT_OCCURENCES =     1013;


    public static final String OID =   "0011223344";

    /* the secure leel of Secure Channel
     */
    public static final int NO_SECUREITY_LEVEL = 0X00;
    public static final int C_MAC = 0X01;
    public static final int C_MAC_ENC = 0X03;
    public static final int R_MAC = 0X10;
    public static final int C_MAC_R_MAC = 0X11;
    public static final int C_MAC_R_MAC_ENC = 0X13;


    // This P1 value is used with the Set Status APDU to indicate a card status change
    public static final byte P1_OP_CARD_STATUS = (byte)0x80;
    // This P1 value is used with the Set Status APDU to indicate an application status change
    public static final byte P1_OP_APPLET_STATUS = (byte)0x40;
    // This P1 value is used with the Set Status APDU to indicate a file status change
    public static final byte P1_OP_FILE_STATUS = (byte)0x20;


    // The OPSystem is operational on the card.
    public static final byte CARD_OP_READY = (byte)0x01;
    // The state is an administrative card production state.
    public static final byte CARD_INITIALIZED = (byte)0x07;
    // The card is ready for issuance or has been issued to a Cardholder
    public static final byte CARD_SECURED = (byte)0x0F;
    // The Card Manager has locked the card because it detected an on-card security threat.
    public static final byte CARD_LOCKED = (byte)0x7F;
    // The card is no longer operational
    public static final byte CARD_TERMINATED = (byte)0xFF;

}

