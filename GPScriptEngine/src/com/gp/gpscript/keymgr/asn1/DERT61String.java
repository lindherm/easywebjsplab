package com.gp.gpscript.keymgr.asn1;

import java.io.*;

/**
 * DER T61String (also the teletex string)
 */
public class DERT61String
    extends DERObject
    implements DERString
{
    String  string;

    /**
     * return a T61 string from the passed in object.
     *
     * @exception IllegalArgumentException if the object cannot be converted.
     */
    public static DERT61String getInstance(
        Object  obj)
    {
        if (obj == null || obj instanceof DERT61String)
        {
            return (DERT61String)obj;
        }

        if (obj instanceof ASN1OctetString)
        {
            return new DERT61String(((ASN1OctetString)obj).getOctets());
        }

        if (obj instanceof ASN1TaggedObject)
        {
            return getInstance(((ASN1TaggedObject)obj).getObject());
        }

        throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    /**
     * return an T61 String from a tagged object.
     *
     * @param obj the tagged object holding the object we want
     * @param explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the tagged object cannot
     *               be converted.
     */
    public static DERT61String getInstance(
        ASN1TaggedObject obj,
        boolean          explicit)
    {
        return getInstance(obj.getObject());
    }

    /**
     * basic constructor - with bytes.
     */
    public DERT61String(
        byte[]   string)
    {
        this.string = new String(string);
    }

    /**
     * basic constructor - with string.
     */
    public DERT61String(
        String   string)
    {
        this.string = string;
    }

    public String getString()
    {
        return string;
    }

    void encode(
        DEROutputStream  out)
        throws IOException
    {
        out.writeEncoded(T61_STRING, string.getBytes());
    }

    public boolean equals(
        Object  o)
    {
        if ((o == null) || !(o instanceof DERT61String))
        {
            return false;
        }

        return this.getString().equals(((DERT61String)o).getString());
    }
}
