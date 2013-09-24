package com.gp.gpscript.keymgr.asn1.x9;

import java.math.BigInteger;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.math.ec.ECConstants;
import com.gp.gpscript.keymgr.math.ec.ECCurve;
import com.gp.gpscript.keymgr.math.ec.ECPoint;


/**
 * ASN.1 def for Elliptic-Curve ECParameters structure. See
 * X9.62, for further details.
 */
public class X9ECParameters
    implements DEREncodable, X9ObjectIdentifiers
{
    private static BigInteger   ONE = BigInteger.valueOf(1);

    private X9FieldID           fieldID;
	private ECCurve             curve;
	private ECPoint             g;
	private BigInteger          n;
	private BigInteger          h;
    private byte[]              seed;

	public X9ECParameters(
        DERConstructedSequence  seq)
    {
        if (!(seq.getObjectAt(0) instanceof DERInteger)
           || !((DERInteger)seq.getObjectAt(0)).getValue().equals(ONE))
        {
            throw new IllegalArgumentException("bad version in X9ECParameters");
        }

        X9Curve     x9c = new X9Curve(
                        new X9FieldID((DERConstructedSequence)seq.getObjectAt(1)),
                        (DERConstructedSequence)seq.getObjectAt(2));

        this.curve = x9c.getCurve();
		this.g = new X9ECPoint(curve, (DEROctetString)seq.getObjectAt(3)).getPoint();
		this.n = ((DERInteger)seq.getObjectAt(4)).getValue();
        this.seed = x9c.getSeed();

        if (seq.getSize() == 6)
        {
		    this.h = ((DERInteger)seq.getObjectAt(5)).getValue();
        }
    }

	public X9ECParameters(
		ECCurve     curve,
		ECPoint     g,
		BigInteger  n)
	{
        this(curve, g, n, ONE, null);
	}

	public X9ECParameters(
		ECCurve     curve,
		ECPoint     g,
		BigInteger  n,
        BigInteger  h)
	{
        this(curve, g, n, h, null);
	}

	public X9ECParameters(
		ECCurve     curve,
		ECPoint     g,
		BigInteger  n,
        BigInteger  h,
        byte[]      seed)
	{
		this.curve = curve;
		this.g = g;
		this.n = n;
		this.h = h;
        this.seed = seed;

        if (curve instanceof ECCurve.Fp)
        {
            this.fieldID = new X9FieldID(prime_field, ((ECCurve.Fp)curve).getQ());
        }
        else
        {
            this.fieldID = new X9FieldID(characteristic_two_field, null);
        }
	}

	public ECCurve getCurve()
	{
		return curve;
	}

	public ECPoint getG()
	{
		return g;
	}

	public BigInteger getN()
	{
		return n;
	}

	public BigInteger getH()
	{
		return h;
	}

	public byte[] getSeed()
	{
		return seed;
	}

    /**
     * <pre>
     *  ECParameters ::= SEQUENCE {
     *      version         INTEGER { ecpVer1(1) } (ecpVer1),
     *      fieldID         FieldID {{FieldTypes}},
     *      curve           X9Curve,
     *      base            X9ECPoint,
     *      order           INTEGER,
     *      cofactor        INTEGER OPTIONAL
     *  }
     * </pre>
     */
    public DERObject getDERObject()
    {
        DERConstructedSequence seq = new DERConstructedSequence();

        seq.addObject(new DERInteger(1));
        seq.addObject(fieldID);
        seq.addObject(new X9Curve(curve, seed));
        seq.addObject(new X9ECPoint(g));
        seq.addObject(new DERInteger(n));

        if (!h.equals(BigInteger.valueOf(1)))
        {
            seq.addObject(new DERInteger(h));
        }

        return seq;
    }
}
