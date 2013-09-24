package com.gp.gpscript.keymgr.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.gp.gpscript.keymgr.crypto.AsymmetricCipherKeyPair;
import com.gp.gpscript.keymgr.crypto.AsymmetricCipherKeyPairGenerator;
import com.gp.gpscript.keymgr.crypto.KeyGenerationParameters;
import com.gp.gpscript.keymgr.crypto.params.DHKeyGenerationParameters;
import com.gp.gpscript.keymgr.crypto.params.DHParameters;
import com.gp.gpscript.keymgr.crypto.params.DHPrivateKeyParameters;
import com.gp.gpscript.keymgr.crypto.params.DHPublicKeyParameters;

/**
 * a basic Diffie-Helman key pair generator.
 *
 * This generates keys consistent for use with the basic algorithm for
 * Diffie-Helman.
 */
public class DHBasicKeyPairGenerator
    implements AsymmetricCipherKeyPairGenerator
{
    private DHKeyGenerationParameters param;

    public void init(
        KeyGenerationParameters param)
    {
        this.param = (DHKeyGenerationParameters)param;
    }

    public AsymmetricCipherKeyPair generateKeyPair()
    {
        BigInteger      p, q, g, x, y;
        int             qLength = param.getStrength() - 1;
        DHParameters    dhParams = param.getParameters();

        p = dhParams.getP();
        g = dhParams.getG();

        //
        // calculate the private key
        //
        x = new BigInteger(qLength, 0, param.getRandom());

        //
        // calculate the public key.
        //
        y = g.modPow(x, p);

        return new AsymmetricCipherKeyPair(
                new DHPublicKeyParameters(y, dhParams),
                new DHPrivateKeyParameters(x, dhParams));
    }
}
