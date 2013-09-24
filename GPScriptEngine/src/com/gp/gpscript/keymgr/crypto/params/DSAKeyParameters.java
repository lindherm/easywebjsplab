package com.gp.gpscript.keymgr.crypto.params;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.gp.gpscript.keymgr.crypto.CipherParameters;

public class DSAKeyParameters
    extends AsymmetricKeyParameter
{
    private DSAParameters    params;

    public DSAKeyParameters(
        boolean         isPrivate,
        DSAParameters   params)
    {
        super(isPrivate);

        this.params = params;
    }

    public DSAParameters getParameters()
    {
        return params;
    }
}
