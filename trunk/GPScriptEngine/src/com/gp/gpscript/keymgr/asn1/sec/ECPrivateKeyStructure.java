package com.gp.gpscript.keymgr.asn1.sec;

import java.math.BigInteger;

import com.gp.gpscript.keymgr.asn1.*;

/**
 * the elliptic curve private key object from SEC 1
 */
public class ECPrivateKeyStructure
implements DEREncodable
{
    private DERConstructedSequence  seq = new DERConstructedSequence();

    public ECPrivateKeyStructure(
        DERConstructedSequence  seq)
    {
        this.seq = seq;
    }

    public ECPrivateKeyStructure(
        BigInteger  key)
    {
        byte[]  bytes = key.toByteArray();

        if (bytes[0] == 0)
        {
            byte[]  tmp = new byte[bytes.length - 1];

            System.arraycopy(bytes, 1, tmp, 0, tmp.length);
            bytes = tmp;
        }

        seq = new DERConstructedSequence();

        seq.addObject(new DERInteger(1));
        seq.addObject(new DEROctetString(bytes));
    }

    public BigInteger getKey()
    {
        DEROctetString  octs = (DEROctetString)seq.getObjectAt(1);

        BigInteger  k = new BigInteger(1, octs.getOctets());

        return k;
    }

    public DERObject getDERObject()
    {
        return seq;
    }
}
