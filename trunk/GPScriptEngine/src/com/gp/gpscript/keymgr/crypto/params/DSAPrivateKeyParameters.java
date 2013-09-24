package com.gp.gpscript.keymgr.crypto.params;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.gp.gpscript.keymgr.crypto.CipherParameters;

public class DSAPrivateKeyParameters
    extends DSAKeyParameters
{
    private BigInteger      x;
    private DSAParameters    params;

    public DSAPrivateKeyParameters(
        BigInteger      x,
        DSAParameters   params)
    {
        super(true, params);

        this.x = x;
    }

    public BigInteger getX()
    {
        return x;
    }
}
