package com.gp.gpscript.keymgr.asn1.x509;

import com.gp.gpscript.keymgr.asn1.*;

public class DistributionPoint
    implements DEREncodable
{
    ASN1Sequence  seq = null;

    public static DistributionPoint getInstance(
        ASN1TaggedObject obj,
        boolean          explicit)
    {
        return getInstance(ASN1Sequence.getInstance(obj, explicit));
    }

    public static DistributionPoint getInstance(
        Object obj)
    {
        if(obj == null || obj instanceof DistributionPoint)
        {
            return (DistributionPoint)obj;
        }

        if(obj instanceof ASN1Sequence)
        {
            return new DistributionPoint((ASN1Sequence)obj);
        }

        throw new IllegalArgumentException("Invalid DistributionPoint: " + obj.getClass().getName());
    }

    public DistributionPoint(
        ASN1Sequence seq)
    {
        this.seq = seq;
    }

    public DistributionPoint(
        DistributionPointName   distributionPoint,
        ReasonFlags             reasons,
        GeneralNames            cRLIssuer)
    {
        seq = new DERConstructedSequence();

        if (distributionPoint != null)
        {
            seq.addObject(new DERTaggedObject(0, distributionPoint));
        }

        if (reasons != null)
        {
            seq.addObject(new DERTaggedObject(1, reasons));
        }

        if (cRLIssuer != null)
        {
            seq.addObject(new DERTaggedObject(2, cRLIssuer));
        }
    }

    /**
     * <pre>
     * DistributionPoint ::= SEQUENCE {
     *      distributionPoint [0] DistributionPointName OPTIONAL,
     *      reasons           [1] ReasonFlags OPTIONAL,
     *      cRLIssuer         [2] GeneralNames OPTIONAL
     * }
     * </pre>
     */
    public DERObject getDERObject()
    {
        return seq;
    }
}
