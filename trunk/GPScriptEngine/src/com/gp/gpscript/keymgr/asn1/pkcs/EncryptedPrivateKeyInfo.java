package com.gp.gpscript.keymgr.asn1.pkcs;

import java.io.*;
import java.util.Enumeration;
import java.math.BigInteger;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.asn1.x509.AlgorithmIdentifier;

public class EncryptedPrivateKeyInfo
    implements PKCSObjectIdentifiers, DEREncodable
{
    private AlgorithmIdentifier algId;
    private DEROctetString      data;

    public EncryptedPrivateKeyInfo(
        DERConstructedSequence  seq)
    {
        Enumeration e = seq.getObjects();

        algId = new AlgorithmIdentifier((DERConstructedSequence)e.nextElement());
        data = (DEROctetString)e.nextElement();
    }

    public EncryptedPrivateKeyInfo(
        AlgorithmIdentifier algId,
        byte[]              encoding)
    {
        this.algId = algId;
        this.data = new DEROctetString(encoding);
    }

    public AlgorithmIdentifier getEncryptionAlgorithm()
    {
        return algId;
    }

    public byte[] getEncryptedData()
    {
        return data.getOctets();
    }

    /**
     * EncryptedPrivateKeyInfo ::= SEQUENCE {
     *      encryptionAlgorithm AlgorithmIdentifier {{KeyEncryptionAlgorithms}},
     *      encryptedData EncryptedData
     * }
     *
     * EncryptedData ::= OCTET STRING
     *
     * KeyEncryptionAlgorithms ALGORITHM-IDENTIFIER ::= {
     *          ... -- For local profiles
     * }
     */
    public DERObject getDERObject()
    {
        DERConstructedSequence seq = new DERConstructedSequence();

        seq.addObject(algId);
        seq.addObject(data);

        return seq;
    }
}
