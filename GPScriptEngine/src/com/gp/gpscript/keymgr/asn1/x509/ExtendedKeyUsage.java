package com.gp.gpscript.keymgr.asn1.x509;

import java.io.*;
import java.util.*;

import com.gp.gpscript.keymgr.asn1.*;

/**
 *  extendedKeyUsage
 *  <pre>
 *      extendedKeyUsage ::= SEQUENCE SIZE (1..MAX) OF KeyPurposeId
 *  </pre>
 */
public class ExtendedKeyUsage
    implements DEREncodable
{
    Hashtable     usageTable = new Hashtable();
    ASN1Sequence  seq;

    public static ExtendedKeyUsage getInstance(
        ASN1TaggedObject obj,
        boolean          explicit)
    {
        return getInstance(ASN1Sequence.getInstance(obj, explicit));
    }

    public static ExtendedKeyUsage getInstance(
        Object obj)
    {
        if(obj == null || obj instanceof ExtendedKeyUsage)
        {
            return (ExtendedKeyUsage)obj;
        }

        if(obj instanceof ASN1Sequence)
        {
            return new ExtendedKeyUsage((ASN1Sequence)obj);
        }

        throw new IllegalArgumentException("Invalid ExtendedKeyUsage: " + obj.getClass().getName());
    }

    public ExtendedKeyUsage(
        ASN1Sequence  seq)
    {
        this.seq = seq;

        Enumeration e = seq.getObjects();

        while (e.hasMoreElements())
        {
            Object  o = e.nextElement();

            this.usageTable.put(o, o);
        }
    }

    public ExtendedKeyUsage(
        Vector  usages)
    {
        this.seq = new DERConstructedSequence();

        Enumeration e = usages.elements();

        while (e.hasMoreElements())
        {
            DERObject  o = (DERObject)e.nextElement();

            seq.addObject(o);
            this.usageTable.put(o, o);
        }
    }

    public boolean hasKeyPurposeId(
        KeyPurposeId keyPurposeId)
    {
        return (usageTable.get(keyPurposeId) != null);
    }

    public DERObject getDERObject()
    {
        return seq;
    }
}
