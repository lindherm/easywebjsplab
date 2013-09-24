package com.gp.gpscript.keymgr.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.gp.gpscript.keymgr.crypto.AsymmetricCipherKeyPair;
import com.gp.gpscript.keymgr.crypto.AsymmetricCipherKeyPairGenerator;
import com.gp.gpscript.keymgr.crypto.KeyGenerationParameters;
import com.gp.gpscript.keymgr.crypto.params.DSAKeyGenerationParameters;
import com.gp.gpscript.keymgr.crypto.params.DSAParameters;
import com.gp.gpscript.keymgr.crypto.params.DSAPrivateKeyParameters;
import com.gp.gpscript.keymgr.crypto.params.DSAPublicKeyParameters;

/**
 * a DSA key pair generator.
 *
 * This generates DSA keys in line with the method described
 * in FIPS 186-2.
 */
public class DSAKeyPairGenerator
    implements AsymmetricCipherKeyPairGenerator
{
    private static BigInteger ZERO = BigInteger.valueOf(0);

    private DSAKeyGenerationParameters param;

    public void init(
        KeyGenerationParameters param)
    {
        this.param = (DSAKeyGenerationParameters)param;
    }

    public AsymmetricCipherKeyPair generateKeyPair()
    {
        BigInteger      p, q, g, x, y;
        DSAParameters   dsaParams = param.getParameters();
        SecureRandom    random = param.getRandom();

        q = dsaParams.getQ();
        p = dsaParams.getP();
        g = dsaParams.getG();

        do
        {
            x = new BigInteger(160, random);
        }
        while (x.equals(ZERO) || x.compareTo(q) >= 0);

        //
        // calculate the public key.
        //
        y = g.modPow(x, p);

        return new AsymmetricCipherKeyPair(
                new DSAPublicKeyParameters(y, dsaParams),
                new DSAPrivateKeyParameters(x, dsaParams));
    }
}
