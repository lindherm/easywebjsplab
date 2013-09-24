package com.gp.gpscript.keymgr.asn1.pkcs;

import java.math.BigInteger;

import com.gp.gpscript.keymgr.asn1.*;

public class RC2CBCParameter
    implements DEREncodable
{
    DERInteger      version;
    DEROctetString  iv;

    public RC2CBCParameter(
        DERConstructedSequence  seq)
    {
        if (seq.getSize() == 1)
        {
            version = null;
            iv = (DEROctetString)seq.getObjectAt(0);
        }
        else
        {
            version = (DERInteger)seq.getObjectAt(0);
            iv = (DEROctetString)seq.getObjectAt(1);
        }
    }

    public BigInteger getRC2ParameterVersion()
    {
        return version.getValue();
    }

    public byte[] getIV()
    {
        return iv.getOctets();
    }

    public DERObject getDERObject()
    {
        DERConstructedSequence  seq = new DERConstructedSequence();

        if (version != null)
        {
            seq.addObject(version);
        }

        seq.addObject(iv);

        return seq;
    }
}
