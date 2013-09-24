package com.gp.gpscript.keymgr.asn1.x509;

import java.math.BigInteger;
import java.util.Vector;
import java.util.Enumeration;

import com.gp.gpscript.keymgr.asn1.*;

public class CertificatePolicies
    implements DEREncodable
{
    static final DERObjectIdentifier anyPolicy = new DERObjectIdentifier("2.5.29.32.0");

    Vector policies = new Vector();

    public static CertificatePolicies getInstance(
        ASN1TaggedObject obj,
        boolean explicit)
    {
        return getInstance(ASN1Sequence.getInstance(obj, explicit));
    }

    public static CertificatePolicies getInstance(
        Object  obj)
    {
        if (obj instanceof CertificatePolicies)
        {
            return (CertificatePolicies)obj;
        }
        else if (obj instanceof ASN1Sequence)
        {
            return new CertificatePolicies((ASN1Sequence)obj);
        }

        throw new IllegalArgumentException("unknown object in factory");
    }

    public CertificatePolicies(
        ASN1Sequence   seq)
    {
        Enumeration e = seq.getObjects();
        while (e.hasMoreElements())
        {
            ASN1Sequence s = (ASN1Sequence)e.nextElement();
            policies.addElement(s.getObjectAt(0));
        }
        // For now we just don't handle PolicyQualifiers
    }

    public CertificatePolicies(
        String p)
    {
        policies.addElement(new DERObjectIdentifier(p));
    }


    public void addPolicy(
        String p)
    {
        policies.addElement(new DERObjectIdentifier(p));
    }
    public String getPolicy(int nr)
    {
        if (policies.size() > nr)
            return ((DERObjectIdentifier)policies.elementAt(nr)).getId();

        return null;
    }


    /**
     * <pre>
     * certificatePolicies ::= SEQUENCE SIZE (1..MAX) OF PolicyInformation
     *
     * PolicyInformation ::= SEQUENCE {
     *   policyIdentifier   CertPolicyId,
     *   policyQualifiers   SEQUENCE SIZE (1..MAX) OF
     *                           PolicyQualifierInfo OPTIONAL }
     *
     * CertPolicyId ::= OBJECT IDENTIFIER
     *
     * PolicyQualifierInfo ::= SEQUENCE {
     *   policyQualifierId  PolicyQualifierId,
     *   qualifier          ANY DEFINED BY policyQualifierId }
     *
     * PolicyQualifierId ::=
     *   OBJECT IDENTIFIER ( id-qt-cps | id-qt-unotice )
     * </pre>
     */
    public DERObject getDERObject()
    {
        DERConstructedSequence  seq = new DERConstructedSequence();

        // We only do policyIdentifier yet...
        for (int i=0;i<policies.size();i++)
        {
            DERConstructedSequence  iseq = new DERConstructedSequence();
            iseq.addObject((DERObjectIdentifier)policies.elementAt(i));
            seq.addObject(iseq);
        }
        return seq;
    }

    public String toString()
    {
        String p = null;
        for (int i=0;i<policies.size();i++)
        {
            if (p != null)
                p += ", ";
            p += ((DERObjectIdentifier)policies.elementAt(i)).getId();
        }
        return "CertificatePolicies: "+p;
    }
}
