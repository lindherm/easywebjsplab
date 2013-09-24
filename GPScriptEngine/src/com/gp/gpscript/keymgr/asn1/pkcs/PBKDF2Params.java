package com.gp.gpscript.keymgr.asn1.pkcs;

import java.util.*;
import java.math.BigInteger;

import com.gp.gpscript.keymgr.asn1.*;

public class PBKDF2Params
    extends KeyDerivationFunc
{
    DERObjectIdentifier id;
    DEROctetString      octStr;
    DERInteger          iterationCount;
    DERInteger          keyLength;

    PBKDF2Params(
        DERConstructedSequence  seq)
    {
        super(seq);

        Enumeration e = seq.getObjects();

        id = (DERObjectIdentifier)e.nextElement();

        DERConstructedSequence  params = (DERConstructedSequence)e.nextElement();

        e = params.getObjects();

        octStr = (DEROctetString)e.nextElement();
        iterationCount = (DERInteger)e.nextElement();

        if (e.hasMoreElements())
        {
            keyLength = (DERInteger)e.nextElement();
        }
        else
        {
            keyLength = null;
        }
    }

    public byte[] getSalt()
    {
        return octStr.getOctets();
    }

    public BigInteger getIterationCount()
    {
        return iterationCount.getValue();
    }

    public BigInteger getKeyLength()
    {
        if (keyLength != null)
        {
            return keyLength.getValue();
        }

        return null;
    }

    public DERObject getDERObject()
    {
        DERConstructedSequence  seq = new DERConstructedSequence();
        DERConstructedSequence  subSeq = new DERConstructedSequence();

        seq.addObject(id);
        subSeq.addObject(octStr);
        subSeq.addObject(iterationCount);

        if (keyLength != null)
        {
            subSeq.addObject(keyLength);
        }

        seq.addObject(subSeq);

        return seq;
    }
}
