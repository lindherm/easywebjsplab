package com.gp.gpscript.keymgr.crypto.params;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.gp.gpscript.keymgr.crypto.CipherParameters;

public class RSAKeyParameters
    extends AsymmetricKeyParameter
{
    private BigInteger      modulus;
    private BigInteger      exponent;

    public RSAKeyParameters(
        boolean     isPrivate,
        BigInteger  modulus,
        BigInteger  exponent)
    {
        super(isPrivate);

        this.modulus = modulus;
        this.exponent = exponent;
    }

    public BigInteger getModulus()
    {
        return modulus;
    }

    public BigInteger getExponent()
    {
        return exponent;
    }
}
