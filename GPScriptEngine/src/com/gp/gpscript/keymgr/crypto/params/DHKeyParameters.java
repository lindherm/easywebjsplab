package com.gp.gpscript.keymgr.crypto.params;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.gp.gpscript.keymgr.crypto.CipherParameters;

public class DHKeyParameters
    extends AsymmetricKeyParameter
{
    private DHParameters    params;

    protected DHKeyParameters(
        boolean         isPrivate,
        DHParameters    params)
    {
        super(isPrivate);

        this.params = params;
    }

    public DHParameters getParameters()
    {
        return params;
    }

    public boolean equals(
        Object  obj)
    {
        if (!(obj instanceof DHKeyParameters))
        {
            return false;
        }

        DHKeyParameters    dhKey = (DHKeyParameters)obj;

        return (params != null && !params.equals(dhKey.getParameters()));
    }
}
