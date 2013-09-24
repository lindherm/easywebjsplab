package com.gp.gpscript.keymgr.crypto.test;

import com.gp.gpscript.keymgr.crypto.*;
import com.gp.gpscript.keymgr.util.encoders.Hex;
import com.gp.gpscript.keymgr.util.test.*;

/**
 * a basic test that takes a stream cipher, key parameter, and an input
 * and output string.
 */
public class StreamCipherVectorTest
    implements Test
{
    int                 id;
    StreamCipher        cipher;
    CipherParameters    param;
    byte[]              input;
    byte[]              output;

    public StreamCipherVectorTest(
        int                 id,
        StreamCipher        cipher,
        CipherParameters    param,
        String              input,
        String              output)
    {
        this.id = id;
        this.cipher = cipher;
        this.param = param;
        this.input = Hex.decode(input);
        this.output = Hex.decode(output);
    }

    public String getName()
    {
        return cipher.getAlgorithmName() + " Vector Test " + id;
    }

    public TestResult perform()
    {
        cipher.init(true, param);

        byte[]  out = new byte[input.length];

        cipher.processBytes(input, 0, input.length, out, 0);

        if (!isEqualArray(out, output))
        {
            return new SimpleTestResult(false,
                    getName() + ": failed - " + "expected " + new String(Hex.encode(output)) + " got " + new String(Hex.encode(out)));
        }

        cipher.init(false, param);

        cipher.processBytes(output, 0, output.length, out, 0);

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
