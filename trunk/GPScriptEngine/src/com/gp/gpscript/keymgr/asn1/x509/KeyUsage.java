package com.gp.gpscript.keymgr.asn1.x509;

import com.gp.gpscript.keymgr.asn1.*;

/**
 * <pre>
 *    id-ce-keyUsage OBJECT IDENTIFIER ::=  { id-ce 15 }
 *
 *    KeyUsage ::= BIT STRING {
 *         digitalSignature        (0),
 *         nonRepudiation          (1),
 *         keyEncipherment         (2),
 *         dataEncipherment        (3),
 *         keyAgreement            (4),
 *         keyCertSign             (5),
 *         cRLSign                 (6),
 *         encipherOnly            (7),
 *         decipherOnly            (8) }
 * </pre>
 */
public class KeyUsage
    extends DERBitString
{
    public static final int        digitalSignature = (1 << 7);
    public static final int        nonRepudiation   = (1 << 6);
    public static final int        keyEncipherment  = (1 << 5);
    public static final int        dataEncipherment = (1 << 4);
    public static final int        keyAgreement     = (1 << 3);
    public static final int        keyCertSign      = (1 << 2);
    public static final int        cRLSign          = (1 << 1);
    public static final int        encipherOnly     = (1 << 0);
    public static final int        decipherOnly     = (1 << 15);

    static private byte[] getUsageBytes(
        int usage)
    {
        byte[]  usageBytes = new byte[2];

        usageBytes[0] = (byte)(usage & 0xFF);
        usageBytes[1] = (byte)((usage >> 8) & 0xFF);

        return usageBytes;
    }

    /**
     * Basic constructor.
     *
     * @param usage - the bitwise OR of the Key Usage flags giving the
     * allowed uses for the key.
     * e.g. (X509KeyUsage.keyEncipherment | X509KeyUsage.dataEncipherment)
     */
    public KeyUsage(
        int usage)
    {
        super(getUsageBytes(usage), 7);
    }

    public KeyUsage(
        DERBitString usage)
    {
        super(usage.getBytes(), usage.getPadBits());
    }

    public String toString()
    {
        return "KeyUsage: 0x" + Integer.toHexString(data[0] & 0xff);
    }
}
