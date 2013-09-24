package com.gp.gpscript.keymgr.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.gp.gpscript.keymgr.crypto.AsymmetricCipherKeyPair;
import com.gp.gpscript.keymgr.crypto.AsymmetricCipherKeyPairGenerator;
import com.gp.gpscript.keymgr.crypto.KeyGenerationParameters;
import com.gp.gpscript.keymgr.crypto.params.ElGamalKeyGenerationParameters;
import com.gp.gpscript.keymgr.crypto.params.ElGamalParameters;
import com.gp.gpscript.keymgr.crypto.params.ElGamalPrivateKeyParameters;
import com.gp.gpscript.keymgr.crypto.params.ElGamalPublicKeyParameters;

/**
 * a ElGamal key pair generator.
 * <p>
 * This generates keys consistent for use with ElGamal as described in
 * page 164 of "Handbook of Applied Cryptography".
 */
public class ElGamalKeyPairGenerator
    implements AsymmetricCipherKeyPairGenerator
{
    private ElGamalKeyGenerationParameters param;

    public void init(
        KeyGenerationParameters param)
    {
        this.param = (ElGamalKeyGenerationParameters)param;
    }

    public AsymmetricCipherKeyPair generateKeyPair()
    {
        BigInteger           p, g, x, y;
        int                  qLength = param.getStrength() - 1;
        ElGamalParameters    elParams = param.getParameters();

        p = elParams.getP();
        g = elParams.getG();

        //
        // calculate the private key
        //
        x = new BigInteger(qLength, param.getRandom());

        //
        // calculate the public key.
        //
        y = g.modPow(x, p);

        return new AsymmetricCipherKeyPair(
                new ElGamalPublicKeyParameters(y, elParams),
                new ElGamalPrivateKeyParameters(x, elParams));
    }
}
