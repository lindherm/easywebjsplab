package com.gp.gpscript.keymgr.asn1.pkcs;

import java.io.*;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.asn1.x509.*;

public class CertBag
    implements DEREncodable
{
	DERConstructedSequence		seq;
    DERObjectIdentifier         certId;
    DERObject                   certValue;

	public CertBag(
		DERConstructedSequence	seq)
	{
        this.seq = (DERConstructedSequence)seq;
        this.certId = (DERObjectIdentifier)seq.getObjectAt(0);
        this.certValue = ((DERTaggedObject)seq.getObjectAt(1)).getObject();
	}

    public CertBag(
        DERObjectIdentifier certId,
        DERObject           certValue)
    {
        this.certId = certId;
        this.certValue = certValue;
    }

	public DERObjectIdentifier getCertId()
	{
		return certId;
	}

    public DERObject getCertValue()
    {
		return certValue;
    }

    public DERObject getDERObject()
    {
        DERConstructedSequence  seq = new DERConstructedSequence();

        seq.addObject(certId);
        seq.addObject(new DERTaggedObject(0, certValue));

        return seq;
    }
}
