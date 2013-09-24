package com.gp.gpscript.keymgr.crypto.params;

import java.security.SecureRandom;
import java.math.BigInteger;

import com.gp.gpscript.keymgr.crypto.KeyGenerationParameters;

public class RSAKeyGenerationParameters
    extends KeyGenerationParameters
{
	private BigInteger publicExponent;
    private int certainty;

    public RSAKeyGenerationParameters(
		BigInteger		publicExponent,
        SecureRandom    random,
        int             strength,
        int             certainty)
    {
        super(random, strength);

		this.publicExponent = publicExponent;
        this.certainty = certainty;
    }

	public BigInteger getPublicExponent()
	{
		return publicExponent;
	}

    public int getCertainty()
    {
        return certainty;
    }
}
