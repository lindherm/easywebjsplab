package com.gp.gpscript.keymgr.asn1.pkcs;

import java.io.*;
import java.util.Enumeration;
import java.math.BigInteger;

import com.gp.gpscript.keymgr.asn1.*;

public class RSAPrivateKeyStructure
    implements DEREncodable
{
    private BigInteger  modulus;
    private BigInteger  publicExponent;
    private BigInteger  privateExponent;
    private BigInteger  prime1;
    private BigInteger  prime2;
    private BigInteger  exponent1;
    private BigInteger  exponent2;
    private BigInteger  coefficient;

    public RSAPrivateKeyStructure(
        BigInteger  modulus,
        BigInteger  publicExponent,
        BigInteger  privateExponent,
        BigInteger  prime1,
        BigInteger  prime2,
        BigInteger  exponent1,
        BigInteger  exponent2,
        BigInteger  coefficient)
    {
        this.modulus = modulus;
        this.publicExponent = publicExponent;
        this.privateExponent = privateExponent;
        this.prime1 = prime1;
        this.prime2 = prime2;
        this.exponent1 = exponent1;
        this.exponent2 = exponent2;
        this.coefficient = coefficient;
    }

    public RSAPrivateKeyStructure(
        DERConstructedSequence  seq)
    {
        Enumeration e = seq.getObjects();

        BigInteger  version = ((DERInteger)e.nextElement()).getValue();
        if (version.intValue() != 0)
        {
            throw new IllegalArgumentException("wrong version for RSA private key");
        }

        modulus = ((DERInteger)e.nextElement()).getValue();
        publicExponent = ((DERInteger)e.nextElement()).getValue();
        privateExponent = ((DERInteger)e.nextElement()).getValue();
        prime1 = ((DERInteger)e.nextElement()).getValue();
        prime2 = ((DERInteger)e.nextElement()).getValue();
        exponent1 = ((DERInteger)e.nextElement()).getValue();
        exponent2 = ((DERInteger)e.nextElement()).getValue();
        coefficient = ((DERInteger)e.nextElement()).getValue();
    }

    public BigInteger getModulus()
    {
        return modulus;
    }

    public BigInteger getPublicExponent()
    {
        return publicExponent;
    }

    public BigInteger getPrivateExponent()
    {
        return privateExponent;
    }

    public BigInteger getPrime1()
    {
        return prime1;
    }

    public BigInteger getPrime2()
    {
        return prime2;
    }

    public BigInteger getExponent1()
    {
        return exponent1;
    }

    public BigInteger getExponent2()
    {
        return exponent2;
    }

    public BigInteger getCoefficient()
    {
        return coefficient;
    }

    /**
     * This outputs the key in PKCS1v2 format.
     * <pre>
     *      RSAPrivateKey ::= SEQUENCE {
     *                          version Version,
     *                          modulus INTEGER, -- n
     *                          publicExponent INTEGER, -- e
     *                          privateExponent INTEGER, -- d
     *                          prime1 INTEGER, -- p
     *                          prime2 INTEGER, -- q
     *                          exponent1 INTEGER, -- d mod (p-1)
     *                          exponent2 INTEGER, -- d mod (q-1)
     *                          coefficient INTEGER -- (inverse of q) mod p
     *                      }
     *
     *      Version ::= INTEGER
     * </pre>
     * <p>
     * This routine is written to output PKCS1 version 0, private keys.
     */
    public DERObject getDERObject()
    {
        DERConstructedSequence  seq = new DERConstructedSequence();

        seq.addObject(new DERInteger(0));                       // version
        seq.addObject(new DERInteger(getModulus()));
        seq.addObject(new DERInteger(getPublicExponent()));
        seq.addObject(new DERInteger(getPrivateExponent()));
        seq.addObject(new DERInteger(getPrime1()));
        seq.addObject(new DERInteger(getPrime2()));
        seq.addObject(new DERInteger(getExponent1()));
        seq.addObject(new DERInteger(getExponent2()));
        seq.addObject(new DERInteger(getCoefficient()));

        return seq;
    }
}
