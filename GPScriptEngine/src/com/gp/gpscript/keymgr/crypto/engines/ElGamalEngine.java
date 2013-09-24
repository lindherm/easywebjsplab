package com.gp.gpscript.keymgr.crypto.engines;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.gp.gpscript.keymgr.crypto.AsymmetricBlockCipher;
import com.gp.gpscript.keymgr.crypto.CipherParameters;
import com.gp.gpscript.keymgr.crypto.DataLengthException;
import com.gp.gpscript.keymgr.crypto.params.AsymmetricKeyParameter;
import com.gp.gpscript.keymgr.crypto.params.ElGamalKeyParameters;
import com.gp.gpscript.keymgr.crypto.params.ElGamalPrivateKeyParameters;
import com.gp.gpscript.keymgr.crypto.params.ElGamalPublicKeyParameters;
import com.gp.gpscript.keymgr.crypto.params.ParametersWithRandom;

/**
 * this does your basic ElGamal algorithm.
 */
public class ElGamalEngine
    implements AsymmetricBlockCipher
{
    private ElGamalKeyParameters    key;
    private SecureRandom            random;
    private boolean                 forEncryption;

    private static BigInteger       ZERO = BigInteger.valueOf(0);
    private static BigInteger       ONE = BigInteger.valueOf(1);
    private static BigInteger       TWO = BigInteger.valueOf(2);

    /**
     * initialise the ElGamal engine.
     *
     * @param forEncryption true if we are encrypting, false otherwise.
     * @param param the necessary ElGamal key parameters.
     */
    public void init(
        boolean             forEncryption,
        CipherParameters    param)
    {
        if (param instanceof ParametersWithRandom)
        {
            ParametersWithRandom    p = (ParametersWithRandom)param;

            this.key = (ElGamalKeyParameters)p.getParameters();
            this.random = p.getRandom();
        }
        else
        {
            this.key = (ElGamalKeyParameters)param;
            this.random = new SecureRandom();
        }

        this.forEncryption = forEncryption;
    }

    /**
     * Return the maximum size for an input block to this engine.
     * For ElGamal this is always one byte less than the size of P on
     * encryption, and twice the length as the size of P on decryption.
     *
     * @return maximum size for an input block.
     */
    public int getInputBlockSize()
    {
        int     bitSize = key.getParameters().getP().bitLength();

        if (forEncryption)
        {
            if ((bitSize % 8) == 0)
            {
                return bitSize / 8 - 1;
            }

            return bitSize / 8;
        }
        else
        {
            return 2 * (((bitSize - 1) + 7) / 8);
        }
    }

    /**
     * Return the maximum size for an output block to this engine.
     * For ElGamal this is always one byte less than the size of P on
     * decryption, and twice the length as the size of P on encryption.
     *
     * @return maximum size for an output block.
     */
    public int getOutputBlockSize()
    {
        int     bitSize = key.getParameters().getP().bitLength();

        if (forEncryption)
        {
            return 2 * (((bitSize - 1) + 7) / 8);
        }
        else
        {
            return (bitSize - 7) / 8;
        }
    }

    /**
     * Process a single block using the basic ElGamal algorithm.
     *
     * @param in the input array.
     * @param inOff the offset into the input buffer where the data starts.
     * @param inLen the length of the data to be processed.
     * @return the result of the ElGamal process.
     * @exception DataLengthException the input block is too large.
     */
    public byte[] processBlock(
        byte[]  in,
        int     inOff,
        int     inLen)
    {
        if (inLen > (getInputBlockSize() + 1))
        {
            throw new DataLengthException("input too large for ElGamal cipher.\n");
        }
        else if (inLen == (getInputBlockSize() + 1) && (in[inOff] & 0x80) != 0)
        {
            throw new DataLengthException("input too large for ElGamal cipher.\n");
        }

        byte[]  block;

        if (inOff != 0 || inLen != in.length)
        {
            block = new byte[inLen];

            System.arraycopy(in, inOff, block, 0, inLen);
        }
        else
        {
            block = in;
        }

        BigInteger  input = new BigInteger(1, block);
        BigInteger  g = key.getParameters().getG();
        BigInteger  p = key.getParameters().getP();

        if (key instanceof ElGamalPrivateKeyParameters)
        {
            byte[]  in1 = new byte[in.length / 2];
            byte[]  in2 = new byte[in.length / 2];

            System.arraycopy(in, 0, in1, 0, in1.length);
            System.arraycopy(in, in1.length, in2, 0, in2.length);

            BigInteger  gamma = new BigInteger(1, in1);
            BigInteger  phi = new BigInteger(1, in2);

            ElGamalPrivateKeyParameters  priv = (ElGamalPrivateKeyParameters)key;

            BigInteger  m = gamma.modPow(p.subtract(ONE).subtract(priv.getX()), p).multiply(phi).mod(p);

            byte[]      out = m.toByteArray();

            if (out[0] != 0)
            {
                return out;
            }
            else
            {
                byte[]  output = new byte[out.length - 1];
                System.arraycopy(out, 1, output, 0, output.length);

                return output;
            }
        }
        else
        {
            ElGamalPublicKeyParameters  pub = (ElGamalPublicKeyParameters)key;

            BigInteger  k = new BigInteger(p.bitLength(), random);

            while (k.equals(ZERO) || (k.compareTo(p.subtract(TWO)) > 0))
            {
                k = new BigInteger(p.bitLength(), random);
            }

            BigInteger  gamma = g.modPow(k, p);
            BigInteger  phi = input.multiply(pub.getY().modPow(k, p)).mod(p);

            byte[]  out1 = gamma.toByteArray();
            byte[]  out2 = phi.toByteArray();
            byte[]  output = new byte[this.getOutputBlockSize()];

            if (out1[0] == 0)
            {
                System.arraycopy(out1, 1, output, output.length / 2 - (out1.length - 1), out1.length - 1);
            }
            else
            {
                System.arraycopy(out1, 0, output, output.length / 2 - out1.length, out1.length);
            }

            if (out2[0] == 0)
            {
                System.arraycopy(out2, 1, output, output.length - (out2.length - 1), out2.length - 1);
            }
            else
            {
                System.arraycopy(out2, 0, output, output.length - out2.length, out2.length);
            }

            return output;
        }
    }
}
