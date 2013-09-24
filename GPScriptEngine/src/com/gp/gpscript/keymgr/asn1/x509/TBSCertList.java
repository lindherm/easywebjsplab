
package com.gp.gpscript.keymgr.asn1.x509;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.asn1.pkcs.*;

/**
 * PKIX RFC-2459
 *
 * <pre>
 * TBSCertList  ::=  SEQUENCE  {
 *      version                 Version OPTIONAL,
 *                                   -- if present, shall be v2
 *      signature               AlgorithmIdentifier,
 *      issuer                  Name,
 *      thisUpdate              Time,
 *      nextUpdate              Time OPTIONAL,
 *      revokedCertificates     SEQUENCE OF SEQUENCE  {
 *           userCertificate         CertificateSerialNumber,
 *           revocationDate          Time,
 *           crlEntryExtensions      Extensions OPTIONAL
 *                                         -- if present, shall be v2
 *                                }  OPTIONAL,
 *      crlExtensions           [0]  EXPLICIT Extensions OPTIONAL
 *                                         -- if present, shall be v2
 *                                }
 * </pre>
 */

public class TBSCertList
    implements DEREncodable
{
    public class CRLEntry
        implements DEREncodable
    {
        DERConstructedSequence  seq;

        DERInteger        userCertificate;
        DERUTCTime        revocationDate;
        X509Extensions    crlEntryExtensions;

        public CRLEntry(
            DERConstructedSequence  seq)
        {
            this.seq = seq;

            userCertificate = (DERInteger)seq.getObjectAt(0);
            revocationDate = (DERUTCTime)seq.getObjectAt(1);
            if ( seq.getSize() == 3 )
            {
                crlEntryExtensions = X509Extensions.getInstance(seq.getObjectAt(2));
            }
        }

        public DERInteger getUserCertificate()
        {
            return userCertificate;
        }

        public DERUTCTime getRevocationDate()
        {
            return revocationDate;
        }

        public X509Extensions getExtensions()
        {
            return crlEntryExtensions;
        }

        public DERObject getDERObject()
        {
            return seq;
        }
    }

    ASN1Sequence     seq;

    DERInteger              version;
    AlgorithmIdentifier     signature;
    X509Name                issuer;
    DERUTCTime              thisUpdate;
    DERUTCTime              nextUpdate;
    CRLEntry[]              revokedCertificates;
    X509Extensions          crlExtensions;

    public static TBSCertList getInstance(
        ASN1TaggedObject obj,
        boolean          explicit)
    {
        return getInstance(ASN1Sequence.getInstance(obj, explicit));
    }

    public static TBSCertList getInstance(
        Object  obj)
    {
        if (obj instanceof TBSCertList)
        {
            return (TBSCertList)obj;
        }
        else if (obj instanceof ASN1Sequence)
        {
            return new TBSCertList((ASN1Sequence)obj);
        }

        throw new IllegalArgumentException("unknown object in factory");
    }

    public TBSCertList(
        ASN1Sequence  seq)
    {
        int seqPos = 0;

        this.seq = seq;

        if (seq.getObjectAt(seqPos) instanceof DERInteger )
        {
            version = (DERInteger)seq.getObjectAt(seqPos++);
        }
        else
        {
            version = new DERInteger(0);
        }

        if ( seq.getObjectAt(seqPos) instanceof AlgorithmIdentifier )
        {
            signature = (AlgorithmIdentifier)seq.getObjectAt(seqPos++);
        }
        else
        {
            signature = new AlgorithmIdentifier((DERConstructedSequence)seq.getObjectAt(seqPos++));
        }

        if ( seq.getObjectAt(seqPos) instanceof X509Name )
        {
            issuer = (X509Name)seq.getObjectAt(seqPos++);
        }
        else
        {
            issuer = new X509Name((DERConstructedSequence)seq.getObjectAt(seqPos++));
        }

        thisUpdate = (DERUTCTime)seq.getObjectAt(seqPos++);

        if ( seqPos < seq.getSize()
            && seq.getObjectAt(seqPos) instanceof DERUTCTime )
        {
            nextUpdate = (DERUTCTime)seq.getObjectAt(seqPos++);
        }

        if ( seqPos < seq.getSize()
            && !(seq.getObjectAt(seqPos) instanceof DERTaggedObject) )
        {
            DERConstructedSequence certs = (DERConstructedSequence)seq.getObjectAt(seqPos++);
            revokedCertificates = new CRLEntry[certs.getSize()];

            for ( int i = 0; i < revokedCertificates.length; i++ )
            {
                revokedCertificates[i] = new CRLEntry((DERConstructedSequence)certs.getObjectAt(i));
            }
        }

        if ( seqPos < seq.getSize()
            && seq.getObjectAt(seqPos) instanceof DERTaggedObject )
        {
            crlExtensions = X509Extensions.getInstance(seq.getObjectAt(seqPos++));
        }
    }

    public int getVersion()
    {
        return version.getValue().intValue() + 1;
    }

    public DERInteger getVersionNumber()
    {
        return version;
    }

    public AlgorithmIdentifier getSignature()
    {
        return signature;
    }

    public X509Name getIssuer()
    {
        return issuer;
    }

    public DERUTCTime getThisUpdate()
    {
        return thisUpdate;
    }

    public DERUTCTime getNextUpdate()
    {
        return nextUpdate;
    }

    public CRLEntry[] getRevokedCertificates()
    {
        return revokedCertificates;
    }

    public X509Extensions getExtensions()
    {
        return crlExtensions;
    }

    public DERObject getDERObject()
    {
        return seq;
    }
}
