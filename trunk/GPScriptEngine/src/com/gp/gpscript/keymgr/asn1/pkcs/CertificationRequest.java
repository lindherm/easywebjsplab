package com.gp.gpscript.keymgr.asn1.pkcs;

import java.io.*;
import java.util.*;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.asn1.x509.*;

/**
 * PKCS10
 *
 * <pre>
 * CertificationRequest ::= SEQUENCE {
 *   certificationRequestInfo  CertificationRequestInfo,
 *   signatureAlgorithm        AlgorithmIdentifier{{ SignatureAlgorithms }},
 *   signature                 BIT STRING
 * }
 * </pre>
 */
public class CertificationRequest
    implements DEREncodable
{
    protected CertificationRequestInfo reqInfo = null;
    protected AlgorithmIdentifier sigAlgId = null;
    protected DERBitString sigBits = null;

    protected CertificationRequest()
    {
    }

    public CertificationRequest(
        CertificationRequestInfo requestInfo,
        AlgorithmIdentifier     algorithm,
        DERBitString            signature)
    {
        this.reqInfo = requestInfo;
        this.sigAlgId = algorithm;
        this.sigBits = signature;
    }

    public CertificationRequest(
        ASN1Sequence seq)
    {
        reqInfo = CertificationRequestInfo.getInstance(seq.getObjectAt(0));
        sigAlgId = AlgorithmIdentifier.getInstance(seq.getObjectAt(1));
        sigBits = (DERBitString)seq.getObjectAt(2);
    }

    public CertificationRequestInfo getCertificationRequestInfo()
    {
        return reqInfo;
    }

    public AlgorithmIdentifier getSignatureAlgorithm()
    {
        return sigAlgId;
    }

    public DERBitString getSignature()
    {
        return sigBits;
    }

    public DERObject getDERObject()
    {
        // Construct the CertificateRequest
        DERConstructedSequence  seq = new DERConstructedSequence();

        seq.addObject(reqInfo);
        seq.addObject(sigAlgId);
        seq.addObject(sigBits);

        return seq;
    }
}
