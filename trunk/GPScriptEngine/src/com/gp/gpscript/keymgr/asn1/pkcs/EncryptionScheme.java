package com.gp.gpscript.keymgr.asn1.pkcs;

import java.util.*;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.asn1.x509.*;

public class EncryptionScheme
    extends AlgorithmIdentifier
{
    DERObject   objectId;
    DERObject   obj;

    EncryptionScheme(
        DERConstructedSequence  seq)
    {
        super(seq);

        objectId = (DERObject)seq.getObjectAt(0);
        obj = (DERObject)seq.getObjectAt(1);
    }

    public DERObject getObject()
    {
        return obj;
    }

    public DERObject getDERObject()
    {
        DERConstructedSequence  seq = new DERConstructedSequence();

        seq.addObject(objectId);
        seq.addObject(obj);

        return seq;
    }
}
