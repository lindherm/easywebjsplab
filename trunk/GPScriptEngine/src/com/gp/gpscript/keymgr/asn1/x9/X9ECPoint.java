package com.gp.gpscript.keymgr.asn1.x9;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.math.ec.*;

/**
 * class for describing an ECPoint as a DER object.
 */
public class X9ECPoint
    implements DEREncodable
{
    ECPoint p;

    public X9ECPoint(
        ECPoint p)
    {
        this.p = p;
    }

    public X9ECPoint(
        ECCurve         c,
        DEROctetString  s)
    {
        this.p = c.decodePoint(s.getOctets());
    }

    public ECPoint getPoint()
    {
        return p;
    }

    /**
     * <pre>
     *  ECPoint ::= OCTET STRING
     * </pre>
     * <p>
     * Octet string produced using ECPoint.getEncoded().
     */
    public DERObject getDERObject()
    {
        return new DEROctetString(p.getEncoded());
    }
}