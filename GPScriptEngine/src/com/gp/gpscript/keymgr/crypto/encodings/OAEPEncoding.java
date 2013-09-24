package com.gp.gpscript.keymgr.crypto.encodings;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.gp.gpscript.keymgr.crypto.AsymmetricBlockCipher;
import com.gp.gpscript.keymgr.crypto.CipherParameters;
import com.gp.gpscript.keymgr.crypto.InvalidCipherTextException;
import com.gp.gpscript.keymgr.crypto.digests.SHA1Digest;
import com.gp.gpscript.keymgr.crypto.params.AsymmetricKeyParameter;
import com.gp.gpscript.keymgr.crypto.params.ParametersWithRandom;

/**
 * Optimal Asymmetric Encryption Padding (OAEP) - see PKCS 1 V 2.
 */
public class OAEPEncoding
    implements AsymmetricBlockCipher
{
    /**
     * by default the encoding parameters for OAEP padding are the empty
     * string - rather than have to create a hash and perform the calculation,
     * this is the output string for a SHA1 hash when given an empty string
     */
    private byte[]          defHash =
                            {
                                (byte)0xda, (byte)0x39, (byte)0xa3, (byte)0xee, (byte)0x5e, (byte)0x6b,
                                (byte)0x4b, (byte)0x0d, (byte)0x32, (byte)0x55, (byte)0xbf, (byte)0xef,
                                (byte)0x95, (byte)0x60, (byte)0x18, (byte)0x90, (byte)0xaf, (byte)0xd8,
                                (byte)0x07, (byte)0x09
                            };

    private SHA1Digest      hash = new SHA1Digest();     // if you change this - change defHash!

    private byte[]          encodingParams = null;  // the default

    private AsymmetricBlockCipher   engine;
    private SecureRandom            random;
    private boolean                 forEncryption;

    public OAEPEncoding(
        AsymmetricBlockCipher   cipher)
    {
        this.engine = cipher;
    }

    public AsymmetricBlockCipher getUnderlyingCipher()
    {
        return engine;
    }

    public void init(
        boolean             forEncryption,
        CipherParameters    param)
    {
        AsymmetricKeyParameter  kParam;

        if (param instanceof ParametersWithRandom)
        {
            ParametersWithRandom  rParam = (ParametersWithRandom)param;

            this.random = rParam.getRandom();
            kParam = (AsymmetricKeyParameter)rParam.getParameters();
        }
        else
        {
            this.random = new SecureRandom();
            kParam = (AsymmetricKeyParameter)param;
        }

        engine.init(forEncryption, kParam);

        this.forEncryption = forEncryption;
    }

    public int getInputBlockSize()
    {
        int     baseBlockSize = engine.getInputBlockSize();

        if (forEncryption)
        {
            return baseBlockSize - 1 - 2 * defHash.length;
        }
        else
        {
            return baseBlockSize;
        }
    }

    public int getOutputBlockSize()
    {
        int     baseBlockSize = engine.getOutputBlockSize();

        if (forEncryption)
        {
            return baseBlockSize;
        }
        else
        {
            return baseBlockSize - 1 - 2 * defHash.length;
        }
    }

    public byte[] processBlock(
        byte[]  in,
        int     inOff,
        int     inLen)
        throws InvalidCipherTextException
    {
        if (forEncryption)
        {
            return encodeBlock(in, inOff, inLen);
        }
        else
        {
            return decodeBlock(in, inOff, inLen);
        }
    }

    public byte[] encodeBlock(
        byte[]  in,
        int     inOff,
        int     inLen)
        throws InvalidCipherTextException
    {
        byte[]  block = new byte[getInputBlockSize() + 1 + 2 * defHash.length];

        //
        // copy in the message
        //
        System.arraycopy(in, inOff, block, block.length - inLen, inLen);

        //
        // add sentinel
        //
        block[block.length - inLen - 1] = 0x01;

        //
        // as the block is already zeroed - there's no need to add PS (the >= 0 pad of 0)
        //

        //
        // add the hash of the encoding params.
        //
        if (encodingParams == null)
        {
            System.arraycopy(defHash, 0, block, defHash.length, defHash.length);
        }
        else
        {
            throw new RuntimeException("forget something?");
        }

        //
        // generate the seed.
        //
        byte[]  seed = new byte[defHash.length];

        random.nextBytes(seed);

        //
        // mask the message block.
        //
        byte[]  mask = maskGeneratorFunction1(seed, 0, seed.length, block.length - defHash.length);

        for (int i = defHash.length; i != block.length; i++)
        {
            block[i] ^= mask[i - defHash.length];
        }

        //
        // add in the seed
        //
        System.arraycopy(seed, 0, block, 0, defHash.length);

        //
        // mask the seed.
        //
        mask = maskGeneratorFunction1(
                        block, defHash.length, block.length - defHash.length, defHash.length);

        for (int i = 0; i != defHash.length; i++)
        {
            block[i] ^= mask[i];
        }

        return engine.processBlock(block, 0, block.length);
    }

    /**
     * @exception InvalidCipherTextException if the decryypted block turns out to
     * be badly formatted.
     */
    public byte[] decodeBlock(
        byte[]  in,
        int     inOff,
        int     inLen)
        throws InvalidCipherTextException
    {
        byte[]  data = engine.processBlock(in, inOff, inLen);
        byte[]  block = null;

        //
        // as we may have zeros in our leading bytes for the block we produced
        // on encryption, we need to make sure our decrypted block comes back
        // the same size.
        //
        if (data.length < engine.getOutputBlockSize())
        {
            block = new byte[engine.getOutputBlockSize()];

            System.arraycopy(data, 0, block, block.length - data.length, data.length);
        }
        else
        {
            block = data;
        }

        if (block.length < (2 * defHash.length) + 1)
        {
            throw new InvalidCipherTextException("data too short");
        }

        //
        // unmask the seed.
        //
        byte[] mask = maskGeneratorFunction1(
                    block, defHash.length, block.length - defHash.length, defHash.length);

        for (int i = 0; i != defHash.length; i++)
        {
            block[i] ^= mask[i];
        }

        //
        // unmask the message block.
        //
        mask = maskGeneratorFunction1(block, 0, defHash.length, block.length - defHash.length);

        for (int i = defHash.length; i != block.length; i++)
        {
            block[i] ^= mask[i - defHash.length];
        }

        //
        // check the hash of the encoding params.
        //
        if (encodingParams == null)
        {
            for (int i = 0; i != defHash.length; i++)
            {
                if (defHash[i] != block[defHash.length + i])
                {
                    throw new InvalidCipherTextException("data hash wrong");
                }
            }
        }
        else
        {
            throw new RuntimeException("forget something?");
        }

        //
        // find the data block
        //
        int start;

        for (start = 2 * defHash.length; start != block.length; start++)
        {
            if (block[start] == 1 || block[start] != 0)
            {
                break;
            }
        }

        if (start >= (block.length - 1) || block[start] != 1)
        {
            throw new InvalidCipherTextException("data start wrong " + start);
        }

        start++;

        //
        // extract the data block
        //
        byte[]  output = new byte[block.length - start];

        System.arraycopy(block, start, output, 0, output.length);

        return output;
    }

    /**
     * int to octet string.
     */
    private void ItoOSP(
        int     i,
        byte[]  sp)
    {
        sp[0] = (byte)(i >>> 24);
        sp[1] = (byte)(i >>> 16);
        sp[2] = (byte)(i >>> 8);
        sp[3] = (byte)(i >>> 0);
    }

    /**
     * mask generator function, as described in PKCS1v2.
     */
    private byte[] maskGeneratorFunction1(
        byte[]  Z,
        int     zOff,
        int     zLen,
        int     length)
    {
        byte[]  mask = new byte[length];
        byte[]  hashBuf = new byte[defHash.length];
        byte[]  C = new byte[4];
        int     counter = 0;

        hash.reset();

        do
        {
            ItoOSP(counter, C);

            hash.update(Z, zOff, zLen);
            hash.update(C, 0, C.length);
            hash.doFinal(hashBuf, 0);

            System.arraycopy(hashBuf, 0, mask, counter * defHash.length, defHash.length);
        }
        while (++counter < (length / defHash.length));

        if ((counter * defHash.length) < length)
        {
            ItoOSP(counter, C);

            hash.update(Z, zOff, zLen);
            hash.update(C, 0, C.length);
            hash.doFinal(hashBuf, 0);

            System.arraycopy(hashBuf, 0, mask, counter * defHash.length, mask.length - (counter * defHash.length));
        }

        return mask;
    }
}
