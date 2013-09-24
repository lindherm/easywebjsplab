package com.gp.gpscript.keymgr.asn1.pkcs;

import java.util.Enumeration;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.asn1.x509.*;

/**
 * a PKCS#7 signer info object. - note: TODO attributes a currently ignored
 */
public class SignerInfo
    implements DEREncodable
{
    private DERInteger              version;
    private IssuerAndSerialNumber   issuerAndSerialNumber;
    private AlgorithmIdentifier     digAlgorithm;
    private DERConstructedSet       authenticatedAttributes;
    private AlgorithmIdentifier     digEncryptionAlgorithm;
    private ASN1OctetString         encryptedDigest;
    private DERConstructedSet       unauthenticatedAttributes;

    public static SignerInfo getInstance(
        Object  o)
    {
        if (o instanceof SignerInfo)
        {
            return (SignerInfo)o;
        }
        else if (o instanceof ASN1Sequence)
        {
            return new SignerInfo((ASN1Sequence)o);
        }

        throw new IllegalArgumentException("unknown object in factory");
    }

    public SignerInfo(
        ASN1Sequence seq)
    {
        Enumeration     e = seq.getObjects();

        version = (DERInteger)e.nextElement();
        issuerAndSerialNumber = IssuerAndSerialNumber.getInstance(e.nextElement());
        digAlgorithm = AlgorithmIdentifier.getInstance(e.nextElement());

        Object obj = e.nextElement();

        if (obj instanceof ASN1TaggedObject)
        {
            authenticatedAttributes = null;
            digEncryptionAlgorithm = AlgorithmIdentifier.getInstance(e.nextElement());
        }
        else
        {
            authenticatedAttributes = null;
            digEncryptionAlgorithm = AlgorithmIdentifier.getInstance(obj);
        }

        encryptedDigest = DEROctetString.getInstance(e.nextElement());

        if (e.hasMoreElements())
        {
            unauthenticatedAttributes = null;
        }
        else
        {
            unauthenticatedAttributes = null;
        }
    }

    public DERInteger getVersion()
    {
        return version;
    }

    public IssuerAndSerialNumber getIssuerAndSerialNumber()
    {
        return issuerAndSerialNumber;
    }

    public AlgorithmIdentifier getDigestAlgorithm()
    {
        return digAlgorithm;
    }

    public ASN1OctetString getEncryptedDigest()
    {
        return encryptedDigest;
    }

    public AlgorithmIdentifier getDigestEncryptionAlgorithm()
    {
        return digEncryptionAlgorithm;
    }

    /**
     * <pre>
     *  SignerInfo ::= SEQUENCE {
     *      version Version,
     *      issuerAndSerialNumber IssuerAndSerialNumber,
     *      digestAlgorithm DigestAlgorithmIdentifier,
     *      authenticatedAttributes [0] IMPLICIT Attributes OPTIONAL,
     *      digestEncryptionAlgorithm DigestEncryptionAlgorithmIdentifier,
     *      encryptedDigest EncryptedDigest,
     *      unauthenticatedAttributes [1] IMPLICIT Attributes OPTIONAL
     *  }
     *
     *  EncryptedDigest ::= OCTET STRING
     *
     *  DigestAlgorithmIdentifier ::= AlgorithmIdentifier
     *
     *  DigestEncryptionAlgorithmIdentifier ::= AlgorithmIdentifier
     * </pre>
     */
    public DERObject getDERObject()
    {
        ASN1Sequence seq = new DERConstructedSequence();

        seq.addObject(version);
        seq.addObject(issuerAndSerialNumber);
        seq.addObject(digAlgorithm);

        if (authenticatedAttributes != null)
        {
            seq.addObject(new DERTaggedObject(false, 0, authenticatedAttributes));
        }

        seq.addObject(digEncryptionAlgorithm);
        seq.addObject(encryptedDigest);

        if (unauthenticatedAttributes != null)
        {
            seq.addObject(new DERTaggedObject(false, 1, unauthenticatedAttributes));
        }

        return seq;
    }
}
