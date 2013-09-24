package com.gp.gpscript.keymgr.crypto.params;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.gp.gpscript.keymgr.crypto.KeyGenerationParameters;

public class DHKeyGenerationParameters
    extends KeyGenerationParameters
{
    private int             certainty;
    private DHParameters    params;

    public DHKeyGenerationParameters(
        SecureRandom    random,
        DHParameters    params)
    {
        super(random, params.getP().bitLength() - 1);

        this.params = params;
    }

    public DHParameters getParameters()
    {
        return params;
    }
}
