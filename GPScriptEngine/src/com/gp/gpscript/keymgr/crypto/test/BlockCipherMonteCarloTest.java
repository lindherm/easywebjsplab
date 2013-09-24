package com.gp.gpscript.keymgr.crypto.test;

import com.gp.gpscript.keymgr.crypto.*;
import com.gp.gpscript.keymgr.util.encoders.Hex;
import com.gp.gpscript.keymgr.util.test.*;

/**
 * a basic test that takes a cipher, key parameter, and an input
 * and output string. This test wraps the engine in a buffered block
 * cipher with padding disabled.
 */
public class BlockCipherMonteCarloTest
    implements Test
{
    int                 id;
    int                 iterations;
    BlockCipher         engine;
    CipherParameters    param;
    byte[]              input;
    byte[]              output;

    public BlockCipherMonteCarloTest(
        int                 id,
        int                 iterations,
        BlockCipher         engine,
        CipherParameters    param,
        String              input,
        String              output)
    {
        this.id = id;
        this.iterations = iterations;
        this.engine = engine;
        this.param = param;
        this.input = Hex.decode(input);
        this.output = Hex.decode(output);
    }

    public String getName()
    {
        return engine.getAlgorithmName() + " Monte Carlo Test " + id;
    }

    public TestResult perform()
    {
        BufferedBlockCipher cipher = new BufferedBlockCipher(engine);

        cipher.init(true, param);

        byte[]  out = new byte[input.length];

        System.arraycopy(input, 0, out, 0, out.length);

        for (int i = 0; i != iterations; i++)
        {
            int len1 = cipher.processBytes(out, 0, out.length, out, 0);

            try
            {
                cipher.doFinal(out, len1);
            }
            catch (CryptoException e)
            {
                e.printStackTrace();
                return new SimpleTestResult(false,
                       getName() + ": failed - exception " + e.toString());
            }
        }

        if (!isEqualArray(out, output))
        {
            return new SimpleTestResult(false,
                    getName() + ": failed - " + "expected " + new String(Hex.encode(output)) + " got " + new String(Hex.encode(out)));
        }

        cipher.init(false, param);

        for (int i = 0; i != iterations; i++)
        {
            int len1 = cipher.processBytes(out, 0, out.length, out, 0);

            try
            {
                cipher.doFinal(out, len1);
            }
            catch (CryptoException e)
            {
                e.printStackTrace();
                return new SimpleTestResult(false,
                       getName() + ": failed reversal - exception " + e.toString());
            }
        }

        if (!isEqualArray(input, out))
        {
            return new SimpleTestResult(false, getName() + ": failed reversal");
        }

        return new SimpleTestResult(true, getName() + ": OKAY");
    }

    private boolean isEqualArray(
        byte[]  a,
        byte[]  b)
    {
        if (a.length != b.length)
        {
            return false;
        }

        for (int i = 0; i != a.length; i++)
        {
            if (a[i] != b[i])
            {
                return false;
            }
        }

        return true;
    }
}
