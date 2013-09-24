package com.gp.gpscript.keymgr.asn1.pkcs;

import java.math.BigInteger;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.asn1.x509.*;

public class MacData
    implements DEREncodable
{
    DigestInfo                  digInfo;
    byte[]                      salt;
    BigInteger                  iterationCount;

    public static MacData getInstance(
        Object  obj)
    {
        if (obj instanceof MacData)
        {
            return (MacData)obj;
        }
        else if (obj instanceof ASN1Sequence)
        {
            return new MacData((ASN1Sequence)obj);
        }

        throw new IllegalArgumentException("unknown object in factory");
    }

    public MacData(
        ASN1Sequence seq)
    {
        this.digInfo = DigestInfo.getInstance(seq.getObjectAt(0));

        this.salt = ((DEROctetString)seq.getObjectAt(1)).getOctets();

        if (seq.getSize() == 3)
        {
            this.iterationCount = ((DERInteger)seq.getObjectAt(2)).getValue();
        }
        else
        {
            this.iterationCount = BigInteger.valueOf(1);
        }
    }

    public MacData(
        DigestInfo  digInfo,
        byte[]      salt,
        int         iterationCount)
    {
        this.digInfo = digInfo;
        this.salt = salt;
        this.iterationCount = BigInteger.valueOf(iterationCount);
    }

    public DigestInfo getMac()
    {
        return digInfo;
    }

    public byte[] getSalt()
    {
        return salt;
    }

    public BigInteger getIterationCount()
    {
        return iterationCount;
    }

    public DERObject getDERObject()
    {
        ASN1Sequence  seq = new DERConstructedSequence();

        seq.addObject(digInfo);
        seq.addObject(new DEROctetString(salt));
        seq.addObject(new DERInteger(iterationCount));

        return seq;
    }
}
