package com.gp.gpscript.keymgr.asn1.pkcs;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.asn1.x509.*;

public class SafeBag
    implements DEREncodable
{
    DERObjectIdentifier         bagId;
    DERObject                   bagValue;
    DERConstructedSet           bagAttributes;

    public SafeBag(
        DERObjectIdentifier     oid,
        DERObject               obj)
    {
        this.bagId = oid;
        this.bagValue = obj;
        this.bagAttributes = null;
    }

    public SafeBag(
        DERObjectIdentifier     oid,
        DERObject               obj,
        DERConstructedSet       bagAttributes)
    {
        this.bagId = oid;
        this.bagValue = obj;
        this.bagAttributes = bagAttributes;
    }

	public SafeBag(
		ASN1Sequence	seq)
	{
		this.bagId = (DERObjectIdentifier)seq.getObjectAt(0);
		this.bagValue = ((DERTaggedObject)seq.getObjectAt(1)).getObject();
        if (seq.getSize() == 3)
        {
            this.bagAttributes = (DERConstructedSet)seq.getObjectAt(2);
        }
	}

	public DERObjectIdentifier getBagId()
	{
		return bagId;
	}

    public DERObject getBagValue()
    {
		return bagValue;
    }

    public DERConstructedSet getBagAttributes()
    {
		return bagAttributes;
    }

    public DERObject getDERObject()
    {
        DERConstructedSequence seq = new DERConstructedSequence();

        seq.addObject(bagId);
        seq.addObject(new DERTaggedObject(0, bagValue));

        if (bagAttributes != null)
        {
            seq.addObject(bagAttributes);
        }

        return seq;
    }
}
