package com.gp.gpscript.keymgr.asn1.pkcs;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.asn1.x509.*;

public class IssuerAndSerialNumber
    implements DEREncodable
{
    X509Name    name;
    DERInteger  certSerialNumber;

    public static IssuerAndSerialNumber getInstance(
        Object  obj)
    {
        if (obj instanceof IssuerAndSerialNumber)
        {
            return (IssuerAndSerialNumber)obj;
        }
        else if (obj instanceof ASN1Sequence)
        {
            return new IssuerAndSerialNumber((ASN1Sequence)obj);
        }

        throw new IllegalArgumentException("unknown object in factory");
    }

    public IssuerAndSerialNumber(
        ASN1Sequence    seq)
    {
        this.name = X509Name.getInstance(seq.getObjectAt(0));
        this.certSerialNumber = (DERInteger)seq.getObjectAt(1);
    }

    public IssuerAndSerialNumber(
        X509Name    name,
        DERInteger  certSerialNumber)
    {
        this.name = name;
        this.certSerialNumber = certSerialNumber;
    }

    public X509Name getName()
    {
        return name;
    }

    public DERInteger getCertificateSerialNumber()
    {
        return certSerialNumber;
    }

    public DERObject getDERObject()
    {
        ASN1Sequence    s = new DERConstructedSequence();

        s.addObject(name);
        s.addObject(certSerialNumber);

        return s;
    }
}
