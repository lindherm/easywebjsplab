package com.gp.gpscript.keymgr.asn1.pkcs;

import java.io.*;
import java.math.BigInteger;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.asn1.util.*;
import com.gp.gpscript.keymgr.util.encoders.*;


/**
 * the infamous Pfx from PKCS12
 */
public class Pfx
    implements DEREncodable, PKCSObjectIdentifiers
{
    private ContentInfo             contentInfo;
    private MacData                 macData = null;

    public Pfx(
        ASN1Sequence   seq)
    {
        BigInteger  version = ((DERInteger)seq.getObjectAt(0)).getValue();
        if (version.intValue() != 3)
        {
            throw new IllegalArgumentException("wrong version for PFX PDU");
        }

        contentInfo = ContentInfo.getInstance(seq.getObjectAt(1));

        if (seq.getSize() == 3)
        {
            macData = MacData.getInstance(seq.getObjectAt(2));
        }
    }

    public Pfx(
        ContentInfo     contentInfo,
        MacData         macData)
    {
        this.contentInfo = contentInfo;
        this.macData = macData;
    }

    public ContentInfo getAuthSafe()
    {
        return contentInfo;
    }

    public MacData getMacData()
    {
        return macData;
    }

    public DERObject getDERObject()
    {
        BERConstructedSequence   seq = new BERConstructedSequence();

        seq.addObject(new DERInteger(3));
        seq.addObject(contentInfo);

        if (macData != null)
        {
            seq.addObject(macData);
        }

        return seq;
    }
}
