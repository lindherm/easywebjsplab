package com.gp.gpscript.keymgr.asn1.x9;

import java.math.BigInteger;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.math.ec.ECCurve;
import com.gp.gpscript.keymgr.math.ec.ECPoint;


/**
 * ASN.1 def for Elliptic-Curve Field ID structure. See
 * X9.62, for further details.
 */
public class X9FieldID
    implements DEREncodable, X9ObjectIdentifiers
{
    private DERObjectIdentifier     id;
    private DERObject               parameters;

	public X9FieldID(
        DERObjectIdentifier id,
		BigInteger          primeP)
	{
        this.id = id;
        this.parameters = new DERInteger(primeP);
	}

    public X9FieldID(
        DERConstructedSequence  seq)
    {
        this.id = (DERObjectIdentifier)seq.getObjectAt(0);
        this.parameters = (DERObject)seq.getObjectAt(1);
    }

    public DERObjectIdentifier getIdentifier()
    {
        return id;
    }

    public DERObject getParameters()
    {
        return parameters;
    }

    /**
     * Produce a DER encoding of the following structure.
     * <pre>
     *  FieldID ::= SEQUENCE {
     *      fieldType       FIELD-ID.&amp;id({IOSet}),
     *      parameters      FIELD-ID.&amp;Type({IOSet}{&#64;fieldType})
     *  }
     * </pre>
     */
    public DERObject getDERObject()
    {
        DERConstructedSequence seq = new DERConstructedSequence();

        seq.addObject(this.id);
        seq.addObject(this.parameters);

        return seq;
    }
}
