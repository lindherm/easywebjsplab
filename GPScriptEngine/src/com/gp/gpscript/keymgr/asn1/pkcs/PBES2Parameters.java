package com.gp.gpscript.keymgr.asn1.pkcs;

import java.util.Enumeration;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.asn1.x509.*;

public class PBES2Parameters
    implements PKCSObjectIdentifiers, DEREncodable
{
    private DERObjectIdentifier objectId;
    private KeyDerivationFunc   func;
    private EncryptionScheme    scheme;

    public PBES2Parameters(
        DERConstructedSequence  obj)
    {
        Enumeration e = obj.getObjects();
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

        seq.addObject(func);
        seq.addObject(scheme);

        return seq;
    }
}
