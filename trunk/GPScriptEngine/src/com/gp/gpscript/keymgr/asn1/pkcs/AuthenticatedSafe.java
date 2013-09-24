package com.gp.gpscript.keymgr.asn1.pkcs;

import com.gp.gpscript.keymgr.asn1.*;

public class AuthenticatedSafe
    implements DEREncodable
{
    ContentInfo[]    info;

    public AuthenticatedSafe(
        ASN1Sequence  seq)
    {
        info = new ContentInfo[seq.getSize()];

        for (int i = 0; i != seq.getSize(); i++)
        {
            info[i] = ContentInfo.getInstance(seq.getObjectAt(i));
        }
    }

    public AuthenticatedSafe(
        ContentInfo[]       info)
    {
        this.info = info;
    }

    public ContentInfo[] getContentInfo()
    {
        return info;
    }

    public DERObject getDERObject()
    {
        ASN1Sequence  seq = new BERConstructedSequence();

        for (int i = 0; i != info.length; i++)
        {
            seq.addObject(info[i]);
        }

        return seq;
    }
}
