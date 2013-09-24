package com.gp.gpscript.keymgr.crypto.params;

import java.security.SecureRandom;
import java.math.BigInteger;

import com.gp.gpscript.keymgr.crypto.KeyGenerationParameters;

public class ECKeyGenerationParameters
    extends KeyGenerationParameters
{
	private ECDomainParameters  domainParams;

    public ECKeyGenerationParameters(
		ECDomainParameters		domainParams,
        SecureRandom            random)
    {
        super(random, domainParams.getN().bitLength());

		this.domainParams = domainParams;
    }

	public ECDomainParameters getDomainParameters()
	{
		return domainParams;
	}
}
