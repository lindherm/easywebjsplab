package com.gp.gpscript.keymgr.asn1.x9;

import java.io.*;
import java.util.Enumeration;
import java.math.BigInteger;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.asn1.pkcs.PKCSObjectIdentifiers;

/**
 * ASN.1 def for Diffie-Hellman key exchange KeySpecificInfo structure. See
 * RFC 2631, or X9.42, for further details.
 */
public class KeySpecificInfo
    implements DEREncodable
{
    private DERObjectIdentifier algorithm;
    private DEROctetString      counter;

    public KeySpecificInfo(
        DERObjectIdentifier algorithm,
        DEROctetString      counter)
    {
        this.algorithm = algorithm;
        this.counter = counter;
    }

    public KeySpecificInfo(
        DERConstructedSequence  seq)
    {
        Enumeration e = seq.getObjects();

        algorithm = (DERObjectIdentifier)e.nextElement();
        counter = (DEROctetString)e.nextElement();
    }

    public DERObjectIdentifier getAlgorithm()
    {
        return algorithm;
    }

    public DEROctetString getCounter()
    {
        return counter;
    }

    /**
     * <pre>
     *  KeySpecificInfo ::= SEQUENCE {
     *      algorithm OBJECT IDENTIFIER,
     *      counter OCTET STRING SIZE (4..4)
     *  }
     * </pre>
     */
    public DERObject getDERObject()
    {
        DERConstructedSequence  seq = new DERConstructedSequence();

        seq.addObject(algorithm);
        seq.addObject(counter);

        return seq;
    }
}
