package com.gp.gpscript.keymgr.asn1.pkcs;

import java.util.Enumeration;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.asn1.x509.*;

/**
 * a PKCS#7 signed data object.
 */
public class SignedData
    implements DEREncodable, PKCSObjectIdentifiers
{
    private DERInteger              version;
    private DERConstructedSet       digestAlgorithms;
    private ContentInfo             contentInfo;
    private ASN1Set                 certificates;
    private ASN1Set                 crls;
    private DERConstructedSet       signerInfos;

    public static SignedData getInstance(
        Object  o)
    {
        if (o instanceof SignedData)
        {
            return (SignedData)o;
        }
        else if (o instanceof ASN1Sequence)
        {
            return new SignedData((ASN1Sequence)o);
        }

        throw new IllegalArgumentException("unknown object in factory");
    }

	public SignedData(
		DERInteger        _version,
		DERConstructedSet _digestAlgorithms,
		ContentInfo       _contentInfo,
		ASN1Set           _certificates,
		ASN1Set           _crls,
		DERConstructedSet _signerInfos)
	{
		version          = _version;
		digestAlgorithms = _digestAlgorithms;
		contentInfo      = _contentInfo;
		certificates     = _certificates;
		crls             = _crls;
		signerInfos      = _signerInfos;
	}

    public SignedData(
        ASN1Sequence seq)
    {
        Enumeration     e = seq.getObjects();

        version = (DERInteger)e.nextElement();
        digestAlgorithms = ((DERConstructedSet)e.nextElement());
        contentInfo = ContentInfo.getInstance(e.nextElement());

        while (e.hasMoreElements())
        {
            DERObject o = (DERObject)e.nextElement();

            //
            // an interesting feature of SignedData is that there appear to be varying implementations...
            // for the moment we ignore anything which doesn't fit.
            //
            if (o instanceof DERTaggedObject)
            {
                DERTaggedObject tagged = (DERTaggedObject)o;

                switch (tagged.getTagNo())
                {
                case 0:
                    certificates = ASN1Set.getInstance(tagged, false);
                    break;
                case 1:
                    crls = ASN1Set.getInstance(tagged, false);
                    break;
                default:
                    throw new IllegalArgumentException("unknown tag value " + tagged.getTagNo());
                }
            }
            else
            {
                signerInfos = (DERConstructedSet)o;
            }
        }
    }

    public DERInteger getVersion()
    {
        return version;
    }

    public DERConstructedSet getDigestAlgorithms()
    {
        return digestAlgorithms;
    }

    public ContentInfo getContentInfo()
    {
        return contentInfo;
    }

    public ASN1Set getCertificates()
    {
        return certificates;
    }

    public ASN1Set getCRLS()
    {
        return crls;
    }

    public DERConstructedSet getSignerInfos()
    {
        return signerInfos;
    }

    /**
     * <pre>
     *  SignedData ::= SEQUENCE {
     *      version Version,
     *      digestAlgorithms DigestAlgorithmIdentifiers,
     *      contentInfo ContentInfo,
     *      certificates
     *          [0] IMPLICIT ExtendedCertificatesAndCertificates
     *                   OPTIONAL,
     *      crls
     *          [1] IMPLICIT CertificateRevocationLists OPTIONAL,
     *      signerInfos SignerInfos }
     * </pre>
     */
    public DERObject getDERObject()
    {
        ASN1Sequence seq = new DERConstructedSequence();

        seq.addObject(version);
        seq.addObject(digestAlgorithms);
        seq.addObject(contentInfo);

        if (certificates != null)
        {
            seq.addObject(new DERTaggedObject(false, 0, certificates));
        }

        if (crls != null)
        {
            seq.addObject(new DERTaggedObject(false, 1, crls));
        }

        seq.addObject(signerInfos);

        return seq;
    }
}
