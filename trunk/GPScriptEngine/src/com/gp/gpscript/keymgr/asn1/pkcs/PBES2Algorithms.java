package com.gp.gpscript.keymgr.asn1.pkcs;

import java.util.Enumeration;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.asn1.x509.*;

/**
 * @deprecated - use AlgorithmIdentifier and PBES2Params
 */
public class PBES2Algorithms
    extends AlgorithmIdentifier implements PKCSObjectIdentifiers
{
    private DERObjectIdentifier objectId;
    private KeyDerivationFunc   func;
    private EncryptionScheme    scheme;

    public PBES2Algorithms(
        DERConstructedSequence  obj)
    {
        super(obj);

        Enumeration     e = obj.getObjects();

        objectId = (DERObjectIdentifier)e.nextElement();

        DERConstructedSequence seq = (DERConstructedSequence)e.nextElement();

        e = seq.getObjects();

        DERConstructedSequence  funcSeq = (DERConstructedSequence)e.nextElement();

        if (funcSeq.getObjectAt(0).equals(id_PBKDF2))
        {
            func = new PBKDF2Params(funcSeq);
        }
        else
        {
            func = new KeyDerivationFunc(funcSeq);
        }

        scheme = new EncryptionScheme((DERConstructedSequence)e.nextElement());
    }

    public DERObjectIdentifier getObjectId()
    {
        return objectId;
    }

    public KeyDerivationFunc getKeyDerivationFunc()
    {
        return func;
    }

    public EncryptionScheme getEncryptionScheme()
    {
        return scheme;
    }

    public DERObject getDERObject()
    {
        DERConstructedSequence  seq = new DERConstructedSequence();
        DERConstructedSequence  subSeq = new DERConstructedSequence();

        seq.addObject(objectId);

        subSeq.addObject(func);
        subSeq.addObject(scheme);
        seq.addObject(subSeq);

        return seq;
    }
}
