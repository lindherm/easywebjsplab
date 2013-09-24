package com.gp.gpscript.keymgr.asn1.x509;

import java.math.BigInteger;

import com.gp.gpscript.keymgr.asn1.*;

/**
 * <pre>
 * CRLNumber::= INTEGER(0..MAX)
 * </pre>
 */
public class CRLNumber
    extends DERInteger
{

    public CRLNumber(
        BigInteger number)
    {
        super(number);
    }

    public BigInteger getCRLNumber()
    {
        return getPositiveValue();
    }
}
