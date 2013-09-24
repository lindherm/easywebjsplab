package com.gp.gpscript.keymgr.asn1.pkcs;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.asn1.x509.*;

/**
 * <pre>
 *      EncryptedData ::= SEQUENCE {
 *           version Version,
 *           encryptedContentInfo EncryptedContentInfo
 *      }
 *
 *
 *      EncryptedContentInfo ::= SEQUENCE {
 *          contentType ContentType,
 *          contentEncryptionAlgorithm  ContentEncryptionAlgorithmIdentifier,
 *          encryptedContent [0] IMPLICIT EncryptedContent OPTIONAL
 *    }
 *
 *    EncryptedContent ::= OCTET STRING
 * </pre>
 */
public class EncryptedData
    implements DEREncodable
{
    DERConstructedSequence      data;
    DERObjectIdentifier         bagId;
    DERObject                   bagValue;

    public EncryptedData(
        ASN1Sequence seq)
    {
        int version = ((DERInteger)seq.getObjectAt(0)).getValue().intValue();

        if (version != 0)
        {
            throw new IllegalArgumentException("sequence not version 0");
        }

        this.data = (DERConstructedSequence)seq.getObjectAt(1);
    }

    public EncryptedData(
        DERObjectIdentifier     contentType,
        AlgorithmIdentifier     encryptionAlgorithm,
        DEREncodable            content)
    {
        data = new BERConstructedSequence();

        data.addObject(contentType);
        data.addObject(encryptionAlgorithm.getDERObject());
        data.addObject(new BERTaggedObject(false, 0, content));
    }

    public DERObjectIdentifier getContentType()
    {
        return (DERObjectIdentifier)data.getObjectAt(0);
    }

    public AlgorithmIdentifier getEncryptionAlgorithm()
    {
        return AlgorithmIdentifier.getInstance(data.getObjectAt(1));
    }

    public ASN1OctetString getContent()
    {
        if (data.getSize() == 3)
        {
            DERTaggedObject o = (DERTaggedObject)data.getObjectAt(2);

            return ASN1OctetString.getInstance(o.getObject());
        }

        return null;
    }

    public DERObject getDERObject()
    {
        DERConstructedSequence  seq = new BERConstructedSequence();

        seq.addObject(new DERInteger(0));
        seq.addObject(data);

        return seq;
    }
}
