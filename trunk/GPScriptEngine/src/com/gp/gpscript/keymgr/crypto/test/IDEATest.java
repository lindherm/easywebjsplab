package com.gp.gpscript.keymgr.crypto.test;

import org.apache.log4j.Logger;

import com.gp.gpscript.keymgr.crypto.*;
import com.gp.gpscript.keymgr.crypto.engines.*;
import com.gp.gpscript.keymgr.crypto.params.*;
import com.gp.gpscript.keymgr.util.encoders.Hex;
import com.gp.gpscript.keymgr.util.test.*;

/**
 */
public class IDEATest
    extends CipherTest
{private static Logger log = Logger.getLogger(IDEATest.class);
    static Test[] tests =
            {
                new BlockCipherVectorTest(0, new IDEAEngine(),
                        new KeyParameter(Hex.decode("00112233445566778899AABBCCDDEEFF")),
                        "000102030405060708090a0b0c0d0e0f", "ed732271a7b39f475b4b2b6719f194bf"),
                new BlockCipherVectorTest(0, new IDEAEngine(),
                        new KeyParameter(Hex.decode("00112233445566778899AABBCCDDEEFF")),
                        "f0f1f2f3f4f5f6f7f8f9fafbfcfdfeff", "b8bc6ed5c899265d2bcfad1fc6d4287d")
            };

    IDEATest()
    {
        super(tests);
    }

    public String getName()
    {
        return "IDEA";
    }

    public static void main(
        String[]    args)
    {
        IDEATest    test = new IDEATest();
        TestResult      result = test.perform();

        log.debug(result);
    }
}
