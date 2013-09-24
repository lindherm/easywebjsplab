package com.gp.gpscript.keymgr.crypto.agreement;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.gp.gpscript.keymgr.crypto.BasicAgreement;
import com.gp.gpscript.keymgr.crypto.CipherParameters;
import com.gp.gpscript.keymgr.crypto.params.AsymmetricKeyParameter;
import com.gp.gpscript.keymgr.crypto.params.DHParameters;
import com.gp.gpscript.keymgr.crypto.params.DHPrivateKeyParameters;
import com.gp.gpscript.keymgr.crypto.params.DHPublicKeyParameters;
import com.gp.gpscript.keymgr.crypto.params.ParametersWithRandom;

/**
 * a Diffie-Hellman key agreement class.
 * <p>
 * note: This is only the basic algorithm, it doesn't take advantage of
 * long term public keys if they are available. See the DHAgreement class
 * for a "better" implementation.
 */
public class DHBasicAgreement
    implements BasicAgreement
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
     * given a short term public key from a given party calculate the next
     * message in the agreement sequence.
     */
    public BigInteger calculateAgreement(
        CipherParameters   pubKey)
    {
        DHPublicKeyParameters   pub = (DHPublicKeyParameters)pubKey;

        if (!pub.getParameters().equals(dhParams))
        {
            throw new IllegalArgumentException("Diffie-Hellman public key has wrong parameters.");
        }

        return pub.getY().modPow(key.getX(), dhParams.getP());
    }
}
