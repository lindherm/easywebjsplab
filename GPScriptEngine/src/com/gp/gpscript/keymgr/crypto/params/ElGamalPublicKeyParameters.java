package com.gp.gpscript.keymgr.crypto.params;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.gp.gpscript.keymgr.crypto.CipherParameters;

public class ElGamalPublicKeyParameters
    extends ElGamalKeyParameters
{
    private BigInteger      y;

    public ElGamalPublicKeyParameters(
        BigInteger      y,
        ElGamalParameters    params)
    {
        super(false, params);

        this.y = y;
    }

    public BigInteger getY()
    {
        return y;
    }

    public boolean equals(
        Object  obj)
    {
        if (!(obj instanceof ElGamalPublicKeyParameters))
        {
            return false;
        }

        ElGamalPublicKeyParameters   pKey = (ElGamalPublicKeyParameters)obj;

        if (!pKey.getY().equals(y))
        {
            return false;
        }

        return super.equals(obj);
    }
}
