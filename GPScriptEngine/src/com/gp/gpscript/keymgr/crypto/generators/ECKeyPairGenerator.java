package com.gp.gpscript.keymgr.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.gp.gpscript.keymgr.crypto.AsymmetricCipherKeyPair;
import com.gp.gpscript.keymgr.crypto.AsymmetricCipherKeyPairGenerator;
import com.gp.gpscript.keymgr.crypto.KeyGenerationParameters;
import com.gp.gpscript.keymgr.crypto.params.ECDomainParameters;
import com.gp.gpscript.keymgr.crypto.params.ECKeyGenerationParameters;
import com.gp.gpscript.keymgr.crypto.params.ECPrivateKeyParameters;
import com.gp.gpscript.keymgr.crypto.params.ECPublicKeyParameters;
import com.gp.gpscript.keymgr.math.ec.ECConstants;
import com.gp.gpscript.keymgr.math.ec.ECPoint;

public class ECKeyPairGenerator
    implements AsymmetricCipherKeyPairGenerator, ECConstants
{
	ECDomainParameters  params;
	SecureRandom        random;

	public void init(
        KeyGenerationParameters param)
    {
        ECKeyGenerationParameters  ecP = (ECKeyGenerationParameters)param;

        this.random = ecP.getRandom();
        this.params = ecP.getDomainParameters();
    }

    /**
     * Given the domain parameters this routine generates an EC key
     * pair in accordance with X9.62 section 5.2.1 pages 26, 27.
     */
    public AsymmetricCipherKeyPair generateKeyPair()
    {
		BigInteger n = params.getN();

		BigInteger d;

		do
		{
			d = new BigInteger(n.bitLength(), random);
		}
		while (d.equals(ZERO) || (d.compareTo(n) >= 0));

		ECPoint Q = params.getG().multiply(d);

		return new AsymmetricCipherKeyPair(
            new ECPublicKeyParameters(Q, params),
			new ECPrivateKeyParameters(d, params));
	}
}
