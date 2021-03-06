package com.gp.gpscript.keymgr.crypto.agreement;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.gp.gpscript.keymgr.crypto.CipherParameters;
import com.gp.gpscript.keymgr.crypto.params.AsymmetricKeyParameter;
import com.gp.gpscript.keymgr.crypto.params.DHParameters;
import com.gp.gpscript.keymgr.crypto.params.DHPrivateKeyParameters;
import com.gp.gpscript.keymgr.crypto.params.DHPublicKeyParameters;
import com.gp.gpscript.keymgr.crypto.params.ParametersWithRandom;

/**
 * a Diffie-Hellman key exchange engine.
 * <p>
 * note: This uses MTI/A0 key agreement in order to make the key agreement
 * secure against passive attacks. If you're doing Diffie-Hellman and both
 * parties have long term public keys you should look at using this. For
 * further information have a look at RFC 2631.
 * <p>
 * It's possible to extend this to more than two parties as well, for the moment
 * that is left as an exercise for the reader.
 */
public class DHAgreement
{
    private DHPrivateKeyParameters  key;
    private DHParameters            dhParams;
    private BigInteger              privateValue;
    private SecureRandom            random;

    public void init(
        CipherParameters    param)
    {
        AsymmetricKeyParameter  kParam;

        if (param instanceof ParametersWithRandom)
        {
            ParametersWithRandom    rParam = (ParametersWithRandom)param;

            this.random = rParam.getRandom();
            kParam = (AsymmetricKeyParameter)rParam.getParameters();
        }
        else
        {
            this.random = new SecureRandom();
            kParam = (AsymmetricKeyParameter)param;
        }


        if (!(kParam instanceof DHPrivateKeyParameters))
        {
            throw new IllegalArgumentException("DHEngine expects DHPrivateKeyParameters");
        }

        this.key = (DHPrivateKeyParameters)kParam;
        this.dhParams = key.getParameters();
    }

    /**
     * calculate our initial message.
     */
    public BigInteger calculateMessage()
    {
        this.privateValue = new BigInteger(
                                    dhParams.getP().bitLength() - 1, 0, random);

        return dhParams.getG().modPow(privateValue, dhParams.getP());
    }

    /**
     * given a message from a given party and the coresponding public key
     * calculate the next message in the agreement sequence. In this case
     * this will represent the shared secret.
     */
    public BigInteger calculateAgreement(
        DHPublicKeyParameters   pub,
        BigInteger              message)
    {
        if (!pub.getParameters().equals(dhParams))
        {
            throw new IllegalArgumentException("Diffie-Hellman public key has wrong parameters.");
        }

        return message.modPow(key.getX(), dhParams.getP()).multiply(pub.getY().modPow(privateValue, dhParams.getP())).mod(dhParams.getP());
    }
}
