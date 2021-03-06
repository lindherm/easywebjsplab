package com.gp.gpscript.keymgr.asn1.x509;

import com.gp.gpscript.keymgr.asn1.*;

public class CRLDistPoint
    implements DEREncodable
{
    ASN1Sequence  seq = null;

    public static CRLDistPoint getInstance(
        ASN1TaggedObject obj,
        boolean          explicit)
    {
        return getInstance(ASN1Sequence.getInstance(obj, explicit));
    }

    public static CRLDistPoint getInstance(
        Object  obj)
    {
        if (obj instanceof CRLDistPoint)
        {
            return (CRLDistPoint)obj;
        }
        else if (obj instanceof ASN1Sequence)
        {
            return new CRLDistPoint((ASN1Sequence)obj);
        }

        throw new IllegalArgumentException("unknown object in factory");
    }

    public CRLDistPoint(
        ASN1Sequence seq)
    {
        this.seq = seq;
    }

    public CRLDistPoint(
        DistributionPoint[] points)
    {
        seq = new DERConstructedSequence();

        for (int i = 0; i != points.length; i++)
        {
            seq.addObject(points[i]);
        }
    }

    /**
     * <pre>
     * CRLDistPoint ::= SEQUENCE SIZE {1..MAX} OF DistributionPoint
     * </pre>
     */
    public DERObject getDERObject()
    {
        return seq;
    }
}
