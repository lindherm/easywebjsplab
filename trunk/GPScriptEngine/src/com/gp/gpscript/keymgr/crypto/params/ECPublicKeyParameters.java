package com.gp.gpscript.keymgr.crypto.params;

import java.math.BigInteger;

import com.gp.gpscript.keymgr.math.ec.ECPoint;

public class ECPublicKeyParameters
    extends ECKeyParameters
{
    ECPoint Q;

    public ECPublicKeyParameters(
        ECPoint             Q,
        ECDomainParameters  params)
    {
        super(false, params);
        this.Q = Q;
    }

    public ECPoint getQ()
    {
        return Q;
    }
}
