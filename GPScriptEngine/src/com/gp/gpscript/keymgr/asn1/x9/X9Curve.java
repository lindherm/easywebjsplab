package com.gp.gpscript.keymgr.asn1.x9;

import java.math.BigInteger;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.math.ec.ECCurve;
import com.gp.gpscript.keymgr.math.ec.ECPoint;


/**
 * ASN.1 def for Elliptic-Curve Curve structure. See
 * X9.62, for further details.
 */
public class X9Curve
    implements DEREncodable, X9ObjectIdentifiers
{
    private ECCurve     curve;
    private byte[]      seed;

	public X9Curve(
		ECCurve     curve)
	{
        this.curve = curve;
        this.seed = null;
	}

	public X9Curve(
		ECCurve     curve,
        byte[]      seed)
	{
        this.curve = curve;
        this.seed = seed;
	}

    public X9Curve(
        X9FieldID               fieldID,
        DERConstructedSequence  seq)
    {
        if (fieldID.getIdentifier().equals(prime_field))
        {
            BigInteger      q = ((DERInteger)fieldID.getParameters()).getValue();
            X9FieldElement  x9A = new X9FieldElement(true, q, (DEROctetString)seq.getObjectAt(0));
            X9FieldElement  x9B = new X9FieldElement(true, q, (DEROctetString)seq.getObjectAt(1));
            curve = new ECCurve.Fp(q, x9A.getValue().toBigInteger(), x9B.getValue().toBigInteger());
        }
        else
        {
            throw new RuntimeException("not implemented");
        }

        if (seq.getSize() == 3)
        {
            seed = ((DERBitString)seq.getObjectAt(2)).getBytes();
        }
    }

    public ECCurve  getCurve()
    {
        return curve;
    }

    public byte[]   getSeed()
    {
        return seed;
    }

    /**
     * <pre>
     *  Curve ::= SEQUENCE {
     *      a               FieldElement,
     *      b               FieldElement,
     *      seed            BIT STRING      OPTIONAL
     *  }
     * </pre>
     */
    public DERObject getDERObject()
    {
        DERConstructedSequence seq = new DERConstructedSequence();

        seq.addObject(new X9FieldElement(curve.getA()).getDERObject());
        seq.addObject(new X9FieldElement(curve.getB()).getDERObject());

        if (seed != null)
        {
            seq.addObject(new DERBitString(seed));
        }

        return seq;
    }
}
