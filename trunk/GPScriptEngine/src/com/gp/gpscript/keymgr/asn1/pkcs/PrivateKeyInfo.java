package com.gp.gpscript.keymgr.asn1.pkcs;

import java.io.*;
import java.util.Enumeration;
import java.math.BigInteger;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.asn1.x509.AlgorithmIdentifier;

public class PrivateKeyInfo
    implements PKCSObjectIdentifiers, DEREncodable
{
    private DERObject               privKey;
    private AlgorithmIdentifier     algId;

    public PrivateKeyInfo(
        AlgorithmIdentifier algId,
        DERObject           privateKey)
    {
        this.privKey = privateKey;
        this.algId = algId;
    }

    public PrivateKeyInfo(
        DERConstructedSequence  seq)
    {
        Enumeration e = seq.getObjects();

        BigInteger  version = ((DERInteger)e.nextElement()).getValue();
        if (version.intValue() != 0)
        {
            throw new IllegalArgumentException("wrong version for private key info");
        }

        algId = new AlgorithmIdentifier((DERConstructedSequence)e.nextElement());

        try
        {
            ByteArrayInputStream    bIn = new ByteArrayInputStream(((DEROctetString)e.nextElement()).getOctets());
            DERInputStream          dIn = new DERInputStream(bIn);

            privKey = dIn.readObject();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            throw new IllegalArgumentException("Error recoverying private key from sequence");
        }
    }

    public AlgorithmIdentifier getAlgorithmId()
    {
        return algId;
    }

    public DERObject getPrivateKey()
    {
        return privKey;
    }

    /**
     * write out an RSA private key with it's asscociated information
     * as described in PKCS8.
     * <pre>
     *      PrivateKeyInfo ::= SEQUENCE {
     *                              version Version,
     *                              privateKeyAlgorithm AlgorithmIdentifier {{PrivateKeyAlgorithms}},
     *                              privateKey PrivateKey,
     *                              attributes [0] IMPLICIT Attributes OPTIONAL
     *                          }
     *      Version ::= INTEGER {v1(0)} (v1,...)
     *
     *      PrivateKey ::= OCTET STRING
     *
     *      Attributes ::= SET OF Attribute
     * </pre>
     */
    public DERObject getDERObject()
    {
        DERConstructedSequence seq = new DERConstructedSequence();

        seq.addObject(new DERInteger(0));
        seq.addObject(algId);
        seq.addObject(new DEROctetString(privKey));

        return seq;
    }
}
