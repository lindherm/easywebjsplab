package com.gp.gpscript.keymgr.crypto.test;

import org.apache.log4j.Logger;

import com.gp.gpscript.keymgr.crypto.*;
import com.gp.gpscript.keymgr.crypto.engines.*;
import com.gp.gpscript.keymgr.crypto.macs.*;
import com.gp.gpscript.keymgr.crypto.params.*;
import com.gp.gpscript.keymgr.util.encoders.*;
import com.gp.gpscript.keymgr.util.test.*;


/**
 * MAC tester - vectors from
 * <a href=http://www.itl.nist.gov/fipspubs/fip81.htm>FIP 81</a> and
 * <a href=http://www.itl.nist.gov/fipspubs/fip113.htm>FIP 113</a>.
 */
public class MacTest
    implements Test
{private static Logger log = Logger.getLogger(MacTest.class);
    static byte[]   keyBytes = Hex.decode("0123456789abcdef");
    static byte[]   ivBytes = Hex.decode("1234567890abcdef");

    static byte[]   input = Hex.decode("37363534333231204e6f77206973207468652074696d6520666f7220");

    static byte[]   output1 = Hex.decode("f1d30f68");
    static byte[]   output2 = Hex.decode("58d2e77e");
    static byte[]   output3 = Hex.decode("cd647403");

    public MacTest()
    {
    }

    private boolean arraysEqual(
        byte[] a,
        byte[] b)
    {
        if (a.length != b.length)
        {
            return false;
        }

        for (int i = 0; i < a.length; i++)
        {
            if (a[i] != b[i]) return false;
        }

        return true;
    }

    public TestResult perform()
    {
        KeyParameter        key = new KeyParameter(keyBytes);
        BlockCipher         cipher = new DESEngine();
        Mac                 mac = new CBCBlockCipherMac(cipher);

        //
        // standard DAC - zero IV
        //
        mac.init(key);

        mac.update(input, 0, input.length);

        byte[]  out = new byte[4];

        mac.doFinal(out, 0);

        if (!arraysEqual(out, output1))
        {
            return new SimpleTestResult(false, getName() + ": Failed - expected " + new String(Hex.encode(output1)) + " got " + new String(Hex.encode(out)));
        }

        //
        // mac with IV.
        //
        ParametersWithIV    param = new ParametersWithIV(key, ivBytes);

        mac.init(param);

        mac.update(input, 0, input.length);

        out = new byte[4];

        mac.doFinal(out, 0);

        if (!arraysEqual(out, output2))
        {
            return new SimpleTestResult(false, getName() + ": Failed - expected " + new String(Hex.encode(output2)) + " got " + new String(Hex.encode(out)));
        }

        //
        // CFB mac with IV - 8 bit CFB mode
        //
        param = new ParametersWithIV(key, ivBytes);

        mac = new CFBBlockCipherMac(cipher);

        mac.init(param);

        mac.update(input, 0, input.length);

        out = new byte[4];

        mac.doFinal(out, 0);

        if (!arraysEqual(out, output3))
        {
            return new SimpleTestResult(false, getName() + ": Failed - expected " + new String(Hex.encode(output3)) + " got " + new String(Hex.encode(out)));
        }

        return new SimpleTestResult(true, getName() + ": Okay");
    }

    public String getName()
    {
        return "Mac";
    }

    public static void main(
        String[]    args)
    {
        MacTest    test = new MacTest();
        TestResult result = test.perform();

        log.debug(result);
    }
}
