package com.gp.gpscript.keymgr.crypto.params;

import java.math.BigInteger;

public class ECKeyParameters
    extends AsymmetricKeyParameter
{
	ECDomainParameters params;

	protected ECKeyParameters(
        boolean             isPrivate,
        ECDomainParameters  params)
	{
        super(isPrivate);

		this.params = params;
	}

	public ECDomainParameters getParameters()
	{
		return params;
	}
}
