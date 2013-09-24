package com.gp.gpscript.keymgr.crypto.params;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.gp.gpscript.keymgr.crypto.KeyGenerationParameters;

public class ElGamalKeyGenerationParameters
    extends KeyGenerationParameters
{
    private int             certainty;
    private ElGamalParameters    params;

    public ElGamalKeyGenerationParameters(
        SecureRandom        random,
        ElGamalParameters   params)
    {
        super(random, params.getP().bitLength() - 1);

        this.params = params;
    }

    public ElGamalParameters getParameters()
    {
        return params;
    }
}
